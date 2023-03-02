package it.unibo.caesena.utils;

import java.util.Optional;

public class Color {

    private Optional<String> name = Optional.empty();
    private String hex;

    private Color() {};

    public static Color createCustomColor(String hex) {
        final Color playerColor = new Color();
        playerColor.hex = hex;
        return playerColor;
    }

    public static Color createColor(String hex, String name) {
        final Color playerColor = new Color();
        playerColor.hex = hex;
        playerColor.name = Optional.of(name);
        return playerColor;
    }

    public String getHex() {
        return this.hex;
    }

    public int getHexAsInteger() {
        return Integer.parseInt(hex, 16);
    }

    public String getName() {
        return this.name.isEmpty() ? "Custom Color" : this.name.get();
    }

    @Override
    public boolean equals(final Object arg0) {
        Color otherColor = (Color)arg0;
        return this.hex == otherColor.hex;
    }

    @Override
    public String toString() {
        return new StringUtil.ToStringBuilder().addFromObjectGetters(this).build();
    }


}