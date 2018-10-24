package com.example.user.shemulapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class Blog  extends AppCompatActivity {
private DatabaseReference mdatabasse;
private RecyclerView mbloglist;
    private FirebaseAuth firebaseAut;
    public Blog() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bloglayout);
        firebaseAut= FirebaseAuth.getInstance();
        mdatabasse= FirebaseDatabase.getInstance().getReference().child(firebaseAut.getUid()).child("Blog");
        mbloglist=(RecyclerView) findViewById(R.id.blogl);
        mbloglist.setHasFixedSize(true);
        mbloglist.setLayoutManager(new LinearLayoutManager(this));




}


        // Inflate the layout for this fragment






    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<posteditem,Blogviewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<posteditem, Blogviewholder>(
                posteditem.class,R.layout.blogrow,Blogviewholder.class,mdatabasse
        ) {



            @Override
            protected void populateViewHolder(Blogviewholder viewHolder, posteditem model, int position) {

viewHolder.settitle(model.getTitle());
                viewHolder.setdescription(model.getDescription());
                viewHolder.setimage(getApplicationContext(),model.getImage());
            }
        };
        mbloglist.setAdapter(firebaseRecyclerAdapter);
    }








    public static class Blogviewholder extends RecyclerView.ViewHolder{

View mview;
        public Blogviewholder(View itemView) {
            super(itemView);
           mview=itemView;

        }


        public void settitle(String title){
            TextView posttitle=(TextView) mview.findViewById(R.id.titleposted);
            posttitle.setText(title);


        }

        public void setdescription(String description){
            TextView postdescription=(TextView) mview.findViewById(R.id.descposted);
            postdescription.setText(description);


        }

        public void setimage(Context ctx, String image){
            ImageView post_image=(ImageView) mview.findViewById(R.id.imageposted);


          //  Picasso.with(ctx).load(image).into(post_image);
            Picasso.get().load(image).into(post_image);

        }


}}

