package de.IchMagOmasKekse.simulation.generation;

import de.IchMagOmasKekse.simulation.Cell;
import de.IchMagOmasKekse.simulation.Simulation;

import java.awt.*;
import java.util.HashMap;

public class Generation {

    public HashMap<String, Cell> cells = new HashMap<>();

    public Generation(boolean generateCells) {
        if(generateCells) {
            for(int x = 0; x != Simulation.xAmountOfCells; x++) {
                for(int y = 0; y != Simulation.yAmountOfCells; y++) {
                    cells.put(x+"/"+y, new Cell(x, y, false));
                }
            }
            Simulation.isGenerated = true;
        }
    }
    public Generation(boolean generateCells, boolean teslaLogo) {
        if(generateCells) {
            if(teslaLogo) {
                Color c = null;
                for(int x = 0; x != Simulation.xAmountOfCells; x++) {
                    for(int y = 0; y != Simulation.yAmountOfCells; y++) {
                        c = new Color(GenerationManager.inputImage.getRGB(x, y));
                        if(c.getRed() + c.getGreen() + c.getBlue() == 765) cells.put(x+"/"+y, new Cell(x, y, true));
                        else cells.put(x+"/"+y, new Cell(x, y, false));
                    }
                }
            }else {
                for (int x = 0; x != Simulation.xAmountOfCells; x++) {
                    for (int y = 0; y != Simulation.yAmountOfCells; y++) {
                        cells.put(x + "/" + y, new Cell(x, y, false));
                    }
                }
            }
            Simulation.isGenerated = true;
        }
    }

    public boolean render(Graphics g) {
        if(!GenerationManager.readyToRender) return false;
        for(String pos : cells.keySet()) if(!cells.get(pos).isAlive()) cells.get(pos).render(g);
        for(String pos : cells.keySet()) if(cells.get(pos).isAlive())cells.get(pos).render(g);
        return true;
    }

    public void update() {
        GenerationManager.readyToRender = false;

        if(Simulation.isGenerated) for(String pos : cells.keySet()) cells.get(pos).update();

        GenerationManager.readyToRender = true;
    }

    public void copy(Generation gen) {
        GenerationManager.readyToRender = false;
        this.cells.clear();
        for(String pos : gen.cells.keySet()) cells.put(pos, gen.cells.get(pos));
        GenerationManager.readyToRender = true;
    }

    public void killAll() {
        for(Cell cell : cells.values()) cell.die();
    }

    public void birthCellAt(int x, int y) {
        if(!cells.get(x+"/"+y).isAlive()) {
            cells.get(x+"/"+y).birth();
            Simulation.currentlyLiving++;
        }
    }
    public void killCellAt(int x, int y) {
        if(cells.get(x+"/"+y).isAlive()) {
            cells.get(x + "/" + y).die();
            Simulation.currentlyLiving--;
        }
    }
    public void switchLifeState(int x, int y) {
        cells.get(x+"/"+y).switchLifeState();
        Simulation.currentlyLiving += (cells.get(x+"/"+y).isAlive() ? 1 : -1);
    }


}
