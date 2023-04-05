package it.unibo.caesena.view;

public interface UpdatableComponent<X> extends BasicComponent<X> {
    /**
     * Updates the component.
     */
    void update();
}
