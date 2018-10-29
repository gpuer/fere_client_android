package com.example.gpu.partner.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gpu.partner.MyApplication;
import com.example.gpu.partner.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ToolFragment extends Fragment {
    private Button bt;
    private Socket socket;
    private DataOutputStream out;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_tool, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        bt=getActivity().findViewById(R.id.send);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication myApplication = (MyApplication) getActivity().getApplication();
                socket=myApplication.getSocket();
                try {
                    out=new DataOutputStream(socket.getOutputStream());
                    out.write("hello".getBytes());
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

}
