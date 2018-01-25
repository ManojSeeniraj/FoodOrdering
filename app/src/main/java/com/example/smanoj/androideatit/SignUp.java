package com.example.smanoj.androideatit;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.smanoj.androideatit.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    MaterialEditText edtphone,edtName,edtPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        edtName=(MaterialEditText)findViewById(R.id.edtName);
        edtPassword=(MaterialEditText)findViewById(R.id.edtPassword);
        edtphone=(MaterialEditText)findViewById(R.id.edtPhone);

        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        //init firebase
        final FirebaseDatabase database =FirebaseDatabase.getInstance();
        final DatabaseReference table_User =database.getReference("user");


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog=new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please Wait...");
                mDialog.show();


                table_User.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //check if alredy user phone

                        if(dataSnapshot.child(edtphone.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this,"Phone Number Alredy Exits",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mDialog.dismiss();
                            User user=new User(edtName.getText().toString(),edtPassword.getText().toString());
                            table_User.child(edtphone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this,"Sign up successfull!!",Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });


    }
}
