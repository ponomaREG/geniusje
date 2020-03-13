package com.msulov.geniusje.Levels;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Game_15;
import com.msulov.geniusje.Levels.Managers.IsCatch_game;
import com.msulov.geniusje.Levels.Managers.Miner_manager;
import com.msulov.geniusje.Levels.Managers.Nonogramm;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import org.w3c.dom.Text;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_27 extends AppCompatActivity {



    private Toast toast;
    private Dialog dialog;
    private Time t;
    private long pressedTime;
    private int count = 0,mistakes = 0,correctAnswer=0;
    private int taskdesc_id;
    private LinearLayout baseLY,taskLY;
    private boolean isWin = true;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_27);

        showBeginningDialog();
        initContAndBackButtons();
        generateLayouts();
//        initOclForAnswers();
    }

    /////BEGINNIG AND RESULTING CONSTANT BLOCKS
    private void showBeginningDialog() {

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
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_21));
        //Находим аватар задания и устанавливаем свой
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.level3_icon));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                    startActivity(new Intent(Level_27.this, LevelsActivity.class));
                    finish();
                } else if (v.getId() == R.id.startDialogButton) {
                    dialog.dismiss();
                    t = new Time();
                    new Thread(t, "Time").start();
                    makeTask();
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
            log("CHILD COUNT",taskLY.getChildCount());
            taskLY.addView(baseLL);
        }


    }


    private View.OnClickListener getOclForCells(){
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = (Integer) v.getTag(R.string.tagX);
                int y = (Integer) v.getTag(R.string.tagY);

                swapInRow(x,y);
                swapInColumn(x,y);
                swapInDiagonal(x,y);
                if(checkIfUserWin()) startResultsDialog();


            }
        };
        return ocl;
    }





    private void swapInDiagonal(int x,int y){

        ImageView current_view = null;
        int y_2 = IsCatch_game.HEIGHT;
        for(int x_2 = x;x_2<IsCatch_game.WIDTH;x_2++){
            y_2--;
            LinearLayout baseLL = (LinearLayout) taskLY.getChildAt(y);
            ImageView cell = (ImageView) baseLL.getChildAt(x);
            if((Integer) cell.getTag(R.string.tagIsMain)==1){
                current_view = cell;
            }
        }
        if(current_view!=null) deleteMainInCell((Integer) current_view.getTag(R.string.tagX),(Integer) current_view.getTag(R.string.tagY));


        current_view = null;
        y_2 = IsCatch_game.HEIGHT;
        for(int x_2 = x;x_2>=0;x_2--){
            y_2--;
            LinearLayout baseLL = (LinearLayout) taskLY.getChildAt(y);
            ImageView cell = (ImageView) baseLL.getChildAt(x);
            if((Integer) cell.getTag(R.string.tagIsMain)==1){
                current_view = cell;
            }
        }
        if(current_view!=null) deleteMainInCell((Integer) current_view.getTag(R.string.tagX),(Integer) current_view.getTag(R.string.tagY));


        current_view = null;
        y_2 = -1;
        for(int x_2 = x;x_2<IsCatch_game.WIDTH;x_2++){
            y_2++;
            LinearLayout baseLL = (LinearLayout) taskLY.getChildAt(y);
            ImageView cell = (ImageView) baseLL.getChildAt(x);
            if((Integer) cell.getTag(R.string.tagIsMain)==1){
                current_view = cell;
            }
        }
        if(current_view!=null) deleteMainInCell((Integer) current_view.getTag(R.string.tagX),(Integer) current_view.getTag(R.string.tagY));


        current_view = null;
        y_2 = -1;
        for(int x_2 = x;x_2>=0;x_2--){
            y_2++;
            LinearLayout baseLL = (LinearLayout) taskLY.getChildAt(y);
            ImageView cell = (ImageView) baseLL.getChildAt(x);
            if((Integer) cell.getTag(R.string.tagIsMain)==1){
                current_view = cell;
            }
        }
        if(current_view!=null) deleteMainInCell((Integer) current_view.getTag(R.string.tagX),(Integer) current_view.getTag(R.string.tagY));


        setMainInCell(x,y);
    }

    private void swapInColumn(int x , int y){
        ImageView current_view = null;
        int y_current = -1;
        for(int i = 0;i<IsCatch_game.HEIGHT;i++){
            LinearLayout baseLL = (LinearLayout) taskLY.getChildAt(i);
            ImageView cell = (ImageView) baseLL.getChildAt(x);
            if((Integer) cell.getTag(R.string.tagIsMain)==1){
                current_view = cell;
            }
        }
        if(current_view!=null) deleteMainInCell((Integer) current_view.getTag(R.string.tagX),(Integer) current_view.getTag(R.string.tagY));
        setMainInCell(x,y);
    }

    private void swapInRow(int x, int y ){
        ImageView current_view=null;
        int x_current=-1;
        LinearLayout baseLL = (LinearLayout) taskLY.getChildAt(y);
        for(int i = 0;i<IsCatch_game.WIDTH;i++){
            ImageView cell = (ImageView) baseLL.getChildAt(i);
            if((Integer) cell.getTag(R.string.tagIsMain)==1){
                current_view = cell;
                x_current = i;
            }
        }
        if(current_view!=null){
            current_view.setTag(R.string.tagIsMain,0);
            current_view.setBackground(getDrawable(R.drawable.cell_style));
            current_view.setClickable(true);
        }
        setMainInCell(x,y);
        deleteMainInCell(x_current,y);
    }



    private boolean checkIfUserWin(){
        for(int y = 0;y<IsCatch_game.HEIGHT;y++){
            baseLY = (LinearLayout) taskLY.getChildAt(y);
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

        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        intent = new Intent(Level_27.this,Level_23.class);
                    }else{
                        intent = new Intent(Level_27.this, Level_27.class); //REPEAT
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


    private void log(String tag,String text){
        Log.d(tag,text);
    }
    private void log(String tag,int text){
        Log.d(tag,text+"");
    }
    private void log(String tag,long text){
        Log.d(tag,text +"");
    }

}
