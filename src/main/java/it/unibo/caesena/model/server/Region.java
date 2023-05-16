package it.unibo.caesena.model.server;

import java.util.List;

public class Region {
    private final int regionID;
    private final Continent continent;
    private final List<Region> regions;

    public Region(int regionID, Continent continent, List<Region> regions) {
        this.regionID = regionID;
        this.continent = continent;
        this.regions = regions;
    }
}
