import exceptions.*;
import helpers.Function;
import models.classes.*;
import models.enums.GameMode;
import models.enums.PlayerColor;
import models.enums.Response;
import models.enums.Rotation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CLI {

    private Game game;
    private int counter = 0;

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
        counter = 0;
        System.out.println("Picked dominoes");
        try {
            game.pickDominoes();
        } catch (NoMoreDominoInGameStack noMoreDominoInGameStack) {
            System.out.println("Last turn!!");
        } catch (NotEnoughDominoesInGameStack notEnoughDominoesInGameStack) {
            // bug
        }
        playerTurn();
    }

    private void playerTurn() {
        System.out.println("Player turn");
        if(game.getTurnNumber() == 1 ) {
            Domino newDomino = game.getNewDominoesList().get(0);
            switch (game.playerChoosesDomino(newDomino)) {
                case PICKDOMINOES:
                    System.out.println("answer pick dominoes in player turn");
                    newTurn();
                    break;
                case NEXTTURNPLAYER:
                    System.out.println("answer next turn in player turn");
                    playerTurn();
                    break;
            }
        } else if(game.isLastTurn()) {
            switch (game.playerChoosesDomino(null)){
                case GAMEOVER:
                    System.out.println("Game ovver !!!!!!!!!!!");
                    Map<Player, Integer> scores = Score.getScores(game);
                    for(Map.Entry<Player, Integer> entry : scores.entrySet()) {
                        System.out.println(entry.getKey().getPlayerColor().toString() + " : " + entry.getValue());
                    }
                    break;
                default:
                    positionForHisDomino();
                    break;
            }
        } else {
            Domino newDomino = game.getNewDominoesList().get(counter);
            counter++;
            Response test = game.playerChoosesDomino(newDomino);
            switch (test) {
                case SHOWPLACEPOSSIBILITIES:
                    System.out.println("show possibilities");
                    positionForHisDomino();
                    break;
            }
        }
    }

    private void positionForHisDomino() {
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
                            error = false;
                            newTurn();
                            break;
                        case NEXTTURNPLAYER:
                            error = false;
                            playerTurn();
                            break;
                    }
                } catch (InvalidDominoPosition e) {

                }
            }
        }
    }

    private List<Position> randomPositions(Domino domino) {
        List<List<Position>> allPossibilities = new ArrayList<>();
        List<Position> randomPositions = new ArrayList<>();
        for(Rotation rotation : Rotation.values()) {
            domino.setRotation(rotation);
            List<List<Position>> positions = game.getCurrentPlayer().getBoard().getPossibilities(domino);
            allPossibilities.addAll(positions);
        }
        if(allPossibilities.size() > 0) {
            randomPositions = allPossibilities.get(Function.randInt(0, allPossibilities.size() - 1));
            return randomPositions;
        } else {
            return randomPositions;
        }
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
