package it.unibo.caesena.model.meeple;

import java.util.Optional;

import it.unibo.caesena.model.Player;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;

public interface Meeple {

    public int getStrength();

    public Optional<TileSection> getTileSection();

    public Optional<Tile> getTile();

    public Player getOwner();

    public boolean place(TileSection section, Tile tile);
    
    public boolean removeFromTile();
    
    public boolean isPlaced();
    
}
