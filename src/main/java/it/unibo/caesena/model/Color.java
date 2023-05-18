package it.unibo.caesena.model;

import it.unibo.caesena.utils.StringUtil;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * This class contains the concept of color.
 * It's meant to be indipendent from any GUI framework, but for this project it
 * can be easily to convert this color as a {@link java.awt.Color}.
 */
@Entity(name = "Colors")
@Table(name = "Colors")
@Access(AccessType.FIELD)
public class Color {

    @Id
    private String hex;

    private String name;
    private int red;
    private int green;
    private int blue;

    /**
     * Class constructor.
     * It required the RGB values of the color.
     */
    public Color() {}

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((hex == null) ? 0 : hex.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Color other = (Color) obj;
        if (hex == null) {
            if (other.hex != null)
                return false;
        } else if (!hex.equals(other.hex))
            return false;
        return true;
    }

   
}
