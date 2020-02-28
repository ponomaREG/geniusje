package com.msulov.geniusje.Levels.Managers;

import android.util.Log;

import java.util.Random;

public class Shaked_words {



    private static String[][] keywords =
            {
                    {"стол","табуретка","стул","кровать","шкаф","кресло"},
                    {"ноутбук","смартфон","планшет","телефон","холодильник","приставка"},
                    {"черный","синий","белый","серый","красный","зеленый"},
                    {"джава","котлин","свифт","си","питон","руби"},
                    {"сигарета","кальян","трубка","вейп","сигара","самокрутка"},
                    {"алгебра","химия","информатика","физика","биология","геометрия"},
                    {"франция","англия","литва","германия","украина","америка"},
                    {"железо","кобальт","ртуть","алюминий","свинец","вольфрам"},
                    {"бриллиант","рубин","изумруд","янтарь","сапфир","гранат"},
                    {"плов","пельмени","голубцы","борщ","карбонара","пицца"}

            };


    private static String[] themes=
            {
                    "мебель",
                    "техника",
                    "цвета",
                    "языки программирования",
                    "табакокурение",
                    "школьные предметы",
                    "страны",
                    "металлы",
                    "драгоценные камни",
                    "еда"
            };





    public String[][] getInfoForTask(int count,boolean getShaked){

        Random random = new Random();
        int current_group = random.nextInt(keywords.length);

        String[] words = getManyWordsFromRandomGroup(current_group,count-1);
        String random_word = getRandomWordFromRandomGroup(current_group);
        words[count-1] = random_word;

        String[][] returnement_array = {{getTitleOfGroup(current_group)}, Memory_game.getShakedArray(words),{getIndexOfRandomWord(random_word,words)}};

        if(getShaked){
            return shuffleWordsIn(returnement_array);
        }
        return returnement_array;

    }


    private String[][] shuffleWordsIn(String[][] array){
        for(int i = 0;i<array[1].length;i++){
            array[1][i] = shakeWord(array[1][i]);
        }
        return  array;
    }


    public static String getIndexOfRandomWord(String word,String[] array){
        Log.d("WORD",word);
        for (int i = 0;i<array.length;i++){
            Log.d("ARRAY WORD",array[i]);
            Log.d("INDEX","1");
            if(array[i].equals(word)){
                return String.valueOf(i);
            }
        }
        Log.d("INDEX","0");
        return null;
    }


    private String getRandomWordFromRandomGroup(int index_of_current_group){
        Random random = new Random();
        int random_group = random.nextInt(keywords.length);
        while(random_group==index_of_current_group){
            random_group = random.nextInt(keywords.length);
        }
        return keywords[random_group][random.nextInt(keywords[random_group].length)];
    }




    private String[] getManyWordsFromRandomGroup(int current_group,int count){
        Random random = new Random();

        int[] indexes;

        String[] words;


        try {
            indexes = getRandomIndexes(count,current_group);
        } catch (Exception e) {
            Log.d("ERROR",e.getStackTrace().toString());
            return null;
        }

        words = new String[count+1];
        for (int i = 0;i<count;i++){
            words[i] = keywords[current_group][indexes[i]];
        }
        return words;

    }



    private int[] getRandomIndexes(int count,int current_group) throws Exception {
        Random random = new Random();

        if((count > keywords[current_group].length)||(count == 0)||(keywords[current_group].length==0)){
            throw new Exception();
        }else{
            int[] indexes = new int[count];
            int[] full_indexes = new int[keywords[current_group].length];
            for(int i = 0;i<keywords[current_group].length;i++){
                full_indexes[i] = i;
            }
            Memory_game.getShakedArray(full_indexes);
            for (int i =0;i<count;i++){
                indexes[i] = full_indexes[i];
            }
            return indexes;
        }

    }


    public static int[] getRandomIndexes(int count,int count_all,boolean isRepeated){
        int[] indexes = new int[count];
        int[] full_indexes = new int[count_all];

        for (int i = 0;i<count_all;i++){
            full_indexes[i] = i;
        }
        Memory_game.getShakedArray(full_indexes);

        for(int i = 0;i<count;i++){
            indexes[i] = full_indexes[i];
        }
        return indexes;
    }


    private String getTitleOfGroup(int current_group){
        return themes[current_group];
    }




    private String shakeWord(String word){
        StringBuilder new_word = new StringBuilder();
        char[] char_word_array = Memory_game.getShakedArray(word.toCharArray());
        for (char let:char_word_array){
            new_word.append(let);
        }
        return new_word.toString();
    }

}
