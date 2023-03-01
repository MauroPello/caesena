package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.controller.*;

final class ControllerTest {

    private static Controller controller;

    @BeforeAll
    public static void init() {
        controller = new ControllerImpl();
    }

    @Test
    public void testStartGame() {
        controller.startGame();
    }
}