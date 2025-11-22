/**
 * 
 */
package bspdungeon;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Cell extends Tile {
    /** Left cell or null if leaf */
    public Cell left = null;
    /** Right cell or null if leaf */
    public Cell right = null;
    /** Horizontal neighbor cells */
    public List<Cell> hNeighors;
    /** Vertical neighbor cells */
    public List<Cell> vNeighors;
    /** Horizontal neighbor halls */
    public List<Hall> hHalls;
    /** Vertical neighbor halls */
    public List<Hall> vHalls;
    
    /**
     * @param x1 top of cell
     * @param y1 left of cell
     * @param x2 bottom of cell
     * @param y2 right of cell
     * @param type cell type
     */
    public Cell(float x1, float y1, float x2, float y2, int type) {
        super(x1, y1, x2, y2, type);
        hNeighors = new ArrayList<>();
        vNeighors = new ArrayList<>();
        hHalls = new ArrayList<>();
        vHalls = new ArrayList<>();
    }
    
    /**
     * @param minCellDim
     */
    public void shrink(int minCellDim) {
        if (left == null) {
            float width = x2 - x1;
            float height = y2 - y1;
            float newWidth = (float) Math.max(width * ((Dungeon.random.nextDouble() * 0.65) + 0.25), minCellDim);
            float newHeight = (float) Math.max(height * ((Dungeon.random.nextDouble() * 0.65) + 0.25), minCellDim);
            
            x1 += (newWidth / 2);
            x2 -= (newWidth / 2);
            y1 += (newHeight / 2);
            y2 -= (newHeight / 2);
        } else {
            left.shrink(minCellDim);
            right.shrink(minCellDim);
        }
    }
    
    /**
     * @param cells
     */
    public void getLeaves(List<Cell> cells) {
        if (left == null) {
            cells.add(this);
        } else {
            left.getLeaves(cells);
            right.getLeaves(cells);
        }
        
    }
    
    /**
     * @param minCellDim
     * @return true if divided
     */
    public boolean divide(int minCellDim) {
        float width = x2 -x1;
        float height = y2 - y1;
        
        if (width < minCellDim && height < minCellDim) {
            return false;
        }
        if (left != null) {
            if ((int) (Dungeon.random.nextDouble() * 100) < 50) {
                return left.divide(minCellDim);
            } else {
                return right.divide(minCellDim);
            }
        }
        
        if (width > height) {
            float newMid = x1 + ((float) ((Dungeon.random.nextDouble() * 0.3) + 0.3)) * width;
            int newType = type;
            if ((Dungeon.random.nextDouble() * 100) < 30.0) {
                newType++;
            }
            left = new Cell(x1, y1, newMid, y2, newType);
            right = new Cell(newMid, y1, x2, y2, newType);
            
            return true;
        } else {
            float newMid = y1 + ((float) ((Dungeon.random.nextDouble() * 0.3) + 0.3)) * height;
            int newType = type;
            if ((Dungeon.random.nextDouble() * 100) < 30.0) {
                newType++;
            }
            left = new Cell(x1, y1, x2, newMid, newType);
            right = new Cell(x1, newMid, x2, y2, newType);
            
            return true;
        }
    }
    
    /**
     * @param g2
     */
    @Override
    public void drawBackground(Graphics2D g2) {
        for (Hall hall : hHalls) {
            hall.drawBackground(g2);
        }
        for (Hall hall : vHalls) {
            hall.drawBackground(g2);
        }
        super.drawBackground(g2);
    }
    
    /**
     * @param g2
     */
    @Override
    public void drawBorder(Graphics2D g2) {
        for (Hall hall : hHalls) {
            hall.drawBorder(g2);
        }
        for (Hall hall : vHalls) {
            hall.drawBorder(g2);
        }
        super.drawBorder(g2);
    }
}
