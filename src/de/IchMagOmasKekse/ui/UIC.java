package de.IchMagOmasKekse.ui;

import de.IchMagOmasKekse.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class UIC {

    protected final String id;
    protected String text;
    protected final String systemName;
    protected int x, y, width, height, rounding = 3;
    protected boolean enabled = true, isSelected = false, isHighlighted = false, hide = false, roundRect = false;
    protected Color c_infill, c_border, c_clicked, c_selected, c_highlighted, c_forecolor, c_disabled;
    protected Font font;
    protected UIC parent = null;
    protected BufferedImage texture;

    /* Dynamics */
    protected boolean dynamicX = false, dynamicY = false, dynamicWidth = false, dynamicHeight = false;
    protected int dynamicWidthOffset = 0, dynamicHeightOffset = 0, dynamicXOffset = 0, dynamicYOffset = 0,
            hitboxX = Integer.MIN_VALUE, hitboxY = Integer.MIN_VALUE, hitboxWidth = Integer.MIN_VALUE, hitboxHeight = Integer.MIN_VALUE;

    /* Graphics */
    protected boolean textAntialiasing = true;
    protected int anima_clicked_cooldown = 0;

    public UIC(String systemName, int x, int y, int width, int height) {
        id = Code.getRandomCode(10, 5);
        this.systemName = systemName;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = "T-Text";
        setup();
    }

    public UIC(String systemName, int x, int y, int width, int height, String text) {
        id = Code.getRandomCode(10, 5);
        this.systemName = systemName;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        setup();
    }

    public abstract void defineColors();

    public abstract void defineFont();

    public abstract void setup();

    public abstract void update();

    /**
     * Call the UIC.render(); instead!
     * The UIC.render(); method does some extra calculations before drawing.
     */
    public abstract void draw(Graphics2D g);

    protected abstract void unselect();

    public abstract void mouseClicked(MouseEvent e);

    public abstract void mouseReleased(MouseEvent e);

    public abstract void mouseDragged(MouseEvent e);

    public abstract void mouseWheeled(MouseWheelEvent e);

    public abstract void mouseHover(MouseEvent e);

    public abstract void keyPressed(KeyEvent e);

    public abstract void keyReleased(KeyEvent e);

    public void checkKeyPressed(KeyEvent e) {
        if (isSelected) keyPressed(e);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && UICHandler.hasSelectedUIC() && UICHandler.getSelectedUIC() == this)
            UICHandler.setSelectedUIC(null);
    }

    public void checkKeyReleased(KeyEvent e) {
        if (isSelected) keyReleased(e);
    }

    public void checkMouseClick(MouseEvent e) {
        if (isInComponentBounds(e.getX(), e.getY())) {
            mouseClicked(e);
        } else if (UICHandler.hasSelectedUIC() && UICHandler.getSelectedUIC() == this)
            UICHandler.setSelectedUIC(null);
    }

    public void checkMouseReleased(MouseEvent e) {
        mouseReleased(e);
    }

    public void checkMouseDragged(MouseEvent e) {
        if (isInComponentBounds(e.getX(), e.getY())) mouseDragged(e);
    }

    public void checkMouseWheel(MouseWheelEvent e) {
        if (isInComponentBounds(e.getX(), e.getY())) mouseWheeled(e);
    }

    public void checkMouseHover(MouseEvent e) {
        if (isInComponentBounds(e.getX(), e.getY())) {
            isHighlighted = true;
            mouseHover(e);
        } else isHighlighted = false;
    }

    public boolean isDoubleClicked() {
        long l = System.currentTimeMillis();
        return (l - MouseInput.lastClick < Settings.doubleClickSpeed);
    }

    public void unselectUIC() {
        isSelected = false;
        unselect();
    }

    public void select() {
        isSelected = true;
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(font);
        if (c_infill == null || c_border == null || c_clicked == null || c_selected == null || c_forecolor == null || c_disabled == null) {
            Chat.sendConsoleError("[" + getClass().getSimpleName() + "] ERROR#455 -> Es wurden nicht alle Farben definiert! Rendern nicht möglich.");
        } else if (font == null) {
            Chat.sendConsoleError("[" + getClass().getSimpleName() + "] ERROR#445 -> Es wurde keine Font definiert.");
        } else if (!hide) {
            if (textAntialiasing) {
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            }
            draw(g2d);
        }

        if (Settings.showUICHitboxes) {
            g.setColor(new Color(255, 2, 0, 120));
            if (hasHitbox() && parent != null) g.fillRect(hitboxX, hitboxY, hitboxWidth, hitboxHeight);
            else g.fillRect(x, y, width, height);
        }
    }

    /**
     * Fits the width and height to the actual window size.
     */
    public void tick() {
        // Fitting size
        if (getSystemName().equals(UICNames.UIC_CANVAS.getSystemName())) {
            if (hasDynamicWidth()) width = GameLoop.windowWidth + dynamicWidthOffset;
            if (hasDynamicHeight()) height = GameLoop.windowHeight + dynamicHeightOffset;
        }

        // Fitting coordinates
        if (hasDynamicX()) x = dynamicXOffset;
        if (hasDynamicY()) y = dynamicYOffset;

        // Send Update Tick
        update();

        // Stepping down cooldowns
        if (anima_clicked_cooldown > width) anima_clicked_cooldown = width;
        if (anima_clicked_cooldown > 0) anima_clicked_cooldown--;
    }


    protected void drawRect(Graphics g) {
        int i = anima_clicked_cooldown; // Offset of clicking animation
        Color c = g.getColor();
        if (texture == null) {
            if (!enabled) g.setColor(c_disabled);
            g.setColor(c_infill);
            if (!roundRect) g.fillRect(x + i, y + i, width - i - i, height - i - i);
            else g.fillRoundRect(x + i, y + i, width - i - i, height - i - i, rounding, rounding);

            g.setColor(c_border);
            if (!roundRect) g.drawRect(x + i, y + i, width - i - i, height - i - i);
            else g.fillRoundRect(x + i, y + i, width - i - i, height - i - i, rounding, rounding);

            if (isSelected) {
                g.setColor(c_selected);
                if (!roundRect) g.fillRect(x + i, y + i, width - i - i, height - i - i);
                else g.fillRoundRect(x + i, y + i, width - i - i, height - i - i, rounding, rounding);
            }

        } else g.drawImage(texture, x + i, y + i, width - i - i, height - i - i, null);
        if (isHighlighted) {
            g.setColor(c_highlighted);
            if (!roundRect) g.fillRect(x + i, y + i, width - i - i, height - i - i);
            else g.fillRoundRect(x + i, y + i, width - i - i, height - i - i, rounding, rounding);
        }
        
        g.setColor(c);
    }

    protected void defineColorsToDefault() {
        c_infill = GameColor.UIC_INFILL.getColor();
        c_border = GameColor.UIC_BORDER.getColor();
        c_clicked = GameColor.UIC_CLICKED.getColor();
        c_selected = GameColor.UIC_SELECTED.getColor();
        c_highlighted = GameColor.UIC_HIGHLIGHT.getColor();
        c_forecolor = GameColor.UIC_FORECOLOR.getColor();
        c_disabled = GameColor.UIC_DISABLED.getColor();
    }
    protected void defineFontToDefault() {
    	font = GameFont.DEFAULT.getFont();
    }

    public void setText(String text) {
        this.text = text;
        recalculateStringSize(text);
    }

    public void setParent(UIC uic) {
        this.parent = uic;
        addPosition(uic.getX(), uic.getY());
    }

    public void recalculateStringSize(String text) {
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        width = (int) (font.getStringBounds(text, frc).getWidth());
        height = (int) (font.getStringBounds(text, frc).getHeight()) / 2;
    }

    public String getId() {
        return id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public void show() {
        hide = false;
    }

    public void hide() {
        hide = true;
    }

    public boolean isInComponentBounds(int x, int y) {
        if (hasHitbox())
            return (x > hitboxX && y > hitboxY && x < hitboxWidth + hitboxX && y < hitboxHeight + hitboxY);
        else return (x > this.x && y > this.y && x < this.x + width && y < this.y + height);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean hasDynamicWidth() {
        return dynamicWidth;
    }

    public boolean hasDynamicHeight() {
        return dynamicHeight;
    }

    public boolean hasDynamicX() {
        return dynamicX;
    }

    public boolean hasDynamicY() {
        return dynamicY;
    }

    public Color getInfillColor() {
        return c_infill;
    }

    public Color getBorderColor() {
        return c_border;
    }

    public Color getClickedColor() {
        return c_clicked;
    }

    public Color getSelectedColor() {
        return c_selected;
    }

    public Color getForeColor() {
        return c_forecolor;
    }

    public Font getFont() {
        return font;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean hasHitbox() {
        return (hitboxX != Integer.MIN_VALUE && hitboxY != Integer.MIN_VALUE && hitboxWidth != Integer.MIN_VALUE && hitboxHeight != Integer.MIN_VALUE);
    }

    public UIC getParentUIC() {
        return parent;
    }

    public void setDynamicCoordinates(boolean dynX, boolean dynY) {
        this.dynamicX = dynX;
        this.dynamicY = dynY;
    }

    public void setDynamicSize(boolean dynWidth, boolean dynHeight) {
        this.dynamicWidth = dynWidth;
        this.dynamicHeight = dynHeight;
    }

    public void setDynamicSizeOffset(int dynamicWidthOffset, int dynamicHeightOffset) {
        this.dynamicWidthOffset = dynamicWidthOffset;
        this.dynamicHeightOffset = dynamicHeightOffset;
    }

    public void setDynamicCoordOffset(int dynamicXOffset, int dynamicYOffset) {
        this.dynamicXOffset = dynamicXOffset;
        this.dynamicYOffset = dynamicYOffset;
    }

    public void addPosition(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void applyTextAntialiasing(boolean apply) {
        textAntialiasing = apply;
    }
}
