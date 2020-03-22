package com.msulov.geniusje.Levels;

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

import androidx.appcompat.app.AppCompatActivity;

import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.Logging.Logging;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_1 extends AppCompatActivity {

    private Random random;
    private Toast toast;
    private Dialog dialog;
    private TextView answerLeft;
    private TextView answerRight;
    private TextView point;
    private Time t;
    private long pressedTime;
    private int left, right, count, correctAnswer, mistakes = 0;
    private boolean isWin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_1);
        init();
        showBeginningDialog();
        makeTask();
    }


    private void init(){
        initVariables();
        initOclForAnswers();
    }

    private void initVariables(){
        random = new Random(System.currentTimeMillis());
        t = new Time();
        answerLeft = findViewById(R.id.answer_left);
        answerRight = findViewById(R.id.answer_right);

    }

    private void showBeginningDialog() {
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
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_1));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.level3_icon));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                startActivity(new Intent(Level_1.this, LevelsActivity.class));
                finish();
            } else if (v.getId() == R.id.startDialogButton) {
                dialog.dismiss();
                t = new Time();
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


    private void makeTask(){
        setRandomNumbers();
    }

    private void initOclForAnswers(){
        answerLeft.setOnClickListener(getOclForAnswers());
        answerRight.setOnClickListener(getOclForAnswers());
    }

    private void setRandomNumbers(){
        answerLeft.setText(String.valueOf((left = random.nextInt(((count + 1) * 10) - 1))));
        answerRight.setText(String.valueOf((right = random.nextInt(((count + 1) * 10) - 1))));
        if((left >=100)||(right >=100)) {
            answerLeft.setTextSize(85);
            answerRight.setTextSize(85);
        }
        else {
            answerLeft.setTextSize(108);
            answerRight.setTextSize(108);
        }


        if (left == right)
            setRandomNumbers();
    }

    private View.OnClickListener getOclForAnswers(){
        return v->{
            count++;
            boolean hasCorrect = false;

            if (v.getId() == R.id.answer_left) {
                if (left > right) {
                    correctAnswer++;
                    hasCorrect = true;
                } else mistakes++;
            }
            if (v.getId() == R.id.answer_right) {
                if (right > left) {
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


    private void CheckCorrect(boolean hasCorrect,TextView point){
        if (hasCorrect) {
            point.setBackgroundResource(R.drawable.points_green_style);
        }
        else  {
            point.setBackgroundResource(R.drawable.points_red_style);
        }
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
                    startActivity(new Intent(getApplicationContext(), Level_1.class)); //REPEAT
                }else{
                    startActivity(new Intent(Level_1.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(Level_1.this,Level_2.class);
                }else{
                    intent = new Intent(Level_1.this, Level_1.class); //REPEAT
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.cancel();
    }




}