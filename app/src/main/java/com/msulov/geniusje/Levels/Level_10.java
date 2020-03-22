package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Memory_game;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;


import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_10 extends AppCompatActivity {

    private Toast toast;
    private Dialog dialog;
    private TextView second_choosed_cell;
    private Time t;
    private long pressedTime;
    private TextView chossedCell;
    private boolean hasChoosed = false;
    private int count;
    private int[] cells;
    private TextView[] cells_views;
    private Handler handler_for_timer;
    private boolean isWin = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_10);

        init();
        showBeginningDialog();
    }




    //BEGIN BLOCK
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
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_10));
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
    //BEGIN BLOCK END

    //SHOW RESULT BLOCK
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
                    startActivity(new Intent(getApplicationContext(), Level_10.class)); //REPEAT
                }else{
                    startActivity(new Intent(getApplicationContext(), LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(getApplicationContext(),Level_11.class);
                }else{
                    intent = new Intent(getApplicationContext(), Level_10.class); //REPEAT
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

    //SHOW RESULT BLOCK END

    //TASK FUNCTIONS
    private void makeTask(){
        showCells();
        handler_for_timer = new Handler();
        handler_for_timer.postDelayed(() -> {
            closeCells();
            initOclForCells();
        },3000);

    }

    //INIT
    private void init(){
        initVariables();
        initCells();
    }

    private void initVariables(){
        count = 0;
        t = new Time();
    }

    private void initOclForCells(){
        for (TextView cell_view :cells_views){
            cell_view.setOnClickListener(getOclForCells());
        }

    }

    private View.OnClickListener getOclForCells(){
        return v->{
            if(!hasChoosed){
                chossedCell = (TextView) v;
                chossedCell.setText(chossedCell.getTag(R.string.tagCellNumber).toString());
                chossedCell.setClickable(false);
                hasChoosed = true;
            }else{
                frozeViews();
                second_choosed_cell = (TextView) v;
                second_choosed_cell.setText(second_choosed_cell.getTag(R.string.tagCellNumber).toString());

                if (second_choosed_cell.getTag(R.string.tagCellNumber).toString().equals(chossedCell.getTag(R.string.tagCellNumber).toString())){
                    count++;
                    second_choosed_cell.setClickable(false);
                    chossedCell.setTag(R.string.tagClosed,0);
                    second_choosed_cell.setTag(R.string.tagClosed,0);
                    unfrozeViews();
                    hasChoosed = false;
                    if(count==8){
                        startResultsDialog();
                    }
                } else{
                    handler_for_timer.postDelayed(() -> {
                        chossedCell.setText("");
                        second_choosed_cell.setText("");
                        unfrozeViews();
                        hasChoosed = false;
                    },600);
                }
            }
        };
    }

    private void initCells(){
        cells = Memory_game.getArrayOfNumbersForGame(16);
        Memory_game.getShakedArray(cells);
        cells_views = new TextView[16];
        for (int i = 1;i<17;i++){
            cells_views[i-1] = findViewById(getResources().getIdentifier("cell_"+i,"id",getPackageName()));
            cells_views[i-1].setTextSize(48);
        }
    }


    private void showCells(){
        for (int i = 0;i<16;i++){
            cells_views[i].setText(String.valueOf(cells[i]));
            cells_views[i].setTag(R.string.tagClosed,1);
            cells_views[i].setTag(R.string.tagCellNumber,cells[i]);
        }
    }

    private void closeCells(){
        for (TextView cell_view:cells_views){
            cell_view.setText("");
        }
    }


    private void frozeViews(){
        for (TextView cell_view:cells_views){

            cell_view.setClickable(false);
        }
    }

    private void unfrozeViews(){
        for (TextView cell_view:cells_views){
            if(cell_view.getTag(R.string.tagClosed).toString().equals("1")) {
                cell_view.setClickable(true);
            }
        }
    }


//TASK'S FUNCTIONS END






//LOG FUNCTIONS
//    private void log(String tag,String text){
//        Log.d(tag,text);
//    }
//    private void log(String tag,int text){
//        Log.d(tag,String.valueOf(text));
//    }
//    private void log(String tag,boolean text){
//        Log.d(tag,String.valueOf(text));
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.cancel();
    }
}

