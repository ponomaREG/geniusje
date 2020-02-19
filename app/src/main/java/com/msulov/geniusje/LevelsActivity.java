package com.msulov.geniusje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.msulov.geniusje.Levels.Level_1;
import com.msulov.geniusje.Levels.Level_10;
import com.msulov.geniusje.Levels.Level_4;
import com.msulov.geniusje.Levels.Level_5;
import com.msulov.geniusje.Levels.Level_6;
import com.msulov.geniusje.Levels.Level_7;

public class LevelsActivity extends AppCompatActivity {

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        // Обработчик нажатия на "Назад" - (Начало)
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LevelsActivity.this, MainActivity.class));
                finish();
            }
        });
        // Обработчик нажатия на "Назад" - (Конец)

        // Обработчики нажатия на блоки "1-30" - (Начало)
        TextView level_1 = findViewById(R.id.level_1);
        TextView level_2 = findViewById(R.id.level_2);
        TextView level_3 = findViewById(R.id.level_3);
        TextView level_4 = findViewById(R.id.level_4);
        TextView level_5 = findViewById(R.id.level_5);
        TextView level_6 = findViewById(R.id.level_6);
        TextView level_7 = findViewById(R.id.level_7);
        TextView level_8 = findViewById(R.id.level_8);
        TextView level_9 = findViewById(R.id.level_9);
        TextView level_10 = findViewById(R.id.level_10);
        TextView level_11 = findViewById(R.id.level_11);
        TextView level_12 = findViewById(R.id.level_12);
        TextView level_13 = findViewById(R.id.level_13);
        TextView level_14 = findViewById(R.id.level_14);
        TextView level_15 = findViewById(R.id.level_15);
        TextView level_16 = findViewById(R.id.level_16);
        TextView level_17 = findViewById(R.id.level_17);
        TextView level_18 = findViewById(R.id.level_18);
        TextView level_19 = findViewById(R.id.level_19);
        TextView level_20 = findViewById(R.id.level_20);
        TextView level_21 = findViewById(R.id.level_21);
        TextView level_22 = findViewById(R.id.level_22);
        TextView level_23 = findViewById(R.id.level_23);
        TextView level_24 = findViewById(R.id.level_24);
        TextView level_25 = findViewById(R.id.level_25);
        TextView level_26 = findViewById(R.id.level_26);
        TextView level_27 = findViewById(R.id.level_27);
        TextView level_28 = findViewById(R.id.level_28);
        TextView level_29 = findViewById(R.id.level_29);
        TextView level_30 = findViewById(R.id.level_30);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.level_1:
                        startActivity(new Intent(LevelsActivity.this, Level_1.class));
                        break;
                    case R.id.level_4:
                        startActivity(new Intent(LevelsActivity.this, Level_4.class));
                        break;
                    case R.id.level_5:
                        startActivity(new Intent(LevelsActivity.this, Level_5.class));
                        break;
                    case R.id.level_6:
                        startActivity(new Intent(LevelsActivity.this, Level_6.class));
                        break;
                    case R.id.level_7:
                        startActivity(new Intent(LevelsActivity.this, Level_7.class));
                        break;
                    case R.id.level_10:
                        startActivity(new Intent(LevelsActivity.this, Level_10.class));
                        break;
                }
            }
        };

        // Передаем обработчики кнопкам
        level_1.setOnClickListener(onClickListener);
        level_2.setOnClickListener(onClickListener);
        level_3.setOnClickListener(onClickListener);
        level_4.setOnClickListener(onClickListener);
        level_5.setOnClickListener(onClickListener);
        level_6.setOnClickListener(onClickListener);
        level_7.setOnClickListener(onClickListener);
        level_8.setOnClickListener(onClickListener);
        level_9.setOnClickListener(onClickListener);
        level_10.setOnClickListener(onClickListener);
        level_11.setOnClickListener(onClickListener);
        level_12.setOnClickListener(onClickListener);
        level_13.setOnClickListener(onClickListener);
        level_14.setOnClickListener(onClickListener);
        level_15.setOnClickListener(onClickListener);
        level_16.setOnClickListener(onClickListener);
        level_17.setOnClickListener(onClickListener);
        level_18.setOnClickListener(onClickListener);
        level_19.setOnClickListener(onClickListener);
        level_20.setOnClickListener(onClickListener);
        level_21.setOnClickListener(onClickListener);
        level_22.setOnClickListener(onClickListener);
        level_23.setOnClickListener(onClickListener);
        level_24.setOnClickListener(onClickListener);
        level_25.setOnClickListener(onClickListener);
        level_26.setOnClickListener(onClickListener);
        level_27.setOnClickListener(onClickListener);
        level_28.setOnClickListener(onClickListener);
        level_29.setOnClickListener(onClickListener);
        level_30.setOnClickListener(onClickListener);
        // Обработчики нажатия на блоки "1-30" - (Конец)
    }

    // Обработчик нажатия системной кнопки "Назад" - (Начало)
    @Override
    public void onBackPressed() {

        startActivity(new Intent(LevelsActivity.this, MainActivity.class));
        finish();
    }
    // Обработчик нажатия системной кнопки "Назад" - (Конец)
}
