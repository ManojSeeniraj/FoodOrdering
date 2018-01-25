package com.example.smanoj.androideatit.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.smanoj.androideatit.Interface.ItemClickListener;
import com.example.smanoj.androideatit.R;

/**
 * Created by s.manoj on 22-01-2018.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView txtOrderId,txtOrderStatus,txtOrderPhone,txtOrderAddress;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);


        txtOrderAddress=(TextView)itemView.findViewById(R.id.order_address);
        txtOrderId=(TextView)itemView.findViewById(R.id.order_id);
        txtOrderStatus=(TextView)itemView.findViewById(R.id.order_status);
        txtOrderPhone=(TextView)itemView.findViewById(R.id.order_phone);



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
