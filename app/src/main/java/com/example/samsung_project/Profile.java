package com.example.samsung_project;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class Profile extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getSupportActionBar().hide();

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

//        new DownloadImageTask(im).execute("https://images-ext-1.discordapp.net/external/qyfnjk5ZErAzQAqoFsKKmWoCdHisH_Kh4tBCFn0k940/%3Fsize%3D660x660%26quality%3D96%26sign%3De6467d23fd76b8cd213f681e7465e330%26type%3Dalbum/https/sun9-21.userapi.com/impg/3Z8gyexEsZRZu3Vg-NxyMXcNpkUXuLBNX5NIlg/i2z774wn3i8.jpg");
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

        long vsnid = 984317834;

        TextView cifarki = new TextView(getApplicationContext());
        LinearLayout.LayoutParams for_cifarki = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for_cifarki.leftMargin = w_proc * 2;
        for_cifarki.topMargin = w_proc * 2;
        for_cifarki.bottomMargin = w_proc * 2;
        cifarki.setText("" + vsnid);
        cifarki.setTextSize(20);
        cifarki.setTextColor(Color.parseColor("#FFFFFF"));
        cifarki.setLayoutParams(for_cifarki);

        id_block.addView(cifarki);

        ImageButton copy = new ImageButton(getApplicationContext());
        LinearLayout.LayoutParams for_copy = new LinearLayout.LayoutParams(100, 100);
        for_copy.topMargin = w_proc;
        for_copy.leftMargin = -w_proc;
        copy.setImageResource(R.drawable.copy);
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

        id_block.addView(copy);

        mainlayout.addView(id_block);

        LinearLayout foto_and_name = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams for_foto_and_name = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, w_proc * 32);
        for_foto_and_name.topMargin = w_proc * 2;
        foto_and_name.setOrientation(LinearLayout.HORIZONTAL);
        foto_and_name.setLayoutParams(for_foto_and_name);
        foto_and_name.setGravity(Gravity.START);

        ImageView im = new ImageView(getApplicationContext());
        new DownloadImageTask(im).execute("https://images-ext-1.discordapp.net/external/qyfnjk5ZErAzQAqoFsKKmWoCdHisH_Kh4tBCFn0k940/%3Fsize%3D660x660%26quality%3D96%26sign%3De6467d23fd76b8cd213f681e7465e330%26type%3Dalbum/https/sun9-21.userapi.com/impg/3Z8gyexEsZRZu3Vg-NxyMXcNpkUXuLBNX5NIlg/i2z774wn3i8.jpg");
        LinearLayout.LayoutParams im_params = new LinearLayout.LayoutParams(w_proc * 25, w_proc * 25);
        im.setLayoutParams(im_params);
        CardView cardView = new CardView(getApplicationContext());
        LinearLayout.LayoutParams card_params = new LinearLayout.LayoutParams(w_proc * 25, w_proc * 25);
        card_params.leftMargin = w_proc * 4;
        cardView.setLayoutParams(card_params);
        cardView.setRadius(w_proc * 7);
        cardView.setContentPadding(0, 0, 0, 0);
        cardView.setCardBackgroundColor(Color.LTGRAY);
        cardView.addView(im);

        foto_and_name.addView(cardView);

        LinearLayout name = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams for_name = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        name.setOrientation(LinearLayout.VERTICAL);
        name.setLayoutParams(for_name);
        name.setGravity(Gravity.START);

        String name_of_chelik = "Биба Абоба Бобович";
        String about_of_chelik = "Эчпочмааааааааааааааааааааааак";

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
        crd_for_button.setCardBackgroundColor(Color.LTGRAY);
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

        long count = 50;

        TextView count_friends = new TextView(getApplicationContext());
        LinearLayout.LayoutParams count_friends_params = new LinearLayout.LayoutParams(w_proc * 20, LinearLayout.LayoutParams.MATCH_PARENT);
        count_friends_params.leftMargin = w_proc;
        count_friends_params.topMargin = h_proc / 10;
        count_friends.setLayoutParams(count_friends_params);
        count_friends.setMinWidth(w_proc * 20);
        count_friends.setText("" + count);
        count_friends.setTextColor(Color.parseColor("#555555"));
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
        friends_scroll.setHorizontalScrollBarEnabled(false);
        friends_scroll.setId(16516);

        LinearLayout friends_layout = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams friends_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        friends_layout.setLayoutParams(friends_layout_params);
        friends_layout.setOrientation(LinearLayout.HORIZONTAL);
        friends_layout.setId(228);

        friends_scroll.addView(friends_layout);

        //я это сделал и нихуя не робит, вместо этого можно for поставить, но если друзей больше 5к, press F
        friends_scroll.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (friends_scroll.getChildAt(0).getRight()
                                <= (friends_scroll.getWidth() + friends_scroll.getScrollX())) {
                            Next_friend();
                        }
                    }
                });
        mainlayout.addView(friends_scroll);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void Next_friend(){
        Display display = getWindowManager().getDefaultDisplay();
        int width_of_screen = display.getWidth();
        int height_of_screen = display.getHeight();
        int h_proc = height_of_screen / 100;
        int w_proc = width_of_screen / 100;
        String name_of_chelik = "Биба Абоба Бобович";

        // короч чекай, на сколько я понял, проблемма в этой хуйне, типа он её найти не может. Я
        // в душе не ебу как это исправить.(листается по горизонтале, как в вк)
        // *кроме этой строчки
        @SuppressLint("ResourceType") LinearLayout friends_layout = findViewById(228);

        LinearLayout one_friend = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams one_friend_params = new LinearLayout.LayoutParams(h_proc * 8, LinearLayout.LayoutParams.WRAP_CONTENT);
        one_friend_params.leftMargin = w_proc * 4;
        one_friend.setLayoutParams(one_friend_params);
        one_friend.setOrientation(LinearLayout.VERTICAL);

        ImageView logo = new ImageView(getApplicationContext());
        new DownloadImageTask(logo).execute("https://images-ext-1.discordapp.net/external/qyfnjk5ZErAzQAqoFsKKmWoCdHisH_Kh4tBCFn0k940/%3Fsize%3D660x660%26quality%3D96%26sign%3De6467d23fd76b8cd213f681e7465e330%26type%3Dalbum/https/sun9-21.userapi.com/impg/3Z8gyexEsZRZu3Vg-NxyMXcNpkUXuLBNX5NIlg/i2z774wn3i8.jpg");
        LinearLayout.LayoutParams logo_params = new LinearLayout.LayoutParams(h_proc * 8, h_proc * 8);
        logo.setLayoutParams(logo_params);
        CardView circle_im = new CardView(getApplicationContext());
        LinearLayout.LayoutParams circle_im_params = new LinearLayout.LayoutParams(h_proc * 8, h_proc * 8);
        circle_im.setLayoutParams(circle_im_params);
        circle_im.setRadius(h_proc * 2);
        circle_im.setContentPadding(0, 0, 0, 0);
        circle_im.setCardBackgroundColor(Color.LTGRAY);
        circle_im.addView(logo);

        one_friend.addView(circle_im);

        TextView fio_friend = new TextView(getApplicationContext());
        LinearLayout.LayoutParams fio_friend_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        fio_friend.setLayoutParams(fio_friend_params);
        fio_friend.setText(name_of_chelik);
        fio_friend.setTextSize(10);
        fio_friend.setTextColor(Color.parseColor("#FFFFFF"));
        fio_friend.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        one_friend.addView(fio_friend);

        friends_layout.addView(one_friend);
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
