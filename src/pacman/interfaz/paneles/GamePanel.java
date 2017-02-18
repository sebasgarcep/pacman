/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.interfaz.paneles;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author sebastian
 */
public abstract class GamePanel extends javax.swing.JPanel {
    public final void setContexto(GamePanel panel) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = getFrame();
            try {
                window.getContentPane().removeAll();
                window.setContentPane(panel);
            } catch(NullPointerException ex) {}
            window.revalidate();
            panel.setFocusable(true);
            panel.requestFocusInWindow();
        });
    }
    
    public JFrame getFrame() {
        return (JFrame)SwingUtilities.getWindowAncestor(this);
    }
}
