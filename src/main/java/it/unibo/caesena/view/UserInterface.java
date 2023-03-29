package it.unibo.caesena.view;

import it.unibo.caesena.controller.Controller;

public interface UserInterface {

    /**
     * Close GUI and view's
     */
    void exit();

    /**
     * 
     * @return GUI controller
     */
    Controller getController();

}
