package kr.pe.burt.android.lib.androidactivity;

import android.graphics.Rect;
import android.os.Build;
import android.telecom.Call;
import android.view.View;
import android.view.ViewTreeObserver;

import java.lang.ref.WeakReference;

/**
 * Created by burt on 2016. 5. 17..
 * @link { https://pspdfkit.com/blog/2016/keyboard-handling-on-android/?utm_source=Android+Weekly&utm_campaign=f7b91895ba-Android_Weekly_205&utm_medium=email&utm_term=0_4eb677ad19-f7b91895ba-337849133 }
 */
class SoftKeyboardHelper {

    interface AppearCallback {
        void callback(int keyboardHeight);
    }

    interface DisappearCallback {
        void callback();
    }


    // Threshold for minimal keyboard height.
    private final static int MIN_KEYBOARD_HEIGHT_PX = 150;
    private WeakReference<AppearCallback> weakAppearCallback = null;
    private WeakReference<DisappearCallback> weakDisappearCallback = null;
    private WeakReference<View> weakView = null;

    void listenSoftKeyboardEvent(final View view, AppearCallback appearCallback, DisappearCallback disappearCallback) {
        weakView = new WeakReference<>(view);
        weakAppearCallback = new WeakReference<>(appearCallback);
        weakDisappearCallback = new WeakReference<>(disappearCallback);
        view.getViewTreeObserver().addOnGlobalLayoutListener(softKeyboardEventListener);
    }

    void removeListenerForSoftKeyboardEvent() {
        View view = weakView.get();
        if(view == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(softKeyboardEventListener);
        } else {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(softKeyboardEventListener);
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener softKeyboardEventListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        private final Rect windowVisibleDisplayFrame = new Rect();
        private int lastVisibleDecorViewHeight;

        @Override
        public void onGlobalLayout() {
            // Retrieve visible rectangle inside window.
            View view = weakView.get();
            if(view == null) return;




            view.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame);
            final int visibleDecorViewHeight = windowVisibleDisplayFrame.height();

            // Decide whether keyboard is visible from changing decor view height.
            if (lastVisibleDecorViewHeight != 0) {
                if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                    // Calculate current keyboard height (this includes also navigation bar height when in fullscreen mode).
                    int currentKeyboardHeight = view.getHeight() - windowVisibleDisplayFrame.bottom;
                    // Notify listener about keyboard being shown.
                    AppearCallback c = weakAppearCallback.get();
                    if(c != null) {
                        c.callback(currentKeyboardHeight);
                    }
                } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                    // Notify listener about keyboard being hidden.
                    DisappearCallback c = weakDisappearCallback.get();
                    if(c != null) {
                        c.callback();
                    }
                }
            }
            // Save current decor view height for the next call.
            lastVisibleDecorViewHeight = visibleDecorViewHeight;
        }
    };
}
