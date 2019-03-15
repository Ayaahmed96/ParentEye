package com.example.parenteye;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPageActivity extends AppCompatActivity {

    private ImageView coverphoto;
    private CircleImageView profilephoto;
    private EditText pagename;
    private EditText pageAbout;
    private Button AddPage;
    private Integer PICK_IMAGE_REQUEST=100;
    private Integer PICK_IMAGE=101;
    private Uri filepath;
    private Uri filepath2;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private String profilekey=null;
    private String coverkey=null;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Community");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);

        mAuth=FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        coverphoto=(ImageView) findViewById(R.id.coverpage);
        profilephoto=(CircleImageView) findViewById(R.id.profilepage);
        pagename=(EditText)findViewById(R.id.pagename);
        pageAbout=(EditText)findViewById(R.id.pageAbout);
        AddPage=(Button)findViewById(R.id.Addpage);

        coverphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select image"),PICK_IMAGE_REQUEST);
            }
        });


        profilephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select image"),PICK_IMAGE);
            }
        });

        AddPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser()!=null) {
                    String page_name = pagename.getText().toString();
                    String page_about = pageAbout.getText().toString();
                    if (!TextUtils.isEmpty(page_name) && !TextUtils.isEmpty(page_about)) {
                        Community page = new Community();
                        page.setCommunityname(page_name);
                        page.setCommunityAbout(page_about);
                        page.setTypeid("2");
                        page.setAdminId(mAuth.getCurrentUser().getUid());
                        upload_profile_pic();
                        upload_cover_pic();
                        if(profilekey!=null){
                            page.setPhotoId(profilekey);
                        }
                        if(coverkey!=null){
                            page.setCoverPhotoId(coverkey);
                        }
                        myRef.push().setValue(page).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AddPageActivity.this, "Page Done Successfully", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(AddPageActivity.this, "Error !! " + task.getException(), Toast.LENGTH_LONG).show();

                                }
                            }
                        });

                        //page.setCreatedDate(java.time.LocalDate.now()); //min sdk 26
                    }
                    else {
                        Toast.makeText(AddPageActivity.this, "you should not leave enpty field", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            if(requestCode==PICK_IMAGE_REQUEST){
                filepath=data.getData();
                try{
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                    coverphoto.setImageBitmap(bitmap);


                }
                catch (IOException e){
                    e.printStackTrace();

                }
            }
            else if(requestCode==PICK_IMAGE){
                filepath2=data.getData();
                try{
                    Bitmap bitmap2= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath2);
                    profilephoto.setImageBitmap(bitmap2);

                }
                catch (IOException e){
                    e.printStackTrace();

                }

            }}


    }


    private void upload_profile_pic(){
        if(filepath2!=null){
            // final ProgressDialog progressdialogue=new ProgressDialog(this);
            //  progressdialogue.setTitle("loading...");
            //  progressdialogue.show();

            profilekey = UUID.randomUUID().toString();
            StorageReference ref = mStorageRef.child("PageImages/"+profilekey);
            ref.putFile(filepath2)

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // progressdialogue.dismiss();
                           // dismissProgressDialog();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressdialogue.dismiss();
                           // dismissProgressDialog();
                            Toast.makeText(AddPageActivity.this,"Profile Photo uploading error",Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
    private void upload_cover_pic(){
        if(filepath!=null){
            // final ProgressDialog progressdialogue=new ProgressDialog(this);
            //  progressdialogue.setTitle("loading...");
            //  progressdialogue.show();

            coverkey = UUID.randomUUID().toString();
            StorageReference ref = mStorageRef.child("PageImages/"+coverkey);
            ref.putFile(filepath)

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // progressdialogue.dismiss();
                            // dismissProgressDialog();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressdialogue.dismiss();
                            // dismissProgressDialog();
                            Toast.makeText(AddPageActivity.this,"cover Photo uploading error",Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }





}
