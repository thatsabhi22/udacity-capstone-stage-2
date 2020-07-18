package com.udacity.spacebinge.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.udacity.spacebinge.R;
import com.udacity.spacebinge.utils.AppUtil;

import java.util.concurrent.ExecutionException;

public class SignUpActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN = 22;
    private static final String TAG = "SignUpActivity";
    Button google_sign_in_btn;
    TextView sign_up_no_internet_tv;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    boolean isOffline;
    private ProgressDialog pDialog;

    @SuppressLint({"ClickableViewAccessibility", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        google_sign_in_btn = findViewById(R.id.google_sign_in_btn);
        sign_up_no_internet_tv = findViewById(R.id.sign_up_no_internet_tv);

        // Initializing Google sign-in Options Object
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        // Setting up onclick listener on sign-up button
        google_sign_in_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pDialog = new ProgressDialog(SignUpActivity.this);
                pDialog.setMessage("Stars can't Shine without Darkness...");
                pDialog.setCancelable(false);

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    google_sign_in_btn.setBackground(getDrawable(R.drawable.btn_google_signin_light));

                    try {
                        isOffline = new AppUtil.CheckOnlineStatus().execute().get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Sign In only when Network is available
                    if (!isOffline) {
                        AppUtil.showpDialog(pDialog);
                        signIn();
                        sign_up_no_internet_tv.setVisibility(View.GONE);
                    } else {
                        sign_up_no_internet_tv.setVisibility(View.VISIBLE);
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    google_sign_in_btn.setBackground(getDrawable(R.drawable.btn_google_signin));
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }
}
