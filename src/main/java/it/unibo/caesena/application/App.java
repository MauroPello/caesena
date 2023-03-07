package it.unibo.caesena.application;

import it.unibo.caesena.controller.ControllerImpl;
import it.unibo.caesena.view.GUI;

public class App {
    public static void main(final String[] args) {
        new GUI(new ControllerImpl());
    }
}
