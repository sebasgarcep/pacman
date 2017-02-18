/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.test;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author sebastian
 */
public class ImageTest implements Serializable {
    public String image;
    public int x;
    public int y;
    
    public ImageTest(String image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }
    
    public void changeImage(ImageTest i) {
        this.image = i.image;
        this.x = i.x;
        this.y = i.y;
    }
    
    public ImageIcon toImage() {
        return new ImageIcon(getClass().getResource(this.image));
    }
}
