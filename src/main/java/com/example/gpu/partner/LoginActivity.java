package com.example.gpu.partner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.gpu.partner.entity.User;
import com.example.gpu.partner.utils.AesEncode;
import com.example.gpu.partner.utils.HttpRequest;


public class LoginActivity extends Activity {
    private Button login_bt;
    private Button reg_bt;
    private EditText userText;
    private EditText pwText;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        login_bt=findViewById(R.id.login_login_bt);
        reg_bt=findViewById(R.id.login_register_bt);
        userText=findViewById(R.id.login_user_text);
        pwText=findViewById(R.id.login_password_text);
        if(user==null)
            user=new User();
        login_bt.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                user.username=userText.getText().toString();
                user.pw=pwText.getText().toString();
                login task=new login();
                task.execute(user);


            }
        });
        reg_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
    private void sendMsg(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }
    private void jump(){
        this.finish();
    }

    class login extends AsyncTask<User,Void,User>{
        @Override
        protected User doInBackground(User... users) {
            User user=users[0];
            HttpRequest http=new HttpRequest();
            String url="http://192.168.43.87:1024/login";
            String data="username="+user.username+"&password="+user.pw;
            String str=http.doPost(url,data,true,false);
            Log.i("aaaaaaaaaaaaaaaaaa",str);
            JSONObject msg= JSON.parseObject(str);
            String code=msg.getString("error");
            user.cookie=http.getCookie();
            user.uid=msg.getString("uid");
            user.msg=msg.getString("massage");
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            Log.i("aaaaaaaaaaaaaaaaaa",user.toString());
            if("success".equals(user.msg)){
                MyApplication application = (MyApplication)getApplication();
                application.setUser(user);
                SharedPreferences sp=getSharedPreferences("information", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("username",user.username);
                editor.putString("password",user.pw);
                editor.putString("cookie",user.cookie);
                editor.putString("uid",user.uid);
                editor.commit();
                sendMsg("登录成功");
                jump();
            }else {
                sendMsg(user.msg);
            }

        }
    }

}
