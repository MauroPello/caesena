package it.unibo.caesena.view.components;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import it.unibo.caesena.model.Player;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.utils.ResourceUtil;
import it.unibo.caesena.view.GameView;

public class MainComponentImpl extends JPanel implements MainComponent<JPanel> {
    private static final long serialVersionUID = 1073591515646435610L;
    private final GameView gameView;
    private final BoardComponent<JPanel> board;
    private final SectionSelectorComponent<JPanel> sectionSelector;
    private boolean showingBoard;
    private final BufferedImage backgroundImage;

    public MainComponentImpl(final GameView gameView) {
        this.gameView = gameView;
        this.board = new BoardComponentImpl(this.gameView);
        this.sectionSelector = new SectionSelectorComponentImpl(this.gameView);
        this.showingBoard = true;
        this.add(this.getBoard().getComponent());
        this.backgroundImage = ResourceUtil.getBufferedImage("background_Board.png", List.of());
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        double width = this.getWidth();
        double height = this.getHeight();
        if (this.getWidth() > backgroundImage.getWidth()) {
            height = (backgroundImage.getHeight() * this.getWidth()) / backgroundImage.getWidth();
        }
        if (this.getHeight() > backgroundImage.getHeight()) {
            width = (backgroundImage.getHeight() * this.getHeight()) / backgroundImage.getWidth();
        }
        g.drawImage(backgroundImage.getScaledInstance((int) Math.round(width), (int) Math.round(height), Image.SCALE_SMOOTH), 
            0, 0, (int) Math.round(width), (int) Math.round(height), null);
    } 

    @Override
    public void toggleComponents() {
        final Player currentPlayer = gameView.getUserInterface().getController().getCurrentPlayer();
        final Optional<Meeple> remainingMeeples = gameView.getUserInterface().getController()
                .getPlayerMeeples(currentPlayer)
                .stream()
                .filter(m -> !m.isPlaced())
                .findAny();
        if (remainingMeeples.isPresent()) {
            showingBoard = !showingBoard;
            updateComponents();
        }
    }

    @Override
    public SectionSelectorComponent<JPanel> getSectionSelector() {
        return this.sectionSelector;
    }

    @Override
    public JPanel getComponent() {
        return this;
    }

    @Override
    public boolean isShowingBoard() {
        return this.showingBoard;
    }

    @Override
    public void endTurn() {

        final var currentPlayer = this.gameView.getUserInterface().getController().getCurrentPlayer();
        final List<Meeple> meeples = this.gameView.getUserInterface().getController().getPlayerMeeples(currentPlayer)
                .stream()
                .filter(m -> !m.isPlaced())
                .toList();
        if (this.getSectionSelector().isSectionSelected()) {
            if (!meeples.isEmpty()) {
                final var section = this.getSectionSelector().getSelectedSection().get();
                if (this.gameView.getUserInterface().getController().placeMeeple(meeples.get(0), section)) {
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
        this.gameView.getUserInterface().getController().endTurn();
        this.getBoard().updateMeeplePrecence();
    }

    @Override
    public BoardComponent<JPanel> getBoard() {
        return this.board;
    }

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

    private void showBoard() {
        this.getSectionSelector().reset();
        this.removeAll();
        this.getBoard().draw();
        this.add(this.getBoard().getComponent());
    }

    private void showSectionSelector() {
        this.removeAll();
        this.getSectionSelector().draw();
        this.add(this.getSectionSelector().getComponent());
    }

}
