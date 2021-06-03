package de.IchMagOmasKekse.ui.comp;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import de.IchMagOmasKekse.simulation.Simulation;

public class ResetButton extends UICButton {

    public ResetButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        defineColorsToDefault();
        roundRect = true;
        rounding = 10;
    }

    @Override
    public void draw(Graphics2D g) {
        drawRect(g);
        g.setColor(c_forecolor);
        g.setFont(font);
        g.drawString(text, x + 8, y + (height/2) + (font.getSize() / 2) -3);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Simulation.reset();
        anima_clicked_cooldown = 5;
    }

    @Override
    public void mouseHover(MouseEvent e) {
        super.mouseHover(e);
    }
}
