package it.unibo.caesena.model.server;

import java.util.List;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "CardinalPoints")
@Table(name = "CardinalPoints")
@Access(AccessType.FIELD)
public class CardinalPoint {

    @Id
    private String point;

    @OneToMany(mappedBy = "cardinalPoint")
    private List<Region> regions;

    public CardinalPoint() {}

    public String getPoint() {
        return point;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((point == null) ? 0 : point.hashCode());
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
        if (point == null) {
            if (other.point != null)
                return false;
        } else if (!point.equals(other.point))
            return false;
        return true;
    }

    
}
