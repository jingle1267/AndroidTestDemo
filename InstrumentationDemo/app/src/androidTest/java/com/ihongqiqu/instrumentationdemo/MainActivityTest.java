package com.ihongqiqu.instrumentationdemo;

import android.content.Intent;
import android.os.SystemClock;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

/**
 * MainActivity测试类
 *
 * Created by zhenguo on 1/6/16.
 */
public class MainActivityTest extends InstrumentationTestCase {

    private MainActivity mainActivity;
    private Button btnTest;
    private TextView tvTest;

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

        assertEquals("Test", tvTest.getText().toString());
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
        assertFalse(mainActivity.isTrue(Boolean.FALSE));
    }

}
