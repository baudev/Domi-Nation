package helpers;

import java.util.HashMap;
import java.util.Map;
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

    /**
     * Returns the duplicated (with the same value) entries of mapToAnalyse.
     * @param mapToAnalyse {@link Map} whose the duplicated entries are searched.
     * @param <K>   Key of the {@link Map}.
     * @param <V>   Value of the {@link Map}. If the value of one entry is equal to another, the entry is considered as a duplicated.
     * @return  The duplicated (with the same value) entries of mapToAnalyse.
     */
    public static <K, V> Map<K, V> getDuplicated(Map<K, V> mapToAnalyse) {
        Map<K, V> equality = new HashMap<>();
        for(Map.Entry<K, V> entry : mapToAnalyse.entrySet()) {
            for(Map.Entry<K, V> entry2 : mapToAnalyse.entrySet()) {
                if(entry.getKey() != entry2.getKey()) {
                    if(entry.getValue() == entry2.getValue()) {
                        if(!equality.containsKey(entry.getKey())) {
                            equality.put(entry.getKey(), entry.getValue());
                        }
                        if(!equality.containsKey(entry2.getKey())) {
                            equality.put(entry2.getKey(), entry2.getValue());
                        }
                    }
                }
            }
        }
        return equality;
    }

    /**
     * Return the {@link Map.Entry<K, Integer>} with the higher value.
     * @param map   {@link Map.Entry<K, Integer>} to be analysed.
     * @param <K>   Index of the {@link Map.Entry}s
     * @return  The {@link Map.Entry<K, Integer>} with the higher value.
     */
    public static <K> Map.Entry<K, Integer> indexWithHigherValue(Map<K,Integer> map) {
        Map.Entry<K, Integer> maxEntry = null;

        for (Map.Entry<K, Integer> entry : map.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }

}
