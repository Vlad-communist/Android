package com.example.samsung_project;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Messages extends AppCompatActivity {
    public String key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages);
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        System.out.println(width);
        width /= 5;
        System.out.println(width);
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

        Cursor c = db.query("sq", null, null, null, null, null, null);
        c.moveToNext();
        key = c.getString(1);
        c.close();
        try {
            Create_Chats();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        getSupportActionBar().hide();
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
        overridePendingTransition(R.anim.top, R.anim.top1);
        this.finish();
    }

    public void Prof(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left, R.anim.left1);
        this.finish();
    }

    public void Create_Chats() throws IOException, JSONException {
        URL url = new URL("http://vsn.intercom.pro:9080/all_messages/" + key);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String photo = "";
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();
            String g = response.toString();
            JSONObject js = new JSONObject(g);
            System.out.println(123);
            JSONObject content = js.getJSONObject("answer");
            LinearLayout layout = (LinearLayout) findViewById(R.id.ln);
            for (int i = 0; i < content.length(); i++){
                TextView ed = new TextView(getApplicationContext());
                ed.setText((content.getJSONArray(Integer.toString(i))).getString(1));
                layout.addView(ed);
            }
//            for (int i = 0; i < content.length(); )
        }
    }

    public class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table sq ("
                    + "id integer primary key autoincrement,"
                    + "yes text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
