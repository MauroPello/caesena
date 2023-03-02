package it.unibo.caesena.controller;

import java.util.List;

import it.unibo.caesena.model.meeple.*;
import it.unibo.caesena.model.tile.*;
import it.unibo.caesena.model.Player;
import it.unibo.caesena.utils.*;

public interface Controller {

    void startGame();

    void addPlayer(String name, Color color);

    boolean placeMeeple(Meeple meeple, TileSection section);

    Tile getCurrentTile();

    Player getCurrentPlayer();

    void rotateCurrentTile();

    boolean placeCurrentTile();

    List<Player> getPlayers();

    List<Tile> getPlacedTiles();

    List<Tile> getNotPlacedTiles();

    List<Meeple> getCurrentPlayerMeeples();

    boolean isGameOver();

    void endTurn();

    void endGame();

    void exitGame();
}
