/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.interfaz.paneles;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import pacman.joystick.JoystickEvent;
import pacman.joystick.JoystickListener;
import pacman.joystick.JoystickObserver;
import pacman.objetos.Jugador;
import pacman.objetos.MovableSprite;
import pacman.objetos.MovableSprite.Orientacion;
import pacman.objetos.manejador.Graficos;
import pacman.objetos.manejador.Juego;

/**
 *
 * @author sebastian
 */
public class Contenedor extends AbstractContenedor {
    private Juego juego;
    private Graficos graficos;
    private Controller controller;
    /**
     * Creates new form Juego
     * @param ventana
     * @param juego
     */
    private Contenedor(Juego juego) {
        this.controller = null;
        if(System.getProperty("os.name").toLowerCase().equals("linux")) {
            Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
            for(Controller c : controllers) {
                System.out.println(c.getType());
                if(c.getType() == Controller.Type.STICK) {
                    this.controller = c;
                }
            }
        }
        
        this.juego = juego;
        this.graficos = new Graficos(this, juego.getModo());
        this.juego.init(this, this.graficos);
    }
    
    public static AbstractContenedor ContenedorArcade() {
        return new Contenedor(Juego.Arcade());
    }
    
    public static AbstractContenedor ContenedorMultijugador() {
        return new Contenedor(Juego.Multijugador());
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.graficos.paint(g);
    }
    
    @Override
    public void attachKeyListener(final Jugador j) {
        if(System.getProperty("os.name").toLowerCase().equals("linux") && this.controller != null && j.id() == 1) {
            joystickListenerMethod(j);
        } else {
            keyListenerMethod(j);
        }
    }

    @Override
    protected void keyListenerMethod(Jugador j) {
        this.addKeyListener(new KeyListener() {
            

            @Override
            public void keyTyped(KeyEvent ke) {
                //Not used
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                switch(j.id()) {
                    case 1:
                        switch(ke.getKeyCode()) {
                            case KeyEvent.VK_LEFT:
                                if(!juego.getPauseState()) j.pressKey(MovableSprite.Orientacion.Izquierda);
                                break;
                            case KeyEvent.VK_RIGHT:
                                if(!juego.getPauseState()) j.pressKey(MovableSprite.Orientacion.Derecha);
                                break;
                            case KeyEvent.VK_UP:
                                if(!juego.getPauseState()) j.pressKey(MovableSprite.Orientacion.Arriba);
                                break;
                            case KeyEvent.VK_DOWN:
                                if(!juego.getPauseState()) j.pressKey(MovableSprite.Orientacion.Abajo);
                                break;
                            case KeyEvent.VK_SPACE:
                                if(juego.getModo() != Juego.Modo.Online) juego.pauseGame();
                                break;
                        }
                        break;
                    case 2:
                        switch(ke.getKeyCode()) {
                            case KeyEvent.VK_A:
                                if(!juego.getPauseState()) j.pressKey(MovableSprite.Orientacion.Izquierda);
                                break;
                            case KeyEvent.VK_D:
                                if(!juego.getPauseState()) j.pressKey(MovableSprite.Orientacion.Derecha);
                                break;
                            case KeyEvent.VK_W:
                                if(!juego.getPauseState()) j.pressKey(MovableSprite.Orientacion.Arriba);
                                break;
                            case KeyEvent.VK_S:
                                if(!juego.getPauseState()) j.pressKey(MovableSprite.Orientacion.Abajo);
                                break;
                        }
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                switch(j.id()) {
                    case 1:
                        switch(ke.getKeyCode()) {
                            case KeyEvent.VK_LEFT:
                                j.releaseKey();
                                break;
                            case KeyEvent.VK_RIGHT:
                                j.releaseKey();
                                break;
                            case KeyEvent.VK_UP:
                                j.releaseKey();
                                break;
                            case KeyEvent.VK_DOWN:
                                j.releaseKey();
                                break;
                        }
                        break;
                    case 2:
                        switch(ke.getKeyCode()) {
                            case KeyEvent.VK_A:
                                j.releaseKey();
                                break;
                            case KeyEvent.VK_D:
                                j.releaseKey();
                                break;
                            case KeyEvent.VK_W:
                                j.releaseKey();
                                break;
                            case KeyEvent.VK_S:
                                j.releaseKey();
                                break;
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void joystickListenerMethod(Jugador j) {
        JoystickObserver jo = new JoystickObserver(this.controller, new JoystickListener() {

            @Override
            public void KeyPressed(JoystickEvent evt) {
                switch(evt.getID()) {
                    case LEFT:
                        if(!juego.getPauseState()) j.pressKey(MovableSprite.Orientacion.Izquierda);
                        break;
                    case RIGHT:
                        if(!juego.getPauseState()) j.pressKey(MovableSprite.Orientacion.Derecha);
                        break;
                    case UP:
                        if(!juego.getPauseState()) j.pressKey(MovableSprite.Orientacion.Arriba);
                        break;
                    case DOWN:
                        if(!juego.getPauseState()) j.pressKey(MovableSprite.Orientacion.Abajo);
                        break;
                    case START:
                        if(juego.getModo() != Juego.Modo.Online) juego.pauseGame();
                        break;
                }
            }

            @Override
            public void KeyReleased(JoystickEvent evt) {
                switch(evt.getID()) {
                    case LEFT:
                        j.releaseKey();
                        break;
                    case RIGHT:
                        j.releaseKey();
                        break;
                    case UP:
                        j.releaseKey();
                        break;
                    case DOWN:
                        j.releaseKey();
                        break;
                }
            }
        });
    }
}
