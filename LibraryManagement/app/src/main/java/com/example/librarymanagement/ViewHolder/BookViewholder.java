package com.example.librarymanagement.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.librarymanagement.Interface.ItemClickListener;
import com.example.librarymanagement.R;

public class BookViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView book_name;
    public ImageView book_image;


    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public BookViewholder(@NonNull View itemView) {
        super(itemView);

        book_name=(TextView)itemView.findViewById(R.id.book_name);
        book_image =(ImageView)itemView.findViewById(R.id.book_image);


        itemView.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);


    }
}
