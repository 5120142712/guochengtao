package com.example.asus.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.news.R;
import com.example.asus.news.entiy.Article;

import java.util.ArrayList;

/**
 * Created by JiangAo on 2016/8/17.
 */
public class SummaryAdapter extends BaseAdapter{
    private ArrayList<Article> dataList=null;
    private Context context=null;

    public SummaryAdapter(ArrayList<Article> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder holder =null;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_summary,viewGroup,false);
            holder=new ViewHolder();
            //通过ID将样式中的控件绑定到ViewHolder
            holder.titleTextView=(TextView) view.findViewById(R.id.title_textView);
            holder.dateTextView=(TextView) view.findViewById(R.id.date_textView);
            view.setTag(holder);
        }
        else{
            holder =(ViewHolder) view.getTag();
        }
        //在ViewHolder中的控件设置内容
        holder.titleTextView.setText(dataList.get(i).getTitle());
        holder.dateTextView.setText(dataList.get(i).getPath());
        return view;
    }

    static class  ViewHolder{
        TextView titleTextView;
        TextView dateTextView;
    }
}

