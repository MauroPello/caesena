package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.gameset.GameSet;
import it.unibo.caesena.model.gameset.GameSetFactory;
import it.unibo.caesena.model.gameset.GameSetFactoryImpl;

final class GameSetFactoryImplTest {

    private static GameSetFactory gamesetFactory;    
    private static GameSet gamesetCity;    
    private static GameSet gamesetMonastery;
    private static GameSet gamesetRoad;
    private static GameSet gamesetField;

    @BeforeAll
    public static void init() {
        gamesetFactory = new GameSetFactoryImpl();
        gamesetCity = gamesetFactory.createCitySet();
        gamesetField =  gamesetFactory.createFieldSet();
        gamesetMonastery =  gamesetFactory.createMonasterySet();
        gamesetRoad =  gamesetFactory.createRoadSet();
    }

    @Test
    public void testCreateJoinedSet () {
        assertThrows(IllegalStateException.class, () -> gamesetFactory.createJoinedSet(gamesetField, gamesetCity));
        assertThrows(IllegalStateException.class, () -> gamesetFactory.createJoinedSet(gamesetField, gamesetMonastery));
        assertThrows(IllegalStateException.class, () -> gamesetFactory.createJoinedSet(gamesetField, gamesetRoad));
        assertThrows(IllegalStateException.class, () -> gamesetFactory.createJoinedSet(gamesetRoad, gamesetCity));
        assertThrows(IllegalStateException.class, () -> gamesetFactory.createJoinedSet(gamesetMonastery, gamesetCity));
        assertThrows(IllegalStateException.class, () -> gamesetFactory.createJoinedSet(gamesetRoad, gamesetMonastery));
        
        assertEquals(gamesetCity.getType(), gamesetFactory.createJoinedSet(gamesetCity, gamesetCity).getType());
        assertEquals(gamesetField.getType(), gamesetFactory.createJoinedSet(gamesetField, gamesetField).getType());
        assertEquals(gamesetRoad.getType(), gamesetFactory.createJoinedSet(gamesetRoad, gamesetRoad).getType());
        assertEquals(gamesetMonastery.getType(), gamesetFactory.createJoinedSet(gamesetMonastery, gamesetMonastery).getType());
    }
}
