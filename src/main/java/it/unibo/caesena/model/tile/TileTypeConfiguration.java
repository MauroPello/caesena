package it.unibo.caesena.model.tile;

import it.unibo.caesena.model.gameset.GameSetType;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "tile_type_configurations")
@Table(name = "tile_type_configurations")
@Access(AccessType.FIELD)
public class TileTypeConfiguration {

    @Id
    @ManyToOne
    @JoinColumn(name = "tile_type_name")
    private TileType tileType;

    @Id
    @ManyToOne
    @JoinColumn(name = "tile_section_type_name")
    private TileSectionType tileSectionType;

    @ManyToOne
    @JoinColumn(name = "gameset_type_name")
    private GameSetType gamesetType;

    private boolean pennant;
    private boolean closed;
    private int id;

    public TileTypeConfiguration() {}

    public int getId() {
        return this.id;
    }

    public TileType getTileType() {
        return tileType;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean hasPennant() {
        return pennant;
    }

    public TileSectionType getTileSectionType() {
        return tileSectionType;
    }

    public GameSetType getGamesetType() {
        return gamesetType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tileType == null) ? 0 : tileType.hashCode());
        result = prime * result + ((tileSectionType == null) ? 0 : tileSectionType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TileTypeConfiguration other = (TileTypeConfiguration) obj;
        if (tileType == null) {
            if (other.tileType != null)
                return false;
        } else if (!tileType.equals(other.tileType))
            return false;
        if (tileSectionType == null) {
            if (other.tileSectionType != null)
                return false;
        } else if (!tileSectionType.equals(other.tileSectionType))
            return false;
        return true;
    }


}
