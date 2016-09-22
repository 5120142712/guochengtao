package com.example.asus.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import java.util.ArrayList;

public class ShowArticle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //自己代码
        WebView articleWebView = (WebView) findViewById(R.id.article_webView);
        Intent intent = this.getIntent();
        StringBuffer sb = new StringBuffer();
        articleWebView.setBackgroundColor(0);
        //title  author author
        if(!intent.getStringExtra("title").matches("null")) {
            sb.append("<h2>" + intent.getStringExtra("title") + "</h2>");
        }
        if(!intent.getStringExtra("author").matches("null")) {
            sb.append("<h4>Author:" + intent.getStringExtra("author") + "</h4>");
        }
        if(!intent.getStringExtra("source").matches("null")) {
            sb.append("<h4>Source:" + intent.getStringExtra("source") + "</h4>");
        }
        sb.append("\n<hr/>\n");
        if(intent.getStringArrayListExtra("mediumPaths").isEmpty()) {
            for (String picPath : intent.getStringArrayListExtra("picPaths")) {
                sb.append("<img src =\"" + picPath + "\"  width=\"100%\"/><p>\n\n</p>");
            }
        }
        // controls="controls"
        else {
            for (String mediumPath : intent.getStringArrayListExtra("mediumPaths")) {
                ArrayList<String> picPaths = intent.getStringArrayListExtra("picPaths");
                String picPath = picPaths.get(0);
                sb.append("<video src=\"" + mediumPath + "\" poster=\"" + picPath +
                        "\" autoplay=\"autoplay\" controls=\"controls\" width=\"100%\" >" +
                        "</video><p>\n\n</p>");
            }
        }
        if(!intent.getStringExtra("content").matches("null")) {
            sb.append(intent.getStringExtra("content"));
        }
        articleWebView.loadData(sb.toString(), "text/html", "UTF-8");

    }

    @Override
    protected void onPause() {
        WebView articleWebView = (WebView) findViewById(R.id.article_webView);
        articleWebView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        WebView articleWebView = (WebView) findViewById(R.id.article_webView);
        articleWebView.onPause();
        super.onDestroy();
    }
}
