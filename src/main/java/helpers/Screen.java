package helpers;

import javafx.geometry.Rectangle2D;

public class Screen {

    private static Rectangle2D primaryScreenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();

    public static double getXMin() {
        return primaryScreenBounds.getMinX();
    }

    public static double getYMin() {
        return primaryScreenBounds.getMinY();
    }

    public static double getXMax() {
        return primaryScreenBounds.getMaxX();
    }

    public static double getYMax() {
        return primaryScreenBounds.getMaxY();
    }

    public static double percentageToXDimension(int percentage) {
        // TODO check that the value is inferior or equal to 100%
        return percentage*(getXMax()-getXMin())/100;
    }

    public static double percentageToYDimension(int percentage) {
        // TODO check that the value is inferior or equal to 100%
        return percentage*(getYMax()-getYMin())/100;
    }

}
