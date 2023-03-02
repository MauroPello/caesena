package it.unibo.caesena.model.gameset;

import java.util.HashSet;
import java.util.Set;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.utils.StringUtil;

public class GameSetImpl implements GameSet{

    private GameSetType type;
    private Set<Meeple> meeples;
    private int points;

    public GameSetImpl (final GameSetType typeOfSet, final int point) {
        this.type = typeOfSet;
        this.points = point;
        this.meeples = new HashSet<>();
    }

    @Override
    public boolean addMeeple(Meeple meeple) {
        this.meeples.add(meeple);
        return meeple.placeOnTile();
    }

    @Override
    public boolean isMeepleFree() {
        return this.meeples.isEmpty();
    }

    @Override
    public Pair<Set<Meeple>, Integer> close() {
        for (Meeple meeple : meeples) {
            meeple.removeFromTile();
        }
        return new Pair<>(this.meeples, this.points);
    }

	@Override
	public GameSetType getType() {
		return this.type;
	}

    @Override
    public String toString() {
        return new StringUtil.ToStringBuilder().addFromObjectGetters(this).build();
    }

}
