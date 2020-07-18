package com.udacity.spacebinge.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.udacity.spacebinge.R;

public class CreditsActivity extends AppCompatActivity {

    TextView credits_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        credits_tv = findViewById(R.id.credits_tv);
        credits_tv.setText(getString(R.string.credit_text));
    }
}
