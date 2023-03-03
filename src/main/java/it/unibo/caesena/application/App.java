package it.unibo.caesena.application;

import it.unibo.caesena.controller.ControllerImpl;
import it.unibo.caesena.view.UserInterface;

public class App {
    public static void main(final String[] args) {
        new UserInterface(new ControllerImpl());
    }
}
