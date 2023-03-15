package it.unibo.caesena.controller;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private static final int POINTS_CLOSED_CITY = 2;
    private static final String SEP = File.separator;
    private static final String FILE_TILES_PATH = "it" + SEP + "unibo" + SEP + "caesena" + SEP + "tile.conf";
    private static final int MEEPLES_PER_PLAYER = 8;
    private final List<Meeple> meeples = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();
    private final List<Tile> tiles = new ArrayList<>();
    private Tile currentTile;
    private Player currentPlayer;
    private int turn; //indice di scorrimento Liste
    private final Map<GameSet, Set<Tile>> gameSets =  new HashMap<>();

    @Override
    public void startGame() throws IllegalStateException {
        if (players.isEmpty()) {
            //TODO sti controlli son da fare per tutti i metodi
            throw new IllegalStateException("Can't start the game without players");
        }
        Collections.shuffle(players);
        currentPlayer = players.get(0);
        buildAllTiles();
        Collections.shuffle(tiles);
        drawNewTile();
        this.placeCurrentTile(new Pair<Integer,Integer>(0, 0));
        drawNewTile();
    }

    private void buildAllTiles() {
        List<String> lines;
        try {
            URI uri = ClassLoader.getSystemResource(FILE_TILES_PATH).toURI();
            lines = Files.readAllLines(Paths.get(uri));
            for (var line : lines) {
                String imageName = line.split(";")[0];
                int numberOfTiles = Integer.parseInt(line.split(";")[1]);
                for (int i = 0; i < numberOfTiles; i++) {
                    tiles.add(TileType.valueOf(imageName).createTile(new TileFactoryWithBuilder()));
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error reading tiles from file, maybe it's missing");
        }
    }

    @Override
    public void addPlayer(String name, Color color) {
        Player newPlayer = new PlayerImpl(name, color);
        players.add(newPlayer);
        for (int i = 0; i < MEEPLES_PER_PLAYER; i++) {
            meeples.add(new NormalMeeple(newPlayer));
        }
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
        for (var entry : getPlacedTiles()) {
            if (entry.getPosition().get().equals(position)) {
                return false;
            }
        }

        Set<Tile> neighbours = getTileNeighbours(position);

        if (neighbours.isEmpty() && !getPlacedTiles().isEmpty()) {
            return false;
        }

        Map<Direction, Set<TileSection>> toCheck = new HashMap<>();
        toCheck.put(Direction.UP, Set.of(TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight));
        toCheck.put(Direction.DOWN, Set.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight));
        toCheck.put(Direction.LEFT, Set.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown));
        toCheck.put(Direction.RIGHT, Set.of(TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown));

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

        Set<Tile> neighbours = getTileNeighbours(position);
        Map<Direction, Set<TileSection>> toCheck = new HashMap<>();
        toCheck.put(Direction.UP, Set.of(TileSection.DownLeft, TileSection.DownCenter, TileSection.DownRight));
        toCheck.put(Direction.DOWN, Set.of(TileSection.UpLeft, TileSection.UpCenter, TileSection.UpRight));
        toCheck.put(Direction.LEFT, Set.of(TileSection.RightUp, TileSection.RightCenter, TileSection.RightDown));
        toCheck.put(Direction.RIGHT, Set.of(TileSection.LeftUp, TileSection.LeftCenter, TileSection.LeftDown));

        for (Tile neighbour : neighbours) {
            for (var entry : toCheck.entrySet()) {
                if (Direction.match(entry.getKey(), position, neighbour.getPosition().get())) {
                    for (var section : entry.getValue()) {
                        if (neighbour.getGameSet(section).getType().equals(currentTile.getGameSet(TileSection.getOpposite(section)).getType()) &&
                        !neighbour.getGameSet(section).equals(currentTile.getGameSet(TileSection.getOpposite(section)))) {

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
                            neighbour.closeSection(section);
                            neighbour.putSection(section, joinedGameSet);

                            for (Tile tile : gameSets.get(currentTileGameSet)) {
                                for (var tileSection : TileSection.values()) {
                                    if(tile.getGameSet(tileSection).equals(currentTileGameSet)) {
                                        tile.putSection(tileSection, joinedGameSet);
                                    }
                                }
                            }
                            currentTile.closeSection(TileSection.getOpposite(section));
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
    public List<Meeple> getCurrentPlayerMeeples() {
        return meeples.stream().filter(m -> m.getOwner().equals(currentPlayer)).toList();
    }

    @Override
    public boolean isGameOver() {
        return getPlacedTiles().size() == tiles.size();
    }

    @Override
    public void endTurn() {
        Set<GameSet> gameSetsToCheck = new HashSet<>();
        for (var section : TileSection.values()) {
            gameSetsToCheck.add(currentTile.getGameSet(section));
        }
        gameSetsToCheck.stream().filter(this::isGameSetClosed).peek(this::distributePoints);


        this.turn = (this.turn + 1) % this.players.size();
        this.currentPlayer = this.players.get(this.turn);
        drawNewTile();
    }

    private void drawNewTile() {
        this.currentTile = this.getNotPlacedTiles().get(0);
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

                fieldsNearCity.forEach(x -> x.addPoints(POINTS_CLOSED_CITY));
                fieldsWithPoints.addAll(fieldsNearCity);
            }
        }

        fieldsWithPoints.forEach(f -> distributePoints(f));
    }

    @Override
    public void exitGame() {
        this.endGame();
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public boolean placeMeeple(final Meeple meeple, final TileSection section) {
        var gameSet = this.currentTile.getGameSet(section);

        if (!gameSet.isMeepleFree() || meeple.isPlaced()) {
            return false;
        }

        gameSet.addMeeple(meeple);

        return true;
    }

    private boolean isGameSetClosed(final GameSet gameSet) {
        boolean flag = true;
        for (Tile tile : tiles) {
            for (TileSection tileSection : TileSection.values()) {
                if (tile.getGameSet(tileSection).equals(gameSet) && !tile.isSectionClosed(tileSection)) {
                    flag = false;
                }
            }
        }

        return flag;
    }

    private void distributePoints (final GameSet gameset) {
        Map<Player, Integer> playerMeeples = new HashMap<>();
        int value = 1;

        if (!gameset.isMeepleFree()) {

            var checkOptional = gameset.close();
            if (checkOptional.isEmpty()) {
                return ;
            }

            Set<Meeple> meeples = checkOptional.get().getX();

            for (Meeple playerMeeple : meeples) {
                Player currentPlayer = playerMeeple.getOwner();

                if (playerMeeples.containsKey(currentPlayer)) {
                    value = playerMeeples.get(currentPlayer);
                    value++;
                }
                playerMeeples.put(currentPlayer, value);

            }

            int maxValueMeeple = playerMeeples.values().stream().mapToInt(x -> x)
                .max().getAsInt();

            playerMeeples.entrySet().stream().filter(x -> x.getValue().equals(maxValueMeeple))
                .peek(x -> x.getKey().addScore(checkOptional.get().getY()));
        }
    }

}
