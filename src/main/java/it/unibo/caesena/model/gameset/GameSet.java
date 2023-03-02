package it.unibo.caesena.model.gameset;

import java.util.List;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.utils.Pair;

public interface GameSet {
    
    public boolean addMeeple(Meeple meeple);

    public boolean isMeepleFree();

    public Pair<List<Meeple>, Integer> close();

}
