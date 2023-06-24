package com.example.studio.Config;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class config {
    private static String DB_URL = "https://studio-fe83d-default-rtdb.asia-southeast1.firebasedatabase.app/";

//    private static DatabaseReference dbRef;

    public static DatabaseReference connection(String reference){
        return FirebaseDatabase.getInstance(DB_URL).getReference(reference);
    }
}
