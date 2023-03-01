package it.unibo.caesena.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import it.unibo.caesena.model.*;
import it.unibo.caesena.model.meeple.*;
import it.unibo.caesena.model.tile.*;
import it.unibo.caesena.model.gameset.*;
import it.unibo.caesena.utils.*;

public class ControllerImpl implements Controller {
    private static final String FILE_TILES_PATH = "it/unibo/caesena/tile.conf";
    private static final int MEEPLES_PER_PLAYER = 8; //TODO guardare le regole del gioco
    private final List<Meeple> meeples = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();
    private final List<Tile> tiles = new ArrayList<>();
    private Tile currentTile;
    private Player currentPlayer;
    private int turn;

    @Override
    public void startGame() {
        Collections.shuffle(players);
        currentPlayer = players.get(0);
        try {
            buildAllTiles();
        } catch (Exception e) {}
    }

    private void buildAllTiles() throws IOException {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource(FILE_TILES_PATH).toURI()));
            for (String line : lines) {
                String imageName = line.split(";")[0];
                int cardinality = Integer.parseInt(line.split(";")[1]);
                for (int i = 0; i < cardinality; i++) {
                    tiles.add(makeTileFromImagePath(imageName));
                }
            }
        } catch (Exception e) { }
    }

    private Tile makeTileFromImagePath(String imageName) {
        TileFactory tileFactory = new TileFactoryWithBuilder();
        try {
            Method method = TileFactory.class.getMethod(getMethodNameFromString(imageName));
            return (Tile)method.invoke(tileFactory);
        } catch (Exception e) {}
        return null;
    }

    private String getMethodNameFromString(String string) {
        String methodName = "create";
        char[] charArray = string.toCharArray();
        boolean nextIsUpperCase = true;
        for (char c : charArray) {
            String currentCharAString = String.valueOf(c);
            if (nextIsUpperCase) {
                currentCharAString = currentCharAString.toUpperCase();
                nextIsUpperCase = false;
            }
            if (c == '-') {
                nextIsUpperCase = true;
            }
            else {
                methodName += currentCharAString;
            }
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
        return currentTile;
    }

    @Override
    public void rotateCurrentTile() {
        currentTile.rotateClockwise();
    }

    @Override
    public boolean placeCurrentTile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'placeCurrentTile'");
    }

    @Override
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    @Override
    public List<Tile> getPlacedTiles() {
        return tiles.stream()
            .filter(x -> x.isPlaced())
            .toList();
    }

    @Override
    public List<Meeple> getMeeples() {
        return Collections.unmodifiableList(meeples);
    }

    @Override
    public boolean isGameOver() {
        return getPlacedTiles().size() == tiles.size();
    }

    @Override
    public void endTurn() {

    }

    @Override
    public void endGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endGame'");
    }

    @Override
    public void exitGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exitGame'");
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public boolean placeMeeple(Meeple meeple) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'placeMeeple'");
    }

}
