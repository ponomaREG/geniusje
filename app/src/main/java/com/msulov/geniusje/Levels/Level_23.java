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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Miner_manager;
import com.msulov.geniusje.Levels.Managers.Nonogramm;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_23 extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private TextView set_textview;
    private Time t;
    private long pressedTime;
    private int mistakes = 0;
    private LinearLayout taskLY;
    private int[] rowSumm,columnSumm;
    private int[][] row_numbers;
    private int[][] column_numbers;
    private int count_all;
    private boolean isWin = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_23);

        showBeginningDialog();
        initContAndBackButtons();
        generateLayouts();
//        initOclForAnswers();
    }

    /////BEGINNIG AND RESULTING CONSTANT BLOCKS
    private void showBeginningDialog() {

        // Вызов диалогового окна - (Начало)
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog);
        setIconAndTask();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    private void setIconAndTask() {
        TextView task = dialog.findViewById(R.id.dialogTask);
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_23));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.level3_icon));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                startActivity(new Intent(Level_23.this, LevelsActivity.class));
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
        initAnonther();
    }


    private void initAnonther(){
        rowSumm = new int[Nonogramm.WIDTH];
        columnSumm = new int[Nonogramm.HEIGHT];
    }




    private void generateLayouts(){
        int difficult = Nonogramm.DIFFICULT;
        taskLY = findViewById(R.id.taskLY);
        int[][] indexes_of_pairs_coord = Nonogramm.getRandomCellsNumber(difficult, Nonogramm.HEIGHT, Nonogramm.WIDTH);
        row_numbers = Nonogramm.getCountOfCellsCheckedInRow(indexes_of_pairs_coord,Nonogramm.HEIGHT,Nonogramm.WIDTH);
        column_numbers = Nonogramm.getCountOfCellsCheckedInColumn(indexes_of_pairs_coord,Nonogramm.HEIGHT,Nonogramm.WIDTH);
        View.OnClickListener ocl = getOclForCells();

        for(int i = 0;i< Nonogramm.HEIGHT;i++){


            LinearLayout baseLY;
            if(i==0){
                baseLY = (LinearLayout) this.getLayoutInflater().inflate(R.layout.base_linearlayout,taskLY,false);
                TextView infoX = (TextView) this.getLayoutInflater().inflate(R.layout.base_textview_info_x, baseLY,false);
                infoX.setVisibility(View.INVISIBLE);
                baseLY.addView(infoX);

                for(int j = 0;j<Nonogramm.WIDTH;j++){
                    TextView infoY = (TextView) this.getLayoutInflater().inflate(R.layout.base_textview_info_y, baseLY,false);
                    StringBuilder s = new StringBuilder();
                    for(int number:column_numbers[j]){
                        if(number!=0) s.append(number).append("\n");
                    }
                    infoY.setText(s.toString());
                    baseLY.addView(infoY);
                }
                taskLY.addView(baseLY);
            }


            baseLY = (LinearLayout) this.getLayoutInflater().inflate(R.layout.base_linearlayout,taskLY,false);
            TextView infoX = (TextView) this.getLayoutInflater().inflate(R.layout.base_textview_info_x, baseLY,false);
            StringBuilder s = new StringBuilder();
            for(int number:row_numbers[i]){
                if(number!=0) {
                    count_all += number;
                    s.append(number).append(" ");
                }
            }
            infoX.setText(s.toString());
            baseLY.addView(infoX);
            for(int j = 0;j<Nonogramm.WIDTH;j++){
                TextView baseCELL = (TextView) this.getLayoutInflater().inflate(R.layout.base_cell_nonogramm, baseLY,false);
                baseCELL.setTag(R.string.tagX,j);
                baseCELL.setTag(R.string.tagY,i);
                if(Miner_manager.isXandYinARRAY(indexes_of_pairs_coord,j,i)){
                    baseCELL.setTag(R.string.tagIsCorrect,1);
//                    baseCELL.setBackground(getDrawable(R.drawable.cell_style_checked));
                }
                else{
                    baseCELL.setTag(R.string.tagIsCorrect,0);
                }
                baseCELL.setOnClickListener(ocl);
                baseLY.addView(baseCELL);
            }
            taskLY.addView(baseLY);
        }


    }

//    @Deprecated
//    private void initAnswersOcl(){
//        findViewById(R.id.scissors).setOnClickListener(getOclForAnswers());
//        findViewById(R.id.rock).setOnClickListener(getOclForAnswers());
//        findViewById(R.id.letter).setOnClickListener(getOclForAnswers());
//    }

    private View.OnClickListener getOclForCells(){
        return v -> {
            set_textview = (TextView) v;
            if(set_textview.getTag(R.string.tagIsCorrect).toString().equals("0")){
                set_textview.setBackground(getDrawable(R.drawable.cell_style_error));
                mistakes++;
                if(mistakes == 5) {
                    isWin = false;
                    startResultsDialog();
                }

            }else{
                setViewChecked(set_textview);
                count_all--;
                int x = Integer.parseInt(set_textview.getTag(R.string.tagX).toString());
                int y = Integer.parseInt(set_textview.getTag(R.string.tagY).toString());
                rowSumm[y]++;
                columnSumm[x]++;
                checkFullColumn(x);
                checkFullRow(y);
                if(count_all==0) startResultsDialog();
            }
            set_textview.setClickable(false);
        };
    }


    private void checkFullRow(int y){
        int summ = 0;

        for(int number:row_numbers[y]){
            summ = summ + number;
        }
        if(rowSumm[y]==summ){
            TextView textView = ((TextView)((LinearLayout)taskLY.getChildAt(y+1)).getChildAt(0));
            textView.setBackground(getDrawable(R.drawable.answer_style_checked));
        }
    }

    private void checkFullColumn(int x){
        int summ = 0;

        for(int number:column_numbers[x]){
            summ = summ + number;
        }
        if(columnSumm[x]==summ){
            TextView textView = ((TextView)((LinearLayout)taskLY.getChildAt(0)).getChildAt(x+1));
            textView.setBackground(getDrawable(R.drawable.answer_style_checked));
        }
    }



    private void setViewChecked(TextView textView){
        textView.setBackground(getDrawable(R.drawable.cell_style_checked));
    }


    public void startResultsDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_results);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.repeatResultsDialog) {
                if(isWin) {
                    startActivity(new Intent(Level_23.this, Level_23.class)); //REPEAT
                }else{
                    startActivity(new Intent(Level_23.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(Level_23.this,Level_24.class);
                }else{
                    intent = new Intent(Level_23.this, Level_23.class); //REPEAT
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

        int correctAnswer = 0;
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
