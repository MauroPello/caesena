package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import it.unibo.caesena.model.PlayerImpl;
import it.unibo.caesena.model.Tile;
import it.unibo.caesena.model.TileImpl;
import it.unibo.caesena.utils.Color;

final class TileTest {

    private static Tile tile;

    @BeforeAll
    public static void init() {
        tile = new TileImpl("OOP22-caesena/src/main/resources/it/unibo/caesena/images/tiles/city-large.png");
    }

    @Test
    public void testGetters() {

        String path = tile.getImagePath();
        String  tile.getPosition();
        tile.getRotationCount();
        
        
        String name = player.getName();
        assertEquals("Giocatore1", name);
        Color color = player.getColor();
        assertEquals(Color.createColor("FF0000", "Red"), color);
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
        assertEquals("[Name: Giocatore1, Color: [Name: Red, Hex: FF0000], Score: 0]", string);
    }
}
