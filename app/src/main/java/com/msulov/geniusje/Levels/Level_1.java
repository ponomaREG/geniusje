package com.msulov.geniusje.Levels;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Random;

public class Level_1 extends AppCompatActivity {

    private Random random;
    private Toast toast;
    private Button backButton, startButton, continueButton, repeatButton;
    private Dialog dialog;
    private TextView answerLeft, answerRight, point;
    private Time t;
    private long pressedTime;
    private int left, right, count, correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_1);

        random = new Random(System.currentTimeMillis());

        t = new Time();

        answerLeft = findViewById(R.id.answer_left);
        answerRight = findViewById(R.id.answer_right);
        //dsadsa
        // Вызов диалогового окна - (Начало)
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog);
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
                    startActivity(new Intent(Level_1.this, LevelsActivity.class));
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

        // Обработчик нажатия на "Назад" - (Начало)
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(OnClickListener);
        // Обработчик нажатия на "Назад" - (Конец)

        answerLeft.setTextSize(196);
        answerLeft.setText(String.valueOf((left = random.nextInt(((count + 1) * 10) - 1))));
        answerRight.setTextSize(196);
        answerRight.setText(String.valueOf((right = random.nextInt(((count + 1) * 10) - 1))));

        if (left == right)
            answerRight.setText(String.valueOf(++right));

        // Уровень -1
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                boolean hasCorrect = false;

                if (v.getId() == R.id.answer_left) {
                    if (left > right) {
                        correctAnswer++;
                        hasCorrect = true;
                    }
                }
                if (v.getId() == R.id.answer_right) {
                    if (right > left) {
                        correctAnswer++;
                        hasCorrect = true;
                    }
                }
                answerLeft.setText(String.valueOf((left = random.nextInt(((count + 1) * 10) - 1))));
                answerRight.setText(String.valueOf((right = random.nextInt(((count + 1) * 10) - 1))));

                if (left == right)
                    answerRight.setText(String.valueOf(++right));

                switch (count) {
                    case 1:
                        point = findViewById(R.id.point_1);
                        answerLeft.setTextSize(108);
                        answerRight.setTextSize(108);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 2:
                        point = findViewById(R.id.point_2);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 3:
                        point = findViewById(R.id.point_3);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 4:
                        point = findViewById(R.id.point_4);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 5:
                        point = findViewById(R.id.point_5);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 6:
                        point = findViewById(R.id.point_6);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 7:
                        point = findViewById(R.id.point_7);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 8:
                        point = findViewById(R.id.point_8);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 9:
                        answerLeft.setTextSize(78);
                        answerRight.setTextSize(78);
                        point = findViewById(R.id.point_9);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 10:
                        point = findViewById(R.id.point_10);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 11:
                        point = findViewById(R.id.point_11);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 12:
                        point = findViewById(R.id.point_12);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 13:
                        point = findViewById(R.id.point_13);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 14:
                        point = findViewById(R.id.point_14);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 15:
                        point = findViewById(R.id.point_15);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 16:
                        point = findViewById(R.id.point_16);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 17:
                        point = findViewById(R.id.point_17);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 18:
                        point = findViewById(R.id.point_18);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 19:
                        point = findViewById(R.id.point_19);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        break;
                    case 20:
                        point = findViewById(R.id.point_20);
                        if (hasCorrect) {
                            point.setBackgroundResource(R.drawable.points_green_style);
                        }
                        else  {
                            point.setBackgroundResource(R.drawable.points_red_style);
                        }
                        // Остановим поток времени
                        t.stopTime();
                        startResultsDialog();
                        break;
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
                    startActivity(new Intent(Level_1.this, LevelsActivity.class));
                    finish();
                } else if (v.getId() == R.id.ContinueResultsDialog) {}
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
            super.onBackPressed();
            return;
        } else {
            toast = Toast.makeText(getBaseContext(), R.string.toastExit, Toast.LENGTH_SHORT);
            toast.show();
        }
        //testCOMMIT
        // Получаем время нажатия системной кнопки - Назад
        pressedTime = System.currentTimeMillis();
    }
    // Обработчик нажатия системной кнопки "Назад" - (Конец)
}