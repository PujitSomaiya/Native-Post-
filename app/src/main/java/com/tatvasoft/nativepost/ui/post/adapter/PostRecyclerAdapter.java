package com.tatvasoft.nativepost.ui.post.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.tatvasoft.nativepost.R;
import com.tatvasoft.nativepost.interfaces.RecyclerInterface;
import com.tatvasoft.nativepost.ui.post.model.PostResponseModel;
import java.util.List;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.PostViewHolder> {

    private Context context;
    private List<PostResponseModel.HitsItem> postsList;
    private PostResponseModel post;
    private final  RecyclerInterface recyclerViewItemClick;
    private int checkNumber=0;

    public PostRecyclerAdapter(Context context, PostResponseModel post, List<PostResponseModel.HitsItem> postsList, RecyclerInterface recyclerViewItemClick) {
        this.context = context;
        this.postsList = postsList;
        this.post = post;
        this.recyclerViewItemClick = recyclerViewItemClick;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_post, parent, false);
        return new PostViewHolder(view);
    }


    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.tvPageNumber.setText("Page No.: " + post.getPage());
        holder.tvStoryId.setText("Story id: " + postsList.get(position).getObjectID());
        holder.tvCreated.setText("Created At: " + postsList.get(position).getCreatedAt());
        holder.tvTitle.setText("Title: " + postsList.get(position).getTitle());
        holder.tvAuthor.setText("Author: " + postsList.get(position).getAuthor());
        holder.swActivation.setOnClickListener(view -> {
            if (holder.swActivation.isChecked()) {
                postsList.remove(position);
                notifyDataSetChanged();
                holder.swActivation.setChecked(false);
                if (holder.chkSelected.isChecked()){
                    checkNumber--;
                    holder.chkSelected.setChecked(false);
                    recyclerViewItemClick.onItemClick(checkNumber);
                    holder.clMain.setBackgroundColor(res.getColor(R.color.colorWhite));
                }
                Toast.makeText(context, "Post Disabled: " + postsList.get(position).getObjectID(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.clMain.setOnLongClickListener(view -> {
            if (holder.chkSelected.isChecked()){
                checkNumber--;
                holder.chkSelected.setChecked(false);
                holder.clMain.setBackgroundColor(res.getColor(R.color.colorWhite));
            }else {
                holder.chkSelected.setChecked(true);
                checkNumber++;
                holder.clMain.setBackgroundColor(res.getColor(R.color.design_default_color_error));
            }
            recyclerViewItemClick.onItemClick(checkNumber);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public void updateData( List<PostResponseModel.HitsItem> posts){
        postsList.addAll(posts);
    }

    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvCreated, tvAuthor, tvPageNumber, tvStoryId;
        SwitchMaterial swActivation;
        MaterialCheckBox chkSelected;
        ConstraintLayout clMain;


        PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCreated = itemView.findViewById(R.id.tvCreated);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvStoryId = itemView.findViewById(R.id.tvStoryId);
            chkSelected = itemView.findViewById(R.id.chkSelected);
            clMain = itemView.findViewById(R.id.clMain);
            tvPageNumber = itemView.findViewById(R.id.tvPageNumber);
            swActivation = itemView.findViewById(R.id.swActivation);
        }


        @Override
        public void onClick(View view) {
            view.getId();
            {
                if (chkSelected.isChecked()){
                    checkNumber--;
                }else {
                    checkNumber++;
                }

            }
        }
    }
}


