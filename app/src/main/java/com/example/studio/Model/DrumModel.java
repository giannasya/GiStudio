package com.example.studio.Model;

public class DrumModel extends BaseModel{
    String drumId;
    String drumName;
    String manufacturerId;

    public DrumModel(){}
    public DrumModel(String drumId, String drumName, String manufacturerId){
        this.drumId = drumId;
        this.drumName = drumName;
        this.manufacturerId = manufacturerId;
    }

    public String getDrumId() {
        return drumId;
    }

    public void setDrumId(String drumId) {
        this.drumId = drumId;
    }

    public String getDrumName() {
        return drumName;
    }

    public void setDrumName(String drumName) {
        this.drumName = drumName;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }
}
