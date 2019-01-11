package views.templates;

import exceptions.InvalidDominoPosition;
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

/**
 * JavaFX view of a {@link Board}.
 */
public class BoardView extends Parent {

    private Board board;
    private List<Rectangle> possiblePositions;
    private OnPossibilityClickListener onPossibilityClickListener;

    /**
     * Generates the view of a {@link Board}.
     * @param board {@link Board} owner of the view.
     * @see Board
     */
    public BoardView(Board board) {
        this.board = board;
        this.possiblePositions = new ArrayList<>();
        showStartTile();
        showCastle();
    }

    /**
     * Shows the {@link models.classes.StartTile} of the {@link Board}.
     */
    private void showStartTile() {
        LandPortionView startTileView = this.getBoard().getStartTile().getLandPortionView();
        this.getChildren().add(startTileView);
        startTileView.setTranslateX(Screen.percentageToXDimension(3) * (this.getBoard().getStartTile().getPosition().getX() - 1));
        startTileView.setTranslateY(Screen.percentageToXDimension(3) * (this.getBoard().getStartTile().getPosition().getY() - 1));
        startTileView.toFront();
    }

    /**
     * Shows the {@link models.classes.Castle} of the {@link Board}.
     */
    private void showCastle() {
        CastleView castleView = this.getBoard().getCastle().getCastleView();
        this.getChildren().add(castleView);
        castleView.setTranslateX(Screen.percentageToXDimension(3) * (this.getBoard().getStartTile().getPosition().getX() - 1));
        castleView.setTranslateY(Screen.percentageToXDimension(3) * (this.getBoard().getStartTile().getPosition().getY() - 1));
        castleView.toFront();
    }

    /**
     * Shows a possible {@link Position} for a {@link Domino} according to the other ones on the {@link Board}.
     * @param position1 {@link Position} of the first {@link models.classes.LandPortion} of the {@link Domino}.
     * @param position2 {@link Position} of the second {@link models.classes.LandPortion} of the {@link Domino}.
     * @see Board#getPossibilities(Domino)
     */
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
                try {
                    getOnPossibilityClickListener().onPossibilityClickListener(position1, position2);
                } catch (InvalidDominoPosition invalidDominoPosition) {
                    invalidDominoPosition.printStackTrace();
                }
            }
        });
        this.getPossiblePositions().add(rectangle);
        this.getChildren().add(rectangle);
    }

    /**
     * Removes all possible {@link Position}s.
     * @see #addPossibility(Position, Position)
     */
    public void removeAllPossibilities() {
        for(Rectangle rectangle : this.getPossiblePositions()) {
            this.getChildren().remove(rectangle);
        }
    }

    /**
     * Adds a {@link Domino} to the {@link Board} view.
     * @param domino {@link Domino} to be added.
     */
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

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    /**
     * Returns the {@link Board} of the view.
     * @return {@link Board} owner of the view.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Sets the {@link Board} to the view.
     * @param board {@link Board} to be linked to the view.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * {@link List} of {@link Rectangle}s which are generated for showing the possible {@link Position}s.
     * @return {@link List} of {@link Rectangle}s which are generated for showing the possible {@link Position}s.
     */
    public List<Rectangle> getPossiblePositions() {
        return possiblePositions;
    }

    /**
     * Sets the {@link List} of {@link Rectangle}s which are generated for showing the possible {@link Position}s.
     * @param possiblePositions {@link List} of {@link Rectangle}s to be set.
     */
    public void setPossiblePositions(List<Rectangle> possiblePositions) {
        this.possiblePositions = possiblePositions;
    }

    /**
     * Gets the callback of the OnPossibilityClickListener.
     * @return Callback of the OnPossibilityClickListener
     * @see OnPossibilityClickListener
     */
    public OnPossibilityClickListener getOnPossibilityClickListener() {
        return onPossibilityClickListener;
    }

    /**
     * Sets the callback of the OnPossibilityClickListener.
     * @param onPossibilityClickListener The callback of the OnPossibilityClickListener.
     */
    public void setOnPossibilityClickListener(OnPossibilityClickListener onPossibilityClickListener) {
        this.onPossibilityClickListener = onPossibilityClickListener;
    }
}
