package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Color_task;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_5 extends AppCompatActivity {



    private Random random;
    private Toast toast;
    private Button backButton, startButton, continueButton, repeatButton;
    private Dialog dialog;
    private CircleImageView icon;
    private TextView answerLeft, answerRight, point,task, color_task;
    private Time t;
    private long pressedTime;
    private int count, correctAnswer, correct_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_5);
        t = new Time();

        answerLeft = findViewById(R.id.answer_left);
        answerRight = findViewById(R.id.answer_right);
        // Вызов диалогового окна - (Начало)
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog);
        //Находим текст задания и устанавливаем его на свой
        task = dialog.findViewById(R.id.dialogTask);
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_5));
        //Находим аватар задания и устанавливаем свой
        icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.level2_icon));
        // Делаем задний фон прозрачным
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Убираем возможность закрывать системной кнопкой "Назад"
        dialog.setCancelable(false);
        dialog.show();
        // Вызов диалогового окна - (Конец)

        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                    startActivity(new Intent(Level_5.this, LevelsActivity.class));
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
        color_task = findViewById(R.id.color_task);

        // Обработчик нажатия на "Назад" - (Начало)
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(OnClickListener);
        // Обработчик нажатия на "Назад" - (Конец)
        answerLeft.setTextSize(38);
        answerRight.setTextSize(38);
        makeTask();


        // Уровень -1
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                boolean hasCorrect = false;

                if (v.getId() == R.id.answer_left) {
                    if (answerLeft.getCurrentTextColor()==correct_color) {
                        correctAnswer++;
                        hasCorrect = true;
                    }
                }
                if (v.getId() == R.id.answer_right) {
                    if (answerRight.getCurrentTextColor()==correct_color) {
                        correctAnswer++;
                        hasCorrect = true;
                    }
                }

                makeTask();


                if (count==1){
                    point = findViewById(R.id.point_1);
                }else {
                    point = findViewById(getResources().getIdentifier("point_" + count, "id", getPackageName()));
                }
                CheckCorrect(hasCorrect,point);
                if (count==20){
                    t.stopTime();
                    startResultsDialog();
                }

            }
        };

        // Добавляем обработчик к кнопкам
        answerLeft.setOnClickListener(onClickListener);
        answerRight.setOnClickListener(onClickListener);
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
                    startActivity(new Intent(Level_5.this, LevelsActivity.class));
                    finish();
                } else if (v.getId() == R.id.ContinueResultsDialog) {
                    startActivity(new Intent(Level_5.this,Level_4.class));
                    finish();
                }
            }
        };

        continueButton = dialog.findViewById(R.id.ContinueResultsDialog);
        continueButton.setOnClickListener(OnClickListener);
        repeatButton = dialog.findViewById(R.id.repeatResultsDialog);
        repeatButton.setOnClickListener(OnClickListener);

        TextView textResultsDialog = dialog.findViewById(R.id.textResultsDialog);
        textResultsDialog.setText("Time: " + String.format("%.1f", t.time) + "\nCorrect: " + correctAnswer);
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


    private void CheckCorrect(boolean hasCorrect,TextView point){
        if (hasCorrect) {
            point.setBackgroundResource(R.drawable.points_green_style);
        }
        else  {
            point.setBackgroundResource(R.drawable.points_red_style);
        }

    }

    private void makeTask(){
        int random_color = Color_task.getRandomColor();
        String random_text_of_color = Color_task.getRandomTextOfColor();
        //correct_text_of_color = random_text_of_color;
        correct_color = Color_task.getColorOfText(random_text_of_color);

        answerLeft.setTextColor(Color_task.getRandomColor());
        answerRight.setTextColor(Color_task.getRandomColor());

        if (Math.random()>0.5){
            answerLeft.setTextColor(correct_color);
            answerRight.setTextColor(Color_task.getRandomColor());
        }
        else {
            answerLeft.setTextColor(Color_task.getRandomColor());
            answerRight.setTextColor(correct_color);
        }

        answerLeft.setText(String.valueOf(Color_task.getRandomTextOfColor()));
        answerRight.setText(String.valueOf(Color_task.getRandomTextOfColor()));

        color_task.setText(random_text_of_color);
        color_task.setTextColor(random_color);
    }
}
