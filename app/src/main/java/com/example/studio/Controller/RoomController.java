package com.example.studio.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studio.Activity.Home;
import com.example.studio.Adapter.CardAdapter;
import com.example.studio.Adapter.CardHolder;
import com.example.studio.Adapter.SpinnerAdapter;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RoomController extends BaseController{
    private ArrayList<RoomModel> roomList;
    private GuitarController guitarController;
    private BassController bassController;
    private DrumController drumController;
    private RoomModel model;
    private RoomImagesController roomImagesController;
    private AppCompatActivity activity;
    private DatabaseReference dbRef;
    private String DB_REF = "ruang";
    private int roomSize;
    public String[] roomArray =  { "ruangA", "ruangB", "ruangC","ruangD",
                        "ruangE", "ruangF", "ruangG", "ruangH"};
    public String[] spinnerArray =  { "Choose your room","ruangA", "ruangB", "ruangC","ruangD",
            "ruangE", "ruangF", "ruangG", "ruangH"};

    public RoomController(){
        dbRef = config.connection(DB_REF);
    }

    public RoomController(RoomModel roomModel){
        dbRef = config.connection(DB_REF);
        this.model = roomModel;
    }

    public void getRoomPrice(String room, TextView priceText){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    if(data.child("room").getValue(String.class).equals(room)){
                        Log.d("price", data.child("price").getValue(String.class));
                        priceText.setText(data.child("price").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });
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

    public void getSelectedRoom(List<BookedModel> bookedModelList, RoomModel selectedRoom, BookingModel bookingModel){
        for(BookedModel data : bookedModelList){
            if(data.getBookedRoom().contains(String.valueOf(selectedRoom))){
                Log.d("Data",data.getBookedRoom());
                bookingModel.setRoom(data.getRoom());
            }
        }
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
                        TextView guitarText, TextView bassText, TextView drumText, TextView priceText, Context context, ImageView image){
        Log.d("index pas masuk", String.valueOf(index));
        roomImagesController = new RoomImagesController();
        Log.d("index next bef", String.valueOf(index));
        index++;
        Log.d("index next after", String.valueOf(index));
        if(index >= roomArray.length){
            index = 0;
        }
        roomImagesController.setImage(context, image, index);
        String roomName = roomArray[index];
        textView.setText(roomName);
        roomModel.setRoom(roomName);
        readRoomDetail(roomModel, guitarText, bassText, drumText, priceText);
        return index;
    }

    public int previousRoom(TextView textView, int index, RoomModel roomModel,
                            TextView guitarText, TextView bassText, TextView drumText, TextView priceText, Context context, ImageView image){
        roomImagesController = new RoomImagesController();
        Log.d("index prev bef", String.valueOf(index));
        index--;
        if(index < 0){
            index = 7;
        }
        roomImagesController.setImage(context, image, index);
        Log.d("index prev after", String.valueOf(index));
        String roomName = roomArray[index];
        textView.setText(roomName);
        roomModel.setRoom(roomName);
        readRoomDetail(roomModel, guitarText, bassText, drumText, priceText);
        return index;
    }

    public void readRoomDetail(RoomModel roomModel, TextView guitarText,
                               TextView bassText, TextView drumText, TextView priceText){
        config.connection(DB_REF).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot roomSnapshot : dataSnapshot.getChildren()){
                    String roomName = roomSnapshot.child("room").getValue(String.class);
                    if(roomName.equalsIgnoreCase(roomModel.getRoom().trim())){
                        model = roomSnapshot.getValue(RoomModel.class);
                        priceText.setText("Price    : " + roomSnapshot.child("price").getValue().toString());
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

    public void getRoomImage(List<HistoryModel> historyModelList, List<BookedModel> bookedModelList,
                             ImageView holder, int position, TextView price){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    HistoryModel result = data.getValue(HistoryModel.class);
                    result.setImageUrl(data.child("imageUrl").getValue(String.class));
                    result.setRoom(data.child("room").getValue(String.class));
                    result.setPrice(data.child("price").getValue(String.class));
                    historyModelList.add(result);
                }
                setCardImage(historyModelList, bookedModelList, holder, position, holder.getContext(), price);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setCardImage(List<HistoryModel> historyModelList, List<BookedModel> bookedModelList,
                             ImageView holder, int position, Context context, TextView price){
        for(int i=0; i<historyModelList.size();i++){
            HistoryModel dataHistory = historyModelList.get(i);
            BookedModel dataBooking = bookedModelList.get(position);
            if(dataBooking.getRoom().equals(dataHistory.getRoom())){
                Log.d("size", String.valueOf(bookedModelList.size()));
                Log.d("size", String.valueOf(historyModelList.size()));
                Glide.with(context).load(historyModelList.get(i).getImageUrl()).into(holder);
                price.setText(historyModelList.get(i).getPrice());
            }
        }
    }
}
