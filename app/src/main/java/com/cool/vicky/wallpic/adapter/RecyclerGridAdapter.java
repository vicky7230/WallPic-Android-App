package com.cool.vicky.wallpic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cool.vicky.wallpic.R;
import com.cool.vicky.wallpic.activity.WallpaperDetailActivity;
import com.cool.vicky.wallpic.pojo.Hit;
import com.cool.vicky.wallpic.util.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by vicky on 3/14/16.
 */
public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.ViewHolder> {
    private List<Hit> wallpaperList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextView;
        public TextView hashId;

        public ViewHolder(FrameLayout v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.grid_item_title);
            mImageView = (ImageView) v.findViewById(R.id.grid_item_image);
            hashId = (TextView) v.findViewById(R.id.hash_id);
        }
    }

    public RecyclerGridAdapter(Context context, List<Hit> wallpaperList) {
        this.wallpaperList = wallpaperList;
        this.context = context;
    }

    @Override
    public RecyclerGridAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent,
                                                             int viewType) {
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_layout, parent, false);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hashId = ((TextView) v.findViewById(R.id.hash_id)).getText().toString();
                ImageView wallpaperThumbnail = (ImageView) v.findViewById(R.id.grid_item_image);
                if (wallpaperThumbnail.getDrawable() != null) {
                    Intent intent = new Intent(context, WallpaperDetailActivity.class);
                    intent.putExtra(Constants.ID, hashId);
                    context.startActivity(intent);
                }
            }
        });
        ViewHolder viewHolder = new ViewHolder(frameLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextView.setText(wallpaperList.get(position).getUser());
        holder.hashId.setText(wallpaperList.get(position).getIdHash());
        Glide.with(context.getApplicationContext()).load(wallpaperList.get(position).getWebformatURL().replace("_640", "_340"))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }
}