package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Arithmetic_cells;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.Logging.Logging;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_29 extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private Time t;
    private long pressedTime;
    private LinearLayout taskLY;
    private boolean isWin = true;
    private TextView current_textview;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_26);

        showBeginningDialog();
        initContAndBackButtons();

        init();
        generateLayouts();
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
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_29));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.icon));
        TextView level_name = findViewById(R.id.level);
        String level = "29";
        level_name.setText(String.format("%s %s",level,getResources().getString(R.string.level)));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                startActivity(new Intent(Level_29.this, LevelsActivity.class));
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
        generateField();
    }


    private void init(){
        initVariables();
    }


    private void initVariables(){
        taskLY = findViewById(R.id.taskLY);
    }



    private void generateLayouts(){
        View.OnClickListener ocl = getOclForCells();
        for(int i = 0; i < Arithmetic_cells.HEIGHT; i++) {
            LinearLayout baseLL = (LinearLayout) this.getLayoutInflater().inflate(R.layout.base_linearlayout,taskLY,false);
            for (int j = 0;j < Arithmetic_cells.WIDTH;j++){
                TextView base_cell = (TextView) this.getLayoutInflater().inflate(R.layout.base_cell,baseLL,false);
                base_cell.setOnClickListener(ocl);
                if(j%2 == 1) base_cell.setClickable(false);
                if(i%2 == 1) base_cell.setClickable(false);
                if((i%2 == 1)&&(j%2 == 1)) {
                    base_cell.setBackground(getDrawable(R.drawable.cell_style_black));
                    base_cell.setClickable(false);
                }
                base_cell.setTag(R.string.tagX,j);
                base_cell.setTag(R.string.tagY,i);
                base_cell.setTag(R.string.tagIsCell,1);
                baseLL.addView(base_cell);
            }
            taskLY.addView(baseLL);
        }
        generateFieldWithNumbers();
    }


    private View.OnClickListener getOclForCells(){
        return view ->{
                if(current_textview != null) current_textview.setBackground(getDrawable(R.drawable.cell_style));
                current_textview = (TextView) view;
                current_textview.setBackground(getDrawable(R.drawable.cell_style_checked));

        };
    }

    private View.OnClickListener getOclForCellsNumbers(){
        return view ->{
            if(current_textview!=null){
                current_textview.setText(view.getTag().toString());
                checkIfUserWin();
            }
        };
    }


    private void generateFieldWithNumbers(){
        View.OnClickListener ocl = getOclForCellsNumbers();
        LinearLayout baseLY = (LinearLayout) this.getLayoutInflater().inflate(R.layout.base_linearlayout,taskLY,false);
        for(int i = Arithmetic_cells.BEGIN_NUMBER;i<=Arithmetic_cells.END_NUMBER;i++){
            TextView base_cell = (TextView) this.getLayoutInflater().inflate(R.layout.base_cell_with_number,baseLY,false);
            base_cell.setText(String.valueOf(i));
            base_cell.setOnClickListener(ocl);
            base_cell.setTag(i);
            baseLY.addView(base_cell);
        }
        taskLY.addView(baseLY);
    }

    private void generateField(){
        for(int i = 0; i < Arithmetic_cells.HEIGHT; i++) {
            LinearLayout baseLL = (LinearLayout) taskLY.getChildAt(i);
            for (int j = 0;j < Arithmetic_cells.WIDTH;j++){
                TextView base_cell = (TextView) baseLL.getChildAt(j);
                if((j%2 == 0)&&(i%2 == 1)) base_cell.setText(Arithmetic_cells.getSymbol(i,j));
                if((j%2 == 1)&&(i%2 == 0)) base_cell.setText(Arithmetic_cells.getSymbol(i,j));
                if((j == 0)&&(i == Arithmetic_cells.WIDTH - 1)) {
                    base_cell.setClickable(false);
                    base_cell.setText(Arithmetic_cells.getSymbol(i,j));
                }
                if((j == Arithmetic_cells.HEIGHT - 5)&&(i == Arithmetic_cells.WIDTH - 3)) {
                    base_cell.setClickable(false);
                    base_cell.setText(Arithmetic_cells.getSymbol(i,j));
                }
                if((j == Arithmetic_cells.HEIGHT - 3)&&(i == Arithmetic_cells.WIDTH - 5)) {
                    base_cell.setClickable(false);
                    base_cell.setText(Arithmetic_cells.getSymbol(i,j));
                }
                if((j == Arithmetic_cells.HEIGHT - 1)&&(i == 0)) {
                    base_cell.setClickable(false);
                    base_cell.setText(Arithmetic_cells.getSymbol(i,j));
                }
                base_cell.setTextSize(getResources().getDimension(R.dimen.textSize_8));
            }
        }
    }



    private void checkIfUserWin(){
        if((checkRowForWin())&&(checkColumnForWin())){
            startResultsDialog();
        }
    }


    private boolean checkRowForWin(){
        for(int i = 0;i < Arithmetic_cells.HEIGHT;i++){
            LinearLayout baseLY = (LinearLayout) taskLY.getChildAt(i);
            int result = 0;
            for(int j = 0;j < Arithmetic_cells.WIDTH;j++){
                TextView base_cell = (TextView) baseLY.getChildAt(j);
                if(i%2 == 0){
                   if(base_cell.getText().toString().equals(" ")) return false;
                   if(j == 0) result = Integer.parseInt(base_cell.getText().toString());
                   else{
                       if((j == Arithmetic_cells.WIDTH-2)){
                           base_cell = (TextView) baseLY.getChildAt(Arithmetic_cells.WIDTH-1);
                           if(base_cell.getText().toString().equals(" ")) return false;
                           if(result != Integer.parseInt(base_cell.getText().toString())){
                               return false;
                           }
                           break;
                       }else{
                           if(j%2 == 0){
                               result = Arithmetic_cells.makeOperation(result,((TextView)baseLY.getChildAt(j-1)).getText().toString(),Integer.parseInt(((TextView)baseLY.getChildAt(j)).getText().toString()));

                           }
                       }

                   }

                }
            }
        }
        return true;
    }

    private boolean checkColumnForWin(){
        for(int i = 0;i < Arithmetic_cells.WIDTH;i++){
            int result = 0;
            for(int j = 0;j < Arithmetic_cells.HEIGHT;j++){
                LinearLayout baseLY = (LinearLayout) taskLY.getChildAt(j);
                TextView base_cell = (TextView) baseLY.getChildAt(i);
                if(i%2 == 0){
                    if(base_cell.getText().toString().equals(" ")) return false;
                    if(j == 0) result = Integer.parseInt(base_cell.getText().toString());
                    else{
                        if((j == Arithmetic_cells.HEIGHT-2)){
                            baseLY = (LinearLayout) taskLY.getChildAt(Arithmetic_cells.HEIGHT-1);
                            base_cell = (TextView) baseLY.getChildAt(i);
                            if(base_cell.getText().toString().equals(" ")) return false;
                            if(result != Integer.parseInt(base_cell.getText().toString())){
                                return false;
                            }
                            Logging.log("SUCCESS","I "+i);
                            break;
                        }else{
                            if(j%2 == 0){
                                baseLY = (LinearLayout) taskLY.getChildAt(j-1);
                                result = Arithmetic_cells.makeOperation(result,((TextView)baseLY.getChildAt(i)).getText().toString(),Integer.parseInt((base_cell).getText().toString()));
                                Logging.log("INFO","I "+ i + " result " +result);
                            }
                        }

                    }

                }
            }
        }
        return true;
    }



    public void startResultsDialog() {
        t.stopTime();
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_results);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.repeatResultsDialog) {
                if(isWin) {
                    startActivity(new Intent(Level_29.this, Level_29.class)); //REPEAT
                }else{
                    startActivity(new Intent(Level_29.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(Level_29.this,Level_27.class);
                }else{
                    intent = new Intent(Level_29.this, Level_29.class); //REPEAT
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
