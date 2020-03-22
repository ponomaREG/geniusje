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

import com.msulov.geniusje.Levels.Managers.Cells;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_6 extends AppCompatActivity {


    private Toast toast;
    private Dialog dialog;
    private Time t;
    private long pressedTime;
    private int count;
    private boolean isWin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_6);

        init();
        showBeginningDialog();
    }

    private void init(){
        initVariables();
        initOclForAnswers();
    }

    private void initVariables(){
        count = 1;
        t = new Time();
    }

    private void initOclForAnswers(){
        for (int i = 1; i < 10; i++) {
            findViewById(getResources().getIdentifier("cell_" + i, "id", getPackageName())).setOnClickListener(getOclForCells());
            ((TextView) findViewById(getResources().getIdentifier("cell_" + i, "id", getPackageName()))).setTextSize(68);
        }

    }

    private View.OnClickListener getOclForCells(){
        return v->{
            if (Integer.parseInt(v.getTag().toString()) == count) {
                if (Integer.parseInt(v.getTag().toString()) != 9) {
                    count++;
                    v.setBackground(getDrawable(R.drawable.answer_style_checked));
                } else {
                    t.stopTime();
                    startResultsDialog();
                }
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
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_6));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.level3_icon));
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


    private void makeTask() {
        int[] array_of_numbers = Cells.getRandomArrayOfNumbers(9);
        for (int i = 1; i < array_of_numbers.length+1; i++) {
            TextView cell = findViewById(getResources().getIdentifier("cell_" + i, "id", getPackageName()));
            cell.setText(String.valueOf(array_of_numbers[i - 1]));
            cell.setTag(array_of_numbers[i - 1]);
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
                    startActivity(new Intent(getApplicationContext(), Level_6.class)); //REPEAT
                }else{
                    startActivity(new Intent(getApplicationContext(), LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(getApplicationContext(),Level_7.class);
                }else{
                    intent = new Intent(getApplicationContext(), Level_6.class); //REPEAT
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
            setResultsOnResultsDialog(textResultsDialog,false,false,false,isWin);

        }else{
            repeatButton.setText(getString(R.string.repeat));
            repeatButton.setTextSize(getResources().getDimension(R.dimen.textSize_8));
            setResultsOnResultsDialog(textResultsDialog,false,false,false,isWin);
        }
    }


    @SuppressLint("DefaultLocale")
    private void setResultsOnResultsDialog(TextView textResultsDialog, boolean hasCorrect, boolean hasPercent, boolean hasMistakes, boolean isWin){
        String result = "%s%.1f";
        result = String.format(result,getString(R.string.resultDialogTime),t.time);

        int correctAnswer = 0;
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
            int mistakes = 0;
            result = String.format(result,getString(R.string.resultDialogMistakes), mistakes);
        }

        if(!isWin){
            result = result + " %s";
            result = String.format(result,getString(R.string.resultDialogRepeat));
        }

        textResultsDialog.setText(result);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.cancel();
    }
}


