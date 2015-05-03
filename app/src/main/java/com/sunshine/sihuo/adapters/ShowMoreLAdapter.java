package com.sunshine.sihuo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.sunshine.sihuo.R;
import com.sunshine.sihuo.beans.MoreBeans;

import java.util.List;

/**
 * Created by Administrator on 2015/5/3.
 */
public class ShowMoreLAdapter extends BaseAdapter {

    private Context context;
    private List<MoreBeans> list;

    private String headStr = "http://static.sihuo.in/";

    public ShowMoreLAdapter(Context context, List<MoreBeans> list) {
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

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.show_list_item, null);

            convertView.setTag(new ViewHolder(convertView));
        }

        MoreBeans moreBeans = list.get(position);
        ViewHolder holder = ((ViewHolder) convertView.getTag());

        holder.title.setText(moreBeans.getTitle());

        List<String> names = moreBeans.getNames();
        if(names != null){
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i) != null) {
                holder.names[i].setText(names.get(i));
            } else {
                holder.names[i].setText("");
                holder.names[i].setVisibility(View.GONE);
            }
        }}

        BitmapUtils utils = new BitmapUtils(context);
        String image = moreBeans.getImage();

        if(image!=null){
            String imageUrl = getImageUrl(image);
            if(imageUrl.length()>headStr.length())
            utils.display(holder.imageView,imageUrl);
        }

        return convertView;
    }

    public class ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView name01, name02, name03, name04, name05, name06;

        private TextView[] names = new TextView[6];

        public ViewHolder(View view) {
            imageView = ((ImageView) view.findViewById(R.id.show_list_item_image));

            title = ((TextView) view.findViewById(R.id.show_list_item_title));

            name01 = ((TextView) view.findViewById(R.id.show_list_item_name01));
            name02 = ((TextView) view.findViewById(R.id.show_list_item_name02));
            name03 = ((TextView) view.findViewById(R.id.show_list_item_name03));
            name04 = ((TextView) view.findViewById(R.id.show_list_item_name04));
            name05 = ((TextView) view.findViewById(R.id.show_list_item_name05));
            name06 = ((TextView) view.findViewById(R.id.show_list_item_name06));

            names[0] = name01;
            names[1] = name02;
            names[2] = name03;
            names[3] = name04;
            names[4] = name05;
            names[5] = name06;
        }
    }

    public String getImageUrl(String icon) {

        StringBuffer buffer = new StringBuffer();

        buffer.append(headStr);
        buffer.append(icon);

        String string = buffer.toString();
        return string;
    }
}
