package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import it.unibo.caesena.model.PlayerImpl;
import it.unibo.caesena.utils.Color;

final class PlayerTest {

    private static PlayerImpl player;

    @BeforeAll
    public static void init() {
        player = new PlayerImpl("Giocatore1", Color.createCustomColor("FF0000"));
    }

    @Test
    public void testGetters() {
        String name = player.getName();
        assertEquals("Giocatore1", name);
        Color color = player.getColor();
        assertEquals(Color.createCustomColor("FF0000"), color);
        int score = player.getScore();
        assertEquals(0, score);
    }

    @Test
    public void testScore() {
        player.setScore(10);
        assertEquals(10, player.getScore());
        player.addScore(10);
        assertEquals(20, player.getScore());
        assertThrows(IllegalStateException.class, () -> player.addScore(-10));
        assertThrows(IllegalStateException.class, () -> player.setScore(10));
    }

    @Test
    public void testToString() {
        String string = player.toString();
        assertEquals("[Color: [Hex: FF0000, Hex as integer: 16711680, Name: Custom Color], Name: Giocatore1, Prova parole dai vediamo: ciao, Score: 0]", string);
    }
}