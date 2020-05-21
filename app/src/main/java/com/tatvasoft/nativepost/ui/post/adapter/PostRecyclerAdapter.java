package com.tatvasoft.nativepost.ui.post.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatvasoft.nativepost.R;
import com.tatvasoft.nativepost.ui.post.model.PostResponseModel;

import java.util.List;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostViewHolder>{

    Context context;
    List<PostResponseModel.HitsItem> postsList;

    public PostRecyclerAdapter(Context context, List<PostResponseModel.HitsItem> postsList) {
        this.context = context;
        this.postsList = postsList;
    }
//    public void setPosts(List<PostResponseModel> posts){
//        this.postsList = posts;
//        notifyDataSetChanged();
//    }

//    public List<PostResponseModel> getPosts(){
//        return postsList;
//    }

//    public void updatePost(PostResponseModel post){
//        postsList.set(postsList.indexOf(post), post);
//        notifyItemChanged(postsList.indexOf(post));
//    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_post,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.tvCreated.setText(postsList.get(position).getCreatedAt());
        holder.tvTitle.setText(postsList.get(position).getTitle());
        holder.webPost.loadUrl(postsList.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }
}

class PostViewHolder extends RecyclerView.ViewHolder {
    TextView tvTitle,tvCreated;
    WebView webPost;

    PostViewHolder(@NonNull View itemView) {
        super(itemView);
        webPost=itemView.findViewById(R.id.webPost);
        tvCreated=itemView.findViewById(R.id.tvCreated);
        tvTitle=itemView.findViewById(R.id.tvTitle);

    }
}
