package it.unibo.caesena.model;

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
            .field(List.of(TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight, 
                TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown))
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
            .field(List.of(TileSection.DownLeft, TileSection.DownRight))
            .road(List.of(TileSection.DownCenter))
            .build();
    }

    @Override
    public Tile createCity() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCity'");
    }

    @Override
    public Tile createCitySideDoubleNext() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCitySideDoubleNext'");
    }

    @Override
    public Tile createCitySideDouble() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCitySideDouble'");
    }

    @Override
    public Tile createCitySideJunction() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCitySideJunction'");
    }

    @Override
    public Tile createCitySide() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCitySide'");
    }

    @Override
    public Tile createCitySideRoad() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCitySideRoad'");
    }

    @Override
    public Tile createCitySideTurnLeft() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCitySideTurnLeft'");
    }

    @Override
    public Tile createCitySideTurnRight() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCitySideTurnRight'");
    }

    @Override
    public Tile createCityTube() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCityTube'");
    }

    @Override
    public Tile createMonastery() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createMonastery'");
    }

    @Override
    public Tile createMonasteryRoad() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createMonasteryRoad'");
    }

    @Override
    public Tile createRoadJunctionLarge() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRoadJunctionLarge'");
    }

    @Override
    public Tile createRoadJunctionSmall() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRoadJunctionSmall'");
    }

    @Override
    public Tile createRoadStraight() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRoadStraight'");
    }

    @Override
    public Tile createRoadTurn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRoadTurn'");
    }
    
}
