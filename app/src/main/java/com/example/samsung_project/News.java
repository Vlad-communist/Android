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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class News extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        Next_posts();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy) ;
        ScrollView scrollView = (ScrollView) findViewById(R.id.lent);
        scrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (scrollView.getChildAt(0).getBottom()
                                <= (scrollView.getHeight() + scrollView.getScrollY())) {
                            Next_posts();
                        }
                    }
                });
    }
    public void Next_posts() {
        TextView text = (TextView)findViewById(R.id.textview);
        text.setText(text.getText().toString() + "здесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текстздесь должен быть длинный текст");
    }
    class DBHelper extends SQLiteOpenHelper {

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
    class Lent_post {
        String text;
        void Lent_post(String text){
            this.text = text;
        }
    }

}
