package com.example.studio.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.studio.Activity.Booking;
import com.example.studio.Activity.History;
import com.example.studio.Activity.Home;
import com.example.studio.Activity.Ruang;

public class OnBackPressedController {
    public void toHome(Context context, String username){
        Intent intent = new Intent(context, Home.class);
        intent.putExtra("username", username);
        context.startActivity(intent);
    }

    public void toRuang(Context context, String username){
        Intent intent = new Intent(context, Ruang.class);
        intent.putExtra("username", username);
        context.startActivity(intent);
    }

    public void toHistory(Context context, String username){
        Intent intent = new Intent(context, History.class);
        intent.putExtra("username", username);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}
