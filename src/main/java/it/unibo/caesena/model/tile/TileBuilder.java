package it.unibo.caesena.model.tile;

import java.util.List;

import it.unibo.caesena.model.GameSetTileMediator;
import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetFactory;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;

public final class TileBuilder {

    private final GameSetTileMediator mediator;
    private final GameSetFactory factory;
    private final Tile tile;

    public TileBuilder(final TileType type, final GameSetTileMediator mediator) {
        this.factory = new GameSetFactoryImpl();
        this.mediator = mediator;
        this.tile = new TileImpl(type);
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
        final GameSet junctionGameSet = this.factory.createJunctionSet();
        junctionGameSet.close();
        applySet(sections, junctionGameSet);
        return this;
    }

    public TileBuilder close(final List<TileSection> sections) {
        sections.forEach(tile::closeSection);
        return this;
    }

    private void applySet(final List<TileSection> sections, final GameSet gameSet) {
        sections.forEach(s -> mediator.addSection(gameSet, tile , s));
    }

    public Tile build() {
        return this.tile;
    }

}
