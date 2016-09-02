package com.example.asus.news.JsonDataDaoTest;

import android.os.SystemClock;
import android.util.Log;

import com.example.asus.news.dao.JsonDataDao;

import junit.framework.TestCase;

/**
 * Created by ASUS on 2016/9/2.
 */
public class artListTest extends TestCase{
    public void testArtList(){
        String data="http://iosnews.chinadaily.com.cn/newsdata/news/columns/32_column_v4.json?time=";
        JsonDataDao dao=new JsonDataDao();
        Log.i("json",dao.getArticleListAsync(data).toString());
    }
}
