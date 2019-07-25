package com.snapcode.twitterpush.Actvity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.snapcode.twitterpush.R;
import com.snapcode.twitterpush.Utli.SessionManagement;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSendOffer,btnLogout;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    SessionManagement sessionManagement;
    ImageView imgProfile;
    TextView tvUserName,tvUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnSendOffer = findViewById(R.id.btnSendOffer);
        btnLogout = findViewById(R.id.btnLogout);

        btnSendOffer.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        sessionManagement = new SessionManagement(ProfileActivity.this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        imgProfile = findViewById(R.id.imgProfile);

        Log.d("photo---",""+sessionManagement.getFirebaseUphoto());
        setUserProfile();
    }

    /*get user session data if user is currenlty Login*/
    private void setUserProfile(){
        Glide.with(ProfileActivity.this).load(sessionManagement.getFirebaseUphoto()).into(imgProfile);
        tvUserName.setText(sessionManagement.getFirebaseUname());
        tvUserEmail.setText(sessionManagement.getFirebaseUemail());

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnSendOffer :
                Intent intent = new Intent(ProfileActivity.this,OfferActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btnLogout :
                clearAll();
                break;

        }
    }

        /*Clear user data if user is logout*/
    private void clearAll(){
        firebaseAuth.signOut();
        sessionManagement.clearAll();

        Intent intent  = new Intent(ProfileActivity.this,SignupActivity.class);
        startActivity(intent);
        finish();

    }


}

