package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.GameSetTileMediatorImpl;
import it.unibo.caesena.model.MutablePlayer;
import it.unibo.caesena.model.PlayerImpl;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;
import it.unibo.caesena.model.meeple.MutableMeeple;
import it.unibo.caesena.model.meeple.NormalMeeple;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileFactoryWithBuilder;
import it.unibo.caesena.model.tile.TileSection;

final class NormalMeepleTest {

    private static MutableMeeple meeple;
    private static MutablePlayer owner;

    @BeforeAll
    static void init() {
        owner = new PlayerImpl("Giocatore1", new Color(50, 50, 50));
        meeple = new NormalMeeple(owner);
    }

    @Test
    void testGetters() {
        assertEquals(1, meeple.getStrength());

        assertFalse(meeple.isPlaced());

        assertEquals(owner, meeple.getOwner());
    }

    @Test
    void testPlace() {
        final Tile tile = new TileFactoryWithBuilder(new GameSetTileMediatorImpl(new GameSetFactoryImpl())).createCity();
        assertFalse(meeple.isPlaced());
        meeple.place(tile, TileSection.CENTER);

        assertTrue(meeple.isPlaced());

        meeple.remove();
        assertFalse(meeple.isPlaced());
    }
}
