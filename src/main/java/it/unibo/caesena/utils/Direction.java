package it.unibo.caesena.utils;

public enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);

    private int x;
    private int y;

    Direction(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 
     * @return value of x
     */
    public int getX() {
        return this.x;
    }

    /**
     * 
     * @return value of y
     */
    public int getY() {
        return this.y;
    }

    /**
     * checks whether two positions coincide with the same direction
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
