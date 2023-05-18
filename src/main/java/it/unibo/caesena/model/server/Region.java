package it.unibo.caesena.model.server;

import java.util.List;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "Regions")
@Table(name = "Regions")
@Access(AccessType.FIELD)
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int regionID;

    @ManyToOne
    private CardinalPoint cardinalPoint;

    @ManyToOne
    private Continent continent;

    @OneToMany(mappedBy = "region")
    private List<Server> Servers;

    public Region() {}

    public int getRegionID() {
        return regionID;
    }

    public Continent getContinent() {
        return continent;
    }

    public List<Server> getServers() {
        return Servers;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + regionID;
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
        Region other = (Region) obj;
        if (regionID != other.regionID)
            return false;
        return true;
    }
    
}
