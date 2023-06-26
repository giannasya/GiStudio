package com.example.studio.Controller;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.studio.Config.config;
import com.example.studio.Model.BassModel;
import com.example.studio.Model.GuitarModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class BassController {
    private ManufacturerController manufacturerController;
    private BassModel model;
    private DatabaseReference dbRef;
    private String DB_REF = "bass";

    public BassController(){
        dbRef = config.connection(DB_REF);
    }

    public void getBassData(String bassId, TextView bassText){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    if(data.child("bassId").getValue(String.class).equals(bassId)){
                        Log.d("bassId", "found");
                        setModel(data);
                        manufacturerController = new ManufacturerController();
                        manufacturerController.getBassManufacturerData(model, bassText);
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
        model = new BassModel();
        model.setBassName(data.child("bassName").getValue(String.class));
        model.setBassId(data.child("bassId").getValue(String.class));
        model.setManufacturerId(data.child("manufacturerId").getValue(String.class));
    }
}
