package it.unibo.caesena.view.components;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.view.GameView;

public class MainComponentImpl extends JPanel implements MainComponent<JPanel> {

    private final GameView gameView;
    private final BoardComponent<JPanel> board;
    private final SectionSelectorComponent<JPanel> sectionSelector;
    private boolean showingBoard;


    public MainComponentImpl(GameView gameView) {
        this.gameView = gameView;
        this.board = new BoardComponentImpl(this.gameView);//TODO capire se serve avere il campo gameview
        this.sectionSelector = new SectionSelectorComponentImpl(this.gameView);
        this.showingBoard = true;
        this.setBackground(Color.DARK_GRAY);
        this.add(this.getBoard().getComponent());
    }

    @Override
    public void toggleComponents() {
        if (this.showingBoard) {
            this.showSectionSelector();
        } else {
            this.showBoard();
        }
        showingBoard = !showingBoard;
    }

    private void showBoard(){
        this.removeAll();
        this.getBoard().draw();
        this.add(this.getBoard().getComponent());
        this.validate();
    }

    private void showSectionSelector(){
        this.removeAll();
        this.getSectionSelector().draw();
        this.add(this.getSectionSelector().getComponent());
        //TODO capire quale diamine serve
        this.validate();
        this.revalidate();
        this.repaint();
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
            var section = this.getSectionSelector().getSelectedSection();
            var currentPlayer = this.gameView.getUserInterface().getController().getCurrentPlayer();
            List<Meeple> meeples = this.gameView.getUserInterface().getController().getNotPlacedPlayerMeeples(currentPlayer);
            if (!meeples.isEmpty()) {
                if (this.gameView.getUserInterface().getController().placeMeeple(meeples.get(0), section)) {
                    this.getBoard().placeMeepleOnCurrentSection(meeples.get(0), section);
                } else {
                    throw new IllegalStateException("Tried to add meeple but gameSet already had at least one");
                }
            } else {
                throw new IllegalStateException("Tried to add meeple but run out of them");
            }
        }
        if (!showingBoard) {
            toggleComponents();
        }
        this.revalidate();
        this.validate();
        this.repaint();
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
    }

}
