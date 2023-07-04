package com.example.studio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studio.Activity.Booking;
import com.example.studio.Activity.History;
import com.example.studio.Controller.BookingController;
import com.example.studio.Controller.RoomController;
import com.example.studio.Model.BookedModel;
import com.example.studio.Model.BookingModel;
import com.example.studio.Model.HistoryModel;
import com.example.studio.Model.RoomModel;
import com.example.studio.R;
import com.bumptech.glide.Glide;


import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardHolder> {
    private Context context;
    private List<BookedModel> bookedRoomModelList;
    private List<RoomModel> roomModelList;
    private List<HistoryModel> historyModelList;
    private RoomController roomController;
    private BookingController bookingController;

    public CardAdapter(Context context, List<BookedModel> bookedRoomModelList, List<RoomModel> roomModelList,
                       List<HistoryModel> historyModelList){
        this.context = context;
        this.bookedRoomModelList = bookedRoomModelList;
        this.roomModelList = roomModelList;
        this.historyModelList = historyModelList;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        roomController = new RoomController();
        roomController.getRoomImage(historyModelList, bookedRoomModelList, holder.recImage, position, holder.recPrice);
        holder.recTitle.setText(bookedRoomModelList.get(position).getRoom());
        holder.recDesc.setText(bookedRoomModelList.get(position).getTanggal());

        holder.recEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Booking.class);
                intent.putExtra("myRoom", bookedRoomModelList.get(holder.getAdapterPosition()).getRoom());
                intent.putExtra("myDate", bookedRoomModelList.get(holder.getAdapterPosition()).getTanggal());
                intent.putExtra("username", bookedRoomModelList.get(holder.getAdapterPosition()).getUsername());
                Log.d("username",bookedRoomModelList.get(holder.getAdapterPosition()).getUsername());
                context.startActivity(intent);
            }
        });

        holder.recDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingController = new BookingController();
                Log.d("key", bookedRoomModelList.get(holder.getAdapterPosition()).getBookedRoom());
                bookingController.delete(bookedRoomModelList.get(holder.getAdapterPosition()).getBookedRoom()
                                        , holder.recDelete.getContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookedRoomModelList.size();
    }
}
