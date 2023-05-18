package it.unibo.caesena.model.gameset;

import java.util.Set;

import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.StringUtil;

import it.unibo.caesena.model.gameset.GameSetImpl;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * {@inheritDoc}
 *
 * Implementation of the GameSet interface.
 */
@Entity(name = "GameSets")
@Table(name = "GameSets")
@Access(AccessType.FIELD)
public final class GameSetImpl implements GameSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private GameSetType type;

    @OneToMany(mappedBy = "gameSet")
    private Set<TileSection> sections;

    private boolean closed;
    private int points;

    public GameSetImpl() {}

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

    public Long getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean close() {
        if (this.isClosed()) {
            return false;
        }

        // TODO
        // if (!this.isMeepleFree()) {
        //     final Map<MutablePlayerInGame, Integer> playerMeepleStrength = new HashMap<>();

        //     for (final MutableMeeple meeple : new ArrayList<MutableMeeple>()) {
        //         final MutablePlayerInGame currentPlayer = (MutablePlayerInGame) meeple.getOwner();

        //         if (!playerMeepleStrength.containsKey(currentPlayer)) {
        //             playerMeepleStrength.put(currentPlayer, 0);
        //         }
        //         playerMeepleStrength.put(currentPlayer,
        //                 playerMeepleStrength.get(currentPlayer) + 1 * meeple.getStrength());

        //     }

        //     final int maxMeepleStrength = playerMeepleStrength.values().stream()
        //             .mapToInt(x -> x).max().getAsInt();

        //     playerMeepleStrength.entrySet().stream()
        //             .filter(e -> e.getValue().equals(maxMeepleStrength))
        //             .forEach(e -> e.getKey().addScore(this.getPoints()));
        // }

        // this.closed = true;
        // meeples.forEach(m -> m.remove());
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

}
