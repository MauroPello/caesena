package it.unibo.caesena.model;

import java.util.List;

public class TileBuilder {
    
    private final Tile tile;

    public TileBuilder(String imagePath) {
        this.tile = new TileImpl(imagePath);
    }

    public TileBuilder city(List<TileSection> sections) {
        applySet(sections, new CitySet());
        return this;
    }

    public TileBuilder road(List<TileSection> sections) {
        applySet(sections, new RoadSet());
        return this;
    }

    public TileBuilder field(List<TileSection> sections) {
        applySet(sections, new FieldSet());
        return this;
    }

    public TileBuilder monastery(List<TileSection> sections) {
        applySet(sections, new MonasterySet());
        return this;
    }

    private void applySet(List<TileSection> sections, GameSet gameSet) {
        for (var section : sections) {
            this.tile.putSection(section, gameSet);
        }
    }

    public Tile build() {
        return this.tile;
    }

}
