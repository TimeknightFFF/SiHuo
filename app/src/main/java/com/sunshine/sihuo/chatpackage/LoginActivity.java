package com.sunshine.sihuo.chatpackage;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sunshine.sihuo.MainActivity;
import com.sunshine.sihuo.R;
import com.sunshine.sihuo.utils.SysApplication;

public class LoginActivity extends FragmentActivity implements ServiceConnection, View.OnClickListener {

    private EditText userName, passWord;
    private Button btn_login, btn_register;

    private ChatService.ChatController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        SysApplication.getInstance().addActivity(this);
        // 启动服务
        Intent intent = new Intent(LoginActivity.this, ChatService.class);
        startService(intent);

        // 控件初始化
        init();

        // 绑定服务
        bindService(intent,this,BIND_AUTO_CREATE);
    }

    private void init() {
        userName = ((EditText) findViewById(R.id.login_edit_name));
        passWord = ((EditText) findViewById(R.id.login_edit_pwd));

        btn_login = ((Button) findViewById(R.id.login_btn_login));
        btn_register = ((Button) findViewById(R.id.login_btn_register));

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        unbindService(this);
        super.onDestroy();
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        controller = ((ChatService.ChatController) service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_login:
                btnLoginOnClick();
                break;
            case R.id.login_btn_register:
                btnRegisterOnClick();
                break;
        }
    }

    /**
     * 登录的按钮响应
     */
    public void btnLoginOnClick(){

        if(controller != null){
            String userNameText = userName.getText().toString().trim();
            String passwordText = passWord.getText().toString().trim();

            Log.v("--DEBUG--", "--》》" + userNameText + " , " + passwordText);
            String login = controller.login(userNameText, passwordText);
            if(login!=null){
                //TODO 登录成功
                Intent intent = new Intent(this, MainActivity.class);

                intent.putExtra("userJID",login);

                startActivity(intent);
                finish();

            }else {
                //TODO 提示错误
                Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 注册按钮的点击事件
     */
    public void btnRegisterOnClick(){

        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);

    }
}
