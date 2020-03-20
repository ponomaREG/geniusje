package com.msulov.geniusje.Levels.Managers;

public class Arithmetic_cells {

    public static int HEIGHT = 7, WIDTH = 7 , BEGIN_NUMBER = 1,END_NUMBER = 9;

    public static String[] tasks = new String[]{
            "7+8/3=5",
            "+#x#+#-",
            "5+3/2=4",
            "/#/#-#+",
            "3+6/3=3",
            "=#=#=#=",
            "4+4/2=4",
    };


    public static String getTask(int index){return tasks[index];}
    public static String getSymbol(int index,int symbol_index){return String.valueOf(tasks[index].charAt(symbol_index));}


    public static int makeOperation(int result_1,String symbol,int result_2){
        return Equation.getAnswerOfEquation(result_1,result_2,symbol);
    }

}
