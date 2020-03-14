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

import com.msulov.geniusje.Levels.Managers.Questions;
import com.msulov.geniusje.Levels.Managers.Shaked_words;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.net.Inet4Address;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class four_choice extends AppCompatActivity {



    private Random random;
    private Toast toast;
    private Button backButton, startButton, continueButton, repeatButton;
    private Dialog dialog;
    private CircleImageView icon;
    private TextView answerLeft, answerRight, point, task, color_task, cell,desc;
    private Time t;
    private long pressedTime;
    private int count, correctAnswer, correct_color;
    private int[] array_of_numbers;
    private String type;
    private int taskdesc_id;
    private View.OnClickListener ocl;

    private final int COUNT = 4;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four_choice);

        Intent intent = getIntent();

        type = intent.getStringExtra("type");
        if(type == null) {
            type = "Shaked_words";
            taskdesc_id = R.string.startDialogWindowForLevel_12;
        }
        else if((type.equals("Shaked_words"))){
            type = "Shaked_words";
            taskdesc_id = R.string.startDialogWindowForLevel_12;
        }
        else if((type.equals("Question_1"))){
                type = "Question_1";
                taskdesc_id = R.string.startDialogWindowForLevel_13;
        }
        else if((type.equals("Question_2"))){
            type = "Question_2";
            taskdesc_id = R.string.startDialogWindowForLevel_14;
        }
        else if((type.equals("Question_3"))){
            type = "Question_3";
            taskdesc_id = R.string.startDialogWindowForLevel_15;
        }else{
            type = "Shaked_words";
            taskdesc_id = R.string.startDialogWindowForLevel_12;
        }



        showBeginningDialog();
        initContAndBackButtons();
        makeTask(type);
        initOclForAnswers();




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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Убираем возможность закрывать системной кнопкой "Назад"
        dialog.setCancelable(false);
        dialog.show();
        // Вызов диалогового окна - (Конец)
    }

    private void setIconAndTask() {
        task = dialog.findViewById(R.id.dialogTask);
        task.setText(getResources().getString(taskdesc_id));
        //Находим аватар задания и устанавливаем свой
        icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.level2_icon));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                    startActivity(new Intent(four_choice.this, LevelsActivity.class));
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


        desc = findViewById(R.id.desc);
    }



    private void makeTask(String type){
        String[][] data;
        switch (type){
            case ("Shaked_words"):
                String[][] words = (new Shaked_words()).getInfoForTask(COUNT,true);
                desc.setText(words[0][0]);
                setCellsTo(words[1],Integer.parseInt(words[2][0]),COUNT);
                break;
            case ("Question_1"):
                data = Questions.getRandomData(Questions.TYPE_MATH);
                desc.setText(data[0][0]);
                setCellsTo(data[1], Integer.parseInt(data[2][0]),COUNT);
                break;
            case ("Question_2"):
                data = Questions.getRandomData(Questions.TYPE_CHEMIC);
                desc.setText(data[0][0]);
                setCellsTo(data[1], Integer.parseInt(data[2][0]),COUNT);
                break;
            case ("Question_3"):
                data = Questions.getRandomData(Questions.TYPE_INF);
                desc.setText(data[0][0]);
                setCellsTo(data[1], Integer.parseInt(data[2][0]),COUNT);
                break;

        }
    }



    private void initOclForAnswers(){
        count = 0;
        correctAnswer = 0;
        Log.d("TYPE",type);

        switch (type){
            case("Shaked_words"):
                ocl = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count++;
                        if((int)v.getTag(R.string.tagIsCorrect) == 1){
                            correctAnswer++;
                        }
                        if(count==5){
                            startResultsDialog();
                        }
                        makeTask(type);

                    }
                };
                break;

            default:
                ocl = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count++;
                        if((int)v.getTag(R.string.tagIsCorrect) == 1){
                            correctAnswer++;
                        }
                        if(count==3){
                            startResultsDialog();
                        }
                        makeTask(type);
                    }
                };
                break;
        }
        for (int i = 1;i<(COUNT+1);i++){
            TextView textView = findViewById(getResources().getIdentifier("answer_"+i,"id",getPackageName()));
            textView.setTextSize(getResources().getDimension(R.dimen.answerCellTextSize));
            textView.setOnClickListener(ocl);
        }

    }



    private void setCellsTo(String[] answers,int index_of_random_word,int count){
        for (int i = 0;i<count;i++){
            int tag;
            ((TextView) findViewById(getResources().getIdentifier("answer_"+(i+1),"id",getPackageName()))).setText(answers[i]);
            tag = 0;
            if(index_of_random_word == i){
                tag = 1;
                (findViewById(getResources().getIdentifier("answer_"+(i+1),"id",getPackageName()))).setTag(R.string.tagIsCorrect,tag);
            }
            (findViewById(getResources().getIdentifier("answer_"+(i+1),"id",getPackageName()))).setTag(R.string.tagIsCorrect,tag);

        }
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
                    startActivity(new Intent(four_choice.this, LevelsActivity.class));
                    finish();
                } else if (v.getId() == R.id.ContinueResultsDialog) {
                    Intent intent = null;
                    if(type.equals("Shaked_words")) intent = new Intent(four_choice.this,four_choice.class).putExtra("type","Question_1");
                    if(type.equals("Question_1")) intent = new Intent(four_choice.this,four_choice.class).putExtra("type","Question_2");
                    if(type.equals("Question_2")) intent = new Intent(four_choice.this,four_choice.class).putExtra("type","Question_3");
                    if(type.equals("Question_3")) intent = new Intent(four_choice.this,Level_16.class).putExtra("type","Find_word");
                    startActivity(intent);
                    finish();
                }
            }
        };

        continueButton = dialog.findViewById(R.id.ContinueResultsDialog);
        continueButton.setOnClickListener(OnClickListener);
        repeatButton = dialog.findViewById(R.id.repeatResultsDialog);
        repeatButton.setOnClickListener(OnClickListener);

        TextView textResultsDialog = dialog.findViewById(R.id.textResultsDialog);
        setResultsOnResultsDialog(textResultsDialog,true,true);

    }

    private void setResultsOnResultsDialog(TextView textResultsDialog,boolean hasCorrect,boolean hasPercent){
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
