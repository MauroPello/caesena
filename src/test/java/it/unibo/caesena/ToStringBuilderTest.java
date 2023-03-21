package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import it.unibo.caesena.model.PlayerImpl;
import it.unibo.caesena.utils.*;

final class ToStringBuilderTest {

    private static PlayerImpl player;

    @BeforeAll
    public static void init() {
        player = new PlayerImpl("Giocatore1");
    }

    @Test
    public void testToStringCoherence() {
        assertEquals(new StringUtil.ToStringBuilder().addFromObjectGetters(player).build(), player.toString());
    }


    @Test
    public void testAutomaticAddCoherence() {
        String firstResult = player.toString();
        for (int i = 0; i < 1000; i++) {
            assertEquals(firstResult, player.toString());
        }
    }

    @Test
    public void testAutomaticAddCoherenceWithCustomElement() {
        var builder = new StringUtil.ToStringBuilder().addFromObjectGetters(player);
        builder.add("Prova aggiunta", "Ciao");
        String firstResult = builder.build();
        for (int i = 0; i < 1000; i++) {
            builder = new StringUtil.ToStringBuilder().addFromObjectGetters(player);
            builder.add("Prova aggiunta", "Ciao");
            assertEquals(firstResult, builder.build());
        }
    }
}