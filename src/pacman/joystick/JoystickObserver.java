/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.joystick;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.java.games.input.Component;
import net.java.games.input.Controller;

/**
 *
 * @author sebastian
 */
public class JoystickObserver extends Thread{
    private static final int pollingSpeed = 150;
    private final Controller controller;
    private JoystickListener listener;
    private JoystickEvent previous;
    private volatile boolean keep_polling;
    
    public JoystickObserver(Controller controller, JoystickListener listener) {
        this.controller = controller;
        this.listener = listener;
        this.start();
    }
    
    public void end() {
        this.keep_polling = false;
    }

    @Override
    public void run() {
        this.previous = null;
        this.keep_polling = true;
        while(this.keep_polling) {
            
            try {
                Thread.sleep(this.pollingSpeed);
            } catch (InterruptedException ex) {
                Logger.getLogger(JoystickObserver.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            this.controller.poll();
            if(this.controller.getComponent(Component.Identifier.Button.BASE).getPollData() == 1) {
                JoystickEvent evt = new JoystickEvent(JoystickEvent.KeyID.DOWN);
                listenerLogic(evt);
            }
            
            else if(this.controller.getComponent(Component.Identifier.Button.BASE2).getPollData() == 1) {
                JoystickEvent evt = new JoystickEvent(JoystickEvent.KeyID.LEFT);
                listenerLogic(evt);
            }
            
            else if(this.controller.getComponent(Component.Identifier.Button.PINKIE).getPollData() == 1) {
                JoystickEvent evt = new JoystickEvent(JoystickEvent.KeyID.RIGHT);
                listenerLogic(evt);
            }
            
            else if(this.controller.getComponent(Component.Identifier.Button.TOP2).getPollData() == 1) {
                JoystickEvent evt = new JoystickEvent(JoystickEvent.KeyID.UP);
                listenerLogic(evt);
            }
            
            else if(this.controller.getComponent(Component.Identifier.Button.TOP).getPollData() == 1 && this.previous == null) {
                JoystickEvent evt = new JoystickEvent(JoystickEvent.KeyID.START);
                listenerLogic(evt);
            }
            
            else if(this.previous != null) {
                listener.KeyReleased(this.previous);
                this.previous = null;
            }
            
        }
    }
    
    private void listenerLogic(JoystickEvent evt) {
        if(this.previous != null && this.previous.getID() != evt.getID()) {
            listener.KeyReleased(this.previous);
            this.previous = evt;
        } else if(this.previous == null) {
            this.previous = evt;
        }
        listener.KeyPressed(evt);
    }
}
