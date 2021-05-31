package de.IchMagOmasKekse;

import de.IchMagOmasKekse.simulation.Cell;
import de.IchMagOmasKekse.simulation.Simulation;
import de.IchMagOmasKekse.simulation.generation.Generation;
import de.IchMagOmasKekse.simulation.generation.GenerationManager;
import de.IchMagOmasKekse.ui.UICHandler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.concurrent.ConcurrentLinkedDeque;

public class MouseInput extends MouseAdapter {

    private ConcurrentLinkedDeque<Integer> pressedButtons = new ConcurrentLinkedDeque<Integer>();
    private int mx = 0, my = 0;
    private boolean convertToGrid = true;
    public static long lastClick = 0L;


    public MouseInput() {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressKey(e.getButton());
        this.mx = (int) (e.getX());
        this.my = (int) (e.getY());
        UICHandler.mouseClicked(e);
        lastClick = System.currentTimeMillis();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        releaseKey(e.getButton());
        UICHandler.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mx = (int) (e.getX());
        this.my = (int) (e.getY());
        UICHandler.mouseMoved(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mx = (int) (e.getX());
        this.my = (int) (e.getY());
        UICHandler.mouseDragged(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        UICHandler.mouseWheeled(e);
    }

    public void pressKey(int key) {
        if (!pressedButtons.contains(key)) pressedButtons.add(key);
        switch (key) {
            case MouseEvent.BUTTON1://BUTTON1 = Linke Maustaste
                if (GenerationManager.readyToRender && Simulation.isGenerated) {
                    if(!Simulation.isInGrid(mx, my)) return;
                    GenerationManager.readyToRender = false;
                    if (KeyInput.isControlling) { // birth
                        GenerationManager.currentGeneration.birthCellAt((mx / Simulation.cellSize), (my / Simulation.cellSize));
                    } else if (KeyInput.isAlting) { // kill
                        GenerationManager.currentGeneration.killCellAt((mx / Simulation.cellSize), (my / Simulation.cellSize));
                    } else {
                        GenerationManager.currentGeneration.switchLifeState((mx / Simulation.cellSize), (my / Simulation.cellSize));
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