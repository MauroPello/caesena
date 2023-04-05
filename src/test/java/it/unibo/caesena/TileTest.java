package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.GameSetTileMediator;
import it.unibo.caesena.model.GameSetTileMediatorImpl;
import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;
import it.unibo.caesena.model.tile.MutableTile;
import it.unibo.caesena.model.tile.TileBuilder;
import it.unibo.caesena.model.tile.TileFactoryWithBuilder;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.model.tile.TileType;
import it.unibo.caesena.utils.Pair;

final class TileTest {

    private static MutableTile tile;
    private static GameSetTileMediator mediator;
    private static Pair<Integer, Integer> position;

    @BeforeAll
    static void init() {
        mediator = new GameSetTileMediatorImpl(new GameSetFactoryImpl());
        final var tileMap = new TileFactoryWithBuilder().createCityEdge();
        tile = tileMap.getX();
        tileMap.getY().forEach((k,v) -> mediator.addSections(k, tile, v));
        position = new Pair<>(1, 1);
    }

    @Test
    void testGetters() {
        assertFalse(tile.isPlaced());
        tile.setPosition(position);
        assertTrue(tile.isPlaced());
        assertEquals(position, tile.getPosition().get());
    }

    @Test
    void testRotation() {
        mediator.rotateTileClockwise(tile);
        assertEquals(tile.getRotationCount() , 1);
        final Pair<MutableTile, Map<GameSet, Set<TileSection>>> tile2 = new TileBuilder(TileType.CITY_EDGE)
                .city(Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .field(Set.of(TileSection.UP_RIGHT, TileSection.UP_CENTER, TileSection.UP_LEFT,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN, TileSection.CENTER))
                .build();

        for (final var entry : tile2.getY().entrySet()) {
            for (final var section : entry.getValue()) {
                assertEquals(entry.getKey().getType(), mediator.getGameSetInSection(tile, section).getType());
            }
        }
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
