package com.udacity.spacebinge.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.udacity.spacebinge.R;

public class ProfileActivity extends AppCompatActivity {

    TextView user_name_tv, email_address_tv;
    Button sign_out_btn;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        user_name_tv = findViewById(R.id.user_name_tv);
        email_address_tv = findViewById(R.id.email_address_tv);
        sign_out_btn = findViewById(R.id.sign_out_btn);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            user_name_tv.setText(signInAccount.getDisplayName());
            email_address_tv.setText(signInAccount.getEmail());
        }

        sign_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
