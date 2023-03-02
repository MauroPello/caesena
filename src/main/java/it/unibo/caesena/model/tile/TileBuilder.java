package it.unibo.caesena.model.tile;

import java.util.List;

import it.unibo.caesena.model.gameset.CitySet;
import it.unibo.caesena.model.gameset.FieldSet;
import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.MonasterySet;
import it.unibo.caesena.model.gameset.RoadSet;

public class TileBuilder {
    
    private final Tile tile;

    public TileBuilder(final String imagePath) {
        this.tile = new TileImpl(imagePath);
    }

    public TileBuilder city(final List<TileSection> sections) {
        applySet(sections, new CitySet());
        return this;
    }

    public TileBuilder road(final List<TileSection> sections) {
        applySet(sections, new RoadSet());
        return this;
    }

    public TileBuilder field(final List<TileSection> sections) {
        applySet(sections, new FieldSet());
        return this;
    }

    public TileBuilder monastery(final List<TileSection> sections) {
        applySet(sections, new MonasterySet());
        return this;
    }

    private void applySet(final List<TileSection> sections, final GameSet gameSet) {
        for (var section : sections) {
            this.tile.putSection(section, gameSet);
        }
    }

    public Tile build() {
        return this.tile;
    }

}
