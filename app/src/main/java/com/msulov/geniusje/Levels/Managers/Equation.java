package com.msulov.geniusje.Levels.Managers;


import java.util.HashSet;
import java.util.Random;

public class Equation {

    static String getRandomSign(){
        Random random = new Random(System.currentTimeMillis());
        int sign_key = random.nextInt(4);
        switch (sign_key){
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "*";
            case 3:
                return "/";
        }
        return null;

    }

    static int getRandomNumber(int count){
        Random random = new Random(System.currentTimeMillis());
        int random_number = 0;
        while (random_number==0) {
            random_number = random.nextInt((int) (count * 1.2));
        }
        return random_number;

    }

    static String[] makeEquation(int number_1,int number_2,String sign){
        String regex = "%d%s%d";
        String equation = String.format(regex,number_1,sign,number_2);
        return new String[]{equation,String.valueOf(getAnswerOfEquation(number_1,number_2,sign))};

    }


    static int getAnswerOfEquation(int number_1, int number_2 ,String sign){
        switch (sign) {
            case "+":
                return number_1 + number_2;
            case "-":
                return number_1 - number_2;
            case "*":
                return number_1 * number_2;
            case "/":
                return number_1 / number_2;
        }
        throw new ArithmeticException();
    }

}
