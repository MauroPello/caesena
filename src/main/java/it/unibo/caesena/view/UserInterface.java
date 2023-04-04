package it.unibo.caesena.view;

import it.unibo.caesena.controller.Controller;

/**
 * Defines an interface for views in the MVC model
 */
public interface UserInterface {

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
