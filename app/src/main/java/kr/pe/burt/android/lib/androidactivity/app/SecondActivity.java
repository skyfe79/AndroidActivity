package kr.pe.burt.android.lib.androidactivity.app;

import android.os.Bundle;
import android.util.Log;

import kr.pe.burt.android.lib.androidactivity.AndroidAppCompatActivity;

public class SecondActivity extends AndroidAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    public void keyboardDidAppear(int keyboardHeight) {
        Log.d("TAG", "keyboardDidAppear : " + keyboardHeight);
    }

    @Override
    public void keyboardDidDisappear() {
        Log.d("TAG", "keyboardDidDisappear");
    }
}
