package com.msulov.geniusje.Levels.Managers;

import java.util.Random;

public class Sudoku {

    public static int HEIGHT = 9;

    public static int EAZY = 3;
    public static int MEDIUM = 5;
    public static int HARD = 7;


    public static int[][] getSudoku() {
        int[] line = getRandomSetOfNineNumbers();
        int[][] data = new int[9][9];
        int count = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                line = moveToThreePositions(line);
                count++;
                for (int n = 0; n < 9; n++) {
                    data[count][n] = line[n];
                }
            }
            line = moveSetOfThreeNumbersToRight(line);
        }

        return data;
    }




    private static int[] getRandomSetOfNineNumbers() {
        Random random = new Random();
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int number, index_2;
        for (int i = 0; i < 9; i++) {
            index_2 = random.nextInt(9);
            number = data[index_2];
            data[index_2] = data[i];
            data[i] = number;
        }
        return data;
    }

    private static int[] moveToThreePositions(int[] set) {
        int number_1, number_2, number_3;
        set[3] = set[3] + set[0];
        set[0] = set[3] - set[0];
        set[3] = set[3] - set[0];

        set[4] = set[4] + set[1];
        set[1] = set[4] - set[1];
        set[4] = set[4] - set[1];


        set[5] = set[5] + set[2];
        set[2] = set[5] - set[2];
        set[5] = set[5] - set[2];

        set[6] = set[6] + set[0];
        set[0] = set[6] - set[0];
        set[6] = set[6] - set[0];

        set[7] = set[7] + set[1];
        set[1] = set[7] - set[1];
        set[7] = set[7] - set[1];


        set[8] = set[8] + set[2];
        set[2] = set[8] - set[2];
        set[8] = set[8] - set[2];


        return set;
    }


    private static int[] moveSetOfThreeNumbersToRight(int[] set) {
        for (int i = 0; i < 9; i = i + 3) {
            set[i + 2] = set[i + 2] + set[i];
            set[i] = set[i + 2] - set[i];
            set[i + 2] = set[i + 2] - set[i];

            set[i + 2] = set[i + 2] + set[i + 1];
            set[i + 1] = set[i + 2] - set[i + 1];
            set[i + 2] = set[i + 2] - set[i + 1];
        }
        return set;
    }



    public static boolean checkUserChoiceForCorrect(int[][] data,int x,int y,int user_choice){
        if(data[y][x]==user_choice) return true;
        return false;
    }

}
