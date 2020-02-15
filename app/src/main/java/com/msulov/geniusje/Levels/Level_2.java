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

import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_2 extends AppCompatActivity {


    private Random random;
    private Toast toast;
    private Button backButton, startButton, continueButton, repeatButton;
    private Dialog dialog;
    private CircleImageView icon;
    private TextView answerLeft, answerRight, point,task ;
    private Time t;
    private long pressedTime;
    private int left, right, count, correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_2);
        random = new Random(System.currentTimeMillis());
        t = new Time();

        answerLeft = findViewById(R.id.answer_left);
        answerRight = findViewById(R.id.answer_right);
        // Вызов диалогового окна - (Начало)
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog);
        task = dialog.findViewById(R.id.dialogTask);
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_2));
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
                    startActivity(new Intent(Level_2.this, LevelsActivity.class));
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
                    if (left < right) {
                        correctAnswer++;
                        hasCorrect = true;
                    }
                }
                if (v.getId() == R.id.answer_right) {
                    if (right < left) {
                        correctAnswer++;
                        hasCorrect = true;
                    }
                }
                answerLeft.setText(String.valueOf((left = random.nextInt(((count + 1) * 10) - 1))));
                answerRight.setText(String.valueOf((right = random.nextInt(((count + 1) * 10) - 1))));

                if (left == right)  answerRight.setText(String.valueOf(++right));

                if (count==1){
                    point = findViewById(R.id.point_1);
                    answerLeft.setTextSize(108);
                    answerRight.setTextSize(108);
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
                    startActivity(new Intent(Level_2.this, LevelsActivity.class));
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


}

