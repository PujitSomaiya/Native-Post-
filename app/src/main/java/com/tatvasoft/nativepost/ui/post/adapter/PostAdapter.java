package com.tatvasoft.nativepost.ui.post.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.tatvasoft.nativepost.R;
import com.tatvasoft.nativepost.interfaces.RecyclerInterface;
import com.tatvasoft.nativepost.ui.post.model.PostResponseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostAdapter extends PagedListAdapter<PostResponseModel.HitsItem, PostAdapter.UserViewHolder> {
    public PostAdapter(RecyclerInterface recyclerInterface) {
        super(USER_COMPARATOR);
        this.recyclerInterface = recyclerInterface;

    }
    private RecyclerInterface recyclerInterface;
    private int checkNumber = 0;

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_post, parent, false);
        return new UserViewHolder(view, recyclerInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        Date sourceDate = new Date();
        try {
            sourceDate = dateFormat.parse(getItem(position).getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
        holder.tvStoryId.setText("Story id: " + getItem(position).getObjectID());
        holder.tvCreated.setText("Created At: " + targetFormat.format(sourceDate));
        holder.tvTitle.setText("Title: " + getItem(position).getTitle());
        holder.tvAuthor.setText("Author: " + getItem(position).getAuthor());
        holder.swActivation.setOnClickListener(view -> {
            if (holder.swActivation.isChecked()) {
                checkNumber++;
                recyclerInterface.onItemClick(checkNumber);
                getItem(position).setCheck(true);
            } else {
                checkNumber--;
                recyclerInterface.onItemClick(checkNumber);
                getItem(position).setCheck(false);
            }
        });
        if (getItem(position).isCheck()) {
            holder.swActivation.setChecked(true);
        } else {
            holder.swActivation.setChecked(false);
        }

    }

    static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvCreated, tvAuthor, tvPageNumber, tvStoryId;
        SwitchMaterial swActivation;
        RecyclerInterface recyclerInterface;
        ConstraintLayout clMain;


        UserViewHolder(@NonNull View itemView, RecyclerInterface recyclerInterface) {
            super(itemView);
            this.recyclerInterface = recyclerInterface;
            tvCreated = itemView.findViewById(R.id.tvCreated);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvStoryId = itemView.findViewById(R.id.tvStoryId);
            clMain = itemView.findViewById(R.id.clMain);
            tvPageNumber = itemView.findViewById(R.id.tvPageNumber);
            swActivation = itemView.findViewById(R.id.swActivation);
        }

    }

    private static final DiffUtil.ItemCallback<PostResponseModel.HitsItem> USER_COMPARATOR = new DiffUtil.ItemCallback<PostResponseModel.HitsItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull PostResponseModel.HitsItem oldItem, @NonNull PostResponseModel.HitsItem newItem) {
            return oldItem.getObjectID().equals(newItem.getObjectID());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull PostResponseModel.HitsItem oldItem, @NonNull PostResponseModel.HitsItem newItem) {
            return oldItem == newItem;
        }
    };
}
