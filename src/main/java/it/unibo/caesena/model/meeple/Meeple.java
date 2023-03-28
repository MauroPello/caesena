package it.unibo.caesena.model.meeple;

import it.unibo.caesena.model.Player;

public interface Meeple {

    int getStrength();

    Player getOwner();

    void setPlaced(final boolean placed);

    boolean isPlaced();

}
