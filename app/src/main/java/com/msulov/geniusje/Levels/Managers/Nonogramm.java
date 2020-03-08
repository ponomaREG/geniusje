package com.msulov.geniusje.Levels.Managers;

import android.util.Log;

import java.util.ArrayList;

public class Nonogramm {


    public static int HEIGHT = 12,WIDTH = 12, DIFFICULT = 110;




    public static int[][] getRandomCellsNumber(int difficult,int height,int width){
        return Memory_game.getRandomPairsOfIndexes(difficult,height,width);
    }




    public static int[][] getCountOfCellsCheckedInRow(int[][] array,int height,int width){
        int[][] count_cells=new int[height][(width/2)+1];
        int count = 0;
        for(int y = 0;y<height;y++){
            for(int x = 0;x<width;x++){
                if(Miner_manager.isXandYinARRAY(array,x,y)) {
                    count++;
                    if(x==width-1) loadInArray(count_cells,y,count);
                }
                else{
                    if(count!=0) {
                        loadInArray(count_cells,y,count);
                        count = 0;
                    }
                }
            }
            count = 0;
        }
        return count_cells;
    }


    private static void loadInArray(int[][] count_cells,int y,int count){
        for (int i = 0; i < count_cells[y].length; i++) {
            if (count_cells[y][i] == 0) {
                count_cells[y][i] = count;
                break;
            }
        }
    }

    public static int[][] getCountOfCellsCheckedInColumn(int[][] array,int height,int width){
        int[][] count_cells=new int[width][height/2+1];

        int count = 0;
        for(int x = 0;x<width;x++){
            for(int y = 0;y<height;y++){
                if(Miner_manager.isXandYinARRAY(array,x,y)) {
                    count++;
                    if(y==height-1) loadInArray(count_cells,x,count);
                }
                else{
                    if(count!=0) {
                        loadInArray(count_cells,x,count);
                        count = 0;
                    }
                }
            }
            count = 0;
        }
        return count_cells;
    }



    public static  void masterlog(int[][] count_of_cells){
        for(int i = 0;i<count_of_cells.length;i++){
            Log.d("Y",i+"");
            StringBuilder s = new StringBuilder();
            for(int j = 0;j<count_of_cells[i].length;j++){
                if(count_of_cells[i][j]!=0){
                    s.append(count_of_cells[i][j]).append(" ");
                }
            }
            Log.d("X",s.toString());
        }
    }

    public static void masterlogcolumn(int[][] count_of_cells){
        for(int x = 0;x<count_of_cells.length;x++){
            Log.d("X",x+"");
            StringBuilder s = new StringBuilder();
            for(int y = 0;y<count_of_cells[x].length;y++){
                if(count_of_cells[x][y]!=0){
                    s.append(count_of_cells[x][y]).append(" ");
                }
            }
            Log.d("Y",s.toString());
        }
    }

}
