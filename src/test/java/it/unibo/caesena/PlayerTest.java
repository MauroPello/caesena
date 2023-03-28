package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.PlayerImpl;

final class PlayerTest {
    private static PlayerImpl player;

    @Test
    public void testGetters() {
        player = new PlayerImpl("Giocatore1");
        final String name = player.getName();
        assertEquals("Giocatore1", name);
        final int score = player.getScore();
        assertEquals(0, score);
    }

    @Test
    public void testScore() {
        player = new PlayerImpl("Giocatore1");
        player.setScore(10);
        assertEquals(10, player.getScore());
        player.addScore(10);
        assertEquals(10 + 10, player.getScore());
        assertThrows(IllegalStateException.class, () -> player.addScore(-1 * 10));
        assertThrows(IllegalStateException.class, () -> player.setScore(10));
    }
}
