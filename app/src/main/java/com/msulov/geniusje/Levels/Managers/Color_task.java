package com.msulov.geniusje.Levels.Managers;

import android.graphics.Color;

import com.msulov.geniusje.R;

import java.util.Random;

public class Color_task {

    public static int getRandomColor(){
        Random random = new Random();
        int random_color = random.nextInt(5);
        switch (random_color){
            case 0:
                return Color.BLACK;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.RED;
            case 3:
                return Color.GRAY;
            case 4:
                return Color.GREEN;
            default:
                return -1;

        }


    }


    public static String getRandomTextOfColor(){
        Random random = new Random();
        int random_text = random.nextInt(5);
        switch (random_text){
            case 0:
                return "черный";
            case 1:
                return "синий";
            case 2:
                return "красный";
            case 3:
                return "серый";
            case 4:
                return "зеленый";
            default:
                return null;

        }
    }

    public static String getTextOfColor(int color_id){
        switch (color_id){
            case Color.BLACK:
                return "черный";
            case Color.BLUE:
                return "синий";
            case Color.RED:
                return "красный";
            case Color.GRAY:
                return "серый";
            case Color.GREEN:
                return "зеленый";
            default:
                return null;

        }
    }



    public static int getColorOfText(String text_of_color){
        switch (text_of_color){
            case "черный":
                return Color.BLACK;
            case "синий":
                return Color.BLUE;
            case "красный":
                return Color.RED;
            case "серый":
                return Color.GRAY;
            case "зеленый":
                return Color.GREEN;
            default:
                return -1;
        }
    }



}
