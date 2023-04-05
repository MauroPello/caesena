package it.unibo.caesena.model.tile;

import java.util.List;

import it.unibo.caesena.model.GameSetTileMediator;

/**
 * A class implemeting a TileFactory where Pennants are only placed in cities
 * and increase their values by 2 points, as the rules say.
 */
public final class TileFactoryWithBuilder implements TileFactory {

    private static final int PENNANT_POINTS = 2;
    private final GameSetTileMediator mediator;

    /**
     * Public constructor that accepts a mediator.
     *
     * @param mediator used to add points in tiles with Pennants and create GameSets
     */
    public TileFactoryWithBuilder(final GameSetTileMediator mediator) {
        this.mediator = mediator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCityEdge() {
        return new TileBuilder(TileType.CITY_EDGE, mediator)
                .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN, TileSection.CENTER))
                .close(List.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCityEdgePennant() {
        final MutableTile tile = new TileBuilder(TileType.CITY_EDGE_PENNANT, mediator)
                .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN, TileSection.CENTER))
                .close(List.of(TileSection.CENTER))
                .build();

        mediator.getGameSetInSection(tile, TileSection.UP_RIGHT).addPoints(PENNANT_POINTS);
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCityEdgeRoad() {
        return new TileBuilder(TileType.CITY_EDGE_ROAD, mediator)
                .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(List.of(TileSection.LEFT_UP, TileSection.DOWN_RIGHT))
                .field(List.of(TileSection.DOWN_LEFT, TileSection.LEFT_DOWN))
                .road(List.of(TileSection.LEFT_CENTER, TileSection.CENTER, TileSection.DOWN_CENTER))
                .close(List.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCityEdgeRoadPennant() {
        final MutableTile tile = new TileBuilder(TileType.CITY_EDGE_ROAD_PENNANT, mediator)
                .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(List.of(TileSection.DOWN_LEFT, TileSection.LEFT_DOWN))
                .field(List.of(TileSection.LEFT_UP, TileSection.DOWN_RIGHT))
                .road(List.of(TileSection.LEFT_CENTER, TileSection.CENTER, TileSection.DOWN_CENTER))
                .close(List.of(TileSection.CENTER))
                .build();

        mediator.getGameSetInSection(tile, TileSection.UP_RIGHT).addPoints(PENNANT_POINTS);
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCityLarge() {
        return new TileBuilder(TileType.CITY_LARGE, mediator)
                .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
                .field(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .close(List.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCityLargePennant() {
        final MutableTile tile = new TileBuilder(TileType.CITY_LARGE_PENNANT, mediator)
                .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
                .field(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .close(List.of(TileSection.CENTER))
                .build();

        mediator.getGameSetInSection(tile, TileSection.UP_RIGHT).addPoints(PENNANT_POINTS);
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCityLargeRoad() {
        return new TileBuilder(TileType.CITY_LARGE_ROAD, mediator)
                .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
                .field(List.of(TileSection.DOWN_RIGHT))
                .field(List.of(TileSection.DOWN_LEFT))
                .road(List.of(TileSection.DOWN_CENTER))
                .close(List.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCityLargeRoadPennant() {
        final MutableTile tile = new TileBuilder(TileType.CITY_LARGE_ROAD_PENNANT, mediator)
                .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
                .field(List.of(TileSection.DOWN_LEFT))
                .field(List.of(TileSection.DOWN_RIGHT))
                .road(List.of(TileSection.DOWN_CENTER))
                .close(List.of(TileSection.CENTER))
                .build();

        mediator.getGameSetInSection(tile, TileSection.UP_RIGHT).addPoints(PENNANT_POINTS);
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCity() {
        return new TileBuilder(TileType.CITY, mediator)
                .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                        TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .close(List.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCityPennant() {
        final MutableTile tile = new TileBuilder(TileType.CITY_PENNANT, mediator)
                .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                        TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .close(List.of(TileSection.CENTER))
                .build();

        mediator.getGameSetInSection(tile, TileSection.UP_RIGHT).addPoints(PENNANT_POINTS);
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCitySideDoubleNext() {
        return new TileBuilder(TileType.CITY_SIDE_DOUBLE_NEXT, mediator)
                .city(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .city(List.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
                .field(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT, TileSection.CENTER))
                .close(List.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCitySideDouble() {
        return new TileBuilder(TileType.CITY_SIDE_DOUBLE, mediator)
                .city(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .city(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .field(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN, TileSection.CENTER))
                .close(List.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCitySideJunction() {
        return new TileBuilder(TileType.CITY_SIDE_JUNCTION, mediator)
                .city(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(List.of(TileSection.DOWN_LEFT, TileSection.LEFT_DOWN))
                .field(List.of(TileSection.DOWN_RIGHT, TileSection.RIGHT_DOWN))
                .field(List.of(TileSection.LEFT_UP, TileSection.RIGHT_UP))
                .road(List.of(TileSection.DOWN_CENTER))
                .road(List.of(TileSection.RIGHT_CENTER))
                .road(List.of(TileSection.LEFT_CENTER))
                .junction(List.of(TileSection.CENTER))
                .close(List.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCitySide() {
        return new TileBuilder(TileType.CITY_SIDE, mediator)
                .city(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(List.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, TileSection.CENTER,
                        TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .close(List.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCitySideRoad() {
        return new TileBuilder(TileType.CITY_SIDE_ROAD, mediator)
                .city(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(List.of(TileSection.LEFT_DOWN, TileSection.RIGHT_DOWN,
                        TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .field(List.of(TileSection.LEFT_UP, TileSection.RIGHT_UP))
                .road(List.of(TileSection.RIGHT_CENTER, TileSection.CENTER, TileSection.LEFT_CENTER))
                .close(List.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCitySideTurnLeft() {
        return new TileBuilder(TileType.CITY_SIDE_TURN_LEFT, mediator)
                .city(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(List.of(TileSection.LEFT_UP, TileSection.DOWN_RIGHT, TileSection.CENTER,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN))
                .field(List.of(TileSection.LEFT_DOWN, TileSection.DOWN_LEFT))
                .road(List.of(TileSection.LEFT_CENTER, TileSection.DOWN_CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCitySideTurnRight() {
        return new TileBuilder(TileType.CITY_SIDE_TURN_RIGHT, mediator)
                .city(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(List.of(TileSection.RIGHT_UP, TileSection.DOWN_LEFT, TileSection.CENTER,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
                .field(List.of(TileSection.RIGHT_DOWN, TileSection.DOWN_RIGHT))
                .road(List.of(TileSection.RIGHT_CENTER, TileSection.DOWN_CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCityTube() {
        return new TileBuilder(TileType.CITY_TUBE, mediator)
                .city(List.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, TileSection.CENTER))
                .field(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .close(List.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createCityTubePennant() {
        final MutableTile tile = new TileBuilder(TileType.CITY_TUBE_PENNANT, mediator)
                .city(List.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, TileSection.CENTER))
                .field(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .field(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .close(List.of(TileSection.CENTER))
                .build();

        mediator.getGameSetInSection(tile, TileSection.RIGHT_CENTER).addPoints(PENNANT_POINTS);
        return tile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createMonastery() {
        return new TileBuilder(TileType.MONASTERY, mediator)
                .field(List.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT,
                        TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
                .monastery(List.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createMonasteryRoad() {
        return new TileBuilder(TileType.MONASTERY_ROAD, mediator)
                .field(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_RIGHT,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .monastery(List.of(TileSection.CENTER))
                .road(List.of(TileSection.DOWN_CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createRoadJunctionLarge() {
        return new TileBuilder(TileType.ROAD_JUNCTION_LARGE, mediator)
                .road(List.of(TileSection.UP_CENTER))
                .road(List.of(TileSection.DOWN_CENTER))
                .road(List.of(TileSection.LEFT_CENTER))
                .road(List.of(TileSection.RIGHT_CENTER))
                .field(List.of(TileSection.DOWN_LEFT, TileSection.LEFT_DOWN))
                .field(List.of(TileSection.DOWN_RIGHT, TileSection.RIGHT_DOWN))
                .field(List.of(TileSection.LEFT_UP, TileSection.UP_LEFT))
                .field(List.of(TileSection.UP_RIGHT, TileSection.RIGHT_UP))
                .junction(List.of(TileSection.CENTER))
                .close(List.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createRoadJunctionSmall() {
        return new TileBuilder(TileType.ROAD_JUNCTION_SMALL, mediator)
                .road(List.of(TileSection.DOWN_CENTER))
                .road(List.of(TileSection.LEFT_CENTER))
                .road(List.of(TileSection.RIGHT_CENTER))
                .field(List.of(TileSection.DOWN_LEFT, TileSection.LEFT_DOWN))
                .field(List.of(TileSection.DOWN_RIGHT, TileSection.RIGHT_DOWN))
                .field(List.of(TileSection.LEFT_UP, TileSection.RIGHT_UP,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .junction(List.of(TileSection.CENTER))
                .close(List.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createRoadStraight() {
        return new TileBuilder(TileType.ROAD_STRAIGHT, mediator)
                .road(List.of(TileSection.DOWN_CENTER, TileSection.CENTER, TileSection.UP_CENTER))
                .field(List.of(TileSection.UP_LEFT, TileSection.DOWN_LEFT,
                        TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
                .field(List.of(TileSection.UP_RIGHT, TileSection.DOWN_RIGHT,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN))
                .close(List.of(TileSection.CENTER))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutableTile createRoadTurn() {
        return new TileBuilder(TileType.ROAD_TURN, mediator)
                .field(List.of(TileSection.DOWN_RIGHT, TileSection.LEFT_UP,
                        TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                        TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
                .road(List.of(TileSection.DOWN_CENTER, TileSection.CENTER, TileSection.LEFT_CENTER))
                .field(List.of(TileSection.LEFT_DOWN, TileSection.DOWN_LEFT))
                .close(List.of(TileSection.CENTER))
                .build();
    }

}
