package com.example.studio.Controller;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.studio.Config.config;
import com.example.studio.Model.AuthModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AuthController extends BaseController{

    private AuthModel model;
    private Activity activity;
    private DatabaseReference dbRef;
    private static String DB_REF = "user";
    private static String unameDB = "username";
    private static String passDB = "password";
    private ArrayList<AuthModel> authList;
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

    public void validate(Context context, String email, String username, String password, String repass){
        List<AuthModel> authModelList = new ArrayList();
        boolean emailGood;
        boolean usernameGood;
        boolean passGood;
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AuthModel result = new AuthModel();
                for(DataSnapshot data: snapshot.getChildren()){
                    result = data.getValue(AuthModel.class);
                    authModelList.add(result);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });
        if(email==null || !email.contains("@") || username==null || repass==null || password==null){
            Toast.makeText(context, "Please fill the data correctly", Toast.LENGTH_SHORT).show();
        }

        if(!authModelList.contains(email)){
            emailGood = true;
        }else{
            Toast.makeText(context, "Email already exists", Toast.LENGTH_SHORT).show();
            emailGood = false;
        }

        if(!authModelList.contains(username)){
            usernameGood = true;
        }else{
            Toast.makeText(context, "Username already exists", Toast.LENGTH_SHORT).show();
            usernameGood = false;
        }

        if(password==repass){
            passGood = true;
        }else{
            Toast.makeText(context, "Password does not match", Toast.LENGTH_SHORT).show();
            passGood = false;
        }

        if(emailGood && usernameGood && passGood){
            pushAuth(email, username, password);
        }
    }

    public void pushAuth(String email, String username, String password){
        model.setEmail(email);
        model.setUsername(username);
        model.setPassword(password);
        dbRef.setValue(model);
    }
}
