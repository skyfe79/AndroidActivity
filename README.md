
# AndroidActivity

![](art/logo.png)

There are many questions about timing for finding Android's view size and detecting appear or disappear of soft keyboard.

 * When can I get android's view size?
 * How to find view width and height before it's drawn?
 * How to detect appear and disappear of soft keyboad?
 * How to get the keyboard size?

If you want to find view size in an activity, using ViewTreeObserver is the answer. However, you have to write some dirty code to use ViewTreeObserver. 

If you find some simple method, AndroidActivity is the solution.

## Setup Gradle

```groovy
dependencies {
    ...
    compile 'kr.pe.burt.android.lib:androidactivity:0.0.2'
}
```
## Activities
 
 * AndroidAppCompatActivity extends AppCompatActivity
 * AndroidActivity extends Activity

 
## Bye Bye ViewTreeObserver

AndroidActivity provides lifecycle callback methods from the aspect of view. If you use these callback methods, you don't have to use ViewTreeObserver. 

* viewDidLoad
 * Called when the content view of activity is setted. 
* viewDidLayout
 * Called when the view's layout is completed.
* viewWillAppear
 * Called when the view's appearance is about to draw. 
* viewWillDisappear
 * Called when the view is about to disappear. 
* viewDidDisappear
 * Called when the view is disappeared.  
* keyboardDidAppear
 * Called when the soft keyboard is appear with keyboard's height in pixel.
* keyboardDidDisappear
 * Called when the soft keyboard is disappear.   

It is similar to the lifecycle of view which is in UIViewController on the iOS.

## When can you get view's size?

* viewDidLayout
* viewWillAppear

You can get the size of view and the position of view on the viewDidLayout, viewWillAppear method. If you need to use view's geometry, you just override that methods and use the view's geometry methods like view.left(), view.top(), view.width() and view.height()

## Callback ordering.

### Launch App

 1. viewDidLoad
 2. viewDidLayout
 3. viewWillAppear

### Rotate App

 1. viewWillDisappear
 2. viewDidDisappear
 3. viewDidLoad
 4. viewDidLayout
 5. viewWillAppear

### Tap the Home button.

 1. viewWillDisappear
 2. viewDidDisappear

### Restore from app history stack.
 
 1. viewWillAppear

### Go to another activity

 1. viewWillDisappear
 2. viewDidDisappear

### Back from another activity

 1. viewWillAppear

## How to use AndroidActivity?

You just inherit AndroidActivity and override above methods.

```java
public class MainActivity extends AndroidActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

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
    }

    @Override
    protected void viewDidDisappear() {
        Log.v(TAG, "viewDidDisappear " + buttonGeometryInfo());
    }

    private String buttonGeometryInfo() {
        Button button = (Button) findViewById(R.id.button);
        return " [button] :: Left = " + button.getLeft() + ", Top = " + button.getTop() + ", Width = " + button.getWidth() + ", Height = " + button.getHeight();
    }

    void onButtonClicked(View sender) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}
```

## How to use keyboard callbacks?

You don't need to set the proprty `android:windowSoftInputMode="adjustResize"` in the AndroidManifest.xml file. You just inherit AndroidActivity and override `keyboardDidAppear` and `keyboardDidDisappear` methods.

```
private int lastKeyboardHeight = 0;

@Override
public void keyboardDidAppear(int keyboardHeight) {
    Log.v("TAG", "MainActivity : keyboardDidAppear");
    scrollUp(keyboardHeight);
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
```

## MIT License

The MIT License (MIT)

Copyright (c) 2016 Sungcheol Kim, [https://github.com/skyfe79/AndroidActivity](https://github.com/skyfe79/AndroidActivity)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE. 