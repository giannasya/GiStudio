package com.example.studio.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studio.Controller.RoomController;
import com.example.studio.Controller.RoomImagesController;
import com.example.studio.Model.RoomModel;
import com.example.studio.R;

public class ruang extends AppCompatActivity {
    private ImageView image;
    private Button previousBtn, nextBtn, bookBtn;
    private RoomImagesController imagesController;
    private RoomController controller;
    private RoomModel model;
    private int index, roomIndex;
    private TextView roomTxt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruang);

        index = getIntent().getIntExtra("roomIndex", 0);

        imagesController = new RoomImagesController();
        controller = new RoomController(model);

        model = new RoomModel();

        image = findViewById(R.id.room_image);
        previousBtn = findViewById(R.id.prev);
        nextBtn = findViewById(R.id.next);
        bookBtn = findViewById(R.id.booking);
        roomTxt = findViewById(R.id.roomText);

        controller.setRoomText(roomTxt, index, model);
        imagesController.setImage(image);

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagesController.previousImage();
                imagesController.setImage(image);
                index = controller.previousRoom(roomTxt, index, model);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagesController.nextImage();
                imagesController.setImage(image);
                index = controller.nextRoom(roomTxt, index, model);
            }
        });

//        bookBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ruang.this, reservation.class);
//                startActivity(intent);
//            }
//        });
    }
}