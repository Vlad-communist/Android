package com.example.samsung_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.KeyEventDispatcher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Insets;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.InputStream;


public class News extends AppCompatActivity {
    public int current_im = 0;
    public int in_block = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getSupportActionBar().hide();
        ScrollView scrollView = (ScrollView) findViewById(R.id.lent);
        Next_posts();
        Next_posts();
        Next_posts();
        Next_posts();
        Next_posts();
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

//    @SuppressLint("SetTextI18n")
    @SuppressLint("SetTextI18n")
    public void Next_posts() {
        current_im++;
        in_block = 0;

        ImageView im = new ImageView(getApplicationContext());
        LinearLayout frameLayout = (LinearLayout) findViewById(R.id.ln);
        new DownloadImageTask(im).execute("https://images-ext-1.discordapp.net/external/qyfnjk5ZErAzQAqoFsKKmWoCdHisH_Kh4tBCFn0k940/%3Fsize%3D660x660%26quality%3D96%26sign%3De6467d23fd76b8cd213f681e7465e330%26type%3Dalbum/https/sun9-21.userapi.com/impg/3Z8gyexEsZRZu3Vg-NxyMXcNpkUXuLBNX5NIlg/i2z774wn3i8.jpg");
//        new DownloadImageTask(im).execute("https://images-ext-1.discordapp.net/external/qyfnjk5ZErAzQAqoFsKKmWoCdHisH_Kh4tBCFn0k940/%3Fsize%3D660x660%26quality%3D96%26sign%3De6467d23fd76b8cd213f681e7465e330%26type%3Dalbum/https/sun9-21.userapi.com/impg/3Z8gyexEsZRZu3Vg-NxyMXcNpkUXuLBNX5NIlg/i2z774wn3i8.jpg" + current_im + ".png");
//        LinearLayout linLayout = new LinearLayout(this);
        LinearLayout linLayout = new LinearLayout(getApplicationContext());
        linLayout.setOrientation(LinearLayout.VERTICAL);
        // создаем LayoutParams
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        linLayoutParam.setMargins(0, 0, 0, 20);

        linLayout.setLayoutParams(linLayoutParam);
//        linLayout.setBackgroundResource(R.drawable.style_for_news);
        linLayout.setPadding(20, 20, 20, -3);

        View line1 = new View(getApplicationContext());
        View line2 = new View(getApplicationContext());
        ViewGroup.LayoutParams g = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
        line1.setLayoutParams(g);
        line2.setLayoutParams(g);
        line1.setBackgroundResource(R.drawable.hz_kakaja_to_parasha);
        line2.setBackgroundResource(R.drawable.hz_kakaja_to_parasha);
        im.setPadding(0, 40, 0, 0);
        line1.setPadding(0, 0, 0, 0);


        TextView textView = new TextView(getApplicationContext());
//        TextView textView = new TextView(this);
//        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(Integer.toString(current_im) + "\naboba aboba aboba aboba aboba aboba " +
                "aboba aboba aboba aboba aboba aboba aboba aboba aboba aboba aboba aboba");
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setGravity(Gravity.FILL_VERTICAL | Gravity.BOTTOM);
        linLayout.setGravity(Gravity.FILL_VERTICAL);

        linLayout.setId(current_im);
        in_block++;
        im.setId(in_block);
        in_block++;
        textView.setId(in_block);
        in_block++;
        line1.setId(in_block);
        in_block++;
        line2.setId(in_block);

        linLayout.addView(line1);
        linLayout.addView(im);
        linLayout.addView(textView);
        linLayout.addView(line2);

        frameLayout.addView(linLayout);
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

    //////////////////////////загрузка изображений с сервака//////////////////////
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                System.out.println(e);
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    ////////////////////////////////////КОНЕЦ////////////////////////////////////

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

}