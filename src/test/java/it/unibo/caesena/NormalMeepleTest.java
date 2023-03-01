package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.meeple.NormalMeeple;
import it.unibo.caesena.model.Player;
import it.unibo.caesena.model.PlayerImpl;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileFactoryWithBuilder;
import it.unibo.caesena.model.tile.TileSection;
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
        assertEquals(Optional.empty(), meeple.getTileSection());
        assertEquals(Optional.empty(), meeple.getTile());
        assertFalse(meeple.isPlaced());
        assertFalse(meeple.removeFromTile());
        assertEquals(owner, meeple.getOwner());
    }

    @Test
    public void testEquals() {
        final NormalMeeple meeple2 = new NormalMeeple(owner);
        assertEquals(meeple2, meeple);
    }

    @Test
    public void testPlace() {
        final Tile tile = new TileFactoryWithBuilder().createMonastery();
        assertTrue(meeple.place(TileSection.Center, tile));
        assertEquals(TileSection.Center, meeple.getTileSection().get());
        assertEquals(tile, meeple.getTile().get());
        assertTrue(meeple.removeFromTile());
    }

}