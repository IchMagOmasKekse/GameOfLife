package de.IchMagOmasKekse.ui.comp;

import de.IchMagOmasKekse.Chat;
import de.IchMagOmasKekse.simulation.Simulation;
import de.IchMagOmasKekse.ui.UIC;
import de.IchMagOmasKekse.ui.UICNames;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class UICButton extends UIC {

    public UICButton(int x, int y) {
        super(UICNames.UIC_BUTTON.getSystemName(), x, y, 0, 0);
        defineColorsToDefault();
        defineFont();
        setDynamicSize(true, true);
        texture = Simulation.textureAtlas.getTexture("button");
    }
    public UICButton(int x, int y, int width, int height) {
        super(UICNames.UIC_BUTTON.getSystemName(), x, y, width,height);
        defineColorsToDefault();
        defineFont();
        setDynamicSize(true, true);
        texture = Simulation.textureAtlas.getTexture("button");
    }

    public UICButton(int x, int y, String fontName, int fontStyle, int fontSize) {
        super(UICNames.UIC_BUTTON.getSystemName(), x, y, 0, 0);
        defineColorsToDefault();
        setDynamicSize(true, true);
        font = new Font(fontName, fontStyle, fontSize);
        texture = Simulation.textureAtlas.getTexture("button");
    }

    public UICButton(int x, int y, int width, int height, String text) {
        super(UICNames.UIC_BUTTON.getSystemName(), x, y, width, height, text);
        defineColorsToDefault();
        defineFont();
        setDynamicSize(true, true);
        texture = Simulation.textureAtlas.getTexture("button");
    }

    @Override
    public void defineColors() {

    }

    @Override
    public void defineFont() {
        font = new Font("Candara", 1, 14);
    }

    @Override
    public void setup() {

    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        drawRect(g);
        g.setColor(c_forecolor);
        g.setFont(font);
        g.drawString(text, x, y + (height/2) + (font.getSize() / 2) - 3);
    }

    @Override
    public void unselect() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseWheeled(MouseWheelEvent e) {

    }

    @Override
    public void mouseHover(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
