package com.msulov.geniusje.Levels.Managers;

import android.util.Log;

import java.util.Random;

public class Cells {

    public static int[] getRandomArrayOfNumbers(int count){
        Random random = new Random();
        int[] array = new int[count];
        int number = -1;

        for (int i = 0;i<(array.length);i++){
            array[i]=i+1;
        }


        for (int i = 0; i < array.length; i++)
        {
            int swap = random.nextInt(array.length);
            number = array[swap];
            array[swap] = array[i];
            array[i] = number;

            Log.d("SHAKE",String.valueOf(array[i]));
        }

        return array;

    }




}
