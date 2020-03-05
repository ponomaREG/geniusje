package com.msulov.geniusje.Levels.Managers;

import com.msulov.geniusje.Levels.Managers.Interfaces.Miner_helper;
import com.msulov.geniusje.Levels.Miner;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Miner_manager{

    public static final int HEIGHT = 12,WIDTH = 12, EAZY = 14,MEDIUM = 17,HARD = 20;


    public static int[][] getRandomCoordOfBombs(int diffucult) {
        return Memory_game.getRandomPairsOfIndexes(diffucult,HEIGHT,WIDTH);
    }

    public static int[][] getCoordOfCellsAroundCell(int x, int y, int width, int height) {
        int[][] coords = null;
        if((x-1>=0)&&(y-1>=0)&&(x<=width-2)&&(y<=height-2)){
            //000
            //010
            //000
            coords = new int[][]{
                    {x+1,y-1},
                    {x+1,y},
                    {x+1,y+1},
                    {x,y-1},
                    {x,y+1},
                    {x-1,y-1},
                    {x-1,y},
                    {x-1,y+1},

            };

        }
        else{
            if(x==width-1){
                if((y==0)){
                    coords = new int[][]{
                            {x,y+1},
                            {x-1,y+1},
                            {x-1,y}
                    };
                }
                else if(y==height-1){
                    coords = new int[][]{
                            {x,y-1},
                            {x-1,y-1},
                            {x-1,y}
                    };
                }
                else{
                    coords = new int[][]{
                            {x-1,y-1},
                            {x-1,y},
                            {x-1,y+1},
                            {x,y-1},
                            {x,y+1},
                    };
                }
            }

            else if((x==0)){
                if((y==0)){
                    coords = new int[][]{
                            {x,y+1},
                            {x+1,y+1},
                            {x+1,y}
                    };
                }
                else if(y==height-1){
                    coords = new int[][]{
                            {x,y-1},
                            {x+1,y-1},
                            {x+1,y}
                    };
                }
                else{
                    coords = new int[][]{
                            {x+1,y-1},
                            {x+1,y},
                            {x+1,y+1},
                            {x,y-1},
                            {x,y+1},
                    };
                }
            }else if(y==0){
                coords = new int[][]{
                        {x-1,y},
                        {x-1,y+1},
                        {x,y+1},
                        {x+1,y},
                        {x+1,y+1}
                };
            }else if(y==height-1){
                coords = new int[][]{
                        {x-1,y},
                        {x-1,y-1},
                        {x,y-1},
                        {x+1,y},
                        {x+1,y-1}
                };
            }
        }
        return coords;
    }



    public static boolean isXandYinARRAY(int[][] array,int x, int y){
        for(int i = 0;i<array.length;i++){
            if((array[i][0])==x&&(array[i][1]==y)) return true;
        }
        return false;
    }
}
