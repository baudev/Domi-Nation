package helpers;

import java.util.Random;

/**
 * Contains all helpful static functions.
 */
public class Function {

    /**
     * Generates a random int contained in the [min; max] interval.
     * @param min   Minimum value of the interval.
     * @param max   Maximum value of the interval.
     * @return int  Random int contained in the interval defined by the parameters.
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

}
