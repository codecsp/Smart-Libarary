package com.example.admin.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.admin.Interface.ItemClickListener;
import com.example.admin.R;



public class orderViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    public TextView txtOrderId,txtOrderStatus,txtOrderPhone,
            getTxtOrderSuggestion;
    private ItemClickListener itemClickListener;


    public orderViewHolder(@NonNull View itemView) {
        super(itemView);


       // getTxtOrderSuggestion=(TextView)itemView.findViewById(R.id.order_suggestion);
        txtOrderId=(TextView)itemView.findViewById(R.id.txtbookid);
        txtOrderPhone=(TextView)itemView.findViewById(R.id.txtprn);
       // txtOrderStatus=(TextView)itemView.findViewById(R.id.order_status);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        //itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
