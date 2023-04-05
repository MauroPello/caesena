package it.unibo.caesena;

import it.unibo.caesena.utils.Direction;
import it.unibo.caesena.utils.Pair;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class DirectionTest {

    private static Direction direction;

    @BeforeAll
    static void init() {
       direction = Direction.UP;
    }

    @Test
    void testGetters() {
        assertEquals(0, direction.getX());
        assertEquals(-1, direction.getY());
    }

    @Test
    void match() {
        final Pair<Integer, Integer> firstDirection = new Pair<>(10, 10);
        final Pair<Integer, Integer> secondDirection = new Pair<>(10, 9);

        assertTrue(Direction.match(direction, firstDirection, secondDirection));
        assertFalse(Direction.match(direction, secondDirection, firstDirection));
    }
}
