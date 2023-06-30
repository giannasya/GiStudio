package com.example.studio.Controller;

import static java.lang.System.in;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.studio.Activity.Booking;
import com.example.studio.Activity.Home;
import com.example.studio.Config.config;
import com.example.studio.Model.BookedModel;
import com.example.studio.Model.BookingModel;
import com.example.studio.Model.RoomModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookingController {
    private BookingModel model;
    private RoomController roomController;
    private DatabaseReference dbRef;
    private String DB_REF = "booking";
    private int day, year, month;
    private String date;

    public BookingController(){
        dbRef = config.connection(DB_REF);
    }

    public void setDate(Context context, TextView datePicker, BookingModel bookingModel,
                        ArrayAdapter<RoomModel> spinnerAdapter, ArrayList<RoomModel> roomList){
        final Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                android.R.style.Theme_DeviceDefault_Dialog,
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                if(day<10){
                    datePicker.setText(year+"-"+0+(month+1)+"-"+0+day);
                }else{
                    datePicker.setText(year+"-"+0+(month+1)+"-"+day);
                }
                date = datePicker.getText().toString().trim();
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<BookedModel> bookingList = new ArrayList<>();
                        for(DataSnapshot data : snapshot.getChildren()){
                            if(data.child("tanggal").getValue(String.class).trim().equals(date.trim())){
                                BookedModel result = data.getValue(BookedModel.class);
                                result.setBookedRoom(result.getRoom() + " - Booked");
                                bookingList.add(result);
                            }
                        }
                        roomController = new RoomController();
                        roomController.updateSelectedRoom(spinnerAdapter, bookingList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }, year,month,day);
        datePickerDialog.show();
    }

    public void backHome(Context context, String username){
        Intent intent = new Intent(context, Home.class);
        intent.putExtra("username", username);
        context.startActivity(intent);
    }

    public void submit(String date, String room, String username, Context context){
        if(date.equals(null) || room.equals(null) || room.contains("Booked")){
            Toast.makeText(context, "Please Input the data Correctly", Toast.LENGTH_SHORT).show();
        }else{
//            Intent intent = new Intent(context, confirmation.class);
//            intent.putExtra("bookingRoom", room);
//            intent.putExtra("dateRoom", date);
//            intent.putExtra("username", username);
//            context.startActivity(intent);
        }
    }

    public void pushToDatabase(String room, String date, String username){
        model.setTanggal(date);
        model.setRoom(room);
        model.setUsername(username);
        dbRef.setValue(model);
    }
}
