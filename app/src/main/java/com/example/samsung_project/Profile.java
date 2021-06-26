package com.example.samsung_project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Profile extends AppCompatActivity {

    public int current_im = 0;
    public int in_block = 0;
    public String key;
    public long vsnid = 1446464646;
    public String name_of_chelik = "Биба Абоба Бобович";
    public String about_of_chelik = "Эчпочмааааааааааааааааааааааак";
    public String image = "0.png";
    public int friend_now = 1;
    public long count = 0;
    public long new_now = 1;
    public boolean flag = true;
    public boolean flag2 = true;

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getSupportActionBar().hide();

        try {
            Get_data();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Display display = getWindowManager().getDefaultDisplay();
        int width_of_screen = display.getWidth();
        int height_of_screen = display.getHeight();
        int h_proc = height_of_screen / 100;
        int w_proc = width_of_screen / 100;
        int width = width_of_screen / 5;
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

        LinearLayout mainlayout = (LinearLayout) findViewById(R.id.ln);

        LinearLayout id_block = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams for_id_block = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);
        id_block.setOrientation(LinearLayout.HORIZONTAL);
        id_block.setLayoutParams(for_id_block);
        id_block.setGravity(Gravity.START);

        TextView id = new TextView(getApplicationContext());
        LinearLayout.LayoutParams for_id = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for_id.leftMargin = w_proc * 4;
        for_id.topMargin = w_proc * 2;
        for_id.bottomMargin = w_proc * 2;
        id.setText("VSNID:");
        id.setTextColor(Color.parseColor("#FFFFFF"));
        id.setTextSize(20);
        id.setLayoutParams(for_id);

        id_block.addView(id);

        LinearLayout digit_and_copy = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams digit_and_copy_params = new LinearLayout.LayoutParams(w_proc * 58, LinearLayout.LayoutParams.MATCH_PARENT);
        digit_and_copy.setLayoutParams(digit_and_copy_params);
        digit_and_copy.setOrientation(LinearLayout.HORIZONTAL);
        digit_and_copy.setPadding(0, 0, 0, 0);

        TextView cifarki = new TextView(getApplicationContext());
        LinearLayout.LayoutParams for_cifarki = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 100);
        for_cifarki.leftMargin = w_proc * 2;
        cifarki.setText("" + vsnid);
        cifarki.setTextSize(20);
        cifarki.setPadding(0, 20, 0, 0);
        cifarki.setTextColor(Color.parseColor("#FFFFFF"));
        cifarki.setLayoutParams(for_cifarki);

        digit_and_copy.addView(cifarki);

        ImageButton copy = new ImageButton(getApplicationContext());
        LinearLayout.LayoutParams for_copy = new LinearLayout.LayoutParams(50, 50);
        for_copy.leftMargin = w_proc;
        for_copy.gravity = Gravity.BOTTOM;
        for_copy.bottomMargin = 15;
        copy.setImageResource(R.drawable.copy);
        copy.setBackgroundColor(Color.parseColor("#36383F"));
        copy.setLayoutParams(for_copy);
        copy.setScaleType(ImageView.ScaleType.CENTER_CROP);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", cifarki.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "VSNID скопирован в буфер обмена", Toast.LENGTH_SHORT).show();
            }
        });

        digit_and_copy.addView(copy);

        id_block.addView(digit_and_copy);

        LinearLayout text_exit = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams text_exit_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        text_exit.setLayoutParams(text_exit_params);
        text_exit.setOrientation(LinearLayout.HORIZONTAL);
        text_exit.setPadding(0, 0, 0, 0);

        TextView exit = new TextView(getApplicationContext());
        LinearLayout.LayoutParams exit_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        exit.setLayoutParams(exit_params);
        exit.setText("Выйти");
        exit.setTextColor(Color.parseColor("#FF0000"));
        exit.setPadding(w_proc * 6, 30, 0, 0);
        exit.setTextSize(16);
        exit.setTypeface(null, Typeface.BOLD);

        text_exit.addView(exit);

        ImageButton off = new ImageButton(getApplicationContext());
        LinearLayout.LayoutParams off_copy = new LinearLayout.LayoutParams(50, 50);
        off_copy.gravity = Gravity.BOTTOM;
        off_copy.leftMargin = w_proc * 2;
        off_copy.bottomMargin = 13;
        off.setImageResource(R.drawable.off);
        off.setBackgroundColor(Color.parseColor("#36383F"));
        off.setLayoutParams(off_copy);
        off.setScaleType(ImageView.ScaleType.CENTER_CROP);

        text_exit.addView(off);
        text_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Аривидерчи", Toast.LENGTH_SHORT).show();
            }
        });

        id_block.addView(text_exit);

        mainlayout.addView(id_block);

        LinearLayout foto_and_name = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams for_foto_and_name = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, w_proc * 32);
        for_foto_and_name.topMargin = w_proc * 2;
        foto_and_name.setOrientation(LinearLayout.HORIZONTAL);
        foto_and_name.setLayoutParams(for_foto_and_name);
        foto_and_name.setGravity(Gravity.START);

        ImageView im = new ImageView(getApplicationContext());
        new DownloadImageTask(im).execute("http://vsn.intercom.pro:9080/image/" + image);
        LinearLayout.LayoutParams im_params = new LinearLayout.LayoutParams(w_proc * 25, w_proc * 25);
        im.setLayoutParams(im_params);
        CardView cardView = new CardView(getApplicationContext());
        LinearLayout.LayoutParams card_params = new LinearLayout.LayoutParams(w_proc * 25, w_proc * 25);
        card_params.leftMargin = w_proc * 4;
        cardView.setLayoutParams(card_params);
        cardView.setRadius(w_proc * 7);
        cardView.setContentPadding(0, 0, 0, 0);
        cardView.setCardBackgroundColor(Color.parseColor("#36383F"));
        im.setScaleType(ImageView.ScaleType.CENTER_CROP);
        cardView.addView(im);

        foto_and_name.addView(cardView);

        LinearLayout name = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams for_name = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        name.setOrientation(LinearLayout.VERTICAL);
        name.setLayoutParams(for_name);
        name.setGravity(Gravity.START);

        TextView fio = new TextView(getApplicationContext());
        TextView about = new TextView(getApplicationContext());
        LinearLayout.LayoutParams for_fio = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams for_about = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for_fio.bottomMargin = w_proc * 1;
        for_fio.topMargin = -w_proc * 1;
        for_fio.leftMargin = w_proc * 4;
        for_about.leftMargin = w_proc * 4;
        fio.setLayoutParams(for_fio);
        about.setLayoutParams(for_about);
        fio.setText(name_of_chelik);
        fio.setTextColor(Color.parseColor("#FFFFFF"));
        about.setText(about_of_chelik);
        about.setTextColor(Color.parseColor("#FFFFFF"));
        fio.setTextSize(18);
        about.setTextSize(14);
        name.addView(fio);
        name.addView(about);

        foto_and_name.addView(name);

        mainlayout.addView(foto_and_name);

        Button edit = new Button(getApplicationContext());
        LinearLayout.LayoutParams for_edit = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        edit.setTextColor(Color.parseColor("#FFFFFF"));
        edit.setBackgroundColor(Color.parseColor("#555555"));
        edit.setLayoutParams(for_edit);
        edit.setText("Редактировать");
        edit.setTextSize(12);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Редактировать", Toast.LENGTH_SHORT).show();
            }
        });
        CardView crd_for_button = new CardView(getApplicationContext());
        LinearLayout.LayoutParams crd_for_button_params = new LinearLayout.LayoutParams(w_proc * 100, w_proc * 10);
        crd_for_button_params.leftMargin = w_proc * 4;
        crd_for_button_params.topMargin = -w_proc * 4;
        crd_for_button.setLayoutParams(crd_for_button_params);
        crd_for_button.setRadius(h_proc);
        crd_for_button.setContentPadding(0, 0, 0, 0);
        crd_for_button.setCardBackgroundColor(Color.parseColor("#36383F"));
        crd_for_button.addView(edit);

        mainlayout.addView(crd_for_button);

        LinearLayout friends_block = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams friends_block_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, h_proc * 3);
        friends_block_params.topMargin = w_proc * 3;
        friends_block.setLayoutParams(friends_block_params);
        friends_block.setOrientation(LinearLayout.HORIZONTAL);

        TextView friends = new TextView(getApplicationContext());
        LinearLayout.LayoutParams friends_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        friends_params.leftMargin = w_proc * 4;
        friends.setLayoutParams(friends_params);
        friends.setText("Друзья");
        friends.setTextColor(Color.parseColor("#FFFFFF"));
        friends.setGravity(Gravity.START);

        friends_block.addView(friends);

        //чееееееееееееееееееееееееееееееееееееееееееееееееееееееел

        TextView count_friends = new TextView(getApplicationContext());
        LinearLayout.LayoutParams count_friends_params = new LinearLayout.LayoutParams(w_proc * 20, LinearLayout.LayoutParams.MATCH_PARENT);
        count_friends_params.leftMargin = w_proc;
        count_friends_params.topMargin = h_proc / 10;
        count_friends.setLayoutParams(count_friends_params);
        count_friends.setMinWidth(w_proc * 20);
        count_friends.setText("" + count);
        count_friends.setTextColor(Color.parseColor("#FFFFFF"));
        count_friends.setGravity(Gravity.START);

        friends_block.addView(count_friends);

        ImageButton right = new ImageButton(getApplicationContext());
        LinearLayout.LayoutParams for_right = new LinearLayout.LayoutParams(h_proc * 3, h_proc * 3);
        for_right.topMargin = -h_proc / 10 * 2;
        for_right.leftMargin = w_proc * 60;
        right.setImageResource(R.drawable.right);
        right.setLayoutParams(for_right);
        right.setScaleType(ImageView.ScaleType.CENTER_CROP);
        right.setPadding(0, 0, 0, 0);
        right.setBackgroundColor(Color.parseColor("#36383F"));
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "вправо", Toast.LENGTH_SHORT).show();
            }
        });

        friends_block.addView(right);

        mainlayout.addView(friends_block);

        HorizontalScrollView friends_scroll = new HorizontalScrollView(getApplicationContext());
        LinearLayout.LayoutParams scroll_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        scroll_params.topMargin = h_proc * 2;
        friends_scroll.setLayoutParams(scroll_params);
        friends_scroll.setHorizontalScrollBarEnabled(true);
        friends_scroll.setId(16516);

        LinearLayout friends_layout = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams friends_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        friends_layout.setLayoutParams(friends_layout_params);
        friends_layout.setOrientation(LinearLayout.HORIZONTAL);
        friends_layout.setId(228);

        friends_scroll.addView(friends_layout);
        for (int i = 0; i < 6; i++) {
            try {
                Next_friend(friends_layout);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        friends_scroll.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (friends_scroll.getChildAt(0).getRight()
                                <= (friends_scroll.getWidth() + friends_scroll.getScrollX())) {
                            try {
                                Next_friend(friends_layout);
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                        }
                    }
                });
        mainlayout.addView(friends_scroll);

        TextView lable = new TextView(getApplicationContext());
        LinearLayout.LayoutParams lable_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, h_proc * 2);
        lable_params.leftMargin = w_proc * 4;
        lable_params.topMargin = h_proc * 2;
        lable.setLayoutParams(lable_params);
        lable.setText("Мои записи");
        lable.setTextColor(Color.parseColor("#FFFFFF"));
        lable.setGravity(Gravity.START);

        mainlayout.addView(lable);

        for (int i = 0; i < 3; i++) {
            try {
                Next_post(mainlayout);
            } catch (Exception ex) {
                break;
            }
        }

        ScrollView scroll = (ScrollView) findViewById(R.id.lent);
        scroll.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (scroll.getChildAt(0).getBottom()
                                <= (scroll.getHeight() + scroll.getScrollY())) {
                            try {
                                for (int i = 0; i < 2; i++) {
                                    Next_post(mainlayout);
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
        name_of_chelik = root.getString("name") + " " + root.getString("surname");
        about_of_chelik = root.getString("status");
        image = root.getString("photo");
        count = Integer.parseInt(root.getString("friends"));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void Next_friend(LinearLayout friends_layout) throws IOException, JSONException {
        if (flag2) {
            Display display = getWindowManager().getDefaultDisplay();
            int width_of_screen = display.getWidth();
            int height_of_screen = display.getHeight();
            int h_proc = height_of_screen / 100;
            int w_proc = width_of_screen / 100;

            String url = "http://vsn.intercom.pro:9080/get_friends/" + key + '/' + friend_now;
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            JSONObject root = new JSONObject(in.readLine());
            in.close();
            if (!root.getString("ans").equals("NO")) {

                String name = root.getString("name") + " " + root.getString("surname");
                String img = root.getString("photo");

                LinearLayout one_friend = new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams one_friend_params = new LinearLayout.LayoutParams(h_proc * 8, LinearLayout.LayoutParams.WRAP_CONTENT);
                one_friend_params.leftMargin = w_proc * 4;
                one_friend.setLayoutParams(one_friend_params);
                one_friend.setOrientation(LinearLayout.VERTICAL);

                ImageView logo = new ImageView(getApplicationContext());
                new DownloadImageTask(logo).execute("http://vsn.intercom.pro:9080/image/" + img);
                LinearLayout.LayoutParams logo_params = new LinearLayout.LayoutParams(h_proc * 8, h_proc * 8);
                logo.setLayoutParams(logo_params);
                CardView circle_im = new CardView(getApplicationContext());
                LinearLayout.LayoutParams circle_im_params = new LinearLayout.LayoutParams(h_proc * 8, h_proc * 8);
                circle_im.setLayoutParams(circle_im_params);
                circle_im.setRadius(h_proc * 2);
                circle_im.setContentPadding(0, 0, 0, 0);
                circle_im.setCardBackgroundColor(Color.parseColor("#36383F"));
                logo.setScaleType(ImageView.ScaleType.CENTER_CROP);
                circle_im.addView(logo);

                one_friend.addView(circle_im);

                TextView fio_friend = new TextView(getApplicationContext());
                LinearLayout.LayoutParams fio_friend_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                fio_friend.setLayoutParams(fio_friend_params);
                fio_friend.setText(name);
                fio_friend.setTextSize(10);
                fio_friend.setTextColor(Color.parseColor("#FFFFFF"));
                fio_friend.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                one_friend.addView(fio_friend);

                friends_layout.addView(one_friend);
                ++friend_now;
            } else {
                flag2 = false;
            }
        }
    }

    public void Next_post(LinearLayout mainlayout) throws IOException, JSONException {
        if (flag) {
            String url = "http://vsn.intercom.pro:9080/self_new/" + key + "/" + new_now;
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            JSONObject root = new JSONObject(in.readLine());
            in.close();
            if (!root.getString("text").equals("nope")) {
                Display display = getWindowManager().getDefaultDisplay();
                int width_of_screen = display.getWidth();
                int height_of_screen = display.getHeight();
                int h_proc = height_of_screen / 100;
                int w_proc = width_of_screen / 100;

                View line1 = new View(getApplicationContext());
                LinearLayout.LayoutParams g = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
                g.topMargin = w_proc * 4;
                line1.setLayoutParams(g);
                line1.setBackgroundResource(R.drawable.hz_kakaja_to_parasha);
                line1.setPadding(0, 0, 0, 0);
                mainlayout.addView(line1);
                String text = root.getString("text");
                String title = root.getString("title");
                String img = root.getString("photo");
                System.out.println(img);
                System.out.println(1234321);
                int count_fotos = Integer.parseInt(root.getString("count_photos").toString());
                LinearLayout logo_box = new LinearLayout(getApplicationContext());
                logo_box.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams for_logo_box = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                logo_box.setLayoutParams(for_logo_box);

                ImageView kartinka = new ImageView(getApplicationContext());
                new DownloadImageTask(kartinka).execute("http://vsn.intercom.pro:9080/image/" + image);

                LinearLayout.LayoutParams for_kartinka = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                kartinka.setLayoutParams(for_kartinka);

                CardView crd_for_button = new CardView(getApplicationContext());
                LinearLayout.LayoutParams crd_for_button_params = new LinearLayout.LayoutParams(w_proc * 10, w_proc * 10);
                crd_for_button_params.leftMargin = w_proc * 4;
                crd_for_button_params.topMargin = w_proc * 4;
                crd_for_button.setLayoutParams(crd_for_button_params);
                crd_for_button.setRadius(w_proc * 2);
                crd_for_button.setContentPadding(0, 0, 0, 0);
                crd_for_button.setCardBackgroundColor(Color.parseColor("#36383F"));
                kartinka.setScaleType(ImageView.ScaleType.CENTER_CROP);
                crd_for_button.addView(kartinka);

                logo_box.addView(crd_for_button);

                String name = name_of_chelik;

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
                mainlayout.addView(logo_box);
                switch (count_fotos) {
                    case 0:
                        break;
                    case 1:
                        ImageView im = new ImageView(getApplicationContext());

                        new DownloadImageTask(im).execute("http://vsn.intercom.pro:9080/image/" + img);
                        LinearLayout.LayoutParams im_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        im.setLayoutParams(im_params);
                        im.setPadding(0, 0, 0, 0);
                        CardView card = new CardView(getApplicationContext());
                        LinearLayout.LayoutParams card_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        card_params.topMargin = w_proc * 4;
                        card_params.leftMargin = w_proc * 4;
                        card_params.rightMargin = w_proc * 4;
                        card_params.gravity = Gravity.CENTER_HORIZONTAL;
                        card.setLayoutParams(card_params);
                        card.setRadius(w_proc * 2);
                        card.setContentPadding(0, 0, 0, 0);
                        card.setCardBackgroundColor(Color.parseColor("#36383F"));
                        card.addView(im);
                        mainlayout.addView(card);
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
                TextView text_of_post = new TextView(getApplicationContext());
                LinearLayout.LayoutParams text_of_post_params = new LinearLayout.LayoutParams(width_of_screen - 50, LinearLayout.LayoutParams.WRAP_CONTENT);
                text_of_post_params.topMargin = w_proc * 4;
                text_of_post_params.leftMargin = w_proc * 4;
                text_of_post_params.gravity = Gravity.FILL;
                text_of_post.setLayoutParams(text_of_post_params);
                text_of_post.setText(title + "\n" + text);
                text_of_post.setTextColor(Color.parseColor("#FFFFFF"));
                text_of_post.setTextSize(h_proc * 10 / 13);

                mainlayout.addView(text_of_post);

                View line2 = new View(getApplicationContext());
                LinearLayout.LayoutParams gg = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
                gg.topMargin = w_proc * 4;
                gg.bottomMargin = -w_proc * 4 - 5;
                line2.setLayoutParams(gg);
                line2.setBackgroundResource(R.drawable.hz_kakaja_to_parasha);
                line2.setPadding(0, 0, 0, 0);

                mainlayout.addView(line2);
                new_now++;
            } else {
                flag = false;
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
        overridePendingTransition(R.anim.top, R.anim.top1);
        this.finish();
    }

    public void Video(View view) {
        Intent intent = new Intent(this, Video.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right, R.anim.right1);
        this.finish();
    }

    public void Friends(View view) {
        Intent intent = new Intent(this, Friends.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right, R.anim.right1);
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
