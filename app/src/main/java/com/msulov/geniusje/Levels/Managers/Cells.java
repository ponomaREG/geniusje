package com.msulov.geniusje.Levels.Managers;

import android.util.Log;

import java.util.Random;

public class Cells {

    public static int[] getRandomArrayOfNumbers(int count){
        Random random = new Random();
        int[] array = new int[count];

        for (int i = 0;i<(array.length);i++){
            array[i]=i+1;

        }


        for (int i = 0; i < array.length; i++)
        {
            int swap = random.nextInt(i+1);
            int number = array[swap];
//            Log.d("NUMBER",String.valueOf(number));
            array[swap] = array[i];
//            Log.d("ARRAY SWAP", String.valueOf(array[swap]));
            array[i] = number;

            Log.d("SHAKE",String.valueOf(array[i]));
        }

        for (int i = 0;i<array.length;i++){
            Log.d("ARRAY",String.valueOf(array[i]));
        }
        return array;

    }




}
