/**
 * 
 */
package bspdungeon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Map {
    private int gridSize = 20;
    private Cell root;
    private List<Cell> cells = new ArrayList<>();
    private int numRooms = 10;
    private int minCellDim = 2* gridSize;
    
    /**
     * 
     */
    public Map() {
        root = new Cell(30, 30, Dungeon.WIDTH - 30, Dungeon.HEIGHT - 30, 1);
    }
    
    /**
     * 
     */
    public void snap() {
        for (Cell cell : cells) {
            cell.x1 = snap(cell.x1);
            cell.x2 = snap(cell.x2);
            cell.y1 = snap(cell.y1);
            cell.y2 = snap(cell.y2);
            for (Hall hall: cell.hHalls) {
                hall.x1 = snap(hall.x1);
                hall.x2 = snap(hall.x2);
                hall.y1 = snap(hall.y1);
                hall.y2 = snap(hall.y2);
            }
            for (Hall hall: cell.vHalls) {
                hall.x1 = snap(hall.x1);
                hall.x2 = snap(hall.x2);
                hall.y1 = snap(hall.y1);
                hall.y2 = snap(hall.y2);
            }
        }
    }
    
    private int snap(float value) {
        return Math.round(value / gridSize) * gridSize;
    }
    
    /**
     * 
     */
    public void addHalls() {
        for (Cell cell : cells) {
            for (Cell neighbor : cell.hNeighors) {
                float rightY = Math.min(cell.y2, neighbor.y2);
                float leftY = Math.max(cell.y1, neighbor.y1);
                if ( (rightY - leftY) > gridSize ) {
                    float y = (float) ((Dungeon.random.nextDouble() * (rightY - leftY - gridSize)) + leftY);
                    cell.hHalls.add(new Hall(cell.x2, y, neighbor.x1, y + gridSize));
                }
            }
            for (Cell neighbor : cell.vNeighors) {
                float bottomX = Math.min(cell.x2, neighbor.x2);
                float topX = Math.max(cell.x1, neighbor.x1);
                if ( (bottomX - topX) > gridSize ) {
                    float x = (float) ((Dungeon.random.nextDouble() * (bottomX - topX - gridSize)) + topX);
                    cell.vHalls.add(new Hall(x, cell.y2, x + gridSize, neighbor.y1));
                }
            }
        }
    }
    
    /**
     * 
     */
    public void shrink() {
        root.shrink(minCellDim);
    }

    /**
     * 
     */
    public void findNeighors() {
        root.getLeaves(cells);
        for (Cell cell : cells) {
            for (Cell other : cells) {
                if (cell != other) {
                    if (cell.x2 == other.x1) {
                        if (Math.max(cell.y1, other.y1) < Math.min(cell.y2, other.y2)) {
                            cell.hNeighors.add(other);
                        }
                    }
                    if (cell.y2 == other.y1) {
                        if (Math.max(cell.x1, other.x1) < Math.min(cell.x2, other.x2)) {
                            cell.vNeighors.add(other);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 
     */
    public void divide() {
        int rooms = 1;
        while (rooms < numRooms) {
            if (root.divide(minCellDim)) {
                rooms++;
            }
        }
    }
    
    /**
     * @param g2
     */
    public void draw(Graphics2D g2) {
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, Dungeon.WIDTH, Dungeon.HEIGHT);
        drawCellBackground(root, g2);
        g2.setColor(Color.DARK_GRAY);
        for (int x=0;x<Dungeon.WIDTH;x+=gridSize) {
            g2.drawLine(x, 0, x, Dungeon.HEIGHT);
        }
        for (int y=0;y<Dungeon.HEIGHT;y+=gridSize) {
            g2.drawLine(0, y, Dungeon.WIDTH, y);
        }
        drawCellBorder(root, g2);
    }
    
    private void drawCellBackground(Cell cell, Graphics2D g2) {
        if (cell.left == null) {
            cell.drawBackground(g2);
        } else {
            drawCellBackground(cell.left, g2);
            drawCellBackground(cell.right, g2);
        }
    }
    
    private void drawCellBorder(Cell cell, Graphics2D g2) {
        if (cell.left == null) {
            cell.drawBorder(g2);
        } else {
            drawCellBorder(cell.left, g2);
            drawCellBorder(cell.right, g2);
        }
    }
}
