package models.classes;


import helpers.Function;

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
            scores.put(player, playerScore(player) + bonusWithMode(game, player));
        }
        return scores;
    }

    /**
     * Returns the winner of the {@link Game}.
     * @param game {@link Game} which we are searching the winner(s).
     * @return {@link List} of {@link Player}s who are the winners.
     */
    public static List<Player> getWinner(Game game) {
        List<Player> winners = new ArrayList<>();
        Map<Player, Integer> scores = Score.getScores(game);
        Map<Player, Integer> equalityPlayers = Function.getDuplicated(scores);
        if(equalityPlayers.size() < 1) { // there is only one winner with an higher score than the others
            winners.add(Function.indexWithHigherValue(equalityPlayers).getKey());
            return winners;
        } else {
            Map<Player, Integer> maxDomainSizePlayers = getMaxDomainSize(game);
            equalityPlayers = Function.getDuplicated(maxDomainSizePlayers);
            if(equalityPlayers.size() < 1) {
                winners.add(Function.indexWithHigherValue(equalityPlayers).getKey()); // one player has a larger domain compared to others
                return winners;
            } else {
                Map<Player, Integer> numberCrownsPlayers = geNumberCrowns(game);
                equalityPlayers = Function.getDuplicated(numberCrownsPlayers);
                if(equalityPlayers.size() < 1) {
                    winners.add(Function.indexWithHigherValue(equalityPlayers).getKey()); // one player has more crowns than the others
                    return winners;
                } else { // many players are winners
                    for(Map.Entry<Player, Integer> entry : equalityPlayers.entrySet()) {
                        winners.add(entry.getKey());
                    }
                    return winners;
                }
            }
        }
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


    /**
     * Returns list of {@link Player}s with their max domain size.
     * @param game  {@link Game} that must be scored.
     * @return  {@link Map} containing {@link Player} and their associated max domain size.
     */
    private static Map<Player, Integer> getMaxDomainSize(Game game) {
        Map scores = new HashMap();
        for (Player player : game.getPlayers()) {
            scores.put(player, playerLargerDomain(player));
        }
        return scores;
    }

    /**
     * Returns the larger domain size of the player.
     * @param player {@link Player} whose we are calculating the size of his larger domain.
     * @return  The larger domain size of the {@link Player}.
     */
    private static int playerLargerDomain(Player player) {
        int total = 0;
        int increment = 0;
        List<LandPortion> landPortionList = new ArrayList<>();
        while (increment != player.getBoard().getDominoes().size()) {
            if(player.getBoard().getDominoes().get(increment) != null) {
                if(player.getBoard().getDominoes().get(increment).getLeftPortion().getPosition() != null) {
                    if (!landPortionList.contains(player.getBoard().getDominoes().get(increment).getLeftPortion())) {
                        total += getGroupSize(player.getBoard().getDominoes().get(increment).getLeftPortion(), landPortionList, player.getBoard());
                    }
                }
                if(player.getBoard().getDominoes().get(increment).getRightPortion().getPosition() != null) {
                    if (!landPortionList.contains(player.getBoard().getDominoes().get(increment).getRightPortion())) {
                        total += getGroupSize(player.getBoard().getDominoes().get(increment).getRightPortion(), landPortionList, player.getBoard());
                    }
                }
            }
            increment++;
        }
        return total;
    }

    /**
     * Returns the size of a {@link LandPortion} group.
     * @param landPortion  The {@link LandPortion} from where the recursive method start.
     * @param landPortionList   The memory {@link List} which stores all already calculated {@link LandPortion}.
     * @param board The {@link Board} that must be calculated.
     * @return
     */
    private static int getGroupSize(LandPortion landPortion, List<LandPortion> landPortionList, Board board) {
        List<LandPortion> tempList = new ArrayList<>();
        getGroupNeighbors(landPortion, tempList, board);
        return tempList.size();
    }

    /**
     * Returns list of {@link Player}s with their number of crowns.
     * @param game  {@link Game} that must be scored.
     * @return  {@link Map} containing {@link Player} and their associated number of crowns.
     */
    private static Map<Player, Integer> geNumberCrowns(Game game) {
        Map scores = new HashMap();
        for (Player player : game.getPlayers()) {
            scores.put(player, getNumberCrownsOfPlayer(player));
        }
        return scores;
    }

    /**
     * Returns the number of crowns that has the player.
     * @param player    {@link Player} whose we are counting his crowns.
     * @return  The number of crowns that has the player.
     */
    private static int getNumberCrownsOfPlayer(Player player) {
        int numberCrowns = 0;
        for(Domino domino : player.getBoard().getDominoes()) {
            numberCrowns += domino.getLeftPortion().getNumberCrowns();
            numberCrowns += domino.getRightPortion().getNumberCrowns();
        }
        return numberCrowns;
    }


}
