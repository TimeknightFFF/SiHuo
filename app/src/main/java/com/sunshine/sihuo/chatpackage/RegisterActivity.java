package com.sunshine.sihuo.chatpackage;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sunshine.sihuo.MainActivity;
import com.sunshine.sihuo.R;
import com.sunshine.sihuo.utils.SysApplication;


public class RegisterActivity extends FragmentActivity implements View.OnClickListener, ServiceConnection {

    private EditText userName, password, password02;
    private Button btn_register, btn_back;

    private String passWord;
    private String passWord02;

    private ChatService.ChatController controller;
    private String user;

    private int index = 5;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                index--;
                if (index >= 0) {
                    Toast.makeText(RegisterActivity.this, "" + index, Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessageDelayed(0, 1000);
                }else {
                    Toast.makeText(RegisterActivity.this, "Welcome to the siHuo world", Toast.LENGTH_SHORT).show();
                    turnActivity();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        SysApplication.getInstance().addActivity(this);
        Intent intent = new Intent(this, ChatService.class);
        startService(intent);

        // 绑定服务
        bindService(intent, this, BIND_AUTO_CREATE);
        // 初始化控件
        init();

//        if(!TextUtils.isEmpty(userName.getText())&&!TextUtils.isEmpty(password.getText())){
        btn_register.setOnClickListener(this);
        btn_back.setOnClickListener(this);
//        }

    }

    private void checkText(String text) {
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        userName = ((EditText) findViewById(R.id.register_edit_name));
        password = ((EditText) findViewById(R.id.register_edit_pwd));
        password02 = ((EditText) findViewById(R.id.register_edit_pwd2));

        btn_register = ((Button) findViewById(R.id.register_btn_register));
        btn_back = ((Button) findViewById(R.id.register_btn_back));
        btn_back.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.register_btn_register:

                // 获取控件里面的内容 , 且不能为空
                user = userName.getText().toString();

                passWord = password.getText().toString();
                passWord02 = password02.getText().toString();

                if (passWord.equals(passWord02)) {

                    if (controller != null) {
                        Log.v("RegisterActivity", "user:   " + user + "       pass: " + passWord);
                        boolean register = controller.register(user, passWord);

                        if (register) {
                            btn_back.setVisibility(View.VISIBLE);
                            Toast.makeText(RegisterActivity.this, "注册成功，页面将在5秒后跳转,您可以点击返回键跳转到登录页面", Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this, "" + index, Toast.LENGTH_SHORT).show();
                            handler.sendEmptyMessageDelayed(0, 1000);
                        } else {
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.register_btn_back:
                finish();
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        controller = ((ChatService.ChatController) service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }


    /**
     * 页面跳转
     */
    public void turnActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * 解绑定
     */
    @Override
    protected void onDestroy() {
        unbindService(this);
        super.onDestroy();
    }
}
