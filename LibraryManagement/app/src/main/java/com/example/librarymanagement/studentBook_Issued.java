package com.example.librarymanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class studentBook_Issued extends AppCompatActivity {

    RecyclerView recyclerView;   //mbloglist
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database; //
    DatabaseReference requests; //mdatabase

    TextView txtprn,txt_bookid;

    TextView bkid1,bkid2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_book__issued);

        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");
        requests.keepSynced(true);
        bkid1=(TextView)findViewById(R.id.txtbook_book1);
        bkid2=(TextView)findViewById(R.id.txtbook_book2);


        txtprn=(TextView)findViewById(R.id.edtPhone);



        // txt_bookid=(TextView)findViewById(R.id.);


    }


}
