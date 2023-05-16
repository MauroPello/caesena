package it.unibo.caesena.model;

import java.util.List;

import it.unibo.caesena.model.gameset.GameSetType;
import it.unibo.caesena.model.meeple.MeepleType;
import it.unibo.caesena.model.tile.TileType;

public class Expansion {
    private final String name;
    private final List<TileType> tileTypes;
    private final List<MeepleType> meepleTypes;
    private final List<GameSetType> gameSetTypes;

    public Expansion(String name, List<TileType> tileTypes, List<MeepleType> meepleTypes,
            List<GameSetType> gameSetTypes) {
        this.name = name;
        this.tileTypes = tileTypes;
        this.meepleTypes = meepleTypes;
        this.gameSetTypes = gameSetTypes;
    }

    public String getName() {
        return name;
    }

    public List<TileType> getTileTypes() {
        return tileTypes;
    }

    public List<MeepleType> getMeepleTypes() {
        return meepleTypes;
    }

    public List<GameSetType> getGameSetTypes() {
        return gameSetTypes;
    }

}
