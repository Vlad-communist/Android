package com.example.samsung_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Friends extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);
        getSupportActionBar().hide();

        Display display = getWindowManager().getDefaultDisplay();
        int width_of_screen = display.getWidth();
        int height_of_screen = display.getHeight();
        int h_proc = height_of_screen / 100;
        int w_proc = width_of_screen / 100;
        int width = width_of_screen;

        width /= 5;
        ImageButton button_home = (ImageButton) findViewById(R.id.home);
        ImageButton button_video = (ImageButton) findViewById(R.id.video);
        ImageButton button_message = (ImageButton) findViewById(R.id.messages);
        ImageButton button_friends = (ImageButton) findViewById(R.id.friends);
        ImageButton button_me = (ImageButton) findViewById(R.id.me);
        ViewGroup.LayoutParams params_home = button_home.getLayoutParams();
        ViewGroup.LayoutParams params_video = button_video.getLayoutParams();
        ViewGroup.LayoutParams params_messages = button_message.getLayoutParams();
        ViewGroup.LayoutParams params_friends = button_friends.getLayoutParams();
        ViewGroup.LayoutParams params_me = button_me.getLayoutParams();
        params_home.width = width;
        params_video.width = width;
        params_messages.width = width;
        params_friends.width = width;
        params_me.width = width;
        params_home.height = 150;
        params_video.height = 150;
        params_messages.height = 150;
        params_friends.height = 150;
        params_me.height = 150;
        button_home.setLayoutParams(params_home);
        button_video.setLayoutParams(params_video);
        button_message.setLayoutParams(params_messages);
        button_friends.setLayoutParams(params_friends);
        button_me.setLayoutParams(params_me);

        LinearLayout mainlayaput = (LinearLayout) findViewById(R.id.ln);
        ScrollView mainscroll = (ScrollView) findViewById(R.id.lent);

        TextView t = new TextView(getApplicationContext());
        LinearLayout.LayoutParams t_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        t_params.topMargin = w_proc * 4;
        t_params.leftMargin = w_proc * 8;
        t_params.gravity = Gravity.START;
        t.setLayoutParams(t_params);
        t.setText("Друзья");
        t.setTextColor(Color.parseColor("#ffffff"));
        t.setTextSize(w_proc * 2);

        mainlayaput.addView(t);

        LinearLayout search = new LinearLayout(getApplicationContext());
//            new DownloadImageTask(logo).execute("https://images-ext-1.discordapp.net/external/qyfnjk5ZErAzQAqoFsKKmWoCdHisH_Kh4tBCFn0k940/%3Fsize%3D660x660%26quality%3D96%26sign%3De6467d23fd76b8cd213f681e7465e330%26type%3Dalbum/https/sun9-21.userapi.com/impg/3Z8gyexEsZRZu3Vg-NxyMXcNpkUXuLBNX5NIlg/i2z774wn3i8.jpg");
        LinearLayout.LayoutParams search_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        search.setLayoutParams(search_params);
        CardView circled_search = new CardView(getApplicationContext());
        LinearLayout.LayoutParams circle_im_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, w_proc * 10);
        circle_im_params.gravity = Gravity.CENTER;
        circle_im_params.leftMargin = w_proc * 4;
        circle_im_params.rightMargin = w_proc * 4;
        circle_im_params.topMargin = w_proc * 4;
        circled_search.setLayoutParams(circle_im_params);
        circled_search.setRadius(w_proc * 25 / 10);
        circled_search.setContentPadding(0, 0, 0, 0);
        circled_search.setCardBackgroundColor(Color.parseColor("#555555"));
        circled_search.addView(search);

        mainlayaput.addView(circled_search);


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
        overridePendingTransition(R.anim.right, R.anim.right1);
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
        overridePendingTransition(R.anim.right, R.anim.right1);
        this.finish();
    }

    public void Friends(View view){
        Intent intent = new Intent(this, Friends.class);
        startActivity(intent);
        overridePendingTransition(R.anim.top, R.anim.top1);
        this.finish();
    }
}
