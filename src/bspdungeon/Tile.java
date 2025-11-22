/**
 * 
 */
package bspdungeon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 */
public abstract class Tile {
    /** top of tile */
    public float x1;
    /** left of tile */
    public float y1;
    /** bottom of tile */
    public float x2;
    /** right of tile */
    public float y2;
    protected int type;

    /**
     * @param x1 top of tile
     * @param y1 left of tile
     * @param x2 bottom of tile
     * @param y2 right of tile
     * @param type 
     */
    public Tile(float x1, float y1, float x2, float y2, int type) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.type = type;
    }
    
    /**
     * @param g2 advanced 2d graphics object
     */
    public void drawBackground(Graphics2D g2) {
        g2.setColor(Color.getHSBColor((30.0f * type) / 360.0f, (type == 0) ? 0.0f : 1.0f, (type == 0) ? 0.8f : 0.5f));
        g2.fillRect((int) x1, (int) y1, (int) (x2 - x1), (int) (y2 - y1));
    }
    
    /**
     * @param g2 advanced 2d graphics object
     */
    public void drawBorder(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect((int) x1, (int) y1, (int) (x2 - x1), (int) (y2 - y1));
    }
}
