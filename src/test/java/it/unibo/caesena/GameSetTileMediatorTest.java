package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.GameSetTileMediator;
import it.unibo.caesena.model.GameSetTileMediatorImpl;
import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;
import it.unibo.caesena.model.gameset.GameSetType;
import it.unibo.caesena.model.tile.MutableTile;
import it.unibo.caesena.model.tile.TileBuilder;
import it.unibo.caesena.model.tile.TileFactoryWithBuilder;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.model.tile.TileType;
import it.unibo.caesena.utils.Pair;

final class GameSetTileMediatorTest {

    private static Pair<MutableTile, Map<GameSet, Set<TileSection>>> tileMap;
    private static GameSetTileMediator mediator;

    @BeforeAll
    static void init() {
        mediator = new GameSetTileMediatorImpl(new GameSetFactoryImpl());
        tileMap = new TileFactoryWithBuilder().createCityEdge();
        tileMap.getY().forEach((k, v) -> mediator.addSections(k, tileMap.getX(), v));
    }

    @Test
    void testGetGameSetInSection() {
        assertEquals(GameSetType.FIELD, mediator.getGameSetInSection(tileMap.getX(), TileSection.CENTER).getType());
    }

    @Test
    void testGetGameSetsInTile() {
        assertEquals(tileMap.getY().keySet(), mediator.getGameSetsInTile(tileMap.getX()));
    }

    @Test
    void testGetAllGameSets() {
        assertEquals(tileMap.getY().keySet(), mediator.getAllGameSets());
    }

    @Test
    void testGetTilesFromGameSet() {
        for (final var gameSet : tileMap.getY().keySet()) {
            assertTrue(mediator.getTilesFromGameSet(gameSet).keySet().contains(tileMap.getX()));
        }
    }

    @Test
    void testRotateTileClockwise() {
        mediator.rotateTileClockwise(tileMap.getX());
        assertEquals(1, tileMap.getX().getRotationCount());
        final Pair<MutableTile, Map<GameSet, Set<TileSection>>> tile2 = new TileBuilder(TileType.CITY_EDGE)
                .city(Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .field(Set.of(TileSection.UP_RIGHT, TileSection.UP_CENTER, TileSection.UP_LEFT,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN, TileSection.CENTER))
                .build();

        for (final var entry : tile2.getY().entrySet()) {
            for (final var section : entry.getValue()) {
                assertEquals(entry.getKey().getType(), mediator.getGameSetInSection(tileMap.getX(), section).getType());
            }
        }
    }
}
