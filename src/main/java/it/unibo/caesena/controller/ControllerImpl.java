package it.unibo.caesena.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.Expansion;
import it.unibo.caesena.model.Game;
import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetImpl;
import it.unibo.caesena.model.gameset.GameSetType;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.meeple.MeepleImpl;
import it.unibo.caesena.model.meeple.MeepleType;
import it.unibo.caesena.model.player.Player;
import it.unibo.caesena.model.player.PlayerInGame;
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
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

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

    private final List<UserInterface> userInterfaces;
    private final CriteriaBuilder criteriaBuilder;
    private final SessionFactory sessionFactory;
    private final Session session;

    private List<Color> playerColors;
    private List<Player> players;
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
        this.criteriaBuilder = this.session.getCriteriaBuilder();

        this.playerColors = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TileSectionType> getAllTileSectionTypes() {
        return game.getAllTileSectionTypes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileSectionType getTileSectionTypeFromName(final String name) {
        return game.getTileSectionTypeFromName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.session.close();
        this.sessionFactory.close();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void createNewGame() {
        if (!players.isEmpty()) {
            if (players.stream().map(Player::getName).collect(Collectors.toSet()).size() == players.size()
                && playerColors.stream().collect(Collectors.toSet()).size() == players.size()) {
                Collections.shuffle(players);
                drawNewTile();
                this.placeCurrentTile(new Pair<>(0, 0));
                drawNewTile();
                updateUserInterfaces();
            } else {
                this.players = new ArrayList<>();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endTurn() {
        game.getGameSetsInTile(game.getCurrentTile()).stream()
                .filter(this::isGameSetClosed)
                .forEach(GameSet::close);

        final List<TileImpl> placedTiles = game.getTiles().stream()
            .filter(MutableTile::isPlaced)
            .toList();
        for (final var nearTile : placedTiles) {
            if (areTilesNear(game.getCurrentTile(), nearTile)) {
                GameSetImpl centerGameset = game.getGameSetInSectionType(nearTile, getTileSectionTypeFromName("CENTER"));
                if (centerGameset.getType().equals(game.getGameSetTypeFromName("MONASTERY"))) {
                    centerGameset.addPoints(Game.POINTS_TILE_NEARBY_MONASTERY);
                    if (isGameSetClosed(centerGameset)) {
                        centerGameset.close();
                    }
                }

                centerGameset = game.getGameSetInSectionType(game.getCurrentTile(), getTileSectionTypeFromName("CENTER"));
                if (centerGameset.getType().equals(game.getGameSetTypeFromName("MONASTERY"))) {
                    centerGameset.addPoints(Game.POINTS_TILE_NEARBY_MONASTERY);
                    if (isGameSetClosed(centerGameset)) {
                        centerGameset.close();
                    }
                }
            }
        }

        drawNewTile();
        updateUserInterfaces();
    }

    /**
     * Ends the game.
     * Its checks if there are closed cities, in which case it assings points to
     * players with meeples in surrounding fields.
     */
    private void endGame() {
        final Set<GameSet> fieldsToClose = game.getAllGameSets().stream()
            .filter(c -> c.getType().equals(game.getGameSetTypeFromName("CITY")))
            .filter(GameSetImpl::isClosed)
            .flatMap(c -> game.getFieldGameSetsNearGameSet(c).stream())
            .peek(f -> f.addPoints(Game.POINTS_CLOSED_CITY_NEARBY_FIELD))
            .collect(Collectors.toSet());
        fieldsToClose.forEach(GameSet::close);

        game.getAllGameSets().stream()
            .filter(x -> !x.isClosed())
            .forEach(g -> {
                g.setPoints(g.getPoints() / g.getType().getEndGameRatio());
                g.close();
            });

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
    public void addPlayer(final String name, final Color color) {
        this.players.add(new Player(name));
        this.playerColors.add(color);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PlayerInGame> getCurrentPlayer() {
        return Optional.ofNullable(game.getCurrentPlayer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlayerInGame> getPlayers() {
        return Collections.unmodifiableList(game.getPlayersInGame());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Tile> getCurrentTile() {
        return Optional.ofNullable(game.getCurrentTile());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean placeCurrentTile(final Pair<Integer, Integer> position) {
        if (!isPositionValidForCurrentTile(position)) {
            return false;
        }

        this.game.getCurrentTile().setPosition(position);

        if (getPlacedTiles().size() > 1) {
            game.getTileNeighbours(position).forEach(n -> game.joinTiles(game.getCurrentTile(), n));
        }

        updateUserInterfaces();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rotateCurrentTile() {
        this.game.rotateTileClockwise(game.getCurrentTile());
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

        return game.isPositionValid(position, game.getCurrentTile());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tile> getPlacedTiles() {
        return new ArrayList<Tile>(game.getTiles().stream()
            .filter(Tile::isPlaced)
            .toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tile> getNotPlacedTiles() {
        return new ArrayList<Tile>(game.getTiles().stream()
            .filter(x -> !x.isPlaced())
            .toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameSet getCurrentTileGameSetInSection(final TileSectionType section) {
        return game.getGameSetInSectionType(game.getCurrentTile(), section);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Meeple> placeMeeple(final TileSectionType section) {
        final Optional<MeepleImpl> currentMeeple = game.getCurrentPlayer().getMeeples().stream()
            .filter(m -> !m.isPlaced())
            .findFirst();

        if (currentMeeple.isPresent() && !game.placeMeeple(currentMeeple.get(), game.getCurrentTile(), section)) {
            updateUserInterfaces();
            return Optional.empty();
        }

        updateUserInterfaces();
        return currentMeeple.isPresent() ? Optional.of(currentMeeple.get()) : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeple> getMeeples() {
        return new ArrayList<>(game.getMeeples());
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
        if (getNotPlacedTiles().isEmpty()) {
            endGame();
        } else {
            game.drawNewTile();
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
    private boolean isGameSetClosed(final GameSetImpl gameSet) {
        if (gameSet.getType().equals(game.getGameSetTypeFromName("FIELD"))) {
            return false;
        }

        if (gameSet.getType().equals(game.getGameSetTypeFromName("MONASTERY"))) {
            return gameSet.getPoints() == Game.POINTS_CLOSED_MONASTERY;
        }

        return game.getTilesFromGameSet(gameSet).stream()
                .allMatch(t -> t.getSections().stream().allMatch(TileSection::isClosed));
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
                final int numberOfNeighbours = game.getTileNeighbours(tile.getPosition().get()).size();
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
            this.game.rotateTileClockwise(game.getCurrentTile());
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'joinGame'");
    }

    @Override
    public List<Color> getDefaultColors() {
        session.beginTransaction();
        CriteriaQuery<Color> query = criteriaBuilder.createQuery(Color.class);
        List<Color> colors = session.createQuery(query.select(query.from(Color.class))).getResultList();
        session.getTransaction().commit();
        return colors;
    }

}
