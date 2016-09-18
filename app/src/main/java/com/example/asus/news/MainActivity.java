package com.example.asus.news;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.asus.news.adapter.SummaryAdapter;
import com.example.asus.news.dao.JsonDataDao;
import com.example.asus.news.entiy.Article;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AbsListView.OnScrollListener {
    ArrayList<Article> articleList;
    ListView articListView;
    SummaryAdapter summaryAdapter;
    private JsonDataDao jsonDataDao;
    private int pageCount = 1;
    private int SUCCESS_ARTICl_LOAD = 1;
    final Handler artListHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SUCCESS_ARTICl_LOAD) {
                summaryAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //以下是自己的代码
        /**
         * artListView的设置
         */
        articListView = (ListView) findViewById(R.id.summary_listView);
        jsonDataDao = new JsonDataDao();
        articleList = jsonDataDao.getNewArtList();
        summaryAdapter = new SummaryAdapter(articleList, this);
        jsonDataDao.setArtListHandler(artListHandler);
        articListView.setAdapter(summaryAdapter);
        articleList = jsonDataDao.getArticleListAsync("China");
        articListView.setOnScrollListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_china) {
            showArticle("China");
        } else if (id == R.id.nav_word) {
            showArticle("World");
        } else if (id == R.id.nav_business) {
            showArticle("Business");
        } else if (id == R.id.nav_sports) {
            showArticle("Sports");
        } else if (id == R.id.nav_lifestyle) {
            showArticle("Lifestyle");
        } else if (id == R.id.nav_opinion) {
            showArticle("Opinion");
        } else if (id == R.id.nav_tech) {
            showArticle("Tech");
        } else if (id == R.id.nav_special) {

        } else if (id == R.id.nav_photo) {

        } else if (id == R.id.nav_video) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showArticle(String navString) {

        jsonDataDao = new JsonDataDao();
        articleList = jsonDataDao.getNewArtList();
        summaryAdapter = new SummaryAdapter(articleList, this);
        jsonDataDao.setArtListHandler(artListHandler);
        articListView.setAdapter(summaryAdapter);
        articleList = jsonDataDao.getArticleListAsync(navString);
        articListView.setOnScrollListener(this);
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        if (i + i1 == i2 && jsonDataDao.isLoadPage() == false) {
            pageCount++;
            jsonDataDao.getArticleListBypageandColumnIdAsync(pageCount, articleList.get(0).getColumnId());
        }
    }
}
