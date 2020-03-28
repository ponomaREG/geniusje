package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.DBHelper;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class RSL extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private Time t;
    private long pressedTime;
    private ImageView answerBot,userAnswer;
    private boolean isWin = true;
    int diffucult = com.msulov.geniusje.Levels.Managers.RSL.POINTS;
    int count_of_correct_answers = 0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_s_l);
        showBeginningDialog();
        initContAndBackButtons();
        generateLayouts();
        initAnswersOcl();
//        initOclForAnswers();
    }

    /////BEGINNIG AND RESULTING CONSTANT BLOCKS
    private void showBeginningDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog);
        setIconAndTask();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    private void setIconAndTask() {
        TextView task = dialog.findViewById(R.id.dialogTask);
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_21));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.icon));
        TextView level_name = findViewById(R.id.level);
        String level = "21";
        level_name.setText(String.format("%s %s",level,getResources().getString(R.string.level)));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                startActivity(new Intent(RSL.this, LevelsActivity.class));
                finish();
            } else if (v.getId() == R.id.startDialogButton) {
                dialog.dismiss();
                makeTask();
                t = new Time();
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






    private void generateLayouts(){
        LinearLayout pointsLL = findViewById(R.id.pointsLL);
        for(int i = 0;i< com.msulov.geniusje.Levels.Managers.RSL.POINTS;i++){
            TextView point = (TextView) this.getLayoutInflater().inflate(R.layout.base_point,pointsLL,false);
            point.setTag(R.string.tagPointNumber,i);
            pointsLL.addView(point);

        }

    }


    private void initAnswersOcl(){
        findViewById(R.id.scissors).setOnClickListener(getOclForAnswers());
        findViewById(R.id.rock).setOnClickListener(getOclForAnswers());
        findViewById(R.id.letter).setOnClickListener(getOclForAnswers());
    }

    private View.OnClickListener getOclForAnswers(){
        return v -> {
            Handler handler = new Handler();
            userAnswer = (ImageView) v;
            v.setBackgroundColor(getResources().getColor(R.color.colorAnswerBackgroundChecked));
            frozeOrUnfrozeViews(false);
            handler.postDelayed(this::showBotChoiceAndCheckWhoWins,300);
        };
    }

    private void showBotChoiceAndCheckWhoWins(){
        Handler handler = new Handler();
        answerBot = findViewById(R.id.botChoice);
        com.msulov.geniusje.Levels.Managers.RSL rsl_manager = new com.msulov.geniusje.Levels.Managers.RSL();
        int choiceBot = rsl_manager.getRandomFigureForBot();
        switch (choiceBot){
            case 0:
                answerBot.setImageDrawable(getDrawable(R.drawable.r));
                break;
            case 1:
                answerBot.setImageDrawable(getDrawable(R.drawable.s));
                break;
            case 2:
                answerBot.setImageDrawable(getDrawable(R.drawable.l));
                break;
        }

        final int status_win = rsl_manager.checkIsUserWin(Integer.parseInt(userAnswer.getTag().toString()),choiceBot);
        showStatusOfWin(status_win);
        handler.postDelayed(() -> {
            if(status_win==1) plusOnePoint();
            if(status_win==0) minusOnePoint();
        },500);

    }


    private void plusOnePoint(){
        count_of_correct_answers++;
        Log.d("COUNT OF CORRECT ANSWER",String.valueOf(count_of_correct_answers));
        LinearLayout pointsLL = findViewById(R.id.pointsLL);
        TextView point = (TextView) pointsLL.getChildAt(count_of_correct_answers-1);
        point.setBackground(getDrawable(R.drawable.points_green_style));
        if(count_of_correct_answers==diffucult){
            startResultsDialog();
        }

    }

    private void minusOnePoint(){
        count_of_correct_answers--;
        Log.d("COUNT OF CORRECT ANSWER",String.valueOf(count_of_correct_answers));
        if(count_of_correct_answers == -1){
            isWin = false;
            startResultsDialog();
        }else{
            LinearLayout pointsLL = findViewById(R.id.pointsLL);
            TextView point = (TextView) pointsLL.getChildAt(count_of_correct_answers);
            point.setBackground(getDrawable(R.drawable.points_style));
        }
    }

    private void showStatusOfWin(int status_win){
        switch (status_win){
            case 1:
                playWithLightIfUserWin();
                break;
            case 0:
                playWithLightIfBotWin();
                break;
            case -1:
                playWithLightIfNobodyWins();
                break;
        }
    }


    private void setUsersChoiceGreen(){
        userAnswer.setBackgroundColor(getResources().getColor(R.color.colorOfCorrectChoice));
    }
    private void setUsersChoiceDefaultColor(){
        userAnswer.setBackgroundColor(getResources().getColor(R.color.colorAnswerBackground));
    }
    private void setUsersChoiceError(){
        userAnswer.setBackgroundColor(getResources().getColor(R.color.cellError));
    }
    private void setUserChoiceNobodyWins(){
        userAnswer.setBackgroundColor(getResources().getColor(R.color.colorIfNobodyWins));
    }


    private void setBotChoiceGreen(){
        answerBot.setBackgroundColor(getResources().getColor(R.color.colorOfCorrectChoice));
    }
    private void setBotChoiceDefaultColor(){
        answerBot.setBackgroundColor(getResources().getColor(R.color.colorAnswerBackground));
    }
    private void setBotChoiceError(){
        answerBot.setBackgroundColor(getResources().getColor(R.color.cellError));
    }
    private void setBotChoiceNobodyWins(){
        answerBot.setBackgroundColor(getResources().getColor(R.color.colorIfNobodyWins));
    }



    private void playWithLightIfUserWin(){
        Handler handler = new Handler();
        long time = 100;
        setUsersChoiceGreen();
        setBotChoiceError();
        handler.postDelayed(this::setUsersChoiceDefaultColor,time);
        handler.postDelayed(this::setUsersChoiceGreen,time*2);
        handler.postDelayed(this::setUsersChoiceDefaultColor,time*3);
        handler.postDelayed(this::setUsersChoiceGreen,time*4);
        handler.postDelayed(this::clearWall,time*5);

    }


    private void playWithLightIfBotWin(){
        Handler handler = new Handler();
        long time = 100;
        setUsersChoiceError();
        setBotChoiceGreen();
        handler.postDelayed(this::setBotChoiceDefaultColor,time);
        handler.postDelayed(this::setBotChoiceGreen,time*2);
        handler.postDelayed(this::setBotChoiceDefaultColor,time*3);
        handler.postDelayed(this::setBotChoiceGreen,time*4);
        handler.postDelayed(this::clearWall,time*5);

    }


    private void playWithLightIfNobodyWins(){
        Handler handler = new Handler();
        setBotChoiceNobodyWins();
        setUserChoiceNobodyWins();

        handler.postDelayed(this::clearWall,300);
    }

    private void clearWall(){
        setBotChoiceDefaultColor();
        setUsersChoiceDefaultColor();
        frozeOrUnfrozeViews(true);
        answerBot.setImageDrawable(getDrawable(R.drawable.question));
    }


    private void frozeOrUnfrozeViews(boolean clickable){
        int[] ids = new int[]{R.id.scissors,R.id.letter,R.id.rock};
        for(int id:ids){
            ImageView answerForFroze = findViewById(id);
            answerForFroze.setClickable(clickable);
        }
    }


    public void startResultsDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_results);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        if(isWin) new DBHelper(this).setOpenTaskNumberIs(22,t.time);

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.repeatResultsDialog) {
                if(isWin) {
                    startActivity(new Intent(RSL.this, RSL.class)); //REPEAT
                }else{
                    startActivity(new Intent(RSL.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(RSL.this,Level_22.class);
                }else{
                    intent = new Intent(RSL.this, RSL.class); //REPEAT
                }
                startActivity(intent);
                finish();
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

        int correctAnswer = 0;
        if(hasCorrect){
            result = result + " %s%s";
            result = String.format(result,getString(R.string.resultDialogCorrect), correctAnswer);
        }

        if(hasPercent){
            result = result + " %s%d";
            int count = 0;
            int percent = (int) (((((float) correctAnswer)/((float) count)))*100);
            result = String.format(result,getString(R.string.resultDialogPercent),percent);

        }

        if(hasMistakes){
            result = result + " %s%d";
            int mistakes = 0;
            result = String.format(result,getString(R.string.resultDialogMistakes), mistakes);
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






    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.cancel();
    }



}
