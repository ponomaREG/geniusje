package com.msulov.geniusje.Levels.Managers;

import com.msulov.geniusje.Levels.Managers.Interfaces.RSL_helper;

import java.util.Random;

public class RSL implements RSL_helper {


    public static final int POINTS = 3;

    @Override
    public int getRandomFigureForBot() {
        Random random = new Random();
        int figure = random.nextInt(3);
        return figure;
    }

    @Override
    public int checkIsUserWin(int user_choice, int bot_choice) {
        if(user_choice == 0){
            if(bot_choice == 1) return 1;
            else if(bot_choice == 2) return 0;
            else return -1;
        }else if(user_choice == 1){
            if(bot_choice == 2) return 1;
            else if(bot_choice == 0) return 0;
            else return -1;
        }else if(user_choice == 2){
            if(bot_choice == 0) return 1;
            else if(bot_choice == 1) return 0;
            else return -1;
        }


        return -1;
    }
}
