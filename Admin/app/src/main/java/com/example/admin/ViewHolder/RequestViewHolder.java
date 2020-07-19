package com.example.admin.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.admin.R;

public class RequestViewHolder extends RecyclerView.ViewHolder
{
    View mview;
    public RequestViewHolder(View itemview)
    {
        super(itemview);
        mview=itemview;

    }

    public void setprn(String Title)
    {

        TextView prn=(TextView)mview.findViewById(R.id.txt_prn_history);
        prn.setText(Title);


    }

    public void set_book_id(String bookid)
    {

        TextView prn=(TextView)mview.findViewById(R.id.txtbook_id_history);
        prn.setText(bookid);


    }



}