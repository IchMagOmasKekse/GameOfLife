package de.IchMagOmasKekse.simulation.generation;

import de.IchMagOmasKekse.Chat;
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
        }
    }

    public boolean render(Graphics g) {
        for(String pos : cells.keySet()) if(!cells.get(pos).isAlive()) cells.get(pos).render(g);
        for(String pos : cells.keySet()) if(cells.get(pos).isAlive())cells.get(pos).render(g);
        return true;
    }

    public void update() {
        GenerationManager.readyToRender = false;

        for(String pos : cells.keySet()) cells.get(pos).update();

        GenerationManager.readyToRender = true;
    }

    public void copy(Generation gen) {
        GenerationManager.readyToRender = false;
        this.cells.clear();
        for(String pos : gen.cells.keySet()) cells.put(pos, gen.cells.get(pos));
        Chat.sendConsoleMessage("Copied Generation.");
        GenerationManager.readyToRender = true;
    }


}
