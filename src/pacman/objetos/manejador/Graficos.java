/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.objetos.manejador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import pacman.interfaz.paneles.AbstractContenedor;
import pacman.objetos.Jugador;
import pacman.objetos.Mundo;
import pacman.objetos.SerializableSprite;
import pacman.objetos.Sprite;
import pacman.objetos.manejador.Juego.Modo;
import pacman.online.Agent;

/**
 *
 * @author sebastian
 */
public class Graficos extends Thread {
    private Modo modo;
    private AbstractContenedor canvas;
    private List<Sprite> sprites;
    private long startingTime;
    private Mundo mundo;
    private Agent server;
    private volatile boolean game_continue;
    
    public Graficos(AbstractContenedor canvas, Modo modo) {
        this.modo = modo;
        this.sprites = null;
        this.mundo = null;
        this.startingTime = System.currentTimeMillis();
        this.canvas = canvas;
        this.canvas.setIgnoreRepaint(true);
        this.canvas.setDoubleBuffered(true);
        this.setName("Game Graphics");
    }
    
    public void init(List<Sprite> spritelist, Mundo mundo, Agent server) {
        this.sprites = spritelist;
        this.mundo = mundo;
        this.server = server;
        
        this.start();
    }
    
    @Override
    public void run() {
        int i = 0;
        this.game_continue = true;
        while(this.game_continue) {
            canvas.repaint();
            i++;
            try {
                Thread.sleep(20);
                if(i == 12) {
                    for(Sprite s : this.sprites) {
                        s.updateSprite();
                        i = 0;
                    }
                }
            } catch (InterruptedException ex) {}
        }
    }
    
    public void stopGraphics() {
        this.game_continue = false;
    }

    public void paint(Graphics g) {
        Queue<SerializableSprite> sendQueue = new LinkedList<>();
        this.mundo.display((Graphics2D) g, sendQueue);
        for(Sprite s : this.sprites){
            s.display((Graphics2D) g, sendQueue);
            if(s instanceof Jugador && ((Jugador)s).id() == 1) {
                g.setColor(Color.black);
                g.drawRect(10, 10, 180, 20);
                g.setColor(Color.white);
                g.fillRect(10, 10, 180, 20);
                g.setColor(Color.black);
                g.setFont(Font.getFont(Font.SANS_SERIF));
                g.drawString("Puntuacion: " + ((Jugador)s).getScore(), 15, 25);
            }
            if(this.modo != Modo.Arcade && s instanceof Jugador && ((Jugador)s).id() == 2) {
                g.setColor(Color.black);
                g.drawRect(450, 10, 180, 20);
                g.setColor(Color.white);
                g.fillRect(450, 10, 180, 20);
                g.setColor(Color.black);
                g.setFont(Font.getFont(Font.SANS_SERIF));
                g.drawString("Puntuacion: " + ((Jugador)s).getScore(), 465, 25);
            }
        }
        if(this.server != null) {
            this.server.send(sendQueue);
        }
    }
}
