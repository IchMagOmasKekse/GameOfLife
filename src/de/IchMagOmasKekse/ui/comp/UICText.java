package de.IchMagOmasKekse.ui.comp;

import de.IchMagOmasKekse.Chat;
import de.IchMagOmasKekse.ui.UIC;
import de.IchMagOmasKekse.ui.UICNames;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class UICText extends UIC {

    public UICText(int x, int y) {
        super(UICNames.UIC_TEXT.getSystemName(), x, y, 0, 0);
        defineColorsToDefault();
        defineFont();
        setDynamicSize(true, true);
        recalculateStringSize(text);
    }

    public UICText(int x, int y, String fontName, int fontStyle, int fontSize) {
        super(UICNames.UIC_TEXT.getSystemName(), x, y, 0, 0);
        defineColorsToDefault();
        setDynamicSize(true, true);
        font = new Font(fontName, fontStyle, fontSize);
        recalculateStringSize(text);
    }

    public UICText(int x, int y, int width, int height, String text) {
        super(UICNames.UIC_TEXT.getSystemName(), x, y, width, height, text);
        defineColorsToDefault();
        defineFont();
        setDynamicSize(true, true);
        recalculateStringSize(text);
    }

    @Override
    public void defineColors() {

    }

    @Override
    public void defineFont() {

    }

    @Override
    public void setup() {

    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(c_forecolor);
        g.setFont(font);
        g.drawString(text, x, y + height);
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
