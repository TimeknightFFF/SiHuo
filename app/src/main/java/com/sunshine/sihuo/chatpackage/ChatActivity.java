package com.sunshine.sihuo.chatpackage;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.sunshine.sihuo.R;
import com.sunshine.sihuo.utils.SysApplication;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import java.util.LinkedList;

public class ChatActivity extends ActionBarActivity implements ServiceConnection, MessageListener, PacketListener {

    private ListView listView;
    private String userJID;
    private Intent service;
    private Chat chat;

    /**
     * 从服务获取的Binder，用于进行消息的发送
     */
    private ChatService.ChatController controller;

    private EditText txtContent;
    private String thread;
    private String body;
    private LinkedList<ChatMessage> chatMessages;
    private ChatMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        SysApplication.getInstance().addActivity(this);
        txtContent = ((EditText) findViewById(R.id.chat_message_content));
        // 接收目标联系人
        Intent intent = getIntent();

        userJID = intent.getStringExtra("userJID");
        // 在Activity上面显示标题
        setTitle(userJID);

        // 获取Chat主题，可能为空， 因为自己点击进入ChatActivity时，是没有的
        thread = intent.getStringExtra("thread");

        body = intent.getStringExtra("body");

        // 绑定服务，用于发送消息
        service = new Intent(this,ChatService.class);

        bindService(service,this,BIND_AUTO_CREATE);

        // listView 显示，实现，聊天的样式。左侧是收到的消息，右侧是发送的消息
        listView = ((ListView) findViewById(R.id.chat_message_list));

        chatMessages = new LinkedList<>();

        adapter = new ChatMessageAdapter(this,chatMessages);

        listView.setAdapter(adapter);

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        controller = ((ChatService.ChatController) service);

        // 绑定成功之后，进行聊天会话的创建 chat对象
        chat = controller.openChat(userJID,null,this);

        // controller 向内部的XMPPTCPConnection添加一个PacketListener进行消息监听
        controller.addPacketListener(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        // 删除 停止 监听数据包的内容
        controller.removePacketListener(this);

        if (chat != null) {
            chat.close();
        }

        controller = null;
    }

    /**
     * 消息接收
     * @param chat
     * @param message
     */
    @Override
    public void processMessage(Chat chat, Message message) {
        // 处理消息的接收和发送

        String from = message.getFrom();
        String to = message.getTo();
        String body1 = message.getBody();

        // 显示消息
        Log.v("ChatActivity", "Message from: " + from + " , to: " + to + " content: " + body1);
    }

    /**
     * 发送按钮点击事件
     *
     * @param v
     */
    public void btnSendOnClick(View v) {
        String trim = txtContent.getText().toString();

        if (chat != null) {
            try {
                chat.sendMessage(trim);
                txtContent.setText("");

                // TODO 创建消息实体，显示在ListView 上面

                ChatMessage msg = new ChatMessage();
                //设置显示的文本
                msg.setBody(trim);
                //类型是发送类型
                msg.setSourceType(ChatMessage.SOURCE_TYPE_SEND);

                chatMessages.add(msg);
                adapter.notifyDataSetChanged();

            } catch (XMPPException e) {
                e.printStackTrace();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void processPacket(Packet packet) throws SmackException.NotConnectedException {

        if(packet instanceof Message){
            Message msg = (Message) packet;

            // 1. 检查消息的来源是否是当前的会话的人
            String from = msg.getFrom();
            /**
             * 因为PacketListener 会接收所有的消息 对会话界面而言 就需要检查消息的来源
             * 是否是当前聊天的人发送的
             */
            if(from.startsWith(userJID)){

                // 显示 ListView消息
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setSourceType(ChatMessage.SOURCE_TYPE_RECEIVED);

                chatMessage.setBody(msg.getBody());

                chatMessage.setFrom(from);
                chatMessage.setTo(msg.getTo());

                chatMessage.setTime(System.currentTimeMillis());

                // 添加消息， 更新ListView
                chatMessages.add(chatMessage);

                // 因为processPacket执行在子线程中，listView更新在主线程中
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(this);
        super.onDestroy();
    }
}
