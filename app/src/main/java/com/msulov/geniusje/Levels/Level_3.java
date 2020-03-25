package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Equation;

import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_3 extends AppCompatActivity {

    private Toast toast;
    private Dialog dialog;
    private TextView answerLeft, answerRight, point,equation;
    private Time t;
    private long pressedTime;
    private int count, correctAnswer, answer, mistakes = 0;
    private boolean isWin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_3);
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
            equation = findViewById(R.id.equation);
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
                    if (Integer.parseInt(answerLeft.getText().toString())==answer) {
                        correctAnswer++;
                        hasCorrect = true;
                    } else mistakes++;
                }
                if (v.getId() == R.id.answer_right) {
                    if (Integer.parseInt(answerRight.getText().toString())==answer) {
                        correctAnswer++;
                        hasCorrect = true;
                    }else mistakes++;
                }
                if(mistakes>10) {
                    isWin = false;
                    startResultsDialog();
                }
                point = findViewById(getResources().getIdentifier("point_" + count, "id", getPackageName()));
                CheckCorrect(hasCorrect,point);
                if (count==20){
                    t.stopTime();
                    startResultsDialog();
                }else{
                    makeTask();
                }
            };
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
            task.setText(getResources().getString(R.string.startDialogWindowForLevel_3));
            CircleImageView icon = dialog.findViewById(R.id.iconTask);
            icon.setImageDrawable(getResources().getDrawable(R.drawable.icon));
            TextView level_name = findViewById(R.id.level);
            String level = "3";
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

    private void makeTask(){
        int number_1 = Equation.getRandomNumber(count);
        int number_2 = Equation.getRandomNumber(count);
        Log.d("numb_1",String.valueOf(number_1));
        Log.d("numb_2",String.valueOf(number_2));
        String sign = Equation.getRandomSign();
        assert sign != null;
        answer = Equation.getAnswerOfEquation(number_1,number_2,sign);
        int another_number = Equation.getNumberWithP(answer);

        if (((answer>=100)||(another_number>=100))||((answer<=-10)||(another_number<=-10))){
            answerLeft.setTextSize(68);
            answerRight.setTextSize(68);
            if((answer>=1000)||(another_number>=1000)||(answer<=-100)||(another_number<=-100)){
                answerLeft.setTextSize(42);
                answerRight.setTextSize(42);
            }
        }else {

            answerLeft.setTextSize(108);
            answerRight.setTextSize(108);
        }
        if (Math.random()>0.5){
            answerLeft.setText(String.valueOf(answer));
            answerRight.setText(String.valueOf(another_number));
        }
        else {
            answerLeft.setText(String.valueOf(another_number));
            answerRight.setText(String.valueOf(answer));
        }
        equation.setText(Equation.makeEquation(number_1,number_2,sign)[0]);
    }



    public void startResultsDialog() {
        dialog.cancel();
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_results);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.repeatResultsDialog) {
                if(isWin) {
                    startActivity(new Intent(getApplicationContext(), Level_3.class)); //REPEAT
                }else{
                    startActivity(new Intent(getApplicationContext(), LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(getApplicationContext(),Level_4.class);
                }else{
                    intent = new Intent(getApplicationContext(), Level_3.class); //REPEAT
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
