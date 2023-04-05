package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.GameSetTileMediator;
import it.unibo.caesena.model.GameSetTileMediatorImpl;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;
import it.unibo.caesena.model.tile.MutableTile;
import it.unibo.caesena.model.tile.TileFactoryWithBuilder;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.Pair;

final class TileTest {

    private static MutableTile tile;

    @BeforeAll
    static void init() {
        final GameSetTileMediator mediator = new GameSetTileMediatorImpl(new GameSetFactoryImpl());
        final var tileMap = new TileFactoryWithBuilder().createCityEdge();
        tile = tileMap.getX();
        tileMap.getY().forEach((k,v) -> mediator.addSections(k, tile, v));
    }

    @Test
    void testGetters() {
        final Pair<Integer, Integer> position = new Pair<>(1, 1);
        assertFalse(tile.isPlaced());
        tile.setPosition(position);
        assertTrue(tile.isPlaced());
        assertEquals(position, tile.getPosition().get());
    }

    @Test
    void testSectionClose() {
        final var section = TileSection.UP_CENTER;
        tile.closeSection(section);
        assertTrue(tile.isSectionClosed(section));
        final var section2 = TileSection.UP_CENTER;
        assertTrue(tile.isSectionClosed(section2));
    }

    @Test
    void testSectionShift() {
        TileSection section = TileSection.UP_LEFT;
        assertEquals(TileSection.LEFT_UP, TileSection.previous(section));
        section = TileSection.LEFT_UP;
        assertEquals(TileSection.UP_LEFT, TileSection.next(section));
    }
}
