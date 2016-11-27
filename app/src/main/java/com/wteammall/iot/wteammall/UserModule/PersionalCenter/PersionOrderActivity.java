package com.wteammall.iot.wteammall.UserModule.PersionalCenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wteammall.iot.wteammall.Bean.UserBean.MyUserInfoBean;
import com.wteammall.iot.wteammall.MainActivity;
import com.wteammall.iot.wteammall.R;
import com.wteammall.iot.wteammall.Utils.ValueUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PersionOrderActivity extends AppCompatActivity {


    TextView TV_Back;
    TextView TV_ToMain;
    private MyUserInfoBean mUserInfoBean;
    private String IMEI;
    private String UserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_order);

        IMEI = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        getLsetActivityInfo();
        initView();
        setListener();
        getMyOrderInfo();
    }

    public void getLsetActivityInfo(){
        Intent intent = getIntent();
        if (intent != null){
            Log.d("UserName","intent");
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                Log.d("UserName","bundle");
                if (bundle.get("UserName") != null){
                    Log.d("UserName",bundle.getString("UserName"));
                    UserName = (String) bundle.get("UserName");
                }
            }
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public void initView(){
        TV_Back = (TextView) findViewById(R.id.tv_persion_ordertitle_back);
        TV_ToMain = (TextView) findViewById(R.id.tv_persion_order_title_mainpage);
    }

    public void setListener(){
        TV_ToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersionOrderActivity.this, MainActivity.class));
            }
        });

        TV_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersionOrderActivity.this, PersonalCenterActivity.class));
            }
        });
    }

    public void getMyOrderInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody requestBody = new FormBody.Builder()
                            .add("username",UserName)
                            .add("token", IMEI)
                            .add("tyep","")
                            .build();
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url(ValueUtils.URL_GET_ORDER_LIST)
                            .build();

                    Response response = okHttpClient
                            .newCall(request)
                            .execute();
                    if (response.isSuccessful()) {
                        Message message = new Message();
                        message.what = 1;
                        message.obj = response.body().string();
                        handler.sendMessage(message);
                    } else {
                        handler.sendEmptyMessage(0);

                    }
                } catch (IOException e) {
                    handler.sendEmptyMessage(0);
                    e.printStackTrace();
                }
            }
        }).start();
    }



    public void parseJson(String json){

    }

}
