package com.example.parenteye;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference postref = database.getReference("Posts");
    DatabaseReference userRef = database.getReference("Users");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuth = FirebaseAuth.getInstance();
    }



    private void ProfilePosts(){
          if(mAuth.getCurrentUser()!=null){

              final String profileUserId="";
              userRef.addChildEventListener(new ChildEventListener() {
                  @Override
                  public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                      Users user=dataSnapshot.getValue(Users.class);
                      if(TextUtils.equals(dataSnapshot.getKey(),profileUserId)){
                          custom_posts_returned custompost=new custom_posts_returned();
                          custompost.setPost_owner_name(user.getUsername());
                          postref.addChildEventListener(new ChildEventListener() {
                              @Override
                              public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                  Posts post=dataSnapshot.getValue(Posts.class);
                                  if(TextUtils.equals(profileUserId,post.getUserId())&&TextUtils.equals(post.getPlaceTypeId(),"1")){



                                  }
                              }

                              @Override
                              public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                              }

                              @Override
                              public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                              }

                              @Override
                              public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                              }

                              @Override
                              public void onCancelled(@NonNull DatabaseError databaseError) {

                              }
                          });
                      }
                  }

                  @Override
                  public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                  }

                  @Override
                  public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                  }

                  @Override
                  public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError databaseError) {

                  }
              });


          }

    }
}
