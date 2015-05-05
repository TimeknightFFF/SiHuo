package com.sunshine.sihuo.chatpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunshine.sihuo.R;

import java.util.List;

/**
 * Created by Administrator on 2015/5/5.
 */
public class ChatMessageAdapter extends BaseAdapter {

    private Context context;
    private List<ChatMessage> list;

    // 加载布局
    private LayoutInflater inflater;

    public ChatMessageAdapter(Context context, List<ChatMessage> list) {
        this.context = context;
        this.list = list;

        inflater = LayoutInflater.from(context);
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

    /**
     * 告诉ListView内部的布局一共有几种
     * @return
     */
    @Override
    public int getViewTypeCount() {
        // 对与2 主要是聊天要有发送和接收两种布局方式
        return 2;
    }

    /**
     * 每次listView显示item消息的时候都会问一下adapter指定位置的item的显示类型
     * @param position  根据位置获取数据类型
     * @return  int 注意：返回的数值必须是从0到   getViewTypeCount()-1
     */
    @Override
    public int getItemViewType(int position) {

        ChatMessage chatMessage = list.get(position);

        int ret=0;
        int sourceType = chatMessage.getSourceType();

        // 对于发M送出去的消息，显示在右侧，指定返回的消息类型为1
        if(sourceType == ChatMessage.SOURCE_TYPE_SEND){
            ret = 1;
        }else if(sourceType == ChatMessage.SOURCE_TYPE_RECEIVED){
            // 对于收到的消息，显示在左侧，
            ret = 0;
        }

        return ret;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ret = null;

        // TODO 1.获取消息，判断消息的类型，根据消息类型进行内容设置
        ChatMessage message = list.get(position);
        // 获取来源类型，根据来源类型进行不同布局的加载与显示
        int sourceType = message.getSourceType();

        if(sourceType == ChatMessage.SOURCE_TYPE_SEND){
            // 收到的，就显示在左侧
            if(convertView != null){
                ret = convertView;
            }else {
                ret=inflater.inflate(R.layout.chat_send_item,null);
            }

            // 显示的内容， 左侧的TextView
            TextView txtMessage = (TextView) ret.findViewById(R.id.chat_message_right);
            txtMessage.setText(message.getBody());
        }else if(sourceType==ChatMessage.SOURCE_TYPE_RECEIVED){
            //TODO 发送的 显示在右侧
            if(convertView!=null){
                ret=convertView;
            }else {
                //LayoutInflater
                ret = inflater.inflate(R.layout.chat_receive_item,null);
            }

            // TODO 显示右侧 的消息
            TextView txtMessage= ((TextView) ret.findViewById(R.id.chat_message));

            txtMessage.setText(message.getBody());
        }


        return ret;
    }
}
