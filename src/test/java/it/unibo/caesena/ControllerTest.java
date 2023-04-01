package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.controller.ControllerImpl;

final class ControllerTest {
    private final Controller controller;

    ControllerTest(final Controller controller) {
        this.controller = new ControllerImpl();
    }

    @BeforeAll
    void init() {
        this.controller.addPlayer("Giocatore1");
        this.controller.startGame();
    }

    @Test
    void testStartGameAndAddPlayer() {
        assertThrows(IllegalStateException.class, () -> this.controller.startGame());
        this.controller.addPlayer("Giocatore1");
        assertDoesNotThrow(() -> this.controller.startGame());
    }

    @Test
    void testTilesBuild() {
        assertFalse(this.controller.getNotPlacedTiles().isEmpty());
    }

    @Test
    void testAddPlayer() {
        assertEquals(this.controller.getPlayers().get(0), this.controller.getCurrentPlayer());
        this.controller.addPlayer("Giocatore2");
        assertEquals(2, this.controller.getPlayers().size());
    }

    @Test
    void testGetMeeples() {
        assertEquals(this.controller.getPlayerMeeples(this.controller.getCurrentPlayer()).size(), 8);
    }
}
