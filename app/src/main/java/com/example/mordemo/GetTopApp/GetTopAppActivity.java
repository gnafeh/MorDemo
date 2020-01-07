package com.example.mordemo.GetTopApp;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.mordemo.R;

import java.util.List;

/**
 * @author Hefang
 * @date 2020/1/7
 * Email: gnafehefang@gmail.com
 */
public class GetTopAppActivity extends Activity {
    private TextView textViewGetTopApp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gettopapp);
        initView();
    }
     private void initView(){
        textViewGetTopApp = findViewById(R.id.textview_get_top_app);
        textViewGetTopApp.setText(getResources().getText(R.string.textview_gettopapp_text) + getTopAppPackageName(this));
     }


    public static String getTopAppPackageName(Context context) {
        String packageName = "";
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        try {
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                List<ActivityManager.RunningTaskInfo> rti = activityManager.getRunningTasks(1);
                packageName = rti.get(0).topActivity.getPackageName();
            } else if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
                if (processes.size() == 0) {
                    return packageName;
                }
                for (ActivityManager.RunningAppProcessInfo process : processes) {
                    if (process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return process.processName;
                    }
                }
            } else if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
                final long end = System.currentTimeMillis();
                final UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService( Context.USAGE_STATS_SERVICE);
                if (null == usageStatsManager) {
                    return packageName;
                }
                final UsageEvents events = usageStatsManager.queryEvents((end - 60 * 1000), end);
                if (null == events) {
                    return packageName;
                }
                UsageEvents.Event usageEvent = new UsageEvents.Event();
                UsageEvents.Event lastMoveToFGEvent = null;
                while (events.hasNextEvent()) {
                    events.getNextEvent(usageEvent);
                    if (usageEvent.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                        lastMoveToFGEvent = usageEvent;
                    }
                }
                if (lastMoveToFGEvent != null) {
                    packageName = lastMoveToFGEvent.getPackageName();
                }
            }
        }catch (Exception ignored){
        }
        return packageName;
    }

}
