package com.wteammall.iot.wteammall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wteammall.iot.wteammall.UserModule.LoginActivity;
import com.wteammall.iot.wteammall.UserModule.PersionalCenter.PersonalCenterActivity;

/**
 * 首页（主页）
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent != null){
            Log.d("UserName","intent");
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                Log.d("UserName","bundle");
                if (bundle.get("UserName") != null){
                    Log.d("UserName",bundle.getString("UserName"));
                }
            }
        }

        Button PersionalCenter = (Button) findViewById(R.id.persionnalcentern);
        Button Login = (Button) findViewById(R.id.login);

        PersionalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PersonalCenterActivity.class));
            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }

}
