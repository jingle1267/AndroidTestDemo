package com.ihongqiqu.instrumentationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
