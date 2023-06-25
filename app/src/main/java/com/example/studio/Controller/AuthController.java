package com.example.studio.Controller;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.studio.Config.config;
import com.example.studio.Model.AuthModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class AuthController extends BaseController{

    private AuthModel model;
    private Activity activity;
    private DatabaseReference dbRef;
    private static String DB_REF = "user";
    private static String unameDB = "username";
    private static String passDB = "password";
    private AuthCallback authCallback;

    public AuthController(){
        dbRef = config.connection(DB_REF);
    }
    public AuthController(Activity activity){
        dbRef = config.connection(DB_REF);
        this.activity = activity;
    }
    public AuthController(AuthModel model){
        dbRef = config.connection(DB_REF);
        this.model = model;
    }
    public interface AuthCallback {
        void onLoginSuccess();
        void onLoginFailure();
    }

    public AuthController(AuthCallback authCallback){
        dbRef = config.connection(DB_REF);
        this.authCallback = authCallback;
    }

    public void loginWithEmail(AuthModel model){
        config.connection(DB_REF).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean loginSuccessful = false;
                for (DataSnapshot authSnapshot : dataSnapshot.getChildren()) {
                    String username = authSnapshot.child(unameDB).getValue(String.class);
                    String password = authSnapshot.child(passDB).getValue(String.class);
                    if (username != null && username.equals(model.getUsername())
                            && password != null && password.equals(model.getPassword())) {
                        loginSuccessful = true;
                        authCallback.onLoginSuccess();
                        break;
                    }
                }
                if (!loginSuccessful) {
                    authCallback.onLoginFailure();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("gagal");
            }
        });
    }

}
