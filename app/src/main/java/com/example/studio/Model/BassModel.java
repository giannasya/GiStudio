package com.example.studio.Model;

public class BassModel extends BaseModel{
    String bassId;
    String bassName;
    String manufacturerId;

    public BassModel() {

    }
    public BassModel(String bassId, String bassName, String manufacturerId){
        this.bassId = bassId;
        this.bassName = bassName;
        this.manufacturerId = manufacturerId;
    }

    public String getBassId() {
        return bassId;
    }

    public void setBassId(String bassId) {
        this.bassId = bassId;
    }

    public String getBassName() {
        return bassName;
    }

    public void setBassName(String bassName) {
        this.bassName = bassName;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }
}

