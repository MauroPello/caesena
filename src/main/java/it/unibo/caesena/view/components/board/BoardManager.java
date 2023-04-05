package it.unibo.caesena.view.components.board;

import it.unibo.caesena.view.BasicComponent;
import it.unibo.caesena.view.UpdatableComponent;

/**
 * This class rapresents a maneger of what should be shown in the center of the
 * screen either a {@link BoardComponent}
 * or a {@link SectionSelectorBasicComponent}.
 *
 * @param <X> is the type of both the components
 */
public interface BoardManager<X> extends UpdatableComponent<X> {

    /**
     * Toggles beetwen the
     * {@link BoardComponent}
     * and a
     * {@link SectionSelectorBasicComponent}.
     */
    void toggleComponents();

    /**
     * Gets the {@link BoardComponent}.
     *
     * @return the {@link BoardComponent}
     */
    BoardComponent<X> getBoard();

    /**
     * Gets the {@link SectionSelectorBasicComponent}.
     *
     * @return the
     *         {@link SectionSelectorBasicComponent}
     */
    SectionSelectorBasicComponent<X> getSectionSelector();

    /**
     * Specifies if the manager is currently showing the
     * {@link BoardComponent}.
     *
     * @return true if is showing the board, false otherwise.
     */
    boolean isShowingBoard();

    /**
     * Ends the current turn.
     */
    void endTurn();

}
