package it.unibo.caesena.utils;

/**
 * Enum containing 4 different directions used for nearby tiles.
 */
public enum Direction {
    /**
     * Direction.UP means that the difference between the coordinates of the two
     * positions is 0 for the x coordinate and -1 for the y coordinate.
     */
    UP(0, -1),
    /**
     * Direction.RIGHT means that the difference between the coordinates of the two
     * positions is 1 for the x coordinate and 0 for the y coordinate.
     */
    RIGHT(1, 0),
    /**
     * Direction.DOWN means that the difference between the coordinates of the two
     * positions is 0 for the x coordinate and 1 for the y coordinate.
     */
    DOWN(0, 1),
    /**
     * Direction.LEFT means that the difference between the coordinates of the two
     * positions is -1 for the x coordinate and 0 for the y coordinate.
     */
    LEFT(-1, 0);

    private Pair<Integer, Integer> coordinates;

    /**
     * Constructor to associate coordinates with every value of the enum.
     *
     * @param x coordinate of the direction to create
     * @param y coordinate of the direction to create
     */
    Direction(final int x, final int y) {
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
