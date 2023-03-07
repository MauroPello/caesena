package it.unibo.caesena.controller;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Map.Entry;

import it.unibo.caesena.model.*;
import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.meeple.*;
import it.unibo.caesena.model.tile.*;
import it.unibo.caesena.utils.*;

public class ControllerImpl implements Controller {
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
                    tiles.add(makeTileFromImagePath(imageName));
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error reading tiles from file, maybe it's missing");
        }
    }

    private Tile makeTileFromImagePath(String imageName) {
        TileFactory tileFactory = new TileFactoryWithBuilder();
        try {
            String methodName = getMethodNameFromString(imageName);
            Method method = TileFactory.class.getMethod(methodName);
            return (Tile)method.invoke(tileFactory);
        } catch (Exception e) {
            throw new IllegalStateException("Error using reflection, devs fault");
        }
    }

    private String getMethodNameFromString(String string) {
        String methodName = "create";
        String[] words = string.split("-");
        for (String word : words) {
            methodName += StringUtil.capitalize(word);
        }
        return methodName;
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
        for (var entry : getPlacedTiles()) {
            if(entry.getPosition().get().getX() == position.getX()
                &&
                entry.getPosition().get().getY() == position.getY()) {
                return false;
            }
        }

        Set<Tile> neighborns = null;
        
        /*
            devo controllare che la current tile sia posizionabile in posizione position
            la tile deve essere vicino alla position di almeno una tile già posizionata.
            devo controllare tutte tutti i lati della tile
        */
        boolean near = false;
        /*for (var entry : getPlacedTiles()) {
            /*
                UP = y-1
                DOWN = y+1
                LEFT = x-1
                RIGHT = x+1
            */
            /*if( entry.getPosition().get().getX() == position.getX() && entry.getPosition().get().getY()-1 == position.getY() ||
                entry.getPosition().get().getX() == position.getX() && entry.getPosition().get().getY()+1 == position.getY() ||
                entry.getPosition().get().getX()-1 == position.getX() && entry.getPosition().get().getY() == position.getY() ||
                entry.getPosition().get().getX()+1 == position.getX() && entry.getPosition().get().getY() == position.getY()) {
                near = true;
            }
        }*/

        this.currentTile.setPosition(position);
        /*
         * Se piazzando la Tile abbiamo chiuso un GameSet, che conteneva dei Meeples, allora distribuiamo i
         * punti ai giocatori
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
        if (this.currentTile.getGameSet(section).isEmpty()){
            return false;
        }

        var gameSet = this.currentTile.getGameSet(section).get();

        gameSet.addMeeple(meeple);

        if (gameSet.isClosed()) {
            this.distributePoints(gameSet);
        } 

        return true;
    }

    private void distributePoints (final GameSet gameset) {
        Map<Player, Integer> playerPoints = new HashMap<>();
        int value = 1;

        if (!gameset.isMeepleFree()) {
            Set<Meeple> meeples = gameset.getMeeples();
            
            for (Meeple playerMeeple : meeples) {
                Player currentPlayer = playerMeeple.getOwner();
                
                if (playerPoints.containsKey(currentPlayer)) {
                    value = playerPoints.get(currentPlayer);
                    value++;
                }
                playerPoints.put(currentPlayer, value);
                
            }
            Player maxPlayer = playerPoints.entrySet().stream().max(new Comparator<Entry<Player, Integer>>() {

                @Override
                public int compare(Entry<Player, Integer> o1, Entry<Player, Integer> o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            }).get().getKey();

            maxPlayer.addScore(gameset.getPoints());
        }

    }

}
