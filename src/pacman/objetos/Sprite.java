/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.objetos;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Queue;

/**
 *
 * @author sebastian
 */
public interface Sprite {

    public void updateSprite();
    
    void display(Graphics2D g, Queue<SerializableSprite> sendQueue);
    
    String getSprite();
    
    default boolean collision(Sprite s) {
        /*
            TRUE if object is to be erased, ELSE false
        */
        if(this.getX() == s.getX() && this.getY() == s.getY()) {
            return interact(s);
        } else {
            return false;
        }
    }
    
    boolean interact(Sprite s);
    
    int getX();
    
    int getY();
    
    SerializableSprite serialize();
}
