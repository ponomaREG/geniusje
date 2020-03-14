package com.msulov.geniusje.Levels.Managers;

public class Horse_check {

    public static int WIDTH = 5, HEIGHT = 5,BEGIN_X = 0,BEGIN_Y = 0;

    public static int[][] getIndexesForHorse(int x,int y){
        int[][] indexes = new int[][]{
                {x+1,y+2},
                {x-1,y+2},
                {x+1,y-2},
                {x-1,y-2},
                {x+2,y+1},
                {x-2,y-1},
                {x+2,y-1},
                {x-2,y+1},
        };
        return indexes;
    }


}
