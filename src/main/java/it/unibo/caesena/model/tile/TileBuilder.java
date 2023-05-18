package it.unibo.caesena.model.tile;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetFactory;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;
import it.unibo.caesena.utils.Pair;

/**
 * A class defining a Builder for Tiles.
 */
public final class TileBuilder {

    private final Map<GameSet, Set<TileSection>> gameSets;
    private final GameSetFactory factory;
    private final MutableTile tile;

    /**
     * Public constructor that accepts a TileType.
     *
     * @param type of the tile to be created
     */
    public TileBuilder(final TileType type) {
        this.factory = new GameSetFactoryImpl();
        this.gameSets = new HashMap<>();
        this.tile = new TileImpl();
    }

    // TODO COMPLETARE LA CREAZIONE DELLE TILESECTION
    /**
     * Marks the passed sections inside the tile as cities.
     *
     * @param sections to be marked as cities
     * @return the builder itself
     */
    public TileBuilder city(final Set<TileSectionType> sections) {
        // gameSets.put(this.factory.createCitySet(), sections.stream().map(s -> new TileSection(s)).collect(Collectors.toSet()));
        return this;
    }

    /**
     * Marks the passed sections inside the tile as roads.
     *
     * @param sections to be marked as roads
     * @return the builder itself
     */
    public TileBuilder road(final Set<TileSectionType> sections) {
        // gameSets.put(this.factory.createRoadSet(), sections.stream().map(s -> new TileSection(s)).collect(Collectors.toSet()));
        return this;
    }

    /**
     * Marks the passed sections inside the tile as fields.
     *
     * @param sections to be marked as fields
     * @return the builder itself
     */
    public TileBuilder field(final Set<TileSectionType> sections) {
        // gameSets.put(this.factory.createFieldSet(), sections.stream().map(s -> new TileSection(s)).collect(Collectors.toSet()));
        return this;
    }

    /**
     * Marks the passed sections inside the tile as monasteries.
     *
     * @param sections to be marked as monasteries
     * @return the builder itself
     */
    public TileBuilder monastery(final Set<TileSectionType> sections) {
        // gameSets.put(this.factory.createMonasterySet(), sections.stream().map(s -> new TileSection(s)).collect(Collectors.toSet()));
        return this;
    }

    /**
     * Marks the passed sections inside the tile as junctions.
     *
     * @param sections to be marked as junctions
     * @return the builder itself
     */
    public TileBuilder junction(final Set<TileSectionType> sections) {
        final GameSet junctionGameSet = this.factory.createJunctionSet();
        junctionGameSet.close();
        // gameSets.put(junctionGameSet, sections.stream().map(s -> new TileSection(s)).collect(Collectors.toSet()));
        return this;
    }

    /**
     * Marks the passed sections inside the tile as closed.
     *
     * @param sections to be marked as closed
     * @return the builder itself
     */
    public TileBuilder close(final Set<TileSectionType> sections) {
        // TODO sections.forEach(tile::closeSection);
        return this;
    }

    /**
     * Gets a pair containing the built tile and a map containing a set of
     * tileSections for every gameSet inside the newly created tile.
     *
     * @return the build tile.
     */
    public Pair<MutableTile, Map<GameSet, Set<TileSection>>> build() {
        return new Pair<>(tile, gameSets);
    }

}
