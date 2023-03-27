package it.unibo.caesena.model.tile;

import java.lang.reflect.Method;

import it.unibo.caesena.utils.StringUtil;

public enum TileType {
    CITY_EDGE,
    CITY_EDGE_PENNANT,
    CITY_EDGE_ROAD,
    CITY_EDGE_ROAD_PENNANT,
    CITY_LARGE,
    CITY_LARGE_PENNANT,
    CITY_LARGE_ROAD,
    CITY_LARGE_ROAD_PENNANT,
    CITY,
    CITY_PENNANT,
    CITY_SIDE_DOUBLE_NEXT,
    CITY_SIDE_DOUBLE,
    CITY_SIDE_JUNCTION,
    CITY_SIDE,
    CITY_SIDE_ROAD,
    CITY_SIDE_TURN_LEFT,
    CITY_SIDE_TURN_RIGHT,
    CITY_TUBE,
    CITY_TUBE_PENNANT,
    MONASTERY,
    MONASTERY_ROAD,
    ROAD_JUNCTION_LARGE,
    ROAD_JUNCTION_SMALL,
    ROAD_STRAIGHT,
    ROAD_TURN;

    public Tile createTile(final TileFactory tileFactory) {
        String methodName = "create";
        String[] words = this.name().split("_");
        for (String word : words) {
            methodName += StringUtil.capitalize(word);
        }

        try {
            Method method = TileFactory.class.getMethod(methodName);
            return (Tile) method.invoke(tileFactory);
        } catch (Exception e) {
            throw new IllegalStateException("Error using reflection, devs fault");
        }
    }
}
