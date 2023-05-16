package it.unibo.caesena.model.gameset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.caesena.model.meeple.MutableMeeple;
import it.unibo.caesena.model.player.MutablePlayerInGame;
import it.unibo.caesena.utils.StringUtil;

/**
 * {@inheritDoc}
 *
 * Implementation of the GameSet interface.
 */
public final class GameSetImpl implements GameSet {

    // private final int id;
    private final GameSetType type;
    // TODO private final Set<TileSection> sections;

    private int points;
    private boolean closed;

    /**
     * Class constructor.
     *
     * @param type type of GameSet
     */
    public GameSetImpl(final GameSetType type) {
        this.type = type;
        this.points = type.getStartingPoints();
        this.closed = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMeeple(final MutableMeeple meeple) {
        // TODO this.meeples.add(meeple);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMeepleFree() {
        // TODO return this.meeples.isEmpty();
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean close() {
        if (this.isClosed()) {
            return false;
        }

        if (!this.isMeepleFree()) {
            final Map<MutablePlayerInGame, Integer> playerMeepleStrength = new HashMap<>();

            // TODO
            for (final MutableMeeple meeple : new ArrayList<MutableMeeple>()) {
                final MutablePlayerInGame currentPlayer = (MutablePlayerInGame) meeple.getOwner();

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
        // TODO meeples.forEach(m -> m.remove());
        return true;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameSetType getType() {
        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new StringUtil.ToStringBuilder().addFromObjectGetters(this).build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isClosed() {
        return this.closed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPoints() {
        return this.points;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPoints(final int points) {
        this.points = points;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPoints(final int points) {
        this.points += points;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MutableMeeple> getMeeples() {
        // TODO return new ArrayList<>(this.meeples);
        return new ArrayList<>();
    }

}
