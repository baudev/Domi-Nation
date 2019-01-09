package views.templates;

import helpers.Screen;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import models.classes.Castle;
import models.enums.LandPortionType;

public class CastleView extends Parent {

    private Castle castle;

    public CastleView(Castle castle) {
        this.castle = castle;
        generateCastle();
    }

    private void generateCastle() {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(Screen.percentageToXDimension(3));
        rectangle.setHeight(Screen.percentageToXDimension(3));
        Image background = new Image("/castles/" + this.castle.getColor().toString().toLowerCase() + ".png");
        rectangle.setFill(new ImagePattern(background));
        this.getChildren().add(rectangle);
    }

}
