package com.example.librarymanagement;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.security.Principal;

public class studentProfile extends AppCompatActivity {

    ImageView imageView;
    Button button;
    private static final int PICKIMAGE=100;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        imageView=(ImageView)findViewById(R.id.profile);
        button=(Button)findViewById(R.id.btn_profile);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });

    }

    private void opengallery() {

    Intent gallery =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
    startActivityForResult(gallery,PICKIMAGE);


    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {


        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK && requestCode==PICKIMAGE){

            imageUri=data.getData();
            imageView.setImageURI(imageUri);


        }
    }
}


