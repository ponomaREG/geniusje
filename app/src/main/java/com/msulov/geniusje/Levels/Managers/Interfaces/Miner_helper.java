package com.msulov.geniusje.Levels.Managers.Interfaces;

public interface Miner_helper {
    int[][] getRandomCoordOfBombs(int diffucult);
    int[][] getCoordOfCellsAroundCell(int x, int y,int width,int height);

}
