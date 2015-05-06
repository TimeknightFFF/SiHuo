package com.sunshine.sihuo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Contacts;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.sunshine.sihuo.chatpackage.ChatActivity;
import com.sunshine.sihuo.chatpackage.ChatService;

import org.jivesoftware.smack.RosterEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyFriendActivity extends FragmentActivity implements View.OnClickListener, AdapterView.OnItemClickListener, ServiceConnection {
    private ListView lv;

    private String[] columns = { // 查询Content Provider时希望返回的列
            Contacts.People._ID, Contacts.People.NAME,};
    private Uri contactUri = Contacts.People.CONTENT_URI; // 访问Content Provider需要的Uri
    private ImageView requestfriend_iv;
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

    // 添加新友的点击事件和 展示已有朋友的listView
    private RelativeLayout layout;
    private ListView listOfFriend;

    // 绑定服务器，加载联系人数据
    private ChatService.ChatController controller;
    // 放置联系人的集合
    private List<RosterEntry> entries;
    // 名字
    private List<String> data;
    private ArrayAdapter<String> nameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_friend);
        lv = (ListView) findViewById(R.id.mine_friend_info_lv);
        requestfriend_iv = (ImageView) findViewById(R.id.mine_friend_info_image);
        requestfriend_iv.setOnClickListener(this);

        getContact();
        SimpleAdapter adapter = new SimpleAdapter(MyFriendActivity.this, list, R.layout.requestfriend_lv_item,
                new String[]{"name", "request"}, new int[]{R.id.requestfriend_tv1,
                R.id.requestfriend_tv2});
        lv.setAdapter(adapter);


        // 初始化两个控件
        layout = ((RelativeLayout) findViewById(R.id.mine_friend_info_add));
        listOfFriend = (ListView) findViewById(R.id.mine_friend_info);

        // 获取已有的朋友列表
        data = new ArrayList<>();

        nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listOfFriend.setAdapter(nameAdapter);
        // listOfFriend的点击事件
        listOfFriend.setOnItemClickListener(this);

        Intent intent = new Intent(this, ChatService.class);
        bindService(intent,this,BIND_AUTO_CREATE);
    }

    private void getContact() {
        ContentResolver resolver = getContentResolver(); // 获取ContentResolver对象
        Cursor cursor = resolver.query(contactUri, columns, null, null, null); // 调用方法查询Content
        // Provider
        int nameIndex = cursor.getColumnIndex(Contacts.People.NAME); // 获得NAME字段的列索引
        String datas[] = new String[]{"name", "request"};

        HashMap map;
        for (cursor.moveToFirst(); (!cursor.isAfterLast()); cursor.moveToNext()) { // 遍历Cursor，提取数据
            map = new HashMap<String, Object>();
            map.put("name", cursor.getString(nameIndex));
            map.put("request", "邀请");
            // tv2.setText(cursor.getString(nameIndex));
            list.add(map);
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        upDateRoster();
    }

    private void upDateRoster() {
        if (controller != null) {
            //每次显示的时候，及时获取联系人列表，进行刷新操作
            //获取联系人信息
            entries = controller.getRosterEntris();

            data.clear();
            for (RosterEntry roster : entries) {
                String user = roster.getUser();
                data.add(user);
            }

            nameAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        // 接触绑定
        unbindService(this);

        super.onDestroy();
    }

    /**
     * 点击联系人启动会话
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO 获取联系人的JID

        RosterEntry entry = entries.get(position);

        //获取联系人帐号
        String userJID = entry.getUser();

        // 开启聊天会话界面
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("userJID",userJID);
        startActivity(intent);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        controller = (ChatService.ChatController) service;
        upDateRoster();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        controller = null;
    }
}
