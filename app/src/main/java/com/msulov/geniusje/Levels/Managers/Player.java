package com.msulov.geniusje.Levels.Managers;

public class Player {

    private int currentScore = 0 , varScores = 0, countThrows = 0;


    public void setCurrentScore(int score){
        this.currentScore = score;
    }

    public int getCurrentScore(){
        return this.currentScore;
    }

    public void incCurrentScore(int inc){
        this.currentScore += inc;
    }

    public void incVarScores(int inc){
        this.varScores += inc;
    }

    public int getVarScores(){
        return this.varScores;
    }

    public void clearVarScores(){
        this.varScores = 0;
    }

    public void incCountThrows(int inc){
        this.countThrows+=inc;
    }

    public int getCountThrows(){
        return this.countThrows;
    }

    public void clearCountThrows(){
        this.countThrows = 0;
    }

}
