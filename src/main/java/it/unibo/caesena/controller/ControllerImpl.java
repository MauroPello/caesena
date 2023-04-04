package it.unibo.caesena.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.GameSetTileMediator;
import it.unibo.caesena.model.GameSetTileMediatorImpl;
import it.unibo.caesena.model.Player;
import it.unibo.caesena.model.PlayerImpl;
import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;
import it.unibo.caesena.model.gameset.GameSetType;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.meeple.NormalMeeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileFactoryWithBuilder;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.UserInterface;

/**
 * {@inheritDoc}
 *
 * Implementation of the Controller interface.
 */
public final class ControllerImpl implements Controller {
    private static final int POINTS_CLOSED_CITY_NEARBY_FIELD = 3;
    private static final int POINTS_TILE_NEARBY_MONASTERY = 1;
    private static final int POINTS_CLOSED_MONASTERY = 9;
    private static final int MEEPLES_PER_PLAYER = 8;
    private final List<UserInterface> userInterfaces;
    private GameSetTileMediator mediator;
    private List<Meeple> meeples;
    private List<Player> players;
    private List<Tile> tiles;
    private Tile currentTile;
    private boolean gameOver;
    private int turn;

    /**
     * Class constructor.
     */
    public ControllerImpl() {
        this.userInterfaces = new ArrayList<>();
        resetGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGame() {
        if (players.isEmpty()) {
            throw new IllegalStateException("Can't start the game without players");
        }
        Collections.shuffle(players);
        drawNewTile();
        this.placeCurrentTile(new Pair<Integer, Integer>(0, 0));
        drawNewTile();
        updateUserInterfaces();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetGame() {
        mediator = new GameSetTileMediatorImpl(new GameSetFactoryImpl());
        tiles = new ConfigurationLoader("config.json").getTiles(new TileFactoryWithBuilder(mediator));
        meeples = new ArrayList<>();
        players = new ArrayList<>();
        gameOver = false;
        turn = 0;
        updateUserInterfaces();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endTurn() {
        mediator.getGameSetsInTile(currentTile).stream()
                .filter(this::isGameSetClosed)
                .forEach(GameSet::close);

        for (final var nearTile : getPlacedTiles()) {
            if (areTilesNear(currentTile, nearTile)) {
                GameSet centerGameset = mediator.getGameSetInSection(nearTile, TileSection.CENTER);
                if (centerGameset.getType().equals(GameSetType.MONASTERY)) {
                    centerGameset.addPoints(POINTS_TILE_NEARBY_MONASTERY);
                    if (isGameSetClosed(centerGameset)) {
                        centerGameset.close();
                    }
                }

                centerGameset = mediator.getGameSetInSection(currentTile, TileSection.CENTER);
                if (centerGameset.getType().equals(GameSetType.MONASTERY)) {
                    centerGameset.addPoints(POINTS_TILE_NEARBY_MONASTERY);
                    if (isGameSetClosed(centerGameset)) {
                        centerGameset.close();
                    }
                }
            }
        }

        this.turn += 1;
        drawNewTile();
        updateUserInterfaces();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame() {
        final Set<GameSet> fieldsToClose = mediator.getAllGameSets().stream()
            .filter(c -> c.getType().equals(GameSetType.CITY))
            .filter(GameSet::isClosed)
            .flatMap(c -> mediator.getFieldGameSetsNearGameSet(c).stream())
            .peek(f -> f.addPoints(POINTS_CLOSED_CITY_NEARBY_FIELD))
            .collect(Collectors.toSet());
        fieldsToClose.forEach(GameSet::close);

        mediator.getAllGameSets().stream()
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
        this.resetGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        return this.gameOver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player addPlayer(final String name, final Color color) {
        final Player newPlayer = new PlayerImpl(name, color);
        players.add(newPlayer);
        for (int i = 0; i < MEEPLES_PER_PLAYER; i++) {
            meeples.add(new NormalMeeple(newPlayer));
        }
        return newPlayer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getCurrentPlayer() {
        return this.players.get(this.turn % this.players.size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tile getCurrentTile() {
        return this.currentTile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean placeCurrentTile(final Pair<Integer, Integer> position) {
        if (!isPositionValidForCurrentTile(position)) {
            return false;
        }

        this.currentTile.setPosition(position);

        if (getPlacedTiles().size() > 1) {
            mediator.getTileNeighbours(position).forEach(n -> mediator.joinTiles(currentTile, n));
        }

        updateUserInterfaces();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rotateCurrentTile() {
        this.mediator.rotateTileClockwise(currentTile);
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
        tiles.remove(currentTile);
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

        return mediator.isPositionValid(position, currentTile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tile> getPlacedTiles() {
        return tiles.stream()
            .filter(Tile::isPlaced)
            .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tile> getNotPlacedTiles() {
        return tiles.stream()
            .filter(x -> !x.isPlaced())
            .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameSet getCurrentTileGameSetInSection(final TileSection section) {
        return mediator.getGameSetInSection(currentTile, section);
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public boolean placeMeeple(final Meeple meeple, final TileSection section) {
        final boolean outcome = mediator.placeMeeple(meeple, currentTile, section);
        updateUserInterfaces();
        return outcome;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public List<Meeple> getPlayerMeeples(final Player player) {
        return meeples.stream()
            .filter(m -> m.getOwner().equals(player))
            .toList();
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public List<Meeple> getMeeples() {
        return this.meeples;
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
    private boolean areTilesNear(final Tile t1, final Tile t2) {
        return Math.abs(t1.getPosition().get().getX() - t2.getPosition().get().getX()) <= 1
            && Math.abs(t1.getPosition().get().getY() - t2.getPosition().get().getY()) <= 1
            && !t1.getPosition().get().equals(t2.getPosition().get());
    }

    /**
     * Draws a new tile from the list of not placed tiles.
     */
    private void drawNewTile() {
        if (getNotPlacedTiles().isEmpty()) {
            gameOver = true;
            endGame();
        } else {
            this.currentTile = this.getNotPlacedTiles().get(0);
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
        if (gameSet.getType().equals(GameSetType.FIELD)) {
            return false;
        }

        if (gameSet.getType().equals(GameSetType.MONASTERY)) {
            return gameSet.getPoints() == POINTS_CLOSED_MONASTERY;
        }

        return mediator.getTilesFromGameSet(gameSet).entrySet().stream()
                .allMatch(p -> p.getValue().stream().allMatch(s -> p.getKey().isSectionClosed(s)));
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
                final int numberOfNeighbours = mediator.getTileNeighbours(tile.getPosition().get()).size();
                if (numberOfNeighbours >= 0 && numberOfNeighbours <= 3) {
                    final Set<Pair<Integer, Integer>> emptyPositions = this
                            .getEmptyNeighbouringPositions(tile.getPosition().get());
                    for (final Pair<Integer, Integer> position : emptyPositions) {
                        if (this.isPositionValidForCurrentTile(position)) {
                            outcome = true;
                        }
                    }
                }
            }
            this.mediator.rotateTileClockwise(currentTile);
        }
        return outcome;
    }


    /**
     * Updates all of the user interfaces.
     */
    private void updateUserInterfaces() {
        this.userInterfaces.forEach(UserInterface::update);
    }

}
