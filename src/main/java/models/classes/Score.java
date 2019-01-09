package models.classes;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is used to get the winner and associated score of each {@link Player} of a {@link Game}.
 */
public class Score {

    /**
     * Returns list of {@link Player}s with their scores.
     * @param game  {@link Game} that must be scored.
     * @return  {@link Map} containing {@link Player} and their associated score.
     */
    public static Map<Player, Integer> getScores(Game game) {
        Map scores = new HashMap();
        for (Player player : game.getPlayers()) {
            scores.put(player, playerScore(player));
        }
        return scores;
    }


    /**
     * Returns the score of a {@link Player}.
     * @param player {@link Player} whose score must be returned. Player must not be null.
     * @return  Score of the player passed as parameter.
     */
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

    /**
     * Calculate the total of points obtained from the different {@link Player}'s {@link LandPortion}s groups.
     * @param landPortion  The {@link LandPortion} from where the recursive method start.
     * @param landPortionList   The memory {@link List} which stores all already scored {@link LandPortion}.
     * @param board The {@link Board} that must be scored.
     * @return  The number of points accumulated by the different {@link Player}'s {@link LandPortion}s groups.
     */
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

    /**
     * Completes the {@link List} by adding {@link LandPortion} of the same groups. This method is recursively collecting any landPortion neighbors with the same {@link models.enums.LandPortionType}.
     * @param landPortion {@link LandPortion} from which we are searching his {@link LandPortion} group.
     * @param landPortionList   {@link List} which is completed with the {@link LandPortion} of the same group.
     * @param board {@link Board} which is scored.
     */
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

    /**
     * Returns the bonus associated to each particular {@link models.enums.GameMode}.
     * @param game  {@link Game} which is currently scored.
     * @param player    {@link Player} whose we are calculating score.
     * @return  The number of points obtained thanks to the bonus {@link models.enums.GameMode}.
     */
    private static int bonusWithMode(Game game, Player player) {
        switch (game.getGameMode()) {
            case MIDDLEKINGDOM:
                return middleKingdomBonus(player);
            case HARMOMY:
                return harmonyBonus(player);
        }
        return 0;
    }

    /**
     * Gets the number of points given by the Middle Kingdom bonus.
     * @param player    {@link Player} who is scored.
     * @return  The number of points given by the Middle Kingdom bonus.
     */
    private static int middleKingdomBonus(Player player) {
        int distanceRight = player.getBoard().mostRightPosition().getX() - player.getBoard().getCastle().getPosition().getX();
        int distanceLeft = player.getBoard().getCastle().getPosition().getX() - player.getBoard().mostLeftPosition().getX();
        int distanceUp = player.getBoard().highestPosition().getY() - player.getBoard().getCastle().getPosition().getY();
        int distanceLow = player.getBoard().getCastle().getPosition().getY() - player.getBoard().lowerPosition().getY();
        if(distanceLeft == distanceRight && distanceUp == distanceLow) {
            return 10;
        }
        return 0;
    }

    /**
     * Gets the number of points given by the Harmony bonus.
     * @param player {@link Player} who is scored.
     * @return  The number of points given by the Harmony bonus.
     */
    private static int harmonyBonus(Player player) {
        if(player.getBoard().getDominoes().size() == 12) { // the board is full
            return 5;
        }
        return 0;
    }


}
