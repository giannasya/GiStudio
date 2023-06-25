package com.example.studio.Controller;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.studio.Config.config;
import com.example.studio.Model.RoomModel;
import com.example.studio.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class RoomController extends BaseController{
    private RoomModel model;
    private Activity activity;
    private DatabaseReference dbRef;
    private String DB_REF = "room";
    private String[] roomArray =  { "roomA", "roomB", "roomC","roomD", "roomE", "roomF", "roomG", "roomH"};

    public RoomController(){
        dbRef = config.connection(DB_REF);
    }
    public RoomController(Activity activity){
        dbRef = config.connection(DB_REF);
        this.activity = activity;
    }
    public RoomController(RoomModel roomModel){
        dbRef = config.connection(DB_REF);
        this.model = roomModel;
    }
    public RoomController(String[] roomArray){
        dbRef = config.connection(DB_REF);
        this.roomArray = roomArray;
    }

    public void setRoomText(TextView textView, int index, RoomModel roomModel){
        if (index >= 0 && index < roomArray.length) {
            String roomName = roomArray[index];
            textView.setText(roomName);
            roomModel.setRoom(roomName);
            Log.d("room nama", roomModel.getRoom());
        }
    }

    public int nextRoom(TextView textView, int index, RoomModel roomModel){
        Log.d("index pas masuk", String.valueOf(index));
        index++;
        if(index >= roomArray.length){
            index = 0;
        }
        String roomName = roomArray[index];
        textView.setText(roomName);
        Log.d("Room name --", roomName);
        Log.d("nilai index sekarang ++", String.valueOf(index));
        roomModel.setRoom(roomName);
        return index;
    }

    public int previousRoom(TextView textView, int index, RoomModel roomModel){
        Log.d("index pas masuk", String.valueOf(index));
        index--;
        if(index < 0){
            index = roomArray.length -1;
        }
        String roomName = roomArray[index];
        Log.d("Room name --", roomName);
        Log.d("nilai index sekarang --", String.valueOf(index));
        textView.setText(roomName);
        roomModel.setRoom(roomName);
        return index;
    }

    public void readRoomDetail(RoomModel roomModel){
        config.connection(DB_REF).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot roomSnapshot : dataSnapshot.getChildren()){
                    if(roomSnapshot.getValue(String.class).equals(roomModel.getRoom())){
                        roomModel.setBassId(roomSnapshot.child("bassId").getValue(String.class));
                        roomModel.setDrumId(roomSnapshot.child("drumId").getValue(String.class));
                        roomModel.setGuitarId(roomSnapshot.child("guitarId").getValue(String.class));
                        roomModel.setPrice(roomSnapshot.child("price").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(R.string.DATA_NOT_FOUND);
            }
        });
    }
}
