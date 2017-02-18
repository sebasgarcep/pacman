/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.objetos.manejador;

import jaco.mp3.player.MP3Player;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author sebastian
 */
public class Audio {
    private static final ExecutorService audioService = serviceFactory();
    private static MP3Player audioPlayer = null;
    private static MP3Player effectPlayer = null;
    private static ExecutorService serviceFactory() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        return service;
    }
    
    public static void tocarTemaPrincipal() {
        if(audioPlayer == null) {
            Runnable run = () -> {
                audioPlayer = new MP3Player(Audio.class.getResource("/pacman/recursos/theme_song.mp3"));
                audioPlayer.play();
            };
            audioService.submit(run);
        }
    }
    
    public static void pararTema() {
        if(audioPlayer != null) {
            audioPlayer.stop();
            audioPlayer = null;
        }
    }
    
    public static void bounce() {
        Runnable run = () -> {
            effectPlayer = new MP3Player(Audio.class.getResource("/pacman/recursos/bump.mp3"));
            effectPlayer.play();
        };
        audioService.submit(run);
    }
    
    public static void growl() {
        Runnable run = () -> {
            effectPlayer = new MP3Player(Audio.class.getResource("/pacman/recursos/growl.mp3"));
            effectPlayer.play();
        };
        audioService.submit(run);
    }
}
