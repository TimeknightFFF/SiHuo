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

        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.find_item_gridview,null);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder= ((ViewHolder) convertView.getTag());

        Hot_category category = list.get(position);
        holder.textView.setText(category.getName());

        // 图片网址不完整，需要添加头部
        String url="http://static.sihuo.in/"+category.getIcon();

        BitmapUtils utils=new BitmapUtils(context);
        utils.display(holder.imageView,url);

        return convertView;
    }

    public class ViewHolder{

        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View itemView) {
            imageView= ((ImageView) itemView.findViewById(R.id.item_gridview_image));
            textView= ((TextView) itemView.findViewById(R.id.item_gridview_title));
        }
    }
}
