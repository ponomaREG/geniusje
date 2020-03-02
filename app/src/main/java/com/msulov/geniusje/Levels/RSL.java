package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Memory_game;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class RSL extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private TextView set_textview;
    private Time t;
    private long pressedTime;
    private int count = 0, correctAnswer,mistakes = 0;
    private int taskdesc_id;
    private LinearLayout baseLY,taskLY;
    private String type,next_level;
    private int[][] indexes_of_pairs_coord;
    private int count_all;
    private boolean isWin = true;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_base);

        Intent intent = new Intent();
        type = intent.getStringExtra("type");
        if(type == null) type = "Get_rhythm";
        switch (type){
            case "Get_rhythm":
                taskdesc_id = R.string.startDialogWindowForLevel_18;
                next_level = "Find_all";
                break;
            case "Find_all":
                next_level = "None";
                taskdesc_id = R.string.startDialogWindowForLevel_19;
                break;
        }

        showBeginningDialog();
        initContAndBackButtons();

        makeTask();
//        initOclForAnswers();
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
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Убираем возможность закрывать системной кнопкой "Назад"
        dialog.setCancelable(false);
        dialog.show();
        // Вызов диалогового окна - (Конец)
    }

    private void setIconAndTask() {
        TextView task = dialog.findViewById(R.id.dialogTask);
        task.setText(getResources().getString(taskdesc_id));
        //Находим аватар задания и устанавливаем свой
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.level3_icon));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                    startActivity(new Intent(RSL.this, LevelsActivity.class));
                    finish();
                } else if (v.getId() == R.id.startDialogButton) {
                    dialog.dismiss();
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

    }




    private View.OnClickListener getOclForCellsSudoku(){
        int diffucult = Memory_game.MEDIUM;
        if(type.equals("Find_all")) diffucult = diffucult+3;
        indexes_of_pairs_coord = Memory_game.getRandomPairsOfIndexes(diffucult);
        count_all = indexes_of_pairs_coord.length;

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(set_textview != null) set_textview.setBackground(getDrawable(R.drawable.cell_style));

                int x = (int) v.getTag(R.string.tagX);
                int y = (int) v.getTag(R.string.tagY);
                set_textview = (TextView) ((LinearLayout) taskLY.getChildAt(y)).getChildAt(x);
                if(type.equals("Get_rhythm")){
                    if(((x==indexes_of_pairs_coord[count][0])&&(y==indexes_of_pairs_coord[count][1]))){
                        set_textview.setBackground(getDrawable(R.drawable.cell_style_checked));
                    }else{
                        set_textview.setBackground(getDrawable(R.drawable.cell_style_error));
                        t.stopTime();
                        isWin = false;
                        startResultsDialog();
                    }
                    count++;
                    if((count==count_all)&&(isWin)){
                        t.stopTime();
                        startResultsDialog();
                    };
                }
                if(type.equals("Find_all")){
                    boolean hasFound = false;
                    for(int[] coords:indexes_of_pairs_coord){
                        if((coords[0]==x)&&(coords[1]==y)){
                            set_textview.setBackground(getDrawable(R.drawable.cell_style_checked));

                        }else{
                            set_textview.setBackground(getDrawable(R.drawable.cell_style_error));
                            t.stopTime();
                            isWin = false;
                            startResultsDialog();
                        }
                    }
                    if(count==count_all){
                        t.stopTime();
                        startResultsDialog();
                    }
                }
            }
        };
        return ocl;
    }


    @Deprecated
    private void frozeOrUnfrozeViews(boolean clickable){
        for(int i = 0 ; i<Memory_game.HEIGHT;i++){
            for(int j = 0;j<Memory_game.WIDTH;j++){
                TextView textView = (TextView) ((LinearLayout) taskLY.getChildAt(i)).getChildAt(j);
                textView.setClickable(clickable);
            }
        }
    }

    @Deprecated
    private void setViewChecked(TextView textView){
        textView.setBackground(getDrawable(R.drawable.cell_style_checked));
    }

    @Deprecated
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
                        startActivity(new Intent(RSL.this, memory_base.class).putExtra("type", type)); //REPEAT
                    }else{
                        startActivity(new Intent(RSL.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                    }
                    finish();
                } else if (v.getId() == R.id.ContinueResultsDialog) {
                    Intent intent;// = null;
                    if(isWin) {
                        if(next_level.equals("None")){
                            intent = new Intent(RSL.this,four_choice.class);
                        }else intent = new Intent(RSL.this, memory_base.class).putExtra("type", next_level); //NEXT LEVEL
                    }else{
                        intent = new Intent(RSL.this, memory_base.class).putExtra("type", type); //REPEAT
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




//    private void startResultDialogForRepeating(){
//
//        Dialog dialog = new Dialog(this);
//        dialog.setContentView();
//
//    }

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
