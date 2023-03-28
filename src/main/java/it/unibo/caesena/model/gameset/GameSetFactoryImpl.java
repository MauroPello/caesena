package it.unibo.caesena.model.gameset;

import java.util.List;

import it.unibo.caesena.model.meeple.Meeple;

public final class GameSetFactoryImpl implements GameSetFactory {

    @Override
    public GameSet createCitySet() {
        return new GameSetImpl(GameSetType.CITY);
    }

    @Override
    public GameSet createMonasterySet() {
        return new GameSetImpl(GameSetType.MONASTERY);
    }

    @Override
    public GameSet createRoadSet() {
        return new GameSetImpl(GameSetType.ROAD);
    }

    @Override
    public GameSet createFieldSet() {
        return new GameSetImpl(GameSetType.FIELD);
    }

    @Override
    public GameSet createJunctionSet() {
        return new GameSetImpl(GameSetType.JUNCTION);
    }

    @Override
    public GameSet createJoinedSet(final GameSet gs1, final GameSet gs2) {

        if (gs1.getType().equals(gs2.getType())) {
            if (gs1.isClosed() || gs2.isClosed()) {
                throw new IllegalStateException("Only not closed GameSets can be joined!");
            }

            final int points = gs1.getPoints() + gs2.getPoints();
            final List<Meeple> meeples = gs1.getMeeples();
            meeples.addAll(gs2.getMeeples());
            final GameSet joinedGameset = new GameSetImpl(gs1.getType(), meeples);
            joinedGameset.setPoints(points);

            return joinedGameset;
        } else {
            throw new IllegalStateException("The type's of GameSet are different");
        }
    }
}
