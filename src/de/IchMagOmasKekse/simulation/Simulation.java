package de.IchMagOmasKekse.simulation;

import de.IchMagOmasKekse.GameLoop;
import de.IchMagOmasKekse.TextureAtlas;
import de.IchMagOmasKekse.simulation.generation.GenerationManager;
import de.IchMagOmasKekse.ui.UI;

import java.awt.*;

public class Simulation {

    public static final int cellSize = 15;
    public static boolean start = false, isGenerated = false;
    public static int xAmountOfCells = 0, yAmountOfCells = 0, generations = 0, currentlyLiving = 0, oldestCell = 0;
    public static TextureAtlas textureAtlas;

    public Simulation() {
        // Calculating amount of cells in x and y.
        xAmountOfCells = GameLoop.windowWidth / cellSize;
        yAmountOfCells = GameLoop.windowHeight / cellSize - 4; // 4 ist die obere weiße Leiste des Fensters.

        new GenerationManager();
    }

    public static boolean render(Graphics g) {
        GenerationManager.render(g);
        g.setColor(Color.BLUE);
        g.drawLine((GameLoop.windowWidth-GameLoop.ui_offset) / 2, 0, (GameLoop.windowWidth-GameLoop.ui_offset) / 2, GameLoop.windowHeight);
        g.drawLine(0, GameLoop.windowHeight / 2, (GameLoop.windowWidth-GameLoop.ui_offset), GameLoop.windowHeight / 2);
        return true;
    }

    public static void update() {
        if (!start) return;
        if (!GameLoop.isLegalTick) return;
        GenerationManager.updateGenerations();
    }

    public static void nextGeneration() {
        GenerationManager.updateGenerations();
    }

    public static String editTitle(String t) {
        return t.replace("WORLDSIZE", "w/h:  " + xAmountOfCells + "/" + yAmountOfCells)
                .replace("GEN", "Generation: " + generations)
                .replace("LIVING", "Living Cells: " + currentlyLiving)
                .replace("OLDEST", "Oldest Cell: " + oldestCell);
    }

    public static boolean isInGrid(int x, int y) {
        return (x > 0 && x < (xAmountOfCells * cellSize) && y > 0 && y < (yAmountOfCells * cellSize));
    }

    public static void toggleGameState() {
        start = !start;
        if (start) UI.gstate = UI.GameState.IN_GAME;
        else UI.gstate = UI.GameState.PAUSED;
    }

    public static void pauseGame() {
        start = false;
        UI.gstate = UI.GameState.PAUSED;
    }

    public static void reset() {
        pauseGame();
        generations = 0;
        oldestCell = 0;
        currentlyLiving = 0;
        GenerationManager.currentGeneration.killAll();
        GenerationManager.nextGeneration.killAll();
    }

}
