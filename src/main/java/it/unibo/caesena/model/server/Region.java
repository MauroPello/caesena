package it.unibo.caesena.model.server;

import java.util.List;

public class Region {

    private int regionID;
    private Continent continent;
    private List<Server> Servers;

    public Region(int regionID, Continent continent) {
        this.regionID = regionID;
        this.continent = continent;
    }

    public int getRegionID() {
        return regionID;
    }

    public Continent getContinent() {
        return continent;
    }

    public List<Server> getServers() {
        return Servers;
    }
}
