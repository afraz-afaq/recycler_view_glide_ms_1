package com.example.milestone1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;

public class GlideActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GlideAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> mImageList;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        setTitle("Glide");
        setupToolBar();
        setupRecyclerView();
    }

    private void setupToolBar(){
        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
    }

    private void setupRecyclerView(){
        mImageList = new ArrayList<>();
        mImageList.add("https://images4.alphacoders.com/815/815287.png");
        mImageList.add("https://images.alphacoders.com/937/thumb-1920-937823.jpg");
        mImageList.add("https://images7.alphacoders.com/102/1028609.png");
        mImageList.add("https://images8.alphacoders.com/931/931972.png");
        mImageList.add("https://images7.alphacoders.com/302/302490.jpg");

        mRecyclerView = findViewById(R.id.glideRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new GlideAdapter(mImageList,GlideActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new GlideAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mImageList.get(position)));
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });
    }
}
