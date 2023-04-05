package it.unibo.caesena.view;

/**
 * An interface representing a {@link it.unibo.caesena.view.BasicComponent} that
 * can be updated during execution.
 *
 * @param <X> the type of the GUI component used
 */
public interface UpdatableComponent<X> extends BasicComponent<X> {
    /**
     * Updates the component.
     */
    void update();
}
