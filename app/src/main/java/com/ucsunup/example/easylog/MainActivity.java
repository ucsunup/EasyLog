package com.ucsunup.example.easylog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ucsunup.easylog.Level;
import com.ucsunup.easylog.Logi;
import com.ucsunup.easylog.annotations.Loga;

public class MainActivity extends AppCompatActivity {

    private TextView mClickView;
    private static String mTxtTest = "dsdg";

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
        testDefaultEasyLog(param1, param2);
    }

    @Loga(level = Level.DEBUG)
    private void testDefaultEasyLog(int i, int j) {
        testEasyLog();
        mClickView.callOnClick();
    }

    @Loga(level = Level.ERROR)
    private void testEasyLog() {
        Logi.i("Logd test");
        Logi.d("logd test debug");
    }
}
