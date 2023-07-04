package com.example.studio.Controller;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.studio.Config.config;
import com.example.studio.Model.RoomModel;
import com.example.studio.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomImagesController extends  RoomController{
    private DatabaseReference dbRef;

    private int currentIndex;

    public RoomImagesController() {
        dbRef = config.connection("ruang");
        this.currentIndex = 0;
    }

    public int setImage(Context context,ImageView imageView, int index) {
        currentIndex = index;
        if (currentIndex >= 0 && currentIndex < 8) {
//            imageView.setImageResource(imageArray[currentIndex]);
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<RoomModel> dataImage = new ArrayList<>();
                    for(DataSnapshot data: snapshot.getChildren()){
                        RoomModel result = data.getValue(RoomModel.class);
                        result.setImageUrl(data.child("imageUrl").getValue(String.class));
                        dataImage.add(result);
                    }
                    for(int i=0;i<dataImage.size();i++){
                        Log.d("current index", String.valueOf(currentIndex));
                        Log.d("image index", String.valueOf(i));
                        if(currentIndex == i){
                            Glide.with(context).load(dataImage.get(i).getImageUrl()).into(imageView);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        return index;
    }

    public void nextImage() {
        currentIndex++;
        if (currentIndex >= 8) {
            currentIndex = 0;
        }
    }

    public void previousImage() {
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = 7;
        }
    }

    public void setBookingRoomImage(ArrayList<RoomModel> roomList, Context context, ImageView image, String selectedRoom){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<RoomModel> imageUrl = new ArrayList<>();
                for(DataSnapshot data:snapshot.getChildren()){
                    if(data.exists()){
                        RoomModel result = data.getValue(RoomModel.class);
                        result.setImageUrl(data.child("imageUrl").getValue(String.class));
                        result.setRoom(data.child("room").getValue(String.class));
                        imageUrl.add(result);
                    }
                }
                for(int i=0;i<roomList.size();i++){
                    RoomModel room = roomList.get(i);
                    if(room.getRoom().equals(selectedRoom)){
                        Glide.with(context).load(imageUrl.get(i).getImageUrl()).into(image);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setRoomImage(Context context,ImageView img1, ImageView img2, ImageView img3, ImageView img4, ImageView img5,
                             ImageView img6, ImageView img7, ImageView img8){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<RoomModel> dataImage = new ArrayList<>();
                for(DataSnapshot data: snapshot.getChildren()){
                    RoomModel result = data.getValue(RoomModel.class);
                    result.setImageUrl(data.child("imageUrl").getValue(String.class));
                    dataImage.add(result);
                }
                Log.d("size", String.valueOf(dataImage.size()));
                for(int i=0; i<dataImage.size();i++){
                    switch (i){
                        case 0:
                            Glide.with(context).load(dataImage.get(i).getImageUrl()).into(img1);
                            break;
                        case 1:
                            Glide.with(context).load(dataImage.get(i).getImageUrl()).into(img2);
                            break;
                        case 2:
                            Glide.with(context).load(dataImage.get(i).getImageUrl()).into(img3);
                            break;
                        case 3:
                            Glide.with(context).load(dataImage.get(i).getImageUrl()).into(img4);
                            break;
                        case 4:
                            Glide.with(context).load(dataImage.get(i).getImageUrl()).into(img5);
                            break;
                        case 5:
                            Glide.with(context).load(dataImage.get(i).getImageUrl()).into(img6);
                            break;
                        case 6:
                            Glide.with(context).load(dataImage.get(i).getImageUrl()).into(img7);
                            break;
                        case 7:
                            Glide.with(context).load(dataImage.get(i).getImageUrl()).into(img8);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
