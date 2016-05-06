package kr.pe.burt.android.lib.androidactivity;

import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public class AndroidActivity extends AppCompatActivity {

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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listenPreDrawEvent();
    }

    @Override
    protected void onPause() {
        viewWillDisappear();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewDidDisappear();
    }

    private void listenLayoutChangeEvent() {
        final View rootView = getWindow().getDecorView().getRootView();
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
        final View rootView = getWindow().getDecorView().getRootView();
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
        View rootView = getWindow().getDecorView().getRootView();
        if(rootView == null) return;
        rootView.requestLayout();
    }

    protected final void invalidateViews() {
        listenPreDrawEvent();
        View rootView = getWindow().getDecorView().getRootView();
        if(rootView == null) return;
        rootView.invalidate();
    }

    protected void viewDidLoad() {
    }

    protected void viewDidLayout() {
    }

    protected void viewWillAppear() {
    }

    protected void viewDidAppear() {
    }

    protected void viewWillDisappear() {
    }

    protected void viewDidDisappear() {
    }
}
