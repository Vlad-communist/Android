package com.example.samsung_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getSupportActionBar().hide();
    }

    public void Back_from_profile(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
        this.finish();
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
