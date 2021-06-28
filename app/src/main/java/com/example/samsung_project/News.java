package com.example.samsung_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.KeyEventDispatcher;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.format.Time;
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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;


public class News extends AppCompatActivity {
    public long current_im = 1;
    public int in_block = 0;
    public String key;
    boolean flag = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);

        Display display = getWindowManager().getDefaultDisplay();
        int width_of_screen = display.getWidth();
        int height_of_screen = display.getHeight();
        double h_proc = height_of_screen / 100.;
        double w_proc = width_of_screen / 100.;
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
        LinearLayout mainlayout = (LinearLayout) findViewById(R.id.ln);
        TextView t = new TextView(getApplicationContext());
        LinearLayout.LayoutParams t_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        t_params.topMargin = (int) Math.round(w_proc * 4);
        t_params.leftMargin = (int) Math.round(w_proc * 8);
        t_params.gravity = Gravity.START;
        t.setLayoutParams(t_params);
        t.setText("Новости");
        t.setTextColor(Color.parseColor("#ffffff"));
        t.setTextSize((int) Math.round(w_proc * 2));
        mainlayout.addView(t);

        View line1 = new View(getApplicationContext());
        LinearLayout.LayoutParams line1_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 3 / 2));
        line1_params.topMargin = (int) Math.round(w_proc * 4);
        line1.setLayoutParams(line1_params);
        line1.setBackgroundColor(Color.parseColor("#000000"));
        mainlayout.addView(line1);
        LinearLayout button = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams butoon_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        button.setLayoutParams(butoon_params);
        button.setBackgroundColor(Color.parseColor("#434446"));
        CardView card = new CardView(getApplicationContext());
        LinearLayout.LayoutParams card_params = new LinearLayout.LayoutParams((int) Math.round(w_proc * 100), (int) Math.round(w_proc * 10));
        card_params.topMargin = (int) Math.round(w_proc * 2);
        card_params.gravity = Gravity.CENTER;
        card.setLayoutParams(card_params);
        card.setRadius((int) Math.round(w_proc * 3));
        LinearLayout for_pen = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams for_pen_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for_pen_params.weight = 10;
        for_pen_params.gravity = Gravity.CENTER;
        for_pen.setLayoutParams(for_pen_params);
        for_pen.setOrientation(LinearLayout.VERTICAL);
        ImageView pen = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams pen_params = new LinearLayout.LayoutParams((int) Math.round(w_proc * 5), (int) Math.round(w_proc * 5));
        pen_params.gravity = Gravity.END;
        pen_params.topMargin = (int) Math.round(w_proc * 5 / 2);
        pen_params.rightMargin = (int) Math.round(w_proc);
        pen.setLayoutParams(pen_params);
        pen.setImageResource(R.drawable.pen_icon);
        pen.setBackgroundColor(Color.parseColor("#434446"));
        for_pen.addView(pen);
        button.addView(for_pen);
        TextView text = new TextView(getApplicationContext());
        LinearLayout.LayoutParams text_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        text_params.topMargin = (int) Math.round(w_proc);
        text_params.weight = 10;
        text.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        text.setLayoutParams(text_params);
        text.setPadding(0, (int) Math.round(w_proc / 2), (int) Math.round(w_proc * 5), 0);
        text.setText("Создать запись");
        text.setTextColor(Color.parseColor("#ffffff"));
        text.setBackgroundColor(Color.parseColor("#434446"));
        text.setTextSize(18);
        button.addView(text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnew(view);
            }
        });
        card.addView(button);
        mainlayout.addView(card);
        View line2 = new View(getApplicationContext());
        LinearLayout.LayoutParams line2_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 1.5));
        line2_params.topMargin = (int) Math.round(w_proc * 2);
        line2_params.bottomMargin = (int) Math.round(-w_proc * 5.5);
        line2.setLayoutParams(line2_params);
        line2.setBackgroundColor(Color.parseColor("#000000"));
        mainlayout.addView(line2);
        for (int i = 0; i < 3; i++) {
            try {
                Async_next_post task = new Async_next_post();
//                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, current_im);
                task.execute(current_im);
                current_im++;
            } catch (Exception ex) {
                break;
            }
        }
        scrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (scrollView.getChildAt(0).getBottom()
                                <= (scrollView.getHeight() + scrollView.getScrollY())) {
//                            try {
////                                Next_posts();
////                                Next_posts();
////                                Next_posts();
//                            } catch (IOException | JSONException e) {
//                                e.printStackTrace();
//                            }
                            for (int i = 0; i < 3; i++) {
                                try {
                                    Async_next_post task = new Async_next_post();
//                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, current_im);
                                    task.execute(current_im);
                                    current_im++;
                                } catch (Exception ex) {
                                    break;
                                }
                            }
                        }
                    }
                });
    }

    //    @SuppressLint("SetTextI18n")
    private class Async_next_post extends AsyncTask<Long, Void, String[][]> {
        @Override
        protected String[][] doInBackground(Long... nomer) {
            try {
                if (flag) {
                    String url = "http://vsn.intercom.pro:9080/new/" + key + "/" + nomer[0];
                    URL obj = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                    connection.setRequestMethod("GET");
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    JSONObject root = new JSONObject(in.readLine());
                    in.close();
                    System.out.println(root.getString("text"));
                    System.out.println(1233333321);
                    if (!root.getString("text").equals("nope")) {
                        String text = root.getString("text");
                        String title = root.getString("title");
                        String img = root.getString("u_photo");
                        String name = root.getString("name");
                        String count_fotos = root.getString("count_photos");
                        String[] images = new String[Integer.parseInt(count_fotos)];
                        for (int i = 0; i < Integer.parseInt(count_fotos); i++) {
                            String image = root.getString("photo");
                            images[i] = image;
                        }
                        return new String[][]{new String[]{text, title, img, name, count_fotos}, images};
                    } else {
                        flag = false;
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            return null;
        }

        protected void onPostExecute(String[][] result) {
            Display display = getWindowManager().getDefaultDisplay();
            int width_of_screen = display.getWidth();
            int height_of_screen = display.getHeight();
            double h_proc = height_of_screen / 100.;
            double w_proc = width_of_screen / 100.;
            int width = width_of_screen;
            in_block = 0;
            if (result == null) {
                return;
            }
            String text = result[0][0];
            String title = result[0][1];
            String img = result[0][2];
            String name = result[0][3];
            int count_fotos = Integer.parseInt(result[0][4]);
            String[] images = new String[count_fotos];
            if (count_fotos > 0) {
                images = result[1];
            }
            LinearLayout frameLayout = (LinearLayout) findViewById(R.id.ln);
            //        new DownloadImageTask(im).execute("https://images-ext-1.discordapp.net/external/qyfnjk5ZErAzQAqoFsKKmWoCdHisH_Kh4tBCFn0k940/%3Fsize%3D660x660%26quality%3D96%26sign%3De6467d23fd76b8cd213f681e7465e330%26type%3Dalbum/https/sun9-21.userapi.com/impg/3Z8gyexEsZRZu3Vg-NxyMXcNpkUXuLBNX5NIlg/i2z774wn3i8.jpg" + current_im + ".png");
            LinearLayout linLayout = new LinearLayout(getApplicationContext());
            linLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linLayout.setLayoutParams(linLayoutParam);
            View line1 = new View(getApplicationContext());
//            View line2 = new View(getApplicationContext());
            LinearLayout.LayoutParams g1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 3 / 2));
//            LinearLayout.LayoutParams g2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, w_proc * 2);
            g1.topMargin = (int) Math.round(w_proc * 4);
            line1.setLayoutParams(g1);
//            line2.setLayoutParams(g2);
            line1.setBackgroundColor(Color.parseColor("#000000"));
//            line2.setBackgroundColor(Color.parseColor("#000000"));
            line1.setPadding(0, 0, 0, 0);
//        ViewGroup.LayoutParams im_params = new ViewGroup.LayoutParams();
            TextView textView = new TextView(getApplicationContext());
            LinearLayout.LayoutParams text_of_post_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            text_of_post_params.topMargin = (int) Math.round(w_proc * 4);
            text_of_post_params.leftMargin = (int) Math.round(w_proc * 4);
            text_of_post_params.rightMargin = (int) Math.round(w_proc * 4);
            text_of_post_params.gravity = Gravity.FILL;
            textView.setLayoutParams(text_of_post_params);
            textView.setText(title + "\n" + text);
            textView.setLinksClickable(true);
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            textView.setTextSize(16);

            linLayout.setId((int) current_im);
            in_block++;
            line1.setId(in_block);
            in_block++;
            textView.setId(in_block);
            in_block++;
//            line2.setId(in_block);
            linLayout.addView(line1);

//        count_fotos = 1; //maximum 10

            LinearLayout logo_box = new LinearLayout(getApplicationContext());
            logo_box.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams for_logo_box = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            logo_box.setLayoutParams(for_logo_box);

            ImageView kartinka = new ImageView(getApplicationContext());
            System.out.println(545);
            new DownloadImageTask(kartinka).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + img);
            LinearLayout.LayoutParams for_kartinka = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            kartinka.setLayoutParams(for_kartinka);

            CardView crd_for_button = new CardView(getApplicationContext());
            LinearLayout.LayoutParams crd_for_button_params = new LinearLayout.LayoutParams((int) Math.round(w_proc * 10), (int) Math.round(w_proc * 10));
            crd_for_button_params.leftMargin = (int) Math.round(w_proc * 4);
            crd_for_button_params.topMargin = (int) Math.round(w_proc * 4);
            crd_for_button.setLayoutParams(crd_for_button_params);
            crd_for_button.setRadius((int) Math.round(w_proc * 2));
            crd_for_button.setContentPadding(0, 0, 0, 0);
            crd_for_button.setCardBackgroundColor(Color.LTGRAY);
            kartinka.setScaleType(ImageView.ScaleType.CENTER_CROP);
            crd_for_button.addView(kartinka);

            logo_box.addView(crd_for_button);

            TextView fio = new TextView(getApplicationContext());
            LinearLayout.LayoutParams for_fio = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 10));
            for_fio.topMargin = (int) Math.round(w_proc * 4);
            fio.setLayoutParams(for_fio);
            fio.setGravity(Gravity.CENTER_VERTICAL);
            fio.setPadding((int) Math.round(w_proc * 2), 0, 0, 0);
            fio.setTextSize(16);
            fio.setTextColor(Color.parseColor("#FFFFFF"));
            fio.setText(name);

            logo_box.addView(fio);

            linLayout.addView(logo_box);
            linLayout.addView(textView);
            switch (count_fotos) {
                case 0:
                    break;
                case 1:
                    ImageView im = new ImageView(getApplicationContext());
                    new DownloadImageTask(im).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);

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
                    linLayout.addView(card);
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
                        new DownloadImageTask(im2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    linLayout.addView(for_images);
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
                        new DownloadImageTask(im3_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    for (int i = 0; i < 1; i++) {
                        ImageView im3_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im3_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    linLayout.addView(for_images3);
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
                        new DownloadImageTask(im4_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    for (int i = 0; i < 2; i++) {
                        ImageView im4_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im4_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    linLayout.addView(for_images4);
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
                        new DownloadImageTask(im5_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    for (int i = 0; i < 2; i++) {
                        ImageView im5_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im5_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    linLayout.addView(for_images5);
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
                        new DownloadImageTask(im6_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    for (int i = 0; i < 3; i++) {
                        ImageView im6_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im6_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    linLayout.addView(for_images6);
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
                        new DownloadImageTask(im7_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    for (int i = 0; i < 3; i++) {
                        ImageView im7_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    for (int i = 0; i < 2; i++) {
                        ImageView im7_3 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_3).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    linLayout.addView(for_images7);
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
                        new DownloadImageTask(im7_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    for (int i = 0; i < 2; i++) {
                        ImageView im7_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    for (int i = 0; i < 3; i++) {
                        ImageView im7_3 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_3).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    linLayout.addView(for_images8);
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
                        new DownloadImageTask(im7_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    for (int i = 0; i < 3; i++) {
                        ImageView im7_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    for (int i = 0; i < 3; i++) {
                        ImageView im7_3 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_3).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    linLayout.addView(for_images9);
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
                        new DownloadImageTask(im7_1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    for (int i = 0; i < 4; i++) {
                        ImageView im7_2 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    for (int i = 0; i < 3; i++) {
                        ImageView im7_3 = new ImageView(getApplicationContext());
                        new DownloadImageTask(im7_3).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://vsn.intercom.pro:9080/image/" + images[0]);
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
                    }
                    linLayout.addView(for_images10);
                    break;
            }
            frameLayout.addView(linLayout);
        }
    }


    public void addnew(View view) {
        Intent intent = new Intent(this, AddNew.class);
        startActivity(intent);
        overridePendingTransition(R.anim.top, R.anim.top1);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    public void Next_posts() throws IOException, JSONException {
        if (flag) {
            String url = "http://vsn.intercom.pro:9080/new/" + key + "/" + current_im;
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            JSONObject root = new JSONObject(in.readLine());
            in.close();
            System.out.println(root.getString("text"));
            System.out.println(1233333321);
            if (!root.getString("text").equals("nope")) {

                String text = root.getString("text");
                String title = root.getString("title");
                String image = root.getString("photo");
                String img = root.getString("u_photo");
                String name = root.getString("name");
                int count_fotos = Integer.parseInt(root.getString("count_photos").toString());
                System.out.println(1);

                Display display = getWindowManager().getDefaultDisplay();
                int width_of_screen = display.getWidth();
                int height_of_screen = display.getHeight();
                int h_proc = height_of_screen / 100;
                int w_proc = width_of_screen / 100;
                int width = width_of_screen;

                in_block = 0;


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

                linLayout.setId((int) current_im);
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
                System.out.println(img);
                System.out.println(545);
                new DownloadImageTask(kartinka).execute("http://vsn.intercom.pro:9080/image/" + img);
                LinearLayout.LayoutParams for_kartinka = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                kartinka.setLayoutParams(for_kartinka);

                CardView crd_for_button = new CardView(getApplicationContext());
                LinearLayout.LayoutParams crd_for_button_params = new LinearLayout.LayoutParams(w_proc * 10, w_proc * 10);
                crd_for_button_params.leftMargin = w_proc * 4;
                crd_for_button_params.topMargin = w_proc * 4;
                crd_for_button.setLayoutParams(crd_for_button_params);
                crd_for_button.setRadius(w_proc * 2);
                crd_for_button.setContentPadding(0, 0, 0, 0);
                crd_for_button.setCardBackgroundColor(Color.LTGRAY);
                kartinka.setScaleType(ImageView.ScaleType.CENTER_CROP);
                crd_for_button.addView(kartinka);

                logo_box.addView(crd_for_button);

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
                switch (count_fotos) {
                    case 0:
                        break;
                    case 1:
                        ImageView im = new ImageView(getApplicationContext());

                        new DownloadImageTask(im).execute("http://vsn.intercom.pro:9080/image/" + image);

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
            } else {
                flag = false;
            }
        }
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

    public void Video(View view) {
        Intent intent = new Intent(this, Video.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left, R.anim.left1);
        this.finish();
    }

    public void Friends(View view) {
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