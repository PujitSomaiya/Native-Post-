package com.tatvasoft.nativepost.ui.post.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.tatvasoft.nativepost.R;
import com.tatvasoft.nativepost.interfaces.RecyclerInterface;
import com.tatvasoft.nativepost.ui.post.model.PostResponseModel;

public class PostAdapter extends PagedListAdapter<PostResponseModel.HitsItem, PostAdapter.UserViewHolder> {
    public PostAdapter(Context context, RecyclerInterface recyclerInterface) {
        super(USER_COMPARATOR);
        this.context=context;
        this.recyclerInterface=recyclerInterface;
    }

    private int checkNumber = 0;
    private  Context context;
    private  RecyclerInterface recyclerInterface;

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_post, parent, false);
        return new UserViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.tvStoryId.setText("Story id: " + getItem(position).getObjectID());
        holder.tvCreated.setText("Created At: " + getItem(position).getCreatedAt());
        holder.tvTitle.setText("Title: " + getItem(position).getTitle());
        holder.tvAuthor.setText("Author: " + getItem(position).getAuthor());
        holder.swActivation.setOnClickListener(view -> {
            if (holder.swActivation.isChecked()) {
                removeAt(position);
                holder.swActivation.setChecked(false);
                if (holder.chkSelected.isChecked()) {
                    checkNumber--;
                    holder.chkSelected.setChecked(false);
                    recyclerInterface.onItemClick(checkNumber);
                    holder.clMain.setBackgroundColor(res.getColor(R.color.colorWhite));
                }
                Toast.makeText(context, "Post Disabled: " + getItem(position).getObjectID(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.clMain.setOnLongClickListener(view -> {
            if (holder.chkSelected.isChecked()) {
                checkNumber--;
                holder.chkSelected.setChecked(false);
                holder.clMain.setBackgroundColor(res.getColor(R.color.colorWhite));
            } else {
                holder.chkSelected.setChecked(true);
                checkNumber++;
                holder.clMain.setBackgroundColor(res.getColor(R.color.design_default_color_error));
            }
            recyclerInterface.onItemClick(checkNumber);
            return false;
        });
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCreated, tvAuthor, tvPageNumber, tvStoryId;
        SwitchMaterial swActivation;
        MaterialCheckBox chkSelected;
        ConstraintLayout clMain;

        UserViewHolder(@NonNull View itemView) {
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
    public void removeAt(int position) {
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }
}
