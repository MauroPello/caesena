package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.controller.ControllerImpl;
import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.GameSetTileMediator;
import it.unibo.caesena.model.GameSetTileMediatorImpl;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileFactoryWithBuilder;
import it.unibo.caesena.utils.Pair;

final class ControllerTest {
    private final Controller controller;
    private final String firstPlayerName;
    private final Color firstPlayerColor;
    private final String secondPlayerName;
    private final Color secondPlayerColor;
    private final GameSetTileMediator mediator;

    ControllerTest() {
        this.controller = new ControllerImpl();
        this.firstPlayerName = "Giocatore1";
        this.secondPlayerName = "Giocatore2";
        this.firstPlayerColor = new Color(0, 0, 0);
        this.secondPlayerColor = new Color(50, 50, 50);
        mediator = new GameSetTileMediatorImpl(new GameSetFactoryImpl());
    }

    @Test
    void testStartGameAndAddPlayer() {
        assertThrows(IllegalStateException.class, () -> this.controller.startGame());
        this.controller.addPlayer(this.firstPlayerName, this.firstPlayerColor);
        assertDoesNotThrow(() -> this.controller.startGame());
    }

    @Test
    void testTilesBuild() {
        this.controller.addPlayer(this.firstPlayerName, this.firstPlayerColor);
        this.controller.startGame();
        assertFalse(this.controller.getNotPlacedTiles().isEmpty());
    }

    @Test
    void testAddPlayer() {
        this.controller.addPlayer(this.firstPlayerName, this.firstPlayerColor);
        this.controller.startGame();
        assertEquals(this.controller.getPlayers().get(0), this.controller.getCurrentPlayer());
        this.controller.addPlayer(this.secondPlayerName, this.secondPlayerColor);
        assertEquals(2, this.controller.getPlayers().size());
    }

    @Test
    void testGetMeeples() {
        this.controller.addPlayer(this.firstPlayerName, this.firstPlayerColor);
        this.controller.startGame();
        assertEquals(this.controller.getPlayerMeeples(this.controller.getCurrentPlayer()).size(), 8);
    }

    @Test
    void testIsCurrentTilePleceable() {
        Tile citytile = new TileFactoryWithBuilder(mediator).createCity();
        Tile tubetile = new TileFactoryWithBuilder(mediator).createCityTube();

        this.controller.startGame(); //crea la tile iniziale in 0,0

        for(int y = -1; y > -3; y--)
        {
            Pair<Integer, Integer> pos = new Pair<Integer,Integer>(0, y);
            while(!this.controller.getCurrentTile().equals(citytile) ||
            !this.controller.getCurrentTile().equals(tubetile)) {
                this.controller.endTurn();
            }
            if(this.controller.getCurrentTile().equals(tubetile)) {
                this.controller.rotateCurrentTile();
            }
            this.controller.placeCurrentTile(pos);
            this.controller.endTurn();
        };
    }
}
