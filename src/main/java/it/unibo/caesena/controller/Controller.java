package it.unibo.caesena.controller;

import java.util.List;

import it.unibo.caesena.model.meeple.*;
import it.unibo.caesena.model.tile.*;
import it.unibo.caesena.model.Player;
import it.unibo.caesena.utils.*;

public interface Controller {

    void startGame();

    Player addPlayer(String name);

    boolean placeMeeple(Meeple meeple, TileSection section);

    Tile getCurrentTile();

    Player getCurrentPlayer();

    void rotateCurrentTile();

    boolean isValidPositionForCurrentTile(Pair<Integer, Integer> position);

    boolean placeCurrentTile(Pair<Integer, Integer> position);

    List<Player> getPlayers();

    List<Tile> getPlacedTiles();

    List<Tile> getNotPlacedTiles();

    List<Meeple> getCurrentPlayerMeeples();

    boolean isGameOver();

    void endTurn();

    void endGame();

    void exitGame();

    boolean discardCurrentTile();
}
