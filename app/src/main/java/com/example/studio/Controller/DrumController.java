package com.example.studio.Controller;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.studio.Config.config;
import com.example.studio.Model.DrumModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DrumController {
    private ManufacturerController manufacturerController;
    private DrumModel model;
    private DatabaseReference dbRef;
    private String DB_REF = "drum";

    public DrumController(){
        dbRef = config.connection(DB_REF);
    }

    public void getDrumData(String drumId, TextView drumText){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    if(data.child("drumId").getValue(String.class).equals(drumId)){
                        Log.d("drumId", "found");
                        model = data.getValue(DrumModel.class);
                    }
                }
                manufacturerController = new ManufacturerController();
                manufacturerController.getDrumManufacturerData(model, drumText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("get data", "failed");
            }
        });
    }
}
