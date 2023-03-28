package it.unibo.caesena.model.tile;

public interface TileFactory {

    Tile createCityEdge();

    Tile createCityEdgeRoad();

    Tile createCityLarge();

    Tile createCityLargeRoad();

    Tile createCity();

    Tile createCitySideDoubleNext();

    Tile createCitySideDouble();

    Tile createCitySideJunction();

    Tile createCitySide();

    Tile createCitySideRoad();

    Tile createCitySideTurnLeft();

    Tile createCitySideTurnRight();

    Tile createCityTube();

    Tile createMonastery();

    Tile createMonasteryRoad();

    Tile createRoadJunctionLarge();

    Tile createRoadJunctionSmall();

    Tile createRoadStraight();

    Tile createRoadTurn();

    Tile createCityEdgePennant();

    Tile createCityEdgeRoadPennant();

    Tile createCityLargePennant();

    Tile createCityLargeRoadPennant();

    Tile createCityPennant();

    Tile createCityTubePennant();
}
