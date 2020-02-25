package com.msulov.geniusje.Levels.Managers;

import java.util.Random;

public class Questions {


    public final static int TYPE_MATH = 0,TYPE_CHEMIC = 1,TYPE_P = 2;

    private static String[][] questions = {
            {
                "Что такое биссектриса?",
                    "Продолжите ряд: делитель , делимое , ?",
                    "Формула дискременанта:",
                    "Синус - это",
                    "Теорема косинусов",
                    "Теорема Пифагора",
                    "Промежуток [1,4] - это",
                    "Сумма углов в треугольнике",
                    "Основание пирамиды",
                    "Острый угол"
            }
    };

    private static String[][][] answers = {
            {
                    {"Крыса , бегущая по углам","Окружность","Число , с плавающей точкой","Луч, делющий угол пополам"},
                    {"Кенгуру","Причастное","Причастие","Частное"},
                    {"a^2+b^2","b^2+4ac","d=abc","b^2-4ac"},
                    {"отношение гипотенузы к прилежащему катету","отношение противолежащего катета к гипотенузе","отношение противолежащего катека к прилжащему","отношение прилежащего катета к противолежащего"},
                    {"a^2+b^2+c^2","b^2+4ac","a^2+b^2+c^2-abc","a^2+b^2-2abcos(A)"},
                    {"b^2+4ac","b^2-4ac","V=abc","a^2+b^2"},
                    {"1,2,3","2,3","2,3,4","1,2,3,4"},
                    {"270","90","360","180"},
                    {"Прямоугольник","Круг","Параллелограмм","Квадрат"},
                    {"<45","<=45","<90",">=90"}
            }

    };



    public static String[][] getRandomData(int type){
        Random random = new Random();
        String[][] data;
        switch (type){
            case TYPE_MATH:
                int number = random.nextInt(answers[TYPE_MATH].length);
                String[] answers = getAnswers(TYPE_MATH,number);
                String answer = answers[answers.length-1];
                answers = Memory_game.getShakedArray(answers);
                data = new String[][]{{getQuestion(TYPE_MATH, number)}, answers, {Shaked_words.getIndexOfRandomWord(answer,answers)}};
                return data;

        }
        return null;
    }


    private static String[] getAnswers(int type,int number_of_question){
        switch (type){
            case TYPE_MATH:
                return answers[TYPE_MATH][number_of_question];
        }
        return null;
    }


    private static String getQuestion(int type, int number_of_question){
        switch (type){
            case TYPE_MATH:
                return questions[TYPE_MATH][number_of_question];
        }
        return null;
    }



}
