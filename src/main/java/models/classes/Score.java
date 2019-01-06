package models.classes;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Score {

    public static Map<Player, Integer> getScores(Game game) {
        Map scores = new HashMap();
        for (Player player : game.getPlayers()) {
            scores.put(player, playerScore(player));
        }
        return scores;
    }

    public static int getScores(Player player) {
        if(player != null) {
            return playerScore(player);
        } else {
            return 0;
        }
    }

    private static int playerScore(Player player) {
        int total = 0;
        int increment = 0;
        List<LandPortion> landPortionList = new ArrayList<>();
        while (increment != player.getBoard().getDominoes().size()) {
            if(player.getBoard().getDominoes().get(increment) != null) {
                if(player.getBoard().getDominoes().get(increment).getLeftPortion().getPosition() != null) {
                    if (!landPortionList.contains(player.getBoard().getDominoes().get(increment).getLeftPortion())) {
                        total += getGroupScore(player.getBoard().getDominoes().get(increment).getLeftPortion(), landPortionList, player.getBoard());
                    }
                }
                if(player.getBoard().getDominoes().get(increment).getRightPortion().getPosition() != null) {
                    if (!landPortionList.contains(player.getBoard().getDominoes().get(increment).getRightPortion())) {
                        total += getGroupScore(player.getBoard().getDominoes().get(increment).getRightPortion(), landPortionList, player.getBoard());
                    }
                }
            }
            increment++;
        }
        return total;
    }

    private static int getGroupScore(LandPortion landPortion, List<LandPortion> landPortionList, Board board) {
        int crownsNumber = 0;
        List<LandPortion> tempList = new ArrayList<>();
        getGroupNeighbors(landPortion, tempList, board);
        for(LandPortion landPortionIter : tempList) {
            crownsNumber += landPortionIter.getNumberCrowns();
        }
        landPortionList.addAll(tempList);
        return crownsNumber * tempList.size();
    }

    private static void getGroupNeighbors(LandPortion landPortion, List<LandPortion> landPortionList, Board board) {
        landPortionList.add(landPortion);
        LandPortion leftNeighbor = board.getLandPortion(new Position(landPortion.getPosition().getX() - 1, landPortion.getPosition().getY()));
        LandPortion rightNeighbor = board.getLandPortion(new Position(landPortion.getPosition().getX() + 1, landPortion.getPosition().getY()));
        LandPortion upNeighbor = board.getLandPortion(new Position(landPortion.getPosition().getX(), landPortion.getPosition().getY() + 1));
        LandPortion lowNeighbor = board.getLandPortion(new Position(landPortion.getPosition().getX(), landPortion.getPosition().getY() - 1));

        if(leftNeighbor != null) {
            if(leftNeighbor.getLandPortionType() == landPortion.getLandPortionType() && !landPortionList.contains(leftNeighbor)) {
                getGroupNeighbors(leftNeighbor, landPortionList, board);
            }
        }
        if(rightNeighbor != null) {
            if(rightNeighbor.getLandPortionType() == landPortion.getLandPortionType() && !landPortionList.contains(rightNeighbor)) {
                getGroupNeighbors(rightNeighbor, landPortionList, board);
            }
        }
        if(upNeighbor != null) {
            if(upNeighbor.getLandPortionType() == landPortion.getLandPortionType() && !landPortionList.contains(upNeighbor)) {
                getGroupNeighbors(upNeighbor, landPortionList, board);
            }
        }
        if(lowNeighbor != null) {
            if(lowNeighbor.getLandPortionType() == landPortion.getLandPortionType() && !landPortionList.contains(lowNeighbor)) {
                getGroupNeighbors(lowNeighbor, landPortionList, board);
            }
        }
    }

    private static int bonusWithMode(Game game, Player player) {
        switch (game.getGameMode()) {
            case MIDDLEKINGDOM:
                return middleKingdomBonus(player);
            case HARMOMY:
                return harmonyBonus(player);
        }
        return 0;
    }

    private static int middleKingdomBonus(Player player) {
        int distanceRight = player.getBoard().mostRightPosition().getX() - player.getBoard().getCastle().getPosition().getX();
        int distanceLeft = player.getBoard().getCastle().getPosition().getX() - player.getBoard().mostLeftPosition().getX();
        int distanceUp = player.getBoard().upperPosition().getY() - player.getBoard().getCastle().getPosition().getY();
        int distanceLow = player.getBoard().getCastle().getPosition().getY() - player.getBoard().lowerPosition().getY();
        if(distanceLeft == distanceRight && distanceUp == distanceLow) {
            return 10;
        }
        return 0;
    }

    private static int harmonyBonus(Player player) {
        if(player.getBoard().getDominoes().size() == 12) { // the board is full
            return 5;
        }
        return 0;
    }


}
