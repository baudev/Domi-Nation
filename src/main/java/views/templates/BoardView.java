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
import models.classes.StartTile;

import java.util.ArrayList;
import java.util.List;


public class BoardView extends Parent {

    private Board board;
    private List<Rectangle> possiblePositions;

    public BoardView(Board board) {
        this.board = board;
        this.possiblePositions = new ArrayList<>();
        generateBoardView();
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
        // TODO
    }

    private void generateBoardView() {
        List<Position> positionList = this.getBoard().calculateMaxGridSize();
        for(Position position : positionList) {
            Rectangle rectangle = new Rectangle();
            rectangle.setFill(Color.RED);
            rectangle.setWidth(Screen.percentageToXDimension(3));
            rectangle.setHeight(Screen.percentageToXDimension(3));
            rectangle.setTranslateX(Screen.percentageToXDimension(3) * (position.getX() - 1));
            rectangle.setTranslateY(Screen.percentageToXDimension(3) * (position.getY() - 1));
            this.getChildren().add(rectangle);
        }
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
        this.getPossiblePositions().add(rectangle);
        this.getChildren().add(rectangle);
    }

    public void removeAllPossibilities() {
        for(Rectangle rectangle : this.getPossiblePositions()) {
            this.getChildren().remove(rectangle);
        }
    }

    public void addDomino(Domino domino) {
        // TODO
        this.getChildren().add(domino.getDominoView());
        domino.getDominoView().setTranslateX(Screen.percentageToXDimension(3) * (domino.getLeftPortion().getPosition().getX() - 1));
        domino.getDominoView().setTranslateY(Screen.percentageToXDimension(3) * (domino.getLeftPortion().getPosition().getY() - 1));
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
}
