package it.unibo.caesena.model;

import java.util.List;

import it.unibo.caesena.model.gameset.GameSetType;
import it.unibo.caesena.model.meeple.MeepleType;
import it.unibo.caesena.model.tile.TileType;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "Expansions")
@Table(name = "Expansions")
@Access(AccessType.FIELD)
public class Expansion {

    @Id
    private String name;

    @OneToMany(mappedBy = "expansion")
    private List<TileType> tileTypes;

    @OneToMany(mappedBy = "expansion")
    private List<MeepleType> meepleTypes;

    @OneToMany(mappedBy = "expansion")
    private List<GameSetType> gameSetTypes;

    public Expansion() {}

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
