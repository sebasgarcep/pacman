/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.objetos.manejador;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.Timer;
import java.util.TimerTask;
import pacman.interfaz.paneles.AbstractContenedor;
import pacman.interfaz.paneles.HiScores;
import pacman.interfaz.paneles.Menu;
import pacman.interfaz.paneles.PauseDialog;
import pacman.objetos.Enemigo;
import pacman.objetos.Jugador;
import pacman.objetos.Mundo;
import pacman.objetos.Pickup;
import pacman.objetos.Sprite;
import pacman.online.Agent;
import pacman.online.Config;

/**
 *
 * @author sebastian
 */
public final class Juego extends Thread {
    public static final int CANVASWIDTH = 640;
    public static final int CANVASHEIGHT = 480;
    public static final int TILESIZE = 40;
    public static final int GRIDWIDTH = 16;
    public static final int GRIDHEIGHT = 12;
    private static final int GAME_OK = 0;
    private static final int GAME_OVER = 1;
    private static final int PICKUP = 2;
    private static final int POWERUP = 3;
    private static final int MAXNIVEL = 3;

    public enum Modo {
        Arcade,
        Multijugador,
        Online
    }
    
    private Modo modo;
    private AbstractContenedor canvas;
    private Mundo mundo;
    private List<Sprite> sprites;
    private Jugador j1;
    private Jugador j2;
    private volatile boolean game_continue;
    private volatile boolean level_continue;
    private volatile boolean paused;
    private Agent agent;
    private Graficos gm;
    private PauseDialog dialog;
    
    private Juego(Modo modo) {
        this.dialog = null;
        this.gm = null;
        this.agent = null;
        this.canvas = null;
        this.modo = modo;
        this.sprites = Collections.synchronizedList(new CopyOnWriteArrayList<>());
        
        switch(modo) {
            case Arcade:
                this.mundo = Mundo.laberinto();
                Pickup.laberinto(this.sprites, 1);
                this.j1 = Jugador.J1(this.mundo);
                this.sprites.add(j1);
                Enemigo.Zombie(this.mundo, this.sprites);
                break;
            case Multijugador:
                this.mundo = Mundo.laberinto();
                Pickup.laberinto(this.sprites, 1);
                this.j1 = Jugador.J1(this.mundo);
                this.sprites.add(j1);
                this.j2 = Jugador.J2(this.mundo);
                this.sprites.add(j2);
                Enemigo.Zombie(this.mundo, this.sprites);
                break;
            case Online:
                this.mundo = Mundo.Online();
                Pickup.laberinto(this.sprites, 0);
                this.j1 = Jugador.J1(this.mundo);
                this.sprites.add(j1);
                this.j2 = Jugador.J2(this.mundo);
                this.sprites.add(j2);
                break;
        }
        
        this.setName("Game Logic");
    }
    
    private Juego(Agent agent) {
        this(Modo.Online);
        this.agent = agent;
    }

    public static Juego Arcade() {
        return new Juego(Modo.Arcade);
    }

    public static Juego Multijugador() {
        return new Juego(Modo.Multijugador);
    }
    
    public static Juego Online(Agent agent) {
        return new Juego(agent);
    }
    
    public void init(AbstractContenedor canvas, Graficos gm) {
        this.gm = gm;
        this.canvas = canvas;
        
        this.canvas.attachKeyListener(this.j1);
        if(this.modo != Modo.Arcade) {
            this.canvas.attachKeyListener(this.j2);
        }
        
        this.gm.init(this.sprites, this.mundo, this.agent);
        
        this.start();
        
    }
    
    public void gameOver() {
        this.game_continue = false;
        this.level_continue = false;
        this.gm.stopGraphics();
        if(this.modo != Modo.Online) {
            String nj1 = JOptionPane.showInputDialog("Fin del Juego. Diganos su nombre Jugador 1:");
            ScoreManager.registrar(nj1, this.j1.getScore());
            if(this.modo == Modo.Multijugador) {
                String nj2 = JOptionPane.showInputDialog("Fin del Juego. Diganos su nombre Jugador 2:");
                ScoreManager.registrar(nj2, this.j2.getScore());
            }
            this.canvas.setContexto(new HiScores());
        } else {
            this.agent.send(Config.GAME_OVER);
            if(this.j1.getScore() > this.j2.getScore()) {
                JOptionPane.showMessageDialog(this.canvas, "Fin del juego" + "\n" + "Ganador: Jugador 1" + "\n" + "Puntajes:" + "\n" + "J1:" + this.j1.getScore() + "\n" + "J2:" + this.j2.getScore());
            } else if(this.j1.getScore() < this.j2.getScore()) {
                JOptionPane.showMessageDialog(this.canvas, "Fin del juego" + "\n" + "Ganador: Jugador 2" + "\n" + "Puntajes:" + "\n" + "J1:" + this.j1.getScore() + "\n" + "J2:" + this.j2.getScore());
            } else {
                JOptionPane.showMessageDialog(this.canvas, "Fin del juego\nTermino en empate");
            }
            this.canvas.setContexto(new Menu());
        }
    }
    
    private void setupNivel() {
        JOptionPane.showMessageDialog(this.canvas, "Has completado este nivel.");
        this.sprites.removeAll(this.sprites);
        int nivel = this.mundo.next();
        if(nivel <= Juego.MAXNIVEL) {
            Pickup.laberinto(this.sprites, nivel);
            this.j1.reset();
            this.sprites.add(this.j1);
            if(this.modo == Modo.Multijugador) {
                this.j2.reset();
                this.sprites.add(this.j2);
            }
            Enemigo.Zombie(this.mundo, this.sprites);
        } else {
           JOptionPane.showMessageDialog(this.canvas, "Has acabado el juego. Felicitaciones!"); 
        }
    }
    
    public void pauseGame() {
        this.paused = !this.paused;
        if(this.dialog == null) {
            this.dialog = new PauseDialog(this.canvas, this);
        }
        if(this.paused) {
            this.dialog.pause();
        } else {
            this.dialog.resume();
        }
    }
    
    public boolean getPauseState() {
        return this.paused;
    }
    
    @Override
    public void run() {
        this.game_continue = true;
        while(this.game_continue) {
            Timer gameTimer = new Timer();
            gameTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    JOptionPane.showMessageDialog(canvas, "Se le ha acabado el tiempo.");
                    gameOver();
                }
            }, 5*60*1000);
            this.level_continue = true;
            int pickup1 = 0;
            int pickup2 = 0;
            boolean exit = true;
            this.paused = false;
            while(this.level_continue) {
                while(this.paused);
                this.mundo.addSmell(this.j1, this.j1.id() - 1);
                if(this.modo == Modo.Multijugador) this.mundo.addSmell(this.j2, 0);
                if(this.modo == Modo.Online) this.mundo.addSmell(this.j2, this.j2.id() - 1);
                this.mundo.reduceSmell();
                exit = true;
                for(Sprite s : this.sprites) {
                    if(s instanceof Pickup) {
                        if(((Pickup)s).type == Pickup.Type.Comida) {
                            exit = false;
                        }
                    }
                    int logicValue = GAME_OK;
                    logicValue = collisionLogic(this.j1, s);
                    if(logicValue == GAME_OVER) {
                        break;
                    } else if(this.modo == Modo.Online && logicValue == PICKUP){
                        pickup1 = pickup1 + 1;
                        if(pickup1 == 5) {
                            pickup1 = 0;
                            Enemigo.addZombieOnline(this.mundo, this.sprites, this.j2);
                        }
                    }
                    if(this.modo != Modo.Arcade) logicValue = collisionLogic(this.j2, s);
                    if(this.modo != Modo.Arcade && logicValue == GAME_OVER) {
                        break;
                    } else if(this.modo == Modo.Online && logicValue == PICKUP){
                        pickup2 = pickup2 + 1;
                        if(pickup2 == 5) {
                            pickup2 = 0;
                            Enemigo.addZombieOnline(this.mundo, this.sprites, this.j1);
                        }
                    }
                    if(s instanceof Enemigo) {
                        Enemigo e = (Enemigo)s;
                        e.makeChoice();
                    }
                }
                if(exit) {
                    if(this.modo != Modo.Online) {
                        this.level_continue = false;
                    } else {
                        gameOver();
                    }
                }
                try {
                    if(this.modo == Modo.Online) informarCliente();
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(this.modo != Modo.Online && exit) {
                gameTimer.cancel();
                gameTimer.purge();
                setupNivel();
            }
        }
    }
    
    private void informarCliente() {
        String score1 = "1 " + this.j1.getScore();
        String score2 = "2 " + this.j2.getScore();
        this.agent.send(score1);
        this.agent.send(score2);
    }
    
    private int collisionLogic(Jugador j, Sprite s) {
        if(j.collision(s)) {
            if(s instanceof Pickup) {
                this.sprites.remove(s);
                return PICKUP;
            } else if(s instanceof Enemigo) {
                gameOver();
                return GAME_OVER;
            }
        }
        return GAME_OK;
    }
    
    public Modo getModo() {
        return this.modo;
    }
}
