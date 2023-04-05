package it.unibo.caesena.view.scene;

import it.unibo.caesena.view.UpdatableComponent;
import it.unibo.caesena.view.UserInterface;

/**
 * An interface for scenes to be used inside UserInterfaces.
 * It uses a generic type to allow the interface to be used in any GUI framework
 * (such as Swing or JavaFX).
 * 
 * @param <X> the type of the GUI component containing the scene
 */
public interface Scene<X> extends UpdatableComponent<X> {

    /**
     * Gets the UserInterface in which the Scene is displayed.
     * 
     * @param <T> the type of the UserInterface in which the Scene is displayed
     * @return the UserInterface in which the Scene is displayed
     */
    <T extends UserInterface> T getUserInterface();

}
