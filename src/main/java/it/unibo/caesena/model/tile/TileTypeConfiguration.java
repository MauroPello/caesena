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

    private boolean closed;

    private boolean pennant;

    public TileTypeConfiguration() {}

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

    public GameSetType getGameSetType() {
        return gameSetType;
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
