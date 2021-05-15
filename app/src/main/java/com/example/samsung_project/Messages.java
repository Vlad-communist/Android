package com.example.samsung_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

    public void Create_Chats() throws IOException {
        URL url = new URL("http://example.com/?param=1");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println();
        } else {
            int a = 2;
        }
    }
}
