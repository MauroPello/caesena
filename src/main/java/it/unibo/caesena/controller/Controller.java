package it.unibo.caesena.controller;

import java.util.List;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.Player;
import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.UserInterface;

public interface Controller {

    void startGame();

    void resetGame();

    Player addPlayer(String name, Color color);

    boolean placeMeeple(Meeple meeple, TileSection section);

    Tile getCurrentTile();

    GameSet getCurrentTileGameSetInSection(TileSection section);

    Player getCurrentPlayer();

    void rotateCurrentTile();

    boolean isPositionValidForCurrentTile(Pair<Integer, Integer> position);

    boolean placeCurrentTile(Pair<Integer, Integer> position);

    List<Player> getPlayers();

    List<Tile> getPlacedTiles();

    List<Tile> getNotPlacedTiles();

    List<Meeple> getPlayerMeeples(Player player);

    boolean isGameOver();

    void endTurn();

    void endGame();

    void exitGame();

    boolean discardCurrentTile();

    void addUserInterface(UserInterface userInterface);
}
