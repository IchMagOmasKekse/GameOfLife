package de.IchMagOmasKekse.ui.comp;

import de.IchMagOmasKekse.simulation.Simulation;

public class LivingCellDisplay extends UICText {

    public LivingCellDisplay(int x, int y, String fontName, int fontStyle, int fontSize) {
        super(x, y, fontName, fontStyle, fontSize);
    }

    public void update() {
        setText("Lebende Zellen: "+ Simulation.currentlyLiving);
    }
}
