package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.meeple.NormalMeeple;
import it.unibo.caesena.model.Player;
import it.unibo.caesena.model.PlayerImpl;
import it.unibo.caesena.utils.Color;

final class NormalMeepleTest {

    private static NormalMeeple meeple;
    private static Player owner;

    @BeforeAll
    public static void init() {
        owner = new PlayerImpl("Giocatore1", Color.createColor("FF0000", "Red"));
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
        assertTrue(meeple.placeOnTile());

        assertTrue(meeple.isPlaced());
        
        assertTrue(meeple.removeFromTile());
        assertFalse(meeple.isPlaced());
    }

}