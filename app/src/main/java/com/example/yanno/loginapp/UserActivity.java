package com.example.yanno.loginapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    private TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        message = (TextView) findViewById(R.id.textView);
    }
}
