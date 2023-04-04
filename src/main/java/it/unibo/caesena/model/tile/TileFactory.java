package it.unibo.caesena.model.tile;

/**
 * An interface defining a method for each Tile part of the normal version of
 * the game.
 */
public interface TileFactory {

    /**
     * Creates a Tile with the same city in two sides in the corner.
     *
     * @return a Tile with the same city in two sides in the corner
     */
    Tile createCityEdge();

    /**
     * Creates a Tile with the same city in two sides in the corner and a road going
     * across the other two empty sides.
     *
     * @return a Tile with the same city in two sides in the corner and a road going
     *         across the other two empty sides
     */
    Tile createCityEdgeRoad();

    /**
     * Creates a Tile with the same city in three sides.
     *
     * @return a Tile with the same city in three sides
     */
    Tile createCityLarge();

    /**
     * Creates a Tile with the same city in three sides and a road leading to it.
     *
     * @return a Tile with the same city in three sides and a road leading to it
     */
    Tile createCityLargeRoad();

    /**
     * Creates a Tile with the same city in all sides.
     *
     * @return a Tile with the same city in all sides
     */
    Tile createCity();

    /**
     * Creates a Tile with two different cities next to each other on different sides.
     *
     * @return a Tile with two different cities next to each other on different sides
     */
    Tile createCitySideDoubleNext();

    /**
     * Creates a Tile with two different cities on different opposing sides.
     *
     * @return a Tile with two different cities on different opposing sides
     */
    Tile createCitySideDouble();

    /**
     * Creates a Tile with a city in a side and a three-way junction below.
     *
     * @return a Tile with a city in a side and a three-way junction below
     */
    Tile createCitySideJunction();

    /**
     * Creates a Tile with a city in a side.
     *
     * @return a Tile with a city in a side
     */
    Tile createCitySide();

    /**
     * Creates a Tile with a city in a side and a road in the middle.
     *
     * @return a Tile with a city in a side and a road in the middle
     */
    Tile createCitySideRoad();

    /**
     * Creates a Tile with a city in a side and a road turning left coming from below.
     *
     * @return a Tile with a city in a side and a road turning left coming from below
     */
    Tile createCitySideTurnLeft();

    /**
     * Creates a Tile with a city in a side and a road turning right coming from below.
     *
     * @return a Tile with a city in a side and a road turning right coming from below
     */
    Tile createCitySideTurnRight();

    /**
     * Creates a Tile with the same city going across two opposite sides.
     *
     * @return a Tile with the same city going across two opposite sides
     */
    Tile createCityTube();

    /**
     * Creates a Tile with a monastery in the center.
     *
     * @return a Tile with a monastery in the center
     */
    Tile createMonastery();

    /**
     * Creates a Tile with a monastery in the center and a road leading to it.
     *
     * @return a Tile with a monastery in the center and a road leading to it
     */
    Tile createMonasteryRoad();

    /**
     * Creates a Tile with a four-way junction inside.
     *
     * @return a Tile with a four-way junction inside
     */
    Tile createRoadJunctionLarge();

    /**
     * Creates a Tile with a three-way junction inside.
     *
     * @return a Tile with a three-way junction inside
     */
    Tile createRoadJunctionSmall();

    /**
     * Creates a Tile with a road going straight across.
     *
     * @return a Tile with a road going straight across
     */
    Tile createRoadStraight();

    /**
     * Creates a Tile with a road turning inside.
     *
     * @return a Tile with a road turning inside
     */
    Tile createRoadTurn();

    /**
     * Creates a Tile with the same city in two sides in the corner and a pennant
     * inside.
     *
     * @return a Tile with the same city in two sides in the corner and a pennant
     *         inside
     */
    Tile createCityEdgePennant();

    /**
     * Creates a Tile with the same city in two sides in the corner, a road going
     * across the other two empty sides and a pennant inside the city.
     *
     * @return a Tile with the same city in two sides in the corner, a road going
     *         across the other two empty sides and a pennant inside the city
     */
    Tile createCityEdgeRoadPennant();

    /**
     * Creates a Tile with the same city in three sides and a pennant inside.
     *
     * @return a Tile with the same city in three sides and a pennant inside
     */
    Tile createCityLargePennant();

    /**
     * Creates a Tile with the same city in three sides, a road leading to it and a
     * pennant inside.
     *
     * @return a Tile with the same city in three sides and a road leading to it and
     *         a pennant inside
     */
    Tile createCityLargeRoadPennant();

    /**
     * Creates a Tile with the same city in all sides and a pennant inside.
     *
     * @return a Tile with the same city in all sides and a pennant inside
     */
    Tile createCityPennant();

    /**
     * Creates a Tile with the same city going across two opposite sides and a
     * pennant inside.
     *
     * @return a Tile with the same city going across two opposite sides and a
     *         pennant inside
     */
    Tile createCityTubePennant();
}
