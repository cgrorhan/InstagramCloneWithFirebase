package com.cagriorhan.instaclonefirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedRecylerAdapter extends RecyclerView.Adapter<FeedRecylerAdapter.PostHolder> {

    private ArrayList<String> userEmailList;
    private ArrayList<String> userCommentList;
    private ArrayList<String> userImageList;

    public FeedRecylerAdapter(ArrayList<String> userEmailList, ArrayList<String> userCommentList, ArrayList<String> userImageList) {
        this.userEmailList = userEmailList;
        this.userCommentList = userCommentList;
        this.userImageList = userImageList;
    }


    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recyler_row,parent,false);
        return new PostHolder(view);
    }

    @Override
    public int getItemCount() {
        return userEmailList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.userEmailText.setText(userEmailList.get(position));
        holder.userCommentText.setText(userCommentList.get(position));
        Picasso.get().load(userImageList.get(position)).into(holder.imageView);

    }

    class PostHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView userEmailText;
        TextView userCommentText;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.recyler_row_imageview);
            userCommentText=itemView.findViewById(R.id.recyler_row_comment_text);
            userEmailText=itemView.findViewById(R.id.recyler_row_usermail_text);














        }
    }
}
