package com.sunshine.sihuo.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.sunshine.sihuo.R;
import com.sunshine.sihuo.beans.Index_Info;

import java.util.List;

/**
 * Created by Administrator on 2015/5/1.
 */
public class Find_List_Adapter extends BaseAdapter {

    private List<Index_Info> list;
    private Context context;

    private String headStr = "http://static.sihuo.in/";

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

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.find_item_list, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = ((ViewHolder) convertView.getTag());
        final Index_Info info = list.get(position);

        holder.userName.setText(info.getuName());
        holder.commonNum.setText(info.getcNum() + "");
        holder.loveNum.setText(info.getpNum() + "");
        holder.desc.setText(info.getDesc());
        holder.pre.setText(info.getOri_price());
        holder.price.setText(info.getPrice());

        BitmapUtils utils = new BitmapUtils(context);
        String avatar = info.getAvatar();
        if (avatar != null) {
            String avatarUrl = getImageUrl(avatar);
            if(avatarUrl.length()>headStr.length())
            utils.display(holder.image01, avatarUrl, new BitmapLoadCallBack<ImageView>() {
                @Override
                public void onLoadCompleted(ImageView imageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                    Bitmap image = getRoundImage(bitmap);
                    imageView.setImageBitmap(image);
                }

                @Override
                public void onLoadFailed(ImageView imageView, String s, Drawable drawable) {

                }
            });
        } else {
            holder.image01.setImageResource(R.drawable.icon_default_avatr100);
        }
        String infoPhoto = info.getPhoto();
        if (infoPhoto != null) {
            String photoUrl = getImageUrl(infoPhoto);
            if (photoUrl.length() > headStr.length()) {
                utils.display(holder.image02, photoUrl);
            }
        }

        /**
         * 设置按钮的点击事件
         */
        convertView.findViewById(R.id.item_list_btn_love).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "ListView中条目自控件的点击事件", Toast.LENGTH_SHORT).show();
            }
        });

        convertView.findViewById(R.id.lv_item_list_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, "http://www.sihuo.com/?c=html5&a=goods&v=1.3.4&os=android&goods_id=" + info.getGoods_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(intent, "分享"));

//                context
//                Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();

            }
        });

        return convertView;
    }

    public class ViewHolder {

        private ImageView image01, image02;
        private TextView userName, price, pre, loveNum, commonNum, desc;

        public ViewHolder(View itemView) {
            image01 = ((ImageView) itemView.findViewById(R.id.item_list_avatar));
            image02 = ((ImageView) itemView.findViewById(R.id.item_list_image));

            userName = ((TextView) itemView.findViewById(R.id.item_list_name));
            price = ((TextView) itemView.findViewById(R.id.item_list_price));
            pre = ((TextView) itemView.findViewById(R.id.item_list_pre));
            loveNum = ((TextView) itemView.findViewById(R.id.item_list_love_num));
            commonNum = ((TextView) itemView.findViewById(R.id.item_list_common_num));
            desc = ((TextView) itemView.findViewById(R.id.item_list_content));
        }
    }

    /**
     * 获取圆形图片
     */
    public static Bitmap getRoundImage(Bitmap srcBitmap) {

        if (null == srcBitmap) {
            Log.e("DEBUG", "the srcBitmap is null");
            return null;
        }

        int bitWidth = srcBitmap.getWidth();
        int bitHight = srcBitmap.getHeight();

        BitmapShader bitmapShader = new BitmapShader(srcBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        RectF rectf = new RectF(0, 0, bitWidth, bitHight);

        Bitmap outBitmap = Bitmap.createBitmap(bitWidth, bitHight,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        canvas.drawRoundRect(rectf, 260, 260, paint);
        canvas.save();
        canvas.restore();

        return outBitmap;
    }

    public String getImageUrl(String icon) {

        StringBuffer buffer = new StringBuffer();

        buffer.append(headStr);
        buffer.append(icon);

        String string = buffer.toString();
        return string;
    }
}
