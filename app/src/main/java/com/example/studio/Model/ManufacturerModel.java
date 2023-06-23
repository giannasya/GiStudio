package com.example.studio.Model;

public class ManufacturerModel extends BaseModel{
    String manufacturerId;
    String manufacturerName;

    public ManufacturerModel(){
    }

    public ManufacturerModel(String manufacturerId, String manufacturerName){
        this.manufacturerId = manufacturerId;
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
}
