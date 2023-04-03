package it.unibo.caesena.application;

import it.unibo.caesena.controller.Controller;
import it.unibo.caesena.controller.ControllerImpl;
import it.unibo.caesena.view.GUI;

public final class App {

    /**
     * Private constructor, used for restricting object creation.
     */
    private App() {

    }

    /**
     * The main function of the program.
     */
    public static void main(final String[] args) {
        final Controller controller = new ControllerImpl();
        new GUI(controller);
        //new GUI(controller);
    }
}
