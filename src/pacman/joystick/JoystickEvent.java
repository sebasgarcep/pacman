/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.joystick;

/**
 *
 * @author sebastian
 */
public class JoystickEvent {
    private KeyID id;
    public enum KeyID {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        START
    }
    
    public JoystickEvent(KeyID id) {
        this.id = id;
    }
    
    public KeyID getID() {
        return this.id;
    }
}
