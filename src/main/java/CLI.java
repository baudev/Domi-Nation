import exceptions.*;
import helpers.Function;
import models.classes.*;
import models.enums.GameMode;
import models.enums.PlayerColor;
import models.enums.Response;
import models.enums.Rotation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CLI {

    private Game game;

    CLI() throws PlayerColorAlreadyUsed, MaxCrownsLandPortionExceeded, IOException, InvalidDominoesCSVFile {
        game = new Game();
        game.setGameMode(GameMode.CLASSIC);
        game.createPlayerWithColor(PlayerColor.BLUE);
        game.createPlayerWithColor(PlayerColor.GREEN);
        game.initiatePlayers();
        game.generateDominoes();
        newTurn();
    }


    private void newTurn() {
        System.out.println("Picked dominoes");
        try {
            game.pickDominoes();
        } catch (NoMoreDominoInGameStack noMoreDominoInGameStack) {
            System.out.println("Dernier tour !!");
        } catch (NotEnoughDominoesInGameStack notEnoughDominoesInGameStack) {
            // bug
        }
        if(!game.isLastTurn()) {
            playerTurn();
        }
    }

    private void playerTurn() {
        System.out.println("Player turn");
        if(game.getTurnNumber() == 1 ) {
            int counter = 0;
            for(Player player : game.getPlayers()) {
                switch (game.playerChoosesDomino(game.getNewDominoesList().get(0))) {
                    case PICKDOMINOES:
                        System.out.println("answer pick dominoes in player turn");
                        newTurn();
                        break;
                    case NEXTTURNPLAYER:
                        System.out.println("answer next turn in player turn");
                        playerTurn();
                        break;
                }

                counter++;
            }
        } else if(game.isLastTurn()) {
            switch (game.playerChoosesDomino(null)){
                case GAMEOVER:
                    System.out.println("Game ovver !!!!!!!!!!!");
                    break;
                default:
                    positionnateHisDomino();
                    break;
            }
        } else {
            int counter = 0;
            for(Player player : game.getPlayers()) {
                Response test = game.playerChoosesDomino(game.getNewDominoesList().get(0));
                switch (test) {
                    case SHOWPLACEPOSSIBILITIES:
                        System.out.println("show possibilities");
                        positionnateHisDomino();
                        break;
                }
                counter++;
            }
        }
    }

    private void positionnateHisDomino() {
        System.out.println("Positionnate");
        Domino previousDomino = game.getPreviousDomino();
        List<Position> randomPosition = randomPositions(previousDomino);
        if(randomPosition.size() == 0) {
            this.discardDomino();
        } else {
            boolean error = true;
            while (error) {
                try {
                    System.out.println("Error test");
                    previousDomino.setRotation(previousDomino.getRotation().getNextRotation());
                    switch (game.playerChoosesPositionForDomino(randomPosition.get(0), randomPosition.get(1))) {
                        case PICKDOMINOES:
                            newTurn();
                            break;
                        case NEXTTURNPLAYER:
                            playerTurn();
                            break;
                    }
                    error = false;
                } catch (InvalidDominoPosition e) {

                }
            }
        }
    }

    private List<Position> randomPositions(Domino domino) {
        List<List<Position>> allPossibilities = new ArrayList<>();
        for(Rotation rotation : Rotation.values()) {
            domino.setRotation(rotation);
            List<List<Position>> positions = game.getCurrentPlayer().getBoard().getPossibilities(domino);
            allPossibilities.addAll(positions);
        }
        List<Position> randomPositions = allPossibilities.get(Function.randInt(0, allPossibilities.size() - 1));
        return randomPositions;
    }

    private void getPossibilitiesForParticularRotation(Domino domino) {
        game.getCurrentPlayer().getBoard().getPossibilities(domino);
    }

    private void simulatePlacingDomino(Domino domino) {
        try {
            game.getCurrentPlayer().getBoard().addDomino(domino);
        } catch (InvalidDominoPosition invalidDominoPosition) {
            invalidDominoPosition.printStackTrace(); // bug n'arrive jamais
        }
    }

    private void removeSimulatedDomino(Domino domino) {
        game.getCurrentPlayer().getBoard().removeDomino(domino);
    }

    private void calculateScores() {
        Map<Player, Integer> results = Score.getScores(game);
        // Player moi = game.getCurrentPlayer();
        for(Map.Entry<Player, Integer> entry : results.entrySet()) {
            Player player  = entry.getKey();
            Integer score = entry.getValue();
        }
    }

    private void placeReallyDomino(Position position1, Position position2) throws InvalidDominoPosition {
        switch (game.playerChoosesPositionForDomino(position1, position2)) {
            case NEXTTURNPLAYER:
                playerTurn();
                break;
            case PICKDOMINOES:
                newTurn();
                break;
            case NULL:
                break;
        }
    }

    private void discardDomino() {
        switch (game.discardDomino()) {
            case PICKDOMINOES:
                newTurn();
                break;
            case NEXTTURNPLAYER:
                playerTurn();
                break;
            case NULL:
                break;
        }
    }

}
