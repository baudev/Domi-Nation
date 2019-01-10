package models.classes;

import exceptions.*;
import helpers.CSVReader;
import helpers.Function;
import models.enums.GameMode;
import models.enums.PlayerColor;
import models.enums.Response;
import models.enums.Rotation;

import java.io.IOException;
import java.util.*;

/**
 * This manages the entirety of a {@link Game}.
 */
public class Game {

    private DominoesList dominoes;
    private List<DominoesList> pickedDominoes;
    private Domino previousDomino, newDomino;
    private List<Player> players;
    private List<Map<Player, Integer>> playerTurns;
    private int turnNumber;
    private Player currentPlayer;
    private GameMode gameMode;
    private boolean isLastTurn;

    /**
     * Creates a new {@link Game} with any properties.
     */
    public Game() {
        this.setPlayers(new ArrayList<>());
        this.setPlayerTurns(new ArrayList<>());
        this.getPlayerTurns().add(new HashMap<>()); // to complete the index 0
        this.getPlayerTurns().add(new HashMap<>()); // to complete the index 1
        this.setPickedDominoes(new ArrayList<>());
        this.setTurnNumber(1); // first turn start
        this.setLastTurn(false);
    }


    /**
     * Returns all unused {@link PlayerColor}s.
     * @return {@link List} of unused {@link PlayerColor}s.
     */
    public List<PlayerColor> getFreePlayerColors() {
        // we add all possible PlayerColors
        List<PlayerColor> playerColorList = new ArrayList<>(Arrays.asList(PlayerColor.values()));
        // we check if some players has not already taken some colors
        for (Player player : this.getPlayers()) {
            playerColorList.remove(player.getPlayerColor()); // we remove the used color
        }
        return playerColorList;
    }

    /**
     * Creates a {@link Player} in the {@link Game} with the {@link PlayerColor} passed in parameter.
     * @param playerColor {@link PlayerColor} of the {@link Player} to be created.
     */
    public void createPlayerWithColor(PlayerColor playerColor) throws PlayerColorAlreadyUsed {
        // we check if the color has not already been taken
        boolean alreadyTaken = false;
        for (Player player : this.getPlayers()) {
            if (player.getPlayerColor().equals(playerColor)) {
                alreadyTaken = true; // another player has the wanted color
            }
        }
        if(!alreadyTaken) { // the color is not already used, we create a new player with this color
            Player player = new Player(playerColor);
            this.addPlayer(player);
        } else {
            throw new PlayerColorAlreadyUsed(); // the color has already been taken, normally the button with this color should not appear on the user's screen
        }
    }

    /**
     * Generates the {@link Domino}s for the current {@link Game}.
     * This collects all {@link Domino}s stored in a csv file and select randomly the good number of them according to the number of {@link Player}s and the {@link GameMode}.
     * @see CSVReader#getDominoes()
     */
    public void generateDominoes() throws IOException, InvalidDominoesCSVFile {
        // TODO check if players have been initialised
        this.setDominoes(CSVReader.getDominoes()); // we get all the dominoes stored in the CSV file
        int numberDominoesToRemove = 0;
        switch (this.getPlayers().size()){
            case 2:
                numberDominoesToRemove = 24;
                break;
            case 3:
                numberDominoesToRemove = 12;
                break;
        }
        // if the gameMode is great duel, then all dominoes should be used
        if(this.getGameMode().equals(GameMode.THEGREATDUEL)) {
            numberDominoesToRemove = 0;
        }
        Collections.shuffle(this.dominoes); // we shuffle the list
        if(numberDominoesToRemove > 0) {
            this.dominoes.subList(this.dominoes.size() - numberDominoesToRemove, this.dominoes.size()).clear(); // we just get a sub part of the list
        }
    }

    /**
     * Initiates all {@link Player} attributes such as their {@link Board}s or {@link King}s.
     * @throws MaxCrownsLandPortionExceeded If a {@link LandPortion} has an invalid number of crowns.
     */
    public void initiatePlayers() throws MaxCrownsLandPortionExceeded {
        // for each player we generate his grid and his kings
        for(Player player : this.getPlayers()) {
            // we generate the board
            player.setBoard(new Board(this.getGameMode(), player.getPlayerColor())); // generate the board with castle and startTile
            // we generate the good number of kings
            int numberOfKings = 0;
            switch (this.getPlayers().size()){
                case 2:
                    numberOfKings = 2;
                    break;
                case 3:
                case 4:
                    numberOfKings = 1;
                    break;
                default:
                    // TODO throw exception ?
                    break;
            }
            for(int i = 0; i < numberOfKings; i++){
                player.addKing(new King(player.getPlayerColor()));
            }
        }
    }

    /**
     * Get the new line of {@link Domino}s on the {@link Game}.
     * @return  {@link DominoesList} containing all {@link Domino}s in the new {@link Game} line.
     */
    public DominoesList getNewDominoesList() {
        DominoesList newDominoesList = this.getPickedDominoes().get(this.getPickedDominoes().size() - 1);
        newDominoesList.sortByNumber(); // sort
        return newDominoesList;
    }

    /**
     * Get the previous line of {@link Domino}s on the {@link Game}.
     * @return  {@link DominoesList} containing all {@link Domino}s in the previous {@link Game} line.
     */
    public DominoesList getPreviousDominoesList() {
        DominoesList previousDominoesList;
        if(!this.isLastTurn) {
            previousDominoesList = this.getPickedDominoes().get(this.getPickedDominoes().size() - 2);
        } else {
            previousDominoesList = this.getPickedDominoes().get(this.getPickedDominoes().size() - 1);
        }
        previousDominoesList.sortByNumber();
        return previousDominoesList;
    }

    /**
     * Picks as number of {@link Domino}s in the {@link Game} stack as the number of {@link King}s in the {@link Game}.
     * @throws NoMoreDominoInGameStack  There is no more {@link Domino}s in the {@link Game} stack. This is consequently the last turn.
     * @throws NotEnoughDominoesInGameStack There is not enough {@link Domino}s in the {@link Game} stack. It's probably a bug as the number of {@link Domino}s in the {@link Game} stack is a multiple of the number of {@link Player}s.
     */
    public void pickDominoes() throws NoMoreDominoInGameStack, NotEnoughDominoesInGameStack {
        int numberToPick = this.numberKingsInGame();
        // we select a part of the class dominoes array
        DominoesList dominoesPicked;
        try {
             dominoesPicked = new DominoesList(this.getDominoes().subList(0, numberToPick));
            // we remove it from the class array
            this.getDominoes().subList(0, numberToPick).clear();
            // we add the DominoesList to the Picked Dominoes list
            this.getPickedDominoes().add(dominoesPicked);
        } catch (IndexOutOfBoundsException e) {
            if(this.getDominoes().size() == 0) {
                this.setLastTurn(true);
                throw new NoMoreDominoInGameStack(); // there is no more Domino in game to be picked
            } else {
                dominoesPicked = new DominoesList(this.getDominoes());
                this.getDominoes().clear();
                // we add the DominoesList to the Picked Dominoes list
                this.getPickedDominoes().add(dominoesPicked);
                throw new NotEnoughDominoesInGameStack(); // there is not enough dominoes asked to be picked
            }
        }
    }

    /**
     * Sets the domino passed as parameter as a {@link Domino} of the current {@link Player}.
     * @param domino    {@link Domino} to be set as one of the current {@link Player}.
     * @return          {@link Response#NEXTTURNPLAYER} if it must be the turn of the next {@link Player}.
     *                  {@link Response#PICKDOMINOES} if a new turn must be started. A new line of {@link Domino}s must be picked.
     *                  {@link Response#SHOWPLACEPOSSIBILITIES} if the {@link Position} possibilities must be shown for the domino.
     *                  {@link Response#GAMEOVER} if the {@link Game} is over.
     *                  {@link Response#NULL} if nothing must be done. It's probably a bug.
     */
    public Response playerChoosesDomino(Domino domino) {
        if(this.isLastTurn){
            this.setPreviousDomino(this.getRandomDominoWithoutPosition());
            if(this.getPreviousDomino() == null) {
                return Response.GAMEOVER;
            }
            this.getPreviousDomino().setKing(null); // !! BEFORE SETTING POSITION OF THE PREVIOUS DOMINO !!

            return Response.SHOWPLACEPOSSIBILITIES;
        } else {
            // we ask for the next King to be placed
            King king = this.nextKing();
            if (domino.getKing() != null || king.isPlaced() || !this.getNewDominoesList().contains(domino)) {
                // we do nothing
            } else {
                this.setNewDomino(domino);
                this.getNewDomino().setKing(king);
                if (this.getTurnNumber() == 1) {
                    try {
                        this.getCurrentPlayer().getBoard().addDomino(this.getNewDomino());
                    } catch (InvalidDominoPosition invalidDominoPosition) {
                        invalidDominoPosition.printStackTrace(); // TODO handle this case
                    }
                    this.getNewDominoesList().remove(this.getNewDomino());
                    this.playerHasFinishSelectingDomino();

                    // we check if it's not a new turn
                    if (this.isAllPickedDominoesListHaveKings(this.getPickedDominoes().size() - 1)) {
                        this.setTurnNumber(this.getTurnNumber() + 1); // increment the turn number
                        return Response.PICKDOMINOES;
                    } else {
                        return Response.NEXTTURNPLAYER;
                    }
                } else {
                    // we get the domino on which the king was
                    this.setPreviousDomino(this.getCurrentPlayer().getDominoWithKing(king));
                    this.getPreviousDomino().setKing(null); // !! BEFORE SETTING POSITION OF THE PREVIOUS DOMINO !!

                    return Response.SHOWPLACEPOSSIBILITIES;
                }

            }
        }
        return Response.NULL;
    }

    /**
     * Returns a random {@link Domino} with has any {@link Position}.
     * This methods was designed for the last turn.
     * Take care, this modify the current {@link Player} of the {@link Game}.
     * @return  Random {@link Domino} without {@link Position}.
     */
    private Domino getRandomDominoWithoutPosition() {
        for(Player player : this.getPlayers()) {
            Domino dominoWithoutPosition = player.getDominoWithoutPosition();
            if(dominoWithoutPosition != null) {
                this.setCurrentPlayer(player);
                return dominoWithoutPosition;
            }
        }
        return null;
    }

    /**
     * Checks if all {@link Domino}s in the {@link DominoesList} selected (by the indexList) from the PickedDominoesList have a {@link King}.
     * @param indexList Index of the {@link DominoesList} from the PickedDominoesList in which this methods checks if all {@link Domino}s have {@link King}.
     * @return  true if all {@link Domino}s in the select {@link DominoesList} have kings.
     *          false otherwise.
     */
    private boolean isAllPickedDominoesListHaveKings(int indexList) {
        DominoesList dominoesList = this.getPickedDominoes().get(indexList);
        for(Domino domino : dominoesList) {
            if(domino.getKing() == null) { // there is at least one domino that has no king yet
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the number of {@link King}s in the {@link Game}.
     * @return  Number of {@link King}s in the {@link Game}.
     */
    public int numberKingsInGame() {
        int numberKings = 0;
        for(Player player : this.getPlayers()) {
            numberKings += player.getKings().size();
        }
        return numberKings;
    }

    /**
     * Returns the next {@link King} that has to be placed and set the current {@link Player} of the {@link Game}.
     */
    private King nextKing() {
        List<King> notPlacedKingList = this.getNotPlacedKings();
        if(this.getTurnNumber() == 1) { // it's the first turn
            King nextKing = notPlacedKingList.get(Function.randInt(0, notPlacedKingList.size() - 1)); // we take the king randomly
            for (Player player : this.getPlayers()) {
                for (King king : player.getKings()) {
                    if (king == nextKing) {
                        this.setCurrentPlayer(player);
                    }
                }
            }
            return nextKing;
        } else { // it's no more the first turn
            Map<Player, Integer> playersTurn = this.getPlayerTurns().get(this.getTurnNumber()); // we take the firstPlayer
            Player player = null;
            int smallValue = 48;
            for (Map.Entry<Player, Integer> entry : playersTurn.entrySet())
            {
                if(entry.getValue() <= smallValue) {
                    smallValue = entry.getValue();
                    player = entry.getKey();
                }
            }
            if(player == null) {
                player = this.getCurrentPlayer();
            }
            this.setCurrentPlayer(player);
            if(player.getNotPlacedKings().size() == 0) { // all kings are placed, we mark all of them as no more placed anymore
                for (King king : player.getKings()) {
                    king.setPlaced(false);
                }
            }
            if(player.getNotPlacedKings().size() == 1) { // after this king, the player would have placed all his kings, then we add the player to the end of the queue
                playersTurn.remove(player); // we remove it from the list

            }
            return player.getNotPlacedKings().get(0);
        }
    }

    /**
     * Must be called when a {@link Player} has over his turn. It edits the turns List for next turns.
     */
    private void playerHasFinishSelectingDomino() {
        if(this.getTurnNumber() + 1 >= this.getPlayerTurns().size()) {
            this.getPlayerTurns().add(new HashMap<>()); // we create the list for the next turn
        }
        Map<Player, Integer> nextTurnPlayerTurns = this.getPlayerTurns().get(this.getTurnNumber() + 1);
        nextTurnPlayerTurns.put(this.getCurrentPlayer(), this.getCurrentPlayer().getSmallestNumberOfUnPlacedDominoes()); // update the value
    }

    /**
     * Returns {@link List} of all not placed {@link King}s.
     * @return  {@link List} of all not placed {@link King}s.
     */
    private List<King> getNotPlacedKings() {
        List<King> notPlacedKings = new ArrayList();
        for(Player player : this.getPlayers()) {
            notPlacedKings.addAll(player.getNotPlacedKings());
        }
        return notPlacedKings;
    }

    /**
     * Makes rotate a {@link Domino} by 90 degrees.
     * It impacts the view of the {@link Game} and shows new {@link Position} possibilities according to the new domino {@link Rotation}.
     * @param domino    {@link Domino} that must rotate.
     * @see Rotation
     */
    public void makeRotateDomino(Domino domino) {
        domino.setRotation(Rotation.getCorrespondingRotation(domino.getRotation().getDegree() + 90));
        this.getCurrentPlayer().getBoard().getBoardView().removeAllPossibilities();
        this.getCurrentPlayer().getBoard().getPossibilities(domino);
    }

    /**
     * Discards the {@link Domino} that the current {@link Player} has to place.
     * @return  {@link Response#NEXTTURNPLAYER} if it must be the turn of the next {@link Player}.
     *          {@link Response#PICKDOMINOES} if a new turn must be started. A new line of {@link Domino}s must be picked.
     *          {@link Response#NULL} if nothing must be done. It's probably a bug.
     */
    public Response discardDomino() {
        try {
            this.getCurrentPlayer().getBoard().getBoardView().removeAllPossibilities();
            this.getCurrentPlayer().getBoard().removeDomino(this.getPreviousDomino());
            this.getNewDominoesList().remove(this.getPreviousDomino());
            this.getPreviousDominoesList().getDominoesListView().removeDominoView(this.getPreviousDomino());
            if(!this.isLastTurn) {
                this.getCurrentPlayer().getBoard().addDomino(this.getNewDomino());
            }
            this.playerHasFinishSelectingDomino();


            // we check if it's not a new turn
            if (this.isAllPickedDominoesListHaveKings(this.getPickedDominoes().size() - 1)) {
                this.setTurnNumber(this.getTurnNumber() + 1); // increment the turn number
                return Response.PICKDOMINOES;
            } else {
                return Response.NEXTTURNPLAYER;
            }
        } catch (InvalidDominoPosition invalidDominoPosition) {
            invalidDominoPosition.printStackTrace();
        }
        return Response.NULL;
    }

    /**
     * Determines the {@link Position} chosen by the current {@link Player} for his {@link Domino}.
     * @param position1 {@link Position} for the first {@link LandPortion} of the {@link Domino}. Take care, position1 must have an x lower than or equal to that of position2. Also, position1 must have an y greater than or equal to that of position2.
     * @param position2 {@link Position} for the second {@link LandPortion} of the {@link Domino}.
     * @return  {@link Response#NEXTTURNPLAYER} if it must be the turn of the next {@link Player}.
     *          {@link Response#PICKDOMINOES} if a new turn must be started. A new line of {@link Domino}s must be picked.
     * @see LandPortion
     */
    public Response playerChoosesPositionForDomino(Position position1, Position position2) {
        this.getCurrentPlayer().getBoard().getBoardView().removeAllPossibilities();
        switch (this.getPreviousDomino().getRotation()) {
            case NORMAL:
            case INVERSE:
                this.getPreviousDomino().getLeftPortion().setPosition(position1);
                this.getPreviousDomino().getRightPortion().setPosition(position2);
                break;
            case LEFT:
            case RIGHT:
                this.getPreviousDomino().getLeftPortion().setPosition(position2);
                this.getPreviousDomino().getRightPortion().setPosition(position1);
                break;
        }
        try {
            /*
             * IMPORTANT
             * We remove the domino from the board as it was already stored in it with empty position. As the position are now set for this domino, we will not be able to add it (by checking if the position are right).
             */
            if(!this.isLastTurn) {
                this.getCurrentPlayer().getBoard().removeDomino(this.getPreviousDomino());
                this.getCurrentPlayer().getBoard().addDomino(this.getPreviousDomino());
                this.getCurrentPlayer().getBoard().addDomino(this.getNewDomino()); // add the new domino
            } else {
                this.getCurrentPlayer().getBoard().getBoardView().addDomino(this.getPreviousDomino());
            }
        } catch (InvalidDominoPosition invalidDominoPosition) {
            invalidDominoPosition.printStackTrace(); // TODO handle this case
        }
        this.getNewDominoesList().remove(this.getPreviousDomino());

        this.playerHasFinishSelectingDomino();

        // we check if it's not a new turn
        if (this.isAllPickedDominoesListHaveKings(this.getPickedDominoes().size() - 1)) {
            this.setTurnNumber(this.getTurnNumber() + 1); // increment the turn number
            return Response.PICKDOMINOES;
        } else {
            return Response.NEXTTURNPLAYER;
        }
    }

    /**
     * Calculates the score of the {@link Game}.
     * @return  {@link Map<Player, Integer>} containing all {@link Player}s and their associated score.
     */
    public Map<Player, Integer> calculateScore() {
        return Score.getScores(this);
    }

    /**
     * Calculates the winner of the {@link Game}.
     * @return  {@link List} containing one or many winners.
     */
    public List<Player> getWinners() {
        return Score.getWinners(this);
    }
    
    /*
     *
     * GETTERS AND SETTERS
     *
     */

    /**
     * Returns all {@link Domino}s in the {@link Game} stack.
     * @return  {@link DominoesList} containing all {@link Domino}s in the {@link Game} stack.
     */
    public DominoesList getDominoes() {
        return dominoes;
    }

    /**
     * Sets all the {@link Domino}s stored in the {@link Game} stack.
     * Take care, it replaces all previous {@link Domino}s stored in this stack.
     * @param dominoes {@link DominoesList} to be set as the {@link Game} stack.
     */
    public void setDominoes(DominoesList dominoes) {
        this.dominoes = dominoes;
    }

    /**
     * Adds a {@link Domino} in the {@link Game} stack.
     * @param domino {@link Domino} to be added into the {@link Game} stack.
     */
    public void addDomino(Domino domino) {
        this.dominoes.add(domino);
    }

    /**
     * Returns all {@link Player}s of the {@link Game}.
     * @return  {@link List} of all {@link Player}s in the {@link Game}.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Sets all {@link Player}s in the {@link Game}.
     * Take care, it replaces all previous {@link Player}s set in the {@link Game}.
     * @param players   {@link List} of {@link Player}s of the {@link Game} to be set.
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Adds a {@link Player} into the {@link Game}.
     * @param player    {@link Player} to be added into the {@link Game}.
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * Returns the {@link GameMode} of the {@link Game}.
     * @return  {@link GameMode} of the {@link Game}.
     * @see GameMode
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Sets the {@link GameMode} of the {@link Game}.
     * @param gameMode {@link GameMode} of the {@link Game} to be set.
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Returns the current {@link Player} of the {@link Game}.
     * @return  Current {@link Player} of the {@link Game}.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the current {@link Player} of the {@link Game}.
     * @param currentPlayer Current {@link Player} of the {@link Game} to be set.
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Returns the {@link List<Map<Player, Integer>>} of {@link Player}s turns.
     * It contains the order of player turns, for each one.
     * @return {@link List<Map<Player, Integer>>} of the {@link Player}s turns.
     */
    public List<Map<Player, Integer>> getPlayerTurns() {
        return playerTurns;
    }

    /**
     * Sets the {@link List<Map<Player, Integer>>} of {@link Player}s turns.
     * It contains the order of player turns, for each one.
     * Take care, it replaces the previous {@link List} stored.
     * @param playerTurns {@link List<Map<Player, Integer>>} of the {@link Player}s turns to be set.
     */
    public void setPlayerTurns(List<Map<Player, Integer>> playerTurns) {
        this.playerTurns = playerTurns;
    }

    /**
     * Returns the current turn number.
     * @return  The current turn number.
     */
    public int getTurnNumber() {
        return turnNumber;
    }

    /**
     * Sets the current turn number.
     * @param turnNumber    Current turn number to be srt.
     */
    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    /**
     * Returns a {@link List} of {@link DominoesList} of pickedDominoes.
     * At each execution of {@link #pickDominoes()} a new {@link DominoesList} is added.
     * @return {@link List} of {@link DominoesList} of pickedDominoes.
     * @see #pickDominoes()
     */
    public List<DominoesList> getPickedDominoes() {
        return pickedDominoes;
    }

    /**
     * Sets the {@link List} of {@link DominoesList} of pickedDominoes.
     * @param pickedDominoes {@link List} of {@link DominoesList} of pickedDominoes to be set.
     * @see #pickDominoes()
     */
    public void setPickedDominoes(List<DominoesList> pickedDominoes) {
        this.pickedDominoes = pickedDominoes;
    }

    /**
     * Returns the previous {@link Domino} of the current {@link Player}.
     * It's generally the {@link Domino} that the current {@link Player} has to place on his {@link Board}.
     * @return  Previous {@link Domino} of the current {@link Player}.
     */
    public Domino getPreviousDomino() {
        return previousDomino;
    }

    /**
     * Sets the previous {@link Domino} of the current {@link Player}.
     * It's generally the {@link Domino} that the current {@link Player} has to place on his {@link Board}.
     * @param previousDomino  Previous {@link Domino} of the current {@link Player} to be set.
     */
    public void setPreviousDomino(Domino previousDomino) {
        this.previousDomino = previousDomino;
    }

    /**
     * Returns the new {@link Domino} of the current {@link Player}.
     * It's generally the {@link Domino} on which the current {@link Player} has placed one of his {@link King}.
     * @return  New {@link Domino} of the current {@link Player}.
     */
    public Domino getNewDomino() {
        return newDomino;
    }

    /**
     * Sets the new {@link Domino} of the current {@link Player}.
     * It's generally the {@link Domino} on which the current {@link Player} has placed one of his {@link King}.
     * @param newDomino  New {@link Domino} of the current {@link Player} to be set.
     */
    public void setNewDomino(Domino newDomino) {
        this.newDomino = newDomino;
    }

    /**
     * Returns if this is the last turn of the {@link Game}.
     * @return  true if this is the last turn of the {@link Game}.
     *          false otherwise.
     */
    public boolean isLastTurn() {
        return isLastTurn;
    }

    /**
     * Sets if this is the last turn of the {@link Game}.
     * @param lastTurn  Boolean defining if this is the last {@link Game} turn.
     */
    public void setLastTurn(boolean lastTurn) {
        isLastTurn = lastTurn;
    }
}


