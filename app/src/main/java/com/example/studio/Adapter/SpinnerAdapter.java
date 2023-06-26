package com.example.studio.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.studio.Model.RoomModel;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {
    private ArrayList<RoomModel> roomList;
    private LayoutInflater inflater;

    public SpinnerAdapter(Context context, ArrayList<RoomModel> roomList) {
        this.roomList = roomList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return roomList.size();
    }

    @Override
    public Object getItem(int position) {
        return roomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RoomModel roomModel = roomList.get(position);
        holder.textView.setText(roomModel.getRoom());

        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
    }
}

