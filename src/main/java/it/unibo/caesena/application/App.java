package it.unibo.caesena.application;

import it.unibo.caesena.controller.ControllerImpl;
import it.unibo.caesena.view.GUI;

public final class App {

    /**
     * Private constructor, used in restricting object creation.
     */
    private App() {

    }

    /**
     * The main function of the program.
     */
    public static void main(final String[] args) {
        new GUI(new ControllerImpl());
    }
}
