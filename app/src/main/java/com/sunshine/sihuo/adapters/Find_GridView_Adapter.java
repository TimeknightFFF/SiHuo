package com.sunshine.sihuo.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sunshine.sihuo.beans.Hot_category;

import java.util.List;

/**
 * Created by Administrator on 2015/5/1.
 */
public class Find_GridView_Adapter extends BaseAdapter{

    private List<Hot_category> list;
    private Context context;

    public Find_GridView_Adapter(List<Hot_category> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public class ViewHolder{

    }
}
