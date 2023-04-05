package it.unibo.caesena.model.tile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.utils.StringUtil;

/**
 * Enum representing the different types of Tiles that can be created.
 */
public enum TileType {
    /**
     * Defines a Tile with the same city in two sides in the corner.
     */
    CITY_EDGE,
    /**
     * Defines a Tile with the same city in two sides in the corner and a road going
     * across the other two empty sides.
     */
    CITY_EDGE_ROAD,
    /**
     * Defines a Tile with the same city in three sides.
     */
    CITY_LARGE,
    /**
     * Defines a Tile with the same city in three sides and a road leading to it.
     */
    CITY_LARGE_ROAD,
    /**
     * Defines a Tile with the same city in all sides.
     */
    CITY,
    /**
     * Defines a Tile with two different cities next to each other on different sides.
     */
    CITY_SIDE_DOUBLE_NEXT,
    /**
     * Defines a Tile with two different cities on different opposing sides.
     */
    CITY_SIDE_DOUBLE,
    /**
     * Defines a Tile with a city in a side and a three-way junction below.
     */
    CITY_SIDE_JUNCTION,
    /**
     * Defines a Tile with a city in a side.
     */
    CITY_SIDE,
    /**
     * Defines a Tile with a city in a side and a road in the middle.
     */
    CITY_SIDE_ROAD,
    /**
     * Defines a Tile with a city in a side and a road turning left coming from below.
     */
    CITY_SIDE_TURN_LEFT,
    /**
     * Defines a Tile with a city in a side and a road turning right coming from below.
     */
    CITY_SIDE_TURN_RIGHT,
    /**
     * Defines a Tile with the same city going across two opposite sides.
     */
    CITY_TUBE,
    /**
     * Defines a Tile with a monastery in the center.
     */
    MONASTERY,
    /**
     * Defines a Tile with a monastery in the center and a road leading to it.
     */
    MONASTERY_ROAD,
    /**
     * Defines a Tile with a four-way junction inside.
     */
    ROAD_JUNCTION_LARGE,
    /**
     * Defines a Tile with a three-way junction inside.
     */
    ROAD_JUNCTION_SMALL,
    /**
     * Defines a Tile with a road going straight across.
     */
    ROAD_STRAIGHT,
    /**
     * Defines a Tile with a road turning inside.
     */
    ROAD_TURN,
    /**
     * Defines a Tile with the same city in two sides in the corner and a pennant
     * inside.
     */
    CITY_EDGE_PENNANT,
    /**
     * Defines a Tile with the same city in two sides in the corner, a road going
     * across the other two empty sides and a pennant inside the city.
     */
    CITY_EDGE_ROAD_PENNANT,
    /**
     * Defines a Tile with the same city in three sides and a pennant inside.
     */
    CITY_LARGE_PENNANT,
    /**
     * Defines a Tile with the same city in three sides, a road leading to it and a
     * pennant inside.
     */
    CITY_LARGE_ROAD_PENNANT,
    /**
     * Defines a Tile with the same city in all sides and a pennant inside.
     */
    CITY_PENNANT,
    /**
     * Defines a Tile with the same city going across two opposite sides and a
     * pennant inside.
     */
    CITY_TUBE_PENNANT;

    /**
     * Creates a new tile from the name of the enum on which it's called.
     * It uses {@link java.lang.reflect} to call the proper method inside the
     * factory to create the new tile.
     *
     * @param tileFactory used to create the new Tile
     * @return a new tile with the same type as the enum on which it's called
     */
    @SuppressWarnings("unchecked")
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> createTile(final TileFactory tileFactory) {
        final StringBuilder methodNameBuilder = new StringBuilder();
        methodNameBuilder.append("create");
        final String[] words = this.name().split("_");
        for (final String word : words) {
            methodNameBuilder.append(StringUtil.capitalize(word));
        }

        final String methodName = methodNameBuilder.toString();

        try {
            final Method method = TileFactory.class.getMethod(methodName);
            return (Pair<MutableTile, Map<GameSet, Set<TileSection>>>) method.invoke(tileFactory);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new IllegalStateException("Error using reflection, devs fault", e);
        }
    }
}
