package it.unibo.caesena.view.components;

/**
 * An interface for sidebars to be used in Scene
 */
public interface SideBarComponent<X> {

    /**
     * @return the component which rapresent the footer.
     */
    X getComponent();
}
