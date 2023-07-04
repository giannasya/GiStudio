package com.example.studio.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studio.Controller.OnBackPressedController;
import com.example.studio.Controller.RoomController;
import com.example.studio.Controller.RoomImagesController;
import com.example.studio.Model.RoomModel;
import com.example.studio.R;

public class Ruang extends AppCompatActivity {
    private ImageView image;
    private Button previousBtn, nextBtn, bookBtn;
    private RoomImagesController imagesController;
    private RoomController controller;
    private RoomModel model;
    private OnBackPressedController onBackPressedController;
    private int index;
    private Context context;
    private String username;
    private TextView roomText, guitarText, bassText, drumText, priceText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruang);

        username = getIntent().getStringExtra("username");
        index = getIntent().getIntExtra("roomIndex", 0);
        Log.d("username", username);

        model = new RoomModel();
        context = this;

        onBackPressedController = new OnBackPressedController();
        imagesController = new RoomImagesController();
        controller = new RoomController(model);

        image = findViewById(R.id.room_image);
        previousBtn = findViewById(R.id.prev);
        nextBtn = findViewById(R.id.next);
        bookBtn = findViewById(R.id.booking);

        roomText = findViewById(R.id.roomText);
        guitarText = findViewById(R.id.guitarText);
        bassText = findViewById(R.id.bassText);
        drumText = findViewById(R.id.drumText);
        priceText = findViewById(R.id.priceText);

        controller.setRoomText(roomText, index, model);
        imagesController.setImage(context,image, index);
        controller.readRoomDetail(model, guitarText, bassText, drumText, priceText);
        Log.d("index", String.valueOf(index));


        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagesController.previousImage();
//                index = imagesController.setImage(context,image, index);
                index = controller.previousRoom(roomText, index,
                        model, guitarText, bassText, drumText, priceText,context,image);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagesController.nextImage();
//                index = imagesController.setImage(context,image, index);
                index = controller.nextRoom(roomText, index, model,
                        guitarText, bassText, drumText, priceText,context, image);
            }
        });

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ruang.this, Booking.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed() {
        onBackPressedController.toHome(context, username);
    }
}