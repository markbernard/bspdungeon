/**
 * 
 */
package bspdungeon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;

/**
 * 
 */
public class Dungeon extends JPanel implements WindowListener {
    private static final long serialVersionUID = -594092383814988784L;
    /** width of the play field.  */
    public static final int WIDTH = 800;
    /** height of the play field. */
    public static final int HEIGHT = 1000;
    
    /** Use a fixed seed to generate repeatable levels */
    public static Random random = new Random(System.currentTimeMillis());
    
    private Map map;
    
    /**
     * @param parent
     */
    public Dungeon(JFrame parent) {
        parent.addWindowListener(this);
        map = new Map();
        map.divide();
        map.findNeighors();
        map.shrink();
        map.addHalls();
        map.snap();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        map.draw((Graphics2D) g);
    }

    
    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String... args) throws Exception {
        JFrame f = new JFrame("BSP Dungeon");
        JRootPane pane = f.getRootPane();
        pane.setLayout(new BorderLayout());
        pane.add(new Dungeon(f), BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);
    }
}
