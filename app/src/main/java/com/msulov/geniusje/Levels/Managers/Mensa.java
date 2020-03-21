package com.msulov.geniusje.Levels.Managers;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.msulov.geniusje.Logging.Logging;
import com.msulov.geniusje.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Mensa {

    private int score = 0, current_question = 0;
    private ArrayList<String> skipped_tasks = new ArrayList<>();

    private static String[] answers = new String[]{
            "A","E","F","F","D",
            "E","E","C","D","D",
            "A","A","B","A","F",
            "B","D","C","A","A",
            "B","E","F","F","E",
            "F","A","A","C","E",
            "D","E","E","A","D"
    };

    public Mensa(){
        for(int i = 0 ;i < COUNT;i++) this.skipped_tasks.add(String.valueOf(i));
    }

    public static int COUNT = answers.length;


    public String[] getSkippedTasks(){return this.skipped_tasks.toArray(new String[0]);}



    public int getScore(){return this.score;}

    public void incScore(){this.score++;}

    public void setCurrent_question(int current_question){ this.current_question = current_question;}

    public int getCurrent_question(){ return this.current_question;}

    public void incCurrentQuestion(){ this.current_question++;}

    public String getCorrectAnswer(int number_of_question){return answers[number_of_question];}

    public static Drawable[] getTaskInfo(int number_question, Context context) {
        number_question++;
        Drawable[] task_and_answers = new Drawable[7];
        String folder = String.format("matrise-%s",number_question);
        for(int i = 0;i < 7;i++) {
            String filepath = null;
            if(i == 0) {filepath = String.format("%s/%s.png",folder,folder);}
            else{filepath = String.format("%s/%s",folder,String.format("answer-%s-%s.png",number_question,i));}
            InputStream ip = null;
            try {
                ip = context.getAssets().open(filepath);
            } catch (IOException e) {
                task_and_answers[i] = context.getDrawable(R.drawable.s);
                Logging.log("ERROR getTaskInfo", 1);
                Logging.log("ERROR getTaskInfo", e.getLocalizedMessage());
                continue;
            }
            task_and_answers[i] = Drawable.createFromStream(ip,null);
        }
        return task_and_answers;
    }

    public void popSkippedQuestion(int number_question){
        this.skipped_tasks.remove(String.valueOf(number_question));
        Logging.log("tasks",this.skipped_tasks.toString());
    }

    public void pushSkippedTaskIntoEnd(int number_of_question){
        skipped_tasks.remove(String.valueOf(number_of_question));
        skipped_tasks.add(String.valueOf(number_of_question));
    }

    public boolean ifTaskContainsInSkippedTasks(int number_of_question){
        return this.skipped_tasks.contains(String.valueOf(number_of_question));
    }

    public int getSkippedQuestion(){
        if(this.skipped_tasks.size() == 0) return -1;
//        for(int i = getCurrent_question();i < this.skipped_tasks.size();i++){
//            if(this.skipped_tasks.get(i)!=null) return Integer.parseInt(this.skipped_tasks.get(i));
//        }
        for(int i = 0;i < this.skipped_tasks.size();i++){
            if(this.skipped_tasks.get(i)!=null) return Integer.parseInt(this.skipped_tasks.get(i));
        }
        return -1;
    }

//    public Drawable[] getTaskInfo(int number_question){
//        Drawable[] task_and_answers = new Drawable[7];
//    }
}
