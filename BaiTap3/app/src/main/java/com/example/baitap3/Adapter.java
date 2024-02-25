package com.example.baitap3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private static final int REQUEST_CODE_PICK_DIRECTORY = 123;
    Context context;
    ArrayList music;

    public Adapter(Context context, ArrayList music){
        this.context = context;
        this.music = music;

    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.txtTitle.setText(music.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return music.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView txtTitle;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            txtTitle = itemView.findViewById(R.id.titleMusic);
            button = itemView.findViewById(R.id.button);

        }
    }
}
