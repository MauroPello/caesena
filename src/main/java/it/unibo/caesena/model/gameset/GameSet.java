package it.unibo.caesena.model.gameset;

import java.util.List;

import it.unibo.caesena.model.meeple.Meeple;

public interface GameSet {

    boolean addMeeple(Meeple meeple);

    boolean isMeepleFree();

    boolean close();

    GameSetType getType();

    boolean isClosed();

    int getPoints();

    void setPoints(final int points);

    void addPoints(int points);

    List<Meeple> getMeeples();

}
