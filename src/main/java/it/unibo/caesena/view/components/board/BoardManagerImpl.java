package it.unibo.caesena.view.components.board;

import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.caesena.model.meeple.MeepleImpl;
import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.UserInterface;
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
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "This component will always need access to the GameScene "
        + "he's placed in as it uses its methods and needs to send and retrieve information from it")
    public BoardManagerImpl(final GameScene gameScene) {
        this.mainPanel = new JPanelWithBackgroundImage(ResourceUtil.getBufferedImage("background_Board.png", List.of()),
                true);
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
        final UserInterface ui = gameScene.getUserInterface();
        final List<MeepleImpl> remainingMeeple = ui.getController().getUnplacedPlayerMeeples(ui.getController().getCurrentPlayer().get());
        if (!remainingMeeple.isEmpty()) {
            showingBoard = !showingBoard;
            update();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This component will always be included in a bigger JPanel which"
        + " has the responsibility of managing its graphical properties according to other components and the layout manager")
    public SectionSelectorComponent<JPanel> getSectionSelector() {
        return this.sectionSelector;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return this.mainPanel.isVisible();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean visible) {
        this.mainPanel.setVisible(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This component will always be included in a bigger JPanel which"
        + " has the responsibility of managing its graphical properties according to other components and the layout manager")
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
            // TODO [PELLO] piazzare i meeple scegliendone uno a caso o far decidere l'utente?
            if (gameScene.getUserInterface().getController().placeMeeple(section)) {
                // this.board.getCurrentTileButton().setMeeple(meeple.get());
            } else {
                throw new IllegalStateException("Tried to add meeple but gameSet already had at least one");
            }
        }
        if (!showingBoard) {
            showingBoard = true;
        }
        this.update();
        this.getSectionSelector().reset();
        this.gameScene.getUserInterface().getController().endTurn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This component will always be included in a bigger JPanel which"
        + " has the responsibility of managing its graphical properties according to other components and the layout manager")
    public BoardComponent<JPanel> getBoard() {
        return this.board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
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
        this.getBoard().update();
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
