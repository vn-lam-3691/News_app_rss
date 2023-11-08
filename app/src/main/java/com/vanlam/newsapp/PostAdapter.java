package com.vanlam.newsapp;

import android.annotation.SuppressLint;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> postList;
    private PostListener listener;

    public PostAdapter(List<Post> postList, PostListener listener) {
        this.postList = postList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Post item = postList.get(position);
        holder.getTvTitle().setText(item.getTitle());
        holder.getTvDesc().setText(item.getDescription());

        Picasso.get().load(item.getImageUrl()).into(holder.getImagePost());

        holder.getLayoutPost().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDesc;
        private RoundedImageView imagePost;
        private ConstraintLayout layoutPost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title_post_item);
            tvDesc = itemView.findViewById(R.id.tv_description);
            imagePost = itemView.findViewById(R.id.image_post_item);
            layoutPost = itemView.findViewById(R.id.layout_container_post_item);
        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public void setTvTitle(TextView tvTitle) {
            this.tvTitle = tvTitle;
        }

        public TextView getTvDesc() {
            return tvDesc;
        }

        public void setTvDesc(TextView tvDesc) {
            this.tvDesc = tvDesc;
        }

        public RoundedImageView getImagePost() {
            return imagePost;
        }

        public void setImagePost(RoundedImageView imagePost) {
            this.imagePost = imagePost;
        }

        public ConstraintLayout getLayoutPost() {
            return layoutPost;
        }

        public void setLayoutPost(ConstraintLayout layoutPost) {
            this.layoutPost = layoutPost;
        }
    }
}
