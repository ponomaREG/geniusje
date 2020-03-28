package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.DBHelper;
import com.msulov.geniusje.Levels.Managers.Colors_shaker;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_22 extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private TextView feedback;
    private Time t;
    private long pressedTime;
    private ImageView answerBot;
    private ImageView userAnswer;
    private Colors_shaker colors_shaker;
    private boolean isWin = true;
    private int diffucult = Colors_shaker.DIFFICULT;
    private int count_of_correct_answers = 0,color_current,count_of_mixed = 0,color_bot = 0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_22);

        showBeginningDialog();
        initContAndBackButtons();

        generateLayouts();
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
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_22));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.icon));
        TextView level_name = findViewById(R.id.level);
        String level = "22";
        level_name.setText(String.format("%s %s",level,getResources().getString(R.string.level)));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                startActivity(new Intent(Level_22.this, LevelsActivity.class));
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

        feedback = findViewById(R.id.feedback);
        colors_shaker = new Colors_shaker();
    }


    private void makeTask(){
        fillBotColor();
    }

    private void generateLayouts(){
        LinearLayout pointsLL = findViewById(R.id.pointsLL);
        for(int i = 0;i< diffucult;i++){
            TextView point = (TextView) this.getLayoutInflater().inflate(R.layout.base_point,pointsLL,false);
            point.setTag(R.string.tagPointNumber,i);
            pointsLL.addView(point);
        }
        LinearLayout colorsLL = findViewById(R.id.colorsAnswers);
        ImageView resetImage = findViewById(R.id.reset);
        View.OnClickListener ocl = getOclForAnswers();
        resetImage.setOnClickListener(ocl);
        for(int i = 0;i< Colors_shaker.COUNT;i++){
            ImageView colorAnswer = (ImageView) this.getLayoutInflater().inflate(R.layout.base_imageview,colorsLL,false);
            colorAnswer.setOnClickListener(ocl);
            colorAnswer.setTag(R.string.tagAnswerNumber,i);
            GradientDrawable drawable = (GradientDrawable) colorAnswer.getBackground();
            drawable.setColor(getResources().getColor(Colors_shaker.ID_COLORS[i]));
            colorsLL.addView(colorAnswer);
        }
        answerBot = findViewById(R.id.botChoice);


    }

    private View.OnClickListener getOclForAnswers(){
        return v -> {
            userAnswer = (ImageView) v;
            if(userAnswer.getId()==R.id.reset){
                clearFeedBack();
            }else{
                addColorToFeedBack();
            }
        };
    }


    private void addColorToFeedBack(){

        GradientDrawable drawable = (GradientDrawable) feedback.getBackground();
        int color_to_mixed = (getResources().getColor(Colors_shaker.ID_COLORS[Integer.parseInt(userAnswer.getTag(R.string.tagAnswerNumber).toString())]));
        count_of_mixed++;
        if(Integer.parseInt(feedback.getTag().toString())==1) {
            color_current = colors_shaker.mixColorsValue(color_current,color_to_mixed,count_of_mixed);
            drawable.setColor(color_current);
        }else{
            feedback.setTag(1);
            color_current = color_to_mixed;
            drawable.setColor(color_to_mixed);
        }
        if((color_current+300000 > color_bot)&&(color_current-300000 < color_bot)&&(count_of_mixed<=diffucult+diffucult/3)){
            makeTask();
            clearFeedBack();
            plusOnePoint();
        }else if ( count_of_mixed == 3) clearFeedBack();
    }

    private void clearFeedBack(){
        GradientDrawable drawable = (GradientDrawable) feedback.getBackground();
        color_current = getResources().getColor(R.color.colorAnswerBackground);
        drawable.setColor(color_current);
        feedback.setTag(0);
        count_of_mixed = 0;
    }



    private void fillBotColor(){
        int[] colorsMixed = colors_shaker.setResources(getResources()).shake().mix(Colors_shaker.DIFFICULT).get();
        color_bot = 0;
        for(int i = 0; i< colorsMixed.length; i++){
            if(color_bot == 0){
                color_bot = getResources().getColor(colorsMixed[i]);
            }else{
                color_bot = colors_shaker.mixColorsValue(color_bot,getResources().getColor(colorsMixed[i]),i+1);
            }
        }
        GradientDrawable drawable = (GradientDrawable) answerBot.getBackground();
        drawable.setColor(color_bot);
    }

    private void plusOnePoint(){
        count_of_correct_answers++;
        LinearLayout pointsLL = findViewById(R.id.pointsLL);
        TextView point = (TextView) pointsLL.getChildAt(count_of_correct_answers-1);
        point.setBackground(getDrawable(R.drawable.points_green_style));
        if(count_of_correct_answers==diffucult){
            startResultsDialog();
        }
    }


    public void startResultsDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_results);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        if(isWin) new DBHelper(this).setOpenTaskNumberIs(23,t.time);

        View.OnClickListener OnClickListener = v -> {
            clearALL();
            if (v.getId() == R.id.repeatResultsDialog) {
                if(isWin) {
                    startActivity(new Intent(Level_22.this, Level_22.class)); //REPEAT
                }else{
                    startActivity(new Intent(Level_22.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(Level_22.this,Level_23.class);
                }else{
                    intent = new Intent(Level_22.this, Level_22.class); //REPEAT
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

        pressedTime = System.currentTimeMillis();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.cancel();
        clearALL();

    }



    private void clearALL(){
        GradientDrawable gradientDrawable = (GradientDrawable) getDrawable(R.drawable.cell_style);
        assert gradientDrawable != null;
        gradientDrawable.setColor(getResources().getColor(R.color.colorAnswerBackground));
        GradientDrawable drawable  = (GradientDrawable) answerBot.getBackground();
        drawable.setColor(getResources().getColor(R.color.colorAnswerBackground));
    }



}
