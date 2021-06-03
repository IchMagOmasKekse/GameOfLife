package de.IchMagOmasKekse;

import java.awt.*;

public enum GameColor {

    LIVING(new Color(0, 181, 3, 255)),
    OLDEST(new Color(195, 255, 0, 255)),
    BACKGROUND(new Color(40, 40, 40, 255)),
    //BACKGROUND(new Color(0, 51, 161, 255)),
    GRID(new Color(20, 20, 20, 255)),
    //GRID(new Color(13, 60, 161, 255)),
    UIC_DISABLED(new Color(133, 133, 133, 255)),
    UIC_INFILL(new Color(168, 223, 255, 255)),
    UIC_BORDER(new Color(89, 194, 255, 255)),
    UIC_CLICKED(new Color(49, 147, 204, 255)),
    UIC_SELECTED(new Color(189, 231, 255, 255)),
    UIC_HIGHLIGHT(new Color(176, 222, 255, 50)),
    UIC_FORECOLOR(new Color(0, 0, 0, 255)),
    PAUSED_BLACK(new Color(0, 0, 0, 50)),
    UIC_ITEM_LIST_ITEM_BACKGROUND(new Color(50, 50, 50, 255)),
	UIC_ITEM_LIST_ITEM_HIGHLIGHTED(new Color(70, 70, 70, 255));

    int r, g, b, a;
    Color color;

    GameColor(Color color) {
        this.color = color;
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
        this.a = color.getAlpha();
    }

    public Color getColor() {
        return color;
    }

    public int getRed() {
        return r;
    }

    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return b;
    }

    public int getAlpha() {
        return a;
    }
}
