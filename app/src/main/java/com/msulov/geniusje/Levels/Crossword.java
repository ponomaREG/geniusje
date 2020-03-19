package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Bones;
import com.msulov.geniusje.Levels.Managers.Crossword_generator;
import com.msulov.geniusje.Levels.Managers.Crossword_static;
import com.msulov.geniusje.Levels.Managers.IsCatch_game;
import com.msulov.geniusje.Levels.Managers.Player;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.Logging.Logging;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.msulov.geniusje.Logging.Logging.log;

public class Crossword extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private Time t;
    private long pressedTime;
    private int count = 0,mistakes = 0,correctAnswer=0;
    private int taskdesc_id;
    private LinearLayout baseLY,taskLY;
    private TextView current_cell;
    private boolean isWin = true;
    private Crossword_generator crossword;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_28);

        showBeginningDialog();
        initContAndBackButtons();
        init();
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
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_21));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.level3_icon));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                    startActivity(new Intent(Crossword.this, LevelsActivity.class));
                    finish();
                } else if (v.getId() == R.id.startDialogButton) {
                    dialog.dismiss();
                    t = new Time();
                    new Thread(t, "Time").start();
                    makeTask();
                }
            }
        };

        Button startButton = dialog.findViewById(R.id.startDialogButton);
        startButton.setOnClickListener(OnClickListener);
        Button backButton = dialog.findViewById(R.id.backDialogButton);
        backButton.setOnClickListener(OnClickListener);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(OnClickListener);
    }



    private void init(){
        initVariables();
        generateLayouts();
    }

    private void initVariables(){
        taskLY = findViewById(R.id.taskLY);

        crossword = new Crossword_generator();
        crossword.setCount_Words(5);

    }




    private void makeTask(){
        generateCrossword();
    }





    private void generateLayouts(){
        View.OnClickListener ocl = getOclForCells();

        for(int i = 0; i < Crossword_generator.HEIGHT; i++) {
            LinearLayout baseLL = (LinearLayout) this.getLayoutInflater().inflate(R.layout.base_linearlayout,taskLY,false);
            for (int j = 0;j < Crossword_generator.WIDTH;j++){
                TextView base_cell = (TextView) this.getLayoutInflater().inflate(R.layout.base_cell_edittext,baseLL,false);
                base_cell.setTag(R.string.tagX,j);
                base_cell.setTag(R.string.tagY,i);
                base_cell.setTag(R.string.tagIsCell,0);
                base_cell.setOnClickListener(ocl);
                base_cell.setVisibility(View.INVISIBLE);
                base_cell.setClickable(false);
                baseLL.addView(base_cell);
            }
            taskLY.addView(baseLL);
        }

    }


    private View.OnClickListener getOclForCells(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logging.log("FIRST INDEX  WORD",view.getTag(R.string.tagIndexWord).toString());
                if(view.getTag(R.string.tagIndexSecondWordIfExists) != null)  Logging.log("SECOND INDEX WORD",view.getTag(R.string.tagIndexSecondWordIfExists).toString());
                if(current_cell != null) current_cell.setBackground(getResources().getDrawable(R.drawable.cell_style));
                view.setBackground(getDrawable(R.drawable.cell_style_checked));
                current_cell = (TextView) view;
                Logging.log("WORD",Crossword_static.getWordFromKeyword((Integer) view.getTag(R.string.tagIndexWord)));
            }
        };
    }




    private void generateCrossword(){
        int[][][] coords_cells = Crossword_static.getCoordsOfCells();
        for(int i = 0;i<coords_cells.length;i++){
            String word = Crossword_static.getWordFromKeyword(i);
            for(int j = 0 ;j<coords_cells[i].length;j++){
                TextView crossword_cell = ((TextView) ((LinearLayout) taskLY.getChildAt(coords_cells[i][j][1])).getChildAt(coords_cells[i][j][0]));
                crossword_cell.setVisibility(View.VISIBLE);
                crossword_cell.setClickable(true);
                crossword_cell.setTag(R.string.tagIsCell,1);
                if(j == 0) crossword_cell.setHint(String.valueOf(i+1));
                if(crossword_cell.getTag(R.string.tagIndexWord) != null) {
                    crossword_cell.setTag(R.string.tagIndexSecondWordIfExists, i);
                } else crossword_cell.setTag(R.string.tagIndexWord, i);
            }
        }

    }


    public void startResultsDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_results);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.repeatResultsDialog) {
                    if(isWin) {
                        startActivity(new Intent(Crossword.this, Level_27.class)); //REPEAT
                    }else{
                        startActivity(new Intent(Crossword.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                    }
                    finish();
                } else if (v.getId() == R.id.ContinueResultsDialog) {
                    Intent intent;// = null;
                    if(isWin) {
                        intent = new Intent(Crossword.this,Level_23.class);
                    }else{
                        intent = new Intent(Crossword.this, Level_27.class); //REPEAT
                    }
                    startActivity(intent);
                    finish();
                }
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



    // Обработчик нажатия системной кнопки "Назад" - (Начало)
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
        //testCOMMIT234
        // Получаем время нажатия системной кнопки - Назад
        pressedTime = System.currentTimeMillis();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.cancel();
    }
}
