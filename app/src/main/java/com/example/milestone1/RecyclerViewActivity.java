package com.example.milestone1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {


    public static final int ITEM_STATE_NEW = -1;
    public static final int IMAGE_CHOOSER = 1;

    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Item> mItemList;
    private Toolbar mToolBar;
    private String mSelectedImage = null;
    private TextView mNoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        setTitle("Recycler View");

        setupToolBar();
        setupFloatingActionButton();
        setupRecyclerView();

    }

    private void setupFloatingActionButton() {
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupDialog(ITEM_STATE_NEW);
            }
        });
    }

    private void setupToolBar() {
        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
    }

    ImageView imageTobeSaved;
    private void setupDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RecyclerViewActivity.this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.add_item_layout, null);

        final EditText dialogName = view.findViewById(R.id.editTextName);
        final EditText dialogEmail = view.findViewById(R.id.editTextEmail);
        final TextView uploadBtn = view.findViewById(R.id.uploadImage);
        imageTobeSaved = view.findViewById(R.id.uploadedImage);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        if (position != ITEM_STATE_NEW) {
            Item item = mItemList.get(position);
            dialogName.setText(item.getmName());
            dialogEmail.setText(item.getmEmail());
            if(item.getmImage() != null)
                imageTobeSaved.setImageBitmap(Helper.decodeBimap(item.getmImage()));
        }
        final AlertDialog alertDialog;
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
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getStringFromEditText(dialogName);
                String email = getStringFromEditText(dialogEmail);
                boolean flag = true;
                if (name.equals("")) {
                    dialogName.setError("Name is required.");
                    flag = false;
                }
                if (email.equals("")) {
                    dialogEmail.setError("Email is required.");
                    flag = false;
                }

                if (flag) {

                    if (position != ITEM_STATE_NEW) {
                        mItemList.get(position).setmName(name);
                        mItemList.get(position).setmEmail(email);
                        mItemList.get(position).setmImage(mSelectedImage);
                        mAdapter.notifyItemChanged(position);
                    } else {
                        mItemList.add(new Item(name, email, mSelectedImage));
                        mAdapter.notifyItemInserted(mItemList.size() - 1);
                    }
                    mNoItems.setVisibility(View.GONE);
                    mSelectedImage = null;
                    alertDialog.dismiss();
                }
            }
        });
    }

    private void setupRecyclerView() {

        mNoItems = findViewById(R.id.noItems);
        mNoItems.setVisibility(View.GONE);

        mItemList = new ArrayList<>();
        mItemList.add(new Item("Afraz", "afraz@gmail.com", null));
        mItemList.add(new Item("Junaid", "junaid@gmail.com", null));
        mItemList.add(new Item("Umair", "umair@gmail.com", null));

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
                if(mItemList.isEmpty())
                    mNoItems.setVisibility(View.VISIBLE);
            }

            @Override
            public void onEditClick(int position) {
                setupDialog(position);
            }
        });
    }

    private void imageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_CHOOSER);
    }

    private String getStringFromEditText(EditText editText) {
        return editText.getText().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.dialogAdd:
                setupDialog(ITEM_STATE_NEW);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CHOOSER && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageTobeSaved.setImageURI(data.getData());
            final Uri imageUri = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            mSelectedImage = Helper.encodeBitmap(selectedImage);
        }
    }


}
