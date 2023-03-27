package it.unibo.caesena.model.gameset;

import java.util.Set;

import it.unibo.caesena.model.meeple.Meeple;

public interface GameSet {

    public boolean addMeeple(Meeple meeple);

    public boolean isMeepleFree();

    public boolean close();

    public GameSetType getType();

    public boolean isClosed();

    public int getPoints();

    public void addPoints(int points);

    public Set<Meeple> getMeeples();

}
