package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.PlayerImpl;

final class PlayerTest {
    private final PlayerImpl player;

    PlayerTest() {
        this.player = new PlayerImpl("Giocatore1");
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
}
