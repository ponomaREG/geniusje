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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.msulov.geniusje.Levels.Managers.Find_word;
import com.msulov.geniusje.LevelsActivity;
import com.msulov.geniusje.R;
import com.msulov.geniusje.Time;

import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Level_16 extends AppCompatActivity {



    private Toast toast;
    private EditText input_word;
    private Button sendButton;
    private Dialog dialog;
    private TextView desc;
    private TextView founded_words;
    private Time t;
    private long pressedTime;
    private int count, mistakes = 0;
    private boolean isWin = true;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_16);

        showBeginningDialog();
        initContAndBackButtons();
        makeTask();
        initOclForAnswers();
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
        task.setText(getResources().getString(R.string.startDialogWindowForLevel_16));
        CircleImageView icon = dialog.findViewById(R.id.iconTask);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.icon));
        TextView level_name = findViewById(R.id.level);
        String level = "16";
        level_name.setText(String.format("%s %s",level,getResources().getString(R.string.level)));
    }


    private void initContAndBackButtons(){

        View.OnClickListener OnClickListener = v -> {
            Log.d("DSA","DSA");
            if (v.getId() == R.id.backDialogButton || v.getId() == R.id.backButton) {
                startActivity(new Intent(Level_16.this, LevelsActivity.class));
                finish();
            } else if (v.getId() == R.id.startDialogButton) {
                Log.d("ASD","ASD");
                dialog.dismiss();
                new Thread(t, "Time").start();
            }
        };

        Button startButton = dialog.findViewById(R.id.startDialogButton);
        startButton.setOnClickListener(OnClickListener);
        Button backButton = dialog.findViewById(R.id.backDialogButton);
        backButton.setOnClickListener(OnClickListener);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(OnClickListener);

        sendButton = findViewById(R.id.buttonSend);

        desc = findViewById(R.id.desc);
    }



    private void makeTask(){
        Random random = new Random();
        int rand_index = random.nextInt(Find_word.COUNT);
        String[] words = Find_word.getWords(rand_index);
        desc.setText(words[0]);
        desc.setTag(R.string.tagDescTag,rand_index);
    }



    @SuppressLint("SetTextI18n")
    private void initOclForAnswers(){
        count = 0;
        input_word = findViewById(R.id.input_word);
        founded_words = findViewById(R.id.founded_words);
        View.OnClickListener ocl = v -> {
            int index = (int) desc.getTag(R.string.tagDescTag);
            String user_word = input_word.getText().toString().toLowerCase();
            Log.d("WORD", "1");
            if (Find_word.isExistsAndNotFound(index, user_word)) {
                Log.d("ASD", "DASDASDA");
                founded_words.setText(founded_words.getText().toString() + user_word + " , ");
                Find_word.setKoefhasFounded(index, user_word);
                count++;
            }else mistakes++;
            if(mistakes == 3) {
                isWin = false;
                startResultsDialog();
            }
            if (count == 3) {
                startResultsDialog();
            }

        };
        sendButton.setOnClickListener(ocl);

    }



    //RESULT BLOCK
    public void startResultsDialog() {
        dialog.cancel();
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_results);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        View.OnClickListener OnClickListener = v -> {
            if (v.getId() == R.id.repeatResultsDialog) {
                if(isWin) {
                    startActivity(new Intent(getApplicationContext(), Level_16.class)); //REPEAT
                }else{
                    startActivity(new Intent(getApplicationContext(), LevelsActivity.class)); //MAIN SCREEN WITH LEVELS
                }
                finish();
            } else if (v.getId() == R.id.ContinueResultsDialog) {
                Intent intent;// = null;
                if(isWin) {
                    intent = new Intent(getApplicationContext(),Level_17.class);
                }else{
                    intent = new Intent(getApplicationContext(), Level_16.class); //REPEAT
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
            setResultsOnResultsDialog(textResultsDialog,false,false,false,isWin);

        }else{
            repeatButton.setText(getString(R.string.repeat));
            repeatButton.setTextSize(getResources().getDimension(R.dimen.textSize_8));
            setResultsOnResultsDialog(textResultsDialog,false,false,false,isWin);
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
            result = String.format(result,getString(R.string.resultDialogMistakes), mistakes);
        }

        if(!isWin){
            result = result + " %s";
            result = String.format(result,getString(R.string.resultDialogRepeat));
        }

        textResultsDialog.setText(result);
    }
    //RESULT BLOCK END

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
