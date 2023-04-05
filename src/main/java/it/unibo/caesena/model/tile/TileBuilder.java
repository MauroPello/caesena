package it.unibo.caesena.model.tile;

import java.util.List;

import it.unibo.caesena.model.GameSetTileMediator;
import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetFactory;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;

/**
 * A class defining a Builder for Tiles.
 */
public final class TileBuilder {

    private final GameSetTileMediator mediator;
    private final GameSetFactory factory;
    private MutableTile tile;

    /**
     * Public constructor that accepts a TileType and a mediator.
     *
     * @param type of the tile to be created
     * @param mediator used to create GameSets
     */
    public TileBuilder(final TileType type, final GameSetTileMediator mediator) {
        this.factory = new GameSetFactoryImpl();
        this.mediator = mediator;
        this.tile = new TileImpl(type);
    }

    /**
     * Marks the passed sections inside the tile as cities.
     *
     * @param sections to be marked as cities
     * @return the builder itself
     */
    public TileBuilder city(final List<TileSection> sections) {
        applySet(sections, this.factory.createCitySet());
        return this;
    }

    /**
     * Marks the passed sections inside the tile as roads.
     *
     * @param sections to be marked as roads
     * @return the builder itself
     */
    public TileBuilder road(final List<TileSection> sections) {
        applySet(sections, this.factory.createRoadSet());
        return this;
    }

    /**
     * Marks the passed sections inside the tile as fields.
     *
     * @param sections to be marked as fields
     * @return the builder itself
     */
    public TileBuilder field(final List<TileSection> sections) {
        applySet(sections, this.factory.createFieldSet());
        return this;
    }

    /**
     * Marks the passed sections inside the tile as monasteries.
     *
     * @param sections to be marked as monasteries
     * @return the builder itself
     */
    public TileBuilder monastery(final List<TileSection> sections) {
        applySet(sections, this.factory.createMonasterySet());
        return this;
    }

    /**
     * Marks the passed sections inside the tile as junctions.
     *
     * @param sections to be marked as junctions
     * @return the builder itself
     */
    public TileBuilder junction(final List<TileSection> sections) {
        final GameSet junctionGameSet = this.factory.createJunctionSet();
        junctionGameSet.close();
        applySet(sections, junctionGameSet);
        return this;
    }

    /**
     * Marks the passed sections inside the tile as closed.
     *
     * @param sections to be marked as closed
     * @return the builder itself
     */
    public TileBuilder close(final List<TileSection> sections) {
        sections.forEach(tile::closeSection);
        return this;
    }

    /**
     * Applies the given GameSet to all the given sections.
     *
     * @param sections to have the GameSet applied to
     * @param gameSet to apply to all the sections
     */
    private void applySet(final List<TileSection> sections, final GameSet gameSet) {
        sections.forEach(s -> mediator.addSection(gameSet, tile, s));
    }

    /**
     * Gets the built tile.
     *
     * @return the build tile.
     */
    public MutableTile build() {
        final MutableTile builtTile = this.tile;
        this.tile = null;
        return builtTile;
    }

}
