package de.IchMagOmasKekse.simulation;

import de.IchMagOmasKekse.GameColor;
import de.IchMagOmasKekse.Numbers;
import de.IchMagOmasKekse.simulation.generation.GenerationManager;

import java.awt.*;

public class Cell {

    private boolean isAlive = true;
    private int age = 0;
    private int maxAge;
    private int x, y;
    private Color color = Color.WHITE;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        maxAge = Numbers.randomInteger(80, 105);
    }

    public Cell(int x, int y, boolean isAlive) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
        maxAge = Numbers.randomInteger(80, 105);
    }

    public void update() {
        if (x == 0 || y == 0 || x == Simulation.xAmountOfCells || y == Simulation.yAmountOfCells) return;
        int alive = scanNeighbors();
        boolean willBeAlive = false;

        if (isAlive) {
            if (alive == 2 || alive == 3) willBeAlive = true;
            else willBeAlive = false;

        } else if (alive == 3) willBeAlive = true;

        if(willBeAlive) age++;
        else age = 0;

        if(Simulation.oldestCell < age) Simulation.oldestCell = age;
        else if(Simulation.oldestCell == age && !willBeAlive) Simulation.oldestCell = 0;
        GenerationManager.nextGeneration.cells.put(x + "/" + y, new Cell(x, y, willBeAlive));
        GenerationManager.nextGeneration.cells.get(x + "/" + y).age = age;
        Simulation.currentlyLiving += (willBeAlive ? 1 : 0);

        if(age >= maxAge) die();
    }

    public void render(Graphics g) {
        if (isAlive) {
            if(Simulation.oldestCell == age) g.setColor(GameColor.OLDEST.getColor());
            else g.setColor(GameColor.LIVING.getColor());

            g.fillRect((x * Simulation.cellSize), (y * Simulation.cellSize), Simulation.cellSize, Simulation.cellSize);
        } else {
            g.setColor(GameColor.GRID.getColor());
            g.drawRect((x * Simulation.cellSize), (y * Simulation.cellSize), Simulation.cellSize, Simulation.cellSize);
        }
    }

    /**
     * Scans for living neighbors and returns the amount of living neighbors.
     */
    public int scanNeighbors() {
        int alive = 0;
        for (int row = -1; row != 2; row++) {
            for (int col = -1; col != 2; col++) {
                if (GenerationManager.getCellFromCurrentGen(x + row, y + col) != null)
                    alive += (GenerationManager.getCellFromCurrentGen(x + row, y + col).isAlive ? 1 : 0);
            }
        }
        alive -= (isAlive ? 1 : 0);
        return alive;
    }

    public void birth() {
        this.isAlive = true;
    }

    public void die() {
        this.isAlive = false;
        if(Simulation.oldestCell == age -1) Simulation.oldestCell = 0;
    }

    public void switchLifeState() {
        isAlive = !isAlive;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
