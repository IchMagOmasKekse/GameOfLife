package de.IchMagOmasKekse.simulation;

import de.IchMagOmasKekse.Chat;
import de.IchMagOmasKekse.GameLoop;
import de.IchMagOmasKekse.simulation.generation.Generation;
import de.IchMagOmasKekse.simulation.generation.GenerationManager;

import java.awt.*;

public class Simulation {

    public static final int cellSize = 5;
    public static boolean start = false;
    public static int xAmountOfCells = 0, yAmountOfCells = 0, generations = 0;

    public Simulation() {
        // Calculating amount of cells in x and y.
        xAmountOfCells = GameLoop.windowWidth / cellSize;
        yAmountOfCells = GameLoop.windowHeight / cellSize;

        new GenerationManager();
    }

    public static boolean render(Graphics g) {
        GenerationManager.render(g);
        return true;
    }

    public static void update() {
        if(!start) return;
        if(!GameLoop.isLegalTick) return;
        GenerationManager.updateGenerations();
    }

    public static void nextGeneration() {
        GenerationManager.updateGenerations();
        generations+=1;
    }

    public static String editTitle(String t) {
        return t.replace("2", "Generation: " + generations);
    }

}
