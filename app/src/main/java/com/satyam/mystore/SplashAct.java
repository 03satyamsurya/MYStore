package com.satyam.mystore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashAct extends AppCompatActivity {

    private FirebaseAuth firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SystemClock.sleep(3000);
        firebase = FirebaseAuth.getInstance();
    }
    protected void onStart()
    {
        super.onStart();
        FirebaseUser currentuser = firebase.getCurrentUser();
        if(currentuser == null)
        {
            Intent registerIntent = new Intent(SplashAct.this,RegisterActivity.class);
            startActivity(registerIntent);
            finish();

        }else{
            Intent mainIntent = new Intent(SplashAct.this,MainActivity.class);
            startActivity(mainIntent);
            finish();

        }
    }
}