package it.unibo.caesena.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.Session;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;
import it.unibo.caesena.model.gameset.GameSetImpl;
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
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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
    private Session session;
    @Transient
    private CriteriaBuilder criteriaBuilder;

    public Game() {}

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

        this.playersInGame = new ArrayList<>();
        session.beginTransaction();
        for (int i = 0; i < players.size(); i++) {
            final PlayerInGameImpl player = new PlayerInGameImpl(players.get(i), colors.get(i), 0);
            playersInGame.add(player);
            session.persist(player);
        }
        session.getTransaction().commit();
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

    public GameSetImpl getGameSetInSectionType(final MutableTile tile, final TileSectionType tileSection) {
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
        return getGameSetInSectionType(tile, tileSection.getNext()).equals(gameSet)
                || getGameSetInSectionType(tile, tileSection.getPrevious()).equals(gameSet);
    }

    public List<GameSetImpl> getAllGameSets() {
        return tiles.stream().flatMap(t -> t.getSections().stream()).map(TileSection::getGameSet).toList();
    }

    public List<TileImpl> getTilesFromGameSet(final GameSetImpl gameSet) {
        session.beginTransaction();
        CriteriaQuery<TileImpl> query = criteriaBuilder.createQuery(TileImpl.class);
        // Root<TileSection> rootSection = query.from(TileSection.class);
        Root<TileImpl> rootTile = query.from(TileImpl.class);

        Join<TileImpl, TileSection> join = rootTile.join("sections", JoinType.INNER);
        // join.on(criteriaBuilder.equal(rootSection.get("tile_tileOrder"), rootTile.get("tileOrder")),
            // criteriaBuilder.equal(rootSection.get("tile_game_gameID"), rootTile.get("game_gameID")));

        List<TileImpl> tiles = session.createQuery(query.select(rootTile)
            .where(criteriaBuilder.and(
                criteriaBuilder.equal(join.get("tile_tileOrder"), join.get("tileOrder")),
                criteriaBuilder.equal(join.get("tile_game_gameID"), join.get("game_gameID")),
                criteriaBuilder.equal(join.get("gameSet_id"), gameSet.getId()))))
            .getResultList();
        session.getTransaction().commit();
        return tiles;
    }

    public boolean placeMeeple(final MutableMeeple meeple, final MutableTile tile, final TileSectionType tileSection) {
        // final GameSet gameSet = getGameSetInSectionType(tile, tileSection);
        // if (meeple.isPlaced() || !gameSet.isMeepleFree()) {
        //     return false;
        // }

        // gameSet.addMeeple(meeple);
        // meeple.place();
        //TODO
        return true;
    }

    public List<GameSetImpl> getGameSetsInTile(final TileImpl tile) {
        return tile.getSections().stream().map(TileSection::getGameSet).toList();
    }

    public boolean isPositionValid(final Pair<Integer, Integer> position, final MutableTile tile) {
        final Set<TileImpl> neighbours = getTileNeighbours(position);
        return !neighbours.isEmpty() && neighbours.stream().allMatch(t -> tilesMatch(position, tile, t));
    }

    public Set<TileImpl> getTileNeighbours(final Pair<Integer, Integer> position) {
        final var neighboursDirections = Stream.of(Direction.values())
            .map(d -> new Pair<Integer, Integer>(position.getX() + d.getX(), position.getY() + d.getY()))
            .toList();

        return tiles.stream()
            .filter(MutableTile::isPlaced)
            .filter(t -> neighboursDirections.contains(t.getPosition().get()))
            .collect(Collectors.toSet());
    }

    public void rotateTileClockwise(final MutableTile tile) {
        final List<TileSection> tileSections = tile.getSections();
        for (final var tileSection : tileSections) {
            tileSection.setType(TileSectionType.rotateClockwise(tileSection.getType()));
        }

        session.beginTransaction();
        tile.rotate();
        tileSections.forEach(session::merge);
        session.getTransaction().commit();
    }

    public GameSetType getGameSetTypeFromName(final String name) {
        session.beginTransaction();
        GameSetType gameSetType = session.get(GameSetType.class, name);
        session.getTransaction().commit();
        return gameSetType;
    }

    public List<TileSectionType> getAllTileSectionTypes() {
        session.beginTransaction();
        CriteriaQuery<TileSectionType> query = criteriaBuilder.createQuery(TileSectionType.class);
        List<TileSectionType> tileSectionTypes = session.createQuery(query.select(query.from(TileSectionType.class))).getResultList();
        session.getTransaction().commit();
        return tileSectionTypes;
    }

    public Set<GameSet> getFieldGameSetsNearGameSet(final GameSetImpl gameSet) {
        final Set<GameSet> fieldsNearCity = new HashSet<>();

        for (final var tile : getTilesFromGameSet(gameSet)) {
            for (final var tileSection : getAllTileSectionTypes()) {
                final GameSet fieldGameSet = getGameSetInSectionType(tile, tileSection);

                if (fieldGameSet.getType().equals(getGameSetTypeFromName("FIELD"))
                        && isSectionNearToGameset(tile, tileSection, gameSet)) {
                    fieldsNearCity.add(fieldGameSet);
                }
            }
        }

        return fieldsNearCity;
    }

    public void joinTiles(final TileImpl t1, final TileImpl t2) {
        for (final var entry : NEIGHBOUR_TILES_CHECK.entrySet()) {
            if (Direction.match(entry.getKey(), t1.getPosition().get(), t2.getPosition().get())) {
                for (int i = 0; i < TileSectionType.getSectionsPerSide(); i++) {
                    final TileSectionType t1SectionType = entry.getValue().getY().get(i);
                    final TileSectionType t2SectionType = entry.getValue().getX().get(i);

                    // t1.closeSection(t1SectionType);
                    // t2.closeSection(t2SectionType);

                    final GameSetImpl t1GameSet = getGameSetInSectionType(t1, t1SectionType);
                    final GameSetImpl t2GameSet = getGameSetInSectionType(t2, t2SectionType);
                    if (!t1GameSet.equals(t2GameSet)) {
                        final GameSetImpl joinedGameSet = new GameSetFactoryImpl().createJoinedSet(t1GameSet, t2GameSet);

                        session.beginTransaction();
                        CriteriaQuery<TileSection> query = criteriaBuilder.createQuery(TileSection.class);
                        Root<TileSection> root = query.from(TileSection.class);
                        List<TileSection> tileSections1 = session.createQuery(query.select(root)
                            .where(criteriaBuilder.equal(root.get("gameSet_id"), t1GameSet.getId())))
                            .getResultList();
                        List<TileSection> tileSections2 = session.createQuery(query.select(root)
                            .where(criteriaBuilder.equal(root.get("gameSet_id"), t2GameSet.getId())))
                            .getResultList();
                        session.getTransaction().commit();

                        tileSections1.forEach(s -> s.setGameSet(joinedGameSet));
                        tileSections2.forEach(s -> s.setGameSet(joinedGameSet));
                        final TileSection t1Section = t1.getSections().stream().filter(s -> s.getType().equals(t1SectionType)).findFirst().get();
                        t1Section.setGameSet(joinedGameSet);
                        final TileSection t2Section = t2.getSections().stream().filter(s -> s.getType().equals(t1SectionType)).findFirst().get();
                        t2Section.setGameSet(joinedGameSet);

                        session.beginTransaction();
                        session.merge(t1Section);
                        session.merge(t2Section);
                        tileSections1.forEach(session::merge);
                        tileSections2.forEach(session::merge);
                        session.getTransaction().commit();
                    }
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
