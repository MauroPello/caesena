package it.unibo.caesena.model.gameset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.caesena.model.Player;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.utils.StringUtil;

public class GameSetImpl implements GameSet{

    private final GameSetType type;
    private final List<Meeple> meeples;
    
    private int points;
    private boolean closed;

    public GameSetImpl (final GameSetType type) {
        this.type = type;
        this.points = type.getStartingPoints();
        this.meeples = new ArrayList<>();
        this.closed = false;
    }

    @Override
    public boolean addMeeple(final Meeple meeple) {
        if (isMeepleFree() && meeple.placeOnTile()) {
            this.meeples.add(meeple);
            return true;
        }

        return false;
    }

    @Override
    public boolean isMeepleFree() {
        return this.meeples.isEmpty();
    }

    @Override
    public boolean close() {
        if (this.isClosed()) {
            return false;
        }

        if (!this.isMeepleFree()) {
            Map<Player, Integer> playerMeeples = new HashMap<>();

            for (Meeple playerMeeple : meeples) {
                Player currentPlayer = playerMeeple.getOwner();

                if (!playerMeeples.containsKey(currentPlayer)) {
                    playerMeeples.put(currentPlayer, 0);
                }
                playerMeeples.put(currentPlayer, playerMeeples.get(currentPlayer)+1);

            }

            int maxValueMeeple = playerMeeples.values().stream().mapToInt(x -> x)
                .max().getAsInt();

            playerMeeples.entrySet().stream()
                .filter(e -> e.getValue().equals(maxValueMeeple))
                .forEach(e -> e.getKey().addScore(points));
        }

        this.closed = true;
        meeples.forEach(Meeple::removeFromTile);
        return true;

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
    public void setPoints(final int points) {
        this.points = points;
    }

    @Override
    public void addPoints(final int points) {
        this.points += points;
    }

    @Override
    public boolean equals (final Object obj) {
        return this == obj;
    }

    public List<Meeple> getMeeples () {
        return this.meeples;
    }

}
