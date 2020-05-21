package com.tatvasoft.nativepost.ui.post.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatvasoft.nativepost.R;
import com.tatvasoft.nativepost.ui.post.model.PostResponseModel;

import java.util.List;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private Context context;
    private List<PostResponseModel.HitsItem> postsList;

    public PostRecyclerAdapter(Context context, List<PostResponseModel.HitsItem> postsList) {
        this.context = context;
        this.postsList = postsList;
    }
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_post, parent, false);
        return new PostViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.tvCreated.setText("Created At: " + postsList.get(position).getCreatedAt());
        holder.tvTitle.setText("Title: " + postsList.get(position).getTitle());
        holder.webPost.setWebViewClient(new WebViewClient());
        holder.webPost.loadUrl(postsList.get(position).getUrl());
        holder.tvAuthor.setText("Author: " + postsList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

}

class PostViewHolder extends RecyclerView.ViewHolder {
    TextView tvTitle, tvCreated, tvAuthor;
    WebView webPost;

    PostViewHolder(@NonNull View itemView) {
        super(itemView);
        webPost = itemView.findViewById(R.id.webPost);
        tvCreated = itemView.findViewById(R.id.tvCreated);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvAuthor = itemView.findViewById(R.id.tvAuthor);
    }
}
