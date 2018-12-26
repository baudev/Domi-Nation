package views.templates;

import helpers.Screen;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import models.classes.Domino;
import views.interfaces.OnDominoClickListener;

public class DominoView extends Parent {

    private Domino domino;
    private Text textNumber;
    private Rectangle rectangleNumber;

    private OnDominoClickListener onDominoClickListener;

    public DominoView(Domino domino) {
        this.setDomino(domino);
        generatePortionsFace();
        generateNumberFace();
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getOnDominoClickListener().onDominoClickListener(domino); // onClick the domino
            }
        });
    }

    /**
     * Generate shapes for the domino Portions face
     */
    private void generatePortionsFace() {
        LandPortionView leftLandPortionView = this.getDomino().getLeftPortion().getLandPortionView();
        LandPortionView rightLandPortionView = this.getDomino().getRightPortion().getLandPortionView();
        rightLandPortionView.setTranslateX(leftLandPortionView.getBoundsInParent().getWidth());
        this.getChildren().add(leftLandPortionView);
        this.getChildren().add(rightLandPortionView);
    }

    /**
     * Generate shapes for the domino number face
     */
    private void generateNumberFace() {
        this.setRectangleNumber(new Rectangle());
        this.getRectangleNumber().setFill(Color.BLACK); // set color
        this.getRectangleNumber().setWidth(Screen.percentageToXDimension(8));
        this.getRectangleNumber().setHeight(Screen.percentageToYDimension(5));
        this.getChildren().add(this.getRectangleNumber());
        this.setTextNumber(new Text());
        this.getTextNumber().setText(String.valueOf(domino.getNumber()));
        this.getTextNumber().setFill(Color.WHITE);
        this.getTextNumber().setStyle("-fx-font: " + Screen.percentageToXDimension(1.5) + " arial;");
        this.getTextNumber().setX(Screen.percentageToXDimension(3.5));
        this.getTextNumber().setY(Screen.percentageToYDimension(3.5));
        this.getChildren().add(this.getTextNumber());
    }

    // TODO Make animations for the following showing/hiding methods

    /**
     * Show the Portions face of the domino
     */
    public void showPortionsFace() {
        this.hideNumberFace(); // we hide the number Face
        this.getDomino().getLeftPortion().getLandPortionView().setOpacity(1.0);
        this.getDomino().getRightPortion().getLandPortionView().setOpacity(1.0);
    }

    /**
     * Show the number face of the domino
     */
    public void showNumberFace() {
        this.hidePortionsFace(); // we hide the portions face
        this.getRectangleNumber().setOpacity(1.0);
        this.getTextNumber().setOpacity(1.0);
    }

    /**
     * Hide the domino number face
     */
    private void hideNumberFace() {
        this.getRectangleNumber().setOpacity(0); // we hide the black rectangle
        this.getTextNumber().setOpacity(0); // and the number
    }

    /**
     * Hide the Portions face of the domino
     */
    private void hidePortionsFace() {
        this.getDomino().getLeftPortion().getLandPortionView().setOpacity(0);
        this.getDomino().getRightPortion().getLandPortionView().setOpacity(0);
    }


    /**
     *
     *  GETTERS AND SETTERS
     *
     */

    public Domino getDomino() {
        return domino;
    }

    public void setDomino(Domino domino) {
        this.domino = domino;
    }

    public Text getTextNumber() {
        return textNumber;
    }

    public void setTextNumber(Text textNumber) {
        this.textNumber = textNumber;
    }

    public Rectangle getRectangleNumber() {
        return rectangleNumber;
    }

    public void setRectangleNumber(Rectangle rectangleNumber) {
        this.rectangleNumber = rectangleNumber;
    }

    public OnDominoClickListener getOnDominoClickListener() {
        return onDominoClickListener;
    }

    public void setOnDominoClickListener(OnDominoClickListener onDominoClickListener) {
        this.onDominoClickListener = onDominoClickListener;
    }
}
