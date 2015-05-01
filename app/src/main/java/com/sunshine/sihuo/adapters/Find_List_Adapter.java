package com.sunshine.sihuo.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sunshine.sihuo.beans.Index_Info;

import java.util.List;

/**
 * Created by Administrator on 2015/5/1.
 */
public class Find_List_Adapter extends BaseAdapter {

    private List<Index_Info> list;
    private Context context;

    public Find_List_Adapter(Context context, List<Index_Info> list) {
        this.context = context;
        this.list = list;
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
}
