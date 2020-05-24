package com.example.milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void navigate(View view){
        Button btn = (Button)view;
        switch (btn.getText().toString()){
            case "Recycler View":
                    startActivity(new Intent(MainActivity.this,RecyclerViewActivity.class));
                break;
            case "glide":
                    startActivity(new Intent(MainActivity.this,GlideActivity.class));
                break;
        }
    }
}
