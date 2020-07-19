package com.example.admin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.Common.Common;
import com.example.admin.Model.Book;
import com.example.admin.Model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Integer.*;
import static java.lang.Integer.valueOf;

public class BookIssued extends AppCompatActivity {

    EditText prn;
    EditText issue_bookid;
    Button issue_book;
    FirebaseDatabase database;
    DatabaseReference requests,book_node;
    static String pcount;
    static int cnt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_issued);

        database = FirebaseDatabase.getInstance();

        book_node=database.getReference("Book");

        requests = database.getReference("Requests");

        prn=(EditText)findViewById(R.id.txtprn);
        issue_bookid=(EditText)findViewById(R.id.txtbookid);
        issue_book=(Button)findViewById(R.id.btn_issue);
        //String s = ;
        //String count;
        //count = String.valueOf(book_node.child(issue_bookid.getText().toString()).child("copies"));
        //pcount=Integer.parseInt(count);
        //pcount=pcount-1;
        issue_book.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Request request=new Request(prn.getText().toString(),issue_bookid.getText().toString());

                //submit to firebase

                book_node.addValueEventListener(new ValueEventListener()
                {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                       // Toast.makeText(BookIssued.this, "in datasnapshot", Toast.LENGTH_LONG ).show();
                        final String bid=issue_bookid.getText().toString();


                        if(dataSnapshot.child(bid).exists())
                        {

                            //User user = dataSnapshot.child(localPhone).getValue(User.class);
                            Book book=dataSnapshot.child(bid).getValue(Book.class);

                            pcount=book.getCopies();
                            //pcount=null;

                            cnt    = Integer.parseInt(pcount);
                            cnt--;

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //pcount="15";
                requests.child(prn.getText().toString()+issue_bookid.getText().toString()).setValue(request);

                book_node.child(issue_bookid.getText().toString()).child("copies").setValue(Integer.toString(cnt));
                   // count++;
                Toast.makeText(BookIssued.this, "Book Issued", Toast.LENGTH_SHORT).show();

                //}
                /*else
                {

                    Toast.makeText(BookIssued.this, "Cannot issue more than two books", Toast.LENGTH_SHORT).show();

                }*/

            }
        });
    }
}
