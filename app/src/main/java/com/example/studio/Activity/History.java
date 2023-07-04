package com.example.studio.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.studio.Adapter.CardAdapter;
import com.example.studio.Controller.BookingController;
import com.example.studio.Controller.OnBackPressedController;
import com.example.studio.Model.BookedModel;
import com.example.studio.Model.BookingModel;
import com.example.studio.Model.HistoryModel;
import com.example.studio.Model.RoomModel;
import com.example.studio.R;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    private TextView room, date;
    private CardAdapter cardAdapter;
    private RecyclerView recyclerView;
    private List<BookedModel> bookedModelList;
    private List<RoomModel> roomModelList;
    private List<HistoryModel> historyModelList;
    private BookingController controller;
    private Context context;
    private OnBackPressedController onBackPressedController;
    private String username;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        context = this;
        controller = new BookingController();
        onBackPressedController = new OnBackPressedController();

        username = getIntent().getStringExtra("username");

        gridLayoutManager = new GridLayoutManager(context, 1);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(gridLayoutManager);

        historyModelList = new ArrayList<>();
        bookedModelList = new ArrayList<>();
        roomModelList = new ArrayList<>();
        cardAdapter = new CardAdapter(context, bookedModelList, roomModelList, historyModelList);

        recyclerView.setAdapter((RecyclerView.Adapter) cardAdapter);

        controller.setCardAdapter(username, bookedModelList, cardAdapter);
    }
    public void onBackPressed() {
        onBackPressedController.toHome(context, username);
    }
}