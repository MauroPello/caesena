package it.unibo.caesena.view.components;

public interface FooterComponent<X> {

    /**
     * Update the JPanel with the current tile image.
     */
    void updateCurrentTile();

    /**
     * @return the component which rapresent the footer.
     */
    X getComponent();

    /**
     * Update every component in the footer.
     */
    void update();
}
