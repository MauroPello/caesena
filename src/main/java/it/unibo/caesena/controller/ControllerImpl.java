package it.unibo.caesena.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

public final class ControllerImpl implements Controller {
    private static final int POINTS_CLOSED_CITY_NEARBY_FIELD = 3;
    private static final int POINTS_TILE_NEARBY_MONASTERY = 1;
    private static final int POINTS_CLOSED_MONASTERY = 9;
    private static final int MEEPLES_PER_PLAYER = 8;
    private GameSetTileMediator mediator;
    private List<Meeple> meeples;
    private List<Player> players;
    private List<Tile> tiles;
    private Tile currentTile;
    private int turn;

    public ControllerImpl() {
        resetGame();
    }

    @Override
    // public void startGame() throws IllegalStateException
    public void startGame() {
        if (players.isEmpty()) {
            throw new IllegalStateException("Can't start the game without players");
        }
        Collections.shuffle(players);
        tiles = new ConfigurationLoader("config.json").getTiles(new TileFactoryWithBuilder(mediator));
        drawNewTile();
        this.placeCurrentTile(new Pair<Integer, Integer>(0, 0));
        drawNewTile();
    }

    @Override
    public void resetGame() {
        mediator = new GameSetTileMediatorImpl(new GameSetFactoryImpl());
        tiles = new ArrayList<>();
        meeples = new ArrayList<>();
        players = new ArrayList<>();
        turn = 0;
    }

    @Override
    public Player addPlayer(final String name) {
        final Player newPlayer = new PlayerImpl(name);
        players.add(newPlayer);
        for (int i = 0; i < MEEPLES_PER_PLAYER; i++) {
            meeples.add(new NormalMeeple(newPlayer));
        }
        return newPlayer;
    }

    @Override
    public Tile getCurrentTile() {
        return this.currentTile;
    }

    @Override
    public void rotateCurrentTile() {
        this.mediator.rotateTileClockwise(currentTile);
    }

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

    private boolean areTilesNear(final Tile t1, final Tile t2) {
        return Math.abs(t1.getPosition().get().getX() - t2.getPosition().get().getX()) <= 1
            && Math.abs(t1.getPosition().get().getY() - t2.getPosition().get().getY()) <= 1
            && !t1.getPosition().get().equals(t2.getPosition().get());
    }

    @Override
    public boolean placeCurrentTile(final Pair<Integer, Integer> position) {
        if (!isPositionValidForCurrentTile(position)) {
            return false;
        }

        this.currentTile.setPosition(position);

        if (getPlacedTiles().size() == 1) {
            return true;
        }

        for (final var nearTile : getPlacedTiles()) {
            if (areTilesNear(currentTile, nearTile)) {
                GameSet centerGameset = mediator.getGameSetInSection(nearTile, TileSection.CENTER);
                if ((centerGameset.getType().equals(GameSetType.MONASTERY) && !centerGameset.isMeepleFree())) {
                    centerGameset.addPoints(POINTS_TILE_NEARBY_MONASTERY);
                    if (centerGameset.getPoints() == POINTS_CLOSED_MONASTERY) {
                        centerGameset.close();
                    }
                }
                centerGameset = mediator.getGameSetInSection(currentTile, TileSection.CENTER);
                if ((centerGameset.getType().equals(GameSetType.MONASTERY) && !centerGameset.isMeepleFree())) {
                    centerGameset.addPoints(POINTS_TILE_NEARBY_MONASTERY);
                    if (centerGameset.getPoints() == POINTS_CLOSED_MONASTERY) {
                        centerGameset.close();
                    }
                }
            }
        }

        mediator.getTileNeighbours(position).forEach(n -> mediator.joinTiles(currentTile, n));

        return true;
    }

    @Override
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    @Override
    public List<Tile> getPlacedTiles() {
        return tiles.stream()
            .filter(Tile::isPlaced)
            .toList();
    }

    @Override
    public List<Tile> getNotPlacedTiles() {
        return tiles.stream()
            .filter(x -> !x.isPlaced())
            .toList();
    }

    @Override
    public List<Meeple> getNotPlacedPlayerMeeples(final Player player) {
        return meeples.stream()
            .filter(m -> m.getOwner().equals(player))
            .filter(m -> !m.isPlaced()).toList();
    }

    @Override
    public boolean isGameOver() {
        return getNotPlacedTiles().isEmpty();
    }

    @Override
    public void endTurn() {
        mediator.getGameSetsInTile(currentTile).stream()
            .filter(this::isGameSetClosed)
            .forEach(GameSet::close);

        this.turn += 1;
        drawNewTile();
    }

    private void drawNewTile() {
        if (isGameOver()) {
            endGame();
        } else {
            this.currentTile = this.getNotPlacedTiles().get(0);
        }
    }

    @Override
    public GameSet getCurrentTileGameSetInSection(final TileSection section) {
        return mediator.getGameSetInSection(currentTile, section);
    }

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
    }

    @Override
    public void exitGame() {
        this.resetGame();
    }

    @Override
    public Player getCurrentPlayer() {
        return this.players.get(this.turn % this.players.size());
    }

    @Override
    public boolean placeMeeple(final Meeple meeple, final TileSection section) {
        return getCurrentTileGameSetInSection(section).addMeeple(meeple);
    }

    private boolean isGameSetClosed(final GameSet gameSet) {
        return mediator.getTilesFromGameSet(gameSet).entrySet().stream()
            .allMatch(p -> p.getValue().stream().allMatch(s -> p.getKey().isSectionClosed(s)));
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
        for (int i = 0; i < 4; i++) {
            for (final Tile tile : tiles) {
                final int numberOfNeighbours = mediator.getTileNeighbours(tile.getPosition().get()).size();
                if (numberOfNeighbours >= 1 && numberOfNeighbours <= 3) {
                    final Set<Pair<Integer, Integer>> emptyPositions = this
                            .getEmptyNeighbouringPositions(tile.getPosition().get());
                    for (final Pair<Integer, Integer> position : emptyPositions) {
                        if (this.isPositionValidForCurrentTile(position)) {
                            return true;
                        }
                    }
                }
            }
            this.rotateCurrentTile();
        }
        return false;
    }

    @Override
    public boolean discardCurrentTile() {
        if (this.isCurrentTilePlaceable()) {
            return false;
        }
        tiles.remove(currentTile);
        this.drawNewTile();
        return true;
    }

}
