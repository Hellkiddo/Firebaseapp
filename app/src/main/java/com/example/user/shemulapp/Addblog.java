package com.example.user.shemulapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Addblog extends AppCompatActivity {

    private FirebaseAuth firebaseAut;
    private FirebaseAuth.AuthStateListener authStateListener;
private DatabaseReference mdatabbase;
    private ImageButton mSelectImage;
    private EditText mPosttitle;
    private EditText mPostdesc;
private Uri mimageUri=null;
    private Button mSubmitBtn;
    private StorageReference mstorage;
private ProgressDialog mmprogress;
    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addblog);



        firebaseAut= FirebaseAuth.getInstance();
mdatabbase= FirebaseDatabase.getInstance().getReference().child(firebaseAut.getUid()).child("Blog");
        mstorage= FirebaseStorage.getInstance().getReference();




        mSelectImage = (ImageButton) findViewById(R.id.imagepost);
        mPosttitle = (EditText) findViewById(R.id.posttitle);
        mPostdesc = (EditText) findViewById(R.id.postdescription);
        mSubmitBtn = (Button) findViewById(R.id.submitpost);
mmprogress=new ProgressDialog(this);
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);

            }
        });

mSubmitBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startposting();
    }
});
    }

    private void startposting() {mmprogress.setMessage("Posting...");

        final String titlevalue=mPosttitle.getText().toString().trim();
        final String descvalue=mPostdesc.getText().toString().trim();

        if (!TextUtils.isEmpty(titlevalue) && !TextUtils.isEmpty(descvalue)){
            mmprogress.show();
            StorageReference filepathss=mstorage.child(firebaseAut.getUid()).child("eventuploads").child(mimageUri.getLastPathSegment());
            filepathss.putFile(mimageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               Uri downloaduri= taskSnapshot.getDownloadUrl();
               DatabaseReference newpost=mdatabbase.push();
               newpost.child("title").setValue(titlevalue);
                    newpost.child("description").setValue(descvalue);
                    newpost.child("uid").setValue(firebaseAut.getCurrentUser().getUid());
                    newpost.child("image").setValue(downloaduri.toString());

               mmprogress.dismiss();
               startActivity(new Intent(Addblog.this,NevigationActivity.class));
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            mimageUri = data.getData();

            mSelectImage.setImageURI(mimageUri);

        }

    }
}
