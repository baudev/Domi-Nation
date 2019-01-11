package views.templates;

import helpers.Screen;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
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
        this.getRectangleNumber().setWidth(Screen.percentageToXDimension(6));
        this.getRectangleNumber().setHeight(Screen.percentageToXDimension(3));
        this.getChildren().add(this.getRectangleNumber());
        this.setTextNumber(new Text());
        this.getTextNumber().setText(String.valueOf(domino.getNumber()));
        this.getTextNumber().setFill(Color.WHITE);
        this.getTextNumber().setStyle("-fx-font: " + Screen.percentageToXDimension(1.5) + " arial;");
        this.getChildren().add(this.getTextNumber());
        this.getTextNumber().setX(Screen.percentageToXDimension(3) - this.getTextNumber().getBoundsInParent().getWidth());
        this.getTextNumber().setY(Screen.percentageToXDimension(1.5) + this.getTextNumber().getBoundsInParent().getHeight() / 2);
    }



    /**
     * Show the Portions face of the domino
     */
    public void showPortionsFace() {
        this.hideNumberFace(); // we hide the number Face
        this.getDomino().getLeftPortion().getLandPortionView().setOpacity(1.0);
        this.getDomino().getRightPortion().getLandPortionView().setOpacity(1.0);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2700),getRectangleNumber());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        FadeTransition fadeTransition1 = new FadeTransition((Duration.millis(2700)),getTextNumber());
        fadeTransition1.setFromValue(1.0);
        fadeTransition1.setToValue(0);
        fadeTransition1.play();
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
     * Add KingView to the DominoView
     */
    public void addKing() {
        if(this.getDomino().getKing() != null) {
            // the add the king on the view
            KingView kingView = this.getDomino().getKing().getKingView();
            this.getChildren().add(kingView);
            kingView.setTranslateX(Screen.percentageToXDimension(3) - kingView.getBoundsInParent().getWidth() / 2);
            kingView.setTranslateY(Screen.percentageToXDimension(1.5) - kingView.getBoundsInParent().getHeight() / 2);
        }
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
