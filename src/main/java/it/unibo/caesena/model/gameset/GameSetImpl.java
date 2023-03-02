package it.unibo.caesena.model.gameset;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.utils.Pair;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addMeeple'");
    }

    @Override
    public boolean isMeepleFree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isMeepleFree'");
    }

    @Override
    public Pair<List<Meeple>, Integer> close() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }
    
}
