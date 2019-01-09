package views.templates;

import helpers.Screen;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import models.classes.LandPortion;
import models.enums.LandPortionType;


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
        rectangle.setWidth(Screen.percentageToXDimension(3));
        rectangle.setHeight(Screen.percentageToXDimension(3));
        if(landPortion.getLandPortionType() != LandPortionType.TOUS) {
            Image background = new Image("/tiles/" + this.landPortion.getLandPortionType().toString().toLowerCase() + ".png");
            rectangle.setFill(new ImagePattern(background));
        }
        this.getChildren().add(rectangle);
    }

    /**
     * Add corresponding number of crowns to the LandPortion
     */
    private void addCrowns() {
        for(int i = 0; i < landPortion.getNumberCrowns(); i++) {
            Circle circle = new Circle();
            circle.setFill(Color.GREENYELLOW);
            circle.setRadius(Screen.percentageToXDimension(0.3));
            circle.setCenterX(Screen.percentageToXDimension(1.1*i + 0.4));
            circle.setCenterY(Screen.percentageToXDimension(0.3));
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
