package it.unibo.caesena.view.components;

/**
 * An interface for sidebars to be used in Scene
 */
public interface SideBarComponent<X> {

    /**
     * Gets the component which represents the sidebar.
     * 
     * @return the component which represents the sidebar.
     */
    X getComponent();
}
