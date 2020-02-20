package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;

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

import com.msulov.geniusje.Levels.Managers.Cells;
import com.msulov.geniusje.Levels.Managers.Memory_game;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_10 extends AppCompatActivity {

    private Random random;
    private Toast toast;
    private Button backButton, startButton, continueButton, repeatButton;
    private Dialog dialog;
    private CircleImageView icon;
    private TextView answerLeft, answerRight, point, task, color_task, cell, second_choosed_cell;
    private Time t;
    private long pressedTime;
    private TextView chossedCell;
    private View.OnClickListener ocl;
    private boolean hasChoosed = false;
    private boolean hasPair = false;
    private int count, correctAnswer, correct_color;
    private int[] array_of_numbers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_10);

        showBeginningDialog();

        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                    startActivity(new Intent(Level_10.this, LevelsActivity.class));
                    finish();
                } else if (v.getId() == R.id.startDialogButton) {
                    dialog.dismiss();
                    new Thread(t, "Time").start();
                    showCells();
                }
            }
        };

        // Обработчик нажатия на "Начать" в диалоговом окне - (Начало)
        startButton = dialog.findViewById(R.id.startDialogButton);
        startButton.setOnClickListener(OnClickListener);
        // Обработчик нажатия на "Начать" в диалоговом окне - (Конец)

        // Обработчик нажатия на "Назад" в диалоговом окне - (Начало)
        backButton = dialog.findViewById(R.id.backDialogButton);
        backButton.setOnClickListener(OnClickListener);
        // Обработчик нажатия на "Назад" в диалоговом окне - (Конец)

        //Уравнение


        // Обработчик нажатия на "Назад" - (Начало)
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(OnClickListener);
        // Обработчик нажатия на "Назад" - (Конец)
//        makeTask();


        // Уровень -1
        count = 1;
//        View.OnClickListener onClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Integer.parseInt(v.getTag().toString()) == count) {
//                    if (Integer.parseInt(v.getTag().toString()) != 16) {
//                        count++;
//                        v.setBackground(getDrawable(R.drawable.answer_style_checked));
//                    } else {
//                        t.stopTime();
//                        startResultsDialog();
//                    }
//                }
//
//            }
//        };

        // Добавляем обработчик к кнопкам
//        for (int i = 1; i < 17; i++) {
//            findViewById(getResources().getIdentifier("cell_" + i, "id", getPackageName())).setOnClickListener(onClickListener);
//            int size = 48;
////            if (i>=10) size = 58;
//            ((TextView) findViewById(getResources().getIdentifier("cell_" + i, "id", getPackageName()))).setTextSize(size);
//        }
    }

    public void startResultsDialog() {
        // Вызов диалогового окна с результатами - (Начало)
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_results);
        // Делаем задний фон прозрачным
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Убираем возможность закрывать системной кнопкой "Назад"
        dialog.setCancelable(false);
        dialog.show();
        // Вызов диалогового окна с результатами - (Конец)

        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.repeatResultsDialog) {
                    startActivity(new Intent(Level_10.this, LevelsActivity.class));
                    finish();
                } else if (v.getId() == R.id.ContinueResultsDialog) {
                    startActivity(new Intent(Level_10.this, Level_8.class));
                    finish();
                }
            }
        };

        continueButton = dialog.findViewById(R.id.ContinueResultsDialog);
        continueButton.setOnClickListener(OnClickListener);
        repeatButton = dialog.findViewById(R.id.repeatResultsDialog);
        repeatButton.setOnClickListener(OnClickListener);

        TextView textResultsDialog = dialog.findViewById(R.id.textResultsDialog);
        textResultsDialog.setText("Time: " + String.format("%.1f", t.time));
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
    // Обработчик нажатия системной кнопки "Назад" - (Конец)


    @Deprecated
    private void CheckCorrect(boolean hasCorrect, TextView point) {
        if (hasCorrect) {
            point.setBackgroundResource(R.drawable.points_green_style);
        } else {
            point.setBackgroundResource(R.drawable.points_red_style);
        }

    }

//
//    private void makeTask() {
//        array_of_numbers = Cells.getRandomArrayOfNumbers(16);
//
//    }

    private void showBeginningDialog() {
        t = new Time();

        // Вызов диалогового окна - (Начало)
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog);
        //Находим текст задания и устанавливаем его на свой
        // Делаем задний фон прозрачным
        setIconAndTask();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Убираем возможность закрывать системной кнопкой "Назад"
        dialog.setCancelable(false);
        dialog.show();
        // Вызов диалогового окна - (Конец)
    }

    private void setIconAndTask() {
        task = dialog.findViewById(R.id.dialogTask);
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_7));
        //Находим аватар задания и устанавливаем свой
        icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.level2_icon));
    }

    private void showCells(){
        int[] cells = Memory_game.getShakedArray(Memory_game.getArrayOfNumbersForGame(16));
        t = new Time();
        new Thread(t,"time");

        for (int i = 1;i<17;i++){
            cell = findViewById(getResources().getIdentifier("cell_"+i,"id",getPackageName()));
            cell.setTextSize(48);
            cell.setTag(R.string.tagClosed,0);
            cell.setTag(R.string.tagCellNumber,cells[i-1]);
            Log.d("array",String.valueOf(i));
            cell.setText(String.valueOf(cells[i-1]));

        }
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(),3000);


    }



    private void closeCells(){
        for (int i = 1;i<17;i++){
            cell = findViewById(getResources().getIdentifier("cell_"+i,"id",getPackageName()));
            cell.setText("");
            setOclOnCells(cell);
        }
    }

    private View.OnClickListener getOnClickListener(){
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view;
                log("1 ds",view.isEnabled());
                log("2 ds",view.isClickable());
                log("3 ds",view.isFocusableInTouchMode());
                log("4 ds",view.isFocusable());
                if (!hasChoosed) {

                    if (Integer.parseInt(view.getTag(R.string.tagClosed).toString()) == 0){
                        chossedCell = textView;
                        textView.setText(chossedCell.getTag(R.string.tagCellNumber).toString());
                        hasChoosed = true;
                        Log.d("CHOOSED","1");
                        chossedCell.setClickable(false);
                        log("1",chossedCell.isEnabled());
                        log("2",chossedCell.isClickable());
                        log("3",chossedCell.isFocusableInTouchMode());
                        log("4",chossedCell.isFocusable());

                    }
                }else{
                      second_choosed_cell = textView;
//                    frozeViews();

                    if (second_choosed_cell.getTag(R.string.tagCellNumber).toString().equals(chossedCell.getTag(R.string.tagCellNumber).toString())){
                        second_choosed_cell.setTag(R.string.tagClosed,1);
                        chossedCell.setTag(R.string.tagClosed,1);
                        count++;
                        second_choosed_cell.setText(second_choosed_cell.getTag(R.string.tagCellNumber).toString());
                        chossedCell.setText(second_choosed_cell.getTag(R.string.tagCellNumber).toString());
                        Log.d("SUCCESS","1");
                        Log.d("CHOOSSED",String.valueOf(chossedCell.isEnabled()));
                        Log.d("TEXTVIEW",String.valueOf(second_choosed_cell.isEnabled()));
                        second_choosed_cell.setClickable(false);
                        if (count==5){
                            startResultsDialog();
                        }
                        hasChoosed = false;


                    }else{
                        second_choosed_cell.setText(second_choosed_cell.getTag(R.string.tagCellNumber).toString());
                        chossedCell.setText(chossedCell.getTag(R.string.tagCellNumber).toString());
//                        second_choosed_cell = textView;
                        chossedCell.setClickable(true);
                        hasChoosed = false;
                        Log.d("CHOOSSED",String.valueOf(chossedCell.isEnabled()));
                        Log.d("TEXTVIEW",String.valueOf(textView.isEnabled()));
                        Timer timer = new Timer();
                        timer.schedule(new AnotherTimerTask(),1500);

                    }

                    unfrozeViews();

                }
            }
        };
        return ocl;
    }

    private void setOclOnCells(TextView cell){
        View.OnClickListener ocl = getOnClickListener();
        cell.setOnClickListener(ocl);
    }

    private void closeCurrentCells(){
        chossedCell.setText("");
        second_choosed_cell.setText("");
    }


    private void frozeViews(){
        for (int i = 1; i < 17; i++){
            TextView view = findViewById(getResources().getIdentifier("cell_"+i,"id",getPackageName()));
            if (Integer.parseInt(view.getTag(R.string.tagClosed).toString()) == 0){
                view.setClickable(false);
                Log.d("VIEW ENABLED",String.valueOf(view.isClickable()));
            }
        }
    }

    private void unfrozeViews(){
        for (int i = 1; i < 17; i++){
            TextView view = findViewById(getResources().getIdentifier("cell_"+i,"id",getPackageName()));
            if (Integer.parseInt(view.getTag(R.string.tagClosed).toString())==0){
                view.setClickable(true);
                Log.d("VIEW ENABLED 2",String.valueOf(view.isEnabled()));
            }
        }
    }


    private void log(String tag,String text){
        Log.d(tag,text);
    }
    private void log(String tag,int text){
        Log.d(tag,String.valueOf(text));
    }
    private void log(String tag,boolean text){
        Log.d(tag,String.valueOf(text));
    }





    class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            try {
                synchronized (this){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeCells();
                        }
                    });
                }

            }catch (Exception e){
                Log.d("ERROR",e.getStackTrace().toString());
            }
        }
    }




    class AnotherTimerTask extends TimerTask{

        @Override
        public void run() {
            closeCurrentCells();
        }
    }




}

