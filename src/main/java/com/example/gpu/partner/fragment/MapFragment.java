package com.example.gpu.partner.fragment;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.example.gpu.partner.LocationService;
import com.example.gpu.partner.MyApplication;
import com.example.gpu.partner.R;
import com.example.gpu.partner.entity.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MapFragment extends Fragment {
    private MapView mMapView = null;
    private BaiduMap BDMap;
    private LocationService locationService;
    private Socket socket;
    private DataOutputStream out;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_map, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMapView = (MapView) getActivity().findViewById(R.id.bmapView);
        BDMap = mMapView.getMap();
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        user=myApplication.getUser();
        socket = myApplication.getSocket();
        if(socket!=null){
            try {
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BDMap.setMyLocationEnabled(true);
        BDMap.setMapStatus(MapStatusUpdateFactory.zoomTo(17));
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.man);
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, icon);
        BDMap.setMyLocationConfiguration(config);
        initOp();



    }


    private void initOp() {
        locationService = ((MyApplication) getActivity().getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getActivity().getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();
    }

    @Override
    public void onStop() {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        Log.i("baid", "stop");
        super.onStop();
    }

    @SuppressLint("StaticFieldLeak")
    public void sendPoint(String str) {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                String jsonStr = strings[0];
                try {
                    if (out != null) {
                        out.write(jsonStr.getBytes());
                        out.flush();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(str);
    }

    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                MyLocationData.Builder builder = new MyLocationData.Builder();
                builder.longitude(location.getLongitude());
                builder.latitude(location.getLatitude());
                builder.accuracy(location.getRadius());
                builder.direction(location.getDirection());
                BDMap.setMyLocationData(builder.build());
                Map<String, Object> map = new HashMap<>();
                map.put("uid","a1");
                map.put("type", "3");
                map.put("user", user);
                map.put("lontitude", location.getLongitude());
                map.put("latitude", location.getLatitude());
                String jsonStr = JSON.toJSONString(map);Log.i("socket", jsonStr);
                sendPoint(jsonStr);


                //Log.i("baidu123","lontitude : "+location.getLongitude()+",latitude : "+location.getLatitude());
            }
        }

    };


}


