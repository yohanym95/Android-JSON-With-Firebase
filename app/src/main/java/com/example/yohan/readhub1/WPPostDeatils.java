package com.example.yohan.readhub1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WPPostDeatils extends AppCompatActivity {

    WebView webView;
    private Model model;
    private RequestQueue mRequestQueue;
    TextView textViewName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wppost_deatils);


        webView = findViewById(R.id.postWebView);
        //textViewName = findViewById(R.id.tv);

        Intent i = getIntent();
        String renderContent = i.getStringExtra(MainActivity.RENDER_CONTENT);


        String head = "<head><style>img{max-width:100%;width:auto;height:auto;}</style><heade>";
        String body = "<html>"+head+"<body>"+renderContent+"</body></html>";

        mRequestQueue = Volley.newRequestQueue(this);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);


       // webView.getSettings().setLoadWithOverviewMode(true);
       // webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
      //  webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.loadData(body,"text/html" ,null);
        //webView.loadUrl(MainActivity.);
      //  ParseJson();

    }



}
