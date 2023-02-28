package it.unibo.caesena.controller;

import java.util.List;

import it.unibo.caesena.model.*;
import it.unibo.caesena.utils.*;

public interface Controller {

    int startGame();

    void addPlayer(String name, Color color);

    boolean placeMeeple(Meeple meeple);

    Tile getCurrentTile();

    void rotateCurrentTile();

    boolean placeCurrentTile();

    List<Player> getPlayers();

    List<Tile> getPlacedTiles();

    List<Meeple> getMeeples();

    boolean isGameOver();

    void endTurn();

    void endGame();

    void exitGame();

    void saveGame();
}
