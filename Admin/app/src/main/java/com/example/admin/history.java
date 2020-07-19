package com.example.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.admin.Model.Request;
import com.example.admin.ViewHolder.RequestViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class history extends AppCompatActivity {

    RecyclerView recyclerView;   //mbloglist
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database; //
    DatabaseReference requests; //mdatabase

    TextView txtprn,txt_bookid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");

        requests.keepSynced(true);
        recyclerView=(RecyclerView)findViewById(R.id.list_book);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        txtprn=(TextView)findViewById(R.id.txtprn);
       // txt_bookid=(TextView)findViewById(R.id.);


    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter



                <Request, RequestViewHolder>firebaseRecyclerAdapter=new
                FirebaseRecyclerAdapter<Request, RequestViewHolder>
                (Request.class,R.layout.history_layout,
                        RequestViewHolder.class,requests) {
            @Override
            protected void populateViewHolder(RequestViewHolder viewHolder, Request model, int position) {

                viewHolder.setprn(model.getPrn());
                viewHolder.set_book_id(model.getIssuedbookid());


            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


}
