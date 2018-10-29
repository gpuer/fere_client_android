package com.example.gpu.partner.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gpu.partner.HomeActivity;
import com.example.gpu.partner.LoginActivity;
import com.example.gpu.partner.R;

public class PersonalFragment extends Fragment {
    private Button bt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_part, container, false);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bt=getActivity().findViewById(R.id.unlogin);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp=getActivity().getSharedPreferences("information", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        });
    }
}
