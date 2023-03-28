package it.unibo.caesena.model.gameset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.caesena.model.Player;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.utils.StringUtil;

public final class GameSetImpl implements GameSet {

    private final GameSetType type;
    private final List<Meeple> meeples;

    private int points;
    private boolean closed;

    public GameSetImpl(final GameSetType type) {
        this.type = type;
        this.points = type.getStartingPoints();
        this.meeples = new ArrayList<>();
        this.closed = false;
    }

    @Override
    public boolean addMeeple(final Meeple meeple) {
        if (!isMeepleFree()) {
            return false;
        }

        meeple.setPlaced(true);
        this.meeples.add(meeple);
        return true;
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
            final Map<Player, Integer> playerMeepleStrength = new HashMap<>();

            for (final Meeple meeple : meeples) {
                final Player currentPlayer = meeple.getOwner();

                if (!playerMeepleStrength.containsKey(currentPlayer)) {
                    playerMeepleStrength.put(currentPlayer, 0);
                }
                playerMeepleStrength.put(currentPlayer,
                        playerMeepleStrength.get(currentPlayer) + 1 * meeple.getStrength());

            }

            final int maxMeepleStrength = playerMeepleStrength.values().stream()
                    .mapToInt(x -> x).max().getAsInt();

            playerMeepleStrength.entrySet().stream()
                    .filter(e -> e.getValue().equals(maxMeepleStrength))
                    .forEach(e -> e.getKey().addScore(this.getPoints()));
        }

        this.closed = true;
        meeples.forEach(m -> m.setPlaced(false));
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((meeples == null) ? 0 : meeples.hashCode());
        result = prime * result + points;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        return this == obj;
    }

    @Override
    public List<Meeple> getMeeples() {
        return this.meeples;
    }

}
