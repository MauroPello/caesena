package it.unibo.caesena.controller;

import java.util.List;
import java.util.Optional;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.player.PlayerInGame;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileSectionType;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.UserInterface;

/**
 * The controller is one of the components of the MVC architetture.
 * Its job is to interface the model with the view, it controlls the flow of the game.
 */
public interface Controller {

    void createNewGame();

    void joinGame(int gameId);

    /**
     * Ends the current turn and starts the next one.
     * It checks if any of the gamesets should be closed, and closes them if they
     * are.
     * It also adds points to monasteries that have tiles placed next to them, in
     * which case it
     * closes those gamesets as well.
     */
    void endTurn();

    /**
     * Exits the current name.
     */
    void exitGame();

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise
     */
    boolean isGameOver();

    /**
     * Checks if the game has started.
     *
     * @return true if the game has started, false otherwise
     */
    boolean hasGameStarted();

    /**
     * Adds a player to the game.
     *
     * @param name  name of the player to add
     * @param color color of the player to add
     */
    void addPlayer(String name, Color color);

    /**
     * Gets the player playing in the current turn.
     *
     * @return the current player
     */
    Optional<PlayerInGame> getCurrentPlayer();

    /**
     * Gets a list of all the players.
     *
     * @return the list of players
     */
    List<PlayerInGame> getPlayers();

    /**
     * Returns the current tile drawn in the current turn.
     *
     * @return the current tile
     */
    Optional<Tile> getCurrentTile();

    /**
     * Places the current tile at the passed position on the board.
     * It also joins the gamesets of the tile with the once of the neighbours.
     *
     * @param position the position at which place the current tile
     * @return true if the position was valide, false otherwise
     */
    boolean placeCurrentTile(Pair<Integer, Integer> position);

    /**
     * Rotates the current tile clockwise.
     */
    void rotateCurrentTile();

    /**
     * Discards the current tile if there is no way to place it in the current turn.
     * Draws a new one so the player che play his turn.
     *
     * @return true if was fair to discard the card, false otherwise
     */
    boolean discardCurrentTile();

    /**
     * Checks if the given position is valid for the current tile to be placed at.
     *
     * @param position the position to check
     * @return True if the position is valide, false otherwise
     */
    boolean isPositionValidForCurrentTile(Pair<Integer, Integer> position);

    /**
     * Returns a list of all the tiles that have been placed on the board.
     *
     * @return the list of placed tiles
     */
    List<Tile> getPlacedTiles();

    /**
     * Returns a list of all the tiles that haven't been placed on the board yet.
     *
     * @return the list of not placed tiles
     */
    List<Tile> getNotPlacedTiles();

    /**
     * Gets the GameSet that is in the section of the current tile.
     *
     * @param section the section of the game board to check for a gameset
     * @return the gameset in the section
     */
    GameSet getCurrentTileGameSetInSection(TileSectionType section);

    /**
     * Places a meeple of the current player on the current tile at the given section.
     *
     * @param section section on which the tile should be placed
     * @return true if placement is valid, false otherwise
     */
    Optional<Meeple> placeMeeple(TileSectionType section);

    /**
     * Gets the list of all the meeples of all the players.
     *
     * @return the list of all the meeples of all the players
     */
    List<Meeple> getMeeples();

    /**
     * Adds a user interface to the list of user interfaces.
     * This means that more that one user interface can be used at the same time.
     * In stardard MVC a user interface would be the "view"
     *
     * @param userInterface the user interface to add
     */
    void addUserInterface(UserInterface userInterface);

    public void close();
}
