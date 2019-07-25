package com.snapcode.twitterpush.Actvity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.OAuthProvider;
import com.snapcode.twitterpush.R;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

/*Splash screen for 3 second */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}
