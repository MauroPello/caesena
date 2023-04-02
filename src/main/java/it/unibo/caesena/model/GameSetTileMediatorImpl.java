package it.unibo.caesena.model;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetFactory;
import it.unibo.caesena.model.gameset.GameSetType;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;

public class GameSetTileMediatorImpl implements GameSetTileMediator {

    private static final Map<Direction, Pair<List<TileSection>, List<TileSection>>> NEIGHBOUR_TILES_CHECK = new HashMap<>(Map.of(
        Direction.UP, new Pair<>(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT),
            List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT)),
        Direction.DOWN, new Pair<>(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT),
            List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT)),
        Direction.LEFT, new Pair<>(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN),
            List.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN)),
        Direction.RIGHT, new Pair<>(List.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN),
            List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN))));
        private final Map<GameSet, Map<Tile, Set<TileSection>>> crossReferences;
    private final GameSetFactory gameSetFactory;

    public GameSetTileMediatorImpl(final GameSetFactory gameSetFactory) {
        this.crossReferences = new HashMap<>();
        this.gameSetFactory = gameSetFactory;
    }

    /**
     * @param gameSet
     * @param tile
     * @param tileSection
     */
    @Override
    public void addSection(final GameSet gameSet, final Tile tile, final TileSection tileSection) {
        if (!this.crossReferences.containsKey(gameSet)) {
            this.crossReferences.put(gameSet, new HashMap<>());
        }

        if (!this.crossReferences.get(gameSet).containsKey(tile)) {
            this.crossReferences.get(gameSet).put(tile, new HashSet<>());
        }

        this.crossReferences.get(gameSet).get(tile).add(tileSection);
    }

    /**
     * @param position
     * @param t1
     * @param t2
     * @return boolean
     */
    private boolean tilesMatch(final Pair<Integer, Integer> position, final Tile t1, final Tile t2) {
        for (final var direction : NEIGHBOUR_TILES_CHECK.keySet()) {
            if (Direction.match(direction, position, t2.getPosition().get())) {
                for (int i = 0; i < TileSection.getSectionsPerSide(); i++) {
                    final TileSection t1Section = NEIGHBOUR_TILES_CHECK.get(direction).getY().get(i);
                    final TileSection t2Section = NEIGHBOUR_TILES_CHECK.get(direction).getX().get(i);

                    if (!getGameSetInSection(t1, t1Section).getType()
                            .equals(getGameSetInSection(t2, t2Section).getType())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * @param position
     * @param tile
     * @return boolean
     */
    @Override
    public boolean isPositionValid(final Pair<Integer, Integer> position, final Tile tile) {
        final Set<Tile> neighbours = getTileNeighbours(position);
        return !neighbours.isEmpty() && neighbours.stream().allMatch(t -> tilesMatch(position, tile, t));
    }

    /**
     * @param position
     * @return Set<Tile>
     */
    @Override
    public Set<Tile> getTileNeighbours(final Pair<Integer, Integer> position) {
        final var neighboursDirections = Stream.of(Direction.values())
                .map(d -> new Pair<Integer, Integer>(position.getX() + d.getX(), position.getY() + d.getY()))
                .toList();

        return crossReferences.values().stream()
                .flatMap(m -> m.keySet().stream())
                .filter(Tile::isPlaced)
                .filter(t -> neighboursDirections.contains(t.getPosition().get()))
                .collect(Collectors.toSet());
    }

    /**
     * @param t1
     * @param t2
     */
    @Override
    public void joinTiles(final Tile t1, final Tile t2) {
        for (final var direction : NEIGHBOUR_TILES_CHECK.keySet()) {
            if (Direction.match(direction, t1.getPosition().get(), t2.getPosition().get())) {
                for (int i = 0; i < TileSection.getSectionsPerSide(); i++) {
                    final TileSection t1Section = NEIGHBOUR_TILES_CHECK.get(direction).getY().get(i);
                    final TileSection t2Section = NEIGHBOUR_TILES_CHECK.get(direction).getX().get(i);

                    t1.closeSection(t1Section);
                    t2.closeSection(t2Section);

                    final GameSet t1GameSet = getGameSetInSection(t1, t1Section);
                    final GameSet t2GameSet = getGameSetInSection(t2, t2Section);
                    if (!t1GameSet.equals(t2GameSet)) {
                        final GameSet joinedGameSet = gameSetFactory.createJoinedSet(t1GameSet, t2GameSet);

                        for (final var entry : crossReferences.remove(t2GameSet).entrySet()) {
                            for (final var section : entry.getValue()) {
                                addSection(joinedGameSet, entry.getKey(), section);
                            }
                        }
                        addSection(joinedGameSet, t2, t2Section);

                        for (final var entry : crossReferences.remove(t1GameSet).entrySet()) {
                            for (final var section : entry.getValue()) {
                                addSection(joinedGameSet, entry.getKey(), section);
                            }
                        }
                        addSection(joinedGameSet, t1, t1Section);
                    }
                }
            }
        }
    }

    /**
     * @param gameSet
     * @return Set<GameSet>
     */
    @Override
    public Set<GameSet> getFieldGameSetsNearGameSet(final GameSet gameSet) {
        final Set<GameSet> fieldsNearCity = new HashSet<>();

        for (final var entry : getTilesFromGameSet(gameSet).entrySet()) {
            for (final var tileSection : TileSection.values()) {
                final GameSet fieldGameSet = getGameSetInSection(entry.getKey(), tileSection);

                if (fieldGameSet.getType().equals(GameSetType.FIELD)
                        && isSectionNearToGameset(entry.getKey(), tileSection, gameSet)) {
                    fieldsNearCity.add(fieldGameSet);
                }
            }
        }

        return fieldsNearCity;
    }

    /**
     * @param tile
     */
    @Override
    public void rotateTileClockwise(final Tile tile) {
        for (final var entry : crossReferences.entrySet()) {
            if (entry.getValue().containsKey(tile)) {
                final var sections = entry.getValue().get(tile);
                entry.getValue().put(tile, new HashSet<>(
                        sections.stream().map(TileSection::rotateClockwise).toList()));
            }
        }
        tile.rotate();
    }

    /**
     * @param tile
     * @param tileSection
     * @param gameSet
     * @return boolean
     */
    private boolean isSectionNearToGameset(final Tile tile, final TileSection tileSection, final GameSet gameSet) {
        return getGameSetInSection(tile, TileSection.next(tileSection)).equals(gameSet)
                || getGameSetInSection(tile, TileSection.previous(tileSection)).equals(gameSet);
    }

    /**
     * @param tile
     * @param tileSection
     * @return GameSet
     */
    @Override
    public GameSet getGameSetInSection(final Tile tile, final TileSection tileSection) {
        return crossReferences.entrySet().stream()
                .filter(e -> e.getValue().containsKey(tile) && e.getValue().get(tile).contains(tileSection))
                .findFirst().get().getKey();
    }

    /**
     * @param tile
     * @return Set<GameSet>
     */
    @Override
    public Set<GameSet> getGameSetsInTile(final Tile tile) {
        return new HashSet<>(crossReferences.entrySet().stream()
                .filter(e -> e.getValue().containsKey(tile))
                .map(e -> e.getKey()).toList());
    }

    /**
     * @return Set<GameSet>
     */
    @Override
    public Set<GameSet> getAllGameSets() {
        return crossReferences.keySet();
    }

    /**
     * @param gameSet
     * @return Map<Tile, Set<TileSection>>
     */
    @Override
    public Map<Tile, Set<TileSection>> getTilesFromGameSet(final GameSet gameSet) {
        return this.crossReferences.get(gameSet);
    }

    @Override
    public boolean placeMeeple(Meeple meeple, Tile tile, TileSection tileSection) {
        final GameSet gameSet = getGameSetInSection(tile, tileSection);
        if (meeple.isPlaced() || !gameSet.isMeepleFree()) {
            return false;
        }

        gameSet.addMeeple(meeple);
        meeple.place(new Pair<>(tile, tileSection));
        return true;
    }
}
