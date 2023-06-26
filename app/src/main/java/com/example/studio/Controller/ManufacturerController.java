package com.example.studio.Controller;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.studio.Config.config;
import com.example.studio.Model.BassModel;
import com.example.studio.Model.DrumModel;
import com.example.studio.Model.GuitarModel;
import com.example.studio.Model.ManufacturerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ManufacturerController {
    private ManufacturerModel model;
    private DatabaseReference dbRef;
    private String DB_REF = "manufacturer";

    public ManufacturerController(){
        dbRef = config.connection(DB_REF);
    }

    public void getGuitarManufacturerData(GuitarModel guitarModel, TextView guitarText){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    if(data.child("manufacturerId").getValue(String.class)
                            .equals(guitarModel.getManufacturerId())){
                        Log.d("manufacturerId", "found");
                        setGuitarManufacturerModel(data);
                        setGuitarText(guitarModel, guitarText);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Manufacturer Error", "unknown");
            }
        });
    }

    public void setGuitarManufacturerModel(DataSnapshot data){
        model = new ManufacturerModel();
        model.setManufacturerName(data.child("manufacturerName").getValue(String.class));
        model.setManufacturerId(data.child("manufacturerId").getValue(String.class));
    }

    public void setGuitarText(GuitarModel guitarModel, TextView guitarText){
        guitarText.setText("Guitar  : " + model.getManufacturerName()
                            + " " + guitarModel.getGuitarName());
    }

    //bass

    public void getBassManufacturerData(BassModel bassModel, TextView bassText){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    if(data.child("manufacturerId").getValue(String.class)
                            .equals(bassModel.getManufacturerId())){
                        Log.d("manufacturerId", "found");
                        setBassManufacturerModel(data);
                        setBassText(bassModel, bassText);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Manufacturer Error", "unknown");
            }
        });
    }

    public void setBassManufacturerModel(DataSnapshot data){
        model = new ManufacturerModel();
        model.setManufacturerName(data.child("manufacturerName").getValue(String.class));
        model.setManufacturerId(data.child("manufacturerId").getValue(String.class));
    }

    public void setBassText(BassModel bassModel, TextView bassText){
        bassText.setText("Bass    : " + model.getManufacturerName()
                            + " " + bassModel.getBassName());
    }

    //drum

    public void getDrumManufacturerData(DrumModel drumModel, TextView drumText){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    if(data.child("manufacturerId").getValue(String.class)
                            .equals(drumModel.getManufacturerId())){
                        Log.d("manufacturerId", "found");
                        setDrumManufacturerModel(data);
                        setDrumText(drumModel, drumText);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Manufacturer Error", "unknown");
            }
        });
    }

    public void setDrumManufacturerModel(DataSnapshot data){
        model = new ManufacturerModel();
        model.setManufacturerName(data.child("manufacturerName").getValue(String.class));
        model.setManufacturerId(data.child("manufacturerId").getValue(String.class));
    }

    public void setDrumText(DrumModel drumModel, TextView drumText){
        drumText.setText("Drum   : " + model.getManufacturerName()
                            + " " + drumModel.getDrumName());
    }
}
