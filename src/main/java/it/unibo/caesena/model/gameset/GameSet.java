package it.unibo.caesena.model.gameset;

import it.unibo.caesena.model.meeple.Meeple;

public interface GameSet {
    
    public boolean addMeeple(Meeple meeple);

    public boolean removeMeeple(Meeple meeple);

    public boolean isMeepleFree();

}
