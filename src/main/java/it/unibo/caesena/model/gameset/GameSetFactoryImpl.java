package it.unibo.caesena.model.gameset;

import it.unibo.caesena.model.gameset.GameSet.GameSetType;

public class GameSetFactoryImpl implements GameSetFactory {

    private static final int POINT_CITY = 2;
    private static final int POINT_MONASTERY = 1;
    private static final int POINT_ROAD = 1;
    private static final int POINT_FIELD = 0;

    @Override
    public GameSet createCitySet() {
        return new GameSetImpl(GameSetType.CITY, POINT_CITY);
    }

    @Override
    public GameSet createMonasterySet() {
        return new GameSetImpl(GameSetType.MONASTERY, POINT_MONASTERY);
    }

    @Override
    public GameSet createRoadSet() {
        return new GameSetImpl(GameSetType.ROAD, POINT_ROAD);
    }

    @Override
    public GameSet createFieldSet() {
        return new GameSetImpl(GameSetType.FIELD, POINT_FIELD);
    }
}
