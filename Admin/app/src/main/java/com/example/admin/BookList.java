package com.example.admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.PointerIcon;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.Common.Common;
import com.example.admin.Interface.ItemClickListener;
import com.example.admin.Model.Book;
import com.example.admin.Model.Category;
import com.example.admin.ViewHolder.BookViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.UUID;





public class BookList extends AppCompatActivity
{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout rootLayout;

    FloatingActionButton fab;


    FirebaseDatabase db;
    DatabaseReference bookList;
    FirebaseStorage storage;
    StorageReference storageReference;
    String categoryId="";

    FirebaseRecyclerAdapter<Book, BookViewHolder> adapter;
    //Add new book
    MaterialEditText edtName,edtbookid,edtcopies,edtauhtor,edtauthkey,edtpublisher;
    Button btnSelect,btnUpload;
    Book newBook;
    Uri saveUri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        // Firebase
        db=FirebaseDatabase.getInstance();
        bookList=db.getReference("Book");
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        //init
        recyclerView=(RecyclerView)findViewById(R.id.recycler_book);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        rootLayout=(RelativeLayout)findViewById(R.id.rootLayout);


        fab =(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              showAddBookDialog();


            }
        });
        if(getIntent()!=null)
        {
            categoryId=getIntent().getStringExtra("CategoryId");

        }
        if(!categoryId.isEmpty())
        {
            loadListBook(categoryId);
        }

    }

    private void showAddBookDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BookList.this);
        alertDialog.setTitle("Add new Book ");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater= this.getLayoutInflater();
        View add_menu_layout= inflater.inflate(R.layout.add_new_book_layout,null);


        edtName=add_menu_layout.findViewById(R.id.edtName);
        edtbookid=add_menu_layout.findViewById(R.id.edtbookid);

        edtcopies=add_menu_layout.findViewById(R.id.edtcopies);

        edtauhtor=add_menu_layout.findViewById(R.id.edtauthor);

        edtauthkey=add_menu_layout.findViewById(R.id.edtauthkey);

        edtpublisher=add_menu_layout.findViewById(R.id.publisher);

        btnSelect=add_menu_layout.findViewById(R.id.btnselect);
        btnUpload=add_menu_layout.findViewById(R.id.btnupload);


        //Event for button
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        alertDialog.setView(add_menu_layout);

        // Set button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                // Here just create new category
                if(newBook!=null)
                {
                    bookList.child(edtbookid.getText().toString()).setValue(newBook);
                    Snackbar.make(rootLayout,"New Category"+newBook.getName()+" was Added",Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        alertDialog.show();

    }
    private void uploadImage() {

        if(saveUri != null)
        {
            final ProgressDialog mDialog =new ProgressDialog(this);
            mDialog.setMessage("Uploading....");
            mDialog.show();
            String imageName= UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("images/"+imageName);
            imageFolder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mDialog.dismiss();
                    Toast.makeText(BookList.this,"Uploaded!!",Toast.LENGTH_SHORT).show();
                    imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            // set value for New category if image is uploaded and we get download link
                            newBook = new Book();
                            newBook.setName(edtName.getText().toString());
                            newBook.setBookid(edtbookid.getText().toString());
                            newBook.setCopies(edtcopies.getText().toString());
                            newBook.setAuthor(edtauhtor.getText().toString());
                            newBook.setAuthkey(edtauthkey.getText().toString());
                            newBook.setPublisher(edtpublisher.getText().toString());
                            newBook.setMenuId(categoryId);
                            newBook.setImage(uri.toString());






                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(BookList.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    mDialog.setMessage("Uploaded "+progress+"%");
                }
            });

        }
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pictures"), Common.PICK_IMAGE_REQUEST);
    }

    private void loadListBook(String categoryId) {
        adapter=new FirebaseRecyclerAdapter<Book, BookViewHolder>(Book.class,R.layout.book_item,BookViewHolder.class,bookList.orderByChild("menuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(BookViewHolder viewHolder, Book model, int position) {

                viewHolder.book_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.book_image);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Code Later
                    }
                });

            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Common.PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            saveUri = data.getData();
            btnSelect.setText("Image Selected !");
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle().equals(Common.UPDATE))
        {
            showUpdateBookDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }else
        if(item.getTitle().equals(Common.DELETE))
        {
                deleteBook(adapter.getRef(item.getOrder()).getKey());

        }



        return super.onOptionsItemSelected(item);
    }

    private void deleteBook(String key) {

        bookList.child(key).removeValue();

    }

    private void showUpdateBookDialog(final String key, final Book item) {

        //copy from showAddBookDialog

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BookList.this);
        alertDialog.setTitle("Edit Book ");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater= this.getLayoutInflater();
        View add_menu_layout= inflater.inflate(R.layout.add_new_book_layout,null);

        edtName=add_menu_layout.findViewById(R.id.edtName);
        edtbookid=add_menu_layout.findViewById(R.id.edtbookid);

        edtcopies=add_menu_layout.findViewById(R.id.edtcopies);

        edtauhtor=add_menu_layout.findViewById(R.id.edtauthor);

        edtauthkey=add_menu_layout.findViewById(R.id.edtauthkey);

        edtpublisher=add_menu_layout.findViewById(R.id.publisher);

        btnSelect=add_menu_layout.findViewById(R.id.btnselect);
        btnUpload=add_menu_layout.findViewById(R.id.btnupload);



        //set default value for view
        edtName.setText(item.getName());
        edtbookid.setText(item.getBookid());
        edtcopies.setText(item.getCopies());
        edtauhtor.setText(item.getAuthor());
        edtauthkey.setText(item.getAuthkey());
        edtpublisher.setText(item.getPublisher());




        //Event for button
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(item);
            }
        });

        alertDialog.setView(add_menu_layout);

        // Set button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                // Here just create new category


                    item.setName(edtName.getText().toString());
                    item.setBookid(edtbookid.getText().toString());
                    item.setCopies(edtcopies.getText().toString());
                    item.setAuthor(edtauhtor.getText().toString());
                    item.setAuthkey(edtauthkey.getText().toString());
                    item.setPublisher(edtpublisher.getText().toString());
                    bookList.child(key).setValue(item);

                    Snackbar.make(rootLayout,"Book "+item.getName()+" was Edited",Snackbar.LENGTH_SHORT).show();


            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        alertDialog.show();

    }

    private void changeImage(final Book item) {

        if(saveUri != null)
        {
            final ProgressDialog mDialog =new ProgressDialog(this);
            mDialog.setMessage("Uploading....");
            mDialog.show();
            String imageName= UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("images/"+imageName);
            imageFolder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mDialog.dismiss();
                    Toast.makeText(BookList.this,"Uploaded!!",Toast.LENGTH_SHORT).show();
                    imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            // set value for New category if image is uploaded and we get download link
                            item.setImage(uri.toString());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(BookList.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    mDialog.setMessage("Uploading "+progress+"%");
                }
            });

        }
    }





}
