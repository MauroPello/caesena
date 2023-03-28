package it.unibo.caesena.view.components;


//finta doc, l'idea è avere un component che mi permette in maniera più chiara di separare la board e la section selector
public interface MainComponent<X> {

    void toggleComponents();

    BoardComponent<X> getBoard();

    SectionSelectorComponent<X> getSectionSelector();

    void updateComponents();

    boolean isShowingBoard();

    void endTurn();

    X getComponent();
}
