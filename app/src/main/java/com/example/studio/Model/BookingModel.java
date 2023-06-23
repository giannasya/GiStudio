package com.example.studio.Model;

public class BookingModel extends BaseModel{

    String room;
    String tanggal;
    String username;

    public BookingModel(){

    }
    public BookingModel(String room,String tanggal, String username){
        this.room = room;
        this.tanggal = tanggal;
        this.username = username;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTanggal(){
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
