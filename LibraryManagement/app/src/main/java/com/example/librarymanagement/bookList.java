package com.example.librarymanagement;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.librarymanagement.Interface.ItemClickListener;
import com.example.librarymanagement.Model.Book;
import com.example.librarymanagement.ViewHolder.BookViewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class bookList extends AppCompatActivity {



    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference bookList;


//    String menuId="";
    String categoryId="";


    FirebaseRecyclerAdapter<Book, BookViewholder>adapter;


    //search functionality


    FirebaseRecyclerAdapter<Book, BookViewholder>searchAdapter;

    List<String>suggestList=new ArrayList<>();

    MaterialSearchBar materialSearchBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);



        //Firebase

        database=FirebaseDatabase.getInstance();
        bookList=database.getReference("Book");

        recyclerView=(RecyclerView)findViewById(R.id.recycler_book);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        //get intent here

        if(getIntent()!=null)
            categoryId=getIntent().getStringExtra("CategoryId");

        if(!categoryId.isEmpty() && categoryId !=null)
        {
            loadListBook(categoryId);


        }



            // search

        materialSearchBar=(MaterialSearchBar)findViewById(R.id.searchBar);
        materialSearchBar.setHint("Search Books here");
        //materialSearchBar.setSpeechMode(false);
        loadSuggest();

        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                //when user type thier their text we will change thier list




                List<String>suggest=new ArrayList<String>();
                for (String search:suggestList)
                {

                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                    {

                        suggest.add(search);

                    }


                }

                materialSearchBar.setLastSuggestions(suggest);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when seaarch bar is closed

                if(!enabled)
                        recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                //when search finished sshow result

                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    private void startSearch(CharSequence text) {

        searchAdapter = new FirebaseRecyclerAdapter
                <Book, BookViewholder>(Book.class, R.layout.book_item, BookViewholder.class, bookList.orderByChild("name").equalTo(text.toString())) {
            @Override
            protected void populateViewHolder(BookViewholder viewHolder, Book model, int position) {
                viewHolder.book_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.book_image);

                final Book local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(bookList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                        //start new activity

                        Intent bookDetail = new Intent(bookList.this, BookDetail.class);
                        bookDetail.putExtra("BookId", searchAdapter.getRef(position).getKey()); //start new activity
                        startActivity(bookDetail);
                        finish();


                    }
                });


            }
        };

        recyclerView.setAdapter(searchAdapter);

    }

    private void loadSuggest()
    {
        bookList.orderByChild("menuId").equalTo(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Book item=postSnapshot.getValue(Book.class);
                    suggestList.add(item.getName());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadListBook(String categoryId) {

        adapter=new FirebaseRecyclerAdapter<Book, BookViewholder>(Book.class,R.layout.book_item,BookViewholder.class,bookList.orderByChild("menuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(BookViewholder viewHolder, Book model, int position) {

            viewHolder.book_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.book_image);

                final Book local=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(bookList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                        //start new activity

                        Intent bookDetail = new Intent(bookList.this,BookDetail.class);
                        bookDetail.putExtra("BookId",adapter.getRef(position).getKey()); //start new activity
                        startActivity(bookDetail);



                    }
                });


            }
        };



        //set adapter
        //Log.d("TAG",""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);

    }
}
