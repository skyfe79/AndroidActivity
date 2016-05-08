
# AndroidActivity

![](art/logo.png)

There are many questions about timing for finding Android's view size.

 * When can I get android's view size?
 * How to find view width and height before it's drawn?

If you want to find view size in an activity, using ViewTreeObserver is the answer. However, you have to write some dirty code to use ViewTreeObserver. 

If you find some simple method, AndroidActivity is the solution.

## Setup Gradle

```groovy
dependencies {
    ...
    compile 'kr.pe.burt.android.lib:androidactivity:0.0.1'
}
```

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
 