package com.example.admin.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.Common.Common;
import com.example.admin.Interface.ItemClickListener;
import com.example.admin.R;

public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener
{

    public TextView book_name;
    public ImageView book_image;

    private ItemClickListener itemClickListener;


    public BookViewHolder(@NonNull View itemView)
    {
        super(itemView);

        book_name=(TextView)itemView.findViewById(R.id.book_name);
        book_image =(ImageView)itemView.findViewById(R.id.book_image);



        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v)
    {

        itemClickListener.onClick(v,getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {

        menu.setHeaderTitle("Select the Action ");
        menu.add(0,0,getAdapterPosition(), Common.UPDATE);
        menu.add(0,0,getAdapterPosition(), Common.DELETE);

    }
}

