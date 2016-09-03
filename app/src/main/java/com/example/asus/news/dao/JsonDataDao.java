package com.example.asus.news.dao;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.asus.news.entiy.Article;
import com.example.asus.news.entiy.Medium;
import com.example.asus.news.entiy.Picture;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ASUS on 2016/9/1.
 */
public class JsonDataDao {
    private ArrayList<Article> artList = null;
    private URL url;
    /**
     * 所有获得Json文件的接口
     */

    private final String China
            = "http://iosnews.chinadaily.com.cn/newsdata/news/columns/32_column_v4.json?time=";
    private final String World
            = "http://iosnews.chinadaily.com.cn/newsdata/news/columns/42_column_v4.json?time=";
    private final String Business
            = " http://iosnews.chinadaily.com.cn/newsdata/news/columns/40_column_v4.json?time=";
    private final String Sports
            = "http://iosnews.chinadaily.com.cn/newsdata/news/columns/47_column_v4.json?time=";
    private final String Lifestyle
            = " http://iosnews.chinadaily.com.cn/newsdata/news/columns/34_column_v4.json?time=";
    private final String Opinion
            = "http://iosnews.chinadaily.com.cn/newsdata/news/columns/33_column_v4.json?time=";
    private final String Tech
            = "http://iosnews.chinadaily.com.cn/newsdata/news/columns/360_column_v4.json?time=";
    private final String Photo
            = "http://iosnews.chinadaily.com.cn/newsdata/news/columns/38_column_v4.json?time=";
    private final String Video
            = "http://iosnews.chinadaily.com.cn/newsdata/news/columns/39_column_v4.json?time=";

    /**
     * 根据传入的字符，异步返回Json数据
     * 在这个接口中的代码是在另一个线程中运行的，
     * 需要注意的是这个返回的结果的运行跟UI线程是不同步的
     *
     * @param data
     * @return
     */
    public ArrayList<Article> getArticleListAsync(String data) {
        switch (data) {
            case "China":
                data = China;
                break;
            case "World":
                data = World;
                break;
            case "Business":
                data = Business;
                break;
            case "Sports":
                data = Sports;
                break;
            case "Lifestyle":
                data = Lifestyle;
                break;
            case "Opinion":
                data = Opinion;
                break;
            case "Tech":
                data = Tech;
                break;
            case "Photo":
                data = Photo;
                break;
            case "Video":
                data = Video;
                break;
            default:
                break;
        }
        try {
            url = new URL(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                getArtList();
            }
        };
        thread.start();
        return artList;
    }
    /**
     *利用网络获取Json文件
     */
    public ArrayList<Article> getArtList() {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Charset", "utf-8");
            urlConnection.connect();
            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return artList;
            }

            StringBuffer jsonStringBuffer = new StringBuffer();
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                jsonStringBuffer.append(line);
            }
            /*
            * 将JSon数据对应到实体
            * */
            String jsonStr = jsonStringBuffer.toString();
            Log.v("jsonStr", jsonStr);
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject arts = jsonObject.getJSONObject("articles");
            Iterator<String> it = arts.keys();
            while (it.hasNext()) {
                String key = it.next();
                JSONObject artJson = arts.getJSONObject(key);
                Log.v("jsonIterator", key);
                Article article = new Article();
                article.setAuthor(artJson.getString("author"));
                article.setContent(artJson.getString("content"));
                article.setDescription(artJson.getString("description"));
                article.setPath(artJson.getString("path"));
                article.setSource(artJson.getString("source"));
                article.setTitle(artJson.getString("title"));
                article.setColumnId(artJson.getInt("columnId"));

                JSONObject pictures = null;
                if (!artJson.isNull("pictures")) {
                    pictures = artJson.getJSONObject("pictures");
                    Iterator itPic = pictures.keys();
                    while (itPic.hasNext()) {
                        String keyPic = (String) itPic.next();
                        JSONObject pictureJSONObject = pictures.getJSONObject(keyPic);
                        Picture pic = new Picture();
                        pic.setDescription(pictureJSONObject.getString("description"));
                        pic.setFile(pictureJSONObject.getString("file"));
                        pic.setFileHD(pictureJSONObject.getString("fileHD"));
                        pic.setHeight(pictureJSONObject.getInt("height"));
                        pic.setWidth(pictureJSONObject.getInt("width"));
                        article.setPictures(pic);
                    }
                }

                if (!artJson.isNull("medias")) {
                    JSONObject media = artJson.getJSONObject("medias");
                    Iterator itMed = media.keys();
                    while (itMed.hasNext()) {
                        String keyMed = (String) itMed.next();
                        JSONObject mediumJSONObject = pictures.getJSONObject(keyMed);
                        Medium medium = new Medium();
                        medium.setDescription(mediumJSONObject.getString("description"));
                        medium.setFile(mediumJSONObject.getString("file"));
                        medium.setFileHD(mediumJSONObject.getString("fileHD"));
                        article.setMedia(medium);
                    }
                }
                artList.add(article);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return artList;
    }


    public JsonDataDao() {
        artList = new ArrayList<Article>();
    }
    /**
     * 异步获取下一页数据，数据存入了JsonData本身中的List中，即这个接口和getArticleListAsync
     * 接口返回的数据是同一个List；
     */

    public ArrayList<Article> getArticleListBypageandColumnIdAsync(int page, int columnId) {

        String data = "http://iosnews.chinadaily.com.cn/newsdata/news/columns.shtml?page=" +
                page + "&columnId=" + columnId;
        try {
            url = new URL(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                getArtList();
            }
        };
        thread.start();
        return artList;
    }

}
