package com.example.studio.Activity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studio.Controller.BookingController;
import com.example.studio.Controller.OnBackPressedController;
import com.example.studio.Controller.RoomController;
import com.example.studio.Controller.RoomImagesController;
import com.example.studio.Model.BookedModel;
import com.example.studio.Model.BookingModel;
import com.example.studio.Model.RoomModel;
import com.example.studio.R;

import java.util.ArrayList;
import java.util.List;

public class Booking extends AppCompatActivity {
    private BookingController bookingController;
    private RoomController roomController;
    private ArrayAdapter<RoomModel> spinnerAdapter;
    private BookingModel bookingModel;
    private RoomImagesController roomImagesController;
    private Spinner spinner;
    private OnBackPressedController onBackPressedController;
    private Context context;
    private ImageView image;
    private TextView datePicker, tanggal;
    private String username, room,myDate;
    private ArrayList<RoomModel> roomList;
    private Button cancel, book;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);

        username = getIntent().getStringExtra("username");

        cancel = findViewById(R.id.cancel);
        book = findViewById(R.id.book);
        image = findViewById(R.id.bookingImage);

        roomController = new RoomController();
        roomImagesController = new RoomImagesController();
        bookingController = new BookingController();
        bookingModel = new BookingModel();
        onBackPressedController = new OnBackPressedController();

        roomList = new ArrayList<>();
        spinner = findViewById(R.id.roomSelector);
        tanggal = findViewById(R.id.tanggalPeminjaman);
        datePicker = findViewById(R.id.datePicker);

        room = getIntent().getStringExtra("myRoom");
        myDate = getIntent().getStringExtra("myDate");
        context = this;

        spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, roomList);

        bookingController.setTanggalText(room, tanggal, myDate, datePicker);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

//        roomController.setSpinnerAdapter(roomList, spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RoomModel selectedRoom = roomList.get(position);
                bookingModel.setRoom(String.valueOf(selectedRoom));
                roomImagesController.setBookingRoomImage(roomList, context, image, String.valueOf(selectedRoom));
//                roomController.getSelectedRoom(bookedModelList, selectedRoom, bookingModel);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println(R.string.DATA_NOT_FOUND);
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingController.setDate(context, datePicker, spinnerAdapter, roomList);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingController.cancel(context, myDate,username);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = datePicker.getText().toString().trim();
                if(myDate!=null){
                    bookingController.submit(date, bookingModel.getRoom(), username, context, myDate, room);
                }else{
                    bookingController.submit(date, bookingModel.getRoom(), username, context);
                }
            }
        });
    }
    public void onBackPressed() {
        if(myDate != null){
            onBackPressedController.toHistory(context, username);
        }else{
            onBackPressedController.toRuang(context, username);
        }
    }
}

