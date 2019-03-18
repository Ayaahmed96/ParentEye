package com.example.parenteye;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference postref = database.getReference("Posts");
    DatabaseReference userRef = database.getReference("Users");
    DatabaseReference CommunityRef = database.getReference("Community");
    DatabaseReference FriendRequestRef = database.getReference("FriendRequest");
    DatabaseReference friendsRef = database.getReference("Friends");
    DatabaseReference membersRef = database.getReference("Members");
    private StorageReference mStorageRef;
    private StorageReference mStorageRef2;
    private StorageReference mStorageRef3;
    private ListView Post_listview;
    private CircleImageView Accountprofile;
    private ImageView AccountCover;
    private TextView Accountname;
    private TextView useraddresse;
    private TextView usergender;
    private int friendscount=0;
    private TextView friendsNumber;
    final long ONE_MEGABYTE = 1024 * 1024;
    private Button Addfriend;
    private boolean IsExist=false;
    private String key=null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);



        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("UserImages/");
        mStorageRef2 = FirebaseStorage.getInstance().getReference("PostsImages/");
        mStorageRef3 = FirebaseStorage.getInstance().getReference("PageImages/");

        Accountprofile=(CircleImageView) findViewById(R.id.Accountprofile);
        AccountCover=(ImageView)findViewById(R.id.Accountcover);
        Accountname=(TextView) findViewById(R.id.Accountname);
        useraddresse=(TextView) findViewById(R.id.userAdresse);
        usergender=(TextView) findViewById(R.id.userGender);
        friendsNumber=(TextView) findViewById(R.id.friendsNumber);
        Addfriend=(Button) findViewById(R.id.addfriend);
        Addfriend.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            //  checkrequest(mAuth.getCurrentUser().getUid(),"BHgoh3PIT6R33lknkJPYswV0A3C2");
                  FriendRequest friend=new FriendRequest();
                  friend.setSenderid(mAuth.getCurrentUser().getUid());
                  friend.setRecieverid("BHgoh3PIT6R33lknkJPYswV0A3C2");
                  friend.setState(1);
                  FriendRequestRef.push().setValue(friend);
                  Addfriend.setText("Request sent");

          }
      });





    }



    @Override
    protected void onStart() {
      //  ProfilePosts();
       // GetUserProfiledata();
       // GetGroupPosts();
        getPageInfo();
        super.onStart();
    }



    private void checkrequest(final String sender, final String reciever)
    {
        FriendRequestRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FriendRequest checkfriend=dataSnapshot.getValue(FriendRequest.class);

                if(TextUtils.equals(checkfriend.getSenderid(),sender) && TextUtils.equals(checkfriend.getRecieverid(),reciever))
                {
                    key=dataSnapshot.getKey();
                   /* FriendRequestRef.child(dataSnapshot.getKey()).removeValue();
                    Addfriend.setText("Add Friend");
                    IsExist=true;
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
























    private void ProfilePosts(){
        final ArrayList<custom_posts_returned> profilePosts=new ArrayList<custom_posts_returned>();
        profilePosts.clear();

       //   if(mAuth.getCurrentUser()!=null){

              Post_listview=(ListView)findViewById(R.id.accountPost_listview);
              final PostAdapter postadapter=new PostAdapter(AccountActivity.this,profilePosts);
              //postadapter.clear();
              Post_listview.setAdapter(postadapter);

              final String profileUserId="BHgoh3PIT6R33lknkJPYswV0A3C2";
              userRef.addChildEventListener(new ChildEventListener() {
                  @Override
                  public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                     final Users user=dataSnapshot.getValue(Users.class);
                      if(TextUtils.equals(dataSnapshot.getKey(),profileUserId)){
                        final   custom_posts_returned custompost=new custom_posts_returned();
                          custompost.setPost_owner_name(user.getUsername());
                          postref.addChildEventListener(new ChildEventListener() {
                              @Override
                              public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                 final Posts post=dataSnapshot.getValue(Posts.class);
                                  if(TextUtils.equals(profileUserId,post.getUserId())&&TextUtils.equals(post.getPlaceTypeId(),"1")){
                                      custompost.setPost_text(post.getPostcontent());
                              //  System.out.println("post content is "+post.getPostcontent());
                                      profilePosts.add(custompost);
                                    int i=profilePosts.indexOf(custompost);

                                   custom_posts_returned c=profilePosts.get(i);
                                    //  System.out.println("content ta7t"+c.getPost_text());
                                   //   System.out.println("index "+i);

                                     // postadapter.add(custompost);
                                      postadapter.notifyDataSetChanged();

                                  /*   if(user.getProfile_pic_id()!=null&&post.isHasimage()==true){ //if both

                                         mStorageRef.child(user.getProfile_pic_id()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                             @Override
                                             public void onSuccess(byte[] bytes) {
                                                 final Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                 DisplayMetrics dm = new DisplayMetrics();
                                                 getWindowManager().getDefaultDisplay().getMetrics(dm);
                                                 custompost.setProfile_image(bm);
                                                 mStorageRef2.child(post.getImageId()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                     @Override
                                                     public void onSuccess(byte[] bytes) {
                                                         Bitmap bm2 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                         DisplayMetrics dm2 = new DisplayMetrics();
                                                         getWindowManager().getDefaultDisplay().getMetrics(dm2);
                                                         custompost.setPost_image(bm2);


                                                         HomePosts.add(custompost);
                                                         postadapter.notifyDataSetChanged();


                                                     }
                                                 });

                                             }
                                         }); }
                                         else if(user.getProfile_pic_id()!=null&&post.isHasimage()==false){ //3ando p.p w post img l2

                                         mStorageRef.child(user.getProfile_pic_id()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                             @Override
                                             public void onSuccess(byte[] bytes) {
                                                 final Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                 DisplayMetrics dm = new DisplayMetrics();
                                                 getWindowManager().getDefaultDisplay().getMetrics(dm);
                                                 custompost.setProfile_image(bm);


                                                 HomePosts.add(custompost);
                                                 for(custom_posts_returned c :HomePosts){
                                                     System.out.println("post content is "+c.getPost_text());
                                                 }
                                                 System.out.println("size is"+HomePosts.size());
                                                 postadapter.notifyDataSetChanged();


                                             }
                                         });

                                      }
                                      else if(user.getProfile_pic_id()==null&&post.isHasimage()==true){  //lw 3ando post image w mfesh profile

                                         mStorageRef2.child(post.getImageId()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                             @Override
                                             public void onSuccess(byte[] bytes) {
                                                 Bitmap bm2 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                 DisplayMetrics dm2 = new DisplayMetrics();
                                                 getWindowManager().getDefaultDisplay().getMetrics(dm2);
                                                 custompost.setPost_image(bm2);


                                                 HomePosts.add(custompost);
                                                 postadapter.notifyDataSetChanged();
                                             }
                                         });
                                      }

                                         else{


                                         HomePosts.add(custompost);
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


         // }

    }



    private void GetGroupPosts(){
       final String GroupId="-La0sXFPy2dXzxX-Xws6";
       final ArrayList<custom_posts_returned> GroupPosts=new ArrayList<custom_posts_returned>();
        GroupPosts.clear();
        Post_listview=(ListView)findViewById(R.id.accountPost_listview);
        final PostAdapter postadapter=new PostAdapter(AccountActivity.this,GroupPosts);
        Post_listview.setAdapter(postadapter);
        CommunityRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Community com=dataSnapshot.getValue(Community.class);
                String key=dataSnapshot.getKey();
                if(TextUtils.equals(key,GroupId)&&TextUtils.equals(com.getTypeid(),"1")){
                    final custom_posts_returned grouppost=new custom_posts_returned();
                    postref.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            final Posts post=dataSnapshot.getValue(Posts.class);
             if(TextUtils.equals(post.getPlaceTypeId(),"3")&&TextUtils.equals(post.getPlaceId(),GroupId)){
                if(post.isHasimage()==true){
                    mStorageRef2.child(post.getImageId()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bm2 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            DisplayMetrics dm2 = new DisplayMetrics();
                            getWindowManager().getDefaultDisplay().getMetrics(dm2);
                            grouppost.setPost_image(bm2);

                            userRef.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                    Users user=dataSnapshot.getValue(Users.class);
                                    String userkey=dataSnapshot.getKey();
                                    if(TextUtils.equals(userkey,post.getUserId())){
                                        grouppost.setPost_owner_name(user.getUsername());
                                        if(user.getProfile_pic_id()!=null){
                                            mStorageRef.child(user.getProfile_pic_id()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                @Override
                                                public void onSuccess(byte[] bytes) {
                                                    final Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    DisplayMetrics dm = new DisplayMetrics();
                                                    getWindowManager().getDefaultDisplay().getMetrics(dm);
                                                    grouppost.setProfile_image(bm);
                                                  if(post.getPostcontent()!=null){
                                                      grouppost.setPost_text(post.getPostcontent());
                                                  }

                                                    GroupPosts.add(grouppost);
                                                    postadapter.notifyDataSetChanged();


                                                }
                                            });

                                        }
                                        else{
                                            if(post.getPostcontent()!=null){
                                                grouppost.setPost_text(post.getPostcontent());
                                            }
                                            GroupPosts.add(grouppost);
                                            postadapter.notifyDataSetChanged();
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
                    });
                }                 else{
                    userRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                           Users user=dataSnapshot.getValue(Users.class);
                           if(TextUtils.equals(dataSnapshot.getKey(),post.getUserId())){
                               grouppost.setPost_owner_name(user.getUsername());
                               if(user.getProfile_pic_id()!=null){
                                   mStorageRef.child(user.getProfile_pic_id()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                       @Override
                                       public void onSuccess(byte[] bytes) {
                                           final Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                           DisplayMetrics dm = new DisplayMetrics();
                                           getWindowManager().getDefaultDisplay().getMetrics(dm);
                                           grouppost.setProfile_image(bm);
                                           if(post.getPostcontent()!=null){
                                               grouppost.setPost_text(post.getPostcontent());
                                           }
                                            // System.out.println("post is "+grouppost.getPost_text());
                                           GroupPosts.add(grouppost);
                                           postadapter.notifyDataSetChanged();
                                 int i=  GroupPosts.indexOf(grouppost);
                                 custom_posts_returned c=GroupPosts.get(i);
                                           System.out.println("post ta7t is "+c.getPost_text());
                                       }
                                   });

                               }
                               else{
                                   if(post.getPostcontent()!=null){
                                       grouppost.setPost_text(post.getPostcontent());
                                   }

                                   GroupPosts.add(grouppost);
                                   postadapter.notifyDataSetChanged();
                                   int i=  GroupPosts.indexOf(grouppost);
                                   custom_posts_returned c=GroupPosts.get(i);
                                   System.out.println("post ta7t is "+c.getPost_text());

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

      public void GetUserProfiledata(){
        final String userID="BHgoh3PIT6R33lknkJPYswV0A3C2";
           userRef.addChildEventListener(new ChildEventListener() {
               @Override
               public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                   final Users checkuser=dataSnapshot.getValue(Users.class);
                   if(TextUtils.equals(dataSnapshot.getKey(),userID)){
                       Accountname.setText(checkuser.getUsername());
                       useraddresse.setText(checkuser.getLocation());
                       if(checkuser.isGender()==true){
                           usergender.setText("Male");
                       }
                       else{
                           usergender.setText("Female");
                       }
                       if(checkuser.getProfile_pic_id()!=null){
                           mStorageRef.child(checkuser.getProfile_pic_id()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                               @Override
                               public void onSuccess(byte[] bytes) {
                                   final Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                   DisplayMetrics dm = new DisplayMetrics();
                                   getWindowManager().getDefaultDisplay().getMetrics(dm);
                                   Accountprofile.setImageBitmap(bm);

                               }
                           });
                       }
                       else{
                           Accountprofile.setImageResource(R.drawable.defaultprofile);
                       }
                       if(checkuser.getCover_pic_id()!=null){
                           mStorageRef.child(checkuser.getCover_pic_id()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                               @Override
                               public void onSuccess(byte[] bytes) {
                                   final Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                   DisplayMetrics dm = new DisplayMetrics();
                                   getWindowManager().getDefaultDisplay().getMetrics(dm);
                                   AccountCover.setImageBitmap(bm);

                               }
                           });
                       }
                       else{
                           AccountCover.setImageResource(R.drawable.cover);
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
          friendsRef.addChildEventListener(new ChildEventListener() {
              @Override
              public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                  Friends fr=dataSnapshot.getValue(Friends.class);
                  if(TextUtils.equals(fr.getUserId(),userID)){
                      String []friends=fr.getUserFriends().split(",");
                      for(String friend:friends){
                          friendscount=friendscount+1;
                          if(TextUtils.equals(friend,userID)){
                              Addfriend.setText("Friends");
                              Addfriend.setEnabled(false);
                          }
                      }

                      friendsNumber.setText("100");

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

          FriendRequestRef.addChildEventListener(new ChildEventListener() {
              @Override
              public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 FriendRequest friendRequest=dataSnapshot.getValue(FriendRequest.class);
 if(TextUtils.equals(friendRequest.getSenderid(),mAuth.getCurrentUser().getUid())&&TextUtils.equals(friendRequest.getRecieverid(),userID)&&friendRequest.getState()==1){
                  Addfriend.setText("Request sent");
                  Addfriend.setEnabled(false);
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
      private void getPagePosts(){
          final ArrayList<custom_posts_returned> pagePosts=new ArrayList<custom_posts_returned>();
          pagePosts.clear();
          Post_listview=(ListView)findViewById(R.id.accountPost_listview);
          final PostAdapter postadapter=new PostAdapter(AccountActivity.this,pagePosts);
          Post_listview.setAdapter(postadapter);
        final String pageId="";
        postref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final Posts post=dataSnapshot.getValue(Posts.class);
                if(TextUtils.equals(post.getPlaceTypeId(),"2")&&TextUtils.equals(post.getPlaceId(),pageId)){
                    final custom_posts_returned custompost=new custom_posts_returned();
                    if(post.getPostcontent()!=null){
                        custompost.setPost_text(post.getPostcontent());
                    }
                    userRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            Users user=dataSnapshot.getValue(Users.class);
                            if(TextUtils.equals(dataSnapshot.getKey(),post.getUserId())){
                                custompost.setPost_owner_name(user.getUsername());
                                if(user.getProfile_pic_id()!=null&&post.isHasimage()==true){ //if both

                                    mStorageRef.child(user.getProfile_pic_id()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                        @Override
                                        public void onSuccess(byte[] bytes) {
                                            final Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                            DisplayMetrics dm = new DisplayMetrics();
                                            getWindowManager().getDefaultDisplay().getMetrics(dm);
                                            custompost.setProfile_image(bm);
                                            mStorageRef2.child(post.getImageId()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                @Override
                                                public void onSuccess(byte[] bytes) {
                                                    Bitmap bm2 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    DisplayMetrics dm2 = new DisplayMetrics();
                                                    getWindowManager().getDefaultDisplay().getMetrics(dm2);
                                                    custompost.setPost_image(bm2);


                                                    pagePosts.add(custompost);
                                                    postadapter.notifyDataSetChanged();


                                                }
                                            });

                                        }
                                    }); }
                                else if(user.getProfile_pic_id()!=null&&post.isHasimage()==false){ //3ando p.p w post img l2

                                    mStorageRef.child(user.getProfile_pic_id()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                        @Override
                                        public void onSuccess(byte[] bytes) {
                                            final Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                            DisplayMetrics dm = new DisplayMetrics();
                                            getWindowManager().getDefaultDisplay().getMetrics(dm);
                                            custompost.setProfile_image(bm);


                                            pagePosts.add(custompost);
                                            postadapter.notifyDataSetChanged();


                                        }
                                    });

                                }
                                else if(user.getProfile_pic_id()==null&&post.isHasimage()==true){  //lw 3ando post image w mfesh profile

                                    mStorageRef2.child(post.getImageId()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                        @Override
                                        public void onSuccess(byte[] bytes) {
                                            Bitmap bm2 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                            DisplayMetrics dm2 = new DisplayMetrics();
                                            getWindowManager().getDefaultDisplay().getMetrics(dm2);
                                            custompost.setPost_image(bm2);


                                            pagePosts.add(custompost);
                                            postadapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                                else{


                                    pagePosts.add(custompost);
                                    postadapter.notifyDataSetChanged();
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


      private void getPageInfo(){
        Addfriend.setText("Like");
       final String pageId="-La0sXFPy2dXzxX-Xws6";
       CommunityRef.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               Community com=dataSnapshot.getValue(Community.class);
               if(TextUtils.equals(com.getTypeid(),"2")&&TextUtils.equals(dataSnapshot.getKey(),pageId)){
                   Accountname.setText(com.getCommunityname());
                   useraddresse.setText(com.getCommunityAbout());
                   if(com.getPhotoId()!=null){
                       mStorageRef3.child(com.getPhotoId()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                           @Override
                           public void onSuccess(byte[] bytes) {
                               final Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                               DisplayMetrics dm = new DisplayMetrics();
                               getWindowManager().getDefaultDisplay().getMetrics(dm);
                               Accountprofile.setImageBitmap(bm);



                           }
                       });

                   }
                   else{
                       Accountprofile.setImageResource(R.drawable.defaultprofile);
                   }
                   if(com.getCoverPhotoId()!=null){
                       mStorageRef3.child(com.getCoverPhotoId()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                           @Override
                           public void onSuccess(byte[] bytes) {
                               final Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                               DisplayMetrics dm = new DisplayMetrics();
                               getWindowManager().getDefaultDisplay().getMetrics(dm);
                               AccountCover.setImageBitmap(bm);



                           }
                       });
                   }
                   else{
                       AccountCover.setImageResource(R.drawable.cover);
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
       membersRef.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               Members member=dataSnapshot.getValue(Members.class);
               if (TextUtils.equals(member.getId(), "2") && TextUtils.equals(member.getCommunityid(), pageId) && TextUtils.equals(member.getUserId(), mAuth.getCurrentUser().getUid())) {

                   Addfriend.setText("Liked");
                   Addfriend.setEnabled(false);
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

      private void getGroupInfo(){

      }


}
