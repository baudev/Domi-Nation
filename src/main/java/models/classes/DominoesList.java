package models.classes;

import java.util.*;

public class DominoesList extends ArrayList<Domino> {

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

}
