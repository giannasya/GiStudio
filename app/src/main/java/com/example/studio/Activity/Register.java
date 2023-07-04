package com.example.studio.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.studio.Controller.AuthController;
import com.example.studio.R;

public class Register extends AppCompatActivity {
    private EditText iEmail, iUser, iPass, iRepass;
    private Button register, back;
    private AuthController controller;
    private Context context;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        controller = new AuthController();

        iEmail = findViewById(R.id.inputEmail);
        iUser = findViewById(R.id.inputUser);
        iPass = findViewById(R.id.inputPass);
        iRepass = findViewById(R.id.inputRepass);

        register = findViewById(R.id.register);
        back = findViewById(R.id.backLogin);

        context = this;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.toLogin(context);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = iEmail.getText().toString().trim();
//                String username = iUser.getText().toString().trim();
                String pass = iPass.getText().toString().trim();
//                String repass = iRepass.getText().toString().trim();
//                Log.d("pass", pass);
//                Log.d("repass", repass);
//
//                controller.getData(context, email, username, pass, repass);
                controller.registerAuth(context, email, pass);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}