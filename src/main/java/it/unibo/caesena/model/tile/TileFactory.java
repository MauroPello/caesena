package it.unibo.caesena.model.tile;

/**
 * An interface defining a method for each Tile part of the normal version of
 * the game.
 */
public interface TileFactory {

    /**
     * Creates a Tile with type CITY_EDGE.
     *
     * @return a Tile with type CITY_EDGE
     */
    Tile createCityEdge();

    /**
     * Creates a Tile with type CITY_EDGE_ROAD.
     *
     * @return a Tile with type CITY_EDGE_ROAD
     */
    Tile createCityEdgeRoad();

    /**
     * Creates a Tile with type CITY_LARGE.
     *
     * @return a Tile with type CITY_LARGE
     */
    Tile createCityLarge();

    /**
     * Creates a Tile with type CITY_LARGE_ROAD.
     *
     * @return a Tile with type CITY_LARGE_ROAD
     */
    Tile createCityLargeRoad();

    /**
     * Creates a Tile with type CITY.
     *
     * @return a Tile with type CITY
     */
    Tile createCity();

    /**
     * Creates a Tile with type CITY_SIDE_DOUBLE_NEXT.
     *
     * @return a Tile with type CITY_SIDE_DOUBLE_NEXT
     */
    Tile createCitySideDoubleNext();

    /**
     * Creates a Tile with type CITY_SIDE_DOUBLE.
     *
     * @return a Tile with type CITY_SIDE_DOUBLE
     */
    Tile createCitySideDouble();

    /**
     * Creates a Tile with type CITY_SIDE_JUNCTION.
     *
     * @return a Tile with type CITY_SIDE_JUNCTION
     */
    Tile createCitySideJunction();

    /**
     * Creates a Tile with type CITY_SIDE.
     *
     * @return a Tile with type CITY_SIDE
     */
    Tile createCitySide();

    /**
     * Creates a Tile with type CITY_SIDE_ROAD.
     *
     * @return a Tile with type CITY_SIDE_ROAD
     */
    Tile createCitySideRoad();

    /**
     * Creates a Tile with type CITY_SIDE_TURN_LEFT.
     *
     * @return a Tile with type CITY_SIDE_TURN_LEFT
     */
    Tile createCitySideTurnLeft();

    /**
     * Creates a Tile with type CITY_SIDE_TURN_RIGHT.
     *
     * @return a Tile with type CITY_SIDE_TURN_RIGHT
     */
    Tile createCitySideTurnRight();

    /**
     * Creates a Tile with type CITY_TUBE.
     *
     * @return a Tile with type CITY_TUBE
     */
    Tile createCityTube();

    /**
     * Creates a Tile with type MONASTERY.
     *
     * @return a Tile with type MONASTERY
     */
    Tile createMonastery();

    /**
     * Creates a Tile with type MONASTERY_ROAD.
     *
     * @return a Tile with type MONASTERY_ROAD
     */
    Tile createMonasteryRoad();

    /**
     * Creates a Tile with type ROAD_JUNCTION_LARGE.
     *
     * @return a Tile with type ROAD_JUNCTION_LARGE
     */
    Tile createRoadJunctionLarge();

    /**
     * Creates a Tile with type ROAD_JUNCTION_SMALL.
     *
     * @return a Tile with type ROAD_JUNCTION_SMALL
     */
    Tile createRoadJunctionSmall();

    /**
     * Creates a Tile with type ROAD_STRAIGHT.
     *
     * @return a Tile with type ROAD_STRAIGHT
     */
    Tile createRoadStraight();

    /**
     * Creates a Tile with type ROAD_TURN.
     *
     * @return a Tile with type ROAD_TURN
     */
    Tile createRoadTurn();

    /**
     * Creates a Tile with type CITY_EDGE_PENNANT.
     *
     * @return a Tile with type CITY_EDGE_PENNANT
     */
    Tile createCityEdgePennant();

    /**
     * Creates a Tile with type CITY_EDGE_ROAD_PENNANT.
     *
     * @return a Tile with type CITY_EDGE_ROAD_PENNANT
     */
    Tile createCityEdgeRoadPennant();

    /**
     * Creates a Tile with type CITY_LARGE_PENNANT.
     *
     * @return a Tile with type CITY_LARGE_PENNANT
     */
    Tile createCityLargePennant();

    /**
     * Creates a Tile with type CITY_LARGE_ROAD_PENNANT.
     *
     * @return a Tile with type CITY_LARGE_ROAD_PENNANT
     */
    Tile createCityLargeRoadPennant();

    /**
     * Creates a Tile with type CITY_PENNANT.
     *
     * @return a Tile with type CITY_PENNANT
     */
    Tile createCityPennant();

    /**
     * Creates a Tile with type CITY_TUBE_PENNANT.
     *
     * @return a Tile with type CITY_TUBE_PENNANT
     */
    Tile createCityTubePennant();
}
