package com.example.studio.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.studio.Controller.AuthController;
import com.example.studio.Controller.OnBackPressedController;
import com.example.studio.R;

public class ForgotPassword extends AppCompatActivity {
    private AuthController controller;
    private EditText emailText;
    private OnBackPressedController onBackPressedController;
    private Button fp;
    private Context context;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        emailText = findViewById(R.id.fpEmail);
        fp =findViewById(R.id.reset);

        controller = new AuthController();
        onBackPressedController = new OnBackPressedController();

        context = this;

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString().trim();
                controller.sendEmail(context,email);
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void onBackPressed() {
        onBackPressedController.toLogin(context);
    }
}