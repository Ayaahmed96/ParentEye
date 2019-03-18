package com.example.parenteye;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<custom_posts_returned> {
    int count=0;
    ArrayList<custom_posts_returned> post_returnedd;
    public PostAdapter(Activity context, ArrayList<custom_posts_returned> post_returned){

        super(context,0,post_returned);
        post_returnedd=post_returned;
      /*  for(custom_posts_returned pest:post_returned){
            System.out.println("name is "+pest.getPost_owner_name());
            System.out.println("profile is "+pest.getProfile_image());
            System.out.println("img post is "+pest.getPost_image());
            System.out.println("text is "+pest.getPost_text());
        }
        System.out.println(" new size is "+post_returned.size());
        */
    }

    @Override
    public int getCount() {
        System.out.println("new size is "+post_returnedd.size());
        return post_returnedd.size();

    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View postlist=convertView;
        if(postlist==null){
            postlist = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_posts, parent, false);

       }

        custom_posts_returned post= getItem(position);
        count=count+1;
        TextView postownername=(TextView) postlist.findViewById(R.id.postowner);
        postownername.setText(post.getPost_owner_name());
        TextView postDescription=(TextView) postlist.findViewById(R.id.postDescription);
        if(post.getPost_text()!=null){
            // postDescription.setDrawingCacheEnabled(false);
            //postDescription.destroyDrawingCache();
            //postDescription.setText(null);
            postDescription.setVisibility(View.VISIBLE);
          //  System.out.println("post desc is "+post.getPost_text());

            postDescription.setText(post.getPost_text());

        }
        else {
            postDescription.setVisibility(View.GONE);
            // postDescription.setText("Empty");
        }

        TextView postdate=(TextView) postlist.findViewById(R.id.postdate);
        postdate.setText("1/1/2001");
        // postdate.setText(post.getPost_date().toString());

        ImageView profileimage=(ImageView) postlist.findViewById(R.id.profile_post);
        ImageView postimage=(ImageView) postlist.findViewById(R.id.post_image);

        if(post.hasprofieimage()==true) {
            // profileimage.setImageResource(post.getProfile_image());
            //  img.setVisibility(View.VISIBLE);
            // profileimage.setVisibility(View.GONE);
            profileimage.setImageBitmap(post.getProfile_image());
        }
        else{
            profileimage.setImageResource(R.drawable.defaultprofile);

        }
        if(post.haspostimage()==true){
            postimage.setVisibility(View.VISIBLE);
            postimage.setImageBitmap(post.getPost_image());

        }
        else{

            // postimage.setImageResource(R.drawable.defaultprofile);
            postimage.setVisibility(View.GONE);
        }




        return postlist;


    }

}
