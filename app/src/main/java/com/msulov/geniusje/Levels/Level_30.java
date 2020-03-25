package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Mensa;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.Logging.Logging;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_30 extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private Time t;
    private long pressedTime;
    private ImageView task;
    private Mensa mensa;
    private boolean isWin = true;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_30);
        showBeginningDialog();
        initContAndBackButtons();
        generateLayouts();
        initAnswersOcl();
        initVariables();
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
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_30));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.icon));
        TextView level_name = findViewById(R.id.level);
        String level = "30";
        level_name.setText(String.format("%s %s",level,getResources().getString(R.string.level)));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                startActivity(new Intent(Level_30.this, LevelsActivity.class));
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



    private void initVariables(){
        mensa = new Mensa();
        task = findViewById(R.id.task);
        initBottomButtons();
    }


    private void initBottomButtons(){
        findViewById(R.id.finish).setOnClickListener(getOclForBottomButtons());
        findViewById(R.id.skip).setOnClickListener(getOclForBottomButtons());
    }

    private View.OnClickListener getOclForBottomButtons(){
        return view ->{
          switch (view.getId()){
              case R.id.finish:
                  startResultsDialog();
                  break;
              case R.id.skip:
                  mensa.pushSkippedTaskIntoEnd(mensa.getCurrent_question());
//                  int index_of_question = mensa.getCurrent_question() + 1;
////                  if(mensa.ifTaskContainsInSkippedTasks(index_of_question)) {
////                      mensa.incCurrentQuestion();
////                      makeTask();
////                  }else setSkippedQuestion();
                  setSkippedQuestion();
                  break;
//              case R.id.find_next_question:
//                  setSkippedQuestion();
//                  break;
          }
        };
    }

    private void makeTask(){
        drawTask();
    }


    private void setSkippedQuestion(){
        if(mensa.getSkippedQuestion() == -1) startResultsDialog();
        else {
            mensa.setCurrent_question(mensa.getSkippedQuestion());
            makeTask();
        }
    }

    private void generateLayouts(){
        LinearLayout pointsLL = findViewById(R.id.pointsLL);
        for(int i = 0; i < Mensa.COUNT; i++){
            TextView point = (TextView) this.getLayoutInflater().inflate(R.layout.base_point,pointsLL,false);
            point.setTag(R.string.tagPointNumber,i);
            point.setTag(R.string.tagClosed,0);
            pointsLL.addView(point);
        }

    }


    private void initAnswersOcl(){
        View.OnClickListener ocl = getOclForAnswers();
        for(int i = 0;i<6;i++){
            findViewById(getResources().getIdentifier("answer_"+(i+1),"id",getPackageName())).setOnClickListener(ocl);
        }
    }

    private View.OnClickListener getOclForAnswers(){
        return view -> {
            ImageView answer = (ImageView) view;
            String n_answer = (String) answer.getTag();
            if(n_answer.equals(mensa.getCorrectAnswer(mensa.getCurrent_question()))){
                mensa.incScore();
            }
            mensa.popSkippedQuestion(mensa.getCurrent_question());
            setPointChecked(mensa.getCurrent_question());
            setSkippedQuestion();
        };
    }




    private void drawTask(){
        Logging.log("c q",mensa.getCurrent_question());
        Logging.log("c s",mensa.getScore());
        Drawable[] task_and_answers = Mensa.getTaskInfo(mensa.getCurrent_question(),getBaseContext());
        task.setImageDrawable(task_and_answers[0]);
        for(int i = 1;i<7;i++){
            ImageView answer = findViewById(getResources().getIdentifier("answer_"+i,"id",getPackageName()));
            answer.setImageDrawable(task_and_answers[i]);
        }
    }



    private void setPointChecked(int number_of_question){
        LinearLayout pointsLL = findViewById(R.id.pointsLL);
        TextView point = (TextView) pointsLL.getChildAt(number_of_question);
        point.setBackground(getDrawable(R.drawable.points_green_style));

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
                    startActivity(new Intent(Level_30.this, Level_30.class)); //REPEAT
                }else{
                    startActivity(new Intent(Level_30.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(Level_30.this,LevelsActivity.class);
                }else{
                    intent = new Intent(Level_30.this, Level_30.class); //REPEAT
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
            setResultsOnResultsDialog(textResultsDialog,true,true,false,true);
        }


    }

    @SuppressLint("DefaultLocale")
    private void setResultsOnResultsDialog(TextView textResultsDialog, boolean hasCorrect, boolean hasPercent, boolean hasMistakes, boolean isWin){
        String result = "%s%.1f";
        result = String.format(result,getString(R.string.resultDialogTime),t.time);

        if(hasCorrect){
            result = result + " %s%s";
            result = String.format(result,getString(R.string.resultDialogCorrect),mensa.getScore());
        }

        if(hasPercent){
            result = result + " %s%d";
            int percent = (int) (((((float) mensa.getScore())/((float) Mensa.COUNT)))*100);
            result = String.format(result,getString(R.string.resultDialogPercent),percent) +"%";

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
