package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.Player;
import it.unibo.caesena.model.PlayerImpl;
import it.unibo.caesena.model.meeple.NormalMeeple;

final class NormalMeepleTest {

    private static NormalMeeple meeple;
    private static Player owner;

    @BeforeAll
    public static void init() {
        owner = new PlayerImpl("Giocatore1");
        meeple = new NormalMeeple(owner);
    }

    @Test
    public void testGetters() {
        assertEquals(1, meeple.getStrength());

        assertFalse(meeple.isPlaced());

        assertEquals(owner, meeple.getOwner());
    }

    @Test
    public void testEquals() {
        final NormalMeeple meeple2 = new NormalMeeple(owner);
        assertEquals(meeple2, meeple);
    }

    @Test
    public void testPlace() {
        assertFalse(meeple.isPlaced());
        meeple.setPlaced(true);

        assertTrue(meeple.isPlaced());

        meeple.setPlaced(false);
        assertFalse(meeple.isPlaced());
    }

}