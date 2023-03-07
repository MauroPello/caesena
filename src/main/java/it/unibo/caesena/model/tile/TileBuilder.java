package it.unibo.caesena.model.tile;

import java.util.List;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetFactory;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;

public class TileBuilder {
    
    private final GameSetFactory factory;
    private final Tile tile;

    public TileBuilder(final String imagePath) {
        this.factory = new GameSetFactoryImpl();

        this.tile = new TileImpl(imagePath);
    }

    public TileBuilder city(final List<TileSection> sections) {
        applySet(sections, this.factory.createCitySet());
        return this;
    }

    public TileBuilder road(final List<TileSection> sections) {
        applySet(sections, this.factory.createRoadSet());
        return this;
    }

    public TileBuilder field(final List<TileSection> sections) {
        applySet(sections, this.factory.createFieldSet());
        return this;
    }

    public TileBuilder monastery(final List<TileSection> sections) {
        applySet(sections, this.factory.createMonasterySet());
        return this;
    }

    public TileBuilder junction(final List<TileSection> sections) {
        applySet(sections, this.factory.createJunctionSet());
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
