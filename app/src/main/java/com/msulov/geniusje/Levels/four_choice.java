package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.DBHelper;
import com.msulov.geniusje.Levels.Managers.Questions;
import com.msulov.geniusje.Levels.Managers.Shaked_words;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.Logging.Logging;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class four_choice extends AppCompatActivity {


    private Toast toast;
    private Dialog dialog;
    private TextView desc;
    private Time t;
    private long pressedTime;
    private int count, correctAnswer,  mistakes = 0;
    private String type,level;
    private int taskdesc_id;
    private boolean isWin = true;

    private final int COUNT = 4;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four_choice);

        Intent intent = getIntent();

        type = intent.getStringExtra("type");
        if(type == null) {
            type = "Shaked_words";
            level = "12";
            taskdesc_id = R.string.startDialogWindowForLevel_12;
        }
        else if((type.equals("Shaked_words"))){
            type = "Shaked_words";
            level = "12";
            taskdesc_id = R.string.startDialogWindowForLevel_12;
        }
        else if((type.equals("Question_1"))){
            type = "Question_1";
            level = "13";
            taskdesc_id = R.string.startDialogWindowForLevel_13;
        }
        else if((type.equals("Question_2"))){
            type = "Question_2";
            level = "14";
            taskdesc_id = R.string.startDialogWindowForLevel_14;
        }
        else if((type.equals("Question_3"))){
            type = "Question_3";
            level = "15";
            taskdesc_id = R.string.startDialogWindowForLevel_15;
        }else{
            type = "Shaked_words";
            level = "12";
            taskdesc_id = R.string.startDialogWindowForLevel_12;
        }

        init();
        showBeginningDialog();
        initContAndBackButtons();
        makeTask(type);
        initOclForAnswers();
    }








    /////BEGINNIG AND RESULTING CONSTANT BLOCKS
    private void showBeginningDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog);
        setIconAndTask();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    private void setIconAndTask() {
        TextView task = dialog.findViewById(R.id.dialogTask);
        task.setText(getResources().getString(taskdesc_id));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.icon));
        TextView level_name = findViewById(R.id.level);
        level_name.setText(String.format("%s %s",level,getResources().getString(R.string.level)));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                startActivity(new Intent(four_choice.this, LevelsActivity.class));
                finish();
            } else if (v.getId() == R.id.startDialogButton) {
                dialog.dismiss();
                new Thread(t, "Time").start();
            }
        };
        Button startButton = dialog.findViewById(R.id.startDialogButton);
        startButton.setOnClickListener(OnClickListener);
        Button backButton = dialog.findViewById(R.id.backDialogButton);
        backButton.setOnClickListener(OnClickListener);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(OnClickListener);
    }
//BEGIN BLOCK END

    //TASK BLOCK


    private void init(){
        t = new Time();
        desc = findViewById(R.id.desc);
    }


    private void makeTask(String type){
        String[][] data;
        switch (type){
            case ("Shaked_words"):
                String[][] words = (new Shaked_words()).getInfoForTask(COUNT,true);
                desc.setText(words[0][0]);
                setCellsTo(words[1],Integer.parseInt(words[2][0]));
                break;
            case ("Question_1"):
                data = Questions.getRandomData(Questions.TYPE_MATH);
                desc.setText(data[0][0]);
                setCellsTo(data[1], Integer.parseInt(data[2][0]));
                break;
            case ("Question_2"):
                data = Questions.getRandomData(Questions.TYPE_CHEMIC);
                desc.setText(data[0][0]);
                setCellsTo(data[1], Integer.parseInt(data[2][0]));
                break;
            case ("Question_3"):
                data = Questions.getRandomData(Questions.TYPE_INF);
                desc.setText(data[0][0]);
                setCellsTo(data[1], Integer.parseInt(data[2][0]));
                break;

        }
    }



    private void initOclForAnswers(){
        count = 0;
        correctAnswer = 0;
        View.OnClickListener ocl;
        if ("Shaked_words".equals(type)) {
            ocl = v -> {
                count++;
                if ((int) v.getTag(R.string.tagIsCorrect) == 1) {
                    correctAnswer++;
                }else mistakes++;
                if (mistakes == 2) isWin = false;
                if (count == 5) {
                    startResultsDialog();
                }else makeTask(type);

            };
        } else {
            ocl = v -> {
                count++;
//                Logging.log("corr ans",(int) v.getTag(R.string.tagIsCorrect));
                if ((int) v.getTag(R.string.tagIsCorrect) == 1) {
                    correctAnswer++;
                }else mistakes++;
                Logging.log("correct answer ",correctAnswer);
                if(mistakes == 2) isWin = false;
                if (count == 5) {
                    startResultsDialog();
                } makeTask(type);
            };
        }
        for (int i = 1;i<(COUNT+1);i++){
            TextView textView = findViewById(getResources().getIdentifier("answer_"+i,"id",getPackageName()));
            textView.setOnClickListener(ocl);
        }

    }



    private void setCellsTo(String[] answers,int index_of_random_word){
        for (int i = 0;i<COUNT;i++){
            int tag;
            TextView answer_cell = findViewById(getResources().getIdentifier("answer_"+(i+1),"id",getPackageName()));
            answer_cell.setText(answers[i]);
            if(answers[i].length()>30) answer_cell.setTextSize(getResources().getDimension(R.dimen.textSize_8));
            else answer_cell.setTextSize(getResources().getDimension(R.dimen.answerCellTextSize));
            tag = 0;
            if(index_of_random_word == i){
                tag = 1;
            }
            Logging.log("tag",tag);
            answer_cell.setTag(R.string.tagIsCorrect,tag);

        }
    }




    public void startResultsDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_results);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        if(isWin) new DBHelper(this).setOpenTaskNumberIs(Integer.parseInt(level)+1,t.time);

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.repeatResultsDialog) {
                if(isWin) {
                    startActivity(new Intent(four_choice.this, four_choice.class).putExtra("type",type)); //REPEAT
                }else{
                    startActivity(new Intent(four_choice.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent = null;
                if(isWin) {
                    if (type.equals("Shaked_words"))
                        intent = new Intent(four_choice.this, four_choice.class).putExtra("type", "Question_1");
                    if (type.equals("Question_1"))
                        intent = new Intent(four_choice.this, four_choice.class).putExtra("type", "Question_2");
                    if (type.equals("Question_2"))
                        intent = new Intent(four_choice.this, four_choice.class).putExtra("type", "Question_3");
                    if (type.equals("Question_3"))
                        intent = new Intent(four_choice.this, Level_16.class);
                 }else intent = new Intent(four_choice.this, four_choice.class).putExtra("type",type); //REPEAT
                startActivity(intent);
                finish();
            }
        };

        Button continueButton = dialog.findViewById(R.id.ContinueResultsDialog);
        continueButton.setOnClickListener(OnClickListener);
        Button repeatButton = dialog.findViewById(R.id.repeatResultsDialog);
        repeatButton.setOnClickListener(OnClickListener);

        TextView textResultsDialog = dialog.findViewById(R.id.textResultsDialog);
        setResultsOnResultsDialog(textResultsDialog,false,false,false,isWin);

        if(!isWin){
            continueButton.setText(getString(R.string.repeat));
            repeatButton.setText(getString(R.string.back));
            setResultsOnResultsDialog(textResultsDialog,false,false,false,false);

        }else{
            repeatButton.setText(getString(R.string.repeat));
            repeatButton.setTextSize(getResources().getDimension(R.dimen.textSize_8));
            setResultsOnResultsDialog(textResultsDialog,false,false,false,true);
        }

    }


    @SuppressLint("DefaultLocale")
    private void setResultsOnResultsDialog(TextView textResultsDialog, boolean hasCorrect, boolean hasPercent, boolean hasMistakes, boolean isWin){
        String result = "%s%.1f";
        result = String.format(result,getString(R.string.resultDialogTime),t.time);
        if(hasCorrect){
            result = result + " %s%s";
            result = String.format(result,getString(R.string.resultDialogCorrect), correctAnswer);
        }

        if(hasPercent){
            result = result + " %s%d";
            int percent = (int) (((((float) correctAnswer)/((float) count)))*100);
            result = String.format(result,getString(R.string.resultDialogPercent),percent);

        }

        if(hasMistakes){
            result = result + " %s%d";
            result = String.format(result,getString(R.string.resultDialogMistakes), mistakes);
        }

        if(!isWin){
            result = result + " %s";
            result = String.format(result,getString(R.string.resultDialogRepeat));
        }

        textResultsDialog.setText(result);
    }

    @Override
    public void onBackPressed() {

        if ((pressedTime + 2000) > System.currentTimeMillis()) {
            toast.cancel();
            t.stopTime();
            super.onBackPressed();
            return;
        } else {
            toast = Toast.makeText(getBaseContext(), R.string.toastExit, Toast.LENGTH_SHORT);
            toast.show();
        }
        pressedTime = System.currentTimeMillis();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.cancel();
    }





}
