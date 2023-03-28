package it.unibo.caesena.application;

import it.unibo.caesena.controller.ControllerImpl;
import it.unibo.caesena.view.GUI;

public final class App {

    private App() {

    }

    public static void main(final String[] args) {
        new GUI(new ControllerImpl());
    }
}
