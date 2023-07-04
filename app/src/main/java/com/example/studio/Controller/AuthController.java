package com.example.studio.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.studio.Activity.History;
import com.example.studio.Activity.Login;
import com.example.studio.Activity.Register;
import com.example.studio.Config.config;
import com.example.studio.Model.AuthModel;
import com.example.studio.Model.RoomModel;
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

    public void toRegister(Context context){
        Intent intent = new Intent(context, Register.class);
        context.startActivity(intent);
    }

    public void getData(Context context, String email, String username, String password, String repass){
        List<AuthModel> authModelList = new ArrayList();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    AuthModel result = data.getValue(AuthModel.class);
                    result.setUsername(data.child("username").getValue().toString());
                    result.setEmail(data.child("email").getValue().toString());
                    authModelList.add(result);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });
        try{
            validate(authModelList, email, username, password, repass, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validate(List<AuthModel> authModelList, String email, String username, String password, String repass,Context context){
        boolean emailExists = false;
        boolean usernameExists = false;
        for(AuthModel data: authModelList){
            Log.d("context", "masuk loop list");
            if(data.getEmail().equals(email)){
                emailExists = true;
            Log.d("context", data.getEmail());
            }
            if(data.getUsername().equals(username)){
                usernameExists = true;
            Log.d("context", data.getUsername());
            }
        }
        if(email.equals("") || !email.contains("@") || username.equals("") || repass.equals("") || password.equals("")){
            Toast.makeText(context, "Please fill the data correctly", Toast.LENGTH_SHORT).show();
        }

        if(emailExists){
            Toast.makeText(context, "Email already exists", Toast.LENGTH_SHORT).show();
            Log.d("email exist", String.valueOf(!emailExists));
        }

        if(usernameExists){
            Toast.makeText(context, "Username already exists", Toast.LENGTH_SHORT).show();
        }

        if(!password.equals(repass)){
            Toast.makeText(context, "Password does not match", Toast.LENGTH_SHORT).show();
        }

        Log.d("email exist", String.valueOf(emailExists));
        if(!emailExists && !usernameExists && password.equals(repass) && !email.equals("") && !username.equals("") && !repass.equals("")){
            pushAuth(email, username, password, context);
        }
    }

    public void pushAuth(String email, String username, String password, Context context){
        Log.d("push Auth", email);
        model = new AuthModel();
        model.setEmail(email);
        model.setUsername(username);
        model.setPassword(password);
        if(!model.getUsername().equals("") && !model.getEmail().equals("") && !model.getPassword().equals("")){
            String id = dbRef.push().getKey();
            dbRef.child(id).setValue(model);
            Intent intent = new Intent(context, Login.class);
            Toast.makeText(context, "Register successful", Toast.LENGTH_SHORT).show();
            context.startActivity(intent);
            activity.finish();
        }else{
            Toast.makeText(context, "Please Input the data correctly", Toast.LENGTH_SHORT).show();
        }
    }

    public void toLogin(Context context){
        Intent intent = new Intent(context, Login.class);
        context.startActivity(intent);
    }
    public void toHistory(Context context, String username){
        Intent intent = new Intent(context, History.class);
        intent.putExtra("username", username);
        context.startActivity(intent);
    }
}
