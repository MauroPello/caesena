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
    private static Controller controller;

    @BeforeAll
    static void init() {
        controller = new ControllerImpl();
        controller.addPlayer("Giocatore1");
        controller.startGame();
    }

    @Test
    void testStartGameAndAddPlayer() {
        controller = new ControllerImpl();
        assertThrows(IllegalStateException.class, () -> controller.startGame());
        controller.addPlayer("Giocatore1");
        assertDoesNotThrow(() -> controller.startGame());
    }

    @Test
    void testTilesBuild() {
        assertFalse(controller.getNotPlacedTiles().isEmpty());
    }

    @Test
    void testAddPlayer() {
        assertEquals(controller.getPlayers().get(0), controller.getCurrentPlayer());
        controller.addPlayer("Giocatore2");
        assertEquals(2, controller.getPlayers().size());
    }

    @Test
    void testGetMeeples() {
        assertEquals(controller.getPlayerMeeples(controller.getCurrentPlayer()).size(), 8);
    }
}
