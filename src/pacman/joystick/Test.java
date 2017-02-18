/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.joystick;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 *
 * @author sebastian
 */
public class Test {
    private static Controller controller;
    
    public static void main(String[] args) {
        controller = null;
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        for(Controller c : controllers) {
            System.out.println(c.getType());
            if(c.getType() == Controller.Type.STICK) {
                controller = c;
            }
        }
        
        while(true) {
            controller.poll();
            for(int i = 0; i < controller.getComponents().length; i++) {
                if(controller.getComponents()[i].getPollData() != 0) {
                    System.out.println(controller.getComponents()[i].getIdentifier());
                }
            }
        }
    }
}
