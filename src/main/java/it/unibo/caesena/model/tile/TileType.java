package it.unibo.caesena.model.tile;

import java.util.List;

import it.unibo.caesena.model.Expansion;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Class representing the different types of Tiles that can be created.
*/
@Entity(name = "tile_types")
@Table(name = "tile_types")
@Access(AccessType.FIELD)
public class TileType {

    @Id
    private String name;

    @ManyToOne
    private Expansion expansion;

    @OneToMany(mappedBy = "type")
    private List<TileImpl> tiles;

    private int quantity;

    public TileType() {}

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        TileType other = (TileType) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }


}
