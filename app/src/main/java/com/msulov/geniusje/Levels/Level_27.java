package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Bones;
import com.msulov.geniusje.Levels.Managers.Player;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_27 extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private Time t;
    private long pressedTime;
    private Player user,bot;
    private boolean isWin = true;
    private Button writeScore , throwCube;
    private TextView botScores,botTotalScores,userScores,userTotalScores, mainScore,status;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_27);

        showBeginningDialog();
        initContAndBackButtons();
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
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_27));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.icon));
        TextView level_name = findViewById(R.id.level);
        String level = "27";
        level_name.setText(String.format("%s %s",level,getResources().getString(R.string.level)));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                startActivity(new Intent(Level_27.this, LevelsActivity.class));
                finish();
            } else if (v.getId() == R.id.startDialogButton) {
                dialog.dismiss();
                t = new Time();
                new Thread(t, "Time").start();
                makeTask();
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
        init();
        decideWhoGoesFirst_UserOrBot();
    }

    private void init(){
        initVariables();
        initOclForButtons();
    }

    private void initVariables(){
        user = new Player();
        bot = new Player();

        writeScore = findViewById(R.id.writeScore);
        throwCube = findViewById(R.id.throwCube);

        botScores = findViewById(R.id.botScores);
        botTotalScores = findViewById(R.id.botTotalScores);

        userScores = findViewById(R.id.userScores);
        userTotalScores = findViewById(R.id.userTotalScores);

        mainScore = findViewById(R.id.number);
        status = findViewById(R.id.description);
    }

    private void initOclForButtons(){


        writeScore.setOnClickListener(getOclForButtonWriteScore());
        throwCube.setOnClickListener(getOclForButtonThrowCube());

    }

    private View.OnClickListener getOclForButtonWriteScore(){
        return view -> playerWritesScore();
    }

    private View.OnClickListener getOclForButtonThrowCube(){
        return view -> playerMakeThrow();
    }


    private void decideWhoGoesFirst_UserOrBot(){
        if(Bones.whoIsFirst()==1) letThePlayerThrow();
        else letTheBotThrow();
    }



    private void letThePlayerThrow(){
        setStatusThatPlayerThrows();
        writeScore.setEnabled(true);
        throwCube.setEnabled(true);
    }

    private void preventThePlayerFromThrowing(){
        writeScore.setEnabled(false);
        throwCube.setEnabled(false);
    }

    private void playerWritesScore(){
        user.incCurrentScore(user.getVarScores());
        clearAllForUser();
        updateTextViewUserCurrentScores();
        checkIsAnybodyWins();
        letTheBotThrow();
    }

    private void playerMakeThrow(){
        int score = Bones.getRandomOfScore();
        if(score != 0){
            changeTextViewMainScore(String.valueOf(score));
            user.incVarScores(score);
            changeTextViewUserVarScores(score);
            changeTextViewUserTotalScoresWithShowingVarScores(user.getCurrentScore(),user.getVarScores());
        }else{
            changeTextViewMainScore("X");
            preventThePlayerFromThrowing();
            clearAllForUserWithDelay();
        }
    }

    private void clearAllForUserWithDelay(){
        Handler delay = new Handler();
        delay.postDelayed(() -> {
            clearAllForUser();
            letTheBotThrow();
        },1000);
    }


    private void clearAllForUser(){
        clearTextViewNUMBER();
        clearTextViewUserScores();
        clearTextViewUserTotalScores(user.getCurrentScore());
        user.clearVarScores();
    }

    private void changeTextViewUserVarScores(int score){
        String text = userScores.getText().toString();
        userScores.setText(String.format("%s + "+text,String.valueOf(score)));
    }

    @SuppressLint("DefaultLocale")
    private void changeTextViewUserTotalScoresWithShowingVarScores(int currentScore, int varScore){
        userTotalScores.setText(String.format("%d+(%d)",currentScore,varScore));
    }

    private void clearTextViewUserScores(){
        userScores.setText(" ");
    }

    private void clearTextViewUserTotalScores(int currentScore){
        userTotalScores.setText(String.valueOf(currentScore));
    }

    private void updateTextViewUserCurrentScores(){
        userTotalScores.setText(String.valueOf(user.getCurrentScore()));
    }












    private void letTheBotThrow(){
        setStatusThatBotThrows();
        preventThePlayerFromThrowing();
        //MAKE BOT THROWES
        botMakesThrowWithDelay();
    }

    private void botMakesThrowWithDelay(){
        Handler delay = new Handler();
        delay.postDelayed(this::botMakesThrow,1000);
    }


    private void botMakesThrow(){
        bot.incCountThrows(1);
        int score = Bones.getRandomOfScore();
        if(score!=0){
            changeTextViewMainScore(String.valueOf(score));
            bot.incVarScores(score);
            changeTextViewBotVarScores(score);
            changeTextViewBotTotalScoresWithShowingVarScores(bot.getCurrentScore(),bot.getVarScores());
            botMakesChoice_ThrowOrWrite();
        }else{
            changeTextViewMainScore("X");
            clearAllForBotWithDelay();
        }
    }

    private void clearAllForBotWithDelay(){
        Handler delay = new Handler();
        delay.postDelayed(() -> {
            clearAllForBot();
            letThePlayerThrow();
        },1000);
    }


    private void botMakesChoice_ThrowOrWrite(){
        if(Math.random()<(0.8-(float) bot.getCountThrows()/10)) {
            botMakesThrowWithDelay();
        }
        else {
            botWritesScoreWithDelay();
        }
    }



    private void changeTextViewBotVarScores(int score){
        String text = botScores.getText().toString();
        botScores.setText(String.format("%s + "+text,String.valueOf(score)));
    }

    private void changeTextViewBotTotalScoresWithShowingVarScores(int currentScore,int varScore){
        botTotalScores.setText(String.format("%s+(%s)",currentScore,varScore));
    }


    private void clearAllForBot(){
        clearTextViewNUMBER();
        clearTextViewBotScores();
        clearTextViewBotTotalScores(bot.getCurrentScore());
        bot.clearCountThrows();
        bot.clearVarScores();

    }

    private void clearTextViewBotScores(){
        botScores.setText(" ");
    }

    private void clearTextViewBotTotalScores(int currentScore){
        botTotalScores.setText(String.valueOf(currentScore));
    }

    private void botWritesScoreWithDelay(){
        Handler delay = new Handler();
        delay.postDelayed(this::botWritesScore,1000);
    }


    private void botWritesScore(){
        bot.incCurrentScore(bot.getVarScores());
        clearAllForBot();
        updateTextViewBotCurrentScores();
        checkIsAnybodyWins();
        letThePlayerThrow();
    }


    private void updateTextViewBotCurrentScores(){
        botTotalScores.setText(String.valueOf(bot.getCurrentScore()));
    }









    private void checkIsAnybodyWins(){
        if(user.getCurrentScore()>=Bones.WIN_SCORE) {
            isWin = true;
            startResultsDialog();
        }
        if(bot.getCurrentScore()>=Bones.WIN_SCORE){
            isWin = false;
            startResultsDialog();
        }
    }

    private void changeTextViewMainScore(String score){
        mainScore.setText(score);
    }

    private void clearTextViewNUMBER(){
        mainScore.setText(" ");
    }

    private void setStatusThatPlayerThrows(){
        status.setText(getResources().getString(R.string.playerChoice));
    }

    private void setStatusThatBotThrows(){
        status.setText(getResources().getString(R.string.botChoice));
    }









    public void startResultsDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_results);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.repeatResultsDialog) {
                if(isWin) {
                    startActivity(new Intent(Level_27.this, Level_27.class)); //REPEAT
                }else{
                    startActivity(new Intent(Level_27.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(Level_27.this,Crossword.class);
                }else{
                    intent = new Intent(Level_27.this, Level_27.class); //REPEAT
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
