package kr.pe.burt.android.lib.androidactivity.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.pe.burt.android.lib.androidactivity.AndroidActivity;

public class SecondActivity extends AndroidActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
