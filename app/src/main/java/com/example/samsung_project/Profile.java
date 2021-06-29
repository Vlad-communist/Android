package com.example.samsung_project;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.Time;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Profile extends AppCompatActivity {

    public int current_im = 0;
    public int in_block = 0;
    public String key;
    public long vsnid = 1446464646;
    public String name_of_chelik = "Биба Абоба Бобович";
    public String about_of_chelik = "Эчпочмааааааааааааааааааааааак";
    public String image = "0.png";
    public long friend_now = 1;
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
        } catch (Exception ex) {
            System.out.println(ex);
        }
        Display display = getWindowManager().getDefaultDisplay();
        int width_of_screen = display.getWidth();
        int height_of_screen = display.getHeight();
        double h_proc = height_of_screen / 100;
        double w_proc = width_of_screen / 100;
        int width = width_of_screen / 5;

        LinearLayout mainlayout = (LinearLayout) findViewById(R.id.ln);

        TextView id = (TextView) findViewById(R.id.text_vsnid);
        LinearLayout.LayoutParams for_id = (LinearLayout.LayoutParams) id.getLayoutParams();
        for_id.leftMargin = (int) Math.round(w_proc * 4);
        for_id.topMargin = (int) Math.round(w_proc * 2);
        for_id.bottomMargin = (int) Math.round(w_proc * 2);
        id.setLayoutParams(for_id);

        LinearLayout digit_and_copy = (LinearLayout) findViewById(R.id.digit_and_copy);
        LinearLayout.LayoutParams digit_and_copy_params = (LinearLayout.LayoutParams) digit_and_copy.getLayoutParams();
        digit_and_copy_params.width = (int) Math.round(w_proc * 40);
        digit_and_copy.setLayoutParams(digit_and_copy_params);

        TextView cifarki = (TextView) findViewById(R.id.cifarki);
        LinearLayout.LayoutParams for_cifarki = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 100);
        for_cifarki.leftMargin = (int) Math.round(w_proc * 2);
        cifarki.setText("" + vsnid);
        cifarki.setLayoutParams(for_cifarki);

        ImageButton copy = (ImageButton) findViewById(R.id.copy);
        LinearLayout.LayoutParams copy_params = (LinearLayout.LayoutParams) copy.getLayoutParams();
        copy_params.leftMargin = (int) Math.round(w_proc);
        copy.setLayoutParams(copy_params);

        ImageButton off = (ImageButton) findViewById(R.id.off);
        LinearLayout.LayoutParams off_copy = (LinearLayout.LayoutParams) off.getLayoutParams();
        off_copy.leftMargin = (int) Math.round(w_proc);
        off_copy.rightMargin = (int) Math.round(w_proc * 4);
        off.setLayoutParams(off_copy);

        LinearLayout foto_and_name = (LinearLayout) findViewById(R.id.foto_and_name);
        LinearLayout.LayoutParams for_foto_and_name = (LinearLayout.LayoutParams) foto_and_name.getLayoutParams();
        for_foto_and_name.topMargin = (int) Math.round(w_proc * 2);
        foto_and_name.setLayoutParams(for_foto_and_name);
        foto_and_name.setGravity(Gravity.START);

        ImageView im = (ImageView) findViewById(R.id.im);
        new DownloadImageTask(im).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + image);
        CardView cardView = (CardView) findViewById(R.id.cardView);
        LinearLayout.LayoutParams card_params = (LinearLayout.LayoutParams) cardView.getLayoutParams();
        card_params.leftMargin = (int) Math.round(w_proc * 4);
        card_params.width = (int) Math.round(w_proc * 25);
        card_params.height = (int) Math.round(w_proc * 25);
        cardView.setLayoutParams(card_params);
        cardView.setRadius((int) Math.round(w_proc * 6));



        TextView fio = (TextView) findViewById(R.id.fio);
        TextView about = (TextView) findViewById(R.id.about);
        LinearLayout.LayoutParams for_fio = (LinearLayout.LayoutParams) fio.getLayoutParams();
        LinearLayout.LayoutParams for_about = (LinearLayout.LayoutParams) about.getLayoutParams();
        for_fio.bottomMargin = (int) Math.round(w_proc * 1);
        for_fio.topMargin = (int) Math.round(-w_proc * 1);
        for_fio.leftMargin = (int) Math.round(w_proc * 4);
        for_about.leftMargin = (int) Math.round(w_proc * 4);
        for_about.rightMargin = (int) Math.round(w_proc * 4);
        fio.setLayoutParams(for_fio);
        about.setLayoutParams(for_about);
        fio.setText(name_of_chelik);
        about.setText(about_of_chelik);
        fio.setTextSize(18);
        about.setTextSize(14);
//


        Button edit = (Button) findViewById(R.id.edit);
        CardView crd_for_button = (CardView) findViewById(R.id.crd_for_button);
        LinearLayout.LayoutParams crd_for_button_params = (LinearLayout.LayoutParams) crd_for_button.getLayoutParams();
        crd_for_button_params.leftMargin = (int) Math.round(w_proc * 4);
        crd_for_button_params.topMargin = (int) Math.round(w_proc * 4);
        crd_for_button_params.width = (int) Math.round(w_proc * 100);
        crd_for_button_params.height = (int) Math.round(w_proc * 10);
        crd_for_button.setLayoutParams(crd_for_button_params);
        crd_for_button.setBackgroundColor(Color.parseColor("#434446"));
        crd_for_button.setRadius((int) Math.round(h_proc));
        crd_for_button.setBackgroundResource(R.drawable.btnclr);
        edit.setBackgroundResource(R.drawable.btnclr);
        edit.setBackgroundColor(Color.parseColor("#00ffffff"));


        LinearLayout friends_block = (LinearLayout) findViewById(R.id.friends_block);
        LinearLayout.LayoutParams friends_block_params = (LinearLayout.LayoutParams) friends_block.getLayoutParams();
        friends_block_params.topMargin = (int) Math.round(w_proc * 3);
        friends_block_params.height = (int) Math.round(h_proc * 3);
        friends_block.setLayoutParams(friends_block_params);

        TextView text_friends = (TextView) findViewById(R.id.text_friends);
        LinearLayout.LayoutParams friends_params = (LinearLayout.LayoutParams) text_friends.getLayoutParams();
        friends_params.leftMargin = (int) Math.round(w_proc * 4);
        text_friends.setLayoutParams(friends_params);


        TextView count_friends = (TextView) findViewById(R.id.count_friends);
        LinearLayout.LayoutParams count_friends_params = (LinearLayout.LayoutParams) count_friends.getLayoutParams();
        count_friends_params.leftMargin = (int) Math.round(w_proc);
        count_friends_params.topMargin = (int) Math.round(h_proc / 10);
        count_friends.setLayoutParams(count_friends_params);
        count_friends.setText("" + count);


        ImageButton right = (ImageButton) findViewById(R.id.right);
        LinearLayout.LayoutParams for_right = (LinearLayout.LayoutParams) right.getLayoutParams();
        for_right.topMargin = (int) Math.round(-h_proc / 10 * 2);
        for_right.height = (int) Math.round(h_proc * 3);
        for_right.width = (int) Math.round(h_proc * 3);
        for_right.rightMargin = (int) Math.round(w_proc * 4);
        right.setLayoutParams(for_right);

//

        HorizontalScrollView friends_scroll = (HorizontalScrollView) findViewById(R.id.friends_scroll);
        LinearLayout.LayoutParams scroll_params = (LinearLayout.LayoutParams) friends_scroll.getLayoutParams();
        scroll_params.topMargin = (int) Math.round(h_proc * 2);
        friends_scroll.setLayoutParams(scroll_params);
        friends_scroll.setHorizontalScrollBarEnabled(true);

        LinearLayout friends_layout = (LinearLayout) findViewById(R.id.friends_layout);

        for (int i = 0; i < 6; i++) {
            try {
                Async_next_friend task = new Async_next_friend();
//                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, friend_now);
                task.execute(friend_now);
                friend_now++;
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
                                Async_next_friend task = new Async_next_friend();
//                                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, friend_now);
                                task.execute(friend_now);
                                friend_now++;
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                        }
                    }
                });

        TextView lable = (TextView) findViewById(R.id.lable);
        LinearLayout.LayoutParams lable_params = (LinearLayout.LayoutParams) lable.getLayoutParams();
        lable_params.leftMargin = (int) Math.round(w_proc * 4);
        lable_params.topMargin = (int) Math.round(h_proc * 2);
        lable_params.height = (int) Math.round(h_proc * 2);
        lable.setLayoutParams(lable_params);


        for (int i = 0; i < 3; i++) {
            try {
                Async_next_post task = new Async_next_post();
//                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new_now);
                task.execute(new_now);
                new_now++;
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
                                    Async_next_post task = new Async_next_post();
//                                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new_now);
                                    task.execute(new_now);
                                    new_now++;
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

    public void edit(View view){
        Toast.makeText(getApplicationContext(), "Редактировать", Toast.LENGTH_SHORT).show();
    }

    public void copy(View view){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", Long.toString(vsnid));
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "VSNID скопирован в буфер обмена", Toast.LENGTH_SHORT).show();
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
                        String[] answer = {name, img};
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
            int h_proc = height_of_screen / 100;
            int w_proc = width_of_screen / 100;
            if (result == null){
                return;
            }
            String name = result[0];
            String img = result[1];

            LinearLayout friends_layout = (LinearLayout) findViewById(R.id.friends_layout);

            LinearLayout one_friend = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams one_friend_params = new LinearLayout.LayoutParams(h_proc * 8, LinearLayout.LayoutParams.WRAP_CONTENT);
            one_friend_params.leftMargin = w_proc * 4;
            one_friend.setLayoutParams(one_friend_params);
            one_friend.setOrientation(LinearLayout.VERTICAL);

            ImageView logo = new ImageView(getApplicationContext());
            new DownloadImageTask(logo).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + img);
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
        }
    }

    private class Async_next_post extends AsyncTask<Long, Void, String[][]>{
        @Override
        protected String[][] doInBackground(Long... nomer) {
            try {
                if (flag) {
                    String url = "http://vsn.intercom.pro:9080/self_new/" + key + "/" + nomer[0];
                    URL obj = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                    connection.setRequestMethod("GET");
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    JSONObject root = new JSONObject(in.readLine());
                    in.close();
                    if (!root.getString("text").equals("nope")) {
                        String text = root.getString("text");
                        String title = root.getString("title");
                        String count_fotos = root.getString("count_photos").toString();
                        String[] images = new String[Integer.parseInt(count_fotos)];
                        for (int i = 0; i < Integer.parseInt(count_fotos); i++){
                            images[i] = (root.getJSONArray("photo")).getString(i);
                        }
                        return new String[][]{new String[]{text, title, count_fotos}, images};
                    } else {
                        flag = false;
                    }
                }
            } catch (Exception ex){
                System.out.println(ex);
            }
            return null;
        }
        @Override
        protected void onPostExecute(String[][] result){
            Display display = getWindowManager().getDefaultDisplay();
            int width_of_screen = display.getWidth();
            int height_of_screen = display.getHeight();
            double h_proc = height_of_screen / 100.;
            double w_proc = width_of_screen / 100.;
            if (result == null){
                return;
            }
            String text = result[0][0];
            String title = result[0][1];
            int count_fotos = Integer.parseInt(result[0][2]);
            String[] images = new String[count_fotos];
            if (count_fotos > 0){
                images = result[1];
            }
            LinearLayout mainlayout = (LinearLayout) findViewById(R.id.ln);
            View line1 = new View(getApplicationContext());
            LinearLayout.LayoutParams g = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
            g.topMargin = (int) Math.round(w_proc * 4);
            line1.setLayoutParams(g);
            line1.setBackgroundResource(R.drawable.hz_kakaja_to_parasha);
            line1.setPadding(0, 0, 0, 0);
            mainlayout.addView(line1);
            System.out.println(images);
            System.out.println(1234321);
            LinearLayout logo_box = new LinearLayout(getApplicationContext());
            logo_box.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams for_logo_box = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            logo_box.setLayoutParams(for_logo_box);
            ImageView kartinka = new ImageView(getApplicationContext());
            new DownloadImageTask(kartinka).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + image);
            LinearLayout.LayoutParams for_kartinka = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            kartinka.setLayoutParams(for_kartinka);
            CardView crd_for_button = new CardView(getApplicationContext());
            LinearLayout.LayoutParams crd_for_button_params = new LinearLayout.LayoutParams((int) Math.round(w_proc * 10), (int) Math.round(w_proc * 10));
            crd_for_button_params.leftMargin = (int) Math.round(w_proc * 4);
            crd_for_button_params.topMargin = (int) Math.round(w_proc * 4);
            crd_for_button.setLayoutParams(crd_for_button_params);
            crd_for_button.setRadius((int) Math.round(w_proc * 2));
            crd_for_button.setContentPadding(0, 0, 0, 0);
            crd_for_button.setCardBackgroundColor(Color.parseColor("#36383F"));
            kartinka.setScaleType(ImageView.ScaleType.CENTER_CROP);
            crd_for_button.addView(kartinka);
            logo_box.addView(crd_for_button);
            String name = name_of_chelik;
            TextView fio = new TextView(getApplicationContext());
            LinearLayout.LayoutParams for_fio = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 10));
            for_fio.topMargin = (int) Math.round(w_proc * 4);
            fio.setLayoutParams(for_fio);
            fio.setGravity(Gravity.CENTER_VERTICAL);
            fio.setPadding((int) Math.round(w_proc * 2), 0, 0, 0);
            fio.setTextSize((int) Math.round(w_proc * 2));
            fio.setTextColor(Color.parseColor("#FFFFFF"));
            fio.setText(name);
            logo_box.addView(fio);
            mainlayout.addView(logo_box);
            TextView text_of_post = new TextView(getApplicationContext());
            LinearLayout.LayoutParams text_of_post_params = new LinearLayout.LayoutParams(width_of_screen - 50, LinearLayout.LayoutParams.WRAP_CONTENT);
            text_of_post_params.topMargin = (int) Math.round(w_proc * 4);
            text_of_post_params.leftMargin = (int) Math.round(w_proc * 4);
            text_of_post_params.gravity = Gravity.FILL;
            text_of_post.setLayoutParams(text_of_post_params);
            text_of_post.setText(title + "\n" + text);
            text_of_post.setTextColor(Color.parseColor("#FFFFFF"));
            text_of_post.setTextSize(16);
            mainlayout.addView(text_of_post);
            int j = 0;
            switch (count_fotos) {
                case 0:
                    break;
                case 1:
                    ImageView im = new ImageView(getApplicationContext());
                    new DownloadImageTask(im).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);

                    LinearLayout.LayoutParams im_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    im.setLayoutParams(im_params);
                    im.setPadding(0, 0, 0, 0);
                    im.setScaleType(ImageView.ScaleType.FIT_XY);

                    im.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            OpenPicture(view);
                        }
                    });

                    CardView card = new CardView(getApplicationContext());
                    LinearLayout.LayoutParams card_params = new LinearLayout.LayoutParams((int) Math.round(w_proc * 92), (int) Math.round(w_proc * 50));
                    card_params.topMargin = (int) Math.round(w_proc * 4);
                    card_params.leftMargin = (int) Math.round(w_proc * 4);
                    card.setLayoutParams(card_params);
                    card.setRadius((int) Math.round(w_proc * 5));
                    card.setContentPadding(0, 0, 0, 0);
                    card.setCardBackgroundColor(Color.parseColor("#36383F"));
                    card.addView(im);
                    im.setBackgroundColor(Color.GRAY);
                    mainlayout.addView(card);
                    break;
                case 2:
                    CardView for_images = new CardView(getApplicationContext());
                    LinearLayout.LayoutParams for_images_params = new LinearLayout.LayoutParams((int) Math.round(w_proc * 91), (int) Math.round(w_proc * 45));
                    for_images_params.leftMargin = (int) Math.round(w_proc * 4);
                    for_images_params.rightMargin = (int) Math.round(w_proc * 4);
                    for_images_params.topMargin = (int) Math.round(w_proc * 4);
                    for_images.setLayoutParams(for_images_params);
                    for_images.setRadius((int) Math.round(w_proc * 10));
                    LinearLayout images_layout = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    images_layout.setLayoutParams(images_layout_params);
                    images_layout.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout.setBackgroundColor(Color.parseColor("#000000"));
                    for_images.addView(images_layout);
                    for (int i = 0; i < 2; i++) {
                        ImageView im2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[i]);
                        im2.setScaleType(ImageView.ScaleType.FIT_XY);
                        im2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params2 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 45), (int) Math.round(w_proc * 45));
                        if (i != 1) {card_params2.rightMargin = (int) Math.round(w_proc);}
                        card_params2.bottomMargin = (int) Math.round(w_proc);
                        im2.setLayoutParams(card_params2);
                        im2.setBackgroundColor(Color.GRAY);
                        images_layout.addView(im2);
                    }
                    mainlayout.addView(for_images);
                    break;
                case 3:
                    CardView for_images3 = new CardView(getApplicationContext());
                    LinearLayout.LayoutParams for_images_params3 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 91), (int) Math.round(w_proc * 91));
                    for_images_params3.leftMargin = (int) Math.round(w_proc * 4);
                    for_images_params3.rightMargin = (int) Math.round(w_proc * 4);
                    for_images_params3.topMargin = (int) Math.round(w_proc * 4);
                    for_images3.setLayoutParams(for_images_params3);
                    for_images3.setRadius((int) Math.round(w_proc * 10));
                    LinearLayout images_layout3_v = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    images_layout3_v.setLayoutParams(images_layout_params3);
                    images_layout3_v.setOrientation(LinearLayout.VERTICAL);
                    images_layout3_v.setBackgroundColor(Color.parseColor("#000000"));
                    for_images3.addView(images_layout3_v);
                    LinearLayout images_layout3_h = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params3_h = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 46));
                    images_layout3_h.setLayoutParams(images_layout_params3_h);
                    images_layout3_h.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout3_h.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout3_v.addView(images_layout3_h);
                    for (int i = 0; i < 2; i++) {
                        ImageView im3_1 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im3_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im3_1.setScaleType(ImageView.ScaleType.FIT_XY);
                        im3_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params3 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 45), (int) Math.round(w_proc * 45));
                        if (i != 1) {card_params3.rightMargin = (int) Math.round(w_proc);}
                        card_params3.bottomMargin = (int) Math.round(w_proc);
                        im3_1.setLayoutParams(card_params3);
                        im3_1.setBackgroundColor(Color.GRAY);
                        images_layout3_h.addView(im3_1);
                        ++j;
                    }
                    for (int i = 0; i < 1; i++) {
                        ImageView im3_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im3_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im3_2.setScaleType(ImageView.ScaleType.FIT_XY);
                        im3_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params3_2 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 91), (int) Math.round(w_proc * 45));
                        im3_2.setLayoutParams(card_params3_2);
                        im3_2.setBackgroundColor(Color.GRAY);
                        images_layout3_v.addView(im3_2);
                        ++j;
                    }
                    mainlayout.addView(for_images3);
                    break;
                case 4:
                    CardView for_images4 = new CardView(getApplicationContext());
                    LinearLayout.LayoutParams for_images_params4 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 91), (int) Math.round(w_proc * 91));
                    for_images_params4.leftMargin = (int) Math.round(w_proc * 4);
                    for_images_params4.rightMargin = (int) Math.round(w_proc * 4);
                    for_images_params4.topMargin = (int) Math.round(w_proc * 4);
                    for_images4.setLayoutParams(for_images_params4);
                    for_images4.setRadius((int) Math.round(w_proc * 10));
                    LinearLayout images_layout4_v = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    images_layout4_v.setLayoutParams(images_layout_params4);
                    images_layout4_v.setOrientation(LinearLayout.VERTICAL);
                    images_layout4_v.setBackgroundColor(Color.parseColor("#000000"));
                    for_images4.addView(images_layout4_v);
                    LinearLayout images_layout4_h_1 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params4_h_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 46));
                    images_layout4_h_1.setLayoutParams(images_layout_params4_h_1);
                    images_layout4_h_1.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout4_h_1.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout4_v.addView(images_layout4_h_1);
                    LinearLayout images_layout4_h_2 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params4_h_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 46));
                    images_layout4_h_2.setLayoutParams(images_layout_params4_h_2);
                    images_layout4_h_2.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout4_h_2.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout4_v.addView(images_layout4_h_2);
                    for (int i = 0; i < 2; i++) {
                        ImageView im4_1 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im4_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im4_1.setScaleType(ImageView.ScaleType.FIT_XY);
                        im4_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params4 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 45), (int) Math.round(w_proc * 45));
                        if (i != 1){card_params4.rightMargin = (int) Math.round(w_proc);}
                        card_params4.bottomMargin = (int) Math.round(w_proc);
                        im4_1.setLayoutParams(card_params4);
                        im4_1.setBackgroundColor(Color.GRAY);
                        images_layout4_h_1.addView(im4_1);
                        ++j;
                    }
                    for (int i = 0; i < 2; i++) {
                        ImageView im4_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im4_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im4_2.setScaleType(ImageView.ScaleType.FIT_XY);
                        im4_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params4_2 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 45), (int) Math.round(w_proc * 45));
                        if (i != 1){card_params4_2.rightMargin = (int) Math.round(w_proc);}
                        im4_2.setLayoutParams(card_params4_2);
                        im4_2.setBackgroundColor(Color.GRAY);
                        images_layout4_h_2.addView(im4_2);
                        ++j;
                    }
                    mainlayout.addView(for_images4);
                    break;
                case 5:
                    CardView for_images5 = new CardView(getApplicationContext());
                    LinearLayout.LayoutParams for_images_params5 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 92), (int) Math.round(w_proc * 77));
                    for_images_params5.leftMargin = (int) Math.round(w_proc * 4);
                    for_images_params5.rightMargin = (int) Math.round(w_proc * 4);
                    for_images_params5.topMargin = (int) Math.round(w_proc * 4);
                    for_images5.setLayoutParams(for_images_params5);
                    for_images5.setRadius((int) Math.round(w_proc * 10));
                    LinearLayout images_layout5_v = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    images_layout5_v.setLayoutParams(images_layout_params5);
                    images_layout5_v.setOrientation(LinearLayout.VERTICAL);
                    images_layout5_v.setBackgroundColor(Color.parseColor("#000000"));
                    for_images5.addView(images_layout5_v);
                    LinearLayout images_layout5_h_1 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params5_h_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 32));
                    images_layout5_h_1.setLayoutParams(images_layout_params5_h_1);
                    images_layout5_h_1.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout5_h_1.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout5_v.addView(images_layout5_h_1);
                    LinearLayout images_layout5_h_2 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params5_h_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 45));
                    images_layout5_h_2.setLayoutParams(images_layout_params5_h_2);
                    images_layout5_h_2.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout5_h_2.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout5_v.addView(images_layout5_h_2);
                    for (int i = 0; i < 3; i++) {
                        ImageView im5_1 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im5_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im5_1.setScaleType(ImageView.ScaleType.FIT_XY);
                        im5_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params5 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 30), (int) Math.round(w_proc * 30));
                        if (i != 2){card_params5.rightMargin = (int) Math.round(w_proc);}
                        card_params5.bottomMargin = (int) Math.round(w_proc * 2);
                        im5_1.setLayoutParams(card_params5);
                        im5_1.setBackgroundColor(Color.GRAY);
                        images_layout5_h_1.addView(im5_1);
                        ++j;
                    }
                    for (int i = 0; i < 2; i++) {
                        ImageView im5_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im5_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im5_2.setScaleType(ImageView.ScaleType.FIT_XY);
                        im5_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params5_2 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 45), (int) Math.round(w_proc * 45));
                        if (i != 1){card_params5_2.rightMargin = (int) Math.round(w_proc * 2);}
                        im5_2.setLayoutParams(card_params5_2);
                        im5_2.setBackgroundColor(Color.GRAY);
                        images_layout5_h_2.addView(im5_2);
                        ++j;
                    }
                    mainlayout.addView(for_images5);
                    break;
                case 6:
                    CardView for_images6 = new CardView(getApplicationContext());
                    LinearLayout.LayoutParams for_images_params6 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 92), (int) Math.round(w_proc * 61));
                    for_images_params6.leftMargin = (int) Math.round(w_proc * 4);
                    for_images_params6.rightMargin = (int) Math.round(w_proc * 4);
                    for_images_params6.topMargin = (int) Math.round(w_proc * 4);
                    for_images6.setLayoutParams(for_images_params6);
                    for_images6.setRadius((int) Math.round(w_proc * 10));
                    LinearLayout images_layout6_v = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params6 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    images_layout6_v.setLayoutParams(images_layout_params6);
                    images_layout6_v.setOrientation(LinearLayout.VERTICAL);
                    images_layout6_v.setBackgroundColor(Color.parseColor("#000000"));
                    for_images6.addView(images_layout6_v);
                    LinearLayout images_layout6_h_1 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params6_h_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 31));
                    images_layout6_h_1.setLayoutParams(images_layout_params6_h_1);
                    images_layout6_h_1.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout6_h_1.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout6_v.addView(images_layout6_h_1);
                    LinearLayout images_layout6_h_2 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params6_h_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 30));
                    images_layout6_h_2.setLayoutParams(images_layout_params6_h_2);
                    images_layout6_h_2.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout6_h_2.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout6_v.addView(images_layout6_h_2);
                    for (int i = 0; i < 3; i++) {
                        ImageView im6_1 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im6_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im6_1.setScaleType(ImageView.ScaleType.FIT_XY);
                        im6_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params6 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 30), (int) Math.round(w_proc * 30));
                        if (i != 2){card_params6.rightMargin = (int) Math.round(w_proc);}
                        card_params6.bottomMargin = (int) Math.round(w_proc * 2);
                        im6_1.setLayoutParams(card_params6);
                        im6_1.setBackgroundColor(Color.GRAY);
                        images_layout6_h_1.addView(im6_1);
                        ++j;
                    }
                    for (int i = 0; i < 3; i++) {
                        ImageView im6_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im6_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im6_2.setScaleType(ImageView.ScaleType.FIT_XY);
                        im6_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params6_2 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 30), (int) Math.round(w_proc * 30));
                        if (i != 2){card_params6_2.rightMargin = (int) Math.round(w_proc);}
                        im6_2.setLayoutParams(card_params6_2);
                        im6_2.setBackgroundColor(Color.GRAY);
                        images_layout6_h_2.addView(im6_2);
                        ++j;
                    }
                    mainlayout.addView(for_images6);
                    break;
                case 7:
                    CardView for_images7 = new CardView(getApplicationContext());
                    LinearLayout.LayoutParams for_images_params7 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 92), (int) Math.round(w_proc * 119));
                    for_images_params7.leftMargin = (int) Math.round(w_proc * 4);
                    for_images_params7.rightMargin = (int) Math.round(w_proc * 4);
                    for_images_params7.topMargin = (int) Math.round(w_proc * 4);
                    for_images7.setLayoutParams(for_images_params7);
                    for_images7.setRadius((int) Math.round(w_proc * 10));
                    LinearLayout images_layout7_v = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params7 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    images_layout7_v.setLayoutParams(images_layout_params7);
                    images_layout7_v.setOrientation(LinearLayout.VERTICAL);
                    images_layout7_v.setBackgroundColor(Color.parseColor("#000000"));
                    for_images7.addView(images_layout7_v);
                    LinearLayout images_layout7_h_1 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params7_h_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 47));
                    images_layout7_h_1.setLayoutParams(images_layout_params7_h_1);
                    images_layout7_h_1.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout7_h_1.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout7_v.addView(images_layout7_h_1);
                    LinearLayout images_layout7_h_2 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params7_h_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 32));
                    images_layout7_h_2.setLayoutParams(images_layout_params7_h_2);
                    images_layout7_h_2.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout7_h_2.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout7_v.addView(images_layout7_h_2);
                    LinearLayout images_layout7_h_3 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params7_h_3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 40));
                    images_layout7_h_3.setLayoutParams(images_layout_params7_h_3);
                    images_layout7_h_3.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout7_h_3.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout7_v.addView(images_layout7_h_3);
                    for (int i = 0; i < 2; i++) {
                        ImageView im7_1 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im7_1.setScaleType(ImageView.ScaleType.FIT_XY);
                        im7_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params7 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 45), (int) Math.round(w_proc * 45));
                        if (i != 1){card_params7.rightMargin = (int) Math.round(w_proc * 2);}
                        card_params7.bottomMargin = (int) Math.round(w_proc * 2);
                        im7_1.setLayoutParams(card_params7);
                        im7_1.setBackgroundColor(Color.GRAY);
                        images_layout7_h_1.addView(im7_1);
                        ++j;
                    }
                    for (int i = 0; i < 3; i++) {
                        ImageView im7_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im7_2.setScaleType(ImageView.ScaleType.FIT_XY);
                        im7_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params7_2 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 30), (int) Math.round(w_proc * 30));
                        if (i != 2){card_params7_2.rightMargin = (int) Math.round(w_proc);}
                        card_params7_2.bottomMargin = (int) Math.round(w_proc * 2);
                        im7_2.setLayoutParams(card_params7_2);
                        im7_2.setBackgroundColor(Color.GRAY);
                        images_layout7_h_2.addView(im7_2);
                        ++j;
                    }
                    for (int i = 0; i < 2; i++) {
                        ImageView im7_3 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_3).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im7_3.setScaleType(ImageView.ScaleType.FIT_XY);
                        im7_3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params7_3 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 45), (int) Math.round(w_proc * 45));
                        if (i != 1){card_params7_3.rightMargin = (int) Math.round(w_proc * 2);}
                        im7_3.setLayoutParams(card_params7_3);
                        im7_3.setBackgroundColor(Color.GRAY);
                        images_layout7_h_3.addView(im7_3);
                        ++j;
                    }
                    mainlayout.addView(for_images7);
                    break;
                case 8:
                    CardView for_images8 = new CardView(getApplicationContext());
                    LinearLayout.LayoutParams for_images_params8 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 92), (int) Math.round(w_proc * 109));
                    for_images_params8.leftMargin = (int) Math.round(w_proc * 4);
                    for_images_params8.rightMargin = (int) Math.round(w_proc * 4);
                    for_images_params8.topMargin = (int) Math.round(w_proc * 4);
                    for_images8.setLayoutParams(for_images_params8);
                    for_images8.setRadius((int) Math.round(w_proc * 10));
                    LinearLayout images_layout8_v = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params8 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    images_layout8_v.setLayoutParams(images_layout_params8);
                    images_layout8_v.setOrientation(LinearLayout.VERTICAL);
                    images_layout8_v.setBackgroundColor(Color.parseColor("#000000"));
                    for_images8.addView(images_layout8_v);
                    LinearLayout images_layout8_h_1 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params8_h_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 32));
                    images_layout8_h_1.setLayoutParams(images_layout_params8_h_1);
                    images_layout8_h_1.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout8_h_1.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout8_v.addView(images_layout8_h_1);
                    LinearLayout images_layout8_h_2 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params8_h_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 47));
                    images_layout8_h_2.setLayoutParams(images_layout_params8_h_2);
                    images_layout8_h_2.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout8_h_2.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout8_v.addView(images_layout8_h_2);
                    LinearLayout images_layout8_h_3 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params8_h_3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 30));
                    images_layout8_h_3.setLayoutParams(images_layout_params8_h_3);
                    images_layout8_h_3.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout8_h_3.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout8_v.addView(images_layout8_h_3);
                    for (int i = 0; i < 3; i++) {
                        ImageView im7_1 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im7_1.setScaleType(ImageView.ScaleType.FIT_XY);
                        im7_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params7 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 30), (int) Math.round(w_proc * 30));
                        if (i != 2){card_params7.rightMargin = (int) Math.round(w_proc);}
                        card_params7.bottomMargin = (int) Math.round(w_proc * 2);
                        im7_1.setLayoutParams(card_params7);
                        im7_1.setBackgroundColor(Color.GRAY);
                        images_layout8_h_1.addView(im7_1);
                        ++j;
                    }
                    for (int i = 0; i < 2; i++) {
                        ImageView im7_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im7_2.setScaleType(ImageView.ScaleType.FIT_XY);
                        im7_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params7_2 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 45), (int) Math.round(w_proc * 45));
                        if (i != 1){card_params7_2.rightMargin = (int) Math.round(w_proc * 2);}
                        card_params7_2.bottomMargin = (int) Math.round(w_proc * 2);
                        im7_2.setLayoutParams(card_params7_2);
                        im7_2.setBackgroundColor(Color.GRAY);
                        images_layout8_h_2.addView(im7_2);
                        ++j;
                    }
                    for (int i = 0; i < 3; i++) {
                        ImageView im7_3 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_3).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im7_3.setScaleType(ImageView.ScaleType.FIT_XY);
                        im7_3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params7_3 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 30), (int) Math.round(w_proc * 30));
                        if (i != 2){card_params7_3.rightMargin = (int) Math.round(w_proc * 2);}
                        im7_3.setLayoutParams(card_params7_3);
                        im7_3.setBackgroundColor(Color.GRAY);
                        images_layout8_h_3.addView(im7_3);
                        ++j;
                    }
                    mainlayout.addView(for_images8);
                    break;
                case 9:
                    CardView for_images9 = new CardView(getApplicationContext());
                    LinearLayout.LayoutParams for_images_params9 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 92), (int) Math.round(w_proc * 92));
                    for_images_params9.leftMargin = (int) Math.round(w_proc * 4);
                    for_images_params9.rightMargin = (int) Math.round(w_proc * 4);
                    for_images_params9.topMargin = (int) Math.round(w_proc * 4);
                    for_images9.setLayoutParams(for_images_params9);
                    for_images9.setRadius((int) Math.round(w_proc * 10));
                    LinearLayout images_layout9_v = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params9 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    images_layout9_v.setLayoutParams(images_layout_params9);
                    images_layout9_v.setOrientation(LinearLayout.VERTICAL);
                    images_layout9_v.setBackgroundColor(Color.parseColor("#000000"));
                    for_images9.addView(images_layout9_v);
                    LinearLayout images_layout9_h_1 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params9_h_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 31));
                    images_layout9_h_1.setLayoutParams(images_layout_params9_h_1);
                    images_layout9_h_1.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout9_h_1.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout9_v.addView(images_layout9_h_1);
                    LinearLayout images_layout9_h_2 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params9_h_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 31));
                    images_layout9_h_2.setLayoutParams(images_layout_params9_h_2);
                    images_layout9_h_2.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout9_h_2.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout9_v.addView(images_layout9_h_2);
                    LinearLayout images_layout9_h_3 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params9_h_3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 30));
                    images_layout9_h_3.setLayoutParams(images_layout_params9_h_3);
                    images_layout9_h_3.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout9_h_3.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout9_v.addView(images_layout9_h_3);
                    for (int i = 0; i < 3; i++) {
                        ImageView im7_1 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im7_1.setScaleType(ImageView.ScaleType.FIT_XY);
                        im7_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params7 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 30), (int) Math.round(w_proc * 30));
                        if (i != 2){card_params7.rightMargin = (int) Math.round(w_proc);}
                        card_params7.bottomMargin = (int) Math.round(w_proc);
                        im7_1.setLayoutParams(card_params7);
                        im7_1.setBackgroundColor(Color.GRAY);
                        images_layout9_h_1.addView(im7_1);
                        ++j;
                    }
                    for (int i = 0; i < 3; i++) {
                        ImageView im7_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im7_2.setScaleType(ImageView.ScaleType.FIT_XY);
                        im7_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params7_2 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 30), (int) Math.round(w_proc * 30));
                        if (i != 2){card_params7_2.rightMargin = (int) Math.round(w_proc);}
                        card_params7_2.bottomMargin = (int) Math.round(w_proc);
                        im7_2.setLayoutParams(card_params7_2);
                        im7_2.setBackgroundColor(Color.GRAY);
                        images_layout9_h_2.addView(im7_2);
                        ++j;
                    }
                    for (int i = 0; i < 3; i++) {
                        ImageView im7_3 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_3).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im7_3.setScaleType(ImageView.ScaleType.FIT_XY);
                        im7_3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params7_3 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 30), (int) Math.round(w_proc * 30));
                        if (i != 2){card_params7_3.rightMargin = (int) Math.round(w_proc);}
                        im7_3.setLayoutParams(card_params7_3);
                        im7_3.setBackgroundColor(Color.GRAY);
                        images_layout9_h_3.addView(im7_3);
                        ++j;
                    }
                    mainlayout.addView(for_images9);
                    break;
                case 10:
                    CardView for_images10 = new CardView(getApplicationContext());
                    LinearLayout.LayoutParams for_images_params10 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 92), (int) Math.round(w_proc * 84));
                    for_images_params10.leftMargin = (int) Math.round(w_proc * 4);
                    for_images_params10.rightMargin = (int) Math.round(w_proc * 4);
                    for_images_params10.topMargin = (int) Math.round(w_proc * 4);
                    for_images10.setLayoutParams(for_images_params10);
                    for_images10.setRadius((int) Math.round(w_proc * 10));
                    LinearLayout images_layout10_v = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params10 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    images_layout10_v.setLayoutParams(images_layout_params10);
                    images_layout10_v.setOrientation(LinearLayout.VERTICAL);
                    images_layout10_v.setBackgroundColor(Color.parseColor("#000000"));
                    for_images10.addView(images_layout10_v);
                    LinearLayout images_layout10_h_1 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params10_h_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 31));
                    images_layout10_h_1.setLayoutParams(images_layout_params10_h_1);
                    images_layout10_h_1.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout10_h_1.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout10_v.addView(images_layout10_h_1);
                    LinearLayout images_layout10_h_2 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params10_h_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 23));
                    images_layout10_h_2.setLayoutParams(images_layout_params10_h_2);
                    images_layout10_h_2.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout10_h_2.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout10_v.addView(images_layout10_h_2);
                    LinearLayout images_layout10_h_3 = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams images_layout_params10_h_3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 30));
                    images_layout10_h_3.setLayoutParams(images_layout_params10_h_3);
                    images_layout10_h_3.setOrientation(LinearLayout.HORIZONTAL);
                    images_layout10_h_3.setBackgroundColor(Color.parseColor("#000000"));
                    images_layout10_v.addView(images_layout10_h_3);
                    for (int i = 0; i < 3; i++) {
                        ImageView im7_1 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im7_1.setScaleType(ImageView.ScaleType.FIT_XY);
                        im7_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params7 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 30), (int) Math.round(w_proc * 30));
                        if (i != 2){card_params7.rightMargin = (int) Math.round(w_proc);}
                        card_params7.bottomMargin = (int) Math.round(w_proc);
                        im7_1.setLayoutParams(card_params7);
                        im7_1.setBackgroundColor(Color.GRAY);
                        images_layout10_h_1.addView(im7_1);
                        ++j;
                    }
                    for (int i = 0; i < 4; i++) {
                        ImageView im7_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im7_2.setScaleType(ImageView.ScaleType.FIT_XY);
                        im7_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params7_2 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 22), (int) Math.round(w_proc * 22));
                        if (i != 3 && i != 1){card_params7_2.rightMargin = (int) Math.round(w_proc);}
                        if (i == 1){card_params7_2.rightMargin = (int) Math.round(w_proc * 2);}
                        card_params7_2.bottomMargin = (int) Math.round(w_proc);
                        im7_2.setLayoutParams(card_params7_2);
                        im7_2.setBackgroundColor(Color.GRAY);
                        images_layout10_h_2.addView(im7_2);
                        ++j;
                    }
                    for (int i = 0; i < 3; i++) {
                        ImageView im7_3 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_3).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[j]);
                        im7_3.setScaleType(ImageView.ScaleType.FIT_XY);
                        im7_3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                OpenPicture(view);
                            }
                        });
                        LinearLayout.LayoutParams card_params7_3 = new LinearLayout.LayoutParams((int) Math.round(w_proc * 30), (int) Math.round(w_proc * 30));
                        if (i != 2){card_params7_3.rightMargin = (int) Math.round(w_proc);}
                        im7_3.setLayoutParams(card_params7_3);
                        im7_3.setBackgroundColor(Color.GRAY);
                        images_layout10_h_3.addView(im7_3);
                        ++j;
                    }
                    mainlayout.addView(for_images10);
                    break;
            }
            View line2 = new View(getApplicationContext());
            LinearLayout.LayoutParams gg = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
            gg.topMargin = (int) Math.round(w_proc * 4);
            gg.bottomMargin = (int) Math.round(-w_proc * 4) - 5;
            line2.setLayoutParams(gg);
            line2.setBackgroundResource(R.drawable.hz_kakaja_to_parasha);
            line2.setPadding(0, 0, 0, 0);
            mainlayout.addView(line2);
        }
    }

    public void OpenPicture(View view) {
        String folderToSave = (getApplicationContext().getFileStreamPath("push.jpg").getPath()).toString();
        OutputStream fOut = null;
        Time time = new Time();
        time.setToNow();
        System.out.println(folderToSave);
//        Toast.makeText(getApplicationContext(), folderToSave, Toast.LENGTH_LONG).show();
        try {
            File file = new File(folderToSave);
            if (file.exists()) {
                file.delete();
                file = new File(folderToSave);
            }
            fOut = new FileOutputStream(file);
            Bitmap bitmap = ((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
//            Toast.makeText(getApplicationContext(), "Получилось: " + folderToSave, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SeePicture.class);
            startActivity(intent);
            overridePendingTransition(R.anim.top, R.anim.top1);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Подождите чуть-чуть", Toast.LENGTH_SHORT).show();
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
    }

    public void buuton_f(View view) {
        Intent intent = new Intent(this, Friends.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left, R.anim.left1);
    }

    public void exit(View view){
        this.deleteDatabase("myDB");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
