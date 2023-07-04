package com.example.studio.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.studio.Controller.AuthController;
import com.example.studio.Controller.RoomController;
import com.example.studio.Controller.RoomImagesController;
import com.example.studio.R;

public class Home extends AppCompatActivity {

    private CardView card1, card2, card3, card4, card5, card6, card7, card8;
    private ImageView rooma, roomb, roomc, roomd, roome, roomf, roomg, roomh;
    private String username;
    private RoomImagesController roomImagesController;
    private AuthController controller;
    private Context context = this;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        username = getIntent().getStringExtra("username");
        Log.d("username", username);

        controller = new AuthController();
        roomImagesController = new RoomImagesController();

        rooma = findViewById(R.id.rooma);
        roomb = findViewById(R.id.roomb);
        roomc = findViewById(R.id.roomc);
        roomd = findViewById(R.id.roomd);
        roome = findViewById(R.id.roome);
        roomf = findViewById(R.id.roomf);
        roomg = findViewById(R.id.roomg);
        roomh = findViewById(R.id.roomh);

        roomImagesController.setRoomImage(context, rooma, roomb, roomc, roomd, roome, roomf, roomg, roomh);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        card6 = findViewById(R.id.card6);
        card7 = findViewById(R.id.card7);
        card8 = findViewById(R.id.card8);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 0;
                navigateToRoomX(x);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 1;
                navigateToRoomX(x);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 2;
                navigateToRoomX(x);
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 3;
                navigateToRoomX(x);
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 4;
                navigateToRoomX(x);
            }
        });

        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 5;
                navigateToRoomX(x);
            }
        });

        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 6;
                navigateToRoomX(x);
            }
        });

        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 7;
                navigateToRoomX(x);
            }
        });
    }

    private void navigateToRoomX(int x) {
        Intent intent = new Intent(Home.this, Ruang.class);
        intent.putExtra("roomIndex", x);
        intent.putExtra("username", username);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.history) {
            Intent intent = new Intent(Home.this, History.class);
            intent.putExtra("username", getIntent().getStringExtra("username"));
            startActivity(intent);
            return true;
        }else if (itemId == R.id.logout) {
            Intent intent = new Intent(Home.this, Login.class);
            getIntent().removeExtra("email");
            getIntent().removeExtra("username");
            startActivity(intent);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this,"Tekan sekali lagi untuk keluar",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = true;
            }
        },3000);
    }

}
