/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.interfaz.paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Queue;
import pacman.objetos.MovableSprite.Orientacion;
import pacman.objetos.SerializableSprite;
import pacman.objetos.manejador.Juego;
import pacman.online.Client;
import pacman.online.KeyPacket;

/**
 *
 * @author sebastian
 */
public class ContenedorClient extends GamePanel {
    private final Client agent;
    private Queue<SerializableSprite> paintList;
    private int score1;
    private int score2;
    
    public static ContenedorClient getPanel(Client agent) {
        return new ContenedorClient(agent);
    }
    
    private ContenedorClient(Client agent) {
        this.setSize(Juego.CANVASWIDTH, Juego.CANVASHEIGHT);
        this.agent = agent;
        this.paintList = null;
        this.score1 = 0;
        this.score2 = 0;
        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                 
            }

            @Override
            public void keyPressed(KeyEvent e) {
                KeyPacket p;
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        p = new KeyPacket(Orientacion.Izquierda);
                        break;
                    case KeyEvent.VK_RIGHT:
                        p = new KeyPacket(Orientacion.Derecha);
                        break;
                    case KeyEvent.VK_UP:
                        p = new KeyPacket(Orientacion.Arriba);
                        break;
                    case KeyEvent.VK_DOWN:
                        p = new KeyPacket(Orientacion.Abajo);
                        break;
                    default:
                        p = null;
                        break;
                }
                if(p != null) {
                    agent.send(p);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                KeyPacket p;
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        p = new KeyPacket(null);
                        break;
                    case KeyEvent.VK_RIGHT:
                        p = new KeyPacket(null);
                        break;
                    case KeyEvent.VK_UP:
                        p = new KeyPacket(null);
                        break;
                    case KeyEvent.VK_DOWN:
                        p = new KeyPacket(null);
                        break;
                    default:
                        p = null;
                        break;
                }
                if(p != null) {
                    agent.send(p);
                }
            }
        });
    }

    public void display(Queue<SerializableSprite> i) {
        this.paintList = i;
        this.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.paintList != null) {
            for(SerializableSprite sprite : this.paintList) {
                sprite.display((Graphics2D) g);
            }
        }
        g.setColor(Color.black);
        g.drawRect(10, 10, 180, 20);
        g.setColor(Color.white);
        g.fillRect(10, 10, 180, 20);
        g.setColor(Color.black);
        g.setFont(Font.getFont(Font.SANS_SERIF));
        g.drawString("Puntuacion: " + score1, 15, 25);
        g.setColor(Color.black);
        g.drawRect(450, 10, 180, 20);
        g.setColor(Color.white);
        g.fillRect(450, 10, 180, 20);
        g.setColor(Color.black);
        g.setFont(Font.getFont(Font.SANS_SERIF));
        g.drawString("Puntuacion: " + score2, 465, 25);
    }
    
    public int getScore(int i) {
        if(i == 1) {
            return this.score1;
        } else {
            return this.score2;
        }
    }
    
    public void setScore(int i, int score) {
        if(i == 1) {
            this.score1 = score;
        } else if(i == 2){
            this.score2 = score;
        }
    }
}
