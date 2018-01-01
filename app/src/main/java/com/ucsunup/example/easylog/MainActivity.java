package com.ucsunup.example.easylog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ucsunup.easylog.annotations.Logit;

public class MainActivity extends AppCompatActivity {

    private TextView mClickView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mClickView = findViewById(R.id.click);
        testDebugLog(1, 2);
    }

    private void testDebugLog(int param1, int param2) {
        testDefaultEasyLog();
    }

    @Logit(type = Logit.Type.Release, append = "default")
    private void testDefaultEasyLog() {
        testEasyLog();
        mClickView.callOnClick();
    }

    @Logit(type = Logit.Type.Debug, append = "test append")
    private void testEasyLog() {
        Log.d("heihei", "test");
    }
}
