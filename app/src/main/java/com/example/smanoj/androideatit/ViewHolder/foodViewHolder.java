package com.example.smanoj.androideatit.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smanoj.androideatit.Interface.ItemClickListener;
import com.example.smanoj.androideatit.R;

/**
 * Created by s.manoj on 10-01-2018.
 */

public class foodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView food_name;
    public ImageView food_image;


    private ItemClickListener itemClickListener;


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public foodViewHolder(View itemView) {
        super(itemView);

        food_name=(TextView)itemView.findViewById(R.id.food_name);
        food_image=(ImageView)itemView.findViewById(R.id.food_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
