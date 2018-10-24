package com.example.user.shemulapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup_Activity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAut;
    private DatabaseReference mdatabase;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Button create,signin;private EditText fname,lname,email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        firebaseAut=FirebaseAuth.getInstance();

        mdatabase= FirebaseDatabase.getInstance().getReference().child("users"); // child crete korlam

        setContentView(R.layout.activity_signup);


        progressDialog=new ProgressDialog(this);
        create=(Button) findViewById(R.id.CreatAccount);
        signin=(Button) findViewById(R.id.SignInFromReg);
        fname=(EditText) findViewById(R.id.FirstNae);
        lname=(EditText) findViewById(R.id.LatName);
        email=(EditText) findViewById(R.id.EmailPhone);
        pass=(EditText) findViewById(R.id.rew);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startreg();
            }
        });

    }

    private void startreg() {
        final String firstname=fname.getText().toString().trim();
        final String lastname=lname.getText().toString().trim();
        String Email=email.getText().toString().trim();
        String Pass=pass.getText().toString().trim();

        if (TextUtils.isEmpty(Pass)&&TextUtils.isEmpty(firstname)&&TextUtils.isEmpty(lastname)&&TextUtils.isEmpty(Email))
        {
            Toast.makeText(this, "Please Fillup All Fields....", Toast.LENGTH_SHORT).show();
        }
        else {
            if (Pass.length()<5) {
                Toast.makeText(this, "Input Atleast 6 Digits..", Toast.LENGTH_SHORT).show();
            }
            else {
                if (!TextUtils.isEmpty(Email) && !TextUtils.isEmpty(Pass) && !TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname)) {
                    progressDialog.setMessage("Signing up...");
                    progressDialog.show();

                    firebaseAut.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {


                                String userid = firebaseAut.getCurrentUser().getUid();//
                                DatabaseReference currentuser = mdatabase.child(userid);
                                currentuser.child("userid").setValue(userid);
                                currentuser.child("firstname").setValue(firstname);
                                currentuser.child("lastname").setValue(lastname);
                                progressDialog.dismiss();
                                Intent mynIntent = new Intent(signup_Activity.this, NevigationActivity.class);
                                mynIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// ager sob kisu clear kore felbe like use name pass field er likha
                                startActivity(mynIntent);


                            }


                        }
                    });

                }
            }
        }



    }}


