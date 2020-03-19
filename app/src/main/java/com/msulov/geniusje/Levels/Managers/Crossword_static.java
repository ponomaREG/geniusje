package com.msulov.geniusje.Levels.Managers;

public class Crossword_static {

    private static String[][] keyword = new String[][]{
            {"стропило","Вопрос 1"},
            {"ми","Вопрос 2"},//+
            {"брак","Вопрос 3"},
            {"бытие","Вопрос 4"},
            {"акт","Вопрос 5"},
            {"зародыш","Вопрос 6"},//+
            {"библиотека","Вопрос 7"},
            {"анод","Вопрос 8"},

    };

    private static int[][][] coords_cells = new int[][][]{
            {{1,4},{2,4},{3,4},{4,4},{5,4},{6,4},{7,4},{8,4}},
            {{5,1},{6,1}},
            {{6,2},{7,2},{8,2},{9,2}},
            {{2,7},{3,7},{4,7},{5,7},{6,7}},
            {{6,9},{7,9},{8,9}},
            {{3,2},{3,3},{3,4},{3,5},{3,6},{3,7},{3,8}},
            {{6,0},{6,1},{6,2},{6,3},{6,4},{6,5},{6,6},{6,7},{6,8},{6,9}},
            {{8,2},{8,3},{8,4},{8,5}},
    };


    public static int[][][] getCoordsOfCells(){
        return coords_cells;
    }

    public static String getWordFromKeyword(int index_of_word){
        return keyword[index_of_word][0];
    }


    public static String getQuestionFromKeyword(int index_of_word){
        return keyword[index_of_word][1];
    }

}
