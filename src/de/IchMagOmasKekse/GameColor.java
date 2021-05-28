package de.IchMagOmasKekse;

import java.awt.*;

public enum GameColor {

    LIVING(new Color(0, 181, 3, 255)),
    OLDEST(new Color(195, 255, 0, 255)),
    BACKGROUND(new Color(0, 51, 161, 255)),
    GRID(new Color(13, 60, 161, 255));

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
