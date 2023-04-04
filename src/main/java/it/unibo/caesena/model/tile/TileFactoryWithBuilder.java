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
    public Tile createCityEdge() {
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
    public Tile createCityEdgePennant() {
        final Tile tile = new TileBuilder(TileType.CITY_EDGE_PENNANT, mediator)
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
    public Tile createCityEdgeRoad() {
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
    public Tile createCityEdgeRoadPennant() {
        final Tile tile = new TileBuilder(TileType.CITY_EDGE_ROAD_PENNANT, mediator)
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
    public Tile createCityLarge() {
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
    public Tile createCityLargePennant() {
        final Tile tile = new TileBuilder(TileType.CITY_LARGE_PENNANT, mediator)
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
    public Tile createCityLargeRoad() {
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
    public Tile createCityLargeRoadPennant() {
        final Tile tile = new TileBuilder(TileType.CITY_LARGE_ROAD_PENNANT, mediator)
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
    public Tile createCity() {
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
    public Tile createCityPennant() {
        final Tile tile = new TileBuilder(TileType.CITY_PENNANT, mediator)
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
    public Tile createCitySideDoubleNext() {
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
    public Tile createCitySideDouble() {
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
    public Tile createCitySideJunction() {
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
    public Tile createCitySide() {
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
    public Tile createCitySideRoad() {
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
    public Tile createCitySideTurnLeft() {
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
    public Tile createCitySideTurnRight() {
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
    public Tile createCityTube() {
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
    public Tile createCityTubePennant() {
        final Tile tile = new TileBuilder(TileType.CITY_TUBE_PENNANT, mediator)
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
    public Tile createMonastery() {
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
    public Tile createMonasteryRoad() {
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
    public Tile createRoadJunctionLarge() {
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
    public Tile createRoadJunctionSmall() {
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
    public Tile createRoadStraight() {
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
    public Tile createRoadTurn() {
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
