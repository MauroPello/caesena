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

    @Override
    public GameSet createJoinedSet(GameSet gs1, GameSet gs2) {
        
        if (gs1.getType().equals(gs2.getType())) {
            
            var firstPair = gs1.close();
            var secondPair = gs2.close();
            GameSetType newTipe = gs1.getType();
            int comulativePoints = firstPair.getY() + secondPair.getY();
            GameSet joinedGameset = new GameSetImpl(newTipe, comulativePoints);
            
            for (var meeple : firstPair.getX()) {
                joinedGameset.addMeeple(meeple);
            }
        
            for (var meeple : secondPair.getX()) {
                joinedGameset.addMeeple(meeple);
            }
            
            return joinedGameset;
        } else {
            throw new IllegalStateException("The type's of GameSet are different");
        }
    }
}
