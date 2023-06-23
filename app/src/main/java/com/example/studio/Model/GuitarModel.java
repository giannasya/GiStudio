package com.example.studio.Model;

public class GuitarModel extends  BaseModel{
    String guitarId;
    String guitarName;
    String manufacturerId;

    public GuitarModel(){

    }
    public GuitarModel(String guitarId, String guitarName, String manufacturerId){
        this.guitarId = guitarId;
        this.guitarName = guitarName;
        this.manufacturerId = manufacturerId;
    }

    public String getGuitarId() {
        return guitarId;
    }

    public void setGuitarId(String guitarId) {
        this.guitarId = guitarId;
    }

    public String getGuitarName() {
        return guitarName;
    }

    public void setGuitarName(String guitarName) {
        this.guitarName = guitarName;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }
}

