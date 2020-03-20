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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Colors_shaker;
import com.msulov.geniusje.Levels.Managers.Game_15;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_24 extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private TextView set_textview,feedback;
    private Time t;
    private long pressedTime;
    private int count = 0, correctAnswer,mistakes = 0;
    private int taskdesc_id;
    private LinearLayout baseLY,taskLY;
    private String type,next_level;
    private int[] rowSumm,columnSumm, arrayGame;
    private int[][] indexes_of_pairs_coord,row_numbers,column_numbers;
    private ImageView answerBot,userAnswer,resetImage;
    private Colors_shaker colors_shaker;
    private int count_all;
    private boolean isWin = true;
    private int diffucult = Colors_shaker.DIFFICULT;
    private int count_of_correct_answers = 0,color_current,count_of_mixed = 0,color_bot = 0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_24);

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
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_24));
        //Находим аватар задания и устанавливаем свой
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.level3_icon));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                    startActivity(new Intent(Level_24.this, LevelsActivity.class));
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






    private void makeTask(){
        initAnother();
    }


    private void initAnother(){
//        rowSumm = new int[Nonogramm.WIDTH];
//        columnSumm = new int[Nonogramm.HEIGHT];
    }






    private void generateLayouts(){
        int size = Game_15.SIZE;
        int count = -1;
        taskLY = findViewById(R.id.taskLY);
        arrayGame = Game_15.getArray(size);


        View.OnClickListener ocl = getOclForCells();

        for(int i = 0;i< size;i++) {
            LinearLayout baseLL = (LinearLayout) this.getLayoutInflater().inflate(R.layout.base_linearlayout,taskLY,false);
            for (int j = 0;j<size;j++){
                count++;
                TextView textView = (TextView) this.getLayoutInflater().inflate(R.layout.base_cell_game15,baseLL,false);
                textView.setTag(R.string.tagCellNumber,arrayGame[count]);
                textView.setTag(R.string.tagCountCell,count);
                if(size<=3) {
                    textView.setTextSize(getResources().getDimension(R.dimen.blockCellTextSizeGame15Eazy));
                }else{
                    textView.setTextSize(getResources().getDimension(R.dimen.blockCellTextSizeGame15Medium));
                }
                if(arrayGame[count]==0){
                    textView.setText(" ");
                }else textView.setText(String.valueOf(arrayGame[count]));
                textView.setOnClickListener(ocl);
                baseLL.addView(textView);
            }
            taskLY.addView(baseLL);
        }


    }


    private View.OnClickListener getOclForCells(){
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAGCELLNUMBER",v.getTag(R.string.tagCellNumber).toString());
                Log.d("TAGCOUNT",v.getTag(R.string.tagCountCell).toString());
                int index = Integer.parseInt(v.getTag(R.string.tagCountCell).toString());
                int index_of_empty_element = Game_15.getIndexOfEmptyElement(index,arrayGame);
                if(index_of_empty_element!=-1){
                    Game_15.swapInArray(index_of_empty_element, index, arrayGame);
                    swapViews(index,index_of_empty_element);
                }

            }
        };
        return ocl;
    }


    private void swapViews(int index,int index_of_empty_element){
        TextView current_textview=null,textview_of_empty_element=null;
        for(int i = 0;i<Game_15.SIZE;i++){
            LinearLayout baseLL = (LinearLayout) taskLY.getChildAt(i);
            for(int j = 0;j<Game_15.SIZE;j++){
                TextView textView = (TextView) baseLL.getChildAt(j);
                if(textView.getTag(R.string.tagCountCell).toString().equals(String.valueOf(index))){
                    log("textView.getTag(R.string.tagCountCell).toString()",textView.getTag(R.string.tagCountCell).toString());
                    current_textview = textView;
                }else if(textView.getTag(R.string.tagCountCell).toString().equals(String.valueOf(index_of_empty_element))){
                    textview_of_empty_element = textView;
                    log("textView.getTag(R.string.tagCountCell).toString() EMPTY ELEMENT",textView.getTag(R.string.tagCountCell).toString());
                }
            }
        }
        int number_current = Integer.parseInt(current_textview.getTag(R.string.tagCellNumber).toString());
        current_textview.setText(" ");
        textview_of_empty_element.setText(String.valueOf(number_current));
        current_textview.setTag(R.string.tagCellNumber,0);
        textview_of_empty_element.setTag(R.string.tagCellNumber,number_current);
        chechIfUserWin();

    }



    private void chechIfUserWin(){
        if(Game_15.checkIfUserWin(arrayGame)){
            startResultsDialog();
        }
    }

    private void checkFullRow(int x,int y){
        int summ = 0;

        for(int number:row_numbers[y]){
            summ = summ + number;
        }
        if(rowSumm[y]==summ){
            TextView textView = ((TextView)((LinearLayout)taskLY.getChildAt(y+1)).getChildAt(0));
            textView.setBackground(getDrawable(R.drawable.answer_style_checked));
        }
    }

    private void checkFullColumn(int x,int y){
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
                        startActivity(new Intent(Level_24.this, Level_24.class)); //REPEAT
                    }else{
                        startActivity(new Intent(Level_24.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                    }
                    finish();
                } else if (v.getId() == R.id.ContinueResultsDialog) {
                    Intent intent;// = null;
                    if(isWin) {
                        intent = new Intent(Level_24.this,Level_25.class);
                    }else{
                        intent = new Intent(Level_24.this, Level_24.class); //REPEAT
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


    private void log(String tag,String text){
        Log.d(tag,text);
    }
    private void log(String tag,int text){
        Log.d(tag,text+"");
    }
    private void log(String tag,long text){
        Log.d(tag,text +"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.cancel();
    }

}
