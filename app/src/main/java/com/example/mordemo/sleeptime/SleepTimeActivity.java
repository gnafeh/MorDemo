package com.example.mordemo.sleeptime;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mordemo.R;


/**
 * @author Hefang
 * @date 2020/1/6
 * Email: gnafehefang@gmail.com
 */
public class SleepTimeActivity extends Activity {
    private TextView textView_current_sleep_time;
    private Button button_set_sleep_time;
    private RadioGroup radioGroup;
    private RadioButton radioButton15s,radioButton30s,radioButton1m,radioButton2m,radioButton5m,radioButton10m,radioButton30m;
    private int[] timemiles = {15000, 30000, 60000, 120000, 300000, 600000, 1800000};
    private int current_index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sleeptime);
        initView();
    }

    private void initView() {
        //init TextView
        textView_current_sleep_time = findViewById(R.id.textview_sleeptime);

        //init RadioGroup
        radioGroup = findViewById(R.id.radiogroup_sleep_time);
        radioButton15s = findViewById(R.id.radiobuttom_15s);
        radioButton30s = findViewById(R.id.radiobuttom_30s);
        radioButton1m = findViewById(R.id.radiobuttom_1m);
        radioButton2m = findViewById(R.id.radiobuttom_2m);
        radioButton5m = findViewById(R.id.radiobuttom_5m);
        radioButton10m = findViewById(R.id.radiobuttom_10m);
        radioButton30m = findViewById(R.id.radiobuttom_30m);
        getDefaultTimeoutIndex();
        if(current_index == 0){
            radioGroup.check(radioButton15s.getId());
        }else if(current_index == 1) {
            radioGroup.check(radioButton30s.getId());
        }else if(current_index == 2) {
            radioGroup.check(radioButton1m.getId());
        }else if(current_index == 3) {
            radioGroup.check(radioButton2m.getId());
        }else if(current_index == 4) {
            radioGroup.check(radioButton5m.getId());
        }else if(current_index == 5) {
            radioGroup.check(radioButton10m.getId());
        }else if(current_index == 6) {
            radioGroup.check(radioButton30m.getId());
        }
        refreshTimeDisplay();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobuttom_15s:
                        current_index = 0;
                        break;
                    case R.id.radiobuttom_30s:
                        current_index = 1;
                        break;
                    case R.id.radiobuttom_1m:
                        current_index = 2;
                        break;
                    case R.id.radiobuttom_2m:
                        current_index = 3;
                        break;
                    case R.id.radiobuttom_5m:
                        current_index = 4;
                        break;
                    case R.id.radiobuttom_10m:
                        current_index = 5;
                        break;
                    case R.id.radiobuttom_30m:
                        current_index = 6;
                        break;
                }
            }
        });
        //init Button
        button_set_sleep_time = findViewById(R.id.button_set_sleep_time);
        button_set_sleep_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //refreshTimeDisplay();
                if(current_index != -1) {
                    Log.d("hf_test", "current = " + current_index);
                    setDormant(timemiles[current_index]);
                    refreshTimeDisplay();
                }
            }
        });

    }

    private int getDefaultTimeoutIndex() {
        int time = getDormant();
        for (int i = 0; i < timemiles.length; i++) {
            if(timemiles[i] == time){
            current_index = i;
                Log.d("hf_test", "current =[ " + i + "],timemiles = " + timemiles[i] + ", time = " + time);
            return i;
            }
        }
        return -1;
    }

    private void refreshTimeDisplay() {
        int time = getDormant();
        time = time / 1000;
        textView_current_sleep_time.setText(getResources().getText(R.string.textview_sleeptime_text) + String.valueOf(time) + " 秒");
    }

    /**
     * 获取系统休眠时间
     */
    public int getDormant() {
        int result = 0;
        try {
            result = Settings.System.getInt(getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        Log.d("hf_test", "getDormant: result =  " + result);
        return result;
    }


    /**
     * 设置系统的休眠时间
     */
    public void setDormant(int time) {
        Log.d("hf_test", "setDormant: result =  " + time);
        Settings.System.putInt(getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT, time);
        Uri uri = Settings.System
                .getUriFor(Settings.System.SCREEN_OFF_TIMEOUT);
        getContentResolver().notifyChange(uri, null);
    }
}
