package models.classes;

import views.templates.DominoesListView;

import java.util.*;

/**
 * {@link Domino} list.
 * It includes additional methods compared to a traditional {@link List}.
 * @see #sortByNumber()
 */
public class DominoesList extends ArrayList<Domino> {

    private DominoesListView dominoesListView;

    /**
     * Generates an empty {@link Domino} list.
     */
    public DominoesList() {

    }

    /**
     * Generates a {@link Domino} list from the listToAdd parameter.
     * @param listToAdd {@link List<Domino>} to add.
     */
    public DominoesList(List<Domino> listToAdd) {
        this.addAll(listToAdd);
    }

    /**
     * Sort all Dominoes by their numbers
     * @return  A {@link DominoesList} sorted by the number of each {@link Domino}
     * @see Domino#getNumber()
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

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    /**
     * Returns the view associated to the {@link DominoesList}.
     * @return  if the view was not created, a new instance of {@link DominoesListView}
     *          if the view was already created, the associated instance of {@link DominoesListView}
     * @see DominoesListView
     */
    public DominoesListView getDominoesListView() {
        if(dominoesListView == null) {
            this.setDominoesListView(new DominoesListView(this));
        }
        return dominoesListView;
    }

    /**
     * Associates a {@link DominoesListView} to the {@link DominoesList}.
     * @param dominoesListView {@link DominoesListView} to be associated to the {@link DominoesList}
     */
    public void setDominoesListView(DominoesListView dominoesListView) {
        this.dominoesListView = dominoesListView;
    }
}
