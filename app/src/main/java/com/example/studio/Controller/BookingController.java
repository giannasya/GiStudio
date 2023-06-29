package com.example.studio.Controller;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.studio.Activity.Booking;
import com.example.studio.Config.config;
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
    private String[] arrayRoom;

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
                        List<BookingModel> bookingList = new ArrayList<>();
                        for(DataSnapshot data : snapshot.getChildren()){
                            if(data.child("tanggal").getValue(String.class).trim().equals(date.trim())){
                                BookingModel result = data.getValue(BookingModel.class);
                                result.setBookedRoom(result.getRoom() + " - Booked");
                                bookingList.add(result);
                            }
                        }
                        for(BookingModel model : bookingList){
                                Log.d("Booking Controller", model.getBookedRoom());
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

    public void resetRoom(BookingModel bookingModel, ArrayAdapter<RoomModel> spinnerAdapter, ArrayList<RoomModel> roomList) {
        roomController = new RoomController();
        String[] arrayRoom = roomController.roomArray; // Assuming there's a method to retrieve the room array
        for (int i = 0; i < arrayRoom.length; i++) {
            RoomModel room = roomList.get(i);
            String bookedRoom = bookingModel.getBookedRoom();
            if (bookedRoom != null && bookedRoom.trim().contains(arrayRoom[i])) {
                bookingModel.setBookedRoom(arrayRoom[i]);
                room.setRoom(bookingModel.getBookedRoom()); // Assuming there's a method to set the booked room
            } else {
                room.setRoom(arrayRoom[i]);
            }
        }
        spinnerAdapter.notifyDataSetChanged();
    }


    public void setBookedRoom(BookingModel bookingModel, ArrayAdapter<RoomModel> spinnerAdapter,
                              ArrayList<RoomModel> roomList) {
        for (int i = 0; i < roomList.size(); i++) {
            RoomModel room = roomList.get(i);
            Log.d("roomList berubah", String.valueOf(roomList.get(i)));
            if (bookingModel.getBookedRoom().trim().equals(room.toString())) {
                Log.d("Data Matched", room.toString());
                room.setRoom(bookingModel.getBookedRoom().trim() + " - Booked");
                spinnerAdapter.notifyDataSetChanged();
            }
        }
    }

}
