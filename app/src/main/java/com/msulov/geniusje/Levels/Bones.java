package com.msulov.geniusje.Levels;

import java.util.Random;

public class Bones {

    public static int WIN_SCORE = 36;

    private static int[] facets = new int[]{
            1,2,3,4,5,0
    };

    public static int getRandomOfScore(){
        Random random = new Random();
        return facets[random.nextInt(facets.length)];
    }


    public static int whoIsFirst(){
        if(Math.random()>=0.6) return 1;
        else return 0;
    }

}
