package it.unibo.caesena.model.tile;

public interface TileFactory {

    public Tile createCityEdge();

    public Tile createCityEdgeRoad();

    public Tile createCityLarge();

    public Tile createCityLargeRoad();

    public Tile createCity();

    public Tile createCitySideDoubleNext();

    public Tile createCitySideDouble();

    public Tile createCitySideJunction();

    public Tile createCitySide();

    public Tile createCitySideRoad();

    public Tile createCitySideTurnLeft();

    public Tile createCitySideTurnRight();

    public Tile createCityTube();

    public Tile createMonastery();

    public Tile createMonasteryRoad();

    public Tile createRoadJunctionLarge();

    public Tile createRoadJunctionSmall();

    public Tile createRoadStraight();

    public Tile createRoadTurn();

    public Tile createCityEdgePennant();

    public Tile createCityEdgeRoadPennant();

    public Tile createCityLargePennant();

    public Tile createCityLargeRoadPennant();

    public Tile createCityPennant();

    public Tile createCityTubePennant();
}
