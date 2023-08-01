package com.example.fyp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.webkit.WebView;

public class WeeklySalesReportActivity extends AppCompatActivity {

    WebView webView;
    public String url ="http://"+ GetIP.getInstance().getIp() +"/fyp/weeklySalesReport.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_sales_report);

        // Set Title Text and Bar Colour
        ActionBar aBar;
        aBar = getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#000080"));
        aBar.setBackgroundDrawable(cd);
        int white = Color.parseColor("white");
        Spannable spannable = new SpannableString("Weekly Sales Report");
        spannable.setSpan(new ForegroundColorSpan(white), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        aBar.setTitle(spannable);

        webView = (WebView) findViewById(R.id.weeklySalesReportWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);


    }
}