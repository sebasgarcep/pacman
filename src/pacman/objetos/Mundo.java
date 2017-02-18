/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.objetos;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Queue;
import java.util.Random;
import javax.swing.ImageIcon;
import pacman.objetos.manejador.Juego;
import pacman.objetos.MovableSprite.Orientacion;
import pacman.objetos.manejador.Graficos;

/**
 *
 * @author sebastian
 */
public class Mundo {
    private int[][] descripcion; //16 x 12
    private int nivel;
    private int[][][] smell;
    private static final int MAXSMELL = 1000;
    private static final int SMELLSTEP = 40;
    private static final int SMELLDISTANCE = 2;
    private int[][] textura;
    private static final String WALLPATH = "/pacman/recursos/terreno/pared.png";
    private static final Image WALL = new ImageIcon(Graficos.class.getResource(WALLPATH)).getImage();
    private static final String TILE1PATH = "/pacman/recursos/terreno/tile1.png";
    private static final Image TILE1 = new ImageIcon(Graficos.class.getResource(TILE1PATH)).getImage();
    private static final String TILE2PATH = "/pacman/recursos/terreno/tile2.png";
    private static final Image TILE2 = new ImageIcon(Graficos.class.getResource(TILE2PATH)).getImage();
    private static final String TILE3PATH = "/pacman/recursos/terreno/tile3.png";
    private static final Image TILE3 = new ImageIcon(Graficos.class.getResource(TILE3PATH)).getImage(); 
    
    private Mundo(int nivel) {
        this.nivel = nivel;
        this.descripcion = getDescripcion(this.nivel);
        this.smell = new int[Juego.GRIDHEIGHT][Juego.GRIDWIDTH][2];
        this.textura = generarTextura();
    }
    
    public static Mundo laberinto() {
        return new Mundo(1);
    }
    
    public static Mundo Online() {
        return new Mundo(4);
    }
    
    public synchronized void addSmell(Jugador p, int n) {
        int x = p.getX();
        int y = p.getY();
        for(int i = -SMELLDISTANCE; i <= SMELLDISTANCE; i++) {
            for(int j = -SMELLDISTANCE; j <= SMELLDISTANCE; j++) {
                try {
                    this.smell[y+j][x+i][n] = Math.max(this.smell[y+j][x+i][n], MAXSMELL/(1+Math.max(Math.abs(i), Math.abs(j))));
                } catch(IndexOutOfBoundsException ex) {}
            }
        }
    }
    
    private int[][] getDescripcion(int nivel) {
        int[][] level;
        switch(nivel) {
            case 1:
                level = 
                new int[][]
                {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1},
                    {1,0,1,0,0,0,0,0,0,0,0,0,0,1,0,1},
                    {1,0,0,0,0,0,1,1,0,1,0,0,0,0,0,1},
                    {1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1},
                    {1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1},
                    {1,0,0,0,0,0,1,0,1,1,0,0,0,0,0,1},
                    {1,0,1,0,0,0,0,0,0,0,0,0,0,1,0,1},
                    {1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                };
                break;
            case 2:
                level = 
                new int[][]
                {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,1,0,1,1,1,1,1,1,0,1,0,0,1},
                    {1,0,1,1,0,0,0,0,0,0,0,0,1,1,0,1},
                    {1,0,0,0,0,0,1,1,0,1,0,0,0,0,0,1},
                    {1,0,0,1,1,0,1,0,0,1,0,1,1,0,0,1},
                    {1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1},
                    {1,0,1,1,0,1,1,0,1,1,1,0,1,1,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,1,1,1,1,1,0,0,1,1,1,1,1,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                };
                break;
            case 3:
                level = 
                new int[][]
                {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,1,0,1,0,0,0,0,0,1},
                    {1,0,1,1,0,1,0,1,0,1,0,0,1,1,0,1},
                    {1,0,1,0,0,0,0,0,0,0,0,0,0,1,0,1},
                    {1,0,0,0,0,0,1,1,0,1,0,0,0,0,0,1},
                    {1,0,0,1,0,1,1,0,0,1,1,0,1,0,0,1},
                    {1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1},
                    {1,0,1,1,1,0,1,0,1,1,0,1,1,1,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,1,1,0,1,1,0,0,1,1,0,1,1,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                };
                break;
            default:
                level = 
                new int[][]
                {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,1,0,1,1,1,1,1,1,0,1,0,0,1},
                    {1,0,1,1,0,0,0,0,0,0,0,0,1,1,0,1},
                    {1,0,0,0,0,0,1,1,0,1,0,0,0,0,0,1},
                    {1,0,0,1,1,0,1,0,0,1,0,1,1,0,0,1},
                    {1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1},
                    {1,0,1,1,0,1,1,0,1,1,1,0,1,1,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,1,1,1,1,1,0,0,1,1,1,1,1,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                };
                break;
        }
        return level;
    }
    
    public int next() {
        this.nivel = this.nivel + 1;
        this.descripcion = getDescripcion(this.nivel);
        return this.nivel;
    }
    
    public synchronized void reduceSmell() {
        for(int n = 0; n < 2; n++) {
            for(int i = 0; i < Juego.GRIDWIDTH; i++) {
                for(int j = 0; j < Juego.GRIDHEIGHT; j++) {
                    this.smell[j][i][n] = Math.max(0, this.smell[j][i][n] - SMELLSTEP);
                }
            }
        }
    }
    
    public boolean isWall(int x, int y) {
        if(x >= 0 && x < this.descripcion[0].length && y >= 0 && y < this.descripcion.length) {
            switch(this.descripcion[y][x]) {
                case 0:
                    return false;
                case 1:
                    return true;
                default:
                    return true;
            }
        } else {
            return true;
        }
    }

    public boolean isNextWall(int x, int y, Orientacion dir) {
        switch(dir) {
            case Arriba:
                return isWall(x, y-1);
            case Abajo:
                return isWall(x, y+1);
            case Izquierda:
                return isWall(x-1, y);
            case Derecha:
                return isWall(x+1, y);
        }
        return true;
    }

    public int getSmell(int x, int y, Orientacion orientacion, int n) {
        switch(orientacion) {
            case Abajo:
                return getSmell(x, y+1, n);
            case Arriba:
                return getSmell(x, y-1, n);
            case Izquierda:
                return getSmell(x-1, y, n);
            case Derecha:
                return getSmell(x+1, y, n);
        }
        return getSmell(x, y, n);
    }
    
    public int getSmell(int x, int y, int n) {
        try {
            return this.smell[y][x][n];
        } catch(IndexOutOfBoundsException ex) {
            return 0;
        }
    }
    
    private int[][] generarTextura() {
        Random rand = new Random();
        int[][] array = new int[Juego.GRIDHEIGHT][Juego.GRIDWIDTH];
        for(int i = 0; i < Juego.GRIDWIDTH; i++) {
            for(int j = 0; j < Juego.GRIDHEIGHT; j++) {
                array[j][i] = rand.nextInt(3);
            }
        }
        return array;
    }
    
    public void display(Graphics2D g, Queue<SerializableSprite> sendQueue) {
        SerializableSprite sprite;
        for(int i = 0; i < Juego.GRIDWIDTH; i++) {
            for(int j = 0; j < Juego.GRIDHEIGHT; j++) {
                switch(getTextura(i, j)) {
                    case 0:
                        g.drawImage(TILE1, Juego.TILESIZE*i, Juego.TILESIZE*j, null);
                        sprite = new SerializableSprite(TILE1PATH, Juego.TILESIZE*i, Juego.TILESIZE*j);
                        sendQueue.add(sprite);
                        break;
                    case 1:
                        g.drawImage(TILE2, Juego.TILESIZE*i, Juego.TILESIZE*j, null);
                        sprite = new SerializableSprite(TILE2PATH, Juego.TILESIZE*i, Juego.TILESIZE*j);
                        sendQueue.add(sprite);
                        break;
                    case 2:
                        g.drawImage(TILE3, Juego.TILESIZE*i, Juego.TILESIZE*j, null);
                        sprite = new SerializableSprite(TILE3PATH, Juego.TILESIZE*i, Juego.TILESIZE*j);
                        sendQueue.add(sprite);
                        break;
                }
                if(this.isWall(i, j)) {
                    g.drawImage(WALL, Juego.TILESIZE*i, Juego.TILESIZE*j, null);
                    sprite = new SerializableSprite(WALLPATH, Juego.TILESIZE*i, Juego.TILESIZE*j);
                    sendQueue.add(sprite);
                }
            }
        }
    }
    
    private int getTextura(int x, int y) {
        return this.textura[y][x];
    }
}
