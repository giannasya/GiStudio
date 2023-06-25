package com.example.studio.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studio.Controller.AuthController;
import com.example.studio.Model.AuthModel;
import com.example.studio.R;

public class login extends AppCompatActivity implements AuthController.AuthCallback{
    private AuthController controller;
    private AuthModel model;
    private Button loginBtn;
    private EditText userText, passText;
    private String uname, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginBtn = findViewById(R.id.loginbtn);
        userText = findViewById(R.id.usernametxt);
        passText = findViewById(R.id.passwordtxt);

        model = new AuthModel();
        controller = new AuthController((AuthController.AuthCallback) this);
    }
    public void loginHome(View view){
        uname = userText.getText().toString().trim();
        pass = passText.getText().toString().trim();

        Log.d("email = ", uname);

        model.setUsername(uname);
        model.setPassword(pass);

        controller.loginWithEmail(model);
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(login.this, R.string.LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(login.this, home.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailure() {
        Toast.makeText(login.this,R.string.LOGIN_FAILED, Toast.LENGTH_SHORT).show();
    }
}

