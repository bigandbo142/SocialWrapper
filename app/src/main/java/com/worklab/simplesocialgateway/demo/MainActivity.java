package com.worklab.simplesocialgateway.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.worklab.simplesocialgateway.R;
import com.worklab.simplesocialgateway.SimpleFacebookActivity;

public class MainActivity extends SimpleFacebookActivity {

    private Button btn_login_fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login_fb = (Button) findViewById(R.id.btn_login_fb);
        btn_login_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
    }
}
