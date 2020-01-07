package com.example.mordemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mordemo.GetTopApp.GetTopAppActivity;
import com.example.mordemo.masterclear.MasterClearActivity;
import com.example.mordemo.silentinstall.SilentInstallActivity;
import com.example.mordemo.sleeptime.SleepTimeActivity;

public class MainActivity extends AppCompatActivity {
    private Button buttonMasterClear, buttonSleepTimeOut,buttonGetTopApp,buttonSilentInstall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        buttonMasterClear = findViewById(R.id.button_main_master_clear);
        buttonSleepTimeOut = findViewById(R.id.button_main_sleep_time_out);
        buttonGetTopApp = findViewById(R.id.button_main_get_top_app);
        buttonSilentInstall = findViewById(R.id.button_main_silent_install);

        buttonMasterClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this, MasterClearActivity.class);
                startActivity(intent);
            }
        });
        buttonSleepTimeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this, SleepTimeActivity.class);
                startActivity(intent);

            }
        });
        buttonGetTopApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this, GetTopAppActivity.class);
                startActivity(intent);
            }
        });
        buttonSilentInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this, SilentInstallActivity.class);
                startActivity(intent);
            }
        });
    }



}
