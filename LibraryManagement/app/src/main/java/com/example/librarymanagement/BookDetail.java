package com.example.librarymanagement;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.librarymanagement.Model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BookDetail extends AppCompatActivity {

    TextView bok_name,author_name,bok_id,bok_publish,book_copies,book_authkey;
    ImageView book_image;


    CollapsingToolbarLayout collapsingToolbarLayout;

    String bookId="";
    FirebaseDatabase database;
    DatabaseReference book;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);


        //firebase

        database=FirebaseDatabase.getInstance();
        book=database.getReference("Book");
        //init view


        bok_id=(TextView)findViewById(R.id.book_id);
        bok_name=(TextView)findViewById(R.id.book_name);
        bok_publish=(TextView)findViewById(R.id.book_publi);
        book_copies=(TextView)findViewById(R.id.book_copies);
        book_authkey=(TextView)findViewById(R.id.book_auth_key);
        author_name=(TextView)findViewById(R.id.author_name);

        book_image =(ImageView)findViewById(R.id.img_book);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collaping);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        //get food id from intent

        if(getIntent()!=null)
        {
            bookId=getIntent().getStringExtra("BookId");

        }

        if(!bookId.isEmpty())
        {
            getDetailBook(bookId);


        }





    }

    private void getDetailBook(String bookId) {

    book.child(bookId).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Book book = dataSnapshot.getValue(Book.class);

            //set image
            Picasso.with(getBaseContext()).load(book.getImage()).into(book_image);
            collapsingToolbarLayout.setTitle(book.getName());
            author_name.setText("By : "+book.getAuthor());
            bok_name.setText("Book Name   : "+book.getName());
            bok_id.setText("BOOK ID      : "+book.getBookid());
            bok_publish.setText("Publication : "+book.getPublisher());
            book_authkey.setText("Author Key  : "+book.getAuthkey());
            book_copies.setText("Copies Available : "+book.getCopies());










        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    }
}
