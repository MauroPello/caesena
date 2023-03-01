package it.unibo.caesena.model.tile;

import java.util.List;

public class TileFactoryWithBuilder implements TileFactory {

    @Override
    public Tile createCityEdge() {
        return new TileBuilder("city-edge")
            .city(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown, 
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight, 
            TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown))
            .build();
    }

    @Override
    public Tile createCityEdgeRoad() {
        return new TileBuilder("city-edge-road")
            .city(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown, 
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.DownLeft, TileSection.LeftDown)) 
            .field(List.of(TileSection.LeftUp, TileSection.DownRight))
            .road(List.of(TileSection.LeftCenter, TileSection.DownCenter))
            .build();
    }

    @Override
    public Tile createCityLarge() {
        return new TileBuilder("city-large")
            .city(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown, 
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight,
                TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown))
            .field(List.of(TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .build();
    }

    @Override
    public Tile createCityLargeRoad() {
        return new TileBuilder("city-large-road")
            .city(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown, 
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight,
                TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown))
            .field(List.of(TileSection.DownLeft))
            .field(List.of(TileSection.DownRight))
            .road(List.of(TileSection.DownCenter))
            .build();
    }

    @Override
    public Tile createCity() {
        return new TileBuilder("city")
            .city(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown, 
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight,
                TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown,
                TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .build();
    }

    @Override
    public Tile createCitySideDoubleNext() {
        return new TileBuilder("city-side-double-next")
            .city(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .city(List.of(TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown))
            .field(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown,
                TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .build();
    }

    @Override
    public Tile createCitySideDouble() {
        return new TileBuilder("city-side-double")
            .city(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .city(List.of(TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .field(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown,
                TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown))
            .build();
    }

    @Override
    public Tile createCitySideJunction() {
        return new TileBuilder("city-side-junction")
            .city(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.DownLeft, TileSection.LeftDown))
            .field(List.of(TileSection.DownRight, TileSection.RightDown))
            .field(List.of(TileSection.LeftUp, TileSection.RightUp))
            .road(List.of(TileSection.DownCenter))
            .road(List.of(TileSection.RightCenter))
            .road(List.of(TileSection.LeftCenter))
            .build();
    }

    @Override
    public Tile createCitySide() {
        return new TileBuilder("city-side")
            .city(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown,
                TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown,
                TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .build();
    }

    @Override
    public Tile createCitySideRoad() {
        return new TileBuilder("city-side-road")
            .city(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.LeftDown, TileSection.RightDown,
                TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .field(List.of(TileSection.LeftUp, TileSection.RightUp))
            .road(List.of(TileSection.RightCenter, TileSection.LeftCenter))
            .build();
    }

    @Override
    public Tile createCitySideTurnLeft() {
        return new TileBuilder("city-side-turn-left")
            .city(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.LeftUp, TileSection.DownRight,
                TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown))
            .field(List.of(TileSection.LeftDown, TileSection.DownLeft))
            .road(List.of(TileSection.LeftCenter, TileSection.DownCenter))
            .build();
    }

    @Override
    public Tile createCitySideTurnRight() {
        return new TileBuilder("city-side-turn-right")
            .city(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.RightUp, TileSection.DownLeft,
                TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown))
            .field(List.of(TileSection.RightDown, TileSection.DownRight))
            .road(List.of(TileSection.RightCenter, TileSection.DownCenter))
            .build();
    }

    @Override
    public Tile createCityTube() {
        return new TileBuilder("city-tube")
            .city(List.of(TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown))
            .city(List.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown))
            .field(List.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .field(List.of(TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .build();
    }

    @Override
    public Tile createMonastery() {
        return new TileBuilder("monastery")
            .field(List.of(TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown,
                TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown,
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight,
                TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight))
            .monastery(List.of(TileSection.Center))
            .build();
    }

    @Override
    public Tile createMonasteryRoad() {
        return new TileBuilder("monastery-road")
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
        return new TileBuilder("road-junction-large")
            .road(List.of(TileSection.UpCenter))
            .road(List.of(TileSection.DownCenter))
            .road(List.of(TileSection.LeftCenter))
            .road(List.of(TileSection.RightCenter))
            .field(List.of(TileSection.DownLeft, TileSection.LeftDown))
            .field(List.of(TileSection.DownRight, TileSection.RightDown))
            .field(List.of(TileSection.LeftUp, TileSection.UpLeft))
            .field(List.of(TileSection.UpRight, TileSection.RightUp))
            .build();
    }

    @Override
    public Tile createRoadJunctionSmall() {
        return new TileBuilder("road-junction-small")
            .road(List.of(TileSection.DownCenter))
            .road(List.of(TileSection.LeftCenter))
            .road(List.of(TileSection.RightCenter))
            .field(List.of(TileSection.DownLeft, TileSection.LeftDown))
            .field(List.of(TileSection.DownRight, TileSection.RightDown))
            .field(List.of(TileSection.LeftUp, TileSection.RightUp,
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .build();
    }

    @Override
    public Tile createRoadStraight() {
        return new TileBuilder("road-straight")
            .road(List.of(TileSection.DownCenter, TileSection.UpCenter))
            .field(List.of(TileSection.UpLeft, TileSection.DownLeft, 
                TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown))
            .field(List.of(TileSection.UpRight, TileSection.DownRight,
                TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown))
            .build();
    }

    @Override
    public Tile createRoadTurn() {
        return new TileBuilder("road-turn")
            .field(List.of(TileSection.DownRight, TileSection.LeftUp,
                TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown,
                TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight))
            .road(List.of(TileSection.DownCenter, TileSection.LeftCenter))
            .field(List.of(TileSection.LeftDown, TileSection.DownLeft))
            .build();
    }
    
}
