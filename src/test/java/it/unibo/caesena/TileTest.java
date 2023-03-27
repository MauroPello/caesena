package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileBuilder;
import it.unibo.caesena.model.tile.TileFactoryWithBuilder;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.model.tile.TileType;
import it.unibo.caesena.utils.Pair;

final class TileTest {

    private static Tile tile;
    private static Pair<Integer, Integer> position;
    static String SEP;

    @BeforeAll
    public static void init() {
        tile = new TileFactoryWithBuilder().createCityEdge();
        position = new Pair<>(1, 1);
        SEP = File.separator;
    }

    @Test
    public void testGetters() {
        assertFalse(tile.isPlaced());
        tile.setPosition(position);
        assertTrue(tile.isPlaced());
        assertEquals(position, tile.getPosition().get());
    }

    @Test
    public void testRotation() {
        assertEquals(tile.getRotationCount(), 0);

        tile.rotateClockwise();
        Tile tile2 = new TileBuilder(TileType.CITY_EDGE)
            .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
            TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
            .field(List.of(TileSection.UP_RIGHT, TileSection.UP_CENTER, TileSection.UP_LEFT,
            TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN, TileSection.CENTER))
            .build();

        for (TileSection section : TileSection.values()) {
            assertEquals(tile2.getGameSet(section).getType(), tile.getGameSet(section).getType());
        }
        
        assertEquals(tile.getRotationCount(), 1);
    }

    @Test
    public void TestSectionClose() {
        var section = TileSection.UP_CENTER;
        tile.closeSection(section);
        assertTrue(tile.isSectionClosed(section));
        var section2 = TileSection.UP_CENTER;
        assertTrue(tile.isSectionClosed(section2));
    }

    @Test
    public void TestOppositeSection() {
        TileSection section = TileSection.DOWN_LEFT;
        assertEquals(TileSection.UP_LEFT, TileSection.getOpposite(section));
    }

    @Test
    public void TestSectionShift() {
        TileSection section = TileSection.UP_LEFT;
        assertEquals(TileSection.LEFT_UP, TileSection.previous(section));
        section = TileSection.LEFT_UP;
        assertEquals(TileSection.UP_LEFT, TileSection.next(section));
    }
}
