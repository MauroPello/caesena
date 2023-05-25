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
    private Optional<TileImpl> currentTile;
    private Optional<PlayerInGameImpl> currentPlayer;

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
                new Pair<>(
                        List.of(getTileSectionTypeFromName("DOWN_LEFT"), getTileSectionTypeFromName("DOWN_CENTER"),
                                getTileSectionTypeFromName("DOWN_RIGHT")),
                        List.of(getTileSectionTypeFromName("UP_LEFT"), getTileSectionTypeFromName("UP_CENTER"),
                                getTileSectionTypeFromName("UP_RIGHT"))),
                Direction.DOWN,
                new Pair<>(
                        List.of(getTileSectionTypeFromName("UP_LEFT"), getTileSectionTypeFromName("UP_CENTER"),
                                getTileSectionTypeFromName("UP_RIGHT")),
                        List.of(getTileSectionTypeFromName("DOWN_LEFT"), getTileSectionTypeFromName("DOWN_CENTER"),
                                getTileSectionTypeFromName("DOWN_RIGHT"))),
                Direction.LEFT,
                new Pair<>(
                        List.of(getTileSectionTypeFromName("RIGHT_UP"), getTileSectionTypeFromName("RIGHT_CENTER"),
                                getTileSectionTypeFromName("RIGHT_DOWN")),
                        List.of(getTileSectionTypeFromName("LEFT_UP"), getTileSectionTypeFromName("LEFT_CENTER"),
                                getTileSectionTypeFromName("LEFT_DOWN"))),
                Direction.RIGHT,
                new Pair<>(
                        List.of(getTileSectionTypeFromName("LEFT_UP"), getTileSectionTypeFromName("LEFT_CENTER"),
                                getTileSectionTypeFromName("LEFT_DOWN")),
                        List.of(getTileSectionTypeFromName("RIGHT_UP"), getTileSectionTypeFromName("RIGHT_CENTER"),
                                getTileSectionTypeFromName("RIGHT_DOWN")))));
        this.currentPlayer = Optional.empty();
        this.currentTile = Optional.empty();
    }

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
                    currentPlayer = Optional.of(playerInGame);
                }
                session.persist(playerInGame);
            }
            session.getTransaction().commit();

            createTiles(expansions);
            createMeeples(expansions);

            this.placeCurrentTile(new Pair<>(0, 0));
            drawNewTile();
            updateUserInterfaces();
        }
    }

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

    @Override
    public List<TileSectionType> getAllTileSectionTypes() {
        session.beginTransaction();
        CriteriaQuery<TileSectionType> query = cb.createQuery(TileSectionType.class);
        query.select(query.from(TileSectionType.class));
        List<TileSectionType> tileSectionTypes = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return tileSectionTypes;
    }

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
        final Set<MutableTile> tiles = getTileSectionsFromGameSet(gameSet).stream()
            .map(TileSection::getTile).collect(Collectors.toSet());

        for (final var tile : tiles) {
            for (final var tileSectionType : getAllTileSectionTypes()) {
                final GameSet fieldGameSet = getGameSetInSectionType(tile, tileSectionType);

                if (fieldGameSet.getType().equals(getGameSetTypeFromName("FIELD"))
                        && isSectionNearToGameset(getTileSectionFromTile(tile, tileSectionType), gameSet)) {
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
        TileSection tileSection = session.createQuery(query).getSingleResult();
        session.getTransaction().commit();
        return tileSection;
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

    private boolean isSectionNearToGameset(final TileSection tileSection, final GameSet gameSet) {
        return getGameSetInSectionType(tileSection.getTile(), tileSection.getType().getNext()).equals(gameSet)
            || getGameSetInSectionType(tileSection.getTile(), tileSection.getType().getPrevious()).equals(gameSet);
    }

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
                // se abbiamo appena cambiato tipo di tile si riparte da zero con il conteggio
                // di esse
                if (tiles.isEmpty()
                        || tileTypeConfiguration.getTileType() != tiles.get(tiles.size() - 1).getTileType()) {
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
                gameSets.get(gameSets.size() - 1).addPoints(PENNANT_POINTS);
            }
            final TileSection tileSection = new TileSection(tiles.get(tiles.size() - 1),
                    tileTypeConfiguration.getTileSectionType(), gameSets.get(gameSets.size() - 1));
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
        currentTile = Optional.of(tiles.get(0));

        this.session.beginTransaction();
        tiles.forEach(session::persist);
        gameSets.forEach(session::persist);
        tileSections.forEach(session::persist);
        this.session.getTransaction().commit();
    }

    public GameSet getGameSetInSectionType(final MutableTile tile, final TileSectionType tileSectionType) {
        this.session.beginTransaction();
        CriteriaQuery<GameSetImpl> query = cb.createQuery(GameSetImpl.class);
        Root<TileSection> root = query.from(TileSection.class);
        query.select(root.get("gameSet"));
        query.where(cb.and(cb.equal(root.get("type"), tileSectionType),
                cb.equal(root.get("tile"), tile)));
        GameSetImpl gameSet = session.createQuery(query).getSingleResult();
        this.session.getTransaction().commit();
        return gameSet;
    }

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

    @Override
    public void endTurn() {
        session.beginTransaction();
        CriteriaQuery<GameSetImpl> query = cb.createQuery(GameSetImpl.class);
        Root<TileSection> root = query.from(TileSection.class);
        query.select(root.get("gameSet"));
        query.where(cb.equal(root.get("tile"), getCurrentTile().get()));
        List<GameSetImpl> gameSets = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        gameSets.stream()
            .filter(this::isGameSetClosed)
            .forEach(this::closeGameSet);

        for (final var nearTile : getPlacedTiles()) {
            if (areTilesNear(getCurrentTile().get(), nearTile)) {
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

                centerGameset = getGameSetInSectionType(getCurrentTile().get(), getTileSectionTypeFromName("CENTER"));
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

        session.beginTransaction();
        getCurrentPlayer().get().setCurrent(false);
        CriteriaQuery<PlayerInGameImpl> queryPlayer = cb.createQuery(PlayerInGameImpl.class);
        Root<PlayerInGameImpl> rootPlayer = queryPlayer.from(PlayerInGameImpl.class);
        final int playersNum = session.createQuery(queryPlayer.select(rootPlayer)
                .where(cb.equal(rootPlayer.get("game"), this.game))).getResultList().size();
        currentPlayer = Optional.ofNullable(session.createQuery(queryPlayer.select(rootPlayer).where(cb.and(
                cb.equal(rootPlayer.get("game"), this.game)),
                cb.equal(rootPlayer.get("playerOrder"), (getCurrentPlayer().get().getPlayerOrder() + 1) % playersNum)))
                .getSingleResultOrNull());
        getCurrentPlayer().get().setCurrent(true);
        session.getTransaction().commit();
        drawNewTile();
        updateUserInterfaces();
    }

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
                .peek(f -> {
                    f.addPoints(POINTS_CLOSED_CITY_NEARBY_FIELD);
                    session.beginTransaction();
                    session.merge(f);
                    session.getTransaction().commit();
                })
                .collect(Collectors.toSet());
        fieldsToClose.forEach(this::closeGameSet);

        session.beginTransaction();
        game.setConcluded(true);
        session.merge(game);
        session.getTransaction().commit();

        allGameSets.stream()
            .filter(x -> !x.isClosed())
            .forEach(this::closeGameSet);
        updateUserInterfaces();
    }

    @Override
    public void exitGame() {
    }

    @Override
    public boolean isGameOver() {
        return this.game != null && this.game.isConcluded();
    }

    @Override
    public Optional<PlayerInGameImpl> getCurrentPlayer() {
        return currentPlayer;
    }

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

    @Override
    public Optional<TileImpl> getCurrentTile() {
        return currentTile;
    }

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

    @Override
    public void rotateCurrentTile() {
        this.rotateTileClockwise(getCurrentTile().get());
        updateUserInterfaces();
    }

    @Override
    public boolean discardCurrentTile() {
        if (this.isCurrentTilePlaceable()) {
            return false;
        }
        this.drawNewTile();
        updateUserInterfaces();
        return true;
    }

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

    @Override
    public GameSet getCurrentTileGameSetInSection(final TileSectionType section) {
        return getGameSetInSectionType(getCurrentTile().get(), section);
    }

    @Override
    public Optional<MeepleImpl> placeMeeple(final TileSectionType sectionType, final MeepleType meepleType) {
        TileImpl tile = this.getCurrentTile().get();
        Optional<MeepleImpl> choosenMeeple = this.getUnplacedPlayerMeeples(this.getCurrentPlayer().get())
            .stream().filter(m -> m.getType().equals(meepleType)).findFirst();
        TileSection choosenTileSection = this.getTileSectionFromTile(tile, sectionType);
        if (choosenMeeple.isPresent() && this.isGameSetFree(choosenTileSection.getGameSet())) {
            choosenMeeple.get().setPlaced(true);
            choosenTileSection.setMeeple(choosenMeeple.get());

            session.beginTransaction();
            session.merge(choosenMeeple.get());
            session.merge(choosenTileSection);
            session.getTransaction().commit();
            updateUserInterfaces();
            return choosenMeeple;
        }
        return Optional.empty();
    }

    @Override
    public Optional<TileSection> getTileSectionFromMeeple(MeepleImpl meeple) {
        if (meeple.isPlaced()) {
            session.beginTransaction();
            CriteriaQuery<TileSection> query = cb.createQuery(TileSection.class);
            Root<TileSection> root = query.from(TileSection.class);
            query.select(root);
            query.where(cb.equal(root.get("meeple"), meeple));
            TileSection tileSection = session.createQuery(query).getSingleResult();
            session.getTransaction().commit();
            return Optional.of(tileSection);
        }
        return Optional.empty();
    }

    @Override
    public Optional<MeepleImpl> getMeepleOnTile(TileImpl tile) {
        session.beginTransaction();
        CriteriaQuery<MeepleImpl> query = cb.createQuery(MeepleImpl.class);
        Root<TileSection> root = query.from(TileSection.class);
        query.select(root.get("meeple"));
        query.where(cb.equal(root.get("tile"), tile));
        MeepleImpl meeple = session.createQuery(query).getSingleResultOrNull();
        session.getTransaction().commit();
        return Optional.ofNullable(meeple);
    }

    @Override
    public boolean isGameSetFree(GameSet gameset) {
        // TODO lo teniamo?
        session.beginTransaction();
        CriteriaQuery<TileSection> query = cb.createQuery(TileSection.class);
        Root<TileSection> root = query.from(TileSection.class);
        query.select(root);
        query.where(cb.and(cb.equal(root.get("gameSet"), gameset),
            cb.isTrue(root.get("meeple").get("placed"))));
        List<TileSection> tileSectionList = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return tileSectionList.isEmpty();
    }

    @Override
    public void addUserInterface(final UserInterface userInterface) {
        this.userInterfaces.add(userInterface);
        userInterface.update();
    }

    private boolean areTilesNear(final MutableTile t1, final MutableTile t2) {
        return Math.abs(t1.getPosition().get().getX() - t2.getPosition().get().getX()) <= 1
                && Math.abs(t1.getPosition().get().getY() - t2.getPosition().get().getY()) <= 1
                && !t1.getPosition().get().equals(t2.getPosition().get());
    }

    private void drawNewTile() {
        session.beginTransaction();
        getCurrentTile().get().setCurrent(false);
        CriteriaQuery<TileImpl> query = cb.createQuery(TileImpl.class);
        Root<TileImpl> root = query.from(TileImpl.class);
        currentTile = Optional.ofNullable(session.createQuery(query.select(root)
                .where(cb.and(cb.equal(root.get("game"), this.game)),
                    cb.equal(root.get("tileOrder"), getCurrentTile().get().getTileOrder() + 1)))
                .getSingleResultOrNull());
        currentTile.ifPresent(t -> t.setCurrent(true));
        session.getTransaction().commit();
        if (currentTile.isEmpty()) {
            endGame();
        }
    }

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
        if (isGameOver()) {
            gameSet.setPoints(gameSet.getPoints() / gameSet.getType().getEndGameRatio());
        }
        gameSet.close();
        session.beginTransaction();
        session.merge(gameSet);
        session.getTransaction().commit();

        final Map<PlayerInGameImpl, Integer> playerMeepleStrength = new HashMap<>();
        List<MeepleImpl> meeplesInGameSet = this.getMeeplesFromGameSet(gameSet);
        if(!meeplesInGameSet.isEmpty()){
            for (var meeple : meeplesInGameSet) {
                final PlayerInGameImpl currentPlayer = (PlayerInGameImpl) meeple.getOwner();
                if (!playerMeepleStrength.containsKey(currentPlayer)) {
                    playerMeepleStrength.put(currentPlayer, 0);
                }
                playerMeepleStrength.put(currentPlayer,
                    playerMeepleStrength.get(currentPlayer) + meeple.getStrength());
            }

            final int maxMeepleStrength = playerMeepleStrength.values().stream()
                .mapToInt(x -> x).max().getAsInt();

            playerMeepleStrength.entrySet().stream()
                .filter(e -> e.getValue().equals(maxMeepleStrength))
                .forEach(e -> {
                    e.getKey().addScore(gameSet.getPoints());
                    session.beginTransaction();
                    session.merge(e.getKey());
                    session.getTransaction().commit();
                });
        }

        final List<TileSection> tileSections = meeplesInGameSet.stream()
            .map(m -> getTileSectionFromMeeple(m).get()).toList();
        tileSections.forEach(t -> t.setMeeple(null));
        meeplesInGameSet.forEach(m -> m.setPlaced(false));
        session.beginTransaction();
        meeplesInGameSet.forEach(session::merge);
        tileSections.forEach(session::merge);
        session.getTransaction().commit();
    }

    public List<MeepleImpl> getMeeplesFromGameSet(GameSet gameSet) {
        session.beginTransaction();
        CriteriaQuery<TileSection> query = this.cb.createQuery(TileSection.class);
        Root<TileSection> root = query.from(TileSection.class);
        query.where(cb.and(cb.isNotNull(root.get("meeple")),
            cb.equal(root.get("gameSet"), gameSet)));
        List<TileSection> tileSections = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return tileSections.stream().map(t -> t.getMeeple().get()).toList();
    }

    private boolean isPositionOccupied(final Pair<Integer, Integer> position) {
        for (final Tile tile : getPlacedTiles()) {
            if (tile.getPosition().get().equals(position)) {
                return true;
            }
        }
        return false;
    }

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

    private void updateUserInterfaces() {
        this.userInterfaces.forEach(UserInterface::update);
    }

    @Override
    public boolean hasGameStarted() {
        return this.game != null && !getPlacedTiles().isEmpty();
    }

    @Override
    public void joinGame(int gameId) {
        session.beginTransaction();
        this.game = session.get(Game.class, gameId);
        session.getTransaction().commit();
        updateUserInterfaces();
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
        // TODO [SPEZ] Farlo con Criteria queries, non cosi` a mano che fa schifo

        List<Server> availableServers = new ArrayList<>();
        @SuppressWarnings("deprecation")
        Query query = session.createQuery(
                "from Servers s where s.active=true and s.maxGames>(select count(s) from Servers s, Games g where g.server=s)");
        availableServers = query.getResultList();

        // session.beginTransaction();
        //
        // Root<Server> rootServer = query.from(Server.class);
        // query.select(rootServer);
        // query.where(cb.isTrue(rootServer.get("active")));
        // List<Server> availableServers = session.createQuery(query).getResultList();
        // session.getTransaction().commit();
        return availableServers;
    }

    public List<Game> getOpenGames(Player player) {
        session.beginTransaction();
        CriteriaQuery<PlayerInGameImpl> query = this.cb.createQuery(PlayerInGameImpl.class);
        Root<PlayerInGameImpl> pigRoot = query.from(PlayerInGameImpl.class);
        query.where(cb.equal(pigRoot.get("player"), player));
        List<PlayerInGameImpl> playersInGame = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return playersInGame.stream().map(p -> p.getGame()).toList();
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

    @Override
    public List<MeepleImpl> getUnplacedPlayerMeeples(PlayerInGameImpl player) {
        // TODO lo teniamo?
        session.beginTransaction();
        CriteriaQuery<MeepleImpl> query = cb.createQuery(MeepleImpl.class);
        Root<MeepleImpl> root = query.from(MeepleImpl.class);
        query.select(root);
        query.where(cb.and(
                cb.equal(root.get("owner"), player),
                cb.isFalse(root.get("placed"))));
        List<MeepleImpl> meeples = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return meeples;
    }

    @Override
    public List<Player> getAllPlayers() {
        session.beginTransaction();
        CriteriaQuery<Player> query = cb.createQuery(Player.class);
        List<Player> players = session.createQuery(query.select(query.from(Player.class))).getResultList();
        session.getTransaction().commit();
        return players;
    }

    @Override
    public Player getPlayerByID(String playerID) {
        session.beginTransaction();
        Player player = session.get(Player.class, playerID);
        session.getTransaction().commit();
        return player;
    }

    @Override
    public List<MeepleType> getMeepleTypesForPlayer(PlayerInGameImpl player) {
        session.beginTransaction();
        CriteriaQuery<MeepleType> query = cb.createQuery(MeepleType.class);
        Root<MeepleImpl> root = query.from(MeepleImpl.class);
        query.select(root.get("type"));
        query.where(cb.and(cb.isFalse(root.get("placed")),
                cb.equal(root.get("owner"), player)));
        List<MeepleType> meepleTypes = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return meepleTypes;
    }

}
