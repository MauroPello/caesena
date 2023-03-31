package it.unibo.caesena.view.components;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.view.GameView;

public class MainComponentImpl extends JPanel implements MainComponent<JPanel> {
    private static final long serialVersionUID = 1073591515646435610L;
    private final GameView gameView;
    private final BoardComponent<JPanel> board;
    private final SectionSelectorComponent<JPanel> sectionSelector;
    private boolean showingBoard;


    public MainComponentImpl(final GameView gameView) {
        this.gameView = gameView;
        this.board = new BoardComponentImpl(this.gameView);
        this.sectionSelector = new SectionSelectorComponentImpl(this.gameView);
        this.showingBoard = true;
        // TODO put background
        this.setBackground(Color.DARK_GRAY);
        this.add(this.getBoard().getComponent());
    }

    @Override
    public void toggleComponents() {
        showingBoard = !showingBoard;
        updateComponents();
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
        final List<Meeple> meeples = this.gameView.getUserInterface().getController().getPlayerMeeples(currentPlayer).stream().filter(m->!m.isPlaced()).toList();
        if (this.getSectionSelector().isSectionSelected()) {
            if (!meeples.isEmpty()) {
                final var section = this.getSectionSelector().getSelectedSection().get();
                if (this.gameView.getUserInterface().getController().placeMeeple(meeples.get(0), section)) {
                    this.board.getCurrentTileButton().setMeeple(meeples.get(0), section);
                } else {
                    throw new IllegalStateException("Tried to add meeple but gameSet already had at least one");
                }
            } else {
                throw new IllegalStateException("Tried to add meeple but run out of them");
            }
        }
        this.getSectionSelector().reset();
        // this.getBoard().updateMeeplePrecence();


        if (!showingBoard) {
            toggleComponents();
        } else{
            updateComponents();
        }
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

    private void showBoard(){
        if (this.getSectionSelector().isSectionSelected()) {
            final var currentPlayer = this.gameView.getUserInterface().getController().getCurrentPlayer();
            final Meeple meeple = this.gameView.getUserInterface().getController().getPlayerMeeples(currentPlayer).stream().filter(m->!m.isPlaced()).findFirst().get();
            this.board.getCurrentTileButton().setMeeple(meeple, this.getSectionSelector().getSelectedSection().get());
        } else {
            this.board.getCurrentTileButton().unsetMeeple();
        }
        this.removeAll();
        this.getBoard().draw();
        this.add(this.getBoard().getComponent());
    }

    private void showSectionSelector(){
        this.removeAll();
        this.getSectionSelector().draw();
        this.add(this.getSectionSelector().getComponent());
    }

}
