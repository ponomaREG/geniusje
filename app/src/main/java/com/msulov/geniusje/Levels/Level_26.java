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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.IsCatch_game;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.Logging.Logging;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_26 extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private Time t;
    private long pressedTime;
    private LinearLayout taskLY;
    private boolean isWin = true;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_26);

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
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_26));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.icon));
        TextView level_name = findViewById(R.id.level);
        String level = "26";
        level_name.setText(String.format("%s %s",level,getResources().getString(R.string.level)));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                startActivity(new Intent(Level_26.this, LevelsActivity.class));
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






    private void makeTask(){ initAnother(); }


    private void initAnother(){
    }






    private void generateLayouts(){
        taskLY = findViewById(R.id.taskLY);
        View.OnClickListener ocl = getOclForCells();

        for(int i = 0; i < IsCatch_game.HEIGHT; i++) {
            LinearLayout baseLL = (LinearLayout) this.getLayoutInflater().inflate(R.layout.base_linearlayout,taskLY,false);
            for (int j = 0;j < IsCatch_game.WIDTH;j++){

                ImageView base_cell = (ImageView) this.getLayoutInflater().inflate(R.layout.base_cell_imageview,baseLL,false);
                base_cell.setTag(R.string.tagX,j);
                base_cell.setTag(R.string.tagY,i);
                base_cell.setTag(R.string.tagIsMain,0);
                base_cell.setOnClickListener(ocl);
                baseLL.addView(base_cell);
            }
            taskLY.addView(baseLL);
        }


    }


    private View.OnClickListener getOclForCells(){
        return v -> {
            int x = (Integer) v.getTag(R.string.tagX);
            int y = (Integer) v.getTag(R.string.tagY);

            swapInRow(x,y);
            swapInColumn(x,y);
            swapInDiagonal(x,y);
            if(checkIfUserWin()) startResultsDialog();


        };
    }





    private void swapInDiagonal(int x,int y){
        int y_2 = y;
        for(int x_2 = x+1;x_2<IsCatch_game.WIDTH;x_2++){
                y_2--;
                Logging.log("X+Y UR", x_2 + " " + y_2);
                if((y_2>=0)&&(y_2<IsCatch_game.HEIGHT)&&(x_2<IsCatch_game.WIDTH)&&(x_2>=0)) {
                    LinearLayout baseLL = (LinearLayout) taskLY.getChildAt(y_2);
                    ImageView cell = (ImageView) baseLL.getChildAt(x_2);
                    Logging.log("ismain UR", cell.getTag(R.string.tagIsMain).toString());
                    if ((Integer) cell.getTag(R.string.tagIsMain) == 1) {
                        deleteMainInCell((Integer) cell.getTag(R.string.tagX), (Integer) cell.getTag(R.string.tagY));
                    }
                }
        }

        y_2 = y;
        for(int x_2 = x-1;x_2>=0;x_2--){
            y_2--;
            Logging.log("X+Y UL",x_2+" "+y_2);
            if((y_2>=0)&&(y_2<IsCatch_game.HEIGHT)&&(x_2<IsCatch_game.WIDTH)) {
                LinearLayout baseLL = (LinearLayout) taskLY.getChildAt(y_2);
                ImageView cell = (ImageView) baseLL.getChildAt(x_2);
                Logging.log("ismain UL", cell.getTag(R.string.tagIsMain).toString());
                if ((Integer) cell.getTag(R.string.tagIsMain) == 1) {
                    deleteMainInCell((Integer) cell.getTag(R.string.tagX), (Integer) cell.getTag(R.string.tagY));
                }
            }
        }

        y_2 = y;
        for(int x_2 = x+1;x_2<IsCatch_game.WIDTH;x_2++){
            y_2++;
            Logging.log("X+Y DR",x_2+" "+y_2);
            if((y_2>=0)&&(y_2<IsCatch_game.HEIGHT)&&(x_2<IsCatch_game.WIDTH)&&(x_2>=0)) {
                LinearLayout baseLL = (LinearLayout) taskLY.getChildAt(y_2);
                ImageView cell = (ImageView) baseLL.getChildAt(x_2);
                Logging.log("ismain DR", cell.getTag(R.string.tagIsMain).toString());
                if ((Integer) cell.getTag(R.string.tagIsMain) == 1) {
                    deleteMainInCell((Integer) cell.getTag(R.string.tagX), (Integer) cell.getTag(R.string.tagY));
                }
            }
        }

        y_2 = y;
        for(int x_2 = x-1;x_2>=0;x_2--) {
            y_2++;
            if ((y_2 >= 0) && (y_2 < IsCatch_game.HEIGHT) && (x_2 < IsCatch_game.WIDTH)) {
                LinearLayout baseLL = (LinearLayout) taskLY.getChildAt(y_2);
                ImageView cell = (ImageView) baseLL.getChildAt(x_2);
                Logging.log("ismain DL", cell.getTag(R.string.tagIsMain).toString());
                if ((Integer) cell.getTag(R.string.tagIsMain) == 1) {
                    deleteMainInCell((Integer) cell.getTag(R.string.tagX), (Integer) cell.getTag(R.string.tagY));
                }
            }
        }

        setMainInCell(x,y);
    }

    private void swapInColumn(int x , int y){
        ImageView current_view;
        for(int i = 0;i<IsCatch_game.HEIGHT;i++){
            LinearLayout baseLL = (LinearLayout) taskLY.getChildAt(i);
            ImageView cell = (ImageView) baseLL.getChildAt(x);
            if(((Integer) cell.getTag(R.string.tagIsMain)==1)&&(y!=i)){
                current_view = cell;
                if(current_view!=null) deleteMainInCell((Integer) current_view.getTag(R.string.tagX),(Integer) current_view.getTag(R.string.tagY));
            }
        }

        setMainInCell(x,y);
    }

    private void swapInRow(int x, int y ){
        ImageView current_view=null;
        int x_current;
        LinearLayout baseLL = (LinearLayout) taskLY.getChildAt(y);
        for(int i = 0;i<IsCatch_game.WIDTH;i++){
            ImageView cell = (ImageView) baseLL.getChildAt(i);
            if(((Integer) cell.getTag(R.string.tagIsMain)==1)&&(i!=x)){
                current_view = cell;
                x_current = i;
                if(current_view!=null) deleteMainInCell(x_current,y);
            }
        }
        setMainInCell(x,y);
    }



    private boolean checkIfUserWin(){
        for(int y = 0;y<IsCatch_game.HEIGHT;y++){
            LinearLayout baseLY = (LinearLayout) taskLY.getChildAt(y);
            boolean isExistsMainCell = false;
            for(int x = 0;x<IsCatch_game.WIDTH;x++){
                ImageView cell = (ImageView) baseLY.getChildAt(x);
                if((Integer) cell.getTag(R.string.tagIsMain) == 1) isExistsMainCell = true;
            }
            if(!isExistsMainCell) return false;
        }
        return true;
    }

    private void deleteMainInCell(int x , int y){
        Logging.log("x y DELETE MAIN IN CELL",x+" "+y);
        ImageView cell = (ImageView)((LinearLayout) taskLY.getChildAt(y)).getChildAt(x);
        cell.setTag(R.string.tagIsMain,0);
        cell.setBackground(getDrawable(R.drawable.cell_style));
        cell.setClickable(true);
    }


    private void setMainInCell(int x , int y){
        ImageView cell = (ImageView)((LinearLayout) taskLY.getChildAt(y)).getChildAt(x);
        cell.setTag(R.string.tagIsMain,1);
        cell.setBackground(getDrawable(R.drawable.cell_style_checked));
        cell.setClickable(false);
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
                    startActivity(new Intent(Level_26.this, Level_26.class)); //REPEAT
                }else{
                    startActivity(new Intent(Level_26.this, LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(Level_26.this,Level_27.class);
                }else{
                    intent = new Intent(Level_26.this, Level_26.class); //REPEAT
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
