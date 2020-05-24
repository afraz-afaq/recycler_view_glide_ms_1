package com.example.milestone1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {


    public static int ITEM_STATE_NEW = -1;

    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Item> mItemList;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        setTitle("Recycler View");

        setupToolBar();
        setupFloatingActionButton();
        setupRecyclerView();

    }

    private void setupFloatingActionButton(){
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupDialog(ITEM_STATE_NEW);
            }
        });
    }

    private void setupToolBar(){
        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
    }

    private void setupDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(RecyclerViewActivity.this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.add_item_layout,null);

        final EditText dialogName = view.findViewById(R.id.editTextName);
        final EditText dialogEmail = view.findViewById(R.id.editTextEmail);

        if(position != ITEM_STATE_NEW){
            Item item = mItemList.get(position);
            dialogName.setText(item.getmName());
            dialogEmail.setText(item.getmEmail());
        }
        final AlertDialog  alertDialog;
        builder.setView(view)
                .setTitle("Add Item")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = getStringFromEditText(dialogName);
                String email = getStringFromEditText(dialogEmail);
                boolean flag = true;
                if(name.equals("")) {
                    dialogName.setError("Name is required.");
                    flag = false;
                }
                if(email.equals("")) {
                    dialogEmail.setError("Email is required.");
                    flag = false;
                }

                if(flag) {

                    if(position != ITEM_STATE_NEW){
                        mItemList.get(position).setmName(name);
                        mItemList.get(position).setmEmail(email);
                        mAdapter.notifyItemChanged(position);
                    }
                    else {
                        mItemList.add(new Item(name, email, null));
                        mAdapter.notifyItemInserted(mItemList.size() - 1);
                    }
                    alertDialog.dismiss();
                }
            }
        });
    }

    private void setupRecyclerView(){
        mItemList = new ArrayList<>();
        mItemList.add(new Item("Afraz","afraz@gmail.com",null));
        mItemList.add(new Item("Junaid","junaid@gmail.com",null));
        mItemList.add(new Item("Umair","umair@gmail.com",null));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ListAdapter(mItemList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                mItemList.remove(position);
                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onEditClick(int position) {
                setupDialog(position);
            }
        });
    }

    private String getStringFromEditText(EditText editText){
        return editText.getText().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.dialogAdd:
                setupDialog(ITEM_STATE_NEW);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
