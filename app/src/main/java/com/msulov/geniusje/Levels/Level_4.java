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
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Levels.Managers.Color_task;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_4 extends AppCompatActivity {

    private Toast toast;
    private Dialog dialog;
    private String correct_text_of_color;
    private TextView answerLeft, answerRight, point, color_task;
    private Time t;
    private long pressedTime;
    private int count, correctAnswer, mistakes = 0;
    private boolean isWin = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_4);
        init();
        showBeginningDialog();
    }

    private void init(){
        initVariables();
        initOclForAnswers();
    }

    private void initVariables(){
        t = new Time();
        answerLeft = findViewById(R.id.answer_left);
        answerRight = findViewById(R.id.answer_right);
        answerLeft.setTextSize(38);
        answerRight.setTextSize(38);
        color_task = findViewById(R.id.color_task);
    }

    private void initOclForAnswers(){
        answerLeft.setOnClickListener(getOclForAnswers());
        answerRight.setOnClickListener(getOclForAnswers());
    }


    private View.OnClickListener getOclForAnswers(){
        return v->{
            count++;
            boolean hasCorrect = false;

            if (v.getId() == R.id.answer_left) {
                if (answerLeft.getText().toString().equals(correct_text_of_color)) {
                    correctAnswer++;
                    hasCorrect = true;
                }else mistakes++;
            }
            if (v.getId() == R.id.answer_right) {
                if (answerRight.getText().toString().equals(correct_text_of_color)) {
                    correctAnswer++;
                    hasCorrect = true;
                }else mistakes++;
            }
            if(mistakes > 10){
                isWin = false;
                startResultsDialog();
            }
            point = findViewById(getResources().getIdentifier("point_" + count, "id", getPackageName()));
            CheckCorrect(hasCorrect,point);
            if (count==20){
                t.stopTime();
                startResultsDialog();
            }
            makeTask();

        };
    }

    private void makeTask(){
        int random_color = Color_task.getRandomColor();
        String random_text_of_color = Color_task.getRandomTextOfColor();
        correct_text_of_color = Color_task.getTextOfColor(random_color);
        answerLeft.setTextColor(Color_task.getRandomColor());
        answerRight.setTextColor(Color_task.getRandomColor());

        if (Math.random()>0.5){
            answerLeft.setText(String.valueOf(correct_text_of_color));
            answerRight.setText(String.valueOf(Color_task.getRandomTextOfColor()));
        }
        else {
            answerLeft.setText(String.valueOf(Color_task.getRandomTextOfColor()));
            answerRight.setText(String.valueOf(correct_text_of_color));
        }
        color_task.setText(random_text_of_color);
        color_task.setTextColor(random_color);
    }


    private void showBeginningDialog(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog);
        initContAndBackButtons();
        setIconAndTask();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    private void setIconAndTask() {
        TextView task = dialog.findViewById(R.id.dialogTask);
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_4));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.icon));
        TextView level_name = findViewById(R.id.level);
        String level = "4";
        level_name.setText(String.format("%s %s",level,getResources().getString(R.string.level)));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                startActivity(new Intent(getApplicationContext(), LevelsActivity.class));
                finish();
            } else if (v.getId() == R.id.startDialogButton) {
                dialog.dismiss();
                t = new Time();
                new Thread(t, "Time").start();
                makeTask();
            }
        };

        Button startButton = dialog.findViewById(R.id.startDialogButton);
        startButton.setOnClickListener(OnClickListener);
        Button backButton = dialog.findViewById(R.id.backDialogButton);
        backButton.setOnClickListener(OnClickListener);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(OnClickListener);

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

    public void startResultsDialog() {
        dialog.cancel();
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_results);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        if(isWin) new DBHelper(this).setOpenTaskNumberIs(5,t.time);

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.repeatResultsDialog) {
                if(isWin) {
                    startActivity(new Intent(getApplicationContext(), Level_4.class)); //REPEAT
                }else{
                    startActivity(new Intent(getApplicationContext(), LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(getApplicationContext(),Level_5.class);
                }else{
                    intent = new Intent(getApplicationContext(), Level_4.class); //REPEAT
                }
                startActivity(intent);
                finish();
            }
        };

        Button continueButton = dialog.findViewById(R.id.ContinueResultsDialog);
        continueButton.setOnClickListener(OnClickListener);
        Button repeatButton = dialog.findViewById(R.id.repeatResultsDialog);
        repeatButton.setOnClickListener(OnClickListener);

        TextView textResultsDialog = dialog.findViewById(R.id.textResultsDialog);

        if(!isWin){
            continueButton.setText(getString(R.string.repeat));
            repeatButton.setText(getString(R.string.back));
            setResultsOnResultsDialog(textResultsDialog,true,false,false,isWin);

        }else{
            repeatButton.setText(getString(R.string.repeat));
            repeatButton.setTextSize(getResources().getDimension(R.dimen.textSize_8));
            setResultsOnResultsDialog(textResultsDialog,true,false,true,isWin);
        }
    }


    @SuppressLint("DefaultLocale")
    private void setResultsOnResultsDialog(TextView textResultsDialog, boolean hasCorrect, boolean hasPercent, boolean hasMistakes, boolean isWin){
        String result = "%s%.1f";
        result = String.format(result,getString(R.string.resultDialogTime),t.time);

        if(hasCorrect){
            result = result + " %s%s";
            result = String.format(result,getString(R.string.resultDialogCorrect),correctAnswer);
        }

        if(hasPercent){
            result = result + " %s%d";
            int percent = (int) (((((float) correctAnswer)/((float) count)))*100);
            result = String.format(result,getString(R.string.resultDialogPercent),percent);

        }

        if(hasMistakes){
            result = result + " %s%d";
            result = String.format(result,getString(R.string.resultDialogMistakes),mistakes);
        }

        if(!isWin){
            result = result + " %s";
            result = String.format(result,getString(R.string.resultDialogRepeat));
        }

        textResultsDialog.setText(result);
    }




    private void CheckCorrect(boolean hasCorrect,TextView point){
        if (hasCorrect) {
            point.setBackgroundResource(R.drawable.points_green_style);
        }
        else  {
            point.setBackgroundResource(R.drawable.points_red_style);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.cancel();
    }

}
