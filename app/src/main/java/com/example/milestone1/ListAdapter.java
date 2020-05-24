package com.example.milestone1;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private ArrayList<Item> mItemList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{

        void onItemClick(int position);
        void onDeleteClick(int position);
        void onEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public ListAdapter(ArrayList<Item> mItemList) {
        this.mItemList = mItemList;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageview;
        public TextView mName;
        public TextView mEmail;
        public ImageView mImageDelete;
        public ImageView mImageEdit;

        public ListViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            mImageview = itemView.findViewById(R.id.imageView);
            mName = itemView.findViewById(R.id.name);
            mEmail = itemView.findViewById(R.id.email);
            mImageDelete = itemView.findViewById(R.id.imageDelete);
            mImageEdit = itemView.findViewById(R.id.imageEdit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mImageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            mImageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onEditClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ListViewHolder listViewHolder = new ListViewHolder(v,mListener);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        Item item = mItemList.get(position);
        holder.mName.setText(item.getmName());
        holder.mEmail.setText(item.getmEmail());

        if(item.getmImage() == null)
            holder.mImageview.setImageResource(R.drawable.ic_face_black_24dp);

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
