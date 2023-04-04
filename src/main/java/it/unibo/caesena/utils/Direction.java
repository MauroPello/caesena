package it.unibo.caesena.utils;

/**
 * Enum containing 4 different directions used for nearby tiles.
 */
public enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);

    private Pair<Integer, Integer> coordinates;

    /**
     * Enum constructor marked as private to not allow creation of directions other
     * than UP, RIGHT, DOWN and LEFT
     */
    private Direction(final int x, final int y) {
        this.coordinates = new Pair<>(x, y);
    }

    /**
     * Gets the x value of direction. 
     * 
     * @return x value of direction
     */
    public int getX() {
        return this.coordinates.getX();
    }

    /**
     * Gets the y value of direction. 
     * 
     * @return y value of direction
     */
    public int getY() {
        return this.coordinates.getY();
    }

    /**
     * Checks whether two positions coincide with the same direction.
     * 
     * @param direction for the matching
     * @param p1        first position of first tile
     * @param p2        second position of second tile
     * @return if p1 and p2 match to eachother
     */
    public static boolean match(final Direction direction, final Pair<Integer, Integer> p1,
            final Pair<Integer, Integer> p2) {
        return p1.getX() + direction.getX() == p2.getX()
                && p1.getY() + direction.getY() == p2.getY();
    }
}
