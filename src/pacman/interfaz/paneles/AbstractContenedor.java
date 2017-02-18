/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.interfaz.paneles;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import pacman.joystick.JoystickEvent;
import pacman.joystick.JoystickListener;
import pacman.joystick.JoystickObserver;
import pacman.objetos.Jugador;
import pacman.objetos.MovableSprite;
import pacman.objetos.manejador.Audio;
import pacman.objetos.manejador.Juego;
import pacman.objetos.manejador.Juego.Modo;

/**
 *
 * @author sebastian
 */
public abstract class AbstractContenedor extends GamePanel {
    protected Juego juego;
    protected Controller controller;
    
    public AbstractContenedor() {
        Audio.pararTema();
        this.controller = null;
        if(System.getProperty("os.name").toLowerCase().equals("linux")) {
            Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
            for(Controller c : controllers) {
                System.out.println(c.getType());
                if(c.getType() == Controller.Type.STICK) {
                    this.controller = c;
                }
            }
        }
    }
    
    public abstract void attachKeyListener(Jugador j);
    
    protected abstract void keyListenerMethod(Jugador j);
    
    protected abstract void joystickListenerMethod(Jugador j);
}
