package it.unibo.caesena.model;

import it.unibo.caesena.utils.StringUtil;

public class Color {
    private final int red;
    private final int green;
    private final int blue;


    public Color(final int red, final int green, final int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed(){
        return this.red;
    }

    public int getGreen(){
        return this.green;
    }

    public int getBlue(){
        return this.blue;
    }

    public java.awt.Color asSwingColor() {
        return new java.awt.Color(this.getRed(), this.getGreen(), this.getBlue());
    }

    @Override
    public String toString() {
        return new StringUtil.ToStringBuilder()
            .addFromObjectGetters(this)
            .build();
    }

}
