package it.unibo.caesena.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.Session;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetType;
import it.unibo.caesena.model.meeple.MeepleImpl;
import it.unibo.caesena.model.meeple.MutableMeeple;
import it.unibo.caesena.model.player.Player;
import it.unibo.caesena.model.player.PlayerInGameImpl;
import it.unibo.caesena.model.server.Server;
import it.unibo.caesena.model.tile.MutableTile;
import it.unibo.caesena.model.tile.TileImpl;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.model.tile.TileSectionType;
import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Entity(name = "Games")
@Table(name = "Games")
@Access(AccessType.FIELD)
public class Game {

    @Transient
    private Map<Direction, Pair<List<TileSectionType>, List<TileSectionType>>> NEIGHBOUR_TILES_CHECK;
    @Transient
    public static final int POINTS_CLOSED_CITY_NEARBY_FIELD = 3;
    @Transient
    public static final int POINTS_TILE_NEARBY_MONASTERY = 1;
    @Transient
    public static final int POINTS_CLOSED_MONASTERY = 9;
    @Transient
    public static final int MEEPLES_PER_PLAYER = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameID;

    @OneToMany(mappedBy = "game")
    private List<PlayerInGameImpl> playersInGame;

    @ManyToMany
    private List<Expansion> expansions;

    @OneToMany(mappedBy = "game")
    private List<TileImpl> tiles;

    @ManyToOne
    private Server server;

    private boolean concluded;

    @Transient
    private final Session session;
    @Transient
    private final CriteriaBuilder criteriaBuilder;

    public Game(final Session session, final List<Player> players, final List<Color> colors) {
        this.session = session;
        this.criteriaBuilder = this.session.getCriteriaBuilder();
        NEIGHBOUR_TILES_CHECK = new HashMap<>(Map.of(
            Direction.UP,
            new Pair<>(List.of(getTileSectionTypeFromName("DOWN_LEFT"), getTileSectionTypeFromName("DOWN_CENTER"), getTileSectionTypeFromName("DOWN_RIGHT")),
                List.of(getTileSectionTypeFromName("UP_LEFT"), getTileSectionTypeFromName("UP_CENTER"), getTileSectionTypeFromName("UP_RIGHT"))),
            Direction.DOWN,
            new Pair<>(List.of(getTileSectionTypeFromName("UP_LEFT"), getTileSectionTypeFromName("UP_CENTER"), getTileSectionTypeFromName("UP_RIGHT")),
                List.of(getTileSectionTypeFromName("DOWN_LEFT"), getTileSectionTypeFromName("DOWN_CENTER"), getTileSectionTypeFromName("DOWN_RIGHT"))),
            Direction.LEFT,
            new Pair<>(List.of(getTileSectionTypeFromName("RIGHT_UP"), getTileSectionTypeFromName("RIGHT_CENTER"), getTileSectionTypeFromName("RIGHT_DOWN")),
                List.of(getTileSectionTypeFromName("LEFT_UP"), getTileSectionTypeFromName("LEFT_CENTER"), getTileSectionTypeFromName("LEFT_DOWN"))),
            Direction.RIGHT,
            new Pair<>(List.of(getTileSectionTypeFromName("LEFT_UP"), getTileSectionTypeFromName("LEFT_CENTER"), getTileSectionTypeFromName("LEFT_DOWN")),
                List.of(getTileSectionTypeFromName("RIGHT_UP"), getTileSectionTypeFromName("RIGHT_CENTER"), getTileSectionTypeFromName("RIGHT_DOWN")))));

        // TODO createPlayers
        createTiles();
    }

    private void createTiles() {

    }

    /**
     * Gets whether or not two tiles match and can be placed next to each other.
     *
     * @param position the position to use if <code>t1</code> is not placed
     * @param t1 one of two tile to check
     * @param t2 one of two tile to check
     * @return whether or not two tiles match and can be placed next to each other
     */
    private boolean tilesMatch(final Pair<Integer, Integer> position, final MutableTile t1, final MutableTile t2) {
        for (final var entry : NEIGHBOUR_TILES_CHECK.entrySet()) {
            if (Direction.match(entry.getKey(), position, t2.getPosition().get())) {
                for (int i = 0; i < TileSectionType.getSectionsPerSide(); i++) {
                    final TileSectionType t1Section = entry.getValue().getY().get(i);
                    final TileSectionType t2Section = entry.getValue().getX().get(i);

                    if (!getGameSetInSectionType(t1, t1Section).getType()
                            .equals(getGameSetInSectionType(t2, t2Section).getType())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public GameSet getGameSetInSectionType(final MutableTile tile, final TileSectionType tileSection) {
        return tile.getSections().stream().filter(s -> s.getType().equals(tileSection))
            .findFirst().get().getGameSet();
    }

    public TileSectionType getTileSectionTypeFromName(final String name) {
        session.beginTransaction();
        TileSectionType tileSectionType = session.get(TileSectionType.class, name);
        session.getTransaction().commit();
        return tileSectionType;
    }

    /**
     *
     * Gets whether or not the specific section in a MutableTile is near a certain GameSet.
     *
     * @param tile the tile that contains the specific section
     * @param tileSection the section to add to the gameSet
     * @param gameSet the GameSet to check if it's near
     * @return whether or not the specific section in a MutableTile is near a certain GameSet
     */
    private boolean isSectionNearToGameset(final MutableTile tile, final TileSectionType tileSection, final GameSet gameSet) {
        return getGameSetInSectionType(tile, TileSectionType.next(tileSection)).equals(gameSet)
                || getGameSetInSectionType(tile, TileSectionType.previous(tileSection)).equals(gameSet);
    }

    public List<GameSet> getAllGameSets() {
        return tiles.stream().flatMap(t -> t.getSections().stream()).map(TileSection::getGameSet).toList();
    }

    public Set<MutableTile> getTilesFromGameSet(final GameSet gameSet) {
        // return ;
        return null;
    }

    public boolean placeMeeple(final MutableMeeple meeple, final MutableTile tile, final TileSectionType tileSection) {
        final GameSet gameSet = getGameSetInSectionType(tile, tileSection);
        if (meeple.isPlaced() || !gameSet.isMeepleFree()) {
            return false;
        }

        gameSet.addMeeple(meeple);
        meeple.place();
        //TODO
        return true;
    }

    public Set<GameSet> getGameSetsInTile(final MutableTile tile) {
        // return new HashSet<>(crossReferences.entrySet().stream()
        //         .filter(e -> e.getValue().containsKey(tile))
        //         .map(e -> e.getKey()).toList());
        return null;
    }

    public boolean isPositionValid(final Pair<Integer, Integer> position, final MutableTile tile) {
        final Set<MutableTile> neighbours = getTileNeighbours(position);
        return !neighbours.isEmpty() && neighbours.stream().allMatch(t -> tilesMatch(position, tile, t));
    }

    public Set<MutableTile> getTileNeighbours(final Pair<Integer, Integer> position) {
        final var neighboursDirections = Stream.of(Direction.values())
            .map(d -> new Pair<Integer, Integer>(position.getX() + d.getX(), position.getY() + d.getY()))
            .toList();

        return tiles.stream()
            .filter(MutableTile::isPlaced)
            .filter(t -> neighboursDirections.contains(t.getPosition().get()))
            .collect(Collectors.toSet());
    }

    public void rotateTileClockwise(final MutableTile tile) {
        // for (final var entry : crossReferences.entrySet()) {
        //     if (entry.getValue().containsKey(tile)) {
        //         final var sections = entry.getValue().get(tile);
        //         entry.getValue().put(tile, new HashSet<>(
        //                 sections.stream().map(TileSectionType::rotateClockwise).toList()));
        //     }
        // }
        tile.rotate();
    }

    public List<TileSectionType> getAllTileSectionTypes() {
        session.beginTransaction();
        CriteriaQuery<TileSectionType> query = criteriaBuilder.createQuery(TileSectionType.class);
        List<TileSectionType> tileSectionTypes = session.createQuery(query.select(query.from(TileSectionType.class))).getResultList();
        session.getTransaction().commit();
        return tileSectionTypes;
    }

    public Set<GameSet> getFieldGameSetsNearGameSet(final GameSet gameSet) {
        final Set<GameSet> fieldsNearCity = new HashSet<>();

        for (final var tile : getTilesFromGameSet(gameSet)) {
            for (final var tileSection : getAllTileSectionTypes()) {
                final GameSet fieldGameSet = getGameSetInSectionType(tile, tileSection);

                if (fieldGameSet.getType().equals(GameSetType.getFromName("FIELD"))
                        && isSectionNearToGameset(tile, tileSection, gameSet)) {
                    fieldsNearCity.add(fieldGameSet);
                }
            }
        }

        return fieldsNearCity;
    }

    public void joinTiles(final MutableTile t1, final MutableTile t2) {
        for (final var entry : NEIGHBOUR_TILES_CHECK.entrySet()) {
            if (Direction.match(entry.getKey(), t1.getPosition().get(), t2.getPosition().get())) {
                for (int i = 0; i < TileSectionType.getSectionsPerSide(); i++) {
                    // final TileSection t1Section = entry.getValue().getY().get(i);
                    // final TileSection t2Section = entry.getValue().getX().get(i);

                    // t1.closeSection(t1Section);
                    // t2.closeSection(t2Section);

                    // final GameSet t1GameSet = getGameSetInSectionType(t1, t1Section);
                    // final GameSet t2GameSet = getGameSetInSectionType(t2, t2Section);
                    // if (!t1GameSet.equals(t2GameSet)) {
                    //     final GameSet joinedGameSet = gameSetFactory.createJoinedSet(t1GameSet, t2GameSet);

                    //     crossReferences.remove(t2GameSet).forEach((k, v) -> addSections(joinedGameSet, k, v));
                    //     addSections(joinedGameSet, t2, Set.of(t2Section));

                    //     crossReferences.remove(t1GameSet).forEach((k, v) -> addSections(joinedGameSet, k, v));
                    //     addSections(joinedGameSet, t1, Set.of(t1Section));
                    // }
                }
            }
        }
    }

    public TileImpl getCurrentTile() {
        return tiles.stream().filter(TileImpl::isCurrent).findFirst().get();
    }

    public PlayerInGameImpl getCurrentPlayer() {
        return playersInGame.stream().filter(PlayerInGameImpl::isCurrent).findFirst().get();
    }

    public List<PlayerInGameImpl> getPlayersInGame() {
        return playersInGame;
    }

    public List<TileImpl> getTiles() {
        return this.tiles;
    }

    public boolean isOver() {
        return this.concluded;
    }

    public List<MeepleImpl> getMeeples() {
        return playersInGame.stream().flatMap(p -> p.getMeeples().stream()).toList();
    }

    public void drawNewTile() {
        session.beginTransaction();
        TileImpl currentTile = getCurrentTile();
        currentTile.setCurrent(false);
        CriteriaQuery<TileImpl> query = criteriaBuilder.createQuery(TileImpl.class);
        Root<TileImpl> root = query.from(TileImpl.class);
        List<TileImpl> tiles = session.createQuery(query.select(root)
            .where(criteriaBuilder.equal(root.get("tileOrder"), currentTile.getTileOrder() + 1))
            .where(criteriaBuilder.equal(root.get("game"), gameID)))
            .getResultList();
        if (tiles.isEmpty()) {
            // TODO end game
        } else {
            tiles.get(0).setCurrent(true);
        }
        session.getTransaction().commit();
    }

    public List<Expansion> getExpansions() {
        return expansions;
    }

    public Server getServer() {
        return server;
    }

    public int getGameID() {
        return gameID;
    }

    public boolean isConcluded() {
        return concluded;
    }
}
