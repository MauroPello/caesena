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
        currentTile = tiles.get(0);
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
    public boolean placeCurrentTile(Pair<Integer, Integer> position) {
        //controllo che in position ci sia già stata piazzata un'altra tile
        for (var entry : getPlacedTiles()) {
            if (entry.getPosition().get().getX() == position.getX()
                &&
                entry.getPosition().get().getY() == position.getY()) {
                return false;
            }
        }
        //in caso negativo,

        Set<Tile> neighbours = getTileNeighbours(position);
        //neighbours sono le MAX 4 pedine adiacenti
        if (neighbours.isEmpty()) {
            return false;
        }

        //ogni Elemento della mappa è un set di valori da controllare
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

        this.currentTile.setPosition(position);

        for (Tile neighbour : neighbours) {
            for (var entry : toCheck.entrySet()) {
                if (Direction.match(entry.getKey(), position, neighbour.getPosition().get())) {
                    for (var section : entry.getValue()) {
                        if (neighbour.getGameSet(section).getType().equals(currentTile.getGameSet(TileSection.getOpposite(section)).getType()) &&
                            !neighbour.getGameSet(section).equals(currentTile.getGameSet(TileSection.getOpposite(section)))) {
                            neighbour.closeSection(section);
                            currentTile.closeSection(TileSection.getOpposite(section));

                            GameSet gameSet = new GameSetFactoryImpl().createJoinedSet(neighbour.getGameSet(section), currentTile.getGameSet(TileSection.getOpposite(section)));
                            neighbour.putSection(section, gameSet);
                            currentTile.putSection(TileSection.getOpposite(section), gameSet);

                            Set<Tile> tiles = new HashSet<>();
                            tiles.addAll(gameSets.remove(neighbour.getGameSet(section)));
                            tiles.add(currentTile);
                            gameSets.put(gameSet, tiles);

                            boolean isGameSetClosed = true;
                            for (Tile tile : tiles) {
                                for (TileSection tileSection : TileSection.values()) {
                                    if (tile.getGameSet(tileSection).equals(gameSet) && !tile.isSectionClosed(tileSection)) {
                                        isGameSetClosed = false;
                                    }
                                }
                            }

                            if (isGameSetClosed) {
                                // calcoliamo punti
                                // gameSet.close()
                                // distributePoints()
                                //08/03/23 15:00-17:30
                            }
                        }
                    }
                }
            }
        }

        
            //Direction.values()
            /* 
            if (Direction.isUP(neighbour.getPosition().get(), position)) {
                //se il vicino è sopra alla tile che vogliamo piazzare:
                //controllo tutti e tre i TileSection
                if (neighbour.getGameSet(TileSection.DownCenter).getType().equals(getCurrentTile().getGameSet(TileSection.getOpposite(TileSection.DownCenter)).getType())
                    && neighbour.getGameSet(TileSection.DownLeft).getType().equals(getCurrentTile().getGameSet(TileSection.getOpposite(TileSection.DownLeft)).getType())
                    && neighbour.getGameSet(TileSection.DownRight).getType().equals(getCurrentTile().getGameSet(TileSection.getOpposite(TileSection.DownRight)).getType())) {
                    return false;
                }
            }
           
            // if (neighbour.getPosition().get().getX()+Direction.UP.getX() == position.getX() &&
            //     neighbour.getPosition().get().getY()+Direction.UP.getY() == position.getY()) {
            //     //se il vicino è sopra alla tile che vogliamo piazzare:
            //     //controllo tutti e tre i TileSection
            //     if (neighbour.getGameSet(TileSection.DownCenter).getType().equals(getCurrentTile().getGameSet(TileSection.getOpposite(TileSection.DownCenter)).getType())
            //         && neighbour.getGameSet(TileSection.DownLeft).getType().equals(getCurrentTile().getGameSet(TileSection.getOpposite(TileSection.DownLeft)).getType())
            //         && neighbour.getGameSet(TileSection.DownRight).getType().equals(getCurrentTile().getGameSet(TileSection.getOpposite(TileSection.DownRight)).getType())) {
            //         return false;}
            // }

            if (neighbour.getPosition().get().getX()+Direction.DOWN.getX() == position.getX() && neighbour.getPosition().get().getY()+Direction.DOWN.getY() == position.getY()) {
                if (!(neighbour.getGameSet(TileSection.UpCenter).getType().equals(getCurrentTile().getGameSet(TileSection.DownCenter).getType())
                    && neighbour.getGameSet(TileSection.UpLeft).getType().equals(getCurrentTile().getGameSet(TileSection.DownLeft).getType())
                    && neighbour.getGameSet(TileSection.UpRight).getType().equals(getCurrentTile().getGameSet(TileSection.DownRight).getType()))) {
                    return false;
                }
            }

            if (neighbour.getPosition().get().getX()+Direction.LEFT.getX() == position.getX() && neighbour.getPosition().get().getY()+Direction.LEFT.getY() == position.getY()) {
                if (!(neighbour.getGameSet(TileSection.RightUp).getType().equals(getCurrentTile().getGameSet(TileSection.LeftUp).getType())
                    && neighbour.getGameSet(TileSection.RightCenter).getType().equals(getCurrentTile().getGameSet(TileSection.LeftCenter).getType())
                    && neighbour.getGameSet(TileSection.RightDown).getType().equals(getCurrentTile().getGameSet(TileSection.LeftDown).getType()))) {
                    return false;
                }
            }

            if (neighbour.getPosition().get().getX()+Direction.RIGHT.getX() == position.getX() && neighbour.getPosition().get().getY()+Direction.RIGHT.getY() == position.getY()) {
                if (!(neighbour.getGameSet(TileSection.LeftUp).getType().equals(getCurrentTile().getGameSet(TileSection.RightUp).getType())
                    && neighbour.getGameSet(TileSection.LeftCenter).getType().equals(getCurrentTile().getGameSet(TileSection.RightCenter).getType())
                    && neighbour.getGameSet(TileSection.LeftDown).getType().equals(getCurrentTile().getGameSet(TileSection.RightDown).getType()))) {
                    return false; 
                }
            }

            /*
             * contorllare se tile fitti
             * settare a closed tutte le section che compabiano
             * unire i gameset -> tramite factory
             * aggiungere i gameset alla mappa gamesets
             * aggiungere la tile ai vari set di tiles per ogni gameset/unito
             * controllare se i gameset sono chisi -> distibuzione punti
             */

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
        this.turn++;
        if (this.players.size() == this.turn) {
            this.turn = 0;
        }
        this.currentPlayer = this.players.get(this.turn);

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

        if (gameSet.isClosed()) {
            this.distributePoints(gameSet);
        }

        return true;
    }

    private void distributePoints (final GameSet gameset) {
        Map<Player, Integer> playerMeeples = new HashMap<>();
        int value = 1;

        if (!gameset.isMeepleFree()) {
            Set<Meeple> meeples = gameset.getMeeples();

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
                .peek(x -> x.getKey().addScore(gameset.getPoints()));

            meeples.forEach(Meeple::removeFromTile);
        }
    }

}
