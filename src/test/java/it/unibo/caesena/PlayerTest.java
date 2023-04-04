package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.PlayerImpl;

final class PlayerTest {
    private final PlayerImpl player;
    private final Color playerColor = new Color(50, 50, 50);

    PlayerTest() {
        this.player = new PlayerImpl("Giocatore1", playerColor);
    }

    @Test
    void testGetters() {
        final String name = player.getName();
        assertEquals("Giocatore1", name);
        final int score = player.getScore();
        assertEquals(0, score);
    }

    @Test
    void testScore() {
        player.setScore(10);
        assertEquals(10, player.getScore());
        player.addScore(10);
        assertEquals(10 + 10, player.getScore());
        assertThrows(IllegalStateException.class, () -> player.addScore(-1 * 10));
        assertThrows(IllegalStateException.class, () -> player.setScore(10));
    }

    @Test
    void testColor () {
        assertEquals(player.getColor(), playerColor);
    }
}
