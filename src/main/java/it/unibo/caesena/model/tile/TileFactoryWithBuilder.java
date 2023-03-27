package it.unibo.caesena.model.tile;

import java.util.List;

public class TileFactoryWithBuilder implements TileFactory {

    private final static int PENNANT_POINTS = 2;

    @Override
    public Tile createCityEdge() {
        return new TileBuilder(TileType.CITY_EDGE)
            .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, 
                TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
            .field(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT, 
            TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN, TileSection.CENTER))
            .close(List.of(TileSection.CENTER))
            .build();
    }

    @Override
    public Tile createCityEdgePennant() {
        Tile tile = new TileBuilder(TileType.CITY_EDGE_PENNANT)
            .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, 
                TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
            .field(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT, 
            TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN, TileSection.CENTER))
            .close(List.of(TileSection.CENTER))
            .build();
            
        tile.getGameSet(TileSection.UP_RIGHT).addPoints(PENNANT_POINTS);
        return tile;
    }

    @Override
    public Tile createCityEdgeRoad() {
        return new TileBuilder(TileType.CITY_EDGE_ROAD)
            .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, 
                TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
            .field(List.of(TileSection.DOWN_LEFT, TileSection.LEFT_DOWN)) 
            .field(List.of(TileSection.LEFT_UP, TileSection.DOWN_RIGHT))
            .road(List.of(TileSection.LEFT_CENTER, TileSection.CENTER, TileSection.DOWN_CENTER))
            .close(List.of(TileSection.CENTER))
            .build();
    }

    @Override
    public Tile createCityEdgeRoadPennant() {
        Tile tile = new TileBuilder(TileType.CITY_EDGE_ROAD_PENNANT)
            .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, 
                TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
            .field(List.of(TileSection.DOWN_LEFT, TileSection.LEFT_DOWN)) 
            .field(List.of(TileSection.LEFT_UP, TileSection.DOWN_RIGHT))
            .road(List.of(TileSection.LEFT_CENTER, TileSection.CENTER, TileSection.DOWN_CENTER))
            .close(List.of(TileSection.CENTER))
            .build();
        
        tile.getGameSet(TileSection.UP_RIGHT).addPoints(PENNANT_POINTS);
        return tile;
    }

    @Override
    public Tile createCityLarge() {
        return new TileBuilder(TileType.CITY_LARGE)
            .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, 
                TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
            .field(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
            .close(List.of(TileSection.CENTER))
            .build();
    }

    
    @Override
    public Tile createCityLargePennant() {
        Tile tile = new TileBuilder(TileType.CITY_LARGE_PENNANT)
            .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, 
                TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
            .field(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
            .close(List.of(TileSection.CENTER))
            .build();

        tile.getGameSet(TileSection.UP_RIGHT).addPoints(PENNANT_POINTS);
        return tile;
    }

    @Override
    public Tile createCityLargeRoad() {
        return new TileBuilder(TileType.CITY_LARGE_ROAD)
            .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, 
                TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
            .field(List.of(TileSection.DOWN_LEFT))
            .field(List.of(TileSection.DOWN_RIGHT))
            .road(List.of(TileSection.DOWN_CENTER))
            .close(List.of(TileSection.CENTER))
            .build();
    }

    @Override
    public Tile createCityLargeRoadPennant() {
        Tile tile = new TileBuilder(TileType.CITY_LARGE_ROAD_PENNANT)
            .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, 
                TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
            .field(List.of(TileSection.DOWN_LEFT))
            .field(List.of(TileSection.DOWN_RIGHT))
            .road(List.of(TileSection.DOWN_CENTER))
            .close(List.of(TileSection.CENTER))
            .build();

        tile.getGameSet(TileSection.UP_RIGHT).addPoints(PENNANT_POINTS);
        return tile;
    }

    @Override
    public Tile createCity() {
        return new TileBuilder(TileType.CITY)
            .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, 
                TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
            .close(List.of(TileSection.CENTER))
            .build();
    }

    @Override
    public Tile createCityPennant() {
        Tile tile = new TileBuilder(TileType.CITY_PENNANT)
            .city(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, 
                TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT, TileSection.CENTER,
                TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
            .close(List.of(TileSection.CENTER))
            .build();
        
        tile.getGameSet(TileSection.UP_RIGHT).addPoints(PENNANT_POINTS);
        return tile;
    }

    @Override
    public Tile createCitySideDoubleNext() {
        return new TileBuilder(TileType.CITY_SIDE_DOUBLE_NEXT)
            .city(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
            .city(List.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
            .field(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT, TileSection.CENTER))
            .close(List.of(TileSection.CENTER))
            .build();
    }

    @Override
    public Tile createCitySideDouble() {
        return new TileBuilder(TileType.CITY_SIDE_DOUBLE)
            .city(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
            .city(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
            .field(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN, TileSection.CENTER))
            .close(List.of(TileSection.CENTER))
            .build();
    }

    @Override
    public Tile createCitySideJunction() {
        return new TileBuilder(TileType.CITY_SIDE_JUNCTION)
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

    @Override
    public Tile createCitySide() {
        return new TileBuilder(TileType.CITY_SIDE)
            .city(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
            .field(List.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, TileSection.CENTER,
                TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
            .close(List.of(TileSection.CENTER))
            .build();
    }

    @Override
    public Tile createCitySideRoad() {
        return new TileBuilder(TileType.CITY_SIDE_ROAD)
            .city(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
            .field(List.of(TileSection.LEFT_DOWN, TileSection.RIGHT_DOWN,
                TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
            .field(List.of(TileSection.LEFT_UP, TileSection.RIGHT_UP))
            .road(List.of(TileSection.RIGHT_CENTER, TileSection.CENTER, TileSection.LEFT_CENTER))
            .close(List.of(TileSection.CENTER))
            .build();
    }

    @Override
    public Tile createCitySideTurnLeft() {
        return new TileBuilder(TileType.CITY_SIDE_TURN_LEFT)
            .city(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
            .field(List.of(TileSection.LEFT_UP, TileSection.DOWN_RIGHT,
                TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN))
            .field(List.of(TileSection.LEFT_DOWN, TileSection.DOWN_LEFT))
            .road(List.of(TileSection.LEFT_CENTER, TileSection.CENTER, TileSection.DOWN_CENTER))
            .close(List.of(TileSection.CENTER))
            .build();
    }

    @Override
    public Tile createCitySideTurnRight() {
        return new TileBuilder(TileType.CITY_SIDE_TURN_RIGHT)
            .city(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
            .field(List.of(TileSection.RIGHT_UP, TileSection.DOWN_LEFT,
                TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
            .field(List.of(TileSection.RIGHT_DOWN, TileSection.DOWN_RIGHT))
            .road(List.of(TileSection.RIGHT_CENTER, TileSection.CENTER, TileSection.DOWN_CENTER))
            .close(List.of(TileSection.CENTER))
            .build();
    }

    @Override
    public Tile createCityTube() {
        return new TileBuilder(TileType.CITY_TUBE)
            .city(List.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, TileSection.CENTER))
            .field(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
            .field(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
            .close(List.of(TileSection.CENTER))
            .build();
    }

    @Override
    public Tile createCityTubePennant() {
        Tile tile = new TileBuilder(TileType.CITY_TUBE_PENNANT)
            .city(List.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN, TileSection.CENTER))
            .field(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
            .field(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
            .close(List.of(TileSection.CENTER))
            .build();

        tile.getGameSet(TileSection.RIGHT_CENTER).addPoints(PENNANT_POINTS);
        return tile;
    }

    @Override
    public Tile createMonastery() {
        return new TileBuilder(TileType.MONASTERY)
            .field(List.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT,
                TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT))
            .monastery(List.of(TileSection.CENTER))
            .build();
    }

    @Override
    public Tile createMonasteryRoad() {
        return new TileBuilder(TileType.MONASTERY_ROAD)
            .field(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_RIGHT, 
                TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN,
                TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
            .monastery(List.of(TileSection.CENTER))
            .road(List.of(TileSection.DOWN_CENTER))
            .build();
    }

    @Override
    public Tile createRoadJunctionLarge() {
        return new TileBuilder(TileType.ROAD_JUNCTION_LARGE)
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

    @Override
    public Tile createRoadJunctionSmall() {
        return new TileBuilder(TileType.ROAD_JUNCTION_SMALL)
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

    @Override
    public Tile createRoadStraight() {
        return new TileBuilder(TileType.ROAD_STRAIGHT)
            .road(List.of(TileSection.DOWN_CENTER, TileSection.CENTER, TileSection.UP_CENTER))
            .field(List.of(TileSection.UP_LEFT, TileSection.DOWN_LEFT, 
                TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN))
            .field(List.of(TileSection.UP_RIGHT, TileSection.DOWN_RIGHT,
                TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN))
            .close(List.of(TileSection.CENTER))
            .build();
    }

    @Override
    public Tile createRoadTurn() {
        return new TileBuilder(TileType.ROAD_TURN)
            .field(List.of(TileSection.DOWN_RIGHT, TileSection.LEFT_UP,
                TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN,
                TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT))
            .road(List.of(TileSection.DOWN_CENTER, TileSection.CENTER, TileSection.LEFT_CENTER))
            .field(List.of(TileSection.LEFT_DOWN, TileSection.DOWN_LEFT))
            .close(List.of(TileSection.CENTER))
            .build();
    }
    
}
