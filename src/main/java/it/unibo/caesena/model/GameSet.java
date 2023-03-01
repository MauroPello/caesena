package it.unibo.caesena.model;

public interface GameSet {
    
    public boolean addMeeple(Meeple meeple);

    public boolean removeMeeple(Meeple meeple);

    public boolean isMeepleFree();

}
