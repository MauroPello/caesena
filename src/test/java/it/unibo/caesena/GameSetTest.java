package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetFactory;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;

final class GameSetTest {

    private static GameSetFactory gamesetFactory;
    private static GameSet gamesetCity;
    private static GameSet gamesetCity2;
    private static GameSet gamesetField;

    @BeforeAll
    static void init() {
        gamesetFactory = new GameSetFactoryImpl();
        gamesetCity = gamesetFactory.createCitySet();
        gamesetCity2 = gamesetFactory.createCitySet();
        gamesetField = gamesetFactory.createFieldSet();
    }

    @Test
    void testCreateJoinedSet() {
        assertThrows(IllegalStateException.class, () -> gamesetFactory.createJoinedSet(gamesetField, gamesetCity));

        assertEquals(gamesetCity.getType(), gamesetFactory.createJoinedSet(gamesetCity, gamesetCity2).getType());
    }

    @Test
    void testMeeple() {
        assertTrue(gamesetField.isMeepleFree());
    }
}
