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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.DBHelper;
import com.msulov.geniusje.Levels.Managers.Memory_game;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class memory_base extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private TextView set_textview;
    private Time t;
    private long pressedTime;
    private int count = 0;
    private int taskdesc_id;
    private LinearLayout taskLY;
    private String type,next_level, level;
    private int[][] indexes_of_pairs_coord;
    private int count_all;
    private boolean isWin = true;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_base);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        Log.d("TYPE",type);
        if(type == null) type = "Get_rhythm";
        switch (type){
            case "Get_rhythm":
                taskdesc_id = R.string.startDialogWindowForLevel_18;
                next_level = "Find_all";
                level = "18";
                break;
            case "Find_all":
                next_level = "None";
                taskdesc_id = R.string.startDialogWindowForLevel_19;
                level = "19";
                break;
        }

        showBeginningDialog();
        initContAndBackButtons();
        makeTask();

    }

    /////BEGINNIG AND RESULTING CONSTANT BLOCKS
    private void showBeginningDialog() {
        t = new Time();

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog);
        setIconAndTask();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    private void setIconAndTask() {
        TextView task = dialog.findViewById(R.id.dialogTask);
        task.setText(getResources().getString(taskdesc_id));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.icon));
        TextView level_name = findViewById(R.id.level);
        level_name.setText(String.format("%s %s",level,getResources().getString(R.string.level)));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                startActivity(new Intent(memory_base.this, LevelsActivity.class));
                finish();
            } else if (v.getId() == R.id.startDialogButton) {
                dialog.dismiss();
                showRhythm();
            }
        };

        Button startButton = dialog.findViewById(R.id.startDialogButton);
        startButton.setOnClickListener(OnClickListener);
        Button backButton = dialog.findViewById(R.id.backDialogButton);
        backButton.setOnClickListener(OnClickListener);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(OnClickListener);
    }


    //TASK FUNCTIONS
    private void makeTask(){
        generateLayouts();
    }


    private void generateLayouts(){
        taskLY = findViewById(R.id.taskLY);
        View.OnClickListener ocl = getOclForCellsSudoku();
        for(int i = 0; i< Memory_game.HEIGHT; i++){
            LinearLayout baseLY = (LinearLayout) this.getLayoutInflater().inflate(R.layout.base_ly_memory, null);
            for(int j = 0 ; j<Memory_game.WIDTH;j++){
                TextView textView = (TextView) baseLY.getChildAt(j);
                textView.setTag(R.string.tagX,j);
                textView.setTag(R.string.tagY,i);
                textView.setTextSize(getResources().getDimension(R.dimen.answerCellTextSizeUltraSmall));
                textView.setOnClickListener(ocl);
            }
            taskLY.addView(baseLY);
        }
    }


    private View.OnClickListener getOclForCellsSudoku(){
        int diffucult = Memory_game.MEDIUM;
//        if(type.equals("Find_all")) diffucult = diffucult+3;
        indexes_of_pairs_coord = Memory_game.getRandomPairsOfIndexes(diffucult);
        count_all = indexes_of_pairs_coord.length;

        return v -> {
            if((set_textview != null)&&(type.equals("Get_rhythm"))) set_textview.setBackground(getDrawable(R.drawable.cell_style));
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
            }
            }
            if(type.equals("Find_all")){
                boolean hasFound = false;
                for(int[] coords:indexes_of_pairs_coord){
                    if ((coords[0] == x) && (coords[1] == y)) {
                        hasFound = true;
                        break;
                    }
                }
                if(hasFound){
                    set_textview.setBackground(getDrawable(R.drawable.cell_style_checked));
                    set_textview.setClickable(false);
                    count++;
                }
                else{
                    set_textview.setBackground(getDrawable(R.drawable.cell_style_error));
                    t.stopTime();
                    isWin = false;
                    startResultsDialog();
                }
                if(count==count_all){
                    t.stopTime();
                    startResultsDialog();
                }
            }
        };
    }


    private void showRhythm(){
        frozeOrUnfrozeViews(false);
        Handler handler = new Handler();
//        if(type.equals("Get_rhythm")) {
        int time_koef = 0;
        int time_of_close = 850;
        for (int[] coords_of_rhythm : indexes_of_pairs_coord) {
            int x = coords_of_rhythm[0];
            int y = coords_of_rhythm[1];
            if(type.equals("Get_rhythm")) {
                Log.d("TIME_KOEF","1");
                time_koef++;
            }
            final TextView textView = (TextView) ((LinearLayout) taskLY.getChildAt(y)).getChildAt(x);

            handler.postDelayed(() -> setViewChecked(textView), 850 * time_koef);
                if(type.equals("Get_rhythm")) time_of_close = time_of_close + 850;


                handler.postDelayed(() -> setViewUnchecked(textView), time_of_close);
            }

            handler.postDelayed(() -> {
                frozeOrUnfrozeViews(true);
                new Thread(t, "Time").start();
            }, time_of_close);
//        }
//        if(type.equals("Find_all")){
//            for(int[] coord_of_rhythm : indexes_of_pairs_coord){
//                int x = coord_of_rhythm[0];
//                int y = coord_of_rhythm[1];
//
//                final TextView textView = (TextView) ((LinearLayout) taskLY.getChildAt(y)).getChildAt(x);
//                setViewChecked(textView);
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        setViewUnchecked(textView);
//                    }
//                },1000);
//
//            }
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    frozeOrUnfrozeViews(true);
//                }
//            },1000);
//        }
    }



    private void frozeOrUnfrozeViews(boolean clickable){
        for(int i = 0 ; i<Memory_game.HEIGHT;i++){
            for(int j = 0;j<Memory_game.WIDTH;j++){
                TextView textView = (TextView) ((LinearLayout) taskLY.getChildAt(i)).getChildAt(j);
                textView.setClickable(clickable);
            }
        }
    }

    private void setViewChecked(TextView textView){
        textView.setBackground(getDrawable(R.drawable.cell_style_checked));
    }


    private void setViewUnchecked(TextView textView){
        textView.setBackground(getDrawable(R.drawable.cell_style));
    }
//TASK FUNCTIONS END
//SHOW RESULT BLOCK
    public void startResultsDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_results);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        if(isWin) new DBHelper(this).setOpenTaskNumberIs(Integer.parseInt(level)+1,t.time);

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.repeatResultsDialog) {
                if(isWin) {
                    startActivity(new Intent(memory_base.this, memory_base.class).putExtra("type", type)); //REPEAT
                }else{
                    startActivity(new Intent(memory_base.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    if(next_level.equals("None")){
                        intent = new Intent(memory_base.this,Miner.class);
                    }else intent = new Intent(memory_base.this, memory_base.class).putExtra("type", next_level); //NEXT LEVEL
                }else{
                    intent = new Intent(memory_base.this, memory_base.class).putExtra("type", type); //REPEAT
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
//SHOW RESULT BLOCK END
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
