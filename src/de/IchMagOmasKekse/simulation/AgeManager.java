package de.IchMagOmasKekse.simulation;

import de.IchMagOmasKekse.Chat;

import java.awt.image.BufferedImage;

public class AgeManager {

    public static BufferedImage getAgeImage(int age, int maxAge) {
        int i = maxAge / 6; // 6 states of age images are available
        if (age >= i * 5)
            return Simulation.textureAtlas.getTexture("cell_rentner");
        else if (age >= i * 4)
            return Simulation.textureAtlas.getTexture("cell_reif");
        else if (age >= i * 3)
            return Simulation.textureAtlas.getTexture("cell_adult");
        else if (age >= i * 2)
            return Simulation.textureAtlas.getTexture("cell_jugendlich");
        else if (age >= i)
            return Simulation.textureAtlas.getTexture("cell_kind");
        else return Simulation.textureAtlas.getTexture("cell_baby");
    }

}
