package models.classes;

import views.templates.DominoesListView;

import java.util.*;

public class DominoesList extends ArrayList<Domino> {

    private DominoesListView dominoesListView;

    public DominoesList() {

    }

    public DominoesList(List<Domino> subList) {
        this.addAll(subList);
    }

    /**
     * Sort all Dominoes by their numbers
     * @return
     */
    public DominoesList sortByNumber() {
        this.sort(new Comparator<Domino>() {
            @Override
            public int compare(Domino o1, Domino o2) {
                if (o1.getNumber() == o2.getNumber()) {
                    return 0;
                }
                return o1.getNumber() < o2.getNumber() ? -1 : 1;
            }
        });
        return this;
    }

    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public DominoesListView getDominoesListView() {
        if(dominoesListView == null) {
            this.setDominoesListView(new DominoesListView(this));
        }
        return dominoesListView;
    }

    public void setDominoesListView(DominoesListView dominoesListView) {
        this.dominoesListView = dominoesListView;
    }
}
