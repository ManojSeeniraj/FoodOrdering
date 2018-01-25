package com.example.smanoj.androideatit.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smanoj.androideatit.Interface.ItemClickListener;
import com.example.smanoj.androideatit.R;

/**
 * Created by s.manoj on 09-01-2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txtMenuName;
    public ImageView imageView;


    private ItemClickListener itemClickListener;


    public MenuViewHolder(View itemView) {
        super(itemView);


        txtMenuName=(TextView)itemView.findViewById(R.id.menu_name);
        imageView=(ImageView)itemView.findViewById(R.id.menu_image);

       itemView.setOnClickListener(this);


    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
