package com.example.studio.Model;

public class RoomModel extends BaseModel{

    private String room;
    private String bassId;
    private String guitarId;
    private String drumId;
    private String price;
    private String imageUrl;

    public RoomModel(){
    }

    public RoomModel(String room, String bassId, String guitarId, String drumId, String price, String imageUrl){
        this.room = room;
        this.bassId = bassId;
        this.guitarId = guitarId;
        this.drumId = drumId;
        this.price = price;
        this.imageUrl = imageUrl;
    };

    public String toString(){
        return room;
    }
    public String getRoom(){
        return room;
    }

    public void setRoom(String room){
        this.room = room;
    }

    public String getBassId(){
        return bassId;
    }

    public void setBassId(String bassId){
        this.bassId = bassId;
    }

    public String getDrumId() {
        return drumId;
    }

    public void setDrumId(String drumId) {
        this.drumId = drumId;
    }

    public String getGuitarId() {
        return guitarId;
    }

    public void setGuitarId(String guitarId) {
        this.guitarId = guitarId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
