package it.unibo.caesena;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.meeple.MutableMeeple;
import it.unibo.caesena.model.meeple.MeepleImpl;
import it.unibo.caesena.model.meeple.MeepleType;
import it.unibo.caesena.model.player.MutablePlayer;
import it.unibo.caesena.model.player.PlayerImpl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetFactory;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

final class GameSetTest {

    private static final Color PLAYER_COLOR = new Color(50, 50, 50);
    private static final int POINTS_INCREMENT = 5;
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
        owner = new PlayerImpl("Giocatore1", PLAYER_COLOR);
        meepleTest = new MeepleImpl(new MeepleType("Normal", 1), owner);
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
    void testPoints() {
        assertEquals(0, gamesetField.getPoints());
        gamesetField.setPoints(POINTS_INCREMENT);
        assertEquals(POINTS_INCREMENT, gamesetField.getPoints());
        gamesetField.addPoints(POINTS_INCREMENT);
        assertEquals(POINTS_INCREMENT + POINTS_INCREMENT, gamesetField.getPoints());
    }

    @Test
    void testCloseGameSet() {
        assertFalse(gamesetToClose.isClosed());
        gamesetToClose.setPoints(POINTS_INCREMENT);
        gamesetToClose.addMeeple(meepleTest);
        gamesetToClose.close();
        assertTrue(gamesetToClose.isClosed());
        assertEquals(POINTS_INCREMENT, owner.getScore());
    }
}
