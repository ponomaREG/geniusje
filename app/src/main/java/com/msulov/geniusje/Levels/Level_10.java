package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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

    private Toast toast;
    private Button backButton, startButton, continueButton, repeatButton;
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
                    startTask();
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
    }




    //BEGIN AND SETUP

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
        TextView task = dialog.findViewById(R.id.dialogTask);
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_10));
        //Находим аватар задания и устанавливаем свой
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.level2_icon));
    }


    //SHOW RESULT
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
                    startActivity(new Intent(Level_10.this, Level_11.class));
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


    //TASK FUNCTIONS
    private void startTask(){
        initCells();
        showCells();
        handler_for_timer = new Handler();
        handler_for_timer.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeCells();
                installOclToCellViews();
            }
        },3000);

    }

    //INIT
    private void initCells(){
        count = 0;
        cells = Memory_game.getArrayOfNumbersForGame(16);
        cells = Memory_game.getShakedArray(cells);
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



    private void installOclToCellViews(){
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        handler_for_timer.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                chossedCell.setText("");
                                second_choosed_cell.setText("");
                                unfrozeViews();
                                hasChoosed = false;
                            }
                        },600);
                    }
                }
                log("VIEW",v.getTag(R.string.tagCellNumber).toString());
            }
        };

        for (TextView cell_view :cells_views){
            cell_view.setOnClickListener(ocl);
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
    private void log(String tag,String text){
        Log.d(tag,text);
    }
    private void log(String tag,int text){
        Log.d(tag,String.valueOf(text));
    }
    private void log(String tag,boolean text){
        Log.d(tag,String.valueOf(text));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.cancel();
    }
}

