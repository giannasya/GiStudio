package com.example.studio.Controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.studio.Adapter.SpinnerAdapter;
import com.example.studio.Config.config;
import com.example.studio.Model.BookedModel;
import com.example.studio.Model.BookingModel;
import com.example.studio.Model.RoomModel;
import com.example.studio.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomController extends BaseController{
    private ArrayList<RoomModel> roomList;
    private GuitarController guitarController;
    private BassController bassController;
    private DrumController drumController;
    private RoomModel model;
    private Activity activity;
    private DatabaseReference dbRef;
    private String DB_REF = "ruang";
    private int roomSize;
    public String[] roomArray =  { "ruangA", "ruangB", "ruangC","ruangD",
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

    public String getRoomPrice(String room, String price){
        final String[] tempPrice = new String[1];
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    if(data.child("room").getValue(String.class)
                            .equals(room)){
                        tempPrice[0] = data.child("price").getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });
        price = tempPrice[0];
        return price;
    }

    public void setSpinnerAdapter(ArrayList<RoomModel> roomList, ArrayAdapter<RoomModel> spinnerAdapter) {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    RoomModel model = new RoomModel();
                    model.setRoom(data.child("room").getValue().toString());
                    roomList.add(model);
                }
                spinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(R.string.DATA_NOT_FOUND);
            }
        });
    }

    public void updateSelectedRoom(ArrayAdapter<RoomModel> spinnerAdapter, List<BookedModel> bookedModels) {

        for(int i=0; i<roomArray.length; i++){
            RoomModel room = spinnerAdapter.getItem(i);
            room.setRoom(roomArray[i]);
            for(BookedModel bookedModel: bookedModels){
                if(bookedModel.getBookedRoom().contains(roomArray[i])){
                    if(!room.getRoom().contains("Booked")){
                        room.setRoom(bookedModel.getBookedRoom());
                        break;
                    }
                }
                Log.d("data selesai if", room.getRoom());
            }
        }
        spinnerAdapter.notifyDataSetChanged();
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
                        model = roomSnapshot.getValue(RoomModel.class);
                    }
                }
                getGuitarDetails(model, guitarText);
                getBassDetails(model, bassText);
                getDrumDetails(model, drumText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(R.string.DATA_NOT_FOUND);
            }
        });
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
