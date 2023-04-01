package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.controller.ControllerImpl;

final class ControllerTest {
    private final Controller controller;
    private final String firstPlayerName;
    private final String secondPlayerName;

    ControllerTest() {
        this.controller = new ControllerImpl();
        this.firstPlayerName = "Giocatore1";
        this.secondPlayerName = "Giocatore2";
    }

    @Test
    void testStartGameAndAddPlayer() {
        assertThrows(IllegalStateException.class, () -> this.controller.startGame());
        this.controller.addPlayer(this.firstPlayerName);
        assertDoesNotThrow(() -> this.controller.startGame());
    }

    @Test
    void testTilesBuild() {
        this.controller.addPlayer(this.firstPlayerName);
        this.controller.startGame();
        assertFalse(this.controller.getNotPlacedTiles().isEmpty());
    }

    @Test
    void testAddPlayer() {
        this.controller.addPlayer(this.firstPlayerName);
        this.controller.startGame();
        assertEquals(this.controller.getPlayers().get(0), this.controller.getCurrentPlayer());
        this.controller.addPlayer(this.secondPlayerName);
        assertEquals(2, this.controller.getPlayers().size());
    }

    @Test
    void testGetMeeples() {
        this.controller.addPlayer(this.firstPlayerName);
        this.controller.startGame();
        assertEquals(this.controller.getPlayerMeeples(this.controller.getCurrentPlayer()).size(), 8);
    }
}
