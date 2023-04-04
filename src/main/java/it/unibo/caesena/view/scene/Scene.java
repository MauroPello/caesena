package it.unibo.caesena.view.scene;

import it.unibo.caesena.view.UserInterface;

/**
 * An interface for scenes to be used inside UserInterfaces.
 * It uses a generic type to allow the interface to be used in any GUI framework
 * (such as Swing or JavaFX).
 * 
 * @param <X> the type of the component containing the scene
 */
public interface Scene<X> {

    /**
     * Updates the Scene.
     */
    void update();

    /**
     *
     * @return whether or not the scene is visible
     */
    boolean isVisible();

    /**
     *
     * @param visible boolean representing whether or not the scene should be
     *                visible
     */
    void setVisible(boolean visible);

    /**
     *
     * @return the component containing the scene
     */
    X getComponent();

    /**
     *
     * @param <T>
     * @return
     */
    <T extends UserInterface> T getUserInterface();

}
