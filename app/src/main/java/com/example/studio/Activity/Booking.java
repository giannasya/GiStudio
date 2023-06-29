package com.example.studio.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studio.Controller.BookingController;
import com.example.studio.Controller.RoomController;
import com.example.studio.Model.BookingModel;
import com.example.studio.Model.RoomModel;
import com.example.studio.R;

import java.util.ArrayList;

public class Booking extends AppCompatActivity {
    private BookingController bookingController;
    private RoomController roomController;
    private ArrayAdapter<RoomModel> spinnerAdapter;
    private BookingModel bookingModel;
    private Spinner spinner;
    private Context context;
    private TextView datePicker;
    private ArrayList<RoomModel> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);
        roomController = new RoomController();
        bookingController = new BookingController();
        bookingModel = new BookingModel();

        roomList = new ArrayList<>();
        spinner = findViewById(R.id.roomSelector);
        context = this;

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        roomController.setSpinnerAdapter(roomList, spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RoomModel selectedRoom = roomList.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println(R.string.DATA_NOT_FOUND);
            }
        });

        datePicker = findViewById(R.id.datePicker);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingController.setDate(context, datePicker, bookingModel, spinnerAdapter, roomList);
            }
        });
    }
}

