package it.unibo.caesena.utils;

import java.util.Optional;

public enum Direction {
    UP(0,-1), 
    RIGHT(1,0), 
    DOWN(0,1), 
    LEFT(-1,0);
		
    int x;
    int y;
    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public static boolean match(Direction direction, Pair<Integer, Integer> p1, Pair<Integer, Integer> p2)
    {
        return p1.getX()+direction.getX() == p2.getX() &&
            p1.getY()+direction.getY() == p2.getY();
    }
}
