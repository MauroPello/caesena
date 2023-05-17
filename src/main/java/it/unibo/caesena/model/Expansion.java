package it.unibo.caesena.model;

import java.util.List;

import it.unibo.caesena.model.gameset.GameSetType;
import it.unibo.caesena.model.meeple.MeepleType;
import it.unibo.caesena.model.tile.TileType;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "Expansions")
@Table(name = "Expansions")
@Access(AccessType.FIELD)
public class Expansion {
    @Id
    private final String name;
    @OneToMany
    @JoinColumn(name = "fk_tile_types")
    private final List<TileType> tileTypes;
    @OneToMany
    @JoinColumn(name = "fk_meeple_types")
    private final List<MeepleType> meepleTypes;
    @OneToMany
    @JoinColumn(name = "fk_game_set_types")
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
