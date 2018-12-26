package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import models.enums.LandPortionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DominoesListTest {

    @Test
    void shouldReturnDominoesSortByTheirNumber() throws MaxCrownsLandPortionExceeded {
        Domino domino1 = new Domino(new LandPortion(1, LandPortionType.CHAMPS), new LandPortion(1, LandPortionType.MINE), 1);
        Domino domino2 = new Domino(new LandPortion(1, LandPortionType.CHAMPS), new LandPortion(1, LandPortionType.MINE), 3);
        Domino domino3 = new Domino(new LandPortion(1, LandPortionType.CHAMPS), new LandPortion(1, LandPortionType.MINE), 3); // with the same number
        DominoesList dominoesList = new DominoesList();
        dominoesList.add(domino2);
        dominoesList.add(domino3);
        dominoesList.add(domino1);

        dominoesList.sortByNumber();

        assertSame(domino1, dominoesList.get(0));
        assertSame(domino2, dominoesList.get(1));
    }

}