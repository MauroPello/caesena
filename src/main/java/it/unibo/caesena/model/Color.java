package it.unibo.caesena.model;

import it.unibo.caesena.utils.StringUtil;

/**
 * This class contains the concept of color.
 * It's meant to be indipendent from any GUI framework, but for this project it
 * can be easily to convert this color as a {@link java.awt.Color}.
 */
public class Color {

    private String hex;
    private String name;
    private int red;
    private int green;
    private int blue;

    /**
     * Class constructor.
     *
     * @param hex   the color as hex
     */
    public Color(final String hex) {
        this.hex = hex;
    }

    /**
     * Class constructor.
     * It required the RGB values of the color.
     *
     * @param red   the red component of the color
     * @param green the green component of the color
     * @param blue  the blue component of the color
     */
    public Color(final int red, final int green, final int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Gets the hex of the color.
     *
     * @return the hex of the color
     */
    public String getHex() {
        return this.hex;
    }

    /**
     * Gets the display name of the color.
     *
     * @return the display name of the color
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the red component of the color.
     *
     * @return the red component of the color
     */
    public int getRed() {
        return this.red;
    }

    /**
     * Gets the green component of the color.
     *
     * @return the green component of the color
     */
    public int getGreen() {
        return this.green;
    }

    /**
     * Gets the blue component of the color.
     *
     * @return the blue component of the color
     */
    public int getBlue() {
        return this.blue;
    }

    /**
     * Gets the color as a {@link java.awt.Color}.
     *
     * @return the color as a {@link java.awt.Color}
     */
    public java.awt.Color asSwingColor() {
        return new java.awt.Color(this.getRed(), this.getGreen(), this.getBlue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new StringUtil.ToStringBuilder()
                .addFromObjectGetters(this)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((hex == null) ? 0 : hex.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Color other = (Color) obj;
        return other.getHex() == this.getHex();
    }
}
