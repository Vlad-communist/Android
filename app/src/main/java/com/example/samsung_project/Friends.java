package com.example.samsung_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

public class Friends extends AppCompatActivity {
    public boolean flag2 = true;
    public String key;
    public long friend_now = 1;
    public long count_friends = 0;
    public Vector<Long> list_names = new Vector<Long>();
    public String now_name = "";


    @SuppressLint("ResourceType")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);
        getSupportActionBar().hide();

        Display display = getWindowManager().getDefaultDisplay();
        int width_of_screen = display.getWidth();
        int height_of_screen = display.getHeight();
        double h_proc = height_of_screen / 100.;
        double w_proc = width_of_screen / 100.;

        try {
            Get_data();
        } catch (Exception ex){
            System.out.println(ex);
        }
        LinearLayout mainlayaput = (LinearLayout) findViewById(R.id.ln);

        TextView t = new TextView(getApplicationContext());
        LinearLayout.LayoutParams t_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        t_params.topMargin = (int) Math.round(w_proc * 4);
        t_params.leftMargin = (int) Math.round(w_proc * 8);
        t_params.gravity = Gravity.START;
        t.setLayoutParams(t_params);
        t.setText("Друзья");
        t.setTextColor(Color.parseColor("#ffffff"));
        t.setTextSize((int) Math.round(w_proc * 2));

        mainlayaput.addView(t);

        LinearLayout search = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams search_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        search.setLayoutParams(search_params);
        search.setOrientation(LinearLayout.HORIZONTAL);
        CardView circled_search = new CardView(getApplicationContext());
        LinearLayout.LayoutParams circle_im_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 10));
        circle_im_params.gravity = Gravity.CENTER;
        circle_im_params.leftMargin = (int) Math.round(w_proc * 4);
        circle_im_params.rightMargin = (int) Math.round(w_proc * 4);
        circle_im_params.topMargin =(int) Math.round( w_proc * 4);
        circled_search.setLayoutParams(circle_im_params);
        circled_search.setRadius((int) Math.round(w_proc * 2.5));
        circled_search.setContentPadding(0, 0, 0, 0);
        circled_search.setCardBackgroundColor(Color.parseColor("#434446"));
        circled_search.addView(search);
        ImageView search_icon = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams search_icon_params = new LinearLayout.LayoutParams((int) Math.round(w_proc * 7), (int) Math.round(w_proc * 7));
        search_icon_params.leftMargin = (int) Math.round(w_proc * 1.5);
        search_icon_params.gravity = Gravity.CENTER_VERTICAL;
        search_icon.setLayoutParams(search_icon_params);
        search_icon.setBackgroundResource(R.drawable.search_icon);
        search.addView(search_icon);
        EditText text = new EditText(getApplicationContext());
        LinearLayout.LayoutParams text_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        text_params.leftMargin = (int) Math.round(w_proc * 1.5);
        text.setLayoutParams(text_params);
        text.setTextColor(Color.parseColor("#ffffff"));
        text.setHintTextColor(Color.GRAY);
        text.setPadding(0, (int) Math.round(w_proc * 0.1), 0, 0);
        text.setBackgroundResource(R.drawable.empty);
        text.setHint("Введите имя");
        text.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                try {
                    String text = "";
                    try {
                        text = s.toString();
                    } catch (Exception ex){
                        text = "";
                    }
                    now_name = text;
                    list_names.removeAllElements();
                    System.out.println(now_name);
                    LinearLayout friends = (LinearLayout) findViewById(R.id.ln);
                    while (friends.getChildCount() > 2){
                        friends.removeView(friends.getChildAt(2));
                    }
                    friend_now = 1;
                    System.out.println(count);
                    for (int i = 0; i < count_friends; i++) {
                        try {
                            flag2 = true;
                            Async_next_friend task = new Async_next_friend();
                            task.execute(friend_now);
                            friend_now++;
                        } catch (Exception ex){
                            break;
                        }
                    }
                } catch (Exception ex){
                    System.out.println(ex);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        search.addView(text);
        mainlayaput.addView(circled_search);
        ScrollView scroll = (ScrollView) findViewById(R.id.lent);
        for (int i = 0; i < count_friends; i++) {
            try {
                Async_next_friend task = new Async_next_friend();
                task.execute(friend_now++);
            } catch (Exception ex){
                break;
            }
        }
        scroll.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (scroll.getChildAt(0).getBottom()
                                <= (scroll.getHeight() + scroll.getScrollY())) {
                            try {
                                for (int i = 0; i < 3; i++) {
                                    Async_next_friend task = new Async_next_friend();
                                    task.execute(friend_now++);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public void Get_data() throws IOException, JSONException {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("sq", null, null, null, null, null, null);
        c.moveToNext();
        key = c.getString(1);

        String url = "http://vsn.intercom.pro:9080/get_info/" + key;
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        JSONObject root = new JSONObject(in.readLine());
        in.close();
        count_friends = Integer.parseInt(root.getString("friends"));
    }

    private class Async_next_friend extends AsyncTask<Long, Void, String[]> {

        @Override
        protected String[] doInBackground(Long... nomer) {
            if (flag2){
                try {
                    String url = "http://vsn.intercom.pro:9080/get_friends/" + key + '/' + nomer[0];
                    URL obj = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                    connection.setRequestMethod("GET");
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    JSONObject root = new JSONObject(in.readLine());
                    in.close();
                    if (!root.getString("ans").equals("NO")) {
                        String name = root.getString("name") + " " + root.getString("surname");
                        String img = root.getString("photo");
                        String name1 = name;
                        name = name.toLowerCase();
                        now_name = now_name.toLowerCase();
                        char[] a = now_name.toCharArray();
                        char[] b = name.toCharArray();
                        System.out.println(now_name + "|" +  name + "|" + count_friends + "|" + flag2);
                        for (int i = 0; i < a.length && i < b.length; i++){
                            if (a[i] != b[i]){
                                System.out.println("-");
                                return null;
                            }
                        }
                        String[] answer = {name1, img, nomer[0].toString()};
                        return answer;
                    } else {
                        flag2 = false;
                    }
                } catch (Exception ex){
                    System.out.println(ex);
                }
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            Display display = getWindowManager().getDefaultDisplay();
            int width_of_screen = display.getWidth();
            int height_of_screen = display.getHeight();
            double h_proc = height_of_screen / 100.;
            double w_proc = width_of_screen / 100.;
            if (result == null){
                return;
            }
            long nomer = Long.parseLong(result[2]);
            int hz = list_names.indexOf(nomer);
            System.out.println(hz);
            if (hz == -1){
                String name = result[0];
                String img = result[1];
                list_names.add(nomer);

                LinearLayout friends_layout = (LinearLayout) findViewById(R.id.ln);

                LinearLayout one_friend = new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams one_friend_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) Math.round(h_proc * 10));
                one_friend_params.leftMargin = (int) Math.round(w_proc * 4);
                one_friend_params.rightMargin = (int) Math.round(w_proc * 4);
                one_friend_params.topMargin = (int) Math.round(w_proc * 4);
                one_friend.setLayoutParams(one_friend_params);
                one_friend.setOrientation(LinearLayout.HORIZONTAL);

                ImageView logo = new ImageView(getApplicationContext());
                new DownloadImageTask(logo).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + img);
                LinearLayout.LayoutParams logo_params = new LinearLayout.LayoutParams((int) Math.round(h_proc * 10), (int) Math.round(h_proc * 10));
                logo.setLayoutParams(logo_params);
                CardView circle_im = new CardView(getApplicationContext());
                LinearLayout.LayoutParams circle_im_params = new LinearLayout.LayoutParams((int) Math.round(h_proc * 10), (int) Math.round(h_proc * 10));
                circle_im_params.gravity = Gravity.CENTER_VERTICAL;
                circle_im.setLayoutParams(circle_im_params);
                circle_im.setRadius((int) Math.round(h_proc * 2));
                circle_im.setContentPadding(0, 0, 0, 0);
                circle_im.setCardBackgroundColor(Color.parseColor("#36383F"));
                logo.setScaleType(ImageView.ScaleType.CENTER_CROP);
                circle_im.addView(logo);

                one_friend.addView(circle_im);

                TextView fio_friend = new TextView(getApplicationContext());
                LinearLayout.LayoutParams fio_friend_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                fio_friend_params.leftMargin = (int) Math.round(w_proc * 2);
                fio_friend.setLayoutParams(fio_friend_params);
                fio_friend.setText(name);
                fio_friend.setGravity(Gravity.CENTER_VERTICAL);
                fio_friend.setTextSize(16);
                fio_friend.setTextColor(Color.parseColor("#FFFFFF"));
                fio_friend.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

                one_friend.addView(fio_friend);
                friends_layout.addView(one_friend);
                ++friend_now;
            }
        }
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

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
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
