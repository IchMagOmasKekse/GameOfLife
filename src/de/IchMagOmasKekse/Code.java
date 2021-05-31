package de.IchMagOmasKekse;

import java.util.Random;

public class Code {

    public static final String seperator = "-";
    public static final Random random = new Random();

    /* Generiert einen zufÃ¤lligen Code, welcher beliebig anpassbar ist */
    public static String getRandomCode(int blocks, int blocksize) {
        if(blocks < 1) blocks = 1;
        if(blocksize < 2) blocksize = 2;

        StringBuilder link = new StringBuilder();

        for(int i = 0; i != blocks; i++) {
            for(int ii = 0; ii != blocksize; ii++) {
                if(random.nextBoolean()) {
                    //Buchstabe
                    if(random.nextBoolean()) {
                        //Großbuchstabe
                        switch(random.nextInt(26)) {
                            case  0: link.append("A"); break;
                            case  1: link.append("B"); break;
                            case  2: link.append("C"); break;
                            case  3: link.append("D"); break;
                            case  4: link.append("E"); break;
                            case  5: link.append("F"); break;
                            case  6: link.append("G"); break;
                            case  7: link.append("H"); break;
                            case  8: link.append("I"); break;
                            case  9: link.append("J"); break;
                            case 10: link.append("K"); break;
                            case 11: link.append("L"); break;
                            case 12: link.append("M"); break;
                            case 13: link.append("N"); break;
                            case 14: link.append("O"); break;
                            case 15: link.append("P"); break;
                            case 16: link.append("Q"); break;
                            case 17: link.append("R"); break;
                            case 18: link.append("S"); break;
                            case 19: link.append("T"); break;
                            case 20: link.append("U"); break;
                            case 21: link.append("V"); break;
                            case 22: link.append("W"); break;
                            case 23: link.append("X"); break;
                            case 24: link.append("Y"); break;
                            case 25: link.append("Z"); break;
                        }
                    }else {
                        switch(random.nextInt(26)) {
                            case  0: link.append("a"); break;
                            case  1: link.append("b"); break;
                            case  2: link.append("c"); break;
                            case  3: link.append("d"); break;
                            case  4: link.append("e"); break;
                            case  5: link.append("f"); break;
                            case  6: link.append("g"); break;
                            case  7: link.append("h"); break;
                            case  8: link.append("i"); break;
                            case  9: link.append("j"); break;
                            case 10: link.append("k"); break;
                            case 11: link.append("l"); break;
                            case 12: link.append("m"); break;
                            case 13: link.append("n"); break;
                            case 14: link.append("o"); break;
                            case 15: link.append("p"); break;
                            case 16: link.append("q"); break;
                            case 17: link.append("r"); break;
                            case 18: link.append("s"); break;
                            case 19: link.append("t"); break;
                            case 20: link.append("u"); break;
                            case 21: link.append("v"); break;
                            case 22: link.append("w"); break;
                            case 23: link.append("x"); break;
                            case 24: link.append("y"); break;
                            case 25: link.append("z"); break;
                        }
                        //Kleinbuchstabe

                    }
                }else {
                    //Zahl
                    link.append(random.nextInt(10));
                }
            }

            if(!(i == (blocks - 1))) link.append(seperator);
        }
        return link.toString();
    }

}
