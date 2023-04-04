package it.unibo.caesena.view.components.board;

/**
 * This class rapresents a maneger of what should be shown in the center of the
 * screen either a {@link it.unibo.caesena.view.components.board.BoardComponent}
 * or a {@link it.unibo.caesena.view.components.board.SectionSelectorComponent}.
 *
 * @param <X> is the type of both the components
 */
public interface BoardManager<X> {

    /**
     * Toggles beetwen the
     * {@link it.unibo.caesena.view.components.board.BoardComponent}
     * and a
     * {@link it.unibo.caesena.view.components.board.SectionSelectorComponent}.
     */
    void toggleComponents();

    /**
     * Gets the {@link it.unibo.caesena.view.components.board.BoardComponent}.
     *
     * @return the {@link it.unibo.caesena.view.components.board.BoardComponent}
     */
    BoardComponent<X> getBoard();

    /**
     * Gets the {@link it.unibo.caesena.view.components.board.SectionSelectorComponent}.
     *
     * @return the
     *         {@link it.unibo.caesena.view.components.board.SectionSelectorComponent}
     */
    SectionSelectorComponent<X> getSectionSelector();

    /**
     * Updates the content for either a
     * {@link it.unibo.caesena.view.components.board.BoardComponent}
     * or a {@link it.unibo.caesena.view.components.board.SectionSelectorComponent}.
     */
    void updateComponents();

    /**
     * Specifies if the manager is currently showing the
     * {@link it.unibo.caesena.view.components.board.BoardComponent}.
     *
     * @return true if is showing the board, false otherwise.
     */
    boolean isShowingBoard();

    /**
     * Ends the current turn.
     */
    void endTurn();

    /**
     * Gets the Board as a specific component of a GUI framework, such as JPanel.
     *
     * @return the Board as a specific component of a GUI framework.
     */
    X getComponent();
}
