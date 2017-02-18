/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.objetos;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author sebastian
 */
public class SerializableSprite implements Serializable {
    private String currSprite;
    private int canvasx;
    private int canvasy;
    
    public SerializableSprite(String currSprite, int canvasx, int canvasy) {
        this.currSprite = currSprite;
        this.canvasx = canvasx;
        this.canvasy = canvasy;
    }
    
    public void display(Graphics2D g) {
        Image display = new ImageIcon(getClass().getResource(this.currSprite)).getImage();
        g.drawImage(display, this.canvasx, this.canvasy, null);
    }
}
