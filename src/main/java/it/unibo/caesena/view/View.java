package it.unibo.caesena.view;

public interface View<X> {

    void update();

    /**
     * 
     * @return if view is visible or not
     */
    boolean isVisible();

    /**
     * 
     * @param visible sets the visibility for the current view
     */
    void setVisible(boolean visible);

    /**
     * 
     * @return
     */
    X getComponent();

    /**
     * 
     * @param <T>
     * @return
     */
    <T extends UserInterface> T getUserInterface();

}
