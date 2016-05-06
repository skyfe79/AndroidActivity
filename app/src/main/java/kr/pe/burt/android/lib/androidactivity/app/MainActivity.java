package kr.pe.burt.android.lib.androidactivity.app;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import kr.pe.burt.android.lib.androidactivity.AndroidActivity;

public class MainActivity extends AndroidActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void viewDidLoad() {

        Log.v(TAG, "viewDidLoad " + textViewGeometryInfo());
    }

    @Override
    protected void viewDidLayout() {
        Log.v(TAG, "viewDidLayout " + textViewGeometryInfo());
    }

    @Override
    protected void viewWillAppear() {
        Log.v(TAG, "viewWillAppear " + textViewGeometryInfo());
    }

    @Override
    protected void viewDidAppear() {
        Log.v(TAG, "viewDidAppear " + textViewGeometryInfo());
    }

    @Override
    protected void viewWillDisappear() {
        Log.v(TAG, "viewWillDisappear " + textViewGeometryInfo());
    }

    @Override
    protected void viewDidDisappear() {
        Log.v(TAG, "viewDidDisappear " + textViewGeometryInfo());
    }

    private String textViewGeometryInfo() {
        TextView textView = (TextView) findViewById(R.id.text);
        return " [textView] :: Left = " + textView.getLeft() + ", Top = " + textView.getTop() + ", Width = " + textView.getWidth() + ", Height = " + textView.getHeight();
    }
}
