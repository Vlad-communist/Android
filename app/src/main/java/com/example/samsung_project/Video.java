package com.example.samsung_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Video extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        getSupportActionBar().hide();

        Display display = getWindowManager().getDefaultDisplay();
        int width_of_screen = display.getWidth();
        int height_of_screen = display.getHeight();
        int h_proc = height_of_screen / 100;
        int w_proc = width_of_screen / 100;
//        ImageButton button_home = (ImageButton) findViewById(R.id.home);
//        ImageButton button_video = (ImageButton) findViewById(R.id.video);
//        ImageButton button_message = (ImageButton) findViewById(R.id.messages);
//        ImageButton button_friends = (ImageButton) findViewById(R.id.friends);
//        ImageButton button_me = (ImageButton) findViewById(R.id.me);
//        ViewGroup.LayoutParams params_home = button_home.getLayoutParams();
//        ViewGroup.LayoutParams params_video = button_video.getLayoutParams();
//        ViewGroup.LayoutParams params_messages = button_message.getLayoutParams();
//        ViewGroup.LayoutParams params_friends = button_friends.getLayoutParams();
//        ViewGroup.LayoutParams params_me = button_me.getLayoutParams();
//        params_home.width = width;
//        params_video.width = width;
//        params_messages.width = width;
//        params_friends.width = width;
//        params_me.width = width;
//        params_home.height = 150;
//        params_video.height = 150;
//        params_messages.height = 150;
//        params_friends.height = 150;
//        params_me.height = 150;
//        button_home.setLayoutParams(params_home);
//        button_video.setLayoutParams(params_video);
//        button_message.setLayoutParams(params_messages);
//        button_friends.setLayoutParams(params_friends);
//        button_me.setLayoutParams(params_me);
    }

    public void New(View view) {
        Intent intent = new Intent(this, News.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right, R.anim.right1);
        this.finish();
    }

    public void Mes(View view) {
        Intent intent = new Intent(this, Messages.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left, R.anim.left1);
        this.finish();
    }

    public void Prof(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left, R.anim.left1);
        this.finish();
    }

    public void Video(View view){
        Intent intent = new Intent(this, Video.class);
        startActivity(intent);
        overridePendingTransition(R.anim.top, R.anim.top1);
        this.finish();
    }

    public void Friends(View view){
        Intent intent = new Intent(this, Friends.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left, R.anim.left1);
        this.finish();
    }
}
