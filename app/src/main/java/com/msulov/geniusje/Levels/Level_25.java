package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Horse_check;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_25 extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private Time t;
    private long pressedTime;
    private LinearLayout taskLY;
    private int[][] indexes;
    private ImageView set_view;
    private boolean isWin = true;
    private int count_of_correct_answers = 0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_25);

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
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_25));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.icon));
        TextView level_name = findViewById(R.id.level);
        String level = "25";
        level_name.setText(String.format("%s %s",level,getResources().getString(R.string.level)));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                startActivity(new Intent(Level_25.this, LevelsActivity.class));
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
        initAnother();
    }


    private void initAnother(){
        updateIndexes(Horse_check.BEGIN_X,Horse_check.BEGIN_Y);

    }






    private void generateLayouts(){
        taskLY = findViewById(R.id.taskLY);
        View.OnClickListener ocl = getOclForCells();

        for(int i = 0;i < Horse_check.HEIGHT;i++) {
            LinearLayout baseLL = (LinearLayout) this.getLayoutInflater().inflate(R.layout.base_linearlayout,taskLY,false);
            for (int j = 0;j < Horse_check.WIDTH;j++){

                ImageView base_cell = (ImageView) this.getLayoutInflater().inflate(R.layout.base_cell_imageview,baseLL,false);
                base_cell.setTag(R.string.tagX,j);
                base_cell.setTag(R.string.tagY,i);
                base_cell.setTag(R.string.tagIsCorrect,0);
                if((j==Horse_check.BEGIN_X)&&(i==Horse_check.BEGIN_Y)) {
                    base_cell.setImageDrawable(getDrawable(R.drawable.horse));
                    base_cell.setTag(R.string.tagClosed,1);
                    base_cell.setBackground(getDrawable(R.drawable.cell_style_checked));
                    base_cell.setClickable(false);
                    set_view = base_cell;
                    count_of_correct_answers++;
                }else base_cell.setTag(R.string.tagClosed,0);
                base_cell.setOnClickListener(ocl);
                baseLL.addView(base_cell);

            }
            taskLY.addView(baseLL);
        }


    }


    private View.OnClickListener getOclForCells(){
        return v -> {
            for(int[] index:indexes){
                if((index[0]>=0)&&(index[0]<Horse_check.WIDTH)&&(index[1]>=0)&&(index[1]<Horse_check.HEIGHT)){
                    if((index[0]==((Integer) v.getTag(R.string.tagX)))&&((index[1])==((Integer) v.getTag(R.string.tagY)))) {
                        v.setBackground(getDrawable(R.drawable.cell_style_checked));
                        swapImagesOnViews((ImageView) v, set_view);
                        set_view.setClickable(false);
                        set_view = (ImageView) v;
                        count_of_correct_answers++;
                        updateIndexes((Integer) v.getTag(R.string.tagX),(Integer) v.getTag(R.string.tagY));
                        break;
                    }
                }
            }

        };
    }


    private void swapImagesOnViews(ImageView view,ImageView set_view){
        view.setImageDrawable(getDrawable(R.drawable.horse));
        set_view.setImageDrawable(null);
    }

    private void updateIndexes(int x ,int y){
        indexes = Horse_check.getIndexesForHorse(x,y);
        if(!checkIndexesForIsAnythingClickableCell(indexes)){
            if(!checkIndexesForIsUserWin()) {
                isWin = false;
            }
            startResultsDialog();
        }
    }

    private boolean checkIndexesForIsUserWin(){
        return count_of_correct_answers == Horse_check.HEIGHT * Horse_check.WIDTH;
    }

    private boolean checkIndexesForIsAnythingClickableCell(int[][] indexes){
        for(int[] index:indexes){
            int x = index[0];
            int y = index[1];
            Log.d("X/Y",x+" "+y);
            if((x>=0)&&(x<Horse_check.WIDTH)&&(y>=0)&&(y<Horse_check.HEIGHT)) {
                LinearLayout baseLY = (LinearLayout) taskLY.getChildAt(y);
                ImageView cell = (ImageView) baseLY.getChildAt(x);
                if (cell.isClickable()) {
                    return true;
                }
            }
        }
        return false;
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
                    startActivity(new Intent(Level_25.this, Level_25.class)); //REPEAT
                }else{
                    startActivity(new Intent(Level_25.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(Level_25.this,Level_26.class);
                }else{
                    intent = new Intent(Level_25.this, Level_25.class); //REPEAT
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
    }

}
