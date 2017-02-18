/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.objetos;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Queue;
import javax.swing.ImageIcon;
import pacman.objetos.manejador.Audio;
import pacman.objetos.manejador.Juego;

/**
 *
 * @author sebastian
 */
public abstract class MovableSprite implements Sprite {
    protected int x;
    protected int y;
    protected int canvasx;
    protected int canvasy;
    protected Orientacion keydir;
    protected Orientacion movevector;
    protected boolean keypressed;
    protected boolean free;
    protected Mundo mundo;
    protected int indexSprite;
    protected String currSprite;
    protected String loadingDir;
    private static final int MOVESPEED = 2;
    public static final String animDir = "/pacman/recursos/animaciones";
    
    protected MovableSprite(Mundo mundo, int x, int y) {
        this.mundo = mundo;
        this.x = x;
        this.y = y;
        this.canvasx = (Juego.TILESIZE) * this.x;
        this.canvasy = (Juego.TILESIZE) * this.y;
        this.indexSprite = 1;
        this.keydir = Orientacion.Abajo;
        this.movevector = Orientacion.Abajo;
        this.keypressed = false;
        this.free = true;
    }
    
    public enum Orientacion {
        Izquierda,
        Derecha,
        Arriba,
        Abajo,
    }
    
    public void move() {
        if(this.free) {
            this.movevector = this.keydir;
            switch(this.keydir) {
                case Izquierda:
                    if(!this.mundo.isWall(this.x - 1, this.y)) {
                        this.x = this.x - 1;
                        this.free = false;
                    } else {
                        if(this instanceof Jugador) Audio.bounce();
                        return;
                    }
                    break;
                case Derecha:
                    if(!this.mundo.isWall(this.x + 1, this.y)) {
                        this.x = this.x + 1;
                        this.free = false;
                    } else {
                        if(this instanceof Jugador) Audio.bounce();
                        return;
                    }
                    break;
                case Arriba:
                    if(!this.mundo.isWall(this.x, this.y - 1)) {
                        this.y = this.y - 1;
                        this.free = false;
                    } else {
                        if(this instanceof Jugador) Audio.bounce();
                        return;
                    }
                    break;
                case Abajo:
                    if(!this.mundo.isWall(this.x, this.y + 1)) {
                        this.y = this.y + 1;
                        this.free = false;
                    } else {
                        if(this instanceof Jugador) Audio.bounce();
                        return;
                    }
                    break;
            }
            updateSprite();
        }
    }
    
    @Override
    public void display(Graphics2D g, Queue<SerializableSprite> sendQueue) {
        Image display = new ImageIcon(getClass().getResource(getSprite())).getImage();
        g.drawImage(display, this.canvasx, this.canvasy, null);
        
        sendQueue.add(serialize());
        
        if(!this.free) {
            switch(this.movevector) {
                case Izquierda:
                    this.canvasx = this.canvasx - MOVESPEED;
                    break;
                case Derecha:
                    this.canvasx = this.canvasx + MOVESPEED;
                    break;
                case Arriba:
                    this.canvasy = this.canvasy - MOVESPEED;
                    break;
                case Abajo:
                    this.canvasy = this.canvasy + MOVESPEED;
                    break;
            }
            
            if(this.canvasx == (Juego.TILESIZE) * this.x && this.canvasy == (Juego.TILESIZE) * this.y) {
                this.free = true;
                if(this.keypressed) {
                    this.move();
                }
            }
        }
    }
    
    public void releaseKey() {
        this.keypressed = false;
    }
    
    @Override
    public int getX() {
        return this.x;
    }
    
    @Override
    public int getY() {
        return this.y;
    }
    
    public int getCanvasX() {
        return this.canvasx;
    }
    
    @Override
    public String getSprite() {
        return this.currSprite;
    }
    
    @Override
    public void updateSprite() {
        switch(this.movevector) {
            case Abajo:
                switch(this.indexSprite) {
                    case 1:
                        this.indexSprite = 2;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-2.png";
                        break;
                    case 2:
                        this.indexSprite = 3;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-3.png";
                        break;
                    case 3:
                        this.indexSprite = 4;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-2.png";
                        break;
                    case 4:
                        this.indexSprite = 1;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-1.png";
                        break;
                }
                break;
            case Arriba:
                switch(this.indexSprite) {
                    case 1:
                        this.indexSprite = 2;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-11.png";
                        break;
                    case 2:
                        this.indexSprite = 3;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-12.png";
                        break;
                    case 3:
                        this.indexSprite = 4;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-11.png";
                        break;
                    case 4:
                        this.indexSprite = 1;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-10.png";
                        break;
                }
                break;
            case Izquierda:
                switch(this.indexSprite) {
                    case 1:
                        this.indexSprite = 2;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-8.png";
                        break;
                    case 2:
                        this.indexSprite = 3;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-9.png";
                        break;
                    case 3:
                        this.indexSprite = 4;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-8.png";
                        break;
                    case 4:
                        this.indexSprite = 1;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-7.png";
                        break;
                }
                break;
            case Derecha:
                switch(this.indexSprite) {
                    case 1:
                        this.indexSprite = 2;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-5.png";
                        break;
                    case 2:
                        this.indexSprite = 3;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-6.png";
                        break;
                    case 3:
                        this.indexSprite = 4;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-5.png";
                        break;
                    case 4:
                        this.indexSprite = 1;
                        this.currSprite = MovableSprite.animDir + this.loadingDir + "-4.png";
                        break;
                }
                break;
        }
    }
        
    @Override
    public SerializableSprite serialize() {
        return new SerializableSprite(this.currSprite, this.canvasx, this.canvasy);
    }
        
}
