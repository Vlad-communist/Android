package com.example.samsung_project;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.format.Time;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class AddNew extends AppCompatActivity {
    private final int Pick_image = 1;
    private String selectedImagePath;
    public int count = 0;
    public int shiiiit = 0;
    public Vector<String> list = new Vector<String>();

    public static class FilesUploadingTask extends AsyncTask<Void, Void, String> {

        // Конец строки
        private String lineEnd = "\r\n";
        // Два тире
        private String twoHyphens = "--";
        // Разделитель
        private String boundary = "----WebKitFormBoundary9xFB2hiUhzqbBQ4M";

        // Переменные для считывания файла в оперативную память
        private int bytesRead, bytesAvailable, bufferSize;
        private byte[] buffer;
        private int maxBufferSize = 1 * 1024 * 1024;

        // Путь к файлу в памяти устройства

        // Адрес метода api для загрузки файла на сервер
        public String API_FILES_UPLOADING_PATH = "http://vsn.intercom.pro:9080/add_new";

        // Ключ, под которым файл передается на сервер
        public Vector<String> list = new Vector<String>();

        public FilesUploadingTask(Vector<String> filePath, String h) {
            this.list = filePath;
            this.API_FILES_UPLOADING_PATH += "/" + h;
        }

        @Override
        protected String doInBackground(Void... params) {
            // Результат выполнения запроса, полученный от сервера
            String result = null;

            try {
                // Создание ссылки для отправки файла
                URL uploadUrl = new URL(API_FILES_UPLOADING_PATH);

                // Создание соединения для отправки файла
                HttpURLConnection connection = (HttpURLConnection) uploadUrl.openConnection();

                // Разрешение ввода соединению
                connection.setDoInput(true);
                // Разрешение вывода соединению
                connection.setDoOutput(true);
                // Отключение кеширования
                connection.setUseCaches(false);

                // Задание запросу типа POST
                connection.setRequestMethod("POST");

                // Задание необходимых свойств запросу
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);


                // Формирование multipart контента
                for (int i = 1; i <= list.size(); ++i) {
                    try {
                        // Создание потока для записи в соединение
                        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

                        // Начало контента
                        outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                        // Заголовок элемента формы
                        outputStream.writeBytes("Content-Disposition: form-data; name=\"" +
                                "file" + i + "\"; filename=\"" + list.get(i-1) + "\"" + lineEnd);
                        // Тип данных элемента формы
                        outputStream.writeBytes("Content-Type: image/jpeg" + lineEnd);
                        // Конец заголовка
                        outputStream.writeBytes(lineEnd);

                        // Поток для считывания файла в оперативную память
                        FileInputStream fileInputStream = new FileInputStream(new File(list.get(i-1)));

                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        // Считывание файла в оперативную память и запись его в соединение
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {
                            outputStream.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                        }

                        // Конец элемента формы
                        outputStream.writeBytes(lineEnd);
                        outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                        fileInputStream.close();
                        outputStream.flush();
                        outputStream.close();
                    } catch (Exception ex){
                        System.out.println("!!!!!!!!!!!!!!!!!!!!" + ex);
                    }
                }
                // Получение ответа от сервера
                int serverResponseCode = connection.getResponseCode();
                // Закрытие соединений и потоков


                // Считка ответа от сервера в зависимости от успеха
                if (serverResponseCode == 200) {
                    result = readStream(connection.getInputStream());
                } else {
                    result = readStream(connection.getErrorStream());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        // Считка потока в строку
        public static String readStream(InputStream inputStream) throws IOException {
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            return buffer.toString();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        Display display = getWindowManager().getDefaultDisplay();
        int width_of_screen = display.getWidth();
        int height_of_screen = display.getHeight();
        double h_proc = height_of_screen / 100.;
        double w_proc = width_of_screen / 100.;
        setContentView(R.layout.addnews);
        LinearLayout mainlayout = (LinearLayout) findViewById(R.id.mainlayout);
        LinearLayout fotos = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams fotos_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fotos_params.topMargin = (int) Math.round(w_proc * 4);
        fotos_params.leftMargin = (int) Math.round(w_proc * 4);
        fotos_params.rightMargin = (int) Math.round(w_proc * 4);
        fotos.setLayoutParams(fotos_params);
        mainlayout.addView(fotos);
        Button add_picture = new Button(getApplicationContext());
        LinearLayout.LayoutParams dsasdasd = new LinearLayout.LayoutParams((int) Math.round(w_proc * 92), (int) Math.round(w_proc * 10));
        dsasdasd.leftMargin = (int) Math.round(w_proc * 4);
        add_picture.setLayoutParams(dsasdasd);
        add_picture.setText("Добавить картинку");
        add_picture.setBackgroundResource(R.drawable.btnclr);
        add_picture.setTextColor(Color.WHITE);
        mainlayout.addView(add_picture);
        add_picture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //Тип получаемых объектов - image:
                photoPickerIntent.setType("image/*");
                photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                startActivityForResult(photoPickerIntent, Pick_image);
            }
        });
        EditText title = new EditText(getApplicationContext());
        LinearLayout.LayoutParams title_params = new LinearLayout.LayoutParams((int) Math.round(w_proc * 92), ViewGroup.LayoutParams.WRAP_CONTENT);
        title_params.leftMargin = (int) Math.round(w_proc * 4);
        title_params.topMargin = (int) Math.round(w_proc * 4);
        title.setLayoutParams(title_params);
        title.setMaxLines(3);
        title.setHint("Заголовок");
        title.setTextColor(Color.WHITE);
        title.setHintTextColor(Color.GRAY);
        title.setBackgroundResource(R.drawable.style_for_news);
        title.setId(230);
        mainlayout.addView(title);
        EditText text = new EditText(getApplicationContext());
        LinearLayout.LayoutParams text_params = new LinearLayout.LayoutParams((int) Math.round(w_proc * 92), ViewGroup.LayoutParams.WRAP_CONTENT);
        text_params.leftMargin = (int) Math.round(w_proc * 4);
        text_params.topMargin = (int) Math.round(w_proc * 4);
        text.setLayoutParams(text_params);
        text.setMaxLines(15);
        text.setHint("Техт поста");
        text.setTextColor(Color.WHITE);
        text.setHintTextColor(Color.GRAY);
        text.setBackgroundResource(R.drawable.style_for_news);
        text.setId(231);
        mainlayout.addView(text);

        Button add_new = new Button(getApplicationContext());
        LinearLayout.LayoutParams add_new_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 10));
        add_new_params.leftMargin = (int) Math.round(w_proc * 4);
        add_new_params.topMargin = (int) Math.round(w_proc * 4);
        add_new_params.rightMargin = (int) Math.round(w_proc * 4);
        add_new_params.bottomMargin = (int) Math.round(w_proc * 4);
        add_new.setLayoutParams(add_new_params);
        add_new.setText("Создать новость");
        add_new.setTextColor(Color.WHITE);
        add_new.setId(228);
        add_new.setBackgroundResource(R.drawable.btnclr);
        mainlayout.addView(add_new);
        add_new.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                add_new(view);
            }
        });
    }

    public void add_new(View view) {
        @SuppressLint("ResourceType") EditText t1 = (EditText) findViewById(230);
        @SuppressLint("ResourceType") EditText t = (EditText) findViewById(231);
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("sq", null, null, null, null, null, null);
        c.moveToNext();
        String key = c.getString(1);
        c.close();
        String title = t1.getText().toString();
        String text = t.getText().toString();
        new FilesUploadingTask(list, title + "/" + text + "/" + key).execute();
        Intent intent = new Intent(this, News.class);
        startActivity(intent);
        this.finish();
    }

    @SuppressLint("ResourceType")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            Display display = getWindowManager().getDefaultDisplay();
            int width_of_screen = display.getWidth();
            int height_of_screen = display.getHeight();
            double h_proc = height_of_screen / 100.;
            double w_proc = width_of_screen / 100.;
// When an Image is picked
            if (requestCode == Pick_image && resultCode == RESULT_OK
                    && null != data) {
// Get the Image from data
                String imageEncoded;
                List<String> imagesEncodedList;
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {
                    if (count + 1 > 10) {
                        Toast.makeText(this, "Вы выбрали слишком много изображений, максимум 10", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Uri mImageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(mImageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    LinearLayout mainlayout = (LinearLayout) findViewById(R.id.mainlayout);
                    count++;
                    @SuppressLint("ResourceType") Button a = (Button) findViewById(228);
                    mainlayout.removeView(a);

                    ImageView kartinka = new ImageView(getApplicationContext());
                    LinearLayout.LayoutParams for_kartinka = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    kartinka.setLayoutParams(for_kartinka);

                    LinearLayout one_picture = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams onr_picture_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    onr_picture_params.leftMargin = (int) Math.round(w_proc * 4);
                    onr_picture_params.topMargin = (int) Math.round(w_proc * 4);
                    one_picture.setLayoutParams(onr_picture_params);
                    one_picture.setOrientation(LinearLayout.HORIZONTAL);
                    CardView crd_for_button = new CardView(getApplicationContext());
                    LinearLayout.LayoutParams crd_for_button_params = new LinearLayout.LayoutParams((int) Math.round(w_proc * 78), (int) Math.round(w_proc * 44));
                    crd_for_button.setLayoutParams(crd_for_button_params);
                    crd_for_button.setRadius((int) Math.round(w_proc * 2));
                    crd_for_button.setContentPadding(0, 0, 0, 0);
                    crd_for_button.setCardBackgroundColor(Color.LTGRAY);
                    kartinka.setScaleType(ImageView.ScaleType.FIT_XY);
                    crd_for_button.addView(kartinka);
                    kartinka.setImageBitmap(selectedImage);

                    String path = saveimage(kartinka, Integer.toString(shiiiit++));
                    list.addElement(path);

                    one_picture.addView(crd_for_button);
                    ImageView delete = new ImageView(getApplicationContext());
                    LinearLayout.LayoutParams delete_params = new LinearLayout.LayoutParams((int) Math.round(w_proc * 10), (int) Math.round(w_proc * 10));
                    delete_params.leftMargin = (int) Math.round(w_proc * 4);
                    delete_params.rightMargin = (int) Math.round(w_proc * 4);
                    delete_params.gravity = Gravity.CENTER_VERTICAL;
                    delete.setLayoutParams(delete_params);
                    delete.setImageResource(R.drawable.trash);
                    delete.setBackgroundColor(Color.parseColor("#36383F"));
                    one_picture.addView(delete);
                    mainlayout.addView(one_picture);
                    delete.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            mainlayout.removeView(one_picture);
                            count--;
                            File file = new File(path);
                            if (file.exists()) {
                                file.delete();
                                list.removeElement(path);
                            }
                        }
                    });
                    Button add_new = new Button(getApplicationContext());
                    LinearLayout.LayoutParams add_new_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 10));
                    add_new_params.leftMargin = (int) Math.round(w_proc * 4);
                    add_new_params.topMargin = (int) Math.round(w_proc * 4);
                    add_new_params.rightMargin = (int) Math.round(w_proc * 4);
                    add_new_params.bottomMargin = (int) Math.round(w_proc * 4);
                    add_new.setLayoutParams(add_new_params);
                    add_new.setText("Создать новость");
                    add_new.setId(228);
                    add_new.setBackgroundResource(R.drawable.btnclr);
                    mainlayout.addView(add_new);
                    add_new.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            add_new(view);
                        }
                    });
// Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri, filePathColumn, null, null, null);
// Move to first row
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    cursor.close();

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        if (mClipData.getItemCount() + count > 10) {
                            Toast.makeText(this, "Вы выбрали слишком много изображений, максимум 10", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        LinearLayout mainlayout = (LinearLayout) findViewById(R.id.mainlayout);
                        @SuppressLint("ResourceType") Button a = (Button) findViewById(228);
                        mainlayout.removeView(a);
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            count++;
                            final InputStream imageStream = getContentResolver().openInputStream(uri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            ImageView kartinka = new ImageView(getApplicationContext());
                            LinearLayout.LayoutParams for_kartinka = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            kartinka.setLayoutParams(for_kartinka);

                            LinearLayout one_picture = new LinearLayout(getApplicationContext());
                            LinearLayout.LayoutParams onr_picture_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            onr_picture_params.leftMargin = (int) Math.round(w_proc * 4);
                            onr_picture_params.topMargin = (int) Math.round(w_proc * 4);
                            one_picture.setLayoutParams(onr_picture_params);
                            one_picture.setOrientation(LinearLayout.HORIZONTAL);
                            CardView crd_for_button = new CardView(getApplicationContext());
                            LinearLayout.LayoutParams crd_for_button_params = new LinearLayout.LayoutParams((int) Math.round(w_proc * 78), (int) Math.round(w_proc * 44));
                            crd_for_button.setLayoutParams(crd_for_button_params);
                            crd_for_button.setRadius((int) Math.round(w_proc * 2));
                            crd_for_button.setContentPadding(0, 0, 0, 0);
                            crd_for_button.setCardBackgroundColor(Color.LTGRAY);
                            kartinka.setScaleType(ImageView.ScaleType.FIT_XY);
                            crd_for_button.addView(kartinka);
                            kartinka.setImageBitmap(selectedImage);

                            String path = saveimage(kartinka, Integer.toString(shiiiit++));
                            list.addElement(path);

                            one_picture.addView(crd_for_button);
                            ImageView delete = new ImageView(getApplicationContext());
                            LinearLayout.LayoutParams delete_params = new LinearLayout.LayoutParams((int) Math.round(w_proc * 10), (int) Math.round(w_proc * 10));
                            delete_params.leftMargin = (int) Math.round(w_proc * 4);
                            delete_params.rightMargin = (int) Math.round(w_proc * 4);
                            delete_params.gravity = Gravity.CENTER_VERTICAL;
                            delete.setLayoutParams(delete_params);
                            delete.setImageResource(R.drawable.trash);
                            delete.setBackgroundColor(Color.parseColor("#36383F"));
                            one_picture.addView(delete);
                            mainlayout.addView(one_picture);
                            delete.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View arg0) {
                                    mainlayout.removeView(one_picture);
                                    count--;
                                    File file = new File(path);
                                    if (file.exists()) {
                                        file.delete();
                                        list.removeElement(path);
                                    }
                                }
                            });
// Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
// Move to first row
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                        }
                        Button add_new = new Button(getApplicationContext());
                        LinearLayout.LayoutParams add_new_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) Math.round(w_proc * 10));
                        add_new_params.leftMargin = (int) Math.round(w_proc * 4);
                        add_new_params.topMargin = (int) Math.round(w_proc * 4);
                        add_new_params.rightMargin = (int) Math.round(w_proc * 4);
                        add_new_params.bottomMargin = (int) Math.round(w_proc * 4);
                        add_new.setLayoutParams(add_new_params);
                        add_new.setText("Создать новость");
                        add_new.setId(228);
                        add_new.setTextColor(Color.WHITE);
                        add_new.setBackgroundResource(R.drawable.btnclr);
                        mainlayout.addView(add_new);
                        add_new.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                add_new(view);
                            }
                        });
                    }
                }
            } else {
                Toast.makeText(this, "Вы не выбрали изображение", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Извините, что-то сломалось:" + e, Toast.LENGTH_LONG).show();
            System.out.println(e);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String saveimage(View view, String name) {
        String folderToSave = (getApplicationContext().getFileStreamPath(name).getPath()).toString();
        OutputStream fOut = null;
        Time time = new Time();
        time.setToNow();
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
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Произошла хуйня", Toast.LENGTH_SHORT).show();
            folderToSave = "error";
        }
        return folderToSave;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.down, R.anim.down1);
    }
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
}
