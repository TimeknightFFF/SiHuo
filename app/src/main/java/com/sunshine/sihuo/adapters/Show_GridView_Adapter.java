package com.sunshine.sihuo.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.sunshine.sihuo.R;
import com.sunshine.sihuo.beans.Index_Info;

import java.util.List;

/**
 * Created by Administrator on 2015/5/2.
 */
public class Show_GridView_Adapter extends BaseAdapter {

    private List<Index_Info> list;
    private Context context;

    private String headStr = "http://static.sihuo.in/";

    public Show_GridView_Adapter(List<Index_Info> list, Context context) {
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

        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.kind_item_grid,null);
            convertView.setTag(new ViewHolder(convertView));
        }

        ViewHolder holder = ((ViewHolder) convertView.getTag());

        Index_Info info = list.get(position);

        Log.v("Adapter",info.toString());
        holder.desc.setText(info.getDesc());
        holder.price.setText(info.getPrice());

        String infoPhoto = info.getPhoto();
        if(infoPhoto!=null) {
            String photoUrl=getImageUrl(infoPhoto);
            if(photoUrl.length()>headStr.length())
            new BitmapUtils(context).display(holder.image,photoUrl);
        }

        return convertView;
    }

    public class ViewHolder{
        private TextView desc,price;
        private ImageView image;

        public ViewHolder(View itemView) {
            desc= ((TextView) itemView.findViewById(R.id.kind_item_desc));
            price= ((TextView) itemView.findViewById(R.id.kind_item_price));

            image= ((ImageView) itemView.findViewById(R.id.kind_item_image));
        }
    }

    public String getImageUrl(String icon) {

        StringBuffer buffer = new StringBuffer();

        buffer.append(headStr);
        buffer.append(icon);

        String string = buffer.toString();
        return string;
    }

    /**
     * 刷新数据
     */
    public void clear(){

        list.clear();

        notifyDataSetChanged();
    }

}
