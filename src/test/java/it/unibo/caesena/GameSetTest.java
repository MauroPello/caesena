package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.PlayerImpl;
import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetFactory;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;
import it.unibo.caesena.model.meeple.Meeple;
import it.unibo.caesena.model.meeple.NormalMeeple;

final class GameSetTest {

    private static GameSetFactory gamesetFactory;
    private static GameSet gamesetCity;
    private static GameSet gamesetCity2;
    private static GameSet gamesetField;
    private static Meeple meeple;

    @BeforeAll
    static void init() {
        gamesetFactory = new GameSetFactoryImpl();
        gamesetCity = gamesetFactory.createCitySet();
        gamesetCity2 = gamesetFactory.createCitySet();
        gamesetField = gamesetFactory.createFieldSet();

        meeple = new NormalMeeple(new PlayerImpl("Giocatore1", new Color(50, 50, 50)));
    }

    @Test
    void testCreateJoinedSet() {
        assertThrows(IllegalStateException.class, () -> gamesetFactory.createJoinedSet(gamesetField, gamesetCity));

        assertEquals(gamesetCity.getType(), gamesetFactory.createJoinedSet(gamesetCity, gamesetCity2).getType());
    }

    @Test
    void testMeeple() {
        assertTrue(gamesetField.isMeepleFree());

        assertTrue(gamesetField.addMeeple(meeple));

        assertFalse(gamesetField.isMeepleFree());
    }
}
