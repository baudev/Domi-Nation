package helpers;

import java.util.Random;

public class Function {

    /**
     * Generate a random int
     * @param min
     * @param max
     * @return
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

}
