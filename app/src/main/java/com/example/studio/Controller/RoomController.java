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
    private GuitarController guitarController;
    private BassController bassController;
    private DrumController drumController;
    private RoomModel model;
    private Activity activity;
    private DatabaseReference dbRef;
    private String DB_REF = "ruang";
    private String[] roomArray =  { "ruangA", "ruangB", "ruangC","ruangD",
                        "ruangE", "ruangF", "ruangG", "ruangH"};

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
        }
    }

    public int nextRoom(TextView textView, int index, RoomModel roomModel,
                        TextView guitarText, TextView bassText, TextView drumText){
        Log.d("index pas masuk", String.valueOf(index));
        index++;
        if(index >= roomArray.length){
            index = 0;
        }
        String roomName = roomArray[index];
        textView.setText(roomName);
        roomModel.setRoom(roomName);
        readRoomDetail(roomModel, guitarText, bassText, drumText);
        return index;
    }

    public int previousRoom(TextView textView, int index, RoomModel roomModel,
                            TextView guitarText, TextView bassText, TextView drumText){
        Log.d("index pas masuk", String.valueOf(index));
        index--;
        if(index < 0){
            index = roomArray.length -1;
        }
        String roomName = roomArray[index];
        textView.setText(roomName);
        roomModel.setRoom(roomName);
        readRoomDetail(roomModel, guitarText, bassText, drumText);
        return index;
    }

    public void readRoomDetail(RoomModel roomModel, TextView guitarText,
                               TextView bassText, TextView drumText){
        config.connection(DB_REF).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot roomSnapshot : dataSnapshot.getChildren()){
                    String roomName = roomSnapshot.child("room").getValue(String.class);
                    if(roomName.equalsIgnoreCase(roomModel.getRoom().trim())){
                        setRoomModel(roomSnapshot, roomModel);
                        getGuitarDetails(roomModel, guitarText);
                        getBassDetails(roomModel, bassText);
                        getDrumDetails(roomModel, drumText);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(R.string.DATA_NOT_FOUND);
            }
        });
    }

    private void setRoomModel(DataSnapshot data, RoomModel roomModel){
        roomModel.setBassId(data.child("bassId").getValue().toString());
        roomModel.setGuitarId(data.child("guitarId").getValue().toString());
        roomModel.setDrumId(data.child("bassId").getValue().toString());
        roomModel.setPrice(data.child("price").getValue().toString());
        Log.d("model dalem loop", roomModel.getRoom() + roomModel.getBassId() +
                roomModel.getGuitarId() + roomModel.getDrumId() + roomModel.getPrice());
    }

    private void getGuitarDetails(RoomModel roomModel, TextView guitarText){
        guitarController = new GuitarController();
        guitarController.getGuitarData(roomModel.getGuitarId(), guitarText);
    }

    private void getBassDetails(RoomModel roomModel, TextView bassText){
        bassController = new BassController();
        bassController.getBassData(roomModel.getBassId(), bassText);
    }

    private void getDrumDetails(RoomModel roomModel, TextView drumText){
        drumController = new DrumController();
        drumController.getDrumData(roomModel.getDrumId(), drumText);
    }
}
