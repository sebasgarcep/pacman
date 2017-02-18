/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.objetos;

import java.util.List;
import java.util.Random;
import pacman.objetos.manejador.Audio;

/**
 *
 * @author sebastian
 */
public class Enemigo extends MovableSprite {
    private Jugador follow;
    
    public static void Zombie(Mundo mundo, List<Sprite> list) {
        list.add(new Enemigo(mundo, 7, 5));
        list.add(new Enemigo(mundo, 7, 6));
        list.add(new Enemigo(mundo, 8, 5));
        list.add(new Enemigo(mundo, 8, 6));
    }
    
    private Enemigo(Mundo mundo, int x, int y) {
        super(mundo, x, y);
        this.follow = null;
        this.loadingDir = "/zombi/zombi1";
        this.currSprite = MovableSprite.animDir + this.loadingDir + "-1.png";
    }
    
    private Enemigo(Mundo mundo, int x, int y, Jugador follow) {
        super(mundo, x, y);
        this.follow = follow;
        this.loadingDir = "/zombi/zombi" + this.follow.id();
        this.currSprite = MovableSprite.animDir + this.loadingDir + "-1.png";
    }
    
    public static void addZombieOnline(Mundo mundo, List<Sprite> list, Jugador jugador) {
        Random r = new Random();
        switch(r.nextInt(4)) {
            case 0:
                list.add(new Enemigo(mundo, 7, 5, jugador));
                break;
            case 1:
                list.add(new Enemigo(mundo, 7, 6, jugador));
                break;
            case 2:
                list.add(new Enemigo(mundo, 8, 5, jugador));
                break;
            case 3: 
                list.add(new Enemigo(mundo, 8, 6, jugador));
                break;
        }
    }
    
    public void makeChoice() {
        if(this.follow == null) {
            smell();
        } else {
            track();
        }
        this.keypressed = true;
        move();
        this.keypressed = false;
    }
    
    private void track() {
        Orientacion[] array = Orientacion.values();
        boolean sw = true;
        int smell = 0;
        for(Orientacion dir : array) {
            if(!this.mundo.isNextWall(this.x, this.y, dir) &&
                    smell < this.mundo.getSmell(this.x, this.y, dir, this.follow.id() - 1)) {
                smell = this.mundo.getSmell(this.x, this.y, dir, this.follow.id() - 1);
                this.keydir = dir;
                sw = false;
            }
        }
        if(sw) {
            int index = (new Random()).nextInt(array.length);
            this.keydir = array[index];
        }
    }
    
    private void smell() {
        Orientacion[] array = Orientacion.values();
        boolean sw = true;
        int smell = 0;
        for(Orientacion dir : array) {
            if(!this.mundo.isNextWall(this.x, this.y, dir) &&
                    smell < this.mundo.getSmell(this.x, this.y, dir, 0)) {
                smell = this.mundo.getSmell(this.x, this.y, dir, 0);
                this.keydir = dir;
                sw = false;
            }
        }
        if(sw) {
            int index = (new Random()).nextInt(array.length);
            this.keydir = array[index];
        }
    }

    @Override
    public boolean interact(Sprite s) {
        if(s instanceof Jugador) {
            Jugador j = (Jugador)s;
            if(this.follow == null && !j.getPowerUp()) {
                Audio.growl();
                return true;
            } else if(this.follow == j && !j.getPowerUp()) {
                Audio.growl();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
}
