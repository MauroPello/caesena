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
    private void addPennantToGameSet(final Map<GameSet, Set<TileSection>> gameSets, final TileSectionType section) {
        for (final var entry : gameSets.entrySet()) {
            if (entry.getValue().stream().map(TileSection::getType).anyMatch(s -> s.equals(section))) {
                entry.getKey().addPoints(PENNANT_POINTS);
                return;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityEdge(final TileType type) {
        return new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .field(Set.of(TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("DOWN_RIGHT"),
                        TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN"), TileSectionType.getFromName("CENTER")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityEdgePennant(final TileType type) {
        final Pair<MutableTile, Map<GameSet, Set<TileSection>>> tile = new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .field(Set.of(TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("DOWN_RIGHT"),
                        TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN"), TileSectionType.getFromName("CENTER")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();

        addPennantToGameSet(tile.getY(), TileSectionType.getFromName("UP_RIGHT"));
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityEdgeRoad(final TileType type) {
        return new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .field(Set.of(TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("DOWN_RIGHT")))
                .field(Set.of(TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("LEFT_DOWN")))
                .road(Set.of(TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("CENTER"), TileSectionType.getFromName("DOWN_CENTER")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityEdgeRoadPennant(final TileType type) {
        final Pair<MutableTile, Map<GameSet, Set<TileSection>>> tile = new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .field(Set.of(TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("LEFT_DOWN")))
                .field(Set.of(TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("DOWN_RIGHT")))
                .road(Set.of(TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("CENTER"), TileSectionType.getFromName("DOWN_CENTER")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();

        addPennantToGameSet(tile.getY(), TileSectionType.getFromName("UP_RIGHT"));
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityLarge(final TileType type) {
        return new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT"), TileSectionType.getFromName("CENTER"),
                        TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN")))
                .field(Set.of(TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("DOWN_RIGHT")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityLargePennant(final TileType type) {
        final Pair<MutableTile, Map<GameSet, Set<TileSection>>> tile = new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT"), TileSectionType.getFromName("CENTER"),
                        TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN")))
                .field(Set.of(TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("DOWN_RIGHT")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();

        addPennantToGameSet(tile.getY(), TileSectionType.getFromName("UP_RIGHT"));
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityLargeRoad(final TileType type) {
        return new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT"), TileSectionType.getFromName("CENTER"),
                        TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN")))
                .field(Set.of(TileSectionType.getFromName("DOWN_RIGHT")))
                .field(Set.of(TileSectionType.getFromName("DOWN_LEFT")))
                .road(Set.of(TileSectionType.getFromName("DOWN_CENTER")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityLargeRoadPennant(final TileType type) {
        final Pair<MutableTile, Map<GameSet, Set<TileSection>>> tile = new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT"), TileSectionType.getFromName("CENTER"),
                        TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN")))
                .field(Set.of(TileSectionType.getFromName("DOWN_LEFT")))
                .field(Set.of(TileSectionType.getFromName("DOWN_RIGHT")))
                .road(Set.of(TileSectionType.getFromName("DOWN_CENTER")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();

        addPennantToGameSet(tile.getY(), TileSectionType.getFromName("UP_RIGHT"));
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCity(final TileType type) {
        return new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT"), TileSectionType.getFromName("CENTER"),
                        TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN"),
                        TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("DOWN_RIGHT")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityPennant(final TileType type) {
        final Pair<MutableTile, Map<GameSet, Set<TileSection>>> tile = new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT"), TileSectionType.getFromName("CENTER"),
                        TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN"),
                        TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("DOWN_RIGHT")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();

        addPennantToGameSet(tile.getY(), TileSectionType.getFromName("UP_RIGHT"));
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideDoubleNext(final TileType type) {
        return new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .city(Set.of(TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN")))
                .field(Set.of(TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("DOWN_RIGHT"), TileSectionType.getFromName("CENTER")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideDouble(final TileType type) {
        return new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .city(Set.of(TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("DOWN_RIGHT")))
                .field(Set.of(TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN"), TileSectionType.getFromName("CENTER")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideJunction(final TileType type) {
        return new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .field(Set.of(TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("LEFT_DOWN")))
                .field(Set.of(TileSectionType.getFromName("DOWN_RIGHT"), TileSectionType.getFromName("RIGHT_DOWN")))
                .field(Set.of(TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("RIGHT_UP")))
                .road(Set.of(TileSectionType.getFromName("DOWN_CENTER")))
                .road(Set.of(TileSectionType.getFromName("RIGHT_CENTER")))
                .road(Set.of(TileSectionType.getFromName("LEFT_CENTER")))
                .junction(Set.of(TileSectionType.getFromName("CENTER")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySide(final TileType type) {
        return new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .field(Set.of(TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN"),
                        TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"), TileSectionType.getFromName("CENTER"),
                        TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("DOWN_RIGHT")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideRoad(final TileType type) {
        return new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .field(Set.of(TileSectionType.getFromName("LEFT_DOWN"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("DOWN_RIGHT")))
                .field(Set.of(TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("RIGHT_UP")))
                .road(Set.of(TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("CENTER"), TileSectionType.getFromName("LEFT_CENTER")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideTurnLeft(final TileType type) {
        return new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .field(Set.of(TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("DOWN_RIGHT"), TileSectionType.getFromName("CENTER"),
                        TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN")))
                .field(Set.of(TileSectionType.getFromName("LEFT_DOWN"), TileSectionType.getFromName("DOWN_LEFT")))
                .road(Set.of(TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("DOWN_CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideTurnRight(final TileType type) {
        return new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .field(Set.of(TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("CENTER"),
                        TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN")))
                .field(Set.of(TileSectionType.getFromName("RIGHT_DOWN"), TileSectionType.getFromName("DOWN_RIGHT")))
                .road(Set.of(TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("DOWN_CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityTube(final TileType type) {
        return new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN"),
                        TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"), TileSectionType.getFromName("CENTER")))
                .field(Set.of(TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .field(Set.of(TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("DOWN_RIGHT")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityTubePennant(final TileType type) {
        final Pair<MutableTile, Map<GameSet, Set<TileSection>>> tile = new TileBuilder(type)
                .city(Set.of(TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN"),
                        TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"), TileSectionType.getFromName("CENTER")))
                .field(Set.of(TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .field(Set.of(TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("DOWN_RIGHT")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();

        addPennantToGameSet(tile.getY(), TileSectionType.getFromName("RIGHT_CENTER"));
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createMonastery(final TileType type) {
        return new TileBuilder(type)
                .field(Set.of(TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN"),
                        TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT"),
                        TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("DOWN_RIGHT")))
                .monastery(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createMonasteryRoad(final TileType type) {
        return new TileBuilder(type)
                .field(Set.of(TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("DOWN_RIGHT"),
                        TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN"),
                        TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .monastery(Set.of(TileSectionType.getFromName("CENTER")))
                .road(Set.of(TileSectionType.getFromName("DOWN_CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createRoadJunctionLarge(final TileType type) {
        return new TileBuilder(type)
                .road(Set.of(TileSectionType.getFromName("UP_CENTER")))
                .road(Set.of(TileSectionType.getFromName("DOWN_CENTER")))
                .road(Set.of(TileSectionType.getFromName("LEFT_CENTER")))
                .road(Set.of(TileSectionType.getFromName("RIGHT_CENTER")))
                .field(Set.of(TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("LEFT_DOWN")))
                .field(Set.of(TileSectionType.getFromName("DOWN_RIGHT"), TileSectionType.getFromName("RIGHT_DOWN")))
                .field(Set.of(TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("UP_LEFT")))
                .field(Set.of(TileSectionType.getFromName("UP_RIGHT"), TileSectionType.getFromName("RIGHT_UP")))
                .junction(Set.of(TileSectionType.getFromName("CENTER")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createRoadJunctionSmall(final TileType type) {
        return new TileBuilder(type)
                .road(Set.of(TileSectionType.getFromName("DOWN_CENTER")))
                .road(Set.of(TileSectionType.getFromName("LEFT_CENTER")))
                .road(Set.of(TileSectionType.getFromName("RIGHT_CENTER")))
                .field(Set.of(TileSectionType.getFromName("DOWN_LEFT"), TileSectionType.getFromName("LEFT_DOWN")))
                .field(Set.of(TileSectionType.getFromName("DOWN_RIGHT"), TileSectionType.getFromName("RIGHT_DOWN")))
                .field(Set.of(TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("RIGHT_UP"),
                        TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .junction(Set.of(TileSectionType.getFromName("CENTER")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createRoadStraight(final TileType type) {
        return new TileBuilder(type)
                .road(Set.of(TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("CENTER"), TileSectionType.getFromName("UP_CENTER")))
                .field(Set.of(TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("DOWN_LEFT"),
                        TileSectionType.getFromName("LEFT_UP"), TileSectionType.getFromName("LEFT_CENTER"), TileSectionType.getFromName("LEFT_DOWN")))
                .field(Set.of(TileSectionType.getFromName("UP_RIGHT"), TileSectionType.getFromName("DOWN_RIGHT"),
                        TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createRoadTurn(final TileType type) {
        return new TileBuilder(type)
                .field(Set.of(TileSectionType.getFromName("DOWN_RIGHT"), TileSectionType.getFromName("LEFT_UP"),
                        TileSectionType.getFromName("RIGHT_UP"), TileSectionType.getFromName("RIGHT_CENTER"), TileSectionType.getFromName("RIGHT_DOWN"),
                        TileSectionType.getFromName("UP_LEFT"), TileSectionType.getFromName("UP_CENTER"), TileSectionType.getFromName("UP_RIGHT")))
                .road(Set.of(TileSectionType.getFromName("DOWN_CENTER"), TileSectionType.getFromName("CENTER"), TileSectionType.getFromName("LEFT_CENTER")))
                .field(Set.of(TileSectionType.getFromName("LEFT_DOWN"), TileSectionType.getFromName("DOWN_LEFT")))
                .close(Set.of(TileSectionType.getFromName("CENTER")))
                .build();
    }

}
