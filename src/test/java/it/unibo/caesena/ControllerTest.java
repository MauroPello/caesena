package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.controller.*;
import it.unibo.caesena.utils.Color;

final class ControllerTest {
    private static Controller controller;
    private static Color color1;
    private static Color color2;

    @BeforeAll
    public static void init() {
        controller = new ControllerImpl();
        color1 = Color.createColor("FF0000", "Red");
        color2 = Color.createColor("00FF00", "Green");
        controller.addPlayer("Giocatore1", color1);
        controller.startGame();
    }

    @Test
    public void testStartGameAndAddPlayer() {
        controller = new ControllerImpl();
        assertThrows(IllegalStateException.class, () -> controller.startGame());
        controller.addPlayer("Giocatore1", color1);
        assertDoesNotThrow(() -> controller.startGame());
    }

    @Test
    public void testTilesBuild() {
        assertTrue(!controller.getNotPlacedTiles().isEmpty());
    }

    @Test
    public void testAddPlayer() {
        assertEquals(controller.getPlayers().get(0), controller.getCurrentPlayer());
        controller.addPlayer("Giocatore2", color2);
        assertEquals(2, controller.getPlayers().size());
    }

    @Test
    public void testGetMeeples() {
        assertEquals(controller.getCurrentPlayerMeeples().size(), 8);
    }
}