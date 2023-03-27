package it.unibo.caesena.model.gameset;

import java.util.List;

import it.unibo.caesena.model.meeple.Meeple;

public interface GameSet {

    public boolean addMeeple(Meeple meeple);

    public boolean isMeepleFree();

    public boolean close();

    public GameSetType getType();

    public boolean isClosed();

    public int getPoints();

    public void setPoints(final int points);

    public void addPoints(int points);

    public List<Meeple> getMeeples();

}
