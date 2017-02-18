/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.objetos;

import java.util.Timer;
import java.util.TimerTask;
import pacman.objetos.manejador.Juego;


/**
 *
 * @author sebastian
 */
public class Jugador extends MovableSprite {
    private final int numJugador;
    private int score;
    private volatile boolean poweredup;
    private static final int SX_1 = 1;
    private static final int SY_1 = 1;
    private static final int SX_2 = 14;
    private static final int SY_2 = 10;
    
    private Jugador(Mundo mundo, int x, int y, int num) {
        super(mundo, x, y);
        this.numJugador = num;
        this.score = 0;
        this.loadingDir = "/pacman/pacman" + this.numJugador;
        this.currSprite = MovableSprite.animDir + this.loadingDir + "-1.png";
        this.poweredup = false;
    }
    
    public void reset() {
        if(this.id() == 1) {
            this.x = SX_1;
            this.y = SY_1;
        } else if(this.id() == 2) {
            this.x = SX_2;
            this.y = SY_2;
        }
        this.canvasx = (Juego.TILESIZE) * this.x;
        this.canvasy = (Juego.TILESIZE) * this.y;
        this.indexSprite = 1;
        this.keydir = Orientacion.Abajo;
        this.movevector = Orientacion.Abajo;
        this.keypressed = false;
        this.free = true;
        this.poweredup = false;
    }
    
    public static Jugador J1(Mundo mundo) {
        return new Jugador(mundo, SX_1, SY_1, 1);
    }
    
    public static Jugador J2(Mundo mundo) {
        return new Jugador(mundo, SX_2, SY_2, 2);
    }
    
    public int id() {
        return this.numJugador;
    }
    
    public void pressKey(Orientacion orientacion) {
        this.keypressed = true;
        this.keydir = orientacion;
        this.move();
    }

    @Override
    public boolean interact(Sprite s) {
        if(s instanceof Pickup) {
            return s.interact(this);
        } else if(s instanceof Enemigo) {
            return s.interact(this);
        }
        return false;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public void updateScore(int pickupValue) {
        this.score = this.score + pickupValue;
    }
    
    public boolean getPowerUp() {
        return this.poweredup;
    }

    public void powerUp() {
        this.poweredup = true;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                poweredup = false;
            }
        }, 10*1000);
    }
}
