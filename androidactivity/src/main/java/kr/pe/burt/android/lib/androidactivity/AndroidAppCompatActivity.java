package kr.pe.burt.android.lib.androidactivity;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public class AndroidAppCompatActivity extends AppCompatActivity {

    private SoftKeyboardHelper softKeyboardHelper = null;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        listenLayoutChangeEvent();
        viewDidLoad();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        listenLayoutChangeEvent();
        viewDidLoad();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        listenLayoutChangeEvent();
        viewDidLoad();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listenPreDrawEvent();

        softKeyboardHelper = new SoftKeyboardHelper();
        softKeyboardHelper.listenSoftKeyboardEvent(getWindow().getDecorView().getRootView(), new SoftKeyboardHelper.AppearCallback() {
            @Override
            public void callback(int keyboardHeight) {
                keyboardDidAppear(keyboardHeight);
            }
        }, new SoftKeyboardHelper.DisappearCallback() {
            @Override
            public void callback() {
                keyboardDidDisappear();
            }
        });
    }

    @Override
    protected void onPause() {
        viewWillDisappear();
        super.onPause();

        if(softKeyboardHelper != null) {
            softKeyboardHelper.removeListenerForSoftKeyboardEvent();
            softKeyboardHelper = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewDidDisappear();


    }

    private void listenLayoutChangeEvent() {
        final View rootView = getWindow().getDecorView();
        if(rootView == null) return;
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewDidLayout();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

    }

    private void listenPreDrawEvent() {
        final View rootView = getWindow().getDecorView();
        if(rootView == null) return;
        rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                viewWillAppear();
                rootView.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }


    protected final void requestLayoutSubviews() {
        listenLayoutChangeEvent();
        View rootView = getWindow().getDecorView();
        if(rootView == null) return;
        rootView.requestLayout();
    }

    protected final void invalidateViews() {
        listenPreDrawEvent();
        View rootView = getWindow().getDecorView();
        if(rootView == null) return;
        rootView.invalidate();
    }

    protected void viewDidLoad() {
    }

    protected void viewDidLayout() {
    }

    protected void viewWillAppear() {
    }

    protected void viewWillDisappear() {
    }

    protected void viewDidDisappear() {
    }

    protected void keyboardDidAppear(int keyboardHeight) {
    }

    protected void keyboardDidDisappear() {
    }
}
