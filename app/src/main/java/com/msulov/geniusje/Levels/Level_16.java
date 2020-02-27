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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Find_word;
import com.msulov.geniusje.Levels.Managers.Questions;
import com.msulov.geniusje.Levels.Managers.Shaked_words;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.net.Inet4Address;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_16 extends AppCompatActivity {



    private Random random;
    private Toast toast;
    private EditText input_word;
    private Button backButton, startButton, continueButton, repeatButton, sendButton;
    private Dialog dialog;
    private CircleImageView icon;
    private TextView answerLeft, answerRight, point, task, color_task, cell,desc, founded_words;
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
        if((type.equals("Find_word"))||(type.isEmpty())){
            taskdesc_id = R.string.startDialogWindowForLevel_16;
            type = "Find_word";
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
                    startActivity(new Intent(Level_16.this, LevelsActivity.class));
                    finish();
                } else if (v.getId() == R.id.startDialogButton) {
                    dialog.dismiss();
                    new Thread(t, "Time").start();
                }
            }
        };

        startButton = dialog.findViewById(R.id.startDialogButton);
        backButton = dialog.findViewById(R.id.backDialogButton);
        backButton.setOnClickListener(OnClickListener);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(OnClickListener);

        sendButton = findViewById(R.id.buttonSend);

        desc = findViewById(R.id.desc);
    }



    private void makeTask(String type){
        String[][] data;
        Random random = new Random();
        switch (type){
            case ("Find_word"):
                int rand_index = random.nextInt(Find_word.COUNT);
                String[] words = Find_word.getWords(rand_index);
                desc.setText(words[0]);
                desc.setTag(R.string.tagDescTag,rand_index);
//                setCellsTo(words[1],Integer.parseInt(words[2][0]),COUNT);
                break;

        }
    }



    private void initOclForAnswers(){
        count = 0;
        correctAnswer = 0;
        input_word = findViewById(R.id.input_word);
        founded_words = findViewById(R.id.founded_words);


        Log.d("TYPE",type);

        switch (type){
            case("Find_word"):
                ocl = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = (int) desc.getTag();
                        String user_word = input_word.getText().toString();
                        if(Find_word.isExistsAndNotFound(index,user_word)){
                            founded_words.setText(founded_words.getText().toString()+" , "+user_word);
                            Find_word.setKoefhasFounded(index,user_word);
                            count++;
                        }
                        if(count==3){
                            startResultsDialog();
                        }

                    }
                };
                break;


        }

        sendButton.setOnClickListener(ocl);

    }


    @Deprecated
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
                    startActivity(new Intent(Level_16.this, LevelsActivity.class));
                    finish();
                } else if (v.getId() == R.id.ContinueResultsDialog) {
                    Intent intent = null;
                    intent = new Intent(Level_16.this,four_choice.class);
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
        setResultsOnResultsDialog(textResultsDialog,false,false);

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










}
