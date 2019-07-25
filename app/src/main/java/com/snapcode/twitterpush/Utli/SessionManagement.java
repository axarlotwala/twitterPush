package com.snapcode.twitterpush.Utli;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.annotation.Retention;

public class SessionManagement {

    private static SessionManagement sessionManagement;
    Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String IS_LOGIN = "login_user";
    public static final String PREF_NAME = "twitter_push";
    public static final String FIREBASE_UID = "user_id";
    public static final String FIREBASE_UNAME = "user_name";
    public static final String FIREBASE_UEMAIL = "user_email";
    public static final String FIREBASE_UPHOTO = "user_photo";
    public static final String FIREBASE_TOKEN = "user_token";
    public static final String FIREBASE_PUSHID = "user_pushID";

    public SessionManagement(Context mcontext){
        context = mcontext;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setIsLogin(boolean isLogin){
        editor.putBoolean(IS_LOGIN,isLogin);
        editor.apply();
    }

    public boolean getIsLogin(){
        return sharedPreferences.getBoolean(IS_LOGIN,false);
    }

    public void setFirebaseUid(String firebaseUid){
        editor.putString(FIREBASE_UID,firebaseUid);
        editor.apply();
    }

    public String getFirebaseUid(){
        return sharedPreferences.getString(FIREBASE_UID,null);
    }

    public void setFirebaseUname(String userName){
        editor.putString(FIREBASE_UNAME,userName);
        editor.apply();
    }

    public String getFirebaseUname() {
        return sharedPreferences.getString(FIREBASE_UNAME,"");
    }

    public void setFirebaseEmail(String email){
        editor.putString(FIREBASE_UEMAIL,email);
        editor.apply();
    }

    public String getFirebaseUemail() {
        return sharedPreferences.getString(FIREBASE_UEMAIL,null);
    }


    public void setFirebaseUphoto(String photo){
        editor.putString(FIREBASE_UPHOTO,photo);
        editor.apply();
    }

    public String getFirebaseUphoto() {
        return sharedPreferences.getString(FIREBASE_UPHOTO,null);
    }

    public void setFirebasePushid(String pushId){
        editor.putString(FIREBASE_PUSHID,pushId);
        editor.apply();
    }

    public String getFirebasePushid(){
        return sharedPreferences.getString(FIREBASE_PUSHID,null);
    }

    public void setFirebaseToken(String token){
        editor.putString(FIREBASE_TOKEN,"");
        editor.apply();
    }

    public String getFirebaseToken() {
        return sharedPreferences.getString(FIREBASE_TOKEN,null);
    }

    public void clearAll(){

        if (editor != null){
            editor.clear();
            editor.commit();
            editor.apply();
        }
    }
}
