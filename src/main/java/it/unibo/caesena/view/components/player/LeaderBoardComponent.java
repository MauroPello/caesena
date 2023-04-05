package it.unibo.caesena.view.components.player;

/**
 * This interface rappresents a LeaderBoard to be used in Scene.
 *
 * This component shows the name, the score and the color of every player
 * taken from the the GUI component
 *
 * @param <X> the type of the GUI component
 */
public interface LeaderBoardComponent<X> {

    /**
     * Sets whether or not the component should be visible.
     *
     * @param visible boolean representing whether or not the component should be
     *                visible
     */
    void setVisible(boolean visible);

    /**
    * Updates the LeaderBoardComponent.
    */
    void update();
    /**
    * Gets the GUI component containing the LeaderBoardComponent.
    *
    * @return the GUI component containing the LeaderBoardComponent
    */
    X getComponent();
}
