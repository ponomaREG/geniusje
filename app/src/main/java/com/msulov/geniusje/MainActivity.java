package com.msulov.geniusje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toast toast;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkDatabaseAndIfDoesntExistThenMake();
        findViewById(R.id.startButton).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LevelsActivity.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() {

        if ((pressedTime + 2000) > System.currentTimeMillis()) {
            toast.cancel();
            super.onBackPressed();
            return;
        } else {
            toast = Toast.makeText(getBaseContext(), R.string.toastExit, Toast.LENGTH_SHORT);
            toast.show();
        }
        pressedTime = System.currentTimeMillis();
    }

    private void checkDatabaseAndIfDoesntExistThenMake(){
        new DBHelper(this).copyDataBase();
    }



}
