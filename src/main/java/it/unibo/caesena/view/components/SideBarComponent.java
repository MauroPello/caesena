package it.unibo.caesena.view.components;

public interface SideBarComponent<X> {

    /**
     * @return the component which rapresent the footer.
     */
    X getComponent();

    /**
     * Update the SideBar.
     */
    void update();
}
