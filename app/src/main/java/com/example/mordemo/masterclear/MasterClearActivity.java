package com.example.mordemo.masterclear;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mordemo.R;

/**
 * @author Hefang
 * @date 2020/1/6
 * Email: gnafehefang@gmail.com
 */
public class MasterClearActivity extends AppCompatActivity {
    private Button buttonMasterClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_masterclear);
        initView();
    }

    private void initView(){
        buttonMasterClear = (Button) findViewById(R.id.button_masterclear);
        buttonMasterClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hf_test","Button [ " + buttonMasterClear.getText() +" ] clicked!");
                doMasterClear(v.getContext());
            }
        });
    }

    private static void doMasterClear(Context context) {
        final String ACTION_FACTORY_RESET = "android.intent.action.FACTORY_RESET";
        final String EXTRA_REASON = "android.intent.extra.REASON";
        final String EXTRA_WIPE_EXTERNAL_STORAGE = "android.intent.extra.WIPE_EXTERNAL_STORAGE";
        final String EXTRA_WIPE_ESIMS = "com.android.internal.intent.extra.WIPE_ESIMS";

        Intent intent = new Intent(ACTION_FACTORY_RESET);
        intent.setPackage("android");
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent.putExtra(EXTRA_REASON, "MasterClearConfirm");
        intent.putExtra(EXTRA_WIPE_EXTERNAL_STORAGE, false);
        intent.putExtra(EXTRA_WIPE_ESIMS, false);
        context.sendBroadcast(intent);
// Intent handling is asynchronous -- assume it will happen soon.
    }

}
