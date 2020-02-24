package com.msulov.geniusje.Levels.Managers;

import android.util.Log;

import java.util.Random;

public class Memory_game {


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
            swap = random.nextInt(i+1);
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


}
