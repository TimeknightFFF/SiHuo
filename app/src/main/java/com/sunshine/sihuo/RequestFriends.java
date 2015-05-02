package com.sunshine.sihuo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestFriends extends ActionBarActivity {

    private String[] columns = { // 查询Content Provider时希望返回的列
            Contacts.People._ID, Contacts.People.NAME,};
    private Uri contactUri = Contacts.People.CONTENT_URI; // 访问Content Provider需要的Uri
    private ListView lv;
    private ImageView requestfriend_iv;
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requestfriend);

        lv = (ListView) findViewById(R.id.requestfriend_lv);
        requestfriend_iv= (ImageView) findViewById(R.id.requestfriend_iv);
        requestfriend_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getContact();
//        Toast.makeText(RequestFriends.this,list.toString(),Toast.LENGTH_LONG).show();
        SimpleAdapter adapter = new SimpleAdapter(RequestFriends.this, list, R.layout.requestfriend_lv_item,
                new String[]{"name", "request"}, new int[]{R.id.requestfriend_tv1,
                R.id.requestfriend_tv2});
        lv.setAdapter(adapter);

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
}
