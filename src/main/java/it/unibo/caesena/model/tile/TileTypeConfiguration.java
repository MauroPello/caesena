package it.unibo.caesena.model.tile;

import it.unibo.caesena.model.gameset.GameSetType;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "TileTypeConfigurations")
@Table(name = "TileTypeConfigurations")
@Access(AccessType.FIELD)
public class TileTypeConfiguration {

    @Id
    @ManyToOne
    private TileType tileType;

    @Id
    @ManyToOne
    private TileSectionType tileSectionType;

    @ManyToOne
    private GameSetType gameSetType;

    public TileTypeConfiguration() {}

    public TileType getTileType() {
        return tileType;
    }

    public TileSectionType getTileSectionType() {
        return tileSectionType;
    }

    public GameSetType getGameSetType() {
        return gameSetType;
    }
}
