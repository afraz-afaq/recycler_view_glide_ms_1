package com.example.milestone1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GlideAdapter extends RecyclerView.Adapter<GlideAdapter.GlideViewHolder> {

    private ArrayList<String> mImageList;
    private Context mContext;
    private OnItemClickListener mListener;

    public GlideAdapter(ArrayList<String> mImageList, Context mContext) {
        this.mImageList = mImageList;
        this.mContext = mContext;
    }

    public interface OnItemClickListener{

        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class GlideViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        public GlideViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public GlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.glide_item,parent,false);
        GlideViewHolder glideViewHolder = new GlideViewHolder(view,mListener);
        return glideViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GlideViewHolder holder, int position) {

        String url = mImageList.get(position);

        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.glide_loading)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }
}
