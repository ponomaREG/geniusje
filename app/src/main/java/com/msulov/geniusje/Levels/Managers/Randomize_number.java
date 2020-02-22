package com.msulov.geniusje.Levels.Managers;


import java.util.Random;

public class Randomize_number {

    public static int HEIGHT = 15;
    public static int WIDTH = 10;

    public static int TEXT_SIZE = 40;

    private final static String[][] pairs = {{"0","o"},{"1","I"},{"8","B"}};


    public static String[] getRandomNumbers(){
        Random random = new Random();
        int pair = random.nextInt(pairs.length);
        return pairs[pair];
    }

    public static int[] getRandomCoordOfCell(){
        Random random = new Random();
        int[] coord = new int[2];
        coord[0] = random.nextInt(WIDTH);
        coord[1] = random.nextInt(HEIGHT);
        return coord;
    }

}
