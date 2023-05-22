package it.unibo.caesena.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.Expansion;
import it.unibo.caesena.model.Game;
import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetImpl;
import it.unibo.caesena.model.gameset.GameSetType;
import it.unibo.caesena.model.meeple.MeepleImpl;
import it.unibo.caesena.model.meeple.MeepleType;
import it.unibo.caesena.model.player.Player;
import it.unibo.caesena.model.player.PlayerInGameImpl;
import it.unibo.caesena.model.server.CardinalPoint;
import it.unibo.caesena.model.server.Continent;
import it.unibo.caesena.model.server.Region;
import it.unibo.caesena.model.server.Server;
import it.unibo.caesena.model.tile.MutableTile;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileImpl;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.model.tile.TileSectionType;
import it.unibo.caesena.model.tile.TileType;
import it.unibo.caesena.model.tile.TileTypeConfiguration;
import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.UserInterface;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * {@inheritDoc}
 *
 * Implementation of the Controller interface.
 */
public final class ControllerImpl implements Controller {

    private Map<Direction, Pair<List<TileSectionType>, List<TileSectionType>>> NEIGHBOUR_TILES_CHECK;
    public static final String BASIC_EXPANSION_NAME = "Basic";
    public static final int POINTS_CLOSED_CITY_NEARBY_FIELD = 3;
    public static final int POINTS_TILE_NEARBY_MONASTERY = 1;
    public static final int POINTS_CLOSED_MONASTERY = 9;
    public static final int MEEPLES_PER_PLAYER = 8;
    public static final int PENNANT_POINTS = 2;

    private final List<UserInterface> userInterfaces;
    private final CriteriaBuilder cb;
    private final SessionFactory sessionFactory;
    private final Session session;

    private Game game;

    /**
     * Class constructor.
     */
    public ControllerImpl() {
        this.userInterfaces = new ArrayList<>();
        Properties properties = new Properties();
        properties.setProperty("connection.driver_class", "com.mysql.jdbc.Driver");
        properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/caesena");
        properties.setProperty("hibernate.connection.username", "caesena");
        properties.setProperty("hibernate.connection.password", "caesena");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        // properties.setProperty("hibernate.show_sql", "true");

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Player.class);
        configuration.addAnnotatedClass(MeepleImpl.class);
        configuration.addAnnotatedClass(MeepleType.class);
        configuration.addAnnotatedClass(GameSetImpl.class);
        configuration.addAnnotatedClass(GameSetType.class);
        configuration.addAnnotatedClass(PlayerInGameImpl.class);
        configuration.addAnnotatedClass(CardinalPoint.class);
        configuration.addAnnotatedClass(Continent.class);
        configuration.addAnnotatedClass(Region.class);
        configuration.addAnnotatedClass(Server.class);
        configuration.addAnnotatedClass(TileImpl.class);
        configuration.addAnnotatedClass(TileType.class);
        configuration.addAnnotatedClass(TileSection.class);
        configuration.addAnnotatedClass(TileSectionType.class);
        configuration.addAnnotatedClass(TileTypeConfiguration.class);
        configuration.addAnnotatedClass(Color.class);
        configuration.addAnnotatedClass(Expansion.class);
        configuration.addAnnotatedClass(Game.class);
        configuration.setProperties(properties);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(properties).build();

        this.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        this.session = sessionFactory.openSession();
        this.cb = this.session.getCriteriaBuilder();

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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createNewGame(Server server, List<Pair<String, Color>> playersData, List<String> expansionNames) {
        if (playersData.stream().map(Pair::getX).collect(Collectors.toSet()).size() == playersData.size()
            && playersData.stream().map(Pair::getY).collect(Collectors.toSet()).size() == playersData.size()) {
            expansionNames = new ArrayList<>(expansionNames);
            expansionNames.add(BASIC_EXPANSION_NAME);
            session.beginTransaction();
            CriteriaQuery<Expansion> query = cb.createQuery(Expansion.class);
            Root<Expansion> root = query.from(Expansion.class);
            query.select(root);
            query.where(root.get("name").in(expansionNames));
            List<Expansion> expansions = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            if (expansions.isEmpty() || playersData.isEmpty()) {
                return;
            }

            this.game = new Game(session, server);
            playersData = new ArrayList<>(playersData);
            Collections.shuffle(playersData);

            session.beginTransaction();
            session.persist(this.game);
            for (int i = 0; i < playersData.size(); i++) {
                final String playerName = playersData.get(i).getX();
                Player player = session.get(Player.class, playerName);
                if (player == null) {
                    player = new Player(playerName);
                    session.persist(player);
                }
                final PlayerInGameImpl playerInGame = new PlayerInGameImpl(player, playersData.get(i).getY(), i, game);
                if (i == 0) {
                    playerInGame.setCurrent(true);
                }
                session.persist(playerInGame);
            }
            session.getTransaction().commit();

            createTiles(expansions);
            createMeeples(expansions);

            drawNewTile();
            this.placeCurrentTile(new Pair<>(0, 0));
            drawNewTile();
            updateUserInterfaces();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Expansion> getAllExpansions() {
        session.beginTransaction();
        CriteriaQuery<Expansion> query = cb.createQuery(Expansion.class);
        query.select(query.from(Expansion.class));
        List<Expansion> expansions = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        expansions.removeIf(e -> e.getName().equals(BASIC_EXPANSION_NAME));
        return expansions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TileSectionType> getAllTileSectionTypes() {
        session.beginTransaction();
        CriteriaQuery<TileSectionType> query = cb.createQuery(TileSectionType.class);
        query.select(query.from(TileSectionType.class));
        List<TileSectionType> tileSectionTypes = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return tileSectionTypes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileSectionType getTileSectionTypeFromName(final String name) {
        session.beginTransaction();
        TileSectionType tileSectionType = session.get(TileSectionType.class, name);
        session.getTransaction().commit();
        return tileSectionType;
    }

    private List<TileSection> getTileSectionsFromGameSet(final GameSet gameSet) {
        session.beginTransaction();
        CriteriaQuery<TileSection> query = cb.createQuery(TileSection.class);
        Root<TileSection> root = query.from(TileSection.class);
        query.select(root).where(cb.equal(root.get("gameSet"), gameSet));
        List<TileSection> tileSections = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return tileSections;
    }

    public List<TileImpl> getTilesFromGameSet(final GameSet gameSet) {
        session.beginTransaction();
        CriteriaQuery<TileImpl> query = cb.createQuery(TileImpl.class);
        Root<TileSection> root = query.from(TileSection.class);
        query.select(root.get("tile"));
        query.where(cb.and(cb.equal(root.get("gameSet"), gameSet)),
                cb.equal(root.get("tile").get("game"), game));
        List<TileImpl> tiles = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return tiles;
    }

    public List<GameSetImpl> getGameSetsInTile(final MutableTile tile) {
        session.beginTransaction();
        CriteriaQuery<GameSetImpl> query = cb.createQuery(GameSetImpl.class);
        Root<TileSection> root = query.from(TileSection.class);
        query.select(root.get("gameSet"));
        query.where(cb.equal(root.get("tile"), tile));
        List<GameSetImpl> gameSets = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return gameSets;
    }

    public boolean isPositionValid(final Pair<Integer, Integer> position, final MutableTile tile) {
        final Set<MutableTile> neighbours = getTileNeighbours(position);
        return !neighbours.isEmpty() && neighbours.stream().allMatch(t -> tilesMatch(position, tile, t));
    }

    public Set<MutableTile> getTileNeighbours(final Pair<Integer, Integer> position) {
        final var neighboursDirections = Stream.of(Direction.values())
            .map(d -> new Pair<Integer, Integer>(position.getX() + d.getX(), position.getY() + d.getY()))
            .toList();
        return getPlacedTiles().stream()
            .filter(t -> neighboursDirections.contains(t.getPosition().get()))
            .collect(Collectors.toSet());
    }

    public void rotateTileClockwise(final MutableTile tile) {
        session.beginTransaction();
        CriteriaQuery<TileSection> query = cb.createQuery(TileSection.class);
        Root<TileSection> root = query.from(TileSection.class);
        query.select(root);
        query.where(cb.equal(root.get("tile"), tile));
        List<TileSection> tileSections = session.createQuery(query).getResultList();
        tileSections.forEach(session::remove);
        session.getTransaction().commit();

        final List<TileSection> newSections = new ArrayList<>(tileSections);
        for (final var tileSection : newSections) {
            tileSection.setType(tileSection.getType().rotateClockwise());
        }
        tile.rotate();

        session.beginTransaction();
        session.merge(tile);
        newSections.forEach(session::persist);
        session.getTransaction().commit();
    }

    public GameSetType getGameSetTypeFromName(final String name) {
        session.beginTransaction();
        GameSetType gameSetType = session.get(GameSetType.class, name);
        session.clear();
        session.getTransaction().commit();
        return gameSetType;
    }

    public Set<GameSet> getFieldGameSetsNearGameSet(final GameSet gameSet) {
        final Set<GameSet> fieldsNearCity = new HashSet<>();

        for (final var tile : getTilesFromGameSet(gameSet)) {
            for (final var tileSectionType : getAllTileSectionTypes()) {
                final GameSet fieldGameSet = getGameSetInSectionType(tile, tileSectionType);

                if (fieldGameSet.getType().equals(getGameSetTypeFromName("FIELD"))
                        && isSectionNearToGameset(tile, tileSectionType, gameSet)) {
                    fieldsNearCity.add(fieldGameSet);
                }
            }
        }

        return fieldsNearCity;
    }

    private TileSection getTileSectionFromTile(final MutableTile tile, final TileSectionType tileSectionType) {
        session.beginTransaction();
        CriteriaQuery<TileSection> query = cb.createQuery(TileSection.class);
        Root<TileSection> root = query.from(TileSection.class);
        query.select(root);
        query.where(cb.and(cb.equal(root.get("tile"), tile),
            cb.equal(root.get("type"), tileSectionType)));
        List<TileSection> tileSections = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return tileSections.get(0);
    }

    public void joinTiles(final MutableTile t1, final MutableTile t2) {
        for (final var entry : NEIGHBOUR_TILES_CHECK.entrySet()) {
            if (Direction.match(entry.getKey(), t1.getPosition().get(), t2.getPosition().get())) {
                for (int i = 0; i < TileSectionType.getSectionsPerSide(); i++) {
                    final TileSection t1Section = getTileSectionFromTile(t1, entry.getValue().getY().get(i));
                    final TileSection t2Section = getTileSectionFromTile(t2, entry.getValue().getX().get(i));

                    t1Section.close();
                    t2Section.close();

                    final GameSetImpl t1GameSet = t1Section.getGameSet();
                    final GameSetImpl t2GameSet = t2Section.getGameSet();
                    if (!t1GameSet.equals(t2GameSet)) {
                        List<TileSection> tileSections1 = getTileSectionsFromGameSet(t1GameSet);
                        List<TileSection> tileSections2 = getTileSectionsFromGameSet(t2GameSet);

                        final int points = t1GameSet.getPoints() + t2GameSet.getPoints();
                        final GameSetImpl joinedGameSet = new GameSetImpl(t1GameSet.getType());
                        joinedGameSet.setPoints(points);
                        tileSections1.forEach(s -> s.setGameSet(joinedGameSet));
                        tileSections2.forEach(s -> s.setGameSet(joinedGameSet));
                        t1Section.setGameSet(joinedGameSet);
                        t2Section.setGameSet(joinedGameSet);

                        session.beginTransaction();
                        session.persist(joinedGameSet);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.session.close();
        this.sessionFactory.close();
    }

    private void createMeeples(final List<Expansion> expansions) {
        this.session.beginTransaction();
        CriteriaQuery<MeepleType> query = cb.createQuery(MeepleType.class);
        Root<MeepleType> root = query.from(MeepleType.class);
        List<MeepleType> meepleTypes = session.createQuery(query.select(root)
            .where(root.get("expansion").in(expansions)))
            .getResultList();
        this.session.getTransaction().commit();

        final List<MeepleImpl> meeples = new ArrayList<>();
        for (final var meepleType : meepleTypes) {
            for (final var player : getPlayers()) {
                for (int i = 0; i < meepleType.getQuantity(); i++) {
                    meeples.add(new MeepleImpl(meepleType, player));
                }
            }
        }

        this.session.beginTransaction();
        meeples.forEach(session::persist);
        this.session.getTransaction().commit();
    }

    private void createTiles(final List<Expansion> expansions) {
        this.session.beginTransaction();
        CriteriaQuery<TileTypeConfiguration> query = cb.createQuery(TileTypeConfiguration.class);
        Root<TileTypeConfiguration> root = query.from(TileTypeConfiguration.class);
        List<TileTypeConfiguration> tileTypeConfigurations = session.createQuery(query.select(root)
            .where(root.get("tileType").get("expansion").in(expansions))
            .orderBy(cb.asc(root.get("tileType")), cb.asc(root.get("id"))))
            .getResultList();
        this.session.getTransaction().commit();

        int currentId = -1;
        int tilesQuantity = 0;
        int tileSectionsNum = getAllTileSectionTypes().size();
        List<TileImpl> tiles = new ArrayList<>();
        List<GameSetImpl> gameSets = new ArrayList<>();
        List<TileSection> tileSections = new ArrayList<>();
        for (int i = 0; i < tileTypeConfigurations.size(); i++) {
            final TileTypeConfiguration tileTypeConfiguration = tileTypeConfigurations.get(i);
            // cambio di Tile (finite le tile section della vecchia tile)
            if (i % tileSectionsNum == 0) {
                // se abbiamo appena cambiato tipo di tile si riparte da zero con il conteggio di esse
                if (tiles.isEmpty() || tileTypeConfiguration.getTileType() != tiles.get(tiles.size()-1).getTileType()) {
                    tilesQuantity = 0;
                }

                tiles.add(new TileImpl(tiles.size(), game, tileTypeConfiguration.getTileType()));
                tilesQuantity++;
                currentId = -1;
            }

            // cambio di gameSet
            if (tileTypeConfiguration.getId() != currentId) {
                currentId = tileTypeConfiguration.getId();
                gameSets.add(new GameSetImpl(tileTypeConfiguration.getGameSetType()));
            }

            if (tileTypeConfiguration.hasPennant()) {
                gameSets.get(gameSets.size()-1).addPoints(PENNANT_POINTS);
            }
            final TileSection tileSection = new TileSection(tiles.get(tiles.size()-1),
                tileTypeConfiguration.getTileSectionType(), gameSets.get(gameSets.size()-1));
            if (tileTypeConfiguration.isClosed()) {
                tileSection.close();
            }
            tileSections.add(tileSection);

            // se era l'ultima tile section di quella tile
            if ((i % tileSectionsNum) == tileSectionsNum - 1) {
                // se non abbiamo finito di generare quel tipo di tile
                if (tilesQuantity < tileTypeConfiguration.getTileType().getQuantity()) {
                    i -= tileSectionsNum;
                }
            }
        }
        Collections.shuffle(tiles);
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).setOrder(i);
        }
        tiles.get(0).setCurrent(true);

        this.session.beginTransaction();
        tiles.forEach(session::persist);
        gameSets.forEach(session::persist);
        tileSections.forEach(session::persist);
        this.session.getTransaction().commit();
    }

    public GameSet getGameSetInSectionType(final MutableTile tile, final TileSectionType tileSectionType) {
        this.session.beginTransaction();
        CriteriaQuery<TileSection> query = cb.createQuery(TileSection.class);
        Root<TileSection> root = query.from(TileSection.class);
        query.select(root);
        query.where(cb.and(cb.equal(root.get("type"), tileSectionType),
            cb.equal(root.get("tile"), tile)));
        List<TileSection> tileSections = session.createQuery(query).getResultList();
        this.session.getTransaction().commit();
        return tileSections.get(0).getGameSet();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void endTurn() {
        final TileImpl currentTile = getCurrentTile().get();
        getGameSetsInTile(currentTile).stream()
            .filter(this::isGameSetClosed)
            .forEach(this::closeGameSet);

        for (final var nearTile : getPlacedTiles()) {
            if (areTilesNear(currentTile, nearTile)) {
                GameSet centerGameset = getGameSetInSectionType(nearTile, getTileSectionTypeFromName("CENTER"));
                if (centerGameset.getType().equals(getGameSetTypeFromName("MONASTERY"))) {
                    centerGameset.addPoints(POINTS_TILE_NEARBY_MONASTERY);
                    session.beginTransaction();
                    session.merge(centerGameset);
                    session.getTransaction().commit();
                    if (isGameSetClosed(centerGameset)) {
                        closeGameSet(centerGameset);
                    }
                }

                centerGameset = getGameSetInSectionType(currentTile, getTileSectionTypeFromName("CENTER"));
                if (centerGameset.getType().equals(getGameSetTypeFromName("MONASTERY"))) {
                    centerGameset.addPoints(POINTS_TILE_NEARBY_MONASTERY);
                    session.beginTransaction();
                    session.merge(centerGameset);
                    session.getTransaction().commit();
                    if (isGameSetClosed(centerGameset)) {
                        closeGameSet(centerGameset);
                    }
                }
            }
        }

        drawNewTile();
        PlayerInGameImpl currentPlayer = getCurrentPlayer().get();
        session.beginTransaction();
        currentPlayer.setCurrent(false);
        CriteriaQuery<PlayerInGameImpl> query = cb.createQuery(PlayerInGameImpl.class);
        Root<PlayerInGameImpl> root = query.from(PlayerInGameImpl.class);
        List<PlayerInGameImpl> players = session.createQuery(query.select(root)
            .where(cb.equal(root.get("game"), this.game))).getResultList();
        session.createQuery(query.select(root).where(cb.and(
            cb.equal(root.get("game"), this.game)),
            cb.equal(root.get("playerOrder"), (currentPlayer.getPlayerOrder()+1) % players.size())))
            .getResultList().get(0).setCurrent(true);
        session.getTransaction().commit();
        updateUserInterfaces();
    }

    /**
     * Ends the game.
     * Its checks if there are closed cities, in which case it assings points to
     * players with meeples in surrounding fields.
     */
    private void endGame() {
        session.beginTransaction();
        CriteriaQuery<GameSetImpl> query = cb.createQuery(GameSetImpl.class);
        Root<TileSection> root = query.from(TileSection.class);
        query.select(root.get("gameSet"));
        query.where(cb.equal(root.get("tile").get("game"), game));
        List<GameSetImpl> allGameSets = session.createQuery(query).getResultList();
        session.getTransaction().commit();

        final Set<GameSet> fieldsToClose = allGameSets.stream()
            .filter(c -> c.getType().equals(getGameSetTypeFromName("CITY")))
            .filter(GameSetImpl::isClosed)
            .flatMap(c -> getFieldGameSetsNearGameSet(c).stream())
            .peek(f -> f.addPoints(POINTS_CLOSED_CITY_NEARBY_FIELD))
            .collect(Collectors.toSet());
        fieldsToClose.forEach(this::closeGameSet);

        allGameSets.stream()
            .filter(x -> !x.isClosed())
            .forEach(g -> {
                g.setPoints(g.getPoints() / g.getType().getEndGameRatio()); // TODO [SPEZ] spostare dentro closeGameSet in if game ended
                closeGameSet(g);
            });

        session.beginTransaction();
        game.setConcluded(true);
        session.merge(game);
        session.getTransaction().commit();
        updateUserInterfaces();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitGame() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        return this.game != null && this.game.isOver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PlayerInGameImpl> getCurrentPlayer() {
        session.beginTransaction();
        CriteriaQuery<PlayerInGameImpl> query = cb.createQuery(PlayerInGameImpl.class);
        Root<PlayerInGameImpl> root = query.from(PlayerInGameImpl.class);
        query.select(root);
        query.where(cb.and(cb.isTrue(root.get("current")),
            cb.equal(root.get("game"), this.game)));
        List<PlayerInGameImpl> players = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return Optional.ofNullable(players.size() == 1 ? players.get(0) : null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlayerInGameImpl> getPlayers() {
        session.beginTransaction();
        CriteriaQuery<PlayerInGameImpl> query = cb.createQuery(PlayerInGameImpl.class);
        Root<PlayerInGameImpl> root = query.from(PlayerInGameImpl.class);
        query.select(root);
        query.where(cb.equal(root.get("game"), this.game));
        List<PlayerInGameImpl> players = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return players;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TileImpl> getCurrentTile() {
        session.beginTransaction();
        CriteriaQuery<TileImpl> query = cb.createQuery(TileImpl.class);
        Root<TileImpl> root = query.from(TileImpl.class);
        query.select(root);
        query.where(cb.and(cb.isTrue(root.get("current")),
            cb.equal(root.get("game"), this.game)));
        List<TileImpl> tiles = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return Optional.ofNullable(tiles.size() == 1 ? tiles.get(0) : null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean placeCurrentTile(final Pair<Integer, Integer> position) {
        if (!isPositionValidForCurrentTile(position)) {
            return false;
        }

        final MutableTile currentTile = this.getCurrentTile().get();
        session.beginTransaction();
        currentTile.setPosition(position);
        session.merge(currentTile);
        session.getTransaction().commit();

        if (getPlacedTiles().size() > 1) {
            getTileNeighbours(position).forEach(n -> joinTiles(getCurrentTile().get(), n));
        }

        updateUserInterfaces();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rotateCurrentTile() {
        this.rotateTileClockwise(getCurrentTile().get());
        updateUserInterfaces();
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public boolean discardCurrentTile() {
        if (this.isCurrentTilePlaceable()) {
            return false;
        }
        this.drawNewTile();
        updateUserInterfaces();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPositionValidForCurrentTile(final Pair<Integer, Integer> position) {
        if (isPositionOccupied(position)) {
            return false;
        }
        if (getPlacedTiles().isEmpty()) {
            return true;
        }

        return isPositionValid(position, getCurrentTile().get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TileImpl> getPlacedTiles() {
        session.beginTransaction();
        CriteriaQuery<TileImpl> query = cb.createQuery(TileImpl.class);
        Root<TileImpl> root = query.from(TileImpl.class);
        query.select(root);
        query.where(cb.and(cb.isNotNull(root.get("xCoordinate"))),
            cb.isNotNull(root.get("yCoordinate")),
            cb.equal(root.get("game"), this.game));
        List<TileImpl> tiles = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return tiles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TileImpl> getNotPlacedTiles() {
        session.beginTransaction();
        CriteriaQuery<TileImpl> query = cb.createQuery(TileImpl.class);
        Root<TileImpl> root = query.from(TileImpl.class);
        query.select(root);
        query.where(cb.and(cb.isNull(root.get("xCoordinate"))),
            cb.isNull(root.get("yCoordinate")),
            cb.equal(root.get("game"), this.game));
        List<TileImpl> tiles = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return tiles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameSet getCurrentTileGameSetInSection(final TileSectionType section) {
        return getGameSetInSectionType(getCurrentTile().get(), section);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean placeMeeple(final TileSectionType section) {
        // TODO scegliere come vogliamo piazzare i meeple (uno a caso o permettiamo di scegliere?)
        // final Optional<MeepleImpl> currentMeeple = game.getCurrentPlayer().getMeeples().stream()
        //     .filter(m -> !m.isPlaced())
        //     .findFirst();

        // if (currentMeeple.isPresent() && !game.placeMeeple(currentMeeple.get(), getCurrentTile().get(), section)) {
        //     updateUserInterfaces();
        //     return Optional.empty();
        // }

        updateUserInterfaces();
        return true;
        // return currentMeeple.isPresent() ? Optional.of(currentMeeple.get()) : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MeepleImpl> getMeeples() {
        session.beginTransaction();
        CriteriaQuery<MeepleImpl> query = cb.createQuery(MeepleImpl.class);
        Root<MeepleImpl> root = query.from(MeepleImpl.class);
        query.select(root);
        query.where(cb.equal(root.get("owner").get("game"), this.game));
        List<MeepleImpl> meeples = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return meeples;
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public void addUserInterface(final UserInterface userInterface) {
        this.userInterfaces.add(userInterface);
        userInterface.update();
    }

    /**
     * Checks if two tile are next to eachother in any direction.
     *
     * @param t1 the first tile
     * @param t2 the second tile
     * @return true if the two tiles are next to each other, false otherwise
     */
    private boolean areTilesNear(final MutableTile t1, final MutableTile t2) {
        return Math.abs(t1.getPosition().get().getX() - t2.getPosition().get().getX()) <= 1
            && Math.abs(t1.getPosition().get().getY() - t2.getPosition().get().getY()) <= 1
            && !t1.getPosition().get().equals(t2.getPosition().get());
    }

    /**
     * Draws a new tile from the list of not placed tiles.
     */
    private void drawNewTile() {
        MutableTile currentTile = getCurrentTile().get();
        session.beginTransaction();
        currentTile.setCurrent(false);
        CriteriaQuery<TileImpl> query = cb.createQuery(TileImpl.class);
        Root<TileImpl> root = query.from(TileImpl.class);
        List<TileImpl> tiles = session.createQuery(query.select(root)
            .where(cb.and(cb.equal(root.get("game"), this.game)),
                cb.equal(root.get("tileOrder"), currentTile.getTileOrder()+1)))
            .getResultList();
        if (tiles.size() == 1) {
            tiles.get(0).setCurrent(true);
        }
        session.getTransaction().commit();
        if (tiles.isEmpty()) {
            endGame();
        }
    }

    /**
     * Checks if a given GameSet is closed.
     * A GameSet is considered closed if all the tiles that partake in it complete a
     * full structure together.
     * As an example, we can take two tiles next to each other where the matching
     * side has a single city piece.
     * In that case, we would have closed(or completed) a city gameset(or
     * structure).
     * In other words, if there is no way to make a gameset(or structure) bigger by
     * adding a new tile, it can be considered a closed gameset.
     * A special case are monasteries that are considered closed if the eight
     * surrounding positions contain a tile.
     * Another special case are fields that can't be closed.
     *
     * @param gameSet to be verified as closed
     * @return true if the gameset is closed, false otherwise
     */
    private boolean isGameSetClosed(final GameSet gameSet) {
        if (gameSet.getType().equals(getGameSetTypeFromName("FIELD"))) {
            return false;
        }

        if (gameSet.getType().equals(getGameSetTypeFromName("MONASTERY"))) {
            return gameSet.getPoints() == POINTS_CLOSED_MONASTERY;
        }

        session.beginTransaction();
        CriteriaQuery<TileSection> query = cb.createQuery(TileSection.class);
        Root<TileSection> root = query.from(TileSection.class);
        query.select(root);
        query.where(cb.and(cb.isFalse(root.get("closed"))),
            cb.equal(root.get("gameSet"), gameSet),
            cb.equal(root.get("tile").get("game"), this.game));
        List<TileSection> tileSections = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return tileSections.isEmpty();
    }

    private void closeGameSet(final GameSet gameSet) {
        session.beginTransaction();
        gameSet.close();
        session.getTransaction().commit();
        // TODO [SPEZ] chiusura del GameSet
        // if (!this.isMeepleFree()) {
        //     final Map<MutablePlayerInGame, Integer> playerMeepleStrength = new HashMap<>();

        //     for (final MutableMeeple meeple : new ArrayList<MutableMeeple>()) {
        //         final MutablePlayerInGame currentPlayer = (MutablePlayerInGame) meeple.getOwner();

        //         if (!playerMeepleStrength.containsKey(currentPlayer)) {
        //             playerMeepleStrength.put(currentPlayer, 0);
        //         }
        //         playerMeepleStrength.put(currentPlayer,
        //                 playerMeepleStrength.get(currentPlayer) + 1 * meeple.getStrength());

        //     }

        //     final int maxMeepleStrength = playerMeepleStrength.values().stream()
        //             .mapToInt(x -> x).max().getAsInt();

        //     playerMeepleStrength.entrySet().stream()
        //             .filter(e -> e.getValue().equals(maxMeepleStrength))
        //             .forEach(e -> e.getKey().addScore(this.getPoints()));
        // }

        // meeples.forEach(m -> m.remove());
    }

    /**
     * Checks to see if a given position is occupied by any other tile.
     *
     * @param position to check if occupied
     * @return true if the provided position is occupied, false otherwise
     */
    private boolean isPositionOccupied(final Pair<Integer, Integer> position) {
        for (final Tile tile : getPlacedTiles()) {
            if (tile.getPosition().get().equals(position)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a set of all the empty neighbouring positions to the given position.
     *
     * @param position at which to check for empty neighbour
     * @return A set of positions that are empty and neighbouring to the given
     *         position
     */
    private Set<Pair<Integer, Integer>> getEmptyNeighbouringPositions(final Pair<Integer, Integer> position) {
        final Set<Pair<Integer, Integer>> neighbouringPositions = new HashSet<>();
        for (final var direction : Direction.values()) {
            final Pair<Integer, Integer> neighbourPosition = new Pair<>(position.getX() + direction.getX(),
                    position.getY() + direction.getY());
            if (!isPositionOccupied(neighbourPosition)) {
                neighbouringPositions.add(neighbourPosition);
            }
        }
        return neighbouringPositions;
    }

    /**
     * Checks if the current tile is placeable.
     * A tile can not be placeable if there aren't any possible way to place it
     * next to other tile, in any orientation.
     *
     * @return true if the current tile can be placed on the board, false otherwise
     */
    private boolean isCurrentTilePlaceable() {
        boolean outcome = false;
        for (int i = 0; i < 4; i++) {
            for (final Tile tile : getPlacedTiles()) {
                final int numberOfNeighbours = getTileNeighbours(tile.getPosition().get()).size();
                if (numberOfNeighbours <= 3) {
                    final Set<Pair<Integer, Integer>> emptyPositions = this
                            .getEmptyNeighbouringPositions(tile.getPosition().get());
                    for (final Pair<Integer, Integer> position : emptyPositions) {
                        if (this.isPositionValidForCurrentTile(position)) {
                            outcome = true;
                        }
                    }
                }
            }
            this.rotateTileClockwise(getCurrentTile().get());
        }
        return outcome;
    }


    /**
     * Updates all of the user interfaces.
     */
    private void updateUserInterfaces() {
        this.userInterfaces.forEach(UserInterface::update);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasGameStarted() {
        return this.game != null && !getPlacedTiles().isEmpty();
    }

    @Override
    public void joinGame(int gameId) {
        session.beginTransaction();
        this.game = session.get(Game.class, gameId);
        session.getTransaction().commit();
    }

    @Override
    public List<Color> getDefaultColors() {
        session.beginTransaction();
        CriteriaQuery<Color> query = cb.createQuery(Color.class);
        List<Color> colors = session.createQuery(query.select(query.from(Color.class))).getResultList();
        session.getTransaction().commit();
        return colors;
    }

    @Override
    public List<Server> getAvailableServers() {
        //TODO [SPEZ] Farlo con Criteria queries, non cosi` a mano che fa schifo

        Query query = session.createQuery("from Servers s where s.active=true and s.maxGames>(select count(s) from Servers s, Games g where g.server=s)");
        return query.getResultList();
    }

    @Override
    public List<MeepleImpl> getPlayerMeeples(PlayerInGameImpl player) {
        session.beginTransaction();
        CriteriaQuery<MeepleImpl> query = cb.createQuery(MeepleImpl.class);
        Root<MeepleImpl> root = query.from(MeepleImpl.class);
        query.select(root);
        query.where(cb.equal(root.get("owner"), player));
        List<MeepleImpl> meeples = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return meeples;
    }

}
