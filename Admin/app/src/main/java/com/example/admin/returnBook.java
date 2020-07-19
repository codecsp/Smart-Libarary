package com.example.admin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.Model.Book;
import com.example.admin.Model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import static com.example.admin.BookIssued.cnt;
import static java.lang.Integer.parseInt;

public class returnBook extends AppCompatActivity {

    EditText prn;
    EditText issue_bookid;
    Button issue_book;
    FirebaseDatabase database;
    DatabaseReference requests,book_node;
    String pcount;
    int cnt;
    //static int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_book);



        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        book_node=FirebaseDatabase.getInstance().getReference("Book");
      //  count=1;

        prn=(EditText)findViewById(R.id.txtprn);
        issue_bookid=(EditText)findViewById(R.id.txtbookid);
        issue_book=(Button)findViewById(R.id.btn_return);


        issue_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //deleteBook(adapter.getOrder()).getKey());
                requests.child(prn.getText().toString()+issue_bookid.getText().toString()).removeValue();

                book_node.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(issue_bookid.getText().toString()).exists())
                        {

                            //User user = dataSnapshot.child(localPhone).getValue(User.class);
                            Book book=dataSnapshot.child(issue_bookid.getText().toString()).getValue(Book.class);
                            book.setBookid(issue_bookid.getText().toString());
                            pcount=book.getCopies();

                            //User user = dataSnapshot.child(localPhone).getValue(User.class);
                           // Book book=dataSnapshot.child(bid).getValue(Book.class);

                            //pcount=book.getCopies();
                            //pcount=null;

                            cnt = Integer.parseInt(pcount);
                            ++cnt;



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

              /*  count= parseInt(pcount);
                //count=count+1;*/

                pcount=String.valueOf(cnt);

                book_node.child(issue_bookid.getText().toString()).child("copies").setValue(pcount);
                Toast.makeText(returnBook.this, "Book Returned", Toast.LENGTH_SHORT).show();


            }
        });

    }
}
