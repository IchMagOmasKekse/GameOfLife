package de.IchMagOmasKekse.simulation.generation;

import de.IchMagOmasKekse.GameLoop;
import de.IchMagOmasKekse.simulation.Cell;
import de.IchMagOmasKekse.simulation.Simulation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GenerationManager {

    public static Generation currentGeneration, nextGeneration;
    public static boolean readyToRender = true;
    public static BufferedImage inputImage;

    public GenerationManager() {
        try {
            System.out.println("Loading Texture: [./src/] tesla_logo.png");
            inputImage = ImageIO.read(new File("./src/tesla_logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentGeneration = new Generation(false);
        nextGeneration = new Generation(true); // False, weil die Cells mit der Maus eingetragen werden.
    }

    public static void updateGenerations() {
        Simulation.currentlyLiving = 0;
        currentGeneration.update();
        currentGeneration.copy(nextGeneration);
        Simulation.generations++;
    }

    public static boolean render(Graphics g) {
        if(!readyToRender) return false;

        currentGeneration.render(g);

        return true;
    }

    public static Cell getCellFromCurrentGen(int x, int y) {
        if(x == -1 || x == Simulation.xAmountOfCells+1) x = ((x + Simulation.xAmountOfCells) % Simulation.xAmountOfCells);
        if(y == -1 || y == Simulation.yAmountOfCells+1) y = ((y + Simulation.yAmountOfCells) % Simulation.yAmountOfCells);

        return currentGeneration.cells.get(x+"/"+y);
    }

}
