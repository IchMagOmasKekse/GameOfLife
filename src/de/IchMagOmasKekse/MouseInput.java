package de.IchMagOmasKekse;

import de.IchMagOmasKekse.simulation.Cell;
import de.IchMagOmasKekse.simulation.Simulation;
import de.IchMagOmasKekse.simulation.generation.GenerationManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.concurrent.ConcurrentLinkedDeque;

public class MouseInput extends MouseAdapter {

    private ConcurrentLinkedDeque<Integer> pressedButtons = new ConcurrentLinkedDeque<Integer>();
    private int mx = 0, my = 0;
    private boolean convertToGrid = true;


    public MouseInput() {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressKey(e.getButton());
        this.mx = (int) (e.getX());
        this.my = (int) (e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        releaseKey(e.getButton());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mx = (int) (e.getX());
        this.my = (int) (e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mx = (int) (e.getX());
        this.my = (int) (e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);
    }

    public void pressKey(int key) {
        if (!pressedButtons.contains(key)) pressedButtons.add(key);
        if (Simulation.generations == 0) Simulation.nextGeneration();
        switch (key) {
            case MouseEvent.BUTTON1://BUTTON1 = Linke Maustaste
                if(GenerationManager.readyToRender) {
                    GenerationManager.readyToRender = false;
                    if (KeyInput.isControlling) { // birth
                        GenerationManager.currentGeneration.cells.get((mx / Simulation.cellSize) + "/" + (my / Simulation.cellSize)).birth();
                    } else if (KeyInput.isAlting) { // kill
                        GenerationManager.currentGeneration.cells.get((mx / Simulation.cellSize) + "/" + (my / Simulation.cellSize)).die();
                    } else {
                        GenerationManager.currentGeneration.cells.get((mx / Simulation.cellSize) + "/" + (my / Simulation.cellSize)).switchLifeState();
                        releaseKey(key);
                    }
                    GenerationManager.readyToRender = true;
                }
                break; //BUTTON1 = Linke Maustaste
            case MouseEvent.BUTTON2:
                break; //BUTTON2 = Mausrad-Klick
            case MouseEvent.BUTTON3:
                break; //BUTTON3 = Rechte Maustaste
        }
    }

    public void releaseKey(int key) {
        if (pressedButtons.contains(key)) pressedButtons.remove(key);
        switch (key) {
            case MouseEvent.BUTTON1:
                break; //BUTTON1 = Linke Maustaste
            case MouseEvent.BUTTON2:
                break; //BUTTON2 = Mausrad-Klick
            case MouseEvent.BUTTON3:
                break; //BUTTON3 = Rechte Maustaste
        }
    }

    /**
     * Geht alle gedrückten Buttons durch, um ein automatischen drücken zu ermöglichen.
     */
    public void tick() {
        //if(handTorch != null) handTorch.tick();
        if (pressedButtons.isEmpty()) return;

        for (int key : pressedButtons) {
            pressKey(key);
        }
    }

    public int getCurrentMouseX() {
        return this.mx;
    }

    public int getCurrentMouseY() {
        return this.my;
    }
}