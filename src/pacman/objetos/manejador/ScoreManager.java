/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.objetos.manejador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sebastian
 */
public class ScoreManager {
    private ScoreManager() {}
    
    public static void registrar(String j, int score) {
        try {
            File scoreFile = new File(ScoreManager.class.getResource("/pacman/recursos/scores").toURI());
            BufferedReader br = new BufferedReader(new FileReader(scoreFile));
            ArrayList<String[]> list = new ArrayList<>();
            String line;
            boolean not_added = true;
            while((line = br.readLine()) != null && list.size() < 5) {
                int checkScore = Integer.parseInt(line.split(" ")[1]);
                if(not_added && score >= checkScore) {
                    list.add(new String[]{j, String.valueOf(score)});
                    not_added = false;
                }
                if(list.size() < 5) {
                    list.add(line.split(" "));
                }
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(scoreFile, false));
            for(String[] s : list) {
                bw.write(String.join(" ", s));
                bw.newLine();
            }
            bw.close();
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
