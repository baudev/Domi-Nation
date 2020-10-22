package helpers;

import javafx.geometry.Rectangle2D;

/**
 * Handles all methods concerning the size of the {@link javafx.stage.Screen}.
 * @see javafx.stage.Screen
 */
public class Screen {

    private static Rectangle2D primaryScreenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();

    /**
     * Returns the minimum x value of the {@link javafx.stage.Screen}.
     * @return Minimum x value.
     */
    public static double getXMin() {
        return primaryScreenBounds.getMinX();
    }

    /**
     * Returns the minimum y value of the {@link javafx.stage.Screen}.
     * @return Minimum y value.
     */
    public static double getYMin() {
        return primaryScreenBounds.getMinY();
    }

    /**
     * Returns the maximum x value of the {@link javafx.stage.Screen}.
     * @return Maximum x value.
     */
    public static double getXMax() {
        return primaryScreenBounds.getMaxX();
    }

    /**
     * Returns the maximum y value of the {@link javafx.stage.Screen}.
     * @return Maximum y value.
     */
    public static double getYMax() {
        return primaryScreenBounds.getMaxY();
    }

    /**
     * Converts a percentage into a x dimension.
     * This method is useful for managing screens of different sizes.
     * @param percentage    Value of which we would like to have the correspondence into x value.
     * @return              Value corresponding to the percentage passed in parameter.
     */
    public static double percentageToXDimension(double percentage) {
        // TODO check that the value is inferior or equal to 100%
        return percentage*(getXMax()-getXMin())/100;
    }

    /**
     * Converts a percentage into a y dimension.
     * This method is useful for managing screens of different sizes.
     * @param percentage    Value of which we would like to have the correspondence into y value.
     * @return              Value corresponding to the percentage passed in parameter.
     */
    public static double percentageToYDimension(double percentage) {
        // TODO check that the value is inferior or equal to 100%
        return percentage*(getYMax()-getYMin())/100;
    }

}
