package com.msulov.geniusje.Levels.Managers;

import android.util.Log;

public class Find_word {
    private static String[][] words = {
            {"наборщик","борщ","банк","нора","икра","кора","роща","краб","кино","брак","кран"},
            {"программный","гной","поры","нора","ранг","рога","йога","морг","рано","найм","пары",},
            {"аллигатор","рога","торг","тигр","лига","тара","литр","игра","игла","грот","трио",},
            {"когтистый","гост","итог","коты","киты","соты","скот","стык","сток","тост","стог",},
            {"волнистый","вино","винт","стол","стон","твой","сито","иной","ноты","лист","ионы",},
    };

    private  static int[][] koef_is_found = {
            {0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,},
    };


    public static int COUNT = words.length;



    public static String[] getWords(int index){
        return words[index];
    }

    private static int[] getKoefIsFound(int index){
        return koef_is_found[index];
    }

    public static boolean isExistsAndNotFound(int index,String word){
        boolean hasFounded = false;
        Log.d("length",String.valueOf(words[index].length));
        Log.d("word",word);
        for (int i = 1 ;i<words[index].length-1;i++){
                        Log.d("words",words[index][i]);
            Log.d("koef",String.valueOf(koef_is_found[index][i]));
            if((words[index][i].equals(word))&&(koef_is_found[index][i]==0)){
                hasFounded = true;
                Log.d("SUCK ","1");
                return hasFounded;
            }
        }
        return hasFounded;
    }


    public static void setKoefhasFounded(int index,String word){
        for (int i = 0 ;i<words[index].length;i++){
            if(words[index][i].equals(word)) koef_is_found[index][i]=1;
        }
    }


}
