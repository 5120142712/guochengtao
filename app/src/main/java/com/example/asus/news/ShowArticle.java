package com.example.asus.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

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
        for (String picPath : intent.getStringArrayListExtra("picPaths")) {
            sb.append("<img src ='" + picPath + "'  width=\"100%\" height=\150px\"/><p>\n" +
                    "\n" +
                    "</p>");
        }
        // controls="controls"
        for (String mediumPath : intent.getStringArrayListExtra("mediumPaths")) {
            sb.append("<video src=\"" + mediumPath + "\" preload='preload'  width=\"100%\" height=150px\" >" +
                    "</video><p>\r\n\n</p>");
        }
        sb.append(intent.getStringExtra("content"));
        articleWebView.loadData(sb.toString(), "text/html", "UTF-8");

    }

}
