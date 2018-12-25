package helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionTest extends Function {

    @Test
    void randInt() {
        assertTrue(isInteger(Function.randInt(1, 5)));
        assertEquals(2, Function.randInt(2, 2));
    }

    /**
     * Check if the object passed as parameter is an integer
     * @param object
     * @return
     */
    public static boolean isInteger(Object object) {
        if(object instanceof Integer) {
            return true;
        } else {
            String string = object.toString();

            try {
                Integer.parseInt(string);
            } catch(Exception e) {
                return false;
            }
        }

        return true;
    }
}