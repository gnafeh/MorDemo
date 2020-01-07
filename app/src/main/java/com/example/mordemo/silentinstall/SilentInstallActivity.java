package com.example.mordemo.silentinstall;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mordemo.R;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Hefang
 * @date 2020/1/7
 * Email: gnafehefang@gmail.com
 */
public class SilentInstallActivity extends Activity {
    private Button buttonSilentInstall;
    private File sdcardFile = Environment.getExternalStorageDirectory();
    private File apkFile = new File(sdcardFile, "target.apk");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_silent_install);
        initView();
    }

    private void initView(){
       buttonSilentInstall = findViewById(R.id.button_silent_install);
       buttonSilentInstall.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //silentInstall();
               if(silentInstall(apkFile.toString())){
                   Toast.makeText(SilentInstallActivity.this, "Install success!", Toast.LENGTH_SHORT).show();
                   Log.d("hf_test", "silent install Success!");
               }else{
                   Toast.makeText(SilentInstallActivity.this, "Install Failed! Please copy target.apk to sdcard!", Toast.LENGTH_SHORT).show();
                   Log.d("hf_test", "silent install Failed!");
               }
           }
       });
    }



        public  boolean silentInstall(String apkPath) {
        Log.d("hf_test","silentInstall ! ");
        try {
            PackageManager packageManager = getPackageManager();
            Class<?> pmClz = packageManager.getClass();

            if (Build.VERSION.SDK_INT >= 21) {
                Class<?> aClass = Class.forName("android.app.PackageInstallObserver");
                Constructor<?> constructor = aClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object installObserver = constructor.newInstance();
                Method method = pmClz.getDeclaredMethod("installPackage", Uri.class, aClass, int.class, String.class);
                method.setAccessible(true);
                method.invoke(packageManager, Uri.fromFile(new File(apkPath)), installObserver, 2, null);
            } else {
                Method method = pmClz.getDeclaredMethod("installPackage", Uri.class, Class.forName("android.content.pm.IPackageInstallObserver"), int.class, String.class);
                method.setAccessible(true);
                method.invoke(packageManager, Uri.fromFile(new File(apkPath)), null, 2, null);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
