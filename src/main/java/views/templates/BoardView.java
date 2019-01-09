package views.templates;

import helpers.Screen;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.classes.Board;
import models.classes.Domino;
import models.classes.Position;
import views.interfaces.OnPossibilityClickListener;

import java.util.ArrayList;
import java.util.List;


public class BoardView extends Parent {

    private Board board;
    private List<Rectangle> possiblePositions;
    private OnPossibilityClickListener onPossibilityClickListener;

    public BoardView(Board board) {
        this.board = board;
        this.possiblePositions = new ArrayList<>();
        showStartTile();
        showCastle();
    }

    private void showStartTile() {
        LandPortionView startTileView = this.getBoard().getStartTile().getLandPortionView();
        this.getChildren().add(startTileView);
        startTileView.setTranslateX(Screen.percentageToXDimension(3) * (this.getBoard().getStartTile().getPosition().getX() - 1));
        startTileView.setTranslateY(Screen.percentageToXDimension(3) * (this.getBoard().getStartTile().getPosition().getY() - 1));
        startTileView.toFront();
    }

    private void showCastle() {
        CastleView castleView = this.getBoard().getCastle().getCastleView();
        this.getChildren().add(castleView);
        castleView.setTranslateX(Screen.percentageToXDimension(3) * (this.getBoard().getStartTile().getPosition().getX() - 1));
        castleView.setTranslateY(Screen.percentageToXDimension(3) * (this.getBoard().getStartTile().getPosition().getY() - 1));
        castleView.toFront();
    }

    public void addPossibility(Position position1, Position position2) {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.LIGHTGREEN);
        rectangle.setWidth(Screen.percentageToXDimension(6));
        rectangle.setHeight(Screen.percentageToXDimension(3));
        rectangle.setTranslateX(Screen.percentageToXDimension(3) * (position1.getX() - 1));
        rectangle.setTranslateY(Screen.percentageToXDimension(3) * (position1.getY() - 1));
        if(position1.getX() == position2.getX()) { // vertical
            rectangle.setTranslateX(Screen.percentageToXDimension(3) * (position1.getX() - 1) - Screen.percentageToXDimension(3) / 2);
            rectangle.setTranslateY(Screen.percentageToXDimension(3) * (position1.getY() - 1) - Screen.percentageToXDimension(3) / 2);
            rectangle.setRotate(90);
        }
        rectangle.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rectangle.toFront();
                rectangle.setFill(Color.LIGHTYELLOW);
            }
        });
        rectangle.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rectangle.setFill(Color.LIGHTGREEN);
            }
        });
        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getOnPossibilityClickListener().onPossibilityClickListener(position1, position2);
            }
        });
        this.getPossiblePositions().add(rectangle);
        this.getChildren().add(rectangle);
    }

    public void removeAllPossibilities() {
        for(Rectangle rectangle : this.getPossiblePositions()) {
            this.getChildren().remove(rectangle);
        }
    }

    public void addDomino(Domino domino) {
        DominoView dominoView = domino.getDominoView();
        this.getChildren().add(dominoView);
        switch (domino.getRotation()){
            case NORMAL:
                dominoView.setTranslateX(Screen.percentageToXDimension(3) * (domino.getLeftPortion().getPosition().getX() - 1));
                dominoView.setTranslateY(Screen.percentageToXDimension(3) * (domino.getLeftPortion().getPosition().getY() - 1));
                break;
            case RIGHT:
                dominoView.setTranslateX(Screen.percentageToXDimension(3) * (domino.getLeftPortion().getPosition().getX() - 1) - Screen.percentageToXDimension(3) / 2);
                dominoView.setTranslateY(Screen.percentageToXDimension(3) * (domino.getLeftPortion().getPosition().getY()) - Screen.percentageToXDimension(3) / 2);
                break;
            case LEFT:
                dominoView.setTranslateX(Screen.percentageToXDimension(3) * (domino.getRightPortion().getPosition().getX() - 1) - Screen.percentageToXDimension(3) / 2);
                dominoView.setTranslateY(Screen.percentageToXDimension(3) * (domino.getRightPortion().getPosition().getY() - 1) - Screen.percentageToXDimension(3) / 2);
                break;
            case INVERSE:
                dominoView.setTranslateX(Screen.percentageToXDimension(3) * (domino.getRightPortion().getPosition().getX() - 2));
                dominoView.setTranslateY(Screen.percentageToXDimension(3) * (domino.getRightPortion().getPosition().getY() - 1));
                break;
        }
        dominoView.toFront();
    }

    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Rectangle> getPossiblePositions() {
        return possiblePositions;
    }

    public void setPossiblePositions(List<Rectangle> possiblePositions) {
        this.possiblePositions = possiblePositions;
    }

    public OnPossibilityClickListener getOnPossibilityClickListener() {
        return onPossibilityClickListener;
    }

    public void setOnPossibilityClickListener(OnPossibilityClickListener onPossibilityClickListener) {
        this.onPossibilityClickListener = onPossibilityClickListener;
    }
}
