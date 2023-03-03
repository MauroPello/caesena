package it.unibo.caesena.model.gameset;

import java.util.Optional;
import java.util.Set;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.utils.Pair;

public interface GameSet {

    enum GameSetType {
        FIELD,
        CITY,
        MONASTERY,
        ROAD
    }
    
    public boolean addMeeple(Meeple meeple);

    public boolean isMeepleFree();

    public Optional<Pair<Set<Meeple>, Integer>> close();

    public GameSetType getType();

    public boolean isClosed();

}
