package com.example.samsung_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.KeyEventDispatcher;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Insets;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.Display;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class News extends AppCompatActivity {
    public int current_im = 1;
    public int in_block = 0;
    public String key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getSupportActionBar().hide();
        ScrollView scrollView = (ScrollView) findViewById(R.id.lent);
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("sq", null, null, null, null, null, null);
        c.moveToNext();
        c.close();
        key = c.getString(1);
        try {
            Next_posts();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        scrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (scrollView.getChildAt(0).getBottom()
                                <= (scrollView.getHeight() + scrollView.getScrollY())) {
                            try {
                                Next_posts();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    //    @SuppressLint("SetTextI18n")
    @SuppressLint("SetTextI18n")
    public void Next_posts() throws IOException, JSONException {
        URL url = new URL("http://vsn.intercom.pro:9080/new/" + key + "/" + current_im);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String content = "";
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
            content = js.getString("text");
            photo = js.getString("photo");
            System.out.println(photo);
        }
        if (content != "nope") {
            in_block = 0;
            LinearLayout frameLayout = (LinearLayout) findViewById(R.id.ln);
            LinearLayout linLayout = new LinearLayout(getApplicationContext());
            linLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linLayoutParam.setMargins(0, 0, 0, 20);
            linLayout.setLayoutParams(linLayoutParam);
            linLayout.setPadding(20, 20, 20, -3);
            View line1 = new View(getApplicationContext());
            View line2 = new View(getApplicationContext());
            ViewGroup.LayoutParams g = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
            line1.setLayoutParams(g);
            line2.setLayoutParams(g);
            line1.setBackgroundResource(R.drawable.hz_kakaja_to_parasha);
            line2.setBackgroundResource(R.drawable.hz_kakaja_to_parasha);
            line1.setPadding(0, 0, 0, 0);
            Display display = getWindowManager().getDefaultDisplay();
            int width = display.getWidth();  // deprecated
            TextView textView = new TextView(getApplicationContext());
            textView.setText(current_im + "\n" + content);
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            textView.setGravity(Gravity.FILL_VERTICAL | Gravity.BOTTOM);
            linLayout.setGravity(Gravity.FILL_VERTICAL);

            linLayout.setId(current_im);
            in_block++;
            line1.setId(in_block);
            in_block++;
            textView.setId(in_block);
            in_block++;
            line2.setId(in_block);
            linLayout.addView(line1);
            int count_fotos = 1;
            switch (count_fotos) {
                case 1:
                    ImageView im = new ImageView(getApplicationContext());
                    new DownloadImageTask(im).execute("http://vsn.intercom.pro:9080/image/" + photo);
                    ViewGroup.LayoutParams im_params = new ViewGroup.LayoutParams(width - 50, width - 50);
                    im.setLayoutParams(im_params);
                    im.setPadding(0, 20, 0, 0);
                    in_block++;
                    im.setId(in_block);
                    linLayout.addView(im);
                    break;
                case 2:

                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
            }
            linLayout.addView(textView);
            linLayout.addView(line2);
            frameLayout.addView(linLayout);
            current_im++;
        }
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