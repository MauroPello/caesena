package it.unibo.caesena.view.components;

public interface MainComponent<X> {

    void toggleComponents();

    BoardComponent<X> getBoard();

    SectionSelectorComponent<X> getSectionSelector();

    void updateComponents();

    boolean isShowingBoard();

    void endTurn();

    X getComponent();
}
