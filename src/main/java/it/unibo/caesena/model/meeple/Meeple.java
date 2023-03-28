package it.unibo.caesena.model.meeple;

import it.unibo.caesena.model.Player;

public interface Meeple {

    public int getStrength();

    public Player getOwner();

    public void setPlaced(final boolean placed);

    public boolean isPlaced();

}
