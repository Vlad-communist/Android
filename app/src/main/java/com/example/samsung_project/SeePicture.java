package com.example.samsung_project;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Objects;

public class SeePicture extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seepicture);
        Objects.requireNonNull(getSupportActionBar()).hide();
        WebView webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setSupportZoom(true);
        webview.setInitialScale(100);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.setPadding(0, 0, 0, 0);
        webview.setScrollbarFadingEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.getSettings().setAllowFileAccess(true);
        String folderToOpen = (getApplicationContext().getFileStreamPath("push.jpg").getPath()).toString();
        System.out.println(folderToOpen);
        webview.loadUrl(folderToOpen);
    }
}
