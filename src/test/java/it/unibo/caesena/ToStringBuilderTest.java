package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.model.Color;
import it.unibo.caesena.model.player.Player;
import it.unibo.caesena.model.player.PlayerInGameImpl;
import it.unibo.caesena.utils.StringUtil;

final class ToStringBuilderTest {

    private static final Color PLAYER_COLOR = new Color(50, 50, 50);
    private static PlayerInGameImpl player;

    @BeforeAll
    static void init() {
        player = new PlayerInGameImpl(new Player("Giocatore1"), PLAYER_COLOR);
    }

    @Test
    void testToStringCoherence() {
        assertEquals(new StringUtil.ToStringBuilder().addFromObjectGetters(player).build(), player.toString());
    }

    @Test
    void testAutomaticAddCoherence() {
        final String firstResult = player.toString();
        for (int i = 0; i < 1000; i++) {
            assertEquals(firstResult, player.toString());
        }
    }

    @Test
    void testAutomaticAddCoherenceWithCustomElement() {
        var builder = new StringUtil.ToStringBuilder().addFromObjectGetters(player);
        builder.add("Prova aggiunta", "Ciao");
        final String firstResult = builder.build();
        for (int i = 0; i < 1000; i++) {
            builder = new StringUtil.ToStringBuilder().addFromObjectGetters(player);
            builder.add("Prova aggiunta", "Ciao");
            assertEquals(firstResult, builder.build());
        }
    }
}
