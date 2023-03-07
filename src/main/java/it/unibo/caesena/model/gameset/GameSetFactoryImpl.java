package it.unibo.caesena.model.gameset;

public class GameSetFactoryImpl implements GameSetFactory {

    private static final int POINT_FIELD = 0;
    private static final int POINT_CITY = 2;
    private static final int POINT_MONASTERY = 1;
    private static final int POINT_ROAD = 1;
    private static final int POINT_JUNCTION = 1;

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
    public GameSet createJunctionSet() {
        return new GameSetImpl(GameSetType.JUNCTION, POINT_JUNCTION);
    }

    @Override
    public GameSet createJoinedSet(final GameSet gs1, final GameSet gs2) {
        
        if (gs1.getType().equals(gs2.getType())) {
            
            final var firstPair = gs1.close().get();
            final var secondPair = gs2.close().get();
            final GameSetType newTipe = gs1.getType();
            final int comulativePoints = firstPair.getY() + secondPair.getY();
            final GameSet joinedGameset = new GameSetImpl(newTipe, comulativePoints);
            
            for (final var meeple : firstPair.getX()) {
                joinedGameset.addMeeple(meeple);
            }
        
            for (final var meeple : secondPair.getX()) {
                joinedGameset.addMeeple(meeple);
            }
            
            return joinedGameset;
        } else {
            throw new IllegalStateException("The type's of GameSet are different");
        }
    }
}
