package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.controller.ControllerImpl;
import it.unibo.caesena.model.Color;

final class ControllerTest {
    private static final Color FIRST_PLAYER_COLOR = new Color(50, 50, 50);
    private static final Color SECOND_PLAYER_COLOR = new Color(0, 0, 0);
    private static final String FIRST_PLAYER_NAME = "Giocatore1";
    private static final String SECOND_PLAYER_NAME = "Giocatore2";
    private final Controller controller;

    ControllerTest() {
        this.controller = new ControllerImpl();
    }

    @Test
    void testTilesBuild() {
        this.controller.resetGame();
        this.controller.addPlayer(FIRST_PLAYER_NAME, FIRST_PLAYER_COLOR);
        this.controller.startGame();
        assertFalse(this.controller.getNotPlacedTiles().isEmpty());
    }

    @Test
    void testAddPlayer() {
        this.controller.resetGame();
        this.controller.addPlayer(FIRST_PLAYER_NAME, FIRST_PLAYER_COLOR);
        this.controller.startGame();
        assertEquals(this.controller.getPlayers().get(0), this.controller.getCurrentPlayer().get());
        this.controller.addPlayer(SECOND_PLAYER_NAME, SECOND_PLAYER_COLOR);
        assertEquals(2, this.controller.getPlayers().size());
    }

    @Test
    void testGetMeeples() {
        this.controller.resetGame();
        this.controller.addPlayer(FIRST_PLAYER_NAME, FIRST_PLAYER_COLOR);
        this.controller.startGame();
        assertEquals(8, this.controller.getPlayerMeeples(this.controller.getCurrentPlayer().get()).size());
    }

    // @Test
    // void testIsCurrentTilePleceable() {
    //     final Tile citytile = new TileFactoryWithBuilder().createCity().getX();
    //     final Tile tubetile = new TileFactoryWithBuilder().createCityTube().getX();

    //     this.controller.resetGame();
    //     this.controller.addPlayer(FIRST_PLAYER_NAME, FIRST_PLAYER_COLOR);
    //     this.controller.startGame();

    //     while (!this.controller.getCurrentTile().get().equals(citytile)
    //         && !this.controller.getCurrentTile().get().equals(tubetile)) {
    //         this.controller.endTurn();
    //     }
    //     if (this.controller.getCurrentTile().get().equals(tubetile)) {
    //         this.controller.rotateCurrentTile();
    //     }
    //     assertTrue(this.controller.isPositionValidForCurrentTile(new Pair<>(0, -1)));
    // }
}
