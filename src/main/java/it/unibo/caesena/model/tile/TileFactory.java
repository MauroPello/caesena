package it.unibo.caesena.model.tile;

/**
 * An interface defining a method for each MutableTile part of the normal version of
 * the game.
 */
public interface TileFactory {

    /**
     * Creates a MutableTile with type CITY_EDGE.
     *
     * @return a MutableTile with type CITY_EDGE
     */
    MutableTile createCityEdge();

    /**
     * Creates a MutableTile with type CITY_EDGE_ROAD.
     *
     * @return a MutableTile with type CITY_EDGE_ROAD
     */
    MutableTile createCityEdgeRoad();

    /**
     * Creates a MutableTile with type CITY_LARGE.
     *
     * @return a MutableTile with type CITY_LARGE
     */
    MutableTile createCityLarge();

    /**
     * Creates a MutableTile with type CITY_LARGE_ROAD.
     *
     * @return a MutableTile with type CITY_LARGE_ROAD
     */
    MutableTile createCityLargeRoad();

    /**
     * Creates a MutableTile with type CITY.
     *
     * @return a MutableTile with type CITY
     */
    MutableTile createCity();

    /**
     * Creates a MutableTile with type CITY_SIDE_DOUBLE_NEXT.
     *
     * @return a MutableTile with type CITY_SIDE_DOUBLE_NEXT
     */
    MutableTile createCitySideDoubleNext();

    /**
     * Creates a MutableTile with type CITY_SIDE_DOUBLE.
     *
     * @return a MutableTile with type CITY_SIDE_DOUBLE
     */
    MutableTile createCitySideDouble();

    /**
     * Creates a MutableTile with type CITY_SIDE_JUNCTION.
     *
     * @return a MutableTile with type CITY_SIDE_JUNCTION
     */
    MutableTile createCitySideJunction();

    /**
     * Creates a MutableTile with type CITY_SIDE.
     *
     * @return a MutableTile with type CITY_SIDE
     */
    MutableTile createCitySide();

    /**
     * Creates a MutableTile with type CITY_SIDE_ROAD.
     *
     * @return a MutableTile with type CITY_SIDE_ROAD
     */
    MutableTile createCitySideRoad();

    /**
     * Creates a MutableTile with type CITY_SIDE_TURN_LEFT.
     *
     * @return a MutableTile with type CITY_SIDE_TURN_LEFT
     */
    MutableTile createCitySideTurnLeft();

    /**
     * Creates a MutableTile with type CITY_SIDE_TURN_RIGHT.
     *
     * @return a MutableTile with type CITY_SIDE_TURN_RIGHT
     */
    MutableTile createCitySideTurnRight();

    /**
     * Creates a MutableTile with type CITY_TUBE.
     *
     * @return a MutableTile with type CITY_TUBE
     */
    MutableTile createCityTube();

    /**
     * Creates a MutableTile with type MONASTERY.
     *
     * @return a MutableTile with type MONASTERY
     */
    MutableTile createMonastery();

    /**
     * Creates a MutableTile with type MONASTERY_ROAD.
     *
     * @return a MutableTile with type MONASTERY_ROAD
     */
    MutableTile createMonasteryRoad();

    /**
     * Creates a MutableTile with type ROAD_JUNCTION_LARGE.
     *
     * @return a MutableTile with type ROAD_JUNCTION_LARGE
     */
    MutableTile createRoadJunctionLarge();

    /**
     * Creates a MutableTile with type ROAD_JUNCTION_SMALL.
     *
     * @return a MutableTile with type ROAD_JUNCTION_SMALL
     */
    MutableTile createRoadJunctionSmall();

    /**
     * Creates a MutableTile with type ROAD_STRAIGHT.
     *
     * @return a MutableTile with type ROAD_STRAIGHT
     */
    MutableTile createRoadStraight();

    /**
     * Creates a MutableTile with type ROAD_TURN.
     *
     * @return a MutableTile with type ROAD_TURN
     */
    MutableTile createRoadTurn();

    /**
     * Creates a MutableTile with type CITY_EDGE_PENNANT.
     *
     * @return a MutableTile with type CITY_EDGE_PENNANT
     */
    MutableTile createCityEdgePennant();

    /**
     * Creates a MutableTile with type CITY_EDGE_ROAD_PENNANT.
     *
     * @return a MutableTile with type CITY_EDGE_ROAD_PENNANT
     */
    MutableTile createCityEdgeRoadPennant();

    /**
     * Creates a MutableTile with type CITY_LARGE_PENNANT.
     *
     * @return a MutableTile with type CITY_LARGE_PENNANT
     */
    MutableTile createCityLargePennant();

    /**
     * Creates a MutableTile with type CITY_LARGE_ROAD_PENNANT.
     *
     * @return a MutableTile with type CITY_LARGE_ROAD_PENNANT
     */
    MutableTile createCityLargeRoadPennant();

    /**
     * Creates a MutableTile with type CITY_PENNANT.
     *
     * @return a MutableTile with type CITY_PENNANT
     */
    MutableTile createCityPennant();

    /**
     * Creates a MutableTile with type CITY_TUBE_PENNANT.
     *
     * @return a MutableTile with type CITY_TUBE_PENNANT
     */
    MutableTile createCityTubePennant();
}
