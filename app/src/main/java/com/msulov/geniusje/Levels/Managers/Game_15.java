package com.msulov.geniusje.Levels.Managers;

import android.util.Log;

public class Game_15 {


    public static int SIZE = 3;
    private static int LENGTH = SIZE*SIZE;



    public static int[] getArray(int size){
        int[] array = new int[size*size];
        for(int i = 0 ;i<(size*size);i++){
            array[i] = i;
        }
        do{
            Memory_game.getShakedArray(array);
        }while(!checkArray(array));
        return array;

    }

    public static int[] getArrayTest(){
        return new int[]{1,2,3,4,7,5,6,0,8};
    }


    public static int[] swapInArray(int index_1,int index_2,int[] array){
        array[index_1] = array[index_1] + array[index_2];
        array[index_2] = array[index_1] - array[index_2];
        array[index_1] = array[index_1] - array[index_2];
        return array;
    }


    public static int getIndexOfEmptyElement(int index,int[] array){
        if((index>=array.length)||(index<0)) return -1;
        if(array[index] == 0) return -1;
        else{
            int[] indexes_around_index = Miner_manager.getArrayOfIndexesAroundOf(index,array,Game_15.SIZE,Game_15.SIZE);
            for(int index_around_current_index:indexes_around_index){
                if(array[index_around_current_index] == 0) return index_around_current_index;
            }
            return -1;
        }
    }

    public static boolean checkIfUserWin(int[] array){
        int max = -1;
        for(int i = 0;i<array.length;i++){
            Log.d("I IN ARRAY",array[i]+"");
            if(array[i]!=0) {
                Log.d("MAX>ARRAY[y]",max+" > "+array[i]);
                if ((max > array[i])) {
                    Log.d("IS?????","false");
                    return false;
                } else {
                    Log.d("IS??????","true");
                    max = array[i];
                }
            }
        }
        return true;
    }


    private static boolean checkArray(int[] array){
        int e = 0;
        int n = 0;
        int count = 0;
        StringBuilder s = new StringBuilder();
        for(int i = 0;i<array.length;i++) {
            if (array[i] == 0) {
                e = i / 3 + 1;
            }
            s.append(array[i]);
            for (int j = i; j < array.length; j++) {
                if (array[i] > array[j]) n++;
            }
            Log.d(array[i] + " N:", n + " ");
            count += n;
            Log.d("count", count + " ");
            n = 0;

        }
        Log.d("S",s.toString()+"");
        count+=e;
        Log.d("count result", count + " ");
        if(count % 2 == 0){
            return true;
        }else{
            return false;
        }
    }



}
