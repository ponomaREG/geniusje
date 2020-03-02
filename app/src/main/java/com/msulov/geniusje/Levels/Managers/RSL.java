package com.msulov.geniusje.Levels.Managers;

import com.msulov.geniusje.Levels.Managers.Interfaces.RSL_helper;

import java.util.Random;

public class RSL implements RSL_helper {


    @Override
    public int getRandomFigureForBot() {
        Random random = new Random();



        return 0;
    }

    @Override
    public boolean checkIsUserWin(int user_choice, int bot_choice) {
        return false;
    }
}
