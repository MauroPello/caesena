package it.unibo.caesena.model.gameset;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.utils.StringUtil;

public class GameSetImpl implements GameSet{

    private final GameSetType type;
    private final Set<Meeple> meeples;
    private int points;
    private final boolean closed;

    public GameSetImpl (final GameSetType typeOfSet, final int point) {
        this.type = typeOfSet;
        this.points = point;
        this.meeples = new HashSet<>();
        this.closed = false;
    }

    @Override
    public boolean addMeeple(final Meeple meeple) {
        this.meeples.add(meeple);
        return meeple.placeOnTile();
    }

    @Override
    public boolean isMeepleFree() {
        return this.meeples.isEmpty();
    }

    @Override
    public Optional<Pair<Set<Meeple>, Integer>> close() {
        if (this.isClosed()) {
            return Optional.empty();
        }

        for (final Meeple meeple : meeples) {
            meeple.removeFromTile();
        }
        return Optional.of(new Pair<>(this.meeples, this.points));
    }

	@Override
	public GameSetType getType() {
		return this.type;
	}

    @Override
    public String toString() {
        return new StringUtil.ToStringBuilder().addFromObjectGetters(this).build();
    }

    @Override
    public boolean isClosed() {
        return this.closed;
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    @Override
    public void addPoints(final int points) {
        this.points += points;
    }

    @Override
    public boolean equals (final Object obj) {
        return this == obj;
    }

}
