/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.objetos;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import java.util.Queue;
import javax.swing.ImageIcon;
import pacman.objetos.manejador.Juego;

/**
 *
 * @author sebastian
 */
public class Pickup implements Sprite {
    public Type type;
    public String sprite;
    private final int x;
    private final int y;
    private final int canvasx;
    private final int canvasy;
    private static final int PICKUP_SCORE = 100;
    private static final int POWERUP_SCORE = 50;

    @Override
    public SerializableSprite serialize() {
        return new SerializableSprite(this.sprite, this.canvasx, this.canvasy);
    }
    public enum Type {
        Comida,
        Powerup
    }
    
    @Override
    public void updateSprite() {
    }

    @Override
    public boolean interact(Sprite s) {
        if(s instanceof Jugador) {
            Jugador j = (Jugador)s;
            switch(this.type) {
                case Comida:
                    j.updateScore(PICKUP_SCORE);
                    return true;
                case Powerup:
                    j.powerUp();
                    return true;
            }
        }
        return false;
    }
    
    public static void laberinto(List<Sprite> list, int nivel) {
        switch(nivel) {
            case 1:
                add(list, 2, 2, Type.Powerup);
                add(list, 2, 9, Type.Comida);
                add(list, 13, 2, Type.Comida);
                add(list, 13, 9, Type.Powerup);
                break;
            case 2:
                add(list,1, 2, Type.Comida);
                add(list,1, 3, Type.Comida);
                add(list,1, 4, Type.Comida);
                add(list,1, 5, Type.Comida);
                add(list,1, 6, Type.Comida);
                add(list,1, 7, Type.Comida);
                add(list,1, 9, Type.Comida);
                add(list,2, 1, Type.Comida);
                add(list,2, 2, Type.Comida);
                add(list,2, 10, Type.Comida);
                add(list,3, 1, Type.Comida);
                add(list,3, 4, Type.Comida);
                add(list,3, 8, Type.Comida);
                add(list,3, 10, Type.Comida);
                add(list,4, 1, Type.Comida);
                add(list,4, 4, Type.Comida);
                add(list,4, 6, Type.Comida);
                add(list,4, 7, Type.Comida);
                add(list,4, 8, Type.Comida);
                add(list,4, 10, Type.Comida);
                add(list,5, 3, Type.Comida);
                add(list,5, 4, Type.Comida);
                add(list,5, 5, Type.Comida);
                add(list,5, 6, Type.Comida);
                add(list,5, 8, Type.Comida);
                add(list,5, 10, Type.Comida);
                add(list,6, 3, Type.Powerup);
                add(list,6, 8, Type.Comida);
                add(list,6, 10, Type.Comida);
                add(list,7, 1, Type.Comida);
                add(list,7, 3, Type.Comida);
                add(list,7, 7, Type.Comida);
                add(list,7, 10, Type.Comida);
                add(list,8, 1, Type.Comida);
                add(list,8, 3, Type.Comida);
                add(list,8, 8, Type.Comida);
                add(list,8, 9, Type.Comida);
                add(list,8, 10, Type.Comida);
                add(list,9, 1, Type.Comida);
                add(list,10, 3, Type.Comida);
                add(list,10, 5, Type.Comida);
                add(list,10, 8, Type.Powerup);
                add(list,10, 10, Type.Comida);
                add(list,11, 1, Type.Comida);
                add(list,11, 2, Type.Comida);
                add(list,11, 6, Type.Comida);
                add(list,11, 8, Type.Comida);
                add(list,11, 10, Type.Comida);
                add(list,12, 1, Type.Comida);
                add(list,12, 4, Type.Comida);
                add(list,13, 1, Type.Comida);
                add(list,13, 2, Type.Comida);
                add(list,13, 5, Type.Comida);
                add(list,13, 6, Type.Comida);
                add(list,13, 8, Type.Comida);
                add(list,13, 10, Type.Comida);
                add(list,14, 1, Type.Comida);
                add(list,14, 4, Type.Comida);
                add(list,14, 5, Type.Comida);
                add(list,14, 6, Type.Comida);
                add(list,14, 7, Type.Comida);
                add(list,14, 8, Type.Comida);
                add(list,14, 9, Type.Comida);
                break;
            case 3:
                add(list,1, 2, Type.Comida);
                add(list,1, 3, Type.Comida);
                add(list,1, 4, Type.Comida);
                add(list,1, 5, Type.Comida);
                add(list,1, 6, Type.Comida);
                add(list,1, 7, Type.Comida);
                add(list,1, 8, Type.Comida);
                add(list,1, 9, Type.Comida);
                add(list,1, 10, Type.Comida);
                add(list,2, 1, Type.Comida);
                add(list,2, 4, Type.Comida);
                add(list,2, 5, Type.Comida);
                add(list,2, 6, Type.Comida);
                add(list,2, 8, Type.Comida);
                add(list,2, 10, Type.Comida);
                add(list,3, 1, Type.Comida);
                add(list,3, 3, Type.Powerup);
                add(list,3, 4, Type.Comida);
                add(list,3, 8, Type.Comida);
                add(list,3, 10, Type.Powerup);
                add(list,4, 1, Type.Comida);
                add(list,4, 2, Type.Comida);
                add(list,4, 3, Type.Comida);
                add(list,4, 4, Type.Comida);
                add(list,4, 5, Type.Comida);
                add(list,4, 6, Type.Comida);
                add(list,4, 8, Type.Comida);
                add(list,4, 9, Type.Comida);
                add(list,4, 10, Type.Comida);
                add(list,5, 1, Type.Comida);
                add(list,5, 3, Type.Comida);
                add(list,5, 4, Type.Comida);
                add(list,5, 6, Type.Comida);
                add(list,5, 7, Type.Comida);
                add(list,5, 8, Type.Comida);
                add(list,5, 10, Type.Comida);
                add(list,6, 1, Type.Comida);
                add(list,6, 2, Type.Comida);
                add(list,6, 3, Type.Comida);
                add(list,6, 8, Type.Comida);
                add(list,6, 10, Type.Comida);
                add(list,7, 3, Type.Comida);
                add(list,7, 7, Type.Comida);
                add(list,7, 8, Type.Comida);
                add(list,7, 9, Type.Comida);
                add(list,7, 10, Type.Comida);
                add(list,8, 1, Type.Comida);
                add(list,8, 2, Type.Comida);
                add(list,8, 3, Type.Comida);
                add(list,8, 4, Type.Comida);
                add(list,8, 8, Type.Comida);
                add(list,8, 9, Type.Comida);
                add(list,8, 10, Type.Comida);
                add(list,9, 3, Type.Comida);
                add(list,9, 8, Type.Comida);
                add(list,9, 10, Type.Comida);
                add(list,10, 1, Type.Comida);
                add(list,10, 2, Type.Comida);
                add(list,10, 3, Type.Powerup);
                add(list,10, 4, Type.Comida);
                add(list,10, 6, Type.Comida);
                add(list,10, 7, Type.Comida);
                add(list,10, 8, Type.Comida);
                add(list,10, 10, Type.Powerup);
                add(list,11, 1, Type.Comida);
                add(list,11, 2, Type.Comida);
                add(list,11, 3, Type.Comida);
                add(list,11, 4, Type.Comida);
                add(list,11, 5, Type.Comida);
                add(list,11, 6, Type.Comida);
                add(list,11, 8, Type.Comida);
                add(list,11, 9, Type.Comida);
                add(list,11, 10, Type.Comida);
                add(list,12, 1, Type.Comida);
                add(list,12, 3, Type.Comida);
                add(list,12, 4, Type.Comida);
                add(list,12, 8, Type.Comida);
                add(list,12, 10, Type.Comida);
                add(list,13, 1, Type.Comida);
                add(list,13, 4, Type.Comida);
                add(list,13, 5, Type.Comida);
                add(list,13, 6, Type.Comida);
                add(list,13, 8, Type.Comida);
                add(list,13, 10, Type.Comida);
                add(list,14, 1, Type.Comida);
                add(list,14, 2, Type.Comida);
                add(list,14, 3, Type.Comida);
                add(list,14, 4, Type.Comida);
                add(list,14, 5, Type.Comida);
                add(list,14, 6, Type.Comida);
                add(list,14, 7, Type.Comida);
                add(list,14, 8, Type.Comida);
                add(list,14, 9, Type.Comida);
                break;
            default:
                add(list,1, 2, Type.Comida);
                add(list,1, 3, Type.Comida);
                add(list,1, 4, Type.Comida);
                add(list,1, 5, Type.Comida);
                add(list,1, 6, Type.Comida);
                add(list,1, 7, Type.Comida);
                add(list,1, 8, Type.Comida);
                add(list,1, 9, Type.Comida);
                add(list,1, 10, Type.Comida);
                add(list,2, 1, Type.Comida);
                add(list,2, 2, Type.Comida);
                add(list,2, 4, Type.Comida);
                add(list,2, 5, Type.Comida);
                add(list,2, 6, Type.Comida);
                add(list,2, 8, Type.Comida);
                add(list,2, 10, Type.Comida);
                add(list,3, 1, Type.Comida);
                add(list,3, 4, Type.Comida);
                add(list,3, 8, Type.Comida);
                add(list,3, 10, Type.Comida);
                add(list,4, 1, Type.Comida);
                add(list,4, 2, Type.Comida);
                add(list,4, 3, Type.Comida);
                add(list,4, 4, Type.Powerup);
                add(list,4, 6, Type.Comida);
                add(list,4, 7, Type.Comida);
                add(list,4, 8, Type.Comida);
                add(list,4, 10, Type.Comida);
                add(list,5, 1, Type.Comida);
                add(list,5, 3, Type.Comida);
                add(list,5, 4, Type.Comida);
                add(list,5, 5, Type.Comida);
                add(list,5, 6, Type.Comida);
                add(list,5, 8, Type.Comida);
                add(list,5, 10, Type.Comida);
                add(list,6, 1, Type.Comida);
                add(list,6, 3, Type.Comida);
                add(list,6, 8, Type.Comida);
                add(list,6, 10, Type.Comida);
                add(list,7, 1, Type.Comida);
                add(list,7, 3, Type.Comida);
                add(list,7, 7, Type.Comida);
                add(list,7, 8, Type.Comida);
                add(list,7, 9, Type.Comida);
                add(list,7, 10, Type.Comida);
                add(list,8, 1, Type.Comida);
                add(list,8, 3, Type.Comida);
                add(list,8, 4, Type.Comida);
                add(list,8, 8, Type.Comida);
                add(list,8, 9, Type.Comida);
                add(list,8, 10, Type.Comida);
                add(list,9, 1, Type.Comida);
                add(list,9, 3, Type.Comida);
                add(list,9, 8, Type.Comida);
                add(list,9, 10, Type.Comida);
                add(list,10, 1, Type.Comida);
                add(list,10, 3, Type.Comida);
                add(list,10, 4, Type.Comida);
                add(list,10, 5, Type.Comida);
                add(list,10, 6, Type.Comida);
                add(list,10, 8, Type.Comida);
                add(list,10, 10, Type.Comida);
                add(list,11, 1, Type.Comida);
                add(list,11, 2, Type.Comida);
                add(list,11, 3, Type.Comida);
                add(list,11, 4, Type.Comida);
                add(list,11, 6, Type.Comida);
                add(list,11, 7, Type.Comida);
                add(list,11, 8, Type.Comida);
                add(list,11, 10, Type.Comida);
                add(list,12, 1, Type.Comida);
                add(list,12, 4, Type.Comida);
                add(list,12, 8, Type.Powerup);
                add(list,12, 10, Type.Comida);
                add(list,13, 1, Type.Comida);
                add(list,13, 2, Type.Comida);
                add(list,13, 4, Type.Comida);
                add(list,13, 5, Type.Comida);
                add(list,13, 6, Type.Comida);
                add(list,13, 8, Type.Comida);
                add(list,13, 10, Type.Comida);
                add(list,14, 1, Type.Comida);
                add(list,14, 2, Type.Comida);
                add(list,14, 3, Type.Comida);
                add(list,14, 4, Type.Comida);
                add(list,14, 5, Type.Comida);
                add(list,14, 6, Type.Comida);
                add(list,14, 7, Type.Comida);
                add(list,14, 8, Type.Comida);
                add(list,14, 9, Type.Comida);
                break;
        }
    }
    
    public Pickup(int x, int y, Type type) {
        this.x = x;
        this.y = y;
        this.canvasx = Juego.TILESIZE * this.x;
        this.canvasy = Juego.TILESIZE * this.y;
        this.type = type;
        switch(this.type) {
            case Comida:
                this.sprite = "/pacman/recursos/animaciones/pickup/pizza.png";
                break;
            case Powerup:
                this.sprite = "/pacman/recursos/animaciones/pickup/mushroom.png";
                break;
        }
    }
    
    private static void add(List<Sprite> list, int x, int y, Type type) {
        list.add(new Pickup(x, y, type));
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }
    
    @Override
    public void display(Graphics2D g, Queue<SerializableSprite> sendQueue) {
        Image display = new ImageIcon(getClass().getResource(getSprite())).getImage();
        g.drawImage(display, this.canvasx, this.canvasy, null);
        sendQueue.add(serialize());
    }
    
    
    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }
    
}
