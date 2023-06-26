package com.example.studio.Activity;

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

public class Ruang extends AppCompatActivity {
    private ImageView image;
    private Button previousBtn, nextBtn, bookBtn;
    private RoomImagesController imagesController;
    private RoomController controller;
    private RoomModel model;
    private int index;
    private TextView roomText, guitarText, bassText, drumText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruang);

        index = getIntent().getIntExtra("roomIndex", 0);

        model = new RoomModel();

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

        controller.setRoomText(roomText, index, model);
        imagesController.setImage(image);
        controller.readRoomDetail(model, guitarText, bassText, drumText);

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                controller.readRoomDetail(model);
                imagesController.previousImage();
                imagesController.setImage(image);
                index = controller.previousRoom(roomText, index,
                        model, guitarText, bassText, drumText);
//                Log.d("model", model.getGuitarId());
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagesController.nextImage();
                imagesController.setImage(image);
                index = controller.nextRoom(roomText, index, model,
                        guitarText, bassText, drumText);
            }
        });

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ruang.this, Booking.class);
                startActivity(intent);
            }
        });
    }
}