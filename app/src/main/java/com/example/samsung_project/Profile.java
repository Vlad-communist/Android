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
    }

    public void Back_from_profile(View view){
        Intent intent = new Intent(this, SecondActivity.class);
        this.finish();
        startActivity(intent);
    }
}
