package com.example.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.Common.Common;
import com.example.admin.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import info.hoang8f.widget.FButton;

public class signin extends AppCompatActivity {

    EditText edtPassword,edtName;
    FirebaseDatabase db;
    DatabaseReference users;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtName = (MaterialEditText)findViewById(R.id.edtPhone);
        btnSignIn = (Button)findViewById(R.id.btnLogin);

        db=FirebaseDatabase.getInstance();
        users=db.getReference("User");
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser(edtName.getText().toString(),edtPassword.getText().toString());
            }
        });
    }

    private void signInUser(String name, final String password) {
        final ProgressDialog mDialog = new ProgressDialog(signin.this);

        mDialog.setMessage("Please Wait....");
        mDialog.show();


        final String localPhone = name;
        final String localPassword = password;

        users.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(edtName.getText().toString()).exists())
                {
                mDialog.dismiss();
                User user = dataSnapshot.child(localPhone).getValue(User.class);
                user.setPhone(localPhone);

                if(Boolean.parseBoolean(user.getIsStaff()))
                {
                    if(user.getPassword().equals(localPassword))
                    {
                        Intent signin= new Intent(signin.this,Home.class);
                        Common.currentUser=user;
                        startActivity(signin);
                        finish();

                    }
                    else
                    {
                        Toast.makeText(signin.this,"Wrong Password ! ", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(signin.this,"Please login with staff account",Toast.LENGTH_SHORT).show();
                }

            }
                else
                {
                    mDialog.dismiss();
                    Toast.makeText(signin.this,"User not exist in Database",Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
