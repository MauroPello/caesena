package it.unibo.caesena.view.scene;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Optional;

import javax.swing.JPanel;

import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;
import it.unibo.caesena.view.GUI;
import it.unibo.caesena.view.components.FooterComponent;
import it.unibo.caesena.view.components.FooterComponentImpl;
import it.unibo.caesena.view.components.SideBarComponent;
import it.unibo.caesena.view.components.SideBarComponentImpl;
import it.unibo.caesena.view.components.board.BoardManager;
import it.unibo.caesena.view.components.board.BoardManagerImpl;
import it.unibo.caesena.view.components.tile.TileImage;

/**
 * A class defining the scene where players can actually play the game.
 */
public class GameScene implements Scene<JPanel> {
    private static final float MAIN_COMPONENT_RATIO = 0.75f;
    private final GUI userInterface;
    private final JPanel mainPanel;
    private final BoardManager<JPanel> boardManager;
    private final FooterComponent<JPanel> footer;
    private final SideBarComponent<JPanel> sidebar;
    private Optional<TileImage> currentTileImage;

    /**
     * Public constructor that sets up the components and places them.
     *
     * @param userInterface the interface in which this scene is displayed
     */
    public GameScene(final GUI userInterface) {
        this.mainPanel = new JPanel();
        this.userInterface = userInterface;
        this.currentTileImage = Optional.empty();
        this.boardManager = new BoardManagerImpl(this);
        this.footer = new FooterComponentImpl(this);
        this.sidebar = new SideBarComponentImpl(this);
        this.mainPanel.setLayout(new GridBagLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = MAIN_COMPONENT_RATIO;
        gridBagConstraints.weighty = MAIN_COMPONENT_RATIO;
        boardManager.getComponent().setPreferredSize(new Dimension((int) Math.round(10 * MAIN_COMPONENT_RATIO),
                (int) Math.round(10 * MAIN_COMPONENT_RATIO)));
        this.mainPanel.add(boardManager.getComponent(), gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1 - MAIN_COMPONENT_RATIO;
        gridBagConstraints.weighty = MAIN_COMPONENT_RATIO;
        sidebar.getComponent().setPreferredSize(new Dimension((int) Math.round(10 * (1 - MAIN_COMPONENT_RATIO)),
                (int) Math.round(10 * MAIN_COMPONENT_RATIO)));
        this.mainPanel.add(sidebar.getComponent(), gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1 - MAIN_COMPONENT_RATIO;
        footer.getComponent().setPreferredSize(
                new Dimension(1, (int) Math.round(10 * (1 - MAIN_COMPONENT_RATIO))));
        this.mainPanel.add(footer.getComponent(), gridBagConstraints);
        this.mainPanel.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean visible) {
        if (visible) {
            this.generateCurrentTileImage();
            this.boardManager.getBoard().getComponent().setVisible(true);
            this.footer.setVisible(true);
            this.sidebar.setVisible(true);
        }

        this.mainPanel.setVisible(visible);
    }

    /**
     * Generates the image for the current tile only if it's different from the one
     * already stored.
     */
    private void generateCurrentTileImage() {
        final Tile currentTile = userInterface.getController().getCurrentTile().get();
        if (currentTileImage.isEmpty() || !currentTile.equals(currentTileImage.get().getTile())) {
            this.currentTileImage = Optional.of(new TileImage(currentTile));
        }
    }

    /**
     * Gets the image for the current tile being played.
     *
     * @return the image for the current tile being played
     */
    public TileImage getCurrentTileImage() {
        return this.currentTileImage.get();
    }

    /**
     * Toggles the components displayed inside the boardManager.
     */
    public void toggleBoard() {
        this.boardManager.toggleComponents();
    }

    /**
     * Gets whether or not the board is currently being showed.
     *
     * @return whether or not the board is currently being showed
     */
    public boolean isShowingBoard() {
        return this.boardManager.isShowingBoard();
    }

    /**
     * Confirms the placement of the current tile in the board in the previously
     * selected position.
     *
     * @return whether or not the operation was successfull
     */
    public boolean placeTile() {
        final Optional<Pair<Integer, Integer>> placedTilePosition = boardManager.getBoard()
                .getUnlockedTileButtonPosition();
        if (placedTilePosition.isPresent()
                && this.userInterface.getController().placeCurrentTile(placedTilePosition.get())) {
            boardManager.getBoard().placeTile();
            return true;
        }
        return false;
    }

    /**
     * Ends the current turn.
     */
    public void endTurn() {
        this.boardManager.endTurn();
    }

    /**
     * Zooms in on the board.
     */
    public void zoomIn() {
        this.boardManager.getBoard().zoomIn();
    }

    /**
     * Zooms out on the board.
     */
    public void zoomOut() {
        this.boardManager.getBoard().zoomOut();
    }

    /**
     * Moves the player point of view of the board.
     *
     * @param direction in which the point of view should be moved
     */
    public void move(final Direction direction) {
        this.boardManager.getBoard().move(direction);
    }

    /**
     * Checks whether or not the player can zoom in on the board.
     *
     * @return whether or not the player can zoom in on the board
     */
    public boolean canZoomIn() {
        return this.boardManager.getBoard().canZoomIn();
    }

    /**
     * Checks whether or not the player can zoom out on the board.
     *
     * @return whether or not the player can zoom out on the board
     */
    public boolean canZoomOut() {
        return this.boardManager.getBoard().canZoomOut();
    }

    /**
     * Checks whether or not the player point of view of the board can be moved by
     * as much as <code>direction</code>.
     *
     * @param direction in which the point of view could be moved
     * @return whether or not the point of view can be actually moved
     */
    public boolean canMove(final Direction direction) {
        return this.boardManager.getBoard().canMove(direction);
    }

    /**
     * Removes from the board the tile currently placed but not yet confirmed.
     */
    public void removePlacedTile() {
        this.boardManager.getBoard().removePlacedTile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final JPanel getComponent() {
        return this.mainPanel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public final GUI getUserInterface() {
        return this.userInterface;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.generateCurrentTileImage();
        this.boardManager.updateComponents();
        this.footer.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }
}
