package com.example.samsung_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ContentValues cv = new ContentValues();
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("sq", null, null, null, null, null, null);
        getSupportActionBar().hide();
        if (c.moveToFirst()) {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            setContentView(R.layout.activity_main);
        }
    }

    public void onMyButtonClick(View view) {
        String pp, ee;
        final EditText em = (EditText) findViewById(R.id.email);
        String email = em.getText().toString();
        final EditText pass = (EditText) findViewById(R.id.password);
        String password = pass.getText().toString();
        Intent intent = new Intent(this, SecondActivity.class);
        AsyncRequest a = new AsyncRequest();
        String ans = a.doInBackground(email, password);
        System.out.println(ans);
        if (ans.contains("not ok")) {
            Toast.makeText(this, "Введены неверные данные", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues cv = new ContentValues();
            DBHelper dbHelper = new DBHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor c = db.query("sq", null, null, null, null, null, null);
            cv.put("yes", ans);
            db.insert("sq", null, cv);
            cv.clear();
            startActivity(intent);
            this.finish();
        }
    }


//                pp = c.getString(c.getColumnIndex("password"));
//                ee = c.getString(c.getColumnIndex("email"));


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

    class AsyncRequest extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... arg) {
            String url = "http://vsn.intercom.pro/" + arg[0] + "/" + arg[1];
            System.out.println(url);
            StringBuffer response;
            try {
                URL obj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

                connection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            } catch (Exception e) {
                return e.toString();
            }
        }
    }
}