Instrumentation
===============

之前学习过了monkey和monkeyrunner，这次来学习instrumentation。

<!-- more -->

## 什么是Instrumentation

  Android测试环境的核心是一个Instrumentation框架，在这个框架下，你的测试应用程序可以精确控制应用程序。使用Instrumentation，你可以在主程序启动之前，创建模拟的系统对象，如Context；控制应用程序的多个生命周期；发送UI事件给应用程序；在执行期间检查程序状态。Instrumentation框架通过将主程序和测试程序运行在同一个进程来实现这些功能。 
  
  android instrumentation是android系统中一系列的控制方法或者钩子（hooks）。这些hooks可以脱离组件的正常生命周期控制一个android组件。它同样可以控制android如何加载应用程序。

## 实现过程

  Instrumentation和Activity有点类似，只不过Activity是需要一个界面的，而Instrumentation并不是这样的，我们可以将它理解为一种没有图形界面的，具有启动能力的，用于监控其他类(用Target Package声明)的工具类。

### 配置Manifest

  在manifest中添加如下配置

```xml
<instrumentation android:targetPackage="com.package.name" android:name="android.test.InstrumentationTestRunner" />
```

### 编写被测试的类

  编写一个Activity，页面包含一个TextView，一个Button和一个Edittext。另外添加add()方法和isTrue()方法。

  布局文件如下:

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="com.ihongqiqu.instrumentationdemo.MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        android:id="@+id/tv_test"
        android:layout_marginTop="47dp"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"/>

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="test"
        android:id="@+id/btn_test"
        android:layout_below="@+id/tv_test"
        android:layout_centerHorizontal="true"
        android:onClick="onClick"
        android:layout_marginTop="30dp"/>

    <EditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/et_name"
        android:layout_centerVertical="true"
        android:layout_alignParentRight="true"
        android:layout_alignParentEnd="true"
        android:hint="Input your name"/>
</RelativeLayout>
```

  Activity代码如下：

```java
public class MainActivity extends AppCompatActivity {

    private TextView tvTest;
    private Button btnTest;
    private EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.etName = (EditText) findViewById(R.id.et_name);
        this.btnTest = (Button) findViewById(R.id.btn_test);
        this.tvTest = (TextView) findViewById(R.id.tv_test);
    }

    public void onClick(View view) {
        if (BuildConfig.DEBUG) Log.d("MainActivity", "Test button clicked");
        tvTest.setText("TEST");
    }

    public int add(int a, int b) {
        return a + b;
    }

    public boolean isTrue(Boolean bool) {
        return Boolean.TRUE == bool;
    }

}
```

### 编写测试类

  测试类实现对按钮点击，文本输入和对add()方法和isTrue()方法的测试，代码如下：

```java
/**
 * MainActivity测试类
 *
 * Created by zhenguo on 1/6/16.
 */
public class MainActivityTest extends InstrumentationTestCase {

    private MainActivity mainActivity;
    private Button btnTest;
    private TextView tvTest;
    private EditText etName;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        intent.setClassName(MainActivity.class.getPackage().getName(), MainActivity.class.getName());
        // 必须添加FLAG_ACTIVITY_NEW_TASK这个flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mainActivity = (MainActivity) getInstrumentation().startActivitySync(intent);
        tvTest = (TextView) mainActivity.findViewById(R.id.tv_test);
        btnTest = (Button) mainActivity.findViewById(R.id.btn_test);
        etName = (EditText) mainActivity.findViewById(R.id.et_name);
    }

    @Override
    protected void tearDown() throws Exception {
        mainActivity.finish();
        super.tearDown();
    }

    public void testActivity() {
        Log.d("MainActivityTest", "testActivity()");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                btnTest.performClick();
            }
        });
        SystemClock.sleep(3000);
        assertEquals("TEST", tvTest.getText().toString());
    }

    public void testAdd() {
        Log.d("MainActivityTest", "testAdd()");
        int result = mainActivity.add(3, 5);
        assertEquals(8, result);
    }

    public void testIsTure() {
        Log.d("MainActivityTest", "testIsTure()");
        assertTrue(mainActivity.isTrue(Boolean.TRUE));
        assertFalse(mainActivity.isTrue(Boolean.FALSE));
        assertFalse(mainActivity.isTrue(null));
    }

    public void testEditText() {
        Log.d("MainActivityTest", "testEditText()");
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                etName.requestFocus();

            }
        });
        SystemClock.sleep(2000);
        // this.sendKeys("jingle1267");
        getInstrumentation().sendStringSync("jingle1267");
        SystemClock.sleep(2000);
        assertEquals("jingle1267", etName.getText().toString());
    }

}
```

### 运行测试代码

  有两种方法运行测试，分别是：

 * IDE中右击 <code>XXXTest.java</code> 选择 <code>Run ‘XXXTest’</code>
 * 手机中选择 <code>Dev Tools</code> ，然后选择Instrumentation选项，然后选择 <code>Test for you.package.name</code>

### 源码

  源码地址 [https://github.com/jingle1267/AndroidTestDemo](https://github.com/jingle1267/AndroidTestDemo)

  如有任何问题，欢迎留言。
