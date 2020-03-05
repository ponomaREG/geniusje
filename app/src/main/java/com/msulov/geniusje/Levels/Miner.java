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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Memory_game;
import com.msulov.geniusje.Levels.Managers.Miner_manager;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Miner extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private TextView set_textview;
    private Time t;
    private long pressedTime;
    private int count = 0, correctAnswer,mistakes = 0;
    private int taskdesc_id;
    private LinearLayout baseLY,taskLY;
    private String type,next_level;
    private int[][] indexes_of_pairs_coord, indexes_bombs;
    private int count_all, diffucult;
    private boolean isWin = true;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.miner);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        Log.d("TYPE",type);
        if(type == null) type = "EAZY";
        switch (type){
            case "EAZY":
                taskdesc_id = R.string.startDialogWindowForLevel_20;
                next_level = "MEDIUM";
                diffucult = Miner_manager.HARD;
                break;
        }

        showBeginningDialog();
        initContAndBackButtons();
        makeTask();
//        initOclForAnswers();
    }

    /////BEGINNIG AND RESULTING CONSTANT BLOCKS
    private void showBeginningDialog() {
        t = new Time();

        // Вызов диалогового окна - (Начало)
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog);
        //Находим текст задания и устанавливаем его на свой
        // Делаем задний фон прозрачным
        setIconAndTask();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Убираем возможность закрывать системной кнопкой "Назад"
        dialog.setCancelable(false);
        dialog.show();
        // Вызов диалогового окна - (Конец)
    }

    private void setIconAndTask() {
        TextView task = dialog.findViewById(R.id.dialogTask);
        task.setText(getResources().getString(taskdesc_id));
        //Находим аватар задания и устанавливаем свой
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.level3_icon));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                    startActivity(new Intent(Miner.this, LevelsActivity.class));
                    finish();
                } else if (v.getId() == R.id.startDialogButton) {
                    dialog.dismiss();
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



    private void makeTask(){
        generateLayouts();
    }


    private void generateLayouts(){
        indexes_bombs = Miner_manager.getRandomCoordOfBombs(diffucult);
        taskLY = findViewById(R.id.taskLY);
        View.OnClickListener ocl = getOclForCellsSudoku();

        for(int i = 0; i< Miner_manager.HEIGHT; i++){
            LinearLayout baseLL = (LinearLayout) this.getLayoutInflater().inflate(R.layout.base_linearlayout,null);
            for(int j = 0 ; j<Miner_manager.WIDTH;j++){
//                boolean isBomb = false;
//                for(int[] bombs:indexes_bombs){
//                    if((bombs[0]==j)&&(bombs[1]==i)){
//                        isBomb = true;
//                    }
//                }

                TextView textView = (TextView) this.getLayoutInflater().inflate(R.layout.base_cell,baseLL,false);

                textView.setText(" ");
                textView.setTag(R.string.tagX,j);
                textView.setTag(R.string.tagY,i);
                textView.setTextSize(getResources().getDimension(R.dimen.answerCellTextSizeUltraSmall));
                textView.setTag(R.string.tagCellNumber,0);
                textView.setOnClickListener(ocl);
                if(Miner_manager.isXandYinARRAY(indexes_bombs,j,i)) {
                    textView.setTag(R.string.tagIsBomb,1);
                }
                else textView.setTag(R.string.tagIsBomb,0);
                baseLL.addView(textView);
//                Log.d("J IS ",String.valueOf(j));
            }
            taskLY.addView(baseLL);
            new Thread(t,"time");
        }


        for(int[] bombs:indexes_bombs){
            int[][] coords_of_around_cells;
            int count_of_bombs;
            int x_bomb = bombs[0];
            int y_bomb = bombs[1];
            coords_of_around_cells = Miner_manager.getCoordOfCellsAroundCell(x_bomb,y_bomb,Miner_manager.WIDTH,Miner_manager.HEIGHT);
            for(int[] coords_of_cell:coords_of_around_cells){
                TextView cell = (TextView) ((LinearLayout)taskLY.getChildAt(coords_of_cell[1])).getChildAt(coords_of_cell[0]);
                if(cell.getTag(R.string.tagIsBomb).toString().equals("0")) {
                    if (cell.getTag(R.string.tagCellNumber).toString().equals("0")){
                        cell.setTag(R.string.tagCellNumber,1);
                    }
                    else {
                        count_of_bombs = (int) cell.getTag(R.string.tagCellNumber);
                        count_of_bombs++;
                        cell.setTag(R.string.tagCellNumber,String.valueOf(count_of_bombs));
                    }
                }
            }
        }

    }


    private View.OnClickListener getOclForCellsSudoku(){
        count_all = indexes_bombs.length;

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_textview = (TextView) v;
                int x = (int) set_textview.getTag(R.string.tagX);
                int y = (int) set_textview.getTag(R.string.tagY);
                if((int) set_textview.getTag(R.string.tagIsBomb) == 1){
                    isWin = false;
                    startResultsDialog();
                }else{
                    set_textview.setText(set_textview.getTag(R.string.tagCellNumber).toString());
                }
            }
        };
        return ocl;
    }


    @Deprecated
    private void showRhythm(){

    }


    @Deprecated
    private void frozeOrUnfrozeViews(boolean clickable){
        for(int i = 0 ; i<Memory_game.HEIGHT;i++){
            for(int j = 0;j<Memory_game.WIDTH;j++){
                TextView textView = (TextView) ((LinearLayout) taskLY.getChildAt(i)).getChildAt(j);
                textView.setClickable(clickable);
            }
        }
    }


    private void setViewChecked(TextView textView){
        textView.setBackground(getDrawable(R.drawable.cell_style_checked));
    }


    private void setViewUnchecked(TextView textView){
        textView.setBackground(getDrawable(R.drawable.cell_style));
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
                        startActivity(new Intent(Miner.this, memory_base.class).putExtra("type", type)); //REPEAT
                    }else{
                        startActivity(new Intent(Miner.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                    }
                    finish();
                } else if (v.getId() == R.id.ContinueResultsDialog) {
                    Intent intent;// = null;
                    if(isWin) {
                        if(next_level.equals("None")){
                            intent = new Intent(Miner.this,memory_base.class).putExtra("type","Find_all");
                        }else intent = new Intent(Miner.this, memory_base.class).putExtra("type", next_level); //NEXT LEVEL
                    }else{
                        intent = new Intent(Miner.this, memory_base.class).putExtra("type", type); //REPEAT
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




//    private void startResultDialogForRepeating(){
//
//        Dialog dialog = new Dialog(this);
//        dialog.setContentView();
//
//    }

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










}
