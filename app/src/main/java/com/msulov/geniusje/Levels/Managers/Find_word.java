package com.msulov.geniusje.Levels.Managers;

public class Find_word {
    private static String[][] words = {
            {"наборщик","борщ","рок","нора","икра","кора","икона","краб","кино","брак","кран"},
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
        for (int i = 0 ;i<words[index].length;i++){
            if((words[index][i].equals(word))&&(koef_is_found[index][i]==1)){
                hasFounded = true;
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
