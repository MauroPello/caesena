package it.unibo.caesena.view;

import it.unibo.caesena.controller.Controller;

/**
 * An interface for views in the MVC model.
 */
public interface UserInterface {

    /**
     * Sets the controller to be used by the UserInterface.
     *
     * @param controller to be used by the UserInterface
     */
    void setController(Controller controller);

    /**
     * Updates the UserInterface.
     */
    void update();

    /**
     * Closes the UserInterface.
     */
    void exit();

    /**
     * Gets the controller used by the UserInterface.
     *
     * @return the controller used by the UserInterface
     */
    Controller getController();

}
