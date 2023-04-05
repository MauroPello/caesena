package it.unibo.caesena.model.tile;

import java.util.Map;
import java.util.Set;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.utils.Pair;

/**
 * A class implemeting a TileFactory where Pennants are only placed in cities
 * and increase their values by 2 points, as the rules say.
 */
public final class TileFactoryWithBuilder implements TileFactory {

    private static final int PENNANT_POINTS = 2;

    /**
     * Adds a Pennant to the GameSet in a certain section.
     *
     * @param gameSets map of all gameSets with their respective TileSections
     * @param section in which to add a Pennant
     */
    private void addPennantToGameSet(final Map<GameSet, Set<TileSection>> gameSets, final TileSection section) {
        for (final var entry : gameSets.entrySet()) {
            if (entry.getValue().contains(section)) {
                entry.getKey().addPoints(PENNANT_POINTS);
                return;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityEdge() {
        return new TileBuilder(TileType.CITY_EDGE)
                .city(Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(Set.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN, TileSection.CENTER))
                .close(Set.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityEdgePennant() {
        final Pair<MutableTile, Map<GameSet, Set<TileSection>>> tile = new TileBuilder(TileType.CITY_EDGE_PENNANT)
                .city(Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(Set.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN, TileSection.CENTER))
                .close(Set.of(TileSection.CENTER))
                .build();

        addPennantToGameSet(tile.getY(), TileSection.UP_RIGHT);
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityEdgeRoad() {
        return new TileBuilder(TileType.CITY_EDGE_ROAD)
                .city(Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(Set.of(TileSection.LEFT_UP, TileSection.DOWN_RIGHT))
                .field(Set.of(TileSection.DOWN_LEFT, TileSection.LEFT_DOWN))
                .road(Set.of(TileSection.LEFT_CENTER, TileSection.CENTER, TileSection.DOWN_CENTER))
                .close(Set.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityEdgeRoadPennant() {
        final Pair<MutableTile, Map<GameSet, Set<TileSection>>> tile = new TileBuilder(TileType.CITY_EDGE_ROAD_PENNANT)
                .city(Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(Set.of(TileSection.DOWN_LEFT, TileSection.LEFT_DOWN))
                .field(Set.of(TileSection.LEFT_UP, TileSection.DOWN_RIGHT))
                .road(Set.of(TileSection.LEFT_CENTER, TileSection.CENTER, TileSection.DOWN_CENTER))
                .close(Set.of(TileSection.CENTER))
                .build();

        addPennantToGameSet(tile.getY(), TileSection.UP_RIGHT);
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityLarge() {
        return new TileBuilder(TileType.CITY_LARGE)
                .city(Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
                .field(Set.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .close(Set.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityLargePennant() {
        final Pair<MutableTile, Map<GameSet, Set<TileSection>>> tile = new TileBuilder(TileType.CITY_LARGE_PENNANT)
                .city(Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
                .field(Set.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .close(Set.of(TileSection.CENTER))
                .build();

        addPennantToGameSet(tile.getY(), TileSection.UP_RIGHT);
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityLargeRoad() {
        return new TileBuilder(TileType.CITY_LARGE_ROAD)
                .city(Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
                .field(Set.of(TileSection.DOWN_RIGHT))
                .field(Set.of(TileSection.DOWN_LEFT))
                .road(Set.of(TileSection.DOWN_CENTER))
                .close(Set.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityLargeRoadPennant() {
        final Pair<MutableTile, Map<GameSet, Set<TileSection>>> tile = new TileBuilder(TileType.CITY_LARGE_ROAD_PENNANT)
                .city(Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
                .field(Set.of(TileSection.DOWN_LEFT))
                .field(Set.of(TileSection.DOWN_RIGHT))
                .road(Set.of(TileSection.DOWN_CENTER))
                .close(Set.of(TileSection.CENTER))
                .build();

        addPennantToGameSet(tile.getY(), TileSection.UP_RIGHT);
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCity() {
        return new TileBuilder(TileType.CITY)
                .city(Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                        TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .close(Set.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityPennant() {
        final Pair<MutableTile, Map<GameSet, Set<TileSection>>> tile = new TileBuilder(TileType.CITY_PENNANT)
                .city(Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                        TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .close(Set.of(TileSection.CENTER))
                .build();

        addPennantToGameSet(tile.getY(), TileSection.UP_RIGHT);
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideDoubleNext() {
        return new TileBuilder(TileType.CITY_SIDE_DOUBLE_NEXT)
                .city(Set.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .city(Set.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
                .field(Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT, TileSection.CENTER))
                .close(Set.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideDouble() {
        return new TileBuilder(TileType.CITY_SIDE_DOUBLE)
                .city(Set.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .city(Set.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .field(Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN, TileSection.CENTER))
                .close(Set.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideJunction() {
        return new TileBuilder(TileType.CITY_SIDE_JUNCTION)
                .city(Set.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(Set.of(TileSection.DOWN_LEFT, TileSection.LEFT_DOWN))
                .field(Set.of(TileSection.DOWN_RIGHT, TileSection.RIGHT_DOWN))
                .field(Set.of(TileSection.LEFT_UP, TileSection.RIGHT_UP))
                .road(Set.of(TileSection.DOWN_CENTER))
                .road(Set.of(TileSection.RIGHT_CENTER))
                .road(Set.of(TileSection.LEFT_CENTER))
                .junction(Set.of(TileSection.CENTER))
                .close(Set.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySide() {
        return new TileBuilder(TileType.CITY_SIDE)
                .city(Set.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(Set.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, TileSection.CENTER,
                        TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .close(Set.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideRoad() {
        return new TileBuilder(TileType.CITY_SIDE_ROAD)
                .city(Set.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(Set.of(TileSection.LEFT_DOWN, TileSection.RIGHT_DOWN,
                        TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .field(Set.of(TileSection.LEFT_UP, TileSection.RIGHT_UP))
                .road(Set.of(TileSection.RIGHT_CENTER, TileSection.CENTER, TileSection.LEFT_CENTER))
                .close(Set.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideTurnLeft() {
        return new TileBuilder(TileType.CITY_SIDE_TURN_LEFT)
                .city(Set.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(Set.of(TileSection.LEFT_UP, TileSection.DOWN_RIGHT, TileSection.CENTER,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN))
                .field(Set.of(TileSection.LEFT_DOWN, TileSection.DOWN_LEFT))
                .road(Set.of(TileSection.LEFT_CENTER, TileSection.DOWN_CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideTurnRight() {
        return new TileBuilder(TileType.CITY_SIDE_TURN_RIGHT)
                .city(Set.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(Set.of(TileSection.RIGHT_UP, TileSection.DOWN_LEFT, TileSection.CENTER,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
                .field(Set.of(TileSection.RIGHT_DOWN, TileSection.DOWN_RIGHT))
                .road(Set.of(TileSection.RIGHT_CENTER, TileSection.DOWN_CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityTube() {
        return new TileBuilder(TileType.CITY_TUBE)
                .city(Set.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, TileSection.CENTER))
                .field(Set.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(Set.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .close(Set.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityTubePennant() {
        final Pair<MutableTile, Map<GameSet, Set<TileSection>>> tile = new TileBuilder(TileType.CITY_TUBE_PENNANT)
                .city(Set.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, TileSection.CENTER))
                .field(Set.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(Set.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .close(Set.of(TileSection.CENTER))
                .build();

        addPennantToGameSet(tile.getY(), TileSection.RIGHT_CENTER);
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createMonastery() {
        return new TileBuilder(TileType.MONASTERY)
                .field(Set.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT,
                        TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .monastery(Set.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createMonasteryRoad() {
        return new TileBuilder(TileType.MONASTERY_ROAD)
                .field(Set.of(TileSection.DOWN_LEFT, TileSection.DOWN_RIGHT,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .monastery(Set.of(TileSection.CENTER))
                .road(Set.of(TileSection.DOWN_CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createRoadJunctionLarge() {
        return new TileBuilder(TileType.ROAD_JUNCTION_LARGE)
                .road(Set.of(TileSection.UP_CENTER))
                .road(Set.of(TileSection.DOWN_CENTER))
                .road(Set.of(TileSection.LEFT_CENTER))
                .road(Set.of(TileSection.RIGHT_CENTER))
                .field(Set.of(TileSection.DOWN_LEFT, TileSection.LEFT_DOWN))
                .field(Set.of(TileSection.DOWN_RIGHT, TileSection.RIGHT_DOWN))
                .field(Set.of(TileSection.LEFT_UP, TileSection.UP_LEFT))
                .field(Set.of(TileSection.UP_RIGHT, TileSection.RIGHT_UP))
                .junction(Set.of(TileSection.CENTER))
                .close(Set.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createRoadJunctionSmall() {
        return new TileBuilder(TileType.ROAD_JUNCTION_SMALL)
                .road(Set.of(TileSection.DOWN_CENTER))
                .road(Set.of(TileSection.LEFT_CENTER))
                .road(Set.of(TileSection.RIGHT_CENTER))
                .field(Set.of(TileSection.DOWN_LEFT, TileSection.LEFT_DOWN))
                .field(Set.of(TileSection.DOWN_RIGHT, TileSection.RIGHT_DOWN))
                .field(Set.of(TileSection.LEFT_UP, TileSection.RIGHT_UP,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .junction(Set.of(TileSection.CENTER))
                .close(Set.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createRoadStraight() {
        return new TileBuilder(TileType.ROAD_STRAIGHT)
                .road(Set.of(TileSection.DOWN_CENTER, TileSection.CENTER, TileSection.UP_CENTER))
                .field(Set.of(TileSection.UP_LEFT, TileSection.DOWN_LEFT,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
                .field(Set.of(TileSection.UP_RIGHT, TileSection.DOWN_RIGHT,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN))
                .close(Set.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createRoadTurn() {
        return new TileBuilder(TileType.ROAD_TURN)
                .field(Set.of(TileSection.DOWN_RIGHT, TileSection.LEFT_UP,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .road(Set.of(TileSection.DOWN_CENTER, TileSection.CENTER, TileSection.LEFT_CENTER))
                .field(Set.of(TileSection.LEFT_DOWN, TileSection.DOWN_LEFT))
                .close(Set.of(TileSection.CENTER))
                .build();
    }

}
