package it.unibo.caesena.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.caesena.model.*;
import it.unibo.caesena.model.meeple.*;
import it.unibo.caesena.model.tile.*;
import it.unibo.caesena.model.gameset.*;
import it.unibo.caesena.utils.*;

public class ControllerImpl implements Controller {

    private static final String FILE_TILES_PATH = "it/unibo/getresource/tile.conf";
    
    private final List<Meeple> meeples = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();
    private final List<Pair<Tile, Boolean>> tiles = new ArrayList<>();
    
    private Tile currentTile;
    private Player currentPlayer;

    @Override
    public void startGame() {
        currentPlayer = players.get(0);
        try {
            buildAllTiles();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void buildAllTiles() throws IOException {
        final InputStream in = Objects.requireNonNull(
            ClassLoader.getSystemResourceAsStream(FILE_TILES_PATH)
        );
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            line = br.readLine();
        }
    }

    @Override
    public void addPlayer(String name, Color color) {
        players.add(new PlayerImpl(name, color));
    }

    @Override
    public Tile getCurrentTile() {
        drawNewTile();
        return currentTile;
    }

    private void drawNewTile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rotateCurrentTile'");
    }

    @Override
    public void rotateCurrentTile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rotateCurrentTile'");
    }

    @Override
    public boolean placeCurrentTile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'placeCurrentTile'");
    }

    @Override
    public List<Player> getPlayers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPlayers'");
    }

    @Override
    public List<Tile> getPlacedTiles() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPlacedTiles'");
    }

    @Override
    public List<Meeple> getCurrentPlayerMeeples() {
        return meeples.stream().filter(m -> m.getOwner().equals(currentPlayer)).toList();
    }

    @Override
    public boolean isGameOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isGameOver'");
    }

    @Override
    public void endTurn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endTurn'");
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
    public void saveGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveGame'");
    }

    @Override
    public Player getCurrentPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentPlayer'");
    }

    @Override
    public boolean placeMeeple(Meeple meeple, TileSection section) {
        return meeple.isPlaced() ? false : meeple.place(section, currentTile);
    }

}
