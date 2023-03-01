package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileFactoryWithBuilder;
import it.unibo.caesena.utils.Pair;

final class TileTest {

    private static Tile tile;
    private static Pair<Integer, Integer> position;

    @BeforeAll
    public static void init() {
        tile = new TileFactoryWithBuilder().createCity();
        position = new Pair<>(1, 1);
    }

    @Test
    public void testGetters() {
        String path = tile.getImageResourcesPath();
        assertEquals("it/unibo/caesena/images/tiles/city.png", path);

        assertFalse(tile.isPlaced());
        tile.setPosition(position);
        assertTrue(tile.isPlaced());
        assertEquals(position, tile.getPosition().get());
    }

    @Test
    public void testRotation() {
        assertEquals(tile.getRotationCount(), 0);

        tile.rotateClockwise();
        //TODO controllare rotazione 

        assertEquals(tile.getRotationCount(), 1);
    }
}
