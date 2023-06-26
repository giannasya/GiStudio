package com.example.studio.Controller;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.studio.Config.config;
import com.example.studio.Model.GuitarModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class GuitarController {
    private ManufacturerController manufacturerController;
    private GuitarModel model;
    private DatabaseReference dbRef;
    private String DB_REF = "guitar";

    public GuitarController(){
        dbRef = config.connection(DB_REF);
    }

    public void getGuitarData(String guitarId, TextView guitarText){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    if(data.child("guitarId").getValue(String.class).equals(guitarId)){
                        Log.d("guitarId", "found");
                        setModel(data);
                        manufacturerController = new ManufacturerController();
                        manufacturerController.getGuitarManufacturerData(model, guitarText);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("get data", "failed");
            }
        });
    }

    public void setModel(DataSnapshot data){
        model = new GuitarModel();
        model.setGuitarName(data.child("guitarName").getValue(String.class));
        model.setGuitarId(data.child("guitarId").getValue(String.class));
        model.setManufacturerId(data.child("manufacturerId").getValue(String.class));
    }
}
