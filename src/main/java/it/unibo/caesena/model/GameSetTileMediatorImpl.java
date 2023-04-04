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

/**
 * Simple implementation of GameSetTileMediator.
 */
public class GameSetTileMediatorImpl implements GameSetTileMediator {

    private static final Map<Direction, Pair<List<TileSection>, List<TileSection>>> NEIGHBOUR_TILES_CHECK = new HashMap<>(
            Map.of(
                    Direction.UP,
                    new Pair<>(List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT),
                            List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT)),
                    Direction.DOWN,
                    new Pair<>(List.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT),
                            List.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT)),
                    Direction.LEFT,
                    new Pair<>(List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN),
                            List.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN)),
                    Direction.RIGHT,
                    new Pair<>(List.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN),
                            List.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN))));
    private final Map<GameSet, Map<Tile, Set<TileSection>>> crossReferences;
    private final GameSetFactory gameSetFactory;

    /**
     * Public constructor that accepts a GameSetFactory used to create new GameSets.
     *
     * @param gameSetFactory to be used when creating new GameSets
     */
    public GameSetTileMediatorImpl(final GameSetFactory gameSetFactory) {
        this.crossReferences = new HashMap<>();
        this.gameSetFactory = gameSetFactory;
    }

    /**
     * {@inheritDoc}
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
     * Gets whether or not two tiles match and can be placed next to each other.
     *
     * @param position the position to use if <code>t1</code> is not placed
     * @param t1 one of two tile to check
     * @param t2 one of two tile to check
     * @return whether or not two tiles match and can be placed next to each other
     */
    private boolean tilesMatch(final Pair<Integer, Integer> position, final Tile t1, final Tile t2) {
        for (final var entry : NEIGHBOUR_TILES_CHECK.entrySet()) {
            if (Direction.match(entry.getKey(), position, t2.getPosition().get())) {
                for (int i = 0; i < TileSection.getSectionsPerSide(); i++) {
                    final TileSection t1Section = entry.getValue().getY().get(i);
                    final TileSection t2Section = entry.getValue().getX().get(i);

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
     * {@inheritDoc}
     */
    @Override
    public boolean isPositionValid(final Pair<Integer, Integer> position, final Tile tile) {
        final Set<Tile> neighbours = getTileNeighbours(position);
        return !neighbours.isEmpty() && neighbours.stream().allMatch(t -> tilesMatch(position, tile, t));
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public void joinTiles(final Tile t1, final Tile t2) {
        for (final var entry : NEIGHBOUR_TILES_CHECK.entrySet()) {
            if (Direction.match(entry.getKey(), t1.getPosition().get(), t2.getPosition().get())) {
                for (int i = 0; i < TileSection.getSectionsPerSide(); i++) {
                    final TileSection t1Section = entry.getValue().getY().get(i);
                    final TileSection t2Section = entry.getValue().getX().get(i);

                    t1.closeSection(t1Section);
                    t2.closeSection(t2Section);

                    final GameSet t1GameSet = getGameSetInSection(t1, t1Section);
                    final GameSet t2GameSet = getGameSetInSection(t2, t2Section);
                    if (!t1GameSet.equals(t2GameSet)) {
                        final GameSet joinedGameSet = gameSetFactory.createJoinedSet(t1GameSet, t2GameSet);

                        for (final var tileEntry : crossReferences.remove(t2GameSet).entrySet()) {
                            for (final var section : tileEntry.getValue()) {
                                addSection(joinedGameSet, tileEntry.getKey(), section);
                            }
                        }
                        addSection(joinedGameSet, t2, t2Section);

                        for (final var tileEntry : crossReferences.remove(t1GameSet).entrySet()) {
                            for (final var section : tileEntry.getValue()) {
                                addSection(joinedGameSet, tileEntry.getKey(), section);
                            }
                        }
                        addSection(joinedGameSet, t1, t1Section);
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     *
     * Gets whether or not the specific section in a Tile is near a certain GameSet.
     *
     * @param tile the tile that contains the specific section
     * @param tileSection the section to add to the gameSet
     * @param gameSet the GameSet to check if it's near
     * @return whether or not the specific section in a Tile is near a certain GameSet
     */
    private boolean isSectionNearToGameset(final Tile tile, final TileSection tileSection, final GameSet gameSet) {
        return getGameSetInSection(tile, TileSection.next(tileSection)).equals(gameSet)
                || getGameSetInSection(tile, TileSection.previous(tileSection)).equals(gameSet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameSet getGameSetInSection(final Tile tile, final TileSection tileSection) {
        return crossReferences.entrySet().stream()
                .filter(e -> e.getValue().containsKey(tile) && e.getValue().get(tile).contains(tileSection))
                .findFirst().get().getKey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GameSet> getGameSetsInTile(final Tile tile) {
        return new HashSet<>(crossReferences.entrySet().stream()
                .filter(e -> e.getValue().containsKey(tile))
                .map(e -> e.getKey()).toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GameSet> getAllGameSets() {
        return crossReferences.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Tile, Set<TileSection>> getTilesFromGameSet(final GameSet gameSet) {
        return this.crossReferences.get(gameSet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean placeMeeple(final Meeple meeple, final Tile tile, final TileSection tileSection) {
        final GameSet gameSet = getGameSetInSection(tile, tileSection);
        if (meeple.isPlaced() || !gameSet.isMeepleFree()) {
            return false;
        }

        gameSet.addMeeple(meeple);
        meeple.place(tile, tileSection);
        return true;
    }
}
