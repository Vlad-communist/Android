package com.example.samsung_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.KeyEventDispatcher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;


public class News extends AppCompatActivity {
    public int current_im = 0;
    public int in_block = 0;
    public String key;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);

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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getSupportActionBar().hide();
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("sq", null, null, null, null, null, null);
        c.moveToNext();
        key = c.getString(1);
        System.out.println(key);
        ScrollView scrollView = (ScrollView) findViewById(R.id.lent);
        try {
           Next_posts();
           Next_posts();
           Next_posts();
        } catch (IOException | JSONException e) {
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
                                Next_posts();
                                Next_posts();
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

//    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    public void Next_posts() throws IOException, JSONException {

        System.out.println(1);

        Display display = getWindowManager().getDefaultDisplay();
        int width_of_screen = display.getWidth();
        int height_of_screen = display.getHeight();
        int h_proc = height_of_screen / 100;
        int w_proc = width_of_screen / 100;
        int width = width_of_screen;

        in_block = 0;
        String url = "http://vsn.intercom.pro:9080/new/" + key + "/" + current_im;

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        JSONObject root = new JSONObject(in.readLine());
        in.close();
        System.out.println();
        String text = root.getString("text");
        String title = root.getString("title");
        String  image = root.getString("photo");
        int count_fotos = Integer.parseInt(root.getString("count_photos").toString());
        LinearLayout frameLayout = (LinearLayout) findViewById(R.id.ln);
        //        new DownloadImageTask(im).execute("https://images-ext-1.discordapp.net/external/qyfnjk5ZErAzQAqoFsKKmWoCdHisH_Kh4tBCFn0k940/%3Fsize%3D660x660%26quality%3D96%26sign%3De6467d23fd76b8cd213f681e7465e330%26type%3Dalbum/https/sun9-21.userapi.com/impg/3Z8gyexEsZRZu3Vg-NxyMXcNpkUXuLBNX5NIlg/i2z774wn3i8.jpg" + current_im + ".png");
        LinearLayout linLayout = new LinearLayout(getApplicationContext());
        linLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linLayoutParam.setMargins(0, 0, 0, 20);
        linLayout.setLayoutParams(linLayoutParam);
        linLayout.setPadding(20, 20, 20, -5);
        View line1 = new View(getApplicationContext());
        View line2 = new View(getApplicationContext());
        ViewGroup.LayoutParams g = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5);
        line1.setLayoutParams(g);
        line2.setLayoutParams(g);
        line1.setBackgroundResource(R.drawable.hz_kakaja_to_parasha);
        line2.setBackgroundResource(R.drawable.hz_kakaja_to_parasha);
        line1.setPadding(0, 0, 0, 0);
//        ViewGroup.LayoutParams im_params = new ViewGroup.LayoutParams();
        TextView textView = new TextView(getApplicationContext());
        LinearLayout.LayoutParams text_of_post_params = new LinearLayout.LayoutParams(width_of_screen - 50, LinearLayout.LayoutParams.WRAP_CONTENT);
        text_of_post_params.topMargin = w_proc * 4;
        text_of_post_params.leftMargin = w_proc * 4;
        text_of_post_params.gravity = Gravity.FILL;
        textView.setLayoutParams(text_of_post_params);
        textView.setText(title + "\n" + text);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setTextSize(h_proc * 10 / 13);

        linLayout.setId(current_im);
        in_block++;
        line1.setId(in_block);
        in_block++;
        textView.setId(in_block);
        in_block++;
        line2.setId(in_block);
        linLayout.addView(line1);

//        count_fotos = 1; //maximum 10

        LinearLayout logo_box = new LinearLayout(getApplicationContext());
        logo_box.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams for_logo_box = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        logo_box.setLayoutParams(for_logo_box);

        ImageView kartinka = new ImageView(getApplicationContext());

        new DownloadImageTask(kartinka).execute("https://images-ext-1.discordapp.net/external/qyfnjk5ZErAzQAqoFsKKmWoCdHisH_Kh4tBCFn0k940/%3Fsize%3D660x660%26quality%3D96%26sign%3De6467d23fd76b8cd213f681e7465e330%26type%3Dalbum/https/sun9-21.userapi.com/impg/3Z8gyexEsZRZu3Vg-NxyMXcNpkUXuLBNX5NIlg/i2z774wn3i8.jpg");

        LinearLayout.LayoutParams for_kartinka = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        kartinka.setLayoutParams(for_kartinka);

        CardView crd_for_button = new CardView(getApplicationContext());
        LinearLayout.LayoutParams crd_for_button_params = new LinearLayout.LayoutParams( w_proc * 10, w_proc * 10);
        crd_for_button_params.leftMargin = w_proc * 4;
        crd_for_button_params.topMargin = w_proc * 4;
        crd_for_button.setLayoutParams(crd_for_button_params);
        crd_for_button.setRadius(w_proc * 2);
        crd_for_button.setContentPadding(0, 0, 0, 0);
        crd_for_button.setCardBackgroundColor(Color.LTGRAY);
        kartinka.setScaleType(ImageView.ScaleType.CENTER_CROP);
        crd_for_button.addView(kartinka);

        logo_box.addView(crd_for_button);

        String name = "Ebobobobooooooooo";

        TextView fio = new TextView(getApplicationContext());
        LinearLayout.LayoutParams for_fio = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, w_proc * 10);
        for_fio.topMargin = w_proc * 4;
        fio.setLayoutParams(for_fio);
        fio.setGravity(Gravity.CENTER_VERTICAL);
        fio.setPadding(w_proc * 2, 0, 0, 0);
        fio.setTextSize(w_proc * 2);
        fio.setTextColor(Color.parseColor("#FFFFFF"));
        fio.setText(name);

        logo_box.addView(fio);

        linLayout.addView(logo_box);

        ImageView logo = new ImageView(getApplicationContext());
        switch (count_fotos){
            case 0:
                break;
            case 1:
                ImageView im = new ImageView(getApplicationContext());

                new DownloadImageTask(im).execute("http://vsn.intercom.pro/image/" + image + ".jpg");
//                new DownloadImageTask(im).execute("https://images-ext-1.discordapp.net/external/qyfnjk5ZErAzQAqoFsKKmWoCdHisH_Kh4tBCFn0k940/%3Fsize%3D660x660%26quality%3D96%26sign%3De6467d23fd76b8cd213f681e7465e330%26type%3Dalbum/https/sun9-21.userapi.com/impg/3Z8gyexEsZRZu3Vg-NxyMXcNpkUXuLBNX5NIlg/i2z774wn3i8.jpg");

                LinearLayout.LayoutParams im_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                im.setLayoutParams(im_params);
                im.setPadding(0, 0, 0, 0);
                CardView card = new CardView(getApplicationContext());
                LinearLayout.LayoutParams card_params = new LinearLayout.LayoutParams(w_proc * 100, w_proc * 100);
                card_params.topMargin = w_proc * 4;
                card_params.gravity = Gravity.CENTER_HORIZONTAL;
                card.setLayoutParams(card_params);
                card.setRadius(w_proc * 2);
                card.setContentPadding(0, 0, 0, 0);
                card.setCardBackgroundColor(Color.parseColor("#36383F"));
                card.addView(im);
                linLayout.addView(card);
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

    public void New(View view) {
        Intent intent = new Intent(this, News.class);
        startActivity(intent);
        overridePendingTransition(R.anim.top, R.anim.top1);
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
        overridePendingTransition(R.anim.left, R.anim.left1);
        this.finish();
    }

    public void Friends(View view){
        Intent intent = new Intent(this, Friends.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left, R.anim.left1);
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