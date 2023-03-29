package it.unibo.caesena.model;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;

public class GameSetTileMediator {
    
    private final Map<GameSet, Map<Tile, Set<TileSection>>> map;

    public GameSetTileMediator() {
        this.map = new HashMap<>();
    }

    public void addSectionToGameSetTile(final GameSet gameSet, final Tile tile, final TileSection tileSection) {
        if (!this.map.containsKey(gameSet)) {
            this.map.put(gameSet, new HashMap<>());
        }

        if (!this.map.get(gameSet).containsKey(tile)) {
            this.map.get(gameSet).put(tile, new HashSet<>());
        }

        this.map.get(gameSet).get(tile).add(tileSection);
    }

    public void rotateTileClockwise(final Tile tile) {
        for (var entry : map.entrySet()) {
            if (entry.getValue().containsKey(tile)) {
                var sections = entry.getValue().get(tile);
                entry.getValue().put(tile, new HashSet<>(sections.stream().map(TileSection::rotateClockwise).toList()));
            }
        }
    }

    public boolean isSectionNearToGameset(final Tile tile, final TileSection tileSection, final GameSet gameSet) {
        return getGameSet(tile, TileSection.next(tileSection)).equals(gameSet) || 
            getGameSet(tile, TileSection.previous(tileSection)).equals(gameSet);
    }

    public GameSet getGameSet(final Tile tile, final TileSection tileSection) {
        return map.entrySet().stream()
            .filter(e -> e.getValue().containsKey(tile) && e.getValue().get(tile).contains(tileSection))
            .map(e -> e.getKey()).findFirst().get();
    }

    public Map<Tile, Set<TileSection>> getTilesFromGameSet(final GameSet gameSet) {
        return this.map.get(gameSet);
    }
}
