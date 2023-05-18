package it.unibo.caesena.model.server;

import java.util.List;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "Continents")
@Table(name = "Continents")
@Access(AccessType.FIELD)
public class Continent {

    @Id
    private String continentName;

    @OneToMany(mappedBy = "continent")
    private List<Region> regions;

    public Continent() {}

    public String getContinentName() {
        return continentName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((continentName == null) ? 0 : continentName.hashCode());
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
        Continent other = (Continent) obj;
        if (continentName == null) {
            if (other.continentName != null)
                return false;
        } else if (!continentName.equals(other.continentName))
            return false;
        return true;
    }
    
}
