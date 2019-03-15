package com.example.parenteye;

import android.content.Intent;

//import android.support.v7.app.AlertController.RecycleListView;
//import android.support.v7.app.AlertController;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private StorageReference mStorageRef2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    DatabaseReference myRef2 = database.getReference("Posts");
    DatabaseReference myRef3 = database.getReference("Friends");
    private Integer userCompleteProfile=0;
    private Button logout;
    private ListView Post_listview;
    private String friendlist;
    private ArrayList<String> friendspostskeys=new ArrayList<String>();
    private ArrayList<Posts> Postss=new ArrayList<Posts>();
    final long ONE_MEGABYTE = 1024 * 1024;
    private Button makePage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("UserImages/");
        mStorageRef2 = FirebaseStorage.getInstance().getReference("PostsImages/");




        makePage=(Button) findViewById(R.id.makepage);
        logout=(Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(mAuth.getCurrentUser()!=null){
                   mAuth.signOut();
                   main_login();

               }
           }
       });
        makePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         Intent makepage=new Intent(MainActivity.this,AddPageActivity.class);
         startActivity(makepage);
         //finish();
            }
        });

/*
        Friends friend=new Friends();
        friend.setUserId("sfqRhCgFi5c5vDfMETgsrODa9Yh1");
        friend.setUserFriends("4tRSpQD4slPQMFQu5TYQXTPhQWT2,KESsF62g1WR48ZXztXXRFjlsqex2,Y07QYSgpGqMTQmuuo4PxeCRWKG93,lGoKGzSP2XMg9tkQsaqq8fa7l5u1,sfqRhCgFi5c5vDfMETgsrODa9Yh1");
        myRef3.push().setValue(friend);
*/



     // 1 sora 3and aya, 1 aya fady, ahmed fady,ayman sora
     /*   Posts post1=new Posts();
        post1.setUserId("w0hp6UeWq5Ul9Zt3rw5V6hoXvFA3");
        post1.setPlaceTypeId("1");
        post1.setPostcontent("This post is done by aya in her timeline");
        post1.setHasimage(true);
        post1.setImageId("");
        Posts post2=new Posts();
        post2.setUserId("w0hp6UeWq5Ul9Zt3rw5V6hoXvFA3");
        post2.setPlaceTypeId("1");
        post2.setHasimage(false);
        post2.setPostcontent("This post is done by aya in her timeline too");
        Posts post3=new Posts();
        post3.setUserId("w0hp6UeWq5Ul9Zt3rw5V6hoXvFA3");
        post3.setPlaceTypeId("3");
        post3.setPostcontent("This post is done by aya in her group ");
        post3.setHasimage(true);
        post3.setImageId("");
        Posts post4=new Posts();
        post4.setUserId("w0hp6UeWq5Ul9Zt3rw5V6hoXvFA3");
        post4.setPlaceTypeId("2");
        post4.setPostcontent("This post is done by aya in her page ");
        post4.setHasimage(true);
        post4.setImageId("");
        Posts post5=new Posts();
        post5.setUserId("4tRSpQD4slPQMFQu5TYQXTPhQWT2");
        post5.setPlaceTypeId("1");
        post5.setPostcontent("This post is done by alaa in her timeline ");
        post5.setHasimage(true);
        post5.setImageId("");
        Posts post6=new Posts();
        post6.setUserId("4tRSpQD4slPQMFQu5TYQXTPhQWT2");
        post6.setPlaceTypeId("2");
        post6.setPostcontent("This post is done by alaa in her page ");
        post6.setHasimage(false);
        Posts post7=new Posts();
        post7.setUserId("KESsF62g1WR48ZXztXXRFjlsqex2");
        post7.setPlaceTypeId("1");
        post7.setPostcontent("This post is done by ahmed in his timeline ");
        post7.setHasimage(false);
        Posts post8=new Posts();
        post8.setUserId("Y07QYSgpGqMTQmuuo4PxeCRWKG93");
        post8.setPlaceTypeId("1");
        post8.setHasimage(true);
        post8.setImageId("");
        post8.setPostcontent("This post is done by ayman in his timeline ");
        Posts post9=new Posts();
        post9.setUserId("lGoKGzSP2XMg9tkQsaqq8fa7l5u1");
        post9.setPlaceTypeId("2");
        post9.setHasimage(false);
        post9.setPostcontent("This post is done by ibrahim in his page ");
        myRef2.push().setValue(post1);
        myRef2.push().setValue(post2);
        myRef2.push().setValue(post3);
        myRef2.push().setValue(post4);
        myRef2.push().setValue(post5);
        myRef2.push().setValue(post6);
        myRef2.push().setValue(post7);
        myRef2.push().setValue(post8);
        myRef2.push().setValue(post9);


*/

    /*   ArrayList<custom_posts_returned> HomePosts=new ArrayList<custom_posts_returned>();
        Post_listview=(ListView)findViewById(R.id.Post_listview);
         PostAdapter postadapter=new PostAdapter(MainActivity.this,HomePosts);
        Post_listview.setAdapter(postadapter);
custom_posts_returned c=new custom_posts_returned();
c.setPost_owner_name("post1");
c.setPost_text("post1 text");
        custom_posts_returned c2=new custom_posts_returned();
        c2.setPost_owner_name("post1");
        c2.setPost_text("post1 text");
        custom_posts_returned c3=new custom_posts_returned();
        c3.setPost_owner_name("post1");
        c3.setPost_text("post1 text");
        custom_posts_returned c1=new custom_posts_returned();
        c1.setPost_owner_name("post1");
        c1.setPost_text("post1 text");
        custom_posts_returned c4=new custom_posts_returned();
        c4.setPost_owner_name("post1");
        c4.setPost_text("post1 text");
        custom_posts_returned c5=new custom_posts_returned();
        c5.setPost_owner_name("post1");
        c5.setPost_text("post1 text");
        custom_posts_returned c6=new custom_posts_returned();
        c6.setPost_owner_name("post1");
        c6.setPost_text("post1 text");
        custom_posts_returned c7=new custom_posts_returned();
        c7.setPost_owner_name("post1");
        c7.setPost_text("post1 text");
        custom_posts_returned c8=new custom_posts_returned();
        c8.setPost_owner_name("post1");
        c8.setPost_text("post1 text");
        custom_posts_returned c9=new custom_posts_returned();
        c9.setPost_owner_name("post1");
        c9.setPost_text("post1 text");
        custom_posts_returned c10=new custom_posts_returned();
        c10.setPost_owner_name("post1");
        c10.setPost_text("post1 text");
        HomePosts.add(c);
        HomePosts.add(c2);
        HomePosts.add(c3);
        HomePosts.add(c1);
        HomePosts.add(c4);
        HomePosts.add(c5);
        HomePosts.add(c6);
        HomePosts.add(c7);
        HomePosts.add(c8);
        HomePosts.add(c9);
        HomePosts.add(c10);
        postadapter.notifyDataSetChanged();

*/

    }

    @Override
    protected void onStart() {
        AllHomePosts();

        if(mAuth.getCurrentUser()==null){
            main_login();
        }
        else{
            check_complete_profile();

        }
        super.onStart();
    }


    private void main_signup(){
        Intent main_signup=new Intent(MainActivity.this,SignupActivity.class);
        startActivity(main_signup);
        finish();
    }
    private void main_login(){
        Intent main_login=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(main_login);
        finish();

    }
    private void check_complete_profile(){


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot UsersSnapshot: dataSnapshot.getChildren()) {
                     Users user=UsersSnapshot.getValue(Users.class);
                     String key=UsersSnapshot.getKey();
                     if(TextUtils.equals(key,mAuth.getCurrentUser().getUid())){
                         userCompleteProfile=1;
                    }

                }
                if(userCompleteProfile==0){
                    Intent userdataactivity_intent=new Intent(MainActivity.this,UserdataActivity.class);
                    startActivity(userdataactivity_intent);
                    finish();
                }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }
  private void AllHomePosts(){
      final ArrayList<custom_posts_returned> HomePosts=new ArrayList<custom_posts_returned>();
      HomePosts.clear();
        if(mAuth.getCurrentUser()!=null){
            Post_listview=(ListView)findViewById(R.id.Post_listview);
            final PostAdapter postadapter=new PostAdapter(MainActivity.this,HomePosts);
            Post_listview.setAdapter(postadapter);
            myRef3.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                   Friends currentuser=dataSnapshot.getValue(Friends.class);
                   if(TextUtils.equals(currentuser.getUserId(),mAuth.getCurrentUser().getUid())){
                       String []friends=currentuser.getUserFriends().split(",");

                       for(final String friend:friends){
                          // System.out.println(" is "+friend);
                           myRef2.addChildEventListener(new ChildEventListener() {
                               @Override
                               public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                   final Posts checkPost=dataSnapshot.getValue(Posts.class);
                                   final String postkey=dataSnapshot.getKey();
                                   if(TextUtils.equals(checkPost.getUserId(),friend)&&TextUtils.equals(checkPost.getPlaceTypeId(),"1")){ //hena post text cheeeck empty
                                      // System.out.println("firends are "+friend);
                                       myRef.addChildEventListener(new ChildEventListener() {
                                           @Override
                                           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                              final Users postuser=dataSnapshot.getValue(Users.class);
                                              String key=dataSnapshot.getKey();
                                               if(TextUtils.equals(key,checkPost.getUserId())){// mn hena username
                                                  final custom_posts_returned custom=new custom_posts_returned();
                                                   custom.setPost_owner_name(postuser.getUsername());
                                                   custom.setPost_text(checkPost.getPostcontent());
                                                   if(postuser.getProfile_pic_id()!=null&&checkPost.isHasimage()==true) {  //lw 3ando both
                                                       mStorageRef.child(postuser.getProfile_pic_id()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                           @Override
                                                           public void onSuccess(byte[] bytes) {
                                                               final Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                               DisplayMetrics dm = new DisplayMetrics();
                                                               getWindowManager().getDefaultDisplay().getMetrics(dm);
                                                               custom.setProfile_image(bm);
                                                               mStorageRef2.child(checkPost.getImageId()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                                   @Override
                                                                   public void onSuccess(byte[] bytes) {
                                                                       Bitmap bm2 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                                       DisplayMetrics dm2 = new DisplayMetrics();
                                                                       getWindowManager().getDefaultDisplay().getMetrics(dm2);
                                                                       custom.setPost_image(bm2);
                                                                       if (checkPost.getPostcontent() != null) {
                                                                           custom.setPost_text(checkPost.getPostcontent());
                                                                       }

                                                                       HomePosts.add(custom);
                                                                       postadapter.notifyDataSetChanged();

                                                                     /*  System.out.println("name is "+custom.getPost_owner_name());
                                                                       System.out.println("profile image is "+custom.getProfile_image());
                                                                       System.out.println("post image is "+custom.getPost_image());
                                                                       System.out.println("post content is "+custom.getPost_text());
                                                                             */
                                                                       //  System.out.println("size is "+HomePosts.size());
                                                                       // System.out.println("from both  is "+custom.getPost_owner_name());
                                                                   }
                                                               });

                                                           }
                                                       });

                                                   }
                                                   else if(postuser.getProfile_pic_id()!=null&&checkPost.isHasimage()==false){ //lw 3ando profile w m3ndosh post image
                                                       mStorageRef.child(postuser.getProfile_pic_id()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                           @Override
                                                           public void onSuccess(byte[] bytes) {
                                                               final Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                               DisplayMetrics dm = new DisplayMetrics();
                                                               getWindowManager().getDefaultDisplay().getMetrics(dm);
                                                               custom.setProfile_image(bm);
                                                               if (checkPost.getPostcontent() != null) {
                                                                   custom.setPost_text(checkPost.getPostcontent());
                                                               }

                                                               HomePosts.add(custom);
                                                               postadapter.notifyDataSetChanged();

                                                              /* System.out.println("name is "+custom.getPost_owner_name());
                                                               System.out.println("profile image is "+custom.getProfile_image());
                                                               System.out.println("post image is "+custom.getPost_image());
                                                               System.out.println("post content is "+custom.getPost_text());
                                                             */
                                                               // System.out.println("size is "+HomePosts.size());
                                                               // System.out.println("from profile only  is "+custom.getPost_owner_name());

                                                           }
                                                       });


                                                   }
                                                   else if(postuser.getProfile_pic_id()==null&&checkPost.isHasimage()==true){ //lw 3ando post image w mfesh profile
                                                       mStorageRef2.child(checkPost.getImageId()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                           @Override
                                                           public void onSuccess(byte[] bytes) {
                                                               Bitmap bm2 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                               DisplayMetrics dm2 = new DisplayMetrics();
                                                               getWindowManager().getDefaultDisplay().getMetrics(dm2);
                                                               custom.setPost_image(bm2);
                                                               if (checkPost.getPostcontent() != null) {
                                                                   custom.setPost_text(checkPost.getPostcontent());
                                                               }

                                                               HomePosts.add(custom);
                                                               postadapter.notifyDataSetChanged();

                                                               /*System.out.println("name is "+custom.getPost_owner_name());
                                                               System.out.println("profile image is "+custom.getProfile_image());
                                                               System.out.println("post image is "+custom.getPost_image());
                                                               System.out.println("post content is "+custom.getPost_text()); */

                                                               //System.out.println("size is "+HomePosts.size());
                                                               //System.out.println("post img only is "+custom.getPost_owner_name());

                                                           }
                                                       });


                                                   }
                                                   else{
                                                       if (checkPost.getPostcontent() != null) {
                                                           custom.setPost_text(checkPost.getPostcontent());
                                                       }

                                                       HomePosts.add(custom);
                                                       postadapter.notifyDataSetChanged();


                                                      /* System.out.println("name is "+custom.getPost_owner_name());
                                                       System.out.println("profile image is "+custom.getProfile_image());
                                                       System.out.println("post image is "+custom.getPost_image());
                                                       System.out.println("post content is "+custom.getPost_text());*/
                                                       //System.out.println("size is "+HomePosts.size());
                                                       //System.out.println("neither is "+custom.getPost_owner_name());


                                                   }


             /* mStorageRef.child(postuser.getProfile_pic_id()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                           @Override
                                                           public void onSuccess(byte[] bytes) {

                                                              final Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                               DisplayMetrics dm = new DisplayMetrics();
                                                               getWindowManager().getDefaultDisplay().getMetrics(dm);
                                                               if(checkPost.isHasimage()==true) {
                                                                   mStorageRef2.child(checkPost.getImageId()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                                       @Override
                                                                       public void onSuccess(byte[] bytes) {
                                                                           Bitmap bm2 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                                           DisplayMetrics dm2 = new DisplayMetrics();
                                                                           getWindowManager().getDefaultDisplay().getMetrics(dm2);
                                                                           custom_posts_returned custom = new custom_posts_returned();
                                                                           custom.setPost_owner_name(postuser.getUsername());
                                                                           custom.setPost_image(bm2);
                                                                           if (checkPost.getPostcontent() != null) {
                                                                               custom.setPost_text(checkPost.getPostcontent());

                                                                           }
                                                                           custom.setProfile_image(bm);
                                                                           HomePosts.add(custom);
                                                                           postadapter.notifyDataSetChanged();


                                                                       }
                                                                   });

                                                               }
                                                               else{
                                                                   custom_posts_returned custom = new custom_posts_returned();
                                                                   custom.setPost_owner_name(postuser.getUsername());
                                                                   if (checkPost.getPostcontent() != null) {
                                                                       custom.setPost_text(checkPost.getPostcontent());

                                                                   }
                                                                   custom.setProfile_image(bm);
                                                                   HomePosts.add(custom);
                                                                   postadapter.notifyDataSetChanged();

                                                               }
                                                           }
                                                       });

                                                    */

                                                      /* else if(checkPost.isHasimage()==true){
                                                       mStorageRef2.child(checkPost.getImageId()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                           @Override
                                                           public void onSuccess(byte[] bytes) {
                                                               Bitmap bm2 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                               DisplayMetrics dm2 = new DisplayMetrics();
                                                               getWindowManager().getDefaultDisplay().getMetrics(dm2);
                                                               custom_posts_returned custom = new custom_posts_returned();
                                                               custom.setPost_owner_name(postuser.getUsername());
                                                               custom.setPost_image(bm2);
                                                               if (checkPost.getPostcontent() != null) {
                                                                   custom.setPost_text(checkPost.getPostcontent());

                                                               }
                                                               HomePosts.add(custom);
                                                               postadapter.notifyDataSetChanged();


                                                           }
                                                       });

                                                   }
                                                   else{
                                                       custom_posts_returned custom=new custom_posts_returned();
                                                       custom.setPost_owner_name(postuser.getUsername());
                                                       if(checkPost.getPostcontent()!=null){
                                                           custom.setPost_text(checkPost.getPostcontent());
                                                       }
                                                       HomePosts.add(custom);
                                                       postadapter.notifyDataSetChanged();
                                                   }

                                                        */



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
