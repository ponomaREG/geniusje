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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Shaked_words;
import com.msulov.geniusje.Levels.Managers.Sudoku;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_17 extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private TextView set_textview;
    private Time t;
    private long pressedTime;
    private int correctAnswer = 0;
    private int mistakes = 0;
    private int[][] sudoku;
    private LinearLayout baseLY,taskLY;
    private boolean isWin = true;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_17);

        showBeginningDialog();
        initContAndBackButtons();
        makeTask();

    }

    /////BEGINNIG CONSTANT BLOCKS
    private void showBeginningDialog() {
        t = new Time();
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog);
        setIconAndTask();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    private void setIconAndTask() {
        TextView task = dialog.findViewById(R.id.dialogTask);
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_17));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.icon));
        TextView level_name = findViewById(R.id.level);
        String level = "17";
        level_name.setText(String.format("%s %s",level,getResources().getString(R.string.level)));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                startActivity(new Intent(Level_17.this, LevelsActivity.class));
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



    private void makeTask(){
        generateLayouts();
        initOclForAnswerCells();
    }


    private void generateLayouts(){
        int[] indexes_of_missing_numbers;
        Random random = new Random();
        sudoku = Sudoku.getSudoku();
        taskLY = findViewById(R.id.taskLY);
        View.OnClickListener ocl = getOclForCellsSudoku();

        for(int i =0 ; i<Sudoku.HEIGHT;i++){
            baseLY = (LinearLayout) this.getLayoutInflater().inflate(R.layout.base_ly_sudoku,null);
            indexes_of_missing_numbers = Shaked_words.getRandomIndexes(random.nextInt(3)+Sudoku.EAZY-1,Sudoku.HEIGHT,false);

            for(int j = 0 ; j<Sudoku.HEIGHT;j++){
                TextView textView = (TextView) baseLY.getChildAt(j);
                textView.setText(String.valueOf(sudoku[i][j]));
                textView.setTag(R.string.tagSudokuX,j);
                textView.setTag(R.string.tagSudokuY,i);
                textView.setTextSize(getResources().getDimension(R.dimen.answerCellTextSizeUltraSmall));
                textView.setOnClickListener(ocl);
                for(int index_of_missing_number:indexes_of_missing_numbers){
                    if(index_of_missing_number == j) {
                        textView.setText("");
                        textView.setTag(R.string.tagSudokuIsChecked,0);
                        break;
                    } else textView.setTag(R.string.tagSudokuIsChecked,1);
                }

            }
            taskLY.addView(baseLY);
        }

    }


    private View.OnClickListener getOclForCellsSudoku(){
        return v -> {
            if(set_textview != null) set_textview.setBackground(getDrawable(R.drawable.cell_style));
            int x = (int) v.getTag(R.string.tagSudokuX);
            int y = (int) v.getTag(R.string.tagSudokuY);
            set_textview = (TextView) ((LinearLayout) taskLY.getChildAt(y)).getChildAt(x);
            set_textview.setBackground(getDrawable(R.drawable.cell_style_checked));
        };
    }


    private void initOclForAnswerCells(){
        View.OnClickListener ocl = v -> {
            if(set_textview != null){
                int user_choice =  Integer.parseInt(v.getTag().toString());
                Log.d("USER CHOICE",String.valueOf(user_choice));
                if(!Sudoku.checkUserChoiceForCorrect(
                        sudoku,
                        (int) set_textview.getTag(R.string.tagSudokuX),
                        (int) set_textview.getTag(R.string.tagSudokuY),
                        user_choice)) {
                    mistakes++;
                    set_textview.setTag(R.string.tagSudokuIsChecked,0);
                    set_textview.setTextColor(getResources().getColor(R.color.colorTextOfSudokuGetMistake));
                    if (mistakes == 5) {
                        isWin = false;
                        startResultsDialog();
                    }
                }else {
                    correctAnswer++;
                    set_textview.setTextColor(getResources().getColor(R.color.black_95));
                    set_textview.setTag(R.string.tagSudokuIsChecked,1);
                }
                set_textview.setText(String.valueOf(user_choice));
                if(isSudokuIsReadyForEnd()){
                    startResultsDialog();
                }
            }
        };

        for(int i = 1;i<Sudoku.HEIGHT+1;i++){
            TextView answerCell = findViewById(getResources().getIdentifier("choice_"+i,"id",getPackageName()));
            answerCell.setOnClickListener(ocl);
        }
    }



    private boolean isSudokuIsReadyForEnd(){
        for(int i = 0;i< Sudoku.HEIGHT;i++){

            baseLY = (LinearLayout) taskLY.getChildAt(i);
            for(int j = 0;j<Sudoku.HEIGHT;j++){
                TextView textView = (TextView) baseLY.getChildAt(j);
                if(Integer.parseInt(textView.getTag(R.string.tagSudokuIsChecked).toString()) == 0) return false;
            }
        }
        return true;
    }

    //RESULTING BLOCK
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
                    startActivity(new Intent(getApplicationContext(), Level_17.class)); //REPEAT
                }else{
                    startActivity(new Intent(getApplicationContext(), LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(getApplicationContext(),memory_base.class);
                }else{
                    intent = new Intent(getApplicationContext(), Level_17.class); //REPEAT
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

        if(hasCorrect){
            result = result + " %s%s";
            result = String.format(result,getString(R.string.resultDialogCorrect), correctAnswer);
        }

        if(hasPercent){
            result = result + " %s%d";
            int count = 0;
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

    //RESULTING BLOCK END



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
