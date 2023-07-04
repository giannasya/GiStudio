package com.example.studio.Controller;

import static java.lang.System.in;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.studio.Activity.Booking;
import com.example.studio.Activity.Confirm;
import com.example.studio.Activity.History;
import com.example.studio.Activity.Home;
import com.example.studio.Adapter.CardAdapter;
import com.example.studio.Config.config;
import com.example.studio.Model.BookedModel;
import com.example.studio.Model.BookingModel;
import com.example.studio.Model.HistoryModel;
import com.example.studio.Model.RoomModel;
import com.example.studio.R;
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

    public void setDate(Context context, TextView datePicker, ArrayAdapter<RoomModel> spinnerAdapter, ArrayList<RoomModel> roomList){
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
                roomController = new RoomController();
                roomController.setSpinnerAdapter(roomList, spinnerAdapter);
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

//    public void chooseAdapter(ArrayAdapter<String> roomAdapter, ArrayAdapter<RoomModel> spinnerAdapter, Spinner spinner, String room){
//        try{
//            if(room == null){
//                spinner.setAdapter(roomAdapter);
//            }else{
//                roomAdapter.clear();
//                spinner.setAdapter(spinnerAdapter);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void setTanggalText(String room, TextView tanggal, String myDate, TextView datePicker){
        try{
            Log.d("room", room);
            if(!room.equals(null) && !myDate.equals(null)){
                tanggal.setText("Ganti Tanggal Peminjaman");
                datePicker.setText(myDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancel(Context context, String date, String username){
        try{
            Log.d("date", String.valueOf(context));
            if(date == null || date.isEmpty()){
                backHome(context, username);
                Log.d("username", username);
            }else{
                backHistory(context,username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backHistory(Context context, String username){
        Intent intent = new Intent(context, History.class);
        intent.putExtra("username", username);
        intent.removeExtra("myDate");
        context.startActivity(intent);
    }

    public void backHome(Context context, String username){
        Intent intent = new Intent(context, Home.class);
        intent.putExtra("username", username);
        context.startActivity(intent);
    }

    public void backBooking(Context context, String username){
        Intent intent = new Intent(context, Booking.class);
        intent.putExtra("username", username);
        context.startActivity(intent);
    }

    public void submit(String date, String room, String username, Context context){
        if(date == null || room == null || room.contains("Booked")){
            Toast.makeText(context, "Please Input the data Correctly", Toast.LENGTH_SHORT).show();
        }else{
            model = new BookingModel();
            model.setRoom(room);
            model.setTanggal(date);
            model.setUsername(username);
            Intent intent = new Intent(context, Confirm.class);
            intent.putExtra("bookingModel", model);
            context.startActivity(intent);
        }
    }

    public void pushToDatabase(String room, String date, String username, Context context){
        model = new BookingModel();
        String id = dbRef.push().getKey();
        model.setTanggal(date);
        model.setRoom(room);
        model.setUsername(username);
        dbRef.child(id).setValue(model);
        Intent intent = new Intent(context, Home.class);
        Toast.makeText(context, "Room Booked", Toast.LENGTH_SHORT).show();
        intent.putExtra("username", username);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public void setCardAdapter(String username, List<BookedModel> bookedModelList, CardAdapter cardAdapter){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookedModelList.clear();
                for(DataSnapshot data: snapshot.getChildren()){
                    if(data.child("username").getValue(String.class).equals(username)){
                        BookedModel result = data.getValue(BookedModel.class);

                        result.setBookedRoom(data.getKey());
                        result.setRoom(data.child("room").getValue(String.class));
                        result.setUsername(data.child("username").getValue(String.class));
                        result.setTanggal(data.child("tanggal").getValue(String.class));

                        bookedModelList.add(result);
                    }
                }
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void delete(String key, Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Delete History Data");
        alertDialogBuilder
                .setMessage("Are you sure want to delete this data?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yessir",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dbRef.child(key).removeValue();
                        Toast.makeText(context, "Data deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Aint no way dude",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
