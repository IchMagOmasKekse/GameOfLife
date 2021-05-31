package de.IchMagOmasKekse;

import java.util.concurrent.ThreadLocalRandom;

public class Numbers {

    public static int randomInteger(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
