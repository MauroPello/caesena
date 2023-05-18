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
}
