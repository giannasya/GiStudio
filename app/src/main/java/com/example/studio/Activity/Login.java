package com.example.studio.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studio.Controller.AuthController;
import com.example.studio.Model.AuthModel;
import com.example.studio.R;

public class Login extends AppCompatActivity implements AuthController.AuthCallback{
    private AuthController controller;
    private AuthModel model;
    private Button loginBtn;
    private EditText userText, passText;
    private String uname, pass;
    private Context context;
    private TextView registerBtn, forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        forget = findViewById(R.id.forgot);
        registerBtn = findViewById(R.id.register);
        loginBtn = findViewById(R.id.loginbtn);
        userText = findViewById(R.id.usernametxt);
        passText = findViewById(R.id.passwordtxt);

        context = this;

        model = new AuthModel();
        controller = new AuthController((AuthController.AuthCallback) this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userText.getText().toString().trim();
                String password = passText.getText().toString().trim();
                controller.loginAuth(context, username, password);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.toRegister(context);
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "praba.elmahdi@gmail.com";
                controller.sendEmail(email);
            }
        });

    }

//    public void loginHome(View view){
//        uname = userText.getText().toString().trim();
//        pass = passText.getText().toString().trim();
//
//        Log.d("email = ", uname);
//
//        model.setUsername(uname);
//        model.setPassword(pass);
//
//        Log.d("model", model.getUsername());
//
//        controller.loginWithEmail(model);
//    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(Login.this, R.string.LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Login.this, Home.class);
        intent.putExtra("username", model.getUsername());
        startActivity(intent);
    }

    @Override
    public void onLoginFailure() {
        Toast.makeText(Login.this,R.string.LOGIN_FAILED, Toast.LENGTH_SHORT).show();
    }

}

