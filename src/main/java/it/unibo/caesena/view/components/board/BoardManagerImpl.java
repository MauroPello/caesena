package it.unibo.caesena.view.components.board;

import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import it.unibo.caesena.model.Player;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.components.common.PanelWithBackgroundImage;
import it.unibo.caesena.view.scene.GameScene;

/**
 * {@inheritDoc}
 *
 * Implements the class using a {@link javax.swing.JPanel}.
 * It extends {@link it.unibo.caesena.view.components.common.PanelWithBackgroundImage}.
 */
public class BoardManagerImpl extends PanelWithBackgroundImage implements BoardManager<JPanel> {
    private static final long serialVersionUID = 1073591515646435610L;
    private final GameScene gameScene;
    private final BoardComponent<JPanel> board;
    private final SectionSelectorComponent<JPanel> sectionSelector;
    private boolean showingBoard;

    /**
     * Class constructor.
     *
     * @param gameScene the parent GameScene
     */
    public BoardManagerImpl(final GameScene gameScene) {
        super(ResourceUtil.getBufferedImage("background_Board.png", List.of()));
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
        return this;
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
        final var currentPlayer = this.gameScene.getUserInterface().getController().getCurrentPlayer();
        final List<Meeple> meeples = this.gameScene.getUserInterface().getController().getPlayerMeeples(currentPlayer.get())
                .stream()
                .filter(m -> !m.isPlaced())
                .toList();
        if (this.getSectionSelector().isSectionSelected()) {
            if (!meeples.isEmpty()) {
                final var section = this.getSectionSelector().getSelectedSection().get();
                if (this.gameScene.getUserInterface().getController().placeMeeple(section)) {
                    this.board.getCurrentTileButton().setMeeple(meeples.get(0));
                } else {
                    throw new IllegalStateException("Tried to add meeple but gameSet already had at least one");
                }
            } else {
                throw new IllegalStateException("Tried to add meeple but run out of them");
            }
        }
        if (!showingBoard) {
            showingBoard = true;
        }
        this.updateComponents();
        this.getSectionSelector().reset();
        this.gameScene.getUserInterface().getController().endTurn();
        this.getBoard().updateMeeplePresence();
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
        this.validate();
        this.repaint();
    }

    /**
     * Shows the board and resets the section selector.
     */
    private void showBoard() {
        this.getSectionSelector().reset();
        this.removeAll();
        this.getBoard().draw();
        this.add(this.getBoard().getComponent());
    }

    /**
     * Shows the section selector.
     */
    private void showSectionSelector() {
        this.removeAll();
        this.getSectionSelector().draw();
        this.add(this.getSectionSelector().getComponent());
    }

}
