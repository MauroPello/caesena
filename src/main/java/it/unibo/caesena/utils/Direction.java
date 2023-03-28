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

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public static boolean match(final Direction direction, final Pair<Integer, Integer> p1,
            final Pair<Integer, Integer> p2) {
        return p1.getX() + direction.getX() == p2.getX() &&
                p1.getY() + direction.getY() == p2.getY();
    }
}
