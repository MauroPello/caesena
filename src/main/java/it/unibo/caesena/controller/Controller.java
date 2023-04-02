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

    /**
     * Strats the game.
     * It shuffles the players, draws the first tile and places it on the board at
     * position (0,0).
     */
    void startGame();

    /**
     * Resets the game to its initial state.
     * It calls updateUserInterfaces() is called so that any changes made will be
     * reflected
     * on screen for all users who have joined the game session thus far (if any).
     */
    void resetGame();

    /**
     * Adds a player to the game.
     *
     * @param name name of the player to add
     * @param color color of the player to add
     * @return the newly created player
     */
    Player addPlayer(String name, Color color);

    Player getCurrentPlayer();

    List<Player> getPlayers();

    boolean placeMeeple(Meeple meeple, TileSection section);

    /**
     * Returns the current tile drawn in the current turn.
     *
     * @return the current tile
     */
    Tile getCurrentTile();

    GameSet getCurrentTileGameSetInSection(TileSection section);


    /**
     * Rotates the current tile clockwise
     */
    void rotateCurrentTile();

    boolean isPositionValidForCurrentTile(Pair<Integer, Integer> position);

    boolean placeCurrentTile(Pair<Integer, Integer> position);

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
