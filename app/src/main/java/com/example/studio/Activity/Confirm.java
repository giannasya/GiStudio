package com.example.studio.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.studio.Controller.BookingController;
import com.example.studio.Controller.RoomController;
import com.example.studio.Model.BookingModel;
import com.example.studio.R;

public class Confirm extends AppCompatActivity {
    private BookingController controller;
    private RoomController roomController;
    private BookingModel model;
    private EditText roomText, dateText, priceText;
    private Button cancel, book;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);
        controller = new BookingController();
        roomController = new RoomController();
        model = (BookingModel) getIntent().getSerializableExtra("bookingModel");
        Log.d("model confirm", model.getUsername());

        roomText = findViewById(R.id.room);
        dateText = findViewById(R.id.date);
        priceText = findViewById(R.id.price);

        roomController.getRoomPrice(model.getRoom(), priceText);
        roomText.setText(model.getRoom());
        dateText.setText(model.getTanggal());

        priceText.setFocusable(false);
        roomText.setFocusable(false);
        dateText.setFocusable(false);

        cancel = findViewById(R.id.cancel);
        book = findViewById(R.id.book);

        context = this;

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.backBooking(context, model.getUsername());
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.pushToDatabase(model.getRoom(), model.getTanggal(), model.getUsername(), context);
            }
        });
    }
}