package com.example.parenteye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class MakeGroupActivity extends AppCompatActivity {
    private ImageView coverphoto;
    private CircleImageView profilephoto;
    private EditText groupName;
    private EditText aboutGroup;
    private Button makeGroup;
    private Integer PICK_IMAGE_REQUEST = 100;
    private Integer PICK_IMAGE = 101;
    private Uri filepath;
    private Uri filepath2;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private String profilekey = null;
    private String coverkey = null;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Community");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_group);
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        coverphoto = (ImageView) findViewById(R.id.groupCover);
        profilephoto = (CircleImageView) findViewById(R.id.groupProfile);
        groupName = (EditText) findViewById(R.id.groupname);
        aboutGroup = (EditText) findViewById(R.id.aboutgroup);
        makeGroup = (Button) findViewById(R.id.makeGroup);

        coverphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select image"), PICK_IMAGE_REQUEST);
            }
        });


        profilephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select image"), PICK_IMAGE);
            }
        });

        makeGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null) {
                    String group_name = groupName.getText().toString();
                    String group_about = aboutGroup.getText().toString();
                    if (!TextUtils.isEmpty(group_name) || !TextUtils.isEmpty(group_about)) {
                        Community group = new Community();
                        group.setCommunityname(group_name);
                        group.setCommunityAbout(group_about);
                        group.setTypeid("1");
                        group.setAdminId(mAuth.getCurrentUser().getUid());
                        upload_profile_pic();
                        upload_cover_pic();
                        if (profilekey != null) {
                            group.setPhotoId(profilekey);
                        }
                        if (coverkey != null) {
                            group.setCoverPhotoId(coverkey);
                        }
                        myRef.push().setValue(group).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MakeGroupActivity.this, "Your Group Created Successfully", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(MakeGroupActivity.this, "Error !! " + task.getException(), Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    } else {
                        Toast.makeText(MakeGroupActivity.this, "These fields are required", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                filepath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                    coverphoto.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();

                }
            } else if (requestCode == PICK_IMAGE) {
                filepath2 = data.getData();
                try {
                    Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath2);
                    profilephoto.setImageBitmap(bitmap2);

                } catch (IOException e) {
                    e.printStackTrace();

                }

            }
        }


    }


    private void upload_profile_pic() {
        if (filepath2 != null) {
            // final ProgressDialog progressdialogue=new ProgressDialog(this);
            //  progressdialogue.setTitle("loading...");
            //  progressdialogue.show();

            profilekey = UUID.randomUUID().toString();
            StorageReference ref = mStorageRef.child("GroupImages/" + profilekey);
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
                            Toast.makeText(MakeGroupActivity.this, "Profile Photo uploading error", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void upload_cover_pic() {
        if (filepath != null) {
            // final ProgressDialog progressdialogue=new ProgressDialog(this);
            //  progressdialogue.setTitle("loading...");
            //  progressdialogue.show();

            coverkey = UUID.randomUUID().toString();
            StorageReference ref = mStorageRef.child("GroupImages/" + coverkey);
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
                            Toast.makeText(MakeGroupActivity.this, "cover Photo uploading error", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }




}