package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Cells;
import com.msulov.geniusje.Levels.Managers.Randomize_number;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_11 extends AppCompatActivity {


    private Toast toast;
    private Button backButton, startButton, continueButton, repeatButton;
    private Dialog dialog;
    private CircleImageView icon;
    private TextView task, cell;
    private Time t;
    private long pressedTime;
    private int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_11);
        showBeginningDialog();
        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                    startActivity(new Intent(Level_11.this, LevelsActivity.class));
                    finish();
                } else if (v.getId() == R.id.startDialogButton) {
                    dialog.dismiss();
                    new Thread(t, "Time").start();
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
        makeTask();


        // Уровень -1

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
                    startActivity(new Intent(Level_11.this, LevelsActivity.class));
                    finish();
                } else if (v.getId() == R.id.ContinueResultsDialog) {
                    startActivity(new Intent(Level_11.this, four_choice.class).putExtra("type","Shaked_words"));
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

    private void makeTask() {
        generateLayouts();
    }

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
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_9));
        //Находим аватар задания и устанавливаем свой
        icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.level2_icon));
    }


    private void generateLayouts(){
        final int HEIGHT = Randomize_number.HEIGHT;

        int[] coord = Randomize_number.getRandomCoordOfCell();
        String[] pair = Randomize_number.getRandomNumbers();

//        final int WIDTH = 10;
//        LinearLayout.LayoutParams params_ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//        LinearLayout ll = new LinearLayout(getApplicationContext());
//        params_ll.setMargins(0,10,0,0);



        LinearLayout taskLL = findViewById(R.id.taskLY);
        LinearLayout baseLL = (LinearLayout) this.getLayoutInflater().inflate(R.layout.base_ly,null);
        log("CHILDS",taskLL.getChildCount());

        for (int i = 0;i<HEIGHT;i++){
//            for (int j = 0;j<WIDTH;j++){
////                if(baseTV.getParent() != null){
////                    ((ViewGroup) baseTV.getParent()).removeView(baseTV);
////                }
//                baseLL.addView(baseTV);
//                baseTV = (TextView) this.getLayoutInflater().inflate(R.layout.base_textview,null);
//                log("CHILDS BASE",baseLL.getChildCount());
//            }
            log("SIZE",baseLL.getChildCount());
            for(int j = 0;j<Randomize_number.WIDTH;j++){
                log("I J",(i + " " + j));
                TextView tv = (TextView) baseLL.getChildAt(j);
                tv.setTextSize(Randomize_number.TEXT_SIZE);
                if((coord[0]==j)&&(coord[1]==i)){
                    tv.setText(pair[1]);
                    tv.setOnClickListener(getOcl());
                    log("I J ANSWER",(i + " " + j));
                }else{
                    tv.setText(pair[0]);
                }
            }

            taskLL.addView(baseLL);
            baseLL = (LinearLayout) this.getLayoutInflater().inflate(R.layout.base_ly,null);
        }
    }

    private View.OnClickListener getOcl(){
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startResultsDialog();
            }
        };
        return ocl;
    }


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
