package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.meeple.MutableMeeple;
import it.unibo.caesena.model.meeple.MeepleImpl;
import it.unibo.caesena.model.meeple.MeepleType;
import it.unibo.caesena.model.player.MutablePlayerInGame;
import it.unibo.caesena.model.player.Player;
import it.unibo.caesena.model.player.PlayerInGameImpl;
import it.unibo.caesena.model.tile.Tile;
import it.unibo.caesena.model.tile.TileFactoryWithBuilder;
import it.unibo.caesena.model.tile.TileSection;
import it.unibo.caesena.model.tile.TileSectionType;
import it.unibo.caesena.model.tile.TileType;

final class NormalMeepleTest {

    private static final Color PLAYER_COLOR = new Color(50, 50, 50);
    private static MutableMeeple meeple;
    private static MutablePlayerInGame owner;

    @BeforeAll
    static void init() {
        owner = new PlayerInGameImpl(new Player("Giocatore1"), PLAYER_COLOR);
        meeple = new MeepleImpl(new MeepleType("Normal", 1), owner);
    }

    @Test
    void testGetters() {
        assertEquals(1, meeple.getStrength());

        assertFalse(meeple.isPlaced());

        assertEquals(owner, meeple.getOwner());
    }

    @Test
    void testPlace() {
        final Tile tile = new TileType("CITY").createTile(new TileFactoryWithBuilder()).getX();
        assertFalse(meeple.isPlaced());
        // TODO meeple.place(tile, TileSectionType.getFromName("CENTER"));

        assertTrue(meeple.isPlaced());

        meeple.remove();
        assertFalse(meeple.isPlaced());
    }
}
