package it.unibo.caesena.view;

import it.unibo.caesena.controller.Controller;

public interface UserInterface {

    void update();

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
