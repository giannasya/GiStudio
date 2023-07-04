package com.example.studio.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studio.R;

public class CardHolder extends RecyclerView.ViewHolder {
    ImageView recImage, recEdit, recDelete;
    TextView recTitle, recDesc, recPrice;
    ConstraintLayout recCard;

    public CardHolder(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recDesc = itemView.findViewById(R.id.recDesc);
        recTitle = itemView.findViewById(R.id.recTitle);
        recPrice = itemView.findViewById(R.id.recPrice);
        recEdit = itemView.findViewById(R.id.recEdit);
        recDelete = itemView.findViewById(R.id.recDelete);
    }
}
