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
        //TODO controllare rotazione
        Tile tile2 = new TileBuilder(TileType.CITY_EDGE)
            .city(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown,
            TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .field(List.of(TileSection.UpRight, TileSection.UpCenter, TileSection.UpLeft,
            TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown, TileSection.Center))
            .build();

        System.out.println(tile.toString());
        System.out.println(tile2.toString());
        
        for (TileSection section : TileSection.values()) {
            assertTrue(tile.getGameSet(section).getType().equals(tile2.getGameSet(section).getType()));
        }
        
        assertEquals(tile.getRotationCount(), 1);
    }

    @Test
    public void TestSectionClose() {
        var section = TileSection.UpCenter;
        tile.closeSection(section);
        assertTrue(tile.isSectionClosed(section));
    }

    @Test
    public void TestOppositeSection() {
        TileSection section = TileSection.DownLeft;
        assertEquals(TileSection.getOpposite(section), TileSection.UpLeft);
    }
}
