package com.msulov.geniusje.Levels.Managers;

import android.util.Log;
import android.view.LayoutInflater;

import java.util.Random;

public class Memory_game {


    private static String[][] symbols_for_rhythm = {
    };

    public static int EAZY = 3, MEDIUM = 5, HARD = 6, ULTRA_HARD_SPECIAL_FOR_MURAD = 11;
    public static int HEIGHT = 6,WIDTH = 6;




    public static int[] getArrayOfNumbersForGame(int count){
        if ((count % 2) == 1){
            throw new ArithmeticException();
        }


        int[] array = new int[count];
        for (int i = 0;i<array.length;i=i+2){
            array[i] = i;
            array[i+1] = i;
        }
        return array;
    }



    public static int[] getShakedArray(int[] array){
        int swap,number;
        for (int i = 0; i < array.length; i++)
        {
            Random random = new Random();
            swap = random.nextInt(array.length);
            number = array[swap];
//            Log.d("NUMBER",String.valueOf(number));
            array[swap] = array[i];
//            Log.d("ARRAY SWAP", String.valueOf(array[swap]));
            array[i] = number;

            Log.d("SHAKE",String.valueOf(array[i]));
        }

        return array;
    }
    public static String[] getShakedArray(String[] array){
        int swap;
        String word;

        for (int i = 0; i < array.length; i++)
        {
            Random random = new Random();
            swap = random.nextInt(i+1);
            word = array[swap];
//            Log.d("NUMBER",String.valueOf(number));
            array[swap] = array[i];
//            Log.d("ARRAY SWAP", String.valueOf(array[swap]));
            array[i] = word;

            Log.d("SHAKE",String.valueOf(array[i]));
        }

        return array;
    }

    public static char[] getShakedArray(char[] array){
        int swap;
        char letter;

        for (int i = 0; i < array.length; i++)
        {
            Random random = new Random();
            swap = random.nextInt(i+1);
            letter = array[swap];
//            Log.d("NUMBER",String.valueOf(number));
            array[swap] = array[i];
//            Log.d("ARRAY SWAP", String.valueOf(array[swap]));
            array[i] = letter;

            Log.d("SHAKE",String.valueOf(array[i]));
        }

        return array;
    }



    @Deprecated
    public static int[][] getRandomPairsForIndexes(int diffucult) {
        int[][] pairs_of_indexes = new int[diffucult][2];
        Random random = new Random();
        for (int i = 0; i < diffucult; i++) {
            boolean needExit = true;
            int x = -1;
            int y = -1;
            x = random.nextInt(WIDTH);
            y = random.nextInt(HEIGHT);
            Log.d("X", String.valueOf(x));
            Log.d("Y", String.valueOf(y));
            pairs_of_indexes[i][0] = x;
            pairs_of_indexes[i][1] = y;
        }
        return pairs_of_indexes;

    }

    public static int[][] getRandomPairsOfIndexes(int diffucult){

        int[][] pairs_of_indexes = new int[diffucult][2];
        int[] coord_Y = new int[HEIGHT];
        int[] coord_X = new int[WIDTH];
        Random random = new Random();

        for (int i = 0;i<HEIGHT;i++){
            coord_Y[i] = i;
        }
        for (int i = 0;i<WIDTH;i++){
            coord_X[i] = i;
        }
        getShakedArray(coord_X);
        getShakedArray(coord_Y);
        for(int i = 0;i<diffucult;i++){
            int x = coord_X[random.nextInt(coord_X.length)];
            int y = coord_Y[i];
            pairs_of_indexes[i][1] = y;
            pairs_of_indexes[i][0] = x;
            Log.d("X",String.valueOf(x));
            Log.d("Y",String.valueOf(y));
        }
        return pairs_of_indexes;
    }

    public static int[][] getRandomPairsOfIndexes(int diffucult,int height,int width){

        int[][] pairs_of_indexes = new int[diffucult][2];
        int[] coord_Y = new int[height];
        int[] coord_X = new int[width];
        Random random = new Random();

        for (int i = 0;i<height;i++){
            coord_Y[i] = i;
        }
        for (int i = 0;i<width;i++){
            coord_X[i] = i;
        }
        getShakedArray(coord_X);
        getShakedArray(coord_Y);
        for(int i = 0;i<diffucult;i++){
            int x = coord_X[i];
            int y = coord_Y[i];
            pairs_of_indexes[i][1] = y;
            pairs_of_indexes[i][0] = x;
            Log.d("X",String.valueOf(x));
            Log.d("Y",String.valueOf(y));
        }
        return pairs_of_indexes;
    }



}
