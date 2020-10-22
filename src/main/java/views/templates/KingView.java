package views.templates;

import helpers.Screen;
import javafx.scene.Parent;
import javafx.scene.shape.Circle;
import models.classes.King;

/**
 * JavaFX view of a {@link King}.
 */
public class KingView extends Parent {

    private King king;

    /**
     * Generates the view of the castle.
     * @param king {@link King} owner of the view.
     */
    public KingView(King king) {
        this.king = king;
        generateKing();
    }

    /**
     * Generates the view of the {@link King}.
     */
    private void generateKing() {
        Circle circle = new Circle();
        circle.setFill(this.king.getColor().getValue());
        circle.setRadius(Screen.percentageToXDimension(0.5));
        circle.setCenterX(Screen.percentageToXDimension(0.6));
        circle.setCenterY(Screen.percentageToYDimension(1.0));
        this.getChildren().add(circle);
    }

}
