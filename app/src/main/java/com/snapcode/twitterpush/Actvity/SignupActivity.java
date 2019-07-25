package com.snapcode.twitterpush.Actvity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.snapcode.twitterpush.Model.UserModel;
import com.snapcode.twitterpush.R;
import com.snapcode.twitterpush.Utli.OpenHelper;
import com.snapcode.twitterpush.Utli.SessionManagement;

import java.security.Provider;

import io.fabric.sdk.android.services.common.SafeToast;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseuser;
    private Button btnTwitter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private SessionManagement management;
    private String fUserId,email,pushId,name,pushToken;
    private Uri photoUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseuser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference(OpenHelper.USER_REFERENCE);

        btnTwitter = findViewById(R.id.btnTwitter);
        btnTwitter.setOnClickListener(this);

        management = new SessionManagement(SignupActivity.this);

        /*generate Refresh token for push notification and refresh every time if user clearData,Uninstall app so update token every time*/
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            pushToken  = task.getResult().getToken();
                            Log.d("PushToken",""+pushToken);
                        }else {
                            Toast.makeText(SignupActivity.this,"Token Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        /*Check User  is Already Login Or Not */
            setIsLoginUser();
    }

    private void setIsLoginUser(){

        /*If user data is get then direct move to profile screen*/

        management.getFirebaseUemail();
        management.getIsLogin();

        if (management.getIsLogin() && management.getFirebaseUemail() != null){
            Intent intent = new Intent(SignupActivity.this,ProfileActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnTwitter :
                setAuthication();
                break;

        }


    }

    private void setAuthication() {
        OAuthProvider.Builder builder = OAuthProvider.newBuilder("twitter.com");
            Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
            if (pendingResultTask != null) {
                pendingResultTask.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(SignupActivity.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                    /*if user have not alreeady signin then check task result and sign in and get user data from account*/
                firebaseAuth.startActivityForSignInWithProvider(SignupActivity.this, builder.build())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                name = authResult.getUser().getDisplayName();
                                photoUrl = authResult.getUser().getPhotoUrl();
                                email = authResult.getUser().getEmail();
                                fUserId = firebaseAuth.getCurrentUser().getUid();
                                Log.d("----0",""+authResult.getUser().getPhotoUrl());

                                /*Check User Email already exist or not in Firebase RealTime Database after that store in realtime Database*/
                                databaseReference.orderByChild(OpenHelper.USER_EMAIL).equalTo(email).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if (!dataSnapshot.exists()){
                                            pushId = databaseReference.push().getKey();
                                            UserModel userModel = new UserModel(name, fUserId, email, photoUrl.toString(), pushId, pushToken);
                                            databaseReference.child(pushId).setValue(userModel);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                /*Share Data on SharedPreference Management*/
                                management.setFirebaseEmail(email);
                                management.setFirebaseUid(fUserId);
                                management.setFirebaseUname(name);
                                management.setFirebasePushid(pushId);
                                management.setFirebaseUphoto(photoUrl.toString());
                                management.setFirebaseToken(pushToken);
                                management.setIsLogin(true);


                                /*If User Successfully Login then Open Profile Activity*/
                                Intent intent = new Intent(SignupActivity.this,ProfileActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(SignupActivity.this, "BigError" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
    }
}
