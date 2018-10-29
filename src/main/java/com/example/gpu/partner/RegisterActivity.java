package com.example.gpu.partner;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
    private Button reg_bt;
    private EditText nickname_text;
    private EditText password_text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void sendMsg(String str){
        Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_LONG).show();
    }

}

