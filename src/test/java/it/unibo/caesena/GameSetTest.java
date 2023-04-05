package it.unibo.caesena;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.meeple.MutableMeeple;
import it.unibo.caesena.model.meeple.NormalMeeple;
import it.unibo.caesena.model.player.MutablePlayer;
import it.unibo.caesena.model.player.PlayerImpl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetFactory;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class GameSetTest {

    private static GameSetFactory gamesetFactory;
    private static GameSet gamesetCity;
    private static GameSet gamesetCity2;
    private static GameSet gamesetField;
    private static GameSet gamesetToClose;
    private static MutableMeeple meepleTest;
    private static MutablePlayer owner;

    @BeforeAll
    static void init() {
        gamesetFactory = new GameSetFactoryImpl();
        gamesetToClose = gamesetFactory.createCitySet();
        gamesetCity = gamesetFactory.createCitySet();
        gamesetCity2 = gamesetFactory.createCitySet();
        gamesetField = gamesetFactory.createFieldSet();
        owner = new PlayerImpl("Giocatore1", new Color(50, 50, 50));
        meepleTest = new NormalMeeple(owner);
    }

    @Test
    void testCreateJoinedSet() {
        assertThrows(IllegalStateException.class, () -> gamesetFactory.createJoinedSet(gamesetField, gamesetCity));

        assertEquals(gamesetCity.getType(), gamesetFactory.createJoinedSet(gamesetCity, gamesetCity2).getType());
    }

    @Test
    void testMeeple() {
        assertTrue(gamesetField.isMeepleFree());
        gamesetField.addMeeple(meepleTest);
        assertFalse(gamesetField.isMeepleFree());
        assertEquals(List.of(meepleTest), gamesetField.getMeeples());
    }

    @Test
    void testPoints () {
        assertEquals(gamesetField.getPoints(), 0);
        gamesetField.setPoints(5);
        assertEquals(gamesetField.getPoints(), 5);
        gamesetField.addPoints(5);
        assertEquals(gamesetField.getPoints(), 10);
        gamesetField.addPoints(5);
        assertEquals(gamesetField.getPoints(), 15);
    }

    @Test
    void testCloseGameSet () {
        assertFalse(gamesetToClose.isClosed());
        gamesetToClose.setPoints(5);
        gamesetToClose.addMeeple(meepleTest);
        gamesetToClose.close();
        assertTrue(gamesetToClose.isClosed());
        assertEquals(owner.getScore(), 5);
    }
}
