package views.templates;

import helpers.Screen;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import models.classes.LandPortion;


public class LandPortionView extends Parent {

    private LandPortion landPortion;

    public LandPortionView(LandPortion landPortion) {
        this.setLandPortion(landPortion);
        addPortionType();
        addCrowns();
    }

    /**
     * Add color corresponding to the PortionType
     */
    private void addPortionType() {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(landPortion.getLandPortionType().getValue()); // set color
        rectangle.setWidth(Screen.percentageToXDimension(4));
        rectangle.setHeight(Screen.percentageToYDimension(5));
        this.getChildren().add(rectangle);
    }

    /**
     * Add corresponding number of crowns to the LandPortion
     */
    private void addCrowns() {
        for(int i = 0; i < landPortion.getNumberCrowns(); i++) {
            Circle circle = new Circle();
            circle.setFill(Color.GREENYELLOW);
            circle.setRadius(Screen.percentageToXDimension(0.5));
            circle.setCenterX(Screen.percentageToXDimension(1.1*i + 0.6));
            circle.setCenterY(Screen.percentageToYDimension(1));
            this.getChildren().add(circle);
        }
    }

    /**
     *
     *  GETTERS AND SETTERs
     *
     */

    public LandPortion getLandPortion() {
        return landPortion;
    }

    public void setLandPortion(LandPortion landPortion) {
        this.landPortion = landPortion;
    }
}
