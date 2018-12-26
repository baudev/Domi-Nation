package views.templates;

import helpers.Screen;
import javafx.scene.Parent;
import models.classes.DominoesList;

public class DominoesListView extends Parent {

    private DominoesList dominoesList;

    public DominoesListView(DominoesList dominoesList) {
        this.dominoesList = dominoesList;
        generateDominoesList();
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
}
