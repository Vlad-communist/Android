package com.example.samsung_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Messages extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages);
        getSupportActionBar().hide();
    }

    public void Back_from_messages(View view){
        Intent intent = new Intent(this, SecondActivity.class);
        this.finish();
        startActivity(intent);
    }
}
