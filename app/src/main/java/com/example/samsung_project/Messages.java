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

    public void New(View view) {
        Intent intent = new Intent(this, News.class);
        startActivity(intent);
        this.finish();
    }

    public void Mes(View view) {
        Intent intent = new Intent(this, Messages.class);
        startActivity(intent);
        this.finish();
    }

    public void Prof(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        this.finish();
    }
}
