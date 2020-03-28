package com.msulov.geniusje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.msulov.geniusje.Levels.Crossword;
import com.msulov.geniusje.Levels.Level_1;
import com.msulov.geniusje.Levels.Level_10;
import com.msulov.geniusje.Levels.Level_11;
import com.msulov.geniusje.Levels.Level_16;
import com.msulov.geniusje.Levels.Level_17;
import com.msulov.geniusje.Levels.Level_2;
import com.msulov.geniusje.Levels.Level_22;
import com.msulov.geniusje.Levels.Level_23;
import com.msulov.geniusje.Levels.Level_24;
import com.msulov.geniusje.Levels.Level_25;
import com.msulov.geniusje.Levels.Level_26;
import com.msulov.geniusje.Levels.Level_27;
import com.msulov.geniusje.Levels.Level_29;
import com.msulov.geniusje.Levels.Level_3;
import com.msulov.geniusje.Levels.Level_30;
import com.msulov.geniusje.Levels.Level_4;
import com.msulov.geniusje.Levels.Level_5;
import com.msulov.geniusje.Levels.Level_6;
import com.msulov.geniusje.Levels.Level_7;
import com.msulov.geniusje.Levels.Level_8;
import com.msulov.geniusje.Levels.Level_9;
import com.msulov.geniusje.Levels.Miner;
import com.msulov.geniusje.Levels.RSL;
import com.msulov.geniusje.Levels.four_choice;
import com.msulov.geniusje.Levels.memory_base;

public class LevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        initCells();


    }



    private void initCells(){
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(LevelsActivity.this, MainActivity.class));
            finish();
        });
        View.OnClickListener ocl = getOclForCells();
        SQLiteDatabase db = new DBHelper(this).getReadableDatabase();
        Cursor c = db.rawQuery("select * from tasks;",null);
        c.moveToFirst();
        for(int i = 1;i<31;i++){
            TextView cell = findViewById(getResources().getIdentifier("level_"+i,"id",getPackageName()));
            int is_open = c.getInt(c.getColumnIndex("is_open"));
//            cell.setOnClickListener(ocl);
            if(is_open == 1) {
                cell.setOnClickListener(ocl);
                cell.setText(String.valueOf(i));
            }
            else cell.setBackground(getDrawable(R.drawable.level_block));
            c.moveToNext();
        }
        c.close();
    }


    private View.OnClickListener getOclForCells(){
        return v -> {
            switch (v.getId()) {
                case R.id.level_1:
                    startActivity(new Intent(this, Level_1.class));
                    break;
                case R.id.level_2:
                    startActivity(new Intent(this, Level_2.class));
                    break;
                case R.id.level_3:
                    startActivity(new Intent(this, Level_3.class));
                    break;
                case R.id.level_4:
                    startActivity(new Intent(this, Level_4.class));
                    break;
                case R.id.level_5:
                    startActivity(new Intent(this, Level_5.class));
                    break;
                case R.id.level_6:
                    startActivity(new Intent(this, Level_6.class));
                    break;
                case R.id.level_7:
                    startActivity(new Intent(this, Level_7.class));
                    break;
                case R.id.level_8:
                    startActivity(new Intent(this, Level_8.class));
                    break;
                case R.id.level_9:
                    startActivity(new Intent(this, Level_9.class));
                    break;
                case R.id.level_10:
                    startActivity(new Intent(this, Level_10.class));
                    break;
                case R.id.level_11:
                    startActivity(new Intent(this, Level_11.class));
                    break;
                case R.id.level_12:
                    startActivity(new Intent(this, four_choice.class).putExtra("type", "Shaked_words"));
                    break;
                case R.id.level_13:
                    startActivity(new Intent(this, four_choice.class).putExtra("type", "Question_1"));
                    break;
                case R.id.level_14:
                    startActivity(new Intent(this, four_choice.class).putExtra("type", "Question_2"));
                    break;
                case R.id.level_15:
                    startActivity(new Intent(this, four_choice.class).putExtra("type", "Question_3"));
                    break;
                case R.id.level_16:
                    startActivity(new Intent(this, Level_16.class).putExtra("type", "Find_word"));
                    break;
                case R.id.level_17:
                    startActivity(new Intent(this, Level_17.class));
                    break;
                case R.id.level_18:
                    startActivity(new Intent(this, memory_base.class).putExtra("type", "Get_rhythm"));
                    break;
                case R.id.level_19:
                    startActivity(new Intent(this, memory_base.class).putExtra("type", "Find_all"));
                    break;
                case R.id.level_20:
                    startActivity(new Intent(this, Miner.class).putExtra("type", "EAZY"));
                    break;
                case R.id.level_21:
                    startActivity(new Intent(this, RSL.class));
                    break;
                case R.id.level_22:
                    startActivity(new Intent(this, Level_22.class));
                    break;
                case R.id.level_23:
                    startActivity(new Intent(this, Level_23.class));
                    break;
                case R.id.level_24:
                    startActivity(new Intent(this, Level_24.class));
                    break;
                case R.id.level_25:
                    startActivity(new Intent(this, Level_25.class));
                    break;
                case R.id.level_26:
                    startActivity(new Intent(this, Level_26.class));
                    break;
                case R.id.level_27:
                    startActivity(new Intent(this, Level_27.class));
                    break;
                case R.id.level_28:
                    startActivity(new Intent(this, Crossword.class));
                    break;
                case R.id.level_29:
                    startActivity(new Intent(this, Level_29.class));
                    break;
                case R.id.level_30:
                    startActivity(new Intent(this, Level_30.class));
                    break;
            }
        };
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(LevelsActivity.this, MainActivity.class));
        finish();
    }
}
