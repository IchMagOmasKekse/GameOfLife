package de.IchMagOmasKekse.ui.comp;

import de.IchMagOmasKekse.simulation.Simulation;

public class GenerationDisplay extends UICText {

    public GenerationDisplay(int x, int y, String fontName, int fontStyle, int fontSize) {
        super(x, y, fontName, fontStyle, fontSize);
    }

    public void update() {
        setText("Generation: "+ Simulation.generations);
    }
}
