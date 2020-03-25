package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Miner_manager;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Miner extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private TextView set_textview;
    private Time t;
    private long pressedTime;
    private int mistakes = 0;
    private LinearLayout taskLY;
    private int[][] indexes_bombs;
    private int diffucult;
    private int all_bombs = 0;
    private boolean isWin = true;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.miner);

        diffucult = Miner_manager.HARD;


        init();
        showBeginningDialog();
        initContAndBackButtons();
        makeTask();
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
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_20));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.icon));
        TextView level_name = findViewById(R.id.level);
        String level = "20";
        level_name.setText(String.format("%s %s",level,getResources().getString(R.string.level)));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                startActivity(new Intent(Miner.this, LevelsActivity.class));
                finish();
            } else if (v.getId() == R.id.startDialogButton) {
                dialog.dismiss();
                new Thread(t,"time");
            }
        };

        Button startButton = dialog.findViewById(R.id.startDialogButton);
        startButton.setOnClickListener(OnClickListener);
        Button backButton = dialog.findViewById(R.id.backDialogButton);
        backButton.setOnClickListener(OnClickListener);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(OnClickListener);
    }



    private void init(){
        t = new Time();
        diffucult = Miner_manager.HARD;
    }

    private void makeTask(){
        generateLayouts();
    }


    private void generateLayouts(){
        indexes_bombs = Miner_manager.getRandomCoordOfBombs(diffucult);
        taskLY = findViewById(R.id.taskLY);
        View.OnClickListener ocl = getOclForCellsSudoku();
        View.OnLongClickListener long_ocl = getLongClickOclForCells();


        for(int i = 0; i< Miner_manager.HEIGHT; i++){
            LinearLayout baseLL = (LinearLayout) this.getLayoutInflater().inflate(R.layout.base_linearlayout,taskLY,false);
            for(int j = 0 ; j<Miner_manager.WIDTH;j++){
//                boolean isBomb = false;
//                for(int[] bombs:indexes_bombs){
//                    if((bombs[0]==j)&&(bombs[1]==i)){
//                        isBomb = true;
//                    }
//                }

                TextView textView = (TextView) this.getLayoutInflater().inflate(R.layout.base_cell,baseLL,false);

                textView.setText(" ");
                textView.setTag(R.string.tagX,j);
                textView.setTag(R.string.tagY,i);
                textView.setTextSize(getResources().getDimension(R.dimen.answerCellTextSizeUltraSmall));
                textView.setTag(R.string.tagCellNumber,0);
                textView.setOnClickListener(ocl);
                textView.setOnLongClickListener(long_ocl);
                textView.setLongClickable(true);

                if(Miner_manager.isXandYinARRAY(indexes_bombs,j,i)) textView.setTag(R.string.tagIsBomb,1);
                else textView.setTag(R.string.tagIsBomb,0);
                baseLL.addView(textView);
            }
            taskLY.addView(baseLL);
        }


        for(int[] bombs:indexes_bombs){
            int[][] coords_of_around_cells;
            int count_of_bombs;
            int x_bomb = bombs[0];
            int y_bomb = bombs[1];
            coords_of_around_cells = Miner_manager.getCoordOfCellsAroundCell(x_bomb,y_bomb,Miner_manager.WIDTH,Miner_manager.HEIGHT);
            for(int[] coords_of_cell:coords_of_around_cells){
                TextView cell = (TextView) ((LinearLayout)taskLY.getChildAt(coords_of_cell[1])).getChildAt(coords_of_cell[0]);
                if(cell.getTag(R.string.tagIsBomb).toString().equals("0")) {
                    if (cell.getTag(R.string.tagCellNumber).toString().equals("0")){
                        cell.setTag(R.string.tagCellNumber,1);
                    }
                    else {
                        count_of_bombs = Integer.parseInt(cell.getTag(R.string.tagCellNumber).toString());
                        count_of_bombs++;
                        cell.setTag(R.string.tagCellNumber,String.valueOf(count_of_bombs));
                    }
                }
            }
        }

    }

    private View.OnClickListener getOclForCellsSudoku(){
        return v -> {
            set_textview = (TextView) v;
            int x = (int) set_textview.getTag(R.string.tagX);
            int y = (int) set_textview.getTag(R.string.tagY);
            Log.d("X Y",x+" "+y);
            Log.d("TAG CELL NUMBER",set_textview.getTag(R.string.tagCellNumber).toString());
            if((int) set_textview.getTag(R.string.tagIsBomb) == 1){
                set_textview.setText("!");
                set_textview.setBackground(getDrawable(R.drawable.cell_style_error));
                isWin = false;
                startResultsDialog();
            }else{
                if(set_textview.getTag(R.string.tagCellNumber).toString().equals("0")){
                    String[] coords_around = getAllIndexexAroundCellIfCellIsZero(x, y, new ArrayList<>()).toArray(new String[0]);
                    for(String coord:coords_around){
                        String[] coords = coord.split(" ");
                        TextView textView = ((TextView)((LinearLayout) taskLY.getChildAt(Integer.parseInt(coords[1]))).getChildAt(Integer.parseInt(coords[0])));
                        textView.setText(textView.getTag(R.string.tagCellNumber).toString());
                    }
                    Log.d("ARRAY SIZE FINALLY",coords_around.length+ " ");
                    set_textview.setText("0");
                }else set_textview.setText(set_textview.getTag(R.string.tagCellNumber).toString());

            }
        };
    }



    private ArrayList<String> getAllIndexexAroundCellIfCellIsZero(int x, int y,ArrayList<String> arrayList){
        if(arrayList.isEmpty()){
            int[][] coords_around = Miner_manager.getCoordOfCellsAroundCell(x,y,Miner_manager.WIDTH,Miner_manager.HEIGHT);
            if(coords_around.length==0) return arrayList;
            for (int[] ints : coords_around) {
                if (((((LinearLayout) taskLY.getChildAt(ints[1])).getChildAt(ints[0])).getTag(R.string.tagCellNumber).toString().equals("0"))) {
                    arrayList.add(ints[0] + " " + ints[1]);
                    arrayList = getAllIndexexAroundCellIfCellIsZero(ints[0], ints[1], arrayList);
                }
            }
            return arrayList;
        }else{
            int[][] coords = Miner_manager.getCoordOfCellsAroundCell(x,y,Miner_manager.WIDTH,Miner_manager.HEIGHT);
            StringBuilder s= new StringBuilder();
            for (int[] ints : coords) {
                s.append(ints[0]).append(" ").append(ints[1]).append(" | ");
            }
            Log.d("X Y",x+" "+y);
            Log.d("S",s.toString());
            for(int[] coord:coords){
                if(!arrayList.contains(coord[0]+" "+coord[1])){
                        arrayList.add(coord[0]+" "+coord[1]);
                    if(((((LinearLayout) taskLY.getChildAt(coord[1])).getChildAt(coord[0])).getTag(R.string.tagCellNumber).toString().equals("0"))) {
                        arrayList = getAllIndexexAroundCellIfCellIsZero(coord[0], coord[1], arrayList);
                    }
                }
            }
            return arrayList;
        }
    }


//    private int[][] getAllIndexexAroundCellIfCellIsZero(int x,int y){
//        ArrayList<Integer[]> arrayList = new ArrayList<>();
//        boolean needStop = true;
//        while(needStop){
//
//        }
//        return (int[][]) arrayList.toArray();
//    }
//





    private View.OnLongClickListener getLongClickOclForCells(){
        return v -> {
            TextView textView = (TextView) v;
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

            if(textView.getText().toString().equals("!")){
                v.setBackground(getDrawable(R.drawable.cell_style));
                textView.setText(" ");
                vibrator.vibrate(50);
                return true;
            }
            else if(all_bombs<diffucult) {
                textView.setText("!");
                if(v.getTag(R.string.tagIsBomb).toString().equals("0")) {
                    mistakes++;
                    v.setBackground(getDrawable(R.drawable.cell_style_error));
                }else{
                    all_bombs++;
                    v.setClickable(false);
                    v.setBackground(getDrawable(R.drawable.cell_style_checked));
                }
                if(all_bombs == indexes_bombs.length) startResultsDialog();
                if(mistakes==2){
                    isWin = false;
                    startResultsDialog();
                }
                vibrator.vibrate(100);
            }
            return true;
        };
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
                    startActivity(new Intent(Miner.this, Miner.class)); //REPEAT
                }else{
                    startActivity(new Intent(Miner.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(Miner.this,RSL.class);//NEXT LEVEL
                }else{
                    intent = new Intent(Miner.this, Miner.class); //REPEAT
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




    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.cancel();
    }





}
