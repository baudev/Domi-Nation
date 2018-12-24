package models.classes;

import exceptions.InvalidDominoesCSVFile;
import exceptions.MaxCrownsLandPortionExceeded;
import exceptions.PlayerColorAlreadyUsed;
import models.enums.GameMode;
import models.enums.LandPortionType;
import models.enums.PlayerColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void getFreePlayerColorsShouldReturn3() {
        Game game = new Game();
        game.addPlayer(new Player(PlayerColor.BLUE));
        assertEquals(3, game.getFreePlayerColors().size());
    }

    @Test
    void createPlayerWithFreeColor() throws PlayerColorAlreadyUsed {
        Game game = new Game();
        game.createPlayerWithColor(PlayerColor.BLUE);
        assertEquals(1, game.getPlayers().size());
    }

    @Test
    void createPlayerWithNonFreeColor() {
        Game game = new Game();
        game.addPlayer(new Player(PlayerColor.BLUE));
        assertThrows(PlayerColorAlreadyUsed.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                game.createPlayerWithColor(PlayerColor.BLUE);
            }
        });
    }

    @Test
    void generateDominoesFor2PlayersShouldGenerate24Dominoes() throws IOException, InvalidDominoesCSVFile {
        Game game = new Game();
        game.setGameMode(GameMode.CLASSIC);
        game.addPlayer(new Player(PlayerColor.BLUE));
        game.addPlayer(new Player(PlayerColor.GREEN));
        game.generateDominoes();
        assertEquals(24, game.getDominoes().size());
    }
    @Test
    void generateDominoesFor3PlayersShouldGenerate36Dominoes() throws IOException, InvalidDominoesCSVFile {
        Game game = new Game();
        game.setGameMode(GameMode.HARMOMY);
        game.addPlayer(new Player(PlayerColor.BLUE));
        game.addPlayer(new Player(PlayerColor.GREEN));
        game.addPlayer(new Player(PlayerColor.PINK));
        game.generateDominoes();
        assertEquals(36, game.getDominoes().size());
    }

    @Test
    void generateDominoesFor4PlayersShouldGenerate48Dominoes() throws IOException, InvalidDominoesCSVFile {
        Game game = new Game();
        game.setGameMode(GameMode.DYNASTY);
        game.addPlayer(new Player(PlayerColor.BLUE));
        game.addPlayer(new Player(PlayerColor.GREEN));
        game.addPlayer(new Player(PlayerColor.PINK));
        game.addPlayer(new Player(PlayerColor.YELLOW));
        game.generateDominoes();
        assertEquals(48, game.getDominoes().size());
    }

    @Test
    void generateDominoesFor2PlayersInGreatDuelModeShouldGenerate48Dominoes() throws IOException, InvalidDominoesCSVFile {
        Game game = new Game();
        game.setGameMode(GameMode.THEGREATDUEL);
        game.addPlayer(new Player(PlayerColor.BLUE));
        game.addPlayer(new Player(PlayerColor.GREEN));
        game.generateDominoes();
        assertEquals(48, game.getDominoes().size());
    }

    @Test
    void addDomino() throws MaxCrownsLandPortionExceeded {
        Game game = new Game();
        game.setDominoes(new DominoesList());
        Domino domino1 = new Domino(new LandPortion(1, LandPortionType.CHAMPS), new LandPortion(1, LandPortionType.MINE), 1);
        game.addDomino(domino1);
        assertSame(domino1, game.getDominoes().get(0));
    }

    @Test
    void initiate2PlayersShouldGenerateBoardAnd2Kings() throws MaxCrownsLandPortionExceeded {
        Game game = new Game();
        game.setGameMode(GameMode.THEGREATDUEL);
        game.addPlayer(new Player(PlayerColor.BLUE));
        game.addPlayer(new Player(PlayerColor.GREEN));
        game.initiatePlayers();
        for(Player player : game.getPlayers()) {
            assertEquals(2, player.getKings().size());
            assertNotNull(player.getBoard());
        }
    }

    @Test
    void initiate3PlayersShouldGenerateBoardAnd1Kings() throws MaxCrownsLandPortionExceeded {
        Game game = new Game();
        game.setGameMode(GameMode.THEGREATDUEL);
        game.addPlayer(new Player(PlayerColor.BLUE));
        game.addPlayer(new Player(PlayerColor.GREEN));
        game.addPlayer(new Player(PlayerColor.PINK));
        game.initiatePlayers();
        for(Player player : game.getPlayers()) {
            assertEquals(1, player.getKings().size());
            assertNotNull(player.getBoard());
        }
    }

    @Test
    void initiate4PlayersShouldGenerateBoardAnd1Kings() throws MaxCrownsLandPortionExceeded {
        Game game = new Game();
        game.setGameMode(GameMode.THEGREATDUEL);
        game.addPlayer(new Player(PlayerColor.BLUE));
        game.addPlayer(new Player(PlayerColor.GREEN));
        game.addPlayer(new Player(PlayerColor.YELLOW));
        game.addPlayer(new Player(PlayerColor.PINK));
        game.initiatePlayers();
        for(Player player : game.getPlayers()) {
            assertEquals(1, player.getKings().size());
            assertNotNull(player.getBoard());
        }
    }

    @Test
    void pick4DominoesOf48ShouldRemain42() throws IOException, InvalidDominoesCSVFile {
        Game game = new Game();
        game.setGameMode(GameMode.THEGREATDUEL);
        game.generateDominoes();
        DominoesList dominoesList = game.pickDominoes(4);
        assertEquals(4, dominoesList.size());
        assertEquals(48 - 4, game.getDominoes().size());
    }

    @Test
    void numberKingsInGameShouldReturn4() throws MaxCrownsLandPortionExceeded {
        Game game = new Game();
        game.setGameMode(GameMode.THEGREATDUEL);
        game.addPlayer(new Player(PlayerColor.BLUE));
        game.addPlayer(new Player(PlayerColor.GREEN));
        game.initiatePlayers();
        assertEquals(4, game.numberKingsInGame());
    }

}