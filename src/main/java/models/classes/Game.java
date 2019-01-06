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

public class Game {

    private DominoesList dominoes;
    private List<DominoesList> pickedDominoes;
    private Domino previousDomino, newDomino;
    private List<Player> players;
    private List<Map<Player, Integer>> playerTurns;
    private int turnNumber;
    private Player currentPlayer;
    private GameMode gameMode;

    public Game() {
        this.setPlayers(new ArrayList<>());
        this.setPlayerTurns(new ArrayList<>());
        this.getPlayerTurns().add(new HashMap<>()); // to complete the index 0
        this.getPlayerTurns().add(new HashMap<>()); // to complete the index 1
        this.setPickedDominoes(new ArrayList<>());
        this.setTurnNumber(1); // first turn start
    }


    /**
     * Return all unused player colors
     * @return
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
     * Create a player in the game passed in parameter
     * @param playerColor
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
     * Generate the dominoes for the current game
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
     * Initiate all player attributes such as their boards or kings
     * @throws MaxCrownsLandPortionExceeded
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
     * Get the new line on the board
     * @return
     */
    public DominoesList getNewDominoesList() {
        DominoesList newDominoesList = this.getPickedDominoes().get(this.getPickedDominoes().size() - 1);
        newDominoesList.sortByNumber(); // sort
        return newDominoesList;
    }

    /**
     * Get the previous line on the board
     * @return
     */
    public DominoesList getPreviousDominoesList() {
        DominoesList previousDominoesList = this.getPickedDominoes().get(this.getPickedDominoes().size() - 2);
        previousDominoesList.sortByNumber();
        return previousDominoesList;
    }

    /**
     * Pick a number of dominoes
     * @throws NoMoreDominoInGameStack
     * @throws NotEnoughDominoesInGameStack
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

    public Response playerChoosesDomino(Domino domino) {
        // we ask for the next King to be placed
        King king = this.nextKing();
        if(domino.getKing() != null || king.isPlaced()) {
            // we do nothing
        } else {
            this.setNewDomino(domino);
            this.getNewDomino().setKing(king);
            if(this.getTurnNumber() == 1) {
                try {
                    this.getCurrentPlayer().getBoard().addDomino(this.getNewDomino());
                } catch (InvalidDominoPosition invalidDominoPosition) {
                    invalidDominoPosition.printStackTrace(); // TODO handle this case
                }
                this.getNewDominoesList().remove(this.getNewDomino());
                this.playerHasSelectedDomino();

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
        return Response.NULL;
    }

    /**
     * Check if all dominoes in the DominoesList selected from the PickedDominoesList have a king
     * @param indexList
     * @return
     */
    public boolean isAllPickedDominoesListHaveKings(int indexList) {
        DominoesList dominoesList = this.getPickedDominoes().get(indexList);
        for(Domino domino : dominoesList) {
            if(domino.getKing() == null) { // there is at least one domino that has no king yet
                return false;
            }
        }
        return true;
    }

    /**
     * Return the number of kings in the game
     * @return
     */
    public int numberKingsInGame() {
        int numberKings = 0;
        for(Player player : this.getPlayers()) {
            numberKings += player.getKings().size();
        }
        return numberKings;
    }

    /**
     * Return the next King and set the current player
     */

    public King nextKing() {
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
     * This function must be called when a player has over his turn. It edits the turns List for next turns.
     */
    public void playerHasSelectedDomino() {
        if(this.getTurnNumber() + 1 >= this.getPlayerTurns().size()) {
            this.getPlayerTurns().add(new HashMap<>()); // we create the list for the next turn
        }
        Map<Player, Integer> nextTurnPlayerTurns = this.getPlayerTurns().get(this.getTurnNumber() + 1);
        nextTurnPlayerTurns.put(this.getCurrentPlayer(), this.getCurrentPlayer().getSmallestNumberOfUnPlacedDominoes()); // update the value
    }

    /**
     * Return List of all not placed kings
     * @return
     */
    private List<King> getNotPlacedKings() {
        List<King> notPlacedKings = new ArrayList();
        for(Player player : this.getPlayers()) {
            notPlacedKings.addAll(player.getNotPlacedKings());
        }
        return notPlacedKings;
    }

    public void makeRotateDomino(Domino domino) {
        domino.setRotation(Rotation.getCorrespondingRotation(domino.getRotation().getDegree() + 90));
        this.getCurrentPlayer().getBoard().getBoardView().removeAllPossibilities();
        this.getCurrentPlayer().getBoard().getPossibilities(domino);
    }

    public Response discardDomino() {
        try {
            this.getCurrentPlayer().getBoard().getBoardView().removeAllPossibilities();
            this.getCurrentPlayer().getBoard().removeDomino(this.getPreviousDomino());
            this.getNewDominoesList().remove(this.getPreviousDomino());
            DominoesList previousDominoesList = this.getPickedDominoes().get(this.getPickedDominoes().size() - 2);
            previousDominoesList.getDominoesListView().removeDominoView(this.getPreviousDomino());
            this.getCurrentPlayer().getBoard().addDomino(this.getNewDomino());

            this.playerHasSelectedDomino();


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
            this.getCurrentPlayer().getBoard().removeDomino(this.getPreviousDomino());
            this.getCurrentPlayer().getBoard().addDomino(this.getPreviousDomino());
            this.getCurrentPlayer().getBoard().addDomino(this.getNewDomino()); // add the new domino
        } catch (InvalidDominoPosition invalidDominoPosition) {
            invalidDominoPosition.printStackTrace(); // TODO handle this case
        }
        //newDominoesList.remove(previousDomino); // TODO useful ?

        // TODO add the Player to the turns list, already done by the following function right ?
        this.playerHasSelectedDomino();


        // we check if it's not a new turn
        if (this.isAllPickedDominoesListHaveKings(this.getPickedDominoes().size() - 1)) {
            this.setTurnNumber(this.getTurnNumber() + 1); // increment the turn number
            return Response.PICKDOMINOES;
        } else {
            return Response.NEXTTURNPLAYER;
        }
    }

    
    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public DominoesList getDominoes() {
        return dominoes;
    }

    public void setDominoes(DominoesList dominoes) {
        this.dominoes = dominoes;
    }

    public void addDomino(Domino domino) {
        this.dominoes.add(domino);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<Map<Player, Integer>> getPlayerTurns() {
        return playerTurns;
    }

    public void setPlayerTurns(List<Map<Player, Integer>> playerTurns) {
        this.playerTurns = playerTurns;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public List<DominoesList> getPickedDominoes() {
        return pickedDominoes;
    }

    public void setPickedDominoes(List<DominoesList> pickedDominoes) {
        this.pickedDominoes = pickedDominoes;
    }

    public Domino getPreviousDomino() {
        return previousDomino;
    }

    public void setPreviousDomino(Domino previousDomino) {
        this.previousDomino = previousDomino;
    }

    public Domino getNewDomino() {
        return newDomino;
    }

    public void setNewDomino(Domino newDomino) {
        this.newDomino = newDomino;
    }
}


