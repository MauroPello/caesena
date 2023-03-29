package it.unibo.caesena.view;

public interface View<X> {

    /**
     * 
     * @return if view is visible or not
     */
    public boolean isVisible();

    /**
     * 
     * @param visible sets the visibility for the current view
     */
    public void setVisible(final boolean visible);

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
