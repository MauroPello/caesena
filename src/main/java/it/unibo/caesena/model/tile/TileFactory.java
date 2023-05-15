package it.unibo.caesena.model.tile;

import java.util.Map;
import java.util.Set;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.utils.Pair;

/**
 * An interface defining a method for each MutableTile part of the normal version of
 * the game.
 */
public interface TileFactory {

    /**
     * Creates a MutableTile with type CITY_EDGE.
     *
     * @return a pair containing a MutableTile with type CITY_EDGE and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityEdge(TileType type);

    /**
     * Creates a MutableTile with type CITY_EDGE_ROAD.
     *
     * @return a pair containing a MutableTile with type CITY_EDGE_ROAD and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityEdgeRoad(TileType type);

    /**
     * Creates a MutableTile with type CITY_LARGE.
     *
     * @return a pair containing a MutableTile with type CITY_LARGE and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityLarge(TileType type);

    /**
     * Creates a MutableTile with type CITY_LARGE_ROAD.
     *
     * @return a pair containing a MutableTile with type CITY_LARGE_ROAD and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityLargeRoad(TileType type);

    /**
     * Creates a MutableTile with type CITY.
     *
     * @return a pair containing a MutableTile with type CITY and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCity(TileType type);

    /**
     * Creates a MutableTile with type CITY_SIDE_DOUBLE_NEXT.
     *
     * @return a pair containing a MutableTile with type CITY_SIDE_DOUBLE_NEXT and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideDoubleNext(TileType type);

    /**
     * Creates a MutableTile with type CITY_SIDE_DOUBLE.
     *
     * @return a pair containing a MutableTile with type CITY_SIDE_DOUBLE and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideDouble(TileType type);

    /**
     * Creates a MutableTile with type CITY_SIDE_JUNCTION.
     *
     * @return a pair containing a MutableTile with type CITY_SIDE_JUNCTION and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideJunction(TileType type);

    /**
     * Creates a MutableTile with type CITY_SIDE.
     *
     * @return a pair containing a MutableTile with type CITY_SIDE and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySide(TileType type);

    /**
     * Creates a MutableTile with type CITY_SIDE_ROAD.
     *
     * @return a pair containing a MutableTile with type CITY_SIDE_ROAD and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideRoad(TileType type);

    /**
     * Creates a MutableTile with type CITY_SIDE_TURN_LEFT.
     *
     * @return a pair containing a MutableTile with type CITY_SIDE_TURN_LEFT and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideTurnLeft(TileType type);

    /**
     * Creates a MutableTile with type CITY_SIDE_TURN_RIGHT.
     *
     * @return a pair containing a MutableTile with type CITY_SIDE_TURN_RIGHT and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCitySideTurnRight(TileType type);

    /**
     * Creates a MutableTile with type CITY_TUBE.
     *
     * @return a pair containing a MutableTile with type CITY_TUBE and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityTube(TileType type);

    /**
     * Creates a MutableTile with type MONASTERY.
     *
     * @return a pair containing a MutableTile with type MONASTERY and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createMonastery(TileType type);

    /**
     * Creates a MutableTile with type MONASTERY_ROAD.
     *
     * @return a pair containing a MutableTile with type MONASTERY_ROAD and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createMonasteryRoad(TileType type);

    /**
     * Creates a MutableTile with type ROAD_JUNCTION_LARGE.
     *
     * @return a pair containing a MutableTile with type ROAD_JUNCTION_LARGE and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createRoadJunctionLarge(TileType type);

    /**
     * Creates a MutableTile with type ROAD_JUNCTION_SMALL.
     *
     * @return a pair containing a MutableTile with type ROAD_JUNCTION_SMALL and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createRoadJunctionSmall(TileType type);

    /**
     * Creates a MutableTile with type ROAD_STRAIGHT.
     *
     * @return a pair containing a MutableTile with type ROAD_STRAIGHT and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createRoadStraight(TileType type);

    /**
     * Creates a MutableTile with type ROAD_TURN.
     *
     * @return a pair containing a MutableTile with type ROAD_TURN and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createRoadTurn(TileType type);

    /**
     * Creates a MutableTile with type CITY_EDGE_PENNANT.
     *
     * @return a pair containing a MutableTile with type CITY_EDGE_PENNANT and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityEdgePennant(TileType type);

    /**
     * Creates a MutableTile with type CITY_EDGE_ROAD_PENNANT.
     *
     * @return a pair containing a MutableTile with type CITY_EDGE_ROAD_PENNANT and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityEdgeRoadPennant(TileType type);

    /**
     * Creates a MutableTile with type CITY_LARGE_PENNANT.
     *
     * @return a pair containing a MutableTile with type CITY_LARGE_PENNANT and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityLargePennant(TileType type);

    /**
     * Creates a MutableTile with type CITY_LARGE_ROAD_PENNANT.
     *
     * @return a pair containing a MutableTile with type CITY_LARGE_ROAD_PENNANT and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityLargeRoadPennant(TileType type);

    /**
     * Creates a MutableTile with type CITY_PENNANT.
     *
     * @return a pair containing a MutableTile with type CITY_PENNANT and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityPennant(TileType type);

    /**
     * Creates a MutableTile with type CITY_TUBE_PENNANT.
     *
     * @return a pair containing a MutableTile with type CITY_TUBE_PENNANT and a map
     *         containing a set of tileSections for every gameSet inside the newly
     *         created tile
     */
    Pair<MutableTile, Map<GameSet, Set<TileSection>>> createCityTubePennant(TileType type);
}
