package com.wteammall.iot.wteammall;

import android.content.Intent;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AnimationUtils;

import com.wteammall.iot.wteammall.UserModule.LoginActivity;

/**
 * 首页（主页）
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, LoginActivity.class));
    }
}
