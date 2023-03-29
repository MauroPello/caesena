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
        if (this.getSectionSelector().isSectionSelected()) {
            final var section = this.getSectionSelector().getSelectedSection();
            final var currentPlayer = this.gameView.getUserInterface().getController().getCurrentPlayer();
            final List<Meeple> meeples = this.gameView.getUserInterface().getController().getNotPlacedPlayerMeeples(currentPlayer);
            if (!meeples.isEmpty()) {
                if (this.gameView.getUserInterface().getController().placeMeeple(meeples.get(0), section)) {
                    board.getPlacedTileButton().addMeeple(meeples.get(0), section);
                } else {
                    throw new IllegalStateException("Tried to add meeple but gameSet already had at least one");
                }
            } else {
                throw new IllegalStateException("Tried to add meeple but run out of them");
            }
            this.getSectionSelector().reset();
        }

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
        this.revalidate();
        this.validate();
        this.repaint();
    }

    private void showBoard(){
        this.removeAll();
        this.add(this.getBoard().getComponent());
        this.getBoard().draw();
    }

    private void showSectionSelector(){
        this.removeAll();
        // TODO se vogliamo toglierlo bisogna trovare un modo di gestire questa cosa in tile button in modo che il giocatore sappia dove ha "piazzato" il meeple
        this.getSectionSelector().reset();
        this.getSectionSelector().draw();
        this.add(this.getSectionSelector().getComponent());
    }

}
