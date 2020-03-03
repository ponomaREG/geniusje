package com.msulov.geniusje.Levels.Managers;

import com.msulov.geniusje.Levels.Managers.Interfaces.Miner_helper;
import com.msulov.geniusje.Levels.Miner;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Miner_manager implements Miner_helper {

    public static final int HEIGHT = 15,WIDTH = 15;


    @Override
    public int[][] getRandomCoordOfBombs(int diffucult) {
        return Memory_game.getRandomPairsOfIndexes(diffucult,HEIGHT,WIDTH);
    }

    @Override
    public int[][] getCoordOfCellsAroundCell(int x, int y, int width, int height) {
        return new int[0][];
    }
}
