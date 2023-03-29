package it.unibo.caesena.view;

public interface View<X> {

    /**
     * 
     * @return
     */
    public boolean isVisible();

    /**
     * 
     * @param visible
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
