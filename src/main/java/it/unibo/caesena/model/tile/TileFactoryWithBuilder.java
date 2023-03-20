package it.unibo.caesena.model.tile;

import java.util.List;

public class TileFactoryWithBuilder implements TileFactory {

    @Override
    public Tile createCityEdge() {
        return new TileBuilder(TileType.CITY_EDGE)
            .city(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown, 
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight, 
            TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown, TileSection.Center))
            .build();
    }

    @Override
    public Tile createCityEdgeRoad() {
        return new TileBuilder(TileType.CITY_EDGE_ROAD)
            .city(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown, 
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.DownLeft, TileSection.LeftDown)) 
            .field(List.of(TileSection.LeftUp, TileSection.DownRight))
            .road(List.of(TileSection.LeftCenter, TileSection.Center, TileSection.DownCenter))
            .build();
    }

    @Override
    public Tile createCityLarge() {
        return new TileBuilder(TileType.CITY_LARGE)
            .city(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown, 
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight, TileSection.Center,
                TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown))
            .field(List.of(TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .close(List.of(TileSection.Center))
            .build();
    }

    @Override
    public Tile createCityLargeRoad() {
        return new TileBuilder(TileType.CITY_LARGE_ROAD)
            .city(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown, 
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight, TileSection.Center,
                TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown))
            .field(List.of(TileSection.DownLeft))
            .field(List.of(TileSection.DownRight))
            .road(List.of(TileSection.DownCenter))
            .close(List.of(TileSection.Center))
            .build();
    }

    @Override
    public Tile createCity() {
        return new TileBuilder(TileType.CITY)
            .city(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown, 
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight, TileSection.Center,
                TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown,
                TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .close(List.of(TileSection.Center))
            .build();
    }

    @Override
    public Tile createCitySideDoubleNext() {
        return new TileBuilder(TileType.CITY_SIDE_DOUBLE_NEXT)
            .city(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .city(List.of(TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown))
            .field(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown,
                TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight, TileSection.Center))
            .build();
    }

    @Override
    public Tile createCitySideDouble() {
        return new TileBuilder(TileType.CITY_SIDE_DOUBLE)
            .city(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .city(List.of(TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .field(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown,
                TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown, TileSection.Center))
            .build();
    }

    @Override
    public Tile createCitySideJunction() {
        return new TileBuilder(TileType.CITY_SIDE_JUNCTION)
            .city(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.DownLeft, TileSection.LeftDown))
            .field(List.of(TileSection.DownRight, TileSection.RightDown))
            .field(List.of(TileSection.LeftUp, TileSection.RightUp))
            .road(List.of(TileSection.DownCenter))
            .road(List.of(TileSection.RightCenter))
            .road(List.of(TileSection.LeftCenter))
            .junction(List.of(TileSection.Center))
            .build();
    }

    @Override
    public Tile createCitySide() {
        return new TileBuilder(TileType.CITY_SIDE)
            .city(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown,
                TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown, TileSection.Center,
                TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .build();
    }

    @Override
    public Tile createCitySideRoad() {
        return new TileBuilder(TileType.CITY_SIDE_ROAD)
            .city(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.LeftDown, TileSection.RightDown,
                TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .field(List.of(TileSection.LeftUp, TileSection.RightUp))
            .road(List.of(TileSection.RightCenter, TileSection.Center, TileSection.LeftCenter))
            .build();
    }

    @Override
    public Tile createCitySideTurnLeft() {
        return new TileBuilder(TileType.CITY_SIDE_TURN_LEFT)
            .city(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.LeftUp, TileSection.DownRight,
                TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown))
            .field(List.of(TileSection.LeftDown, TileSection.DownLeft))
            .road(List.of(TileSection.LeftCenter, TileSection.Center, TileSection.DownCenter))
            .build();
    }

    @Override
    public Tile createCitySideTurnRight() {
        return new TileBuilder(TileType.CITY_SIDE_TURN_RIGHT)
            .city(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.RightUp, TileSection.DownLeft,
                TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown))
            .field(List.of(TileSection.RightDown, TileSection.DownRight))
            .road(List.of(TileSection.RightCenter, TileSection.Center, TileSection.DownCenter))
            .build();
    }

    @Override
    public Tile createCityTube() {
        return new TileBuilder(TileType.CITY_TUBE)
            .city(List.of(TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown,
                TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown, TileSection.Center))
            .field(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .close(List.of(TileSection.Center))
            .build();
    }

    @Override
    public Tile createMonastery() {
        return new TileBuilder(TileType.MONASTERY)
            .field(List.of(TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown,
                TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown,
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight,
                TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .monastery(List.of(TileSection.Center))
            .build();
    }

    @Override
    public Tile createMonasteryRoad() {
        return new TileBuilder(TileType.MONASTERY_ROAD)
            .field(List.of(TileSection.DownLeft, TileSection.DownRight, 
                TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown,
                TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown,
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .monastery(List.of(TileSection.Center))
            .road(List.of(TileSection.DownCenter))
            .build();
    }

    @Override
    public Tile createRoadJunctionLarge() {
        return new TileBuilder(TileType.ROAD_JUNCTION_LARGE)
            .road(List.of(TileSection.UpCenter))
            .road(List.of(TileSection.DownCenter))
            .road(List.of(TileSection.LeftCenter))
            .road(List.of(TileSection.RightCenter))
            .field(List.of(TileSection.DownLeft, TileSection.LeftDown))
            .field(List.of(TileSection.DownRight, TileSection.RightDown))
            .field(List.of(TileSection.LeftUp, TileSection.UpLeft))
            .field(List.of(TileSection.UpRight, TileSection.RightUp))
            .junction(List.of(TileSection.Center))
            .build();
    }

    @Override
    public Tile createRoadJunctionSmall() {
        return new TileBuilder(TileType.ROAD_JUNCTION_SMALL)
            .road(List.of(TileSection.DownCenter))
            .road(List.of(TileSection.LeftCenter))
            .road(List.of(TileSection.RightCenter))
            .field(List.of(TileSection.DownLeft, TileSection.LeftDown))
            .field(List.of(TileSection.DownRight, TileSection.RightDown))
            .field(List.of(TileSection.LeftUp, TileSection.RightUp,
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .junction(List.of(TileSection.Center))
            .build();
    }

    @Override
    public Tile createRoadStraight() {
        return new TileBuilder(TileType.ROAD_STRAIGHT)
            .road(List.of(TileSection.DownCenter, TileSection.Center, TileSection.UpCenter))
            .field(List.of(TileSection.UpLeft, TileSection.DownLeft, 
                TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown))
            .field(List.of(TileSection.UpRight, TileSection.DownRight,
                TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown))
            .build();
    }

    @Override
    public Tile createRoadTurn() {
        return new TileBuilder(TileType.ROAD_TURN)
            .field(List.of(TileSection.DownRight, TileSection.LeftUp,
                TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown,
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .road(List.of(TileSection.DownCenter, TileSection.Center, TileSection.LeftCenter))
            .field(List.of(TileSection.LeftDown, TileSection.DownLeft))
            .build();
    }
    
}
