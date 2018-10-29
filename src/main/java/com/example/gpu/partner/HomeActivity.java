package com.example.gpu.partner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.gpu.partner.entity.User;
import com.example.gpu.partner.fragment.MapFragment;
import com.example.gpu.partner.fragment.MyFragment;
import com.example.gpu.partner.fragment.PersonalFragment;
import com.example.gpu.partner.fragment.ToolFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends FragmentActivity {
    private BottomNavigationBar bottomBar;
    private ViewPager viewPager;
    private List<Fragment> listfragment; //ViewPager的数据
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initBottonBar();
        initPageview();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cheacpLogin();
    }
    private void showMsg(String str){
        Toast.makeText(HomeActivity.this,str,Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    private void initPageview(){
        viewPager = findViewById(R.id.viewPager);
        listfragment=new ArrayList<Fragment>();
        listfragment.add(new MapFragment());
        listfragment.add(new ToolFragment());
        listfragment.add(new PersonalFragment());
        FragmentManager fm=getSupportFragmentManager();
        MyFragment mfm=new MyFragment(fm,listfragment);
        viewPager.setAdapter(mfm);
        viewPager.setCurrentItem(0);


    }
    private void initBottonBar(){
        bottomBar= (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomBar.setBarBackgroundColor(R.color.white);
        bottomBar.addItem(new BottomNavigationItem(R.drawable.map, "位置").setActiveColorResource(R.color.pick))
                .addItem(new BottomNavigationItem(R.drawable.tool, "工具").setActiveColorResource(R.color.pick))
                .addItem(new BottomNavigationItem(R.drawable.we, "我的").setActiveColorResource(R.color.pick))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成
        bottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position){
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        break;
                    case 3:
                        viewPager.setCurrentItem(3);
                        break;
                }

            }
            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }
    @SuppressLint("StaticFieldLeak")
    private void cheacpLogin(){
        SharedPreferences sp=getSharedPreferences("information", Context.MODE_PRIVATE);
        user=new User();
        user.username=sp.getString("username","");
        user.pw=sp.getString("password","");
        user.cookie=sp.getString("cookie","");
        Log.i("login",user.toString());
        if("".equals(user.cookie))
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
        else{

        }
    }
}
