package it.unibo.caesena.model.gameset;

import java.util.List;

import it.unibo.caesena.model.meeple.MutableMeeple;

/**
 * {@inheritDoc}
 *
 * Implementation of the GameSetFactory interface.
 */
public final class GameSetFactoryImpl implements GameSetFactory {

    //TODO
    /**
     * {@inheritDoc}
     */
    @Override
    public GameSet createCitySet() {
        // return new GameSetImpl(GameSetType.getFromName("CITY"));
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameSet createMonasterySet() {
        // return new GameSetImpl(GameSetType.getFromName("MONASTERY"));
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameSet createRoadSet() {
        // return new GameSetImpl(GameSetType.getFromName("ROAD"));
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameSet createFieldSet() {
        // return new GameSetImpl(GameSetType.getFromName("FIELD"));
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameSet createJunctionSet() {
        // return new GameSetImpl(GameSetType.getFromName("JUNCTION"));
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameSetImpl createJoinedSet(final GameSet gs1, final GameSet gs2) {
        if (gs1.getType().equals(gs2.getType())) {
            if (gs1.isClosed() || gs2.isClosed()) {
                throw new IllegalStateException("Only not closed GameSets can be joined!");
            }

            final int points = gs1.getPoints() + gs2.getPoints();
            final List<MutableMeeple> meeples = gs1.getMeeples();
            meeples.addAll(gs2.getMeeples());
            final GameSetImpl joinedGameset = new GameSetImpl(gs1.getType());
            meeples.forEach(joinedGameset::addMeeple);
            joinedGameset.setPoints(points);

            return joinedGameset;
        } else {
            throw new IllegalStateException("The type's of GameSet are different");
        }
    }
}
