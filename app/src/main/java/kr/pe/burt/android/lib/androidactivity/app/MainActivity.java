package kr.pe.burt.android.lib.androidactivity.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import kr.pe.burt.android.lib.androidactivity.AndroidAppCompatActivity;

public class MainActivity extends AndroidAppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private int lastKeyboardHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void viewDidLoad() {

        Log.v(TAG, "viewDidLoad " + buttonGeometryInfo());
    }

    @Override
    protected void viewDidLayout() {
        Log.v(TAG, "viewDidLayout " + buttonGeometryInfo());
    }

    @Override
    protected void viewWillAppear() {
        Log.v(TAG, "viewWillAppear " + buttonGeometryInfo());
    }


    @Override
    protected void viewWillDisappear() {
        Log.v(TAG, "viewWillDisappear " + buttonGeometryInfo());
        if(lastKeyboardHeight != 0) {
            scrollDown();
        }
    }

    @Override
    protected void viewDidDisappear() {
        Log.v(TAG, "viewDidDisappear " + buttonGeometryInfo());
    }

    private String buttonGeometryInfo() {
        Button button = (Button) findViewById(R.id.button);
        return " [button] :: Left = " + button.getLeft() + ", Top = " + button.getTop() + ", Width = " + button.getWidth() + ", Height = " + button.getHeight();
    }

    public void onButtonClicked(View sender) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }


    @Override
    public void keyboardDidAppear() {
        Log.v("TAG", "MainActivity : keyboardDidAppear");
        scrollUp(0);
    }

    @Override
    public void keyboardDidDisappear() {
        Log.v("TAG", "MainActivity : keyboardDidDisappear");
        scrollDown();
    }

    private void scrollUp(int keyboardHeight) {
        View layout = findViewById(R.id.layout);
        layout.scrollBy(0, keyboardHeight/2);
        lastKeyboardHeight = keyboardHeight/2;
    }

    private void scrollDown() {
        View layout = findViewById(R.id.layout);
        layout.scrollBy(0, -lastKeyboardHeight);
        lastKeyboardHeight = 0;
    }
}
