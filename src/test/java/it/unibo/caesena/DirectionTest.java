package it.unibo.caesena;

import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class DirectionTest {

    private static Direction direction;

    @BeforeAll
    static void init() {
       direction = Direction.UP;
    }

    @Test
    void getX() {
        assertEquals(direction.getX(), 0);
    }

    @Test
    void getY() {
        assertEquals(direction.getY(), -1);
    }

    @Test
    void match() {
        final Pair<Integer, Integer> firstDirection = new Pair<>(10 ,10);
        final Pair<Integer, Integer> secondDirection = new Pair<>(10 ,9);

        assertTrue(Direction.match(direction, firstDirection, secondDirection));
        assertFalse(Direction.match(direction, secondDirection, firstDirection));
    }
}