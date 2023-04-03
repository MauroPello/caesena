package it.unibo.caesena.view.components.board;

public interface BoardManager<X> {

    void toggleComponents();

    BoardComponent<X> getBoard();

    SectionSelectorComponent<X> getSectionSelector();

    void updateComponents();

    boolean isShowingBoard();

    void endTurn();

    X getComponent();
}
