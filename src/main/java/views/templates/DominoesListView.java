package views.templates;

import helpers.Screen;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import models.classes.Domino;
import models.classes.DominoesList;
import models.enums.Rotation;

public class DominoesListView extends Parent {

    private DominoesList dominoesList;

    public DominoesListView(DominoesList dominoesList) {
        this.dominoesList = dominoesList;
        generateDominoesList();
        center();
    }

    private void generateDominoesList() {
        for(int i = 0; i < this.dominoesList.size(); i++) {
            DominoView dominoView = this.dominoesList.get(i).getDominoView();
            dominoView.setTranslateX(Screen.percentageToXDimension(8.5 * i));
            this.getChildren().add(dominoView);
        }
    }

    public void showPortionsFaces() {
        for(int i = 0; i < this.dominoesList.size(); i++) {
            DominoView dominoView = this.dominoesList.get(i).getDominoView();
            dominoView.showPortionsFace();
        }
    }

    public void showNumberFaces() {
        for(int i = 0; i < this.dominoesList.size(); i++) {
            DominoView dominoView = this.dominoesList.get(i).getDominoView();
            dominoView.showNumberFace();
        }
    }

    public void center() {
        this.setTranslateX(Screen.percentageToXDimension(50 - 8.5 * dominoesList.size() / 2));
        this.setTranslateY(Screen.percentageToYDimension(50 - 5));
    }

    public void setLowerPosition() {
        this.setTranslateY(Screen.percentageToYDimension(50 - 5 - 4));
    }

    public void setUpperPosition() {
        this.setTranslateY(Screen.percentageToYDimension(50 - 5 + 4));
    }
}
