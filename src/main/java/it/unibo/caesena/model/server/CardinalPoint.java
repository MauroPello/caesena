package it.unibo.caesena.model.server;

import java.util.List;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "cardinal_points")
@Table(name = "cardinal_points")
@Access(AccessType.FIELD)
public class CardinalPoint {

    @Id
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "cardinalPoint")
    private List<Region> regions;

    public CardinalPoint() {}

    public String getName() {
        return name;
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
        CardinalPoint other = (CardinalPoint) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
