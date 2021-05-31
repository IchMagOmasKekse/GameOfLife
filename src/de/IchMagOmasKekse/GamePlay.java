package de.IchMagOmasKekse;

public class GamePlay {

    public static double score = 0;

    public static enum SpeedType {
        INSANE(0, 3, "IRRE SCHNELL"),
        ULTRA(4, 10,"Ultra"),
        FAST(11, 20, "Schnell"),
        NORMAL(21, 30, "Normal"),
        SLOW(31, 40, "Langsam"),
        SLOWER(41, 50, "Noch langsamer"),
        SLUG_SPEED(51,60, "Schneckentempo...");

        int min, max;
        String name;

        SpeedType(int min, int max, String name) {
            this.min = min;
            this.max = max;
            this.name = name;
        }

        public int getMax() {
            return max;
        }

        public int getMin() {
            return min;
        }

        public String getName() {
            return name;
        }
    }

}
