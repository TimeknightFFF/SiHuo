package com.sunshine.sihuo.chatpackage;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ChatService extends Service {

    private XMPPTCPConnection conn;

    public class ChatController extends Binder {

        /**
         * 停止监听器，不再接收消息
         */
        public void removePacketListener(PacketListener listener) {

            if (listener != null) {

                if (conn != null) {
                    conn.removePacketListener(listener);
                }
            }
        }

        /**
         * 添加监听器接口，用于外部界面要接收消息的时候的测试
         */
        public void addPacketListener(PacketListener listener) {

            if (listener != null) {
                if (conn != null) {
                    // 参数1  收到消息的监听器  参数2 ：消息的类型
                    conn.addPacketListener(listener, new MessageTypeFilter(Message.Type.chat));
                }
            }
        }

        /**
         * 用于开启聊天会话
         *
         * @param target   需要和谁聊天
         * @param thread
         * @param listener 监听消息
         * @return Chat对象  通过chat直接发送消息
         */
        public Chat openChat(String target, String thread, MessageListener listener) {
            Chat chat = null;

            if (target != null) {
                if (conn != null) {

                    // 判断当前是否已经登录
                    if (conn.isAuthenticated()) {
                        ChatManager chatManager = ChatManager.getInstanceFor(conn);

                        // 创建聊天会话
                        chat = chatManager.createChat(target, thread, listener);
                    }
                }
            }

            return chat;
        }

        /**
         * 给外部的Activity提供直接调用登录的功能
         *
         * @param userName
         * @param password
         * @return
         */
        public String login(String userName, String password) {

            String ret = null;

            if (conn != null) {
                if (!conn.isAuthenticated()) {
                    try {
                        if(!conn.isConnected()){
                            conn.connect();
                        }
                        conn.login(userName, password);

                        ret = conn.getUser();
                    } catch (XMPPException e) {
                        e.printStackTrace();
                    } catch (SmackException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return ret;
        }

        /**
         * 获取当前登录账户中的联系人的信息
         */
        public List<RosterEntry> getRosterEntris() {
            List<RosterEntry> ret = null;

            if (conn != null) {
                if (conn.isAuthenticated()) {

                    Roster roster = conn.getRoster();

                    if (roster != null) {
                        // 获取联系人列表
                        Collection<RosterEntry> entries = roster.getEntries();

                        ret = new LinkedList<>();
                        ret.addAll(entries);
                    }
                }
            }

            return ret;
        }

        public boolean register(String userName, String password) {

            boolean flag = false;
            if (userName != null && password != null) {

                if (conn != null) {

                    if (!conn.isAuthenticated()) {
                        try {

                            AccountManager manager = AccountManager.getInstance(conn);
                            Log.d("register","  user : "+userName+"   pass: "+password);
                            manager.createAccount(userName, password);
                            flag = true;
                        } catch (SmackException e) {
                           e.printStackTrace();
                            flag = false;
                        } catch (XMPPException e) {
                            e.printStackTrace();
                            flag = false;
                        }
                    }
                }
            }
            return flag;
        }
    }

    public ChatService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new ChatController();
    }

    private ChatThread chatThread;

    /**
     * 在OnCreate()方法里面初始化XMPPTCPConnection 对象
     */
    @Override
    public void onCreate() {
        super.onCreate();

        if (conn != null) {
            try {
                conn.disconnect();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
            conn = null;
        }
//        ConnectionConfiguration configuration = new ConnectionConfiguration("10.0.154.155");
//        configuration.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
//        SASLAuthentication.supportSASLMechanism("DIGEST-MD5");
        conn = new XMPPTCPConnection("10.0.154.155");

    }

    /**
     * 启动服务
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (chatThread == null) {
            chatThread = new ChatThread();
            chatThread.start();
        }
        // 启动线程
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (chatThread != null) {
            chatThread.stopThread();
            chatThread = null;
        }
        super.onDestroy();
    }

    class ChatThread extends Thread {

        // 标志线程
        private boolean running;

        public void stopThread() {
            running = false;
        }

        @Override
        public void run() {

            running = true;

            try {
                conn.connect();

                // TODO 接收消息
                /**
                 * 向连接中，添加数据包的监听器，当服务器给客户端转发消息时。XMPPTCPConnection会
                 * 自动调用PacketListener的回调。
                 */

                PacketListener packetListener = new PacketListener() {
                    @Override
                    public void processPacket(Packet packet) throws SmackException.NotConnectedException {
                        // TODO 处理消息类型数据包
                        // 因为Message类型继承了Packet ，所以检查是否是Message

                        if (packet instanceof Message) {

                            Message msg = ((Message) packet);

                            String body = msg.getBody();
                            String subject = msg.getSubject();
                            String from = msg.getFrom();
                            String to = msg.getTo();
                            // 聊天会话的主题，通过这个主题就可以确定发送者创建的Chat对象
                            // 这个thread类似于对讲机之间的关系
                            String thread = msg.getThread();

                            Log.d("ChatThread", "Packet from : " + from + " to: " + to + "content: " + body);
                        }
                    }
                };

                // 在开始会话之前，进行PacketListener设置
                // 参数1 ： 数据包监听器 用于处理数据
                // 参数2：  监听器要监听那些类型的数据
                conn.addPacketListener(packetListener, new MessageTypeFilter(Message.Type.chat));

                while (running) {
                    Thread.sleep(300);
                }

            } catch (SmackException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XMPPException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
