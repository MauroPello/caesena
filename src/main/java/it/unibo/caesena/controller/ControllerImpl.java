package it.unibo.caesena.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unibo.caesena.model.*;
import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;
import it.unibo.caesena.model.gameset.GameSetType;
import it.unibo.caesena.model.meeple.*;
import it.unibo.caesena.model.tile.*;
import it.unibo.caesena.utils.*;

public class ControllerImpl implements Controller {
    private static final int POINTS_CLOSED_CITY_NEARBY_FIELD = 3;
    private static final int POINTS_TILE_NEARBY_MONASTERY = 1;
    private static final int POINTS_CLOSED_MONASTERY = 9;
    private static final int MEEPLES_PER_PLAYER = 8;
    private Map<GameSet, Set<Tile>> gameSets = new HashMap<>();
    private List<Meeple> meeples = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private List<Tile> tiles = new ArrayList<>();
    private Tile currentTile;
    private Player currentPlayer;
    private int turn;

    @Override
    public void startGame() throws IllegalStateException {
        if (players.isEmpty()) {
            //TODO sti controlli son da fare per tutti i metodi
            throw new IllegalStateException("Can't start the game without players");
        }
        Collections.shuffle(players);
        currentPlayer = players.get(0);
        tiles = new ConfigurationLoader().read("config.json");
        drawNewTile();
        this.placeCurrentTile(new Pair<Integer,Integer>(0, 0));
        drawNewTile();
    }

    public void resetGame() {
        tiles = new ArrayList<>(); 
        meeples = new ArrayList<>();
        players = new ArrayList<>();
        gameSets = new HashMap<>();
    }

    @Override
    public Player addPlayer(String name) {
        Player newPlayer = new PlayerImpl(name);
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
        this.currentTile.rotateClockwise();
    }

    @Override
    public boolean isValidPositionForCurrentTile(Pair<Integer, Integer> position) {
        if (!isPositionNotOccupied(position)) {
            return false;
        }

        Set<Tile> neighbours = getTileNeighbours(position);

        if (neighbours.isEmpty() && !getPlacedTiles().isEmpty()) {
            return false;
        }

        Map<Direction, Set<TileSection>> toCheck = new HashMap<>();
        toCheck.put(Direction.UP, Set.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT));
        toCheck.put(Direction.DOWN, Set.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT));
        toCheck.put(Direction.LEFT, Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN));
        toCheck.put(Direction.RIGHT, Set.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN));

        for (Tile neighbour : neighbours) {
            for (var entry : toCheck.entrySet()) {
                if (Direction.match(entry.getKey(), position, neighbour.getPosition().get())) {
                    for (var section : entry.getValue()) {
                        if (!neighbour.getGameSet(section).getType().equals(currentTile.getGameSet(TileSection.getOpposite(section)).getType())) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean placeCurrentTile(Pair<Integer, Integer> position) {

        if (!isValidPositionForCurrentTile(position)) {
            return false;
        }

        for (var tile : getPlacedTiles()) {
            if ((tile.getPosition().get().getX() >= position.getX()-1 && tile.getPosition().get().getY() >= position.getY()-1) &&
                (tile.getPosition().get().getX() <= position.getX()+1 && tile.getPosition().get().getY() <= position.getY()+1)) {
                GameSet CenterGameset = tile.getGameSet(TileSection.CENTER);
                if (CenterGameset.getType().equals(GameSetType.MONASTERY) && !CenterGameset.isMeepleFree()) {
                    CenterGameset.addPoints(POINTS_TILE_NEARBY_MONASTERY);
                    if (CenterGameset.getPoints() == POINTS_CLOSED_MONASTERY) {
                        CenterGameset.close();
                    }
                }
            }
        }

        this.currentTile.setPosition(position);
        for (var section : TileSection.values()) {
            if (!gameSets.containsKey(currentTile.getGameSet(section))) {
                gameSets.put(currentTile.getGameSet(section), Set.of(currentTile));
            } else {
                var gameSetTiles = new HashSet<>(gameSets.get(currentTile.getGameSet(section)));
                gameSetTiles.add(currentTile);
                gameSets.put(currentTile.getGameSet(section), gameSetTiles);
            }
        }

        if (this.currentTile.getGameSet(TileSection.CENTER).getType().equals(GameSetType.MONASTERY)) {
            int nearMonasteryTilesNum = 0;
            for (var nearTile : getPlacedTiles()) {
                if ((nearTile.getPosition().get().getX() >= position.getX()-1 && nearTile.getPosition().get().getY() >= position.getY()-1) &&
                    (nearTile.getPosition().get().getX() <= position.getX()+1 && nearTile.getPosition().get().getY() <= position.getY()+1) &&
                    !nearTile.getPosition().get().equals(position)) {
                    nearMonasteryTilesNum++;
                }
            }
            this.currentTile.getGameSet(TileSection.CENTER).addPoints(nearMonasteryTilesNum * POINTS_TILE_NEARBY_MONASTERY);
        }

        Set<Tile> neighbours = getTileNeighbours(position);
        Map<Direction, Set<TileSection>> toCheck = new HashMap<>();
        toCheck.put(Direction.UP, Set.of(TileSection.DOWN_LEFT, TileSection.DOWN_CENTER, TileSection.DOWN_RIGHT));
        toCheck.put(Direction.DOWN, Set.of(TileSection.UP_LEFT, TileSection.UP_CENTER, TileSection.UP_RIGHT));
        toCheck.put(Direction.LEFT, Set.of(TileSection.RIGHT_UP, TileSection.RIGHT_CENTER, TileSection.RIGHT_DOWN));
        toCheck.put(Direction.RIGHT, Set.of(TileSection.LEFT_UP, TileSection.LEFT_CENTER, TileSection.LEFT_DOWN));

        for (Tile neighbour : neighbours) {
            for (var entry : toCheck.entrySet()) {
                if (Direction.match(entry.getKey(), position, neighbour.getPosition().get())) {
                    for (var section : entry.getValue()) {
                        if (neighbour.getGameSet(section).getType().equals(currentTile.getGameSet(TileSection.getOpposite(section)).getType())) {
                            // se matchano le chiudo
                            neighbour.closeSection(section);
                            currentTile.closeSection(TileSection.getOpposite(section));

                            if (!neighbour.getGameSet(section).equals(currentTile.getGameSet(TileSection.getOpposite(section)))) {
                                // se però non sono nello stesso gameSet unisco i due diversi gameSet
                                GameSet neighbourGameSet = neighbour.getGameSet(section);
                                GameSet currentTileGameSet = currentTile.getGameSet(TileSection.getOpposite(section));
                                GameSet joinedGameSet = new GameSetFactoryImpl().createJoinedSet(neighbourGameSet, currentTileGameSet);
                                
                                for (Tile tile : gameSets.get(neighbourGameSet)) {
                                    for (var tileSection : TileSection.values()) {
                                        if(tile.getGameSet(tileSection).equals(neighbourGameSet)) {
                                            tile.putSection(tileSection, joinedGameSet);
                                        }
                                    }
                                }
                                neighbour.putSection(section, joinedGameSet);
                                
                                for (Tile tile : gameSets.get(currentTileGameSet)) {
                                    for (var tileSection : TileSection.values()) {
                                        if(tile.getGameSet(tileSection).equals(currentTileGameSet)) {
                                            tile.putSection(tileSection, joinedGameSet);
                                        }
                                    }
                                }
                                currentTile.putSection(TileSection.getOpposite(section), joinedGameSet);
                                
                                Set<Tile> tiles = new HashSet<>();
                                tiles.addAll(gameSets.remove(neighbourGameSet));
                                tiles.addAll(gameSets.remove(currentTileGameSet));
                                gameSets.put(joinedGameSet, tiles);
                            }
                        }
                    }
                }
            }
        }
                     
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

    private Set<Tile> getTileNeighbours(Pair<Integer, Integer> position) {
        var neighboursDirections = Stream.of(Direction.values())
            .map(d -> new Pair<Integer, Integer>(position.getX() + d.getX(), position.getY() + d.getY()))
            .collect(Collectors.toSet());

        return getPlacedTiles().stream()
            .filter(t -> neighboursDirections.contains(t.getPosition().get()))
            .collect(Collectors.toSet());
    }

    @Override
    public List<Meeple> getPlayerMeeples(final Player player) {
        return meeples.stream().filter(m -> m.getOwner().equals(player)).toList();
    }

    @Override
    public boolean isGameOver() {
        return getNotPlacedTiles().isEmpty();
    }

    @Override
    public void endTurn() {
        Set<GameSet> gameSetsToCheck = new HashSet<>();
        for (var section : TileSection.values()) {
            gameSetsToCheck.add(currentTile.getGameSet(section));
        }

        gameSetsToCheck.stream()
            .filter(this::isGameSetClosed)
            .forEach(GameSet::close);


        this.turn += 1;
        this.currentPlayer = this.players.get(this.turn % this.players.size());
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
    public void endGame() {
        Set<GameSet> fieldsWithPoints = new HashSet<>();

        for (var cityGameSet : gameSets.keySet()) {
            if (cityGameSet.getType().equals(GameSetType.CITY) && cityGameSet.isClosed()) {
                Set<GameSet> fieldsNearCity = new HashSet<>();

                for (var tile : gameSets.get(cityGameSet)) {
                    for (var tileSection : TileSection.values()) {
                        GameSet fieldGameSet = tile.getGameSet(tileSection);

                        if (fieldGameSet.getType().equals(GameSetType.FIELD) &&
                            tile.isSectionNearToGameset(tileSection, cityGameSet)) {
                            fieldsNearCity.add(fieldGameSet);
                        }
                    }
                }

                fieldsNearCity.forEach(x -> x.addPoints(POINTS_CLOSED_CITY_NEARBY_FIELD));
                fieldsWithPoints.addAll(fieldsNearCity);
            }
        }
        fieldsWithPoints.forEach(GameSet::close);

        this.gameSets.keySet().stream()
            .filter(x->!x.isClosed())
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
        return currentPlayer;
    }

    @Override
    public boolean placeMeeple(final Meeple meeple, final TileSection section) {
        return this.currentTile.getGameSet(section).addMeeple(meeple);
    }

    private boolean isGameSetClosed(final GameSet gameSet) {
        for (Tile tile : gameSets.get(gameSet)) {
            for (TileSection tileSection : TileSection.values()) {
                if (tile.getGameSet(tileSection).equals(gameSet) && !tile.isSectionClosed(tileSection)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isPositionNotOccupied(final Pair<Integer, Integer> position) {
        for (Tile tile : getPlacedTiles()) {
            if (tile.getPosition().get().equals(position)) {
                return false;
            }
        }
        return true;
    }

    private Set<Pair<Integer, Integer>> getEmptyNeighbouringPositions(Pair<Integer, Integer> position) {
        Set<Pair<Integer, Integer>> neighbouringPositions = new HashSet<>();
        for (var direction : Direction.values()) {
            Pair<Integer, Integer> neighbourPosition = new Pair<>(position.getX()+direction.getX(), position.getY()+direction.getY());
            if (this.isPositionNotOccupied(neighbourPosition)) {
                neighbouringPositions.add(neighbourPosition);
            }
        }
        return neighbouringPositions;
    }

    private boolean isCurrentTilePlaceable() {
        for (int i = 0; i < 4; i++) {
            for (Tile tile : tiles) {
                int numberOfNeighbours = this.getTileNeighbours(tile.getPosition().get()).size();
                if (numberOfNeighbours >= 1 && numberOfNeighbours <= 3) {
                    Set<Pair<Integer, Integer>> emptyPositions = this.getEmptyNeighbouringPositions(tile.getPosition().get());
                    for (Pair<Integer, Integer> position : emptyPositions) {
                        if (this.isValidPositionForCurrentTile(position)) {
                            return true;
                        }
                    }
                }
            }
            this.rotateCurrentTile();
        }
        return false;
    }

    public boolean discardCurrentTile() {
        if (this.isCurrentTilePlaceable()) {
            return false;
        }
        tiles.remove(currentTile);
        this.drawNewTile();
        return true;
    }

}
