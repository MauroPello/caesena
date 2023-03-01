package it.unibo.caesena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.caesena.utils.Color;

final class ColorTest {

    private static Color color;
    private static Color customColor;

    @BeforeAll
    public static void init() {
        color = Color.createColor("00FF00", "Green");
        customColor = Color.createCustomColor("00FF00");
    }

    @Test
    public void testEquals() {
        assertEquals(color, customColor);
    }
    @Test
    public void testToString() {
        String string = color.toString();
        assertEquals("[Hex: 00FF00, Hex as integer: 65280, Name: Green]", string);
    }
}