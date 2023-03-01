package it.unibo.caesena.model;

import java.util.Optional;

public interface Meeple {

    public int getStrength();

    public Optional<TileSection> getTileSection();

    public Optional<Tile> getTile();

    public Player getOwner();

    public boolean isPlaced();

    public boolean place(TileSection section, Tile tile);

    public boolean removeFromTile();

}
