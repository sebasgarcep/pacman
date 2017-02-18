/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.online;

import java.io.Serializable;
import pacman.objetos.MovableSprite.Orientacion;

/**
 *
 * @author sebastian
 */
public class KeyPacket implements Serializable {
    private final Orientacion orientacion;
    
    public KeyPacket(Orientacion orientacion) {
        this.orientacion = orientacion;
    }
    
    public Orientacion getOrientacion() {
        return this.orientacion;
    }
}
