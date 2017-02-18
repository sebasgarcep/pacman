/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.interfaz.paneles;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pacman.objetos.manejador.Juego;

/**
 *
 * @author sebastian
 */
public class PauseDialog extends JDialog {
    private JFrame parent;
    
    public PauseDialog(GamePanel owner, Juego juego) {
        super(owner.getFrame());
        this.parent = owner.getFrame();
        
        this.setSize(300, 200);
        this.setLocation(150, 100);
        this.setUndecorated(true);
        this.setLocationRelativeTo(owner.getFrame());
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        JLabel pauseLabel = new JLabel("PAUSA");
        pauseLabel.setFont(new Font("SANS-SERIF", Font.BOLD, 32));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(pauseLabel, c);
        
        JButton button = new JButton("TERMINAR JUEGO");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                resume();
                juego.gameOver();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(button, c);
        
        this.add(panel);
    }
    
    public void pause() {
        this.setVisible(true);
        this.parent.toFront();
        this.parent.setState(Frame.NORMAL);
    }

    public void resume() {
        this.setVisible(false);
    }
}
