package it.unibo.caesena.view.components.board;

import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.player.Player;
import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.components.common.JPanelWithBackgroundImage;
import it.unibo.caesena.view.scene.GameScene;

/**
 * {@inheritDoc}
 *
 * Implements the class using a {@link javax.swing.JPanel}.
 * It extends {@link it.unibo.caesena.view.components.common.JPanelWithBackgroundImage}.
 */
public class BoardManagerImpl implements BoardManager<JPanel> {
    private final JPanelWithBackgroundImage mainPanel;
    private final SectionSelectorComponent<JPanel> sectionSelector;
    private final BoardComponent<JPanel> board;
    private final GameScene gameScene;
    private boolean showingBoard;

    /**
     * Class constructor.
     *
     * @param gameScene the parent GameScene
     */
    public BoardManagerImpl(final GameScene gameScene) {
        this.mainPanel = new JPanelWithBackgroundImage(ResourceUtil.getBufferedImage("background_Board.png", List.of()));
        this.gameScene = gameScene;
        this.board = new BoardComponentImpl(this.gameScene);
        this.sectionSelector = new SectionSelectorComponentImpl(this.gameScene);
        this.showingBoard = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggleComponents() {
        final Player currentPlayer = gameScene.getUserInterface().getController().getCurrentPlayer().get();
        final Optional<Meeple> remainingMeeples = gameScene.getUserInterface().getController()
                .getPlayerMeeples(currentPlayer)
                .stream()
                .filter(m -> !m.isPlaced())
                .findAny();
        if (remainingMeeples.isPresent()) {
            showingBoard = !showingBoard;
            updateComponents();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SectionSelectorComponent<JPanel> getSectionSelector() {
        return this.sectionSelector;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getComponent() {
        return this.mainPanel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isShowingBoard() {
        return this.showingBoard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endTurn() {
        if (this.getSectionSelector().isSectionSelected()) {
            final var section = this.getSectionSelector().getSelectedSection().get();
            final var meeple = this.gameScene.getUserInterface().getController().placeMeeple(section);
            if (meeple.isPresent()) {
                this.board.getCurrentTileButton().setMeeple(meeple.get());
            } else {
                throw new IllegalStateException("Tried to add meeple but gameSet already had at least one");
            }
        }
        if (!showingBoard) {
            showingBoard = true;
        }
        this.updateComponents();
        this.getSectionSelector().reset();
        this.gameScene.getUserInterface().getController().endTurn();
        //this.getBoard().updateBoard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BoardComponent<JPanel> getBoard() {
        return this.board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateComponents() {
        if (this.showingBoard) {
            this.showBoard();
        } else {
            this.showSectionSelector();
        }
        this.mainPanel.validate();
        this.mainPanel.repaint();
    }

    /**
     * Shows the board and resets the section selector.
     */
    private void showBoard() {
        this.getSectionSelector().reset();
        this.mainPanel.removeAll();
        this.getBoard().draw();
        this.mainPanel.add(this.getBoard().getComponent());
    }

    /**
     * Shows the section selector.
     */
    private void showSectionSelector() {
        this.mainPanel.removeAll();
        this.getSectionSelector().draw();
        this.mainPanel.add(this.getSectionSelector().getComponent());
    }

}
