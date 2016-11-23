package com.wteammall.iot.wteammall.UserModule.PersionalCenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wteammall.iot.wteammall.Bean.MyUserInfoBean;
import com.wteammall.iot.wteammall.MainActivity;
import com.wteammall.iot.wteammall.R;
import com.wteammall.iot.wteammall.Utils.ValueUtils;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PersionInformationActivity extends AppCompatActivity {

    TextView TV_SignStatus;             //签到状态
    TextView TV_UserName;               //姓名
    TextView TV_Email;                  //邮箱
    TextView TV_AccountStatus;          //账号状态
    TextView TV_RegisterTime;           //注册时间
    TextView TV_ChangePass;             //修改密码
    TextView TV_Points;                 //积分

    Button BT_Signing;                  //签到按钮
    Button BT_CheckEmail;               //邮箱验证按钮

    ImageView IV_Header;                //头像
    TextView TV_HeaderName;             //头像名称（这个好像有点鸡肋）

    TextView TV_TitleBack;              //标题栏返回按键
    TextView TV_TitleToMain;            //标题栏跳转主页按键

    String IMEI;
    String UserName;

    MyUserInfoBean userInfoBean = new MyUserInfoBean();          //用户信息容器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_informatio);

        initView();
        setListener();
        getPersonalInfo();
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //Log.d("------",msg.obj.toString());
            switch (msg.what) {
                case 0:
                    Toast.makeText(PersionInformationActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    parseJson(msg.obj.toString());
                    TV_HeaderName.setText(userInfoBean.getUserName()+"，您好");
                    TV_UserName.setText(userInfoBean.getUserName());
                    TV_Email.setText(userInfoBean.getEmail());
                    TV_RegisterTime.setText(userInfoBean.getRegisterTime());
                    TV_Points.setText(userInfoBean.getPoints()+" ");
                    if (userInfoBean.getSignStatus()){
                        TV_SignStatus.setText("已签到");
                    }else {
                        TV_SignStatus.setText("未签到");
                    }
                    if(userInfoBean.getAccountStatus()==1){
                        TV_AccountStatus.setText("已验证");
                    }else {
                        TV_AccountStatus.setText("未验证");
                    }
                    Log.d("Signed",(userInfoBean.getSignStatus()+"+"));
                    BT_Signing.setEnabled(!userInfoBean.getSignStatus());
                    if(userInfoBean.getAccountStatus()==0){
                        BT_CheckEmail.setEnabled(true);
                    }else {
                        BT_CheckEmail.setEnabled(false);
                    }
                    break;
                case 2:
                    IV_Header.setImageBitmap((Bitmap) msg.obj);
                    break;
                case 3:
                    break;
            }
        }
    };

    public void initView() {

        TV_TitleBack = (TextView) findViewById(R.id.tv_per_info_title_back);
        TV_TitleToMain = (TextView) findViewById(R.id.tv_per_info_title_mainpage);

        TV_SignStatus = (TextView) findViewById(R.id.tv_per_info_signed_statue);
        TV_UserName = (TextView) findViewById(R.id.tv_per_info_username);
        TV_ChangePass = (TextView) findViewById(R.id.tv_per_info_changepass);
        TV_Email = (TextView) findViewById(R.id.tv_per_info_email);
        TV_AccountStatus = (TextView) findViewById(R.id.tv_per_info_account_status);
        TV_RegisterTime = (TextView) findViewById(R.id.tv_per_info_register_time);
        TV_HeaderName = (TextView) findViewById(R.id.tv_per_info_headername);
        TV_Points = (TextView) findViewById(R.id.tv_per_info_points);

        IV_Header = (ImageView) findViewById(R.id.iv_per_info_header);
        BT_Signing = (Button) findViewById(R.id.bt_per_info_signing);
        BT_CheckEmail = (Button) findViewById(R.id.bt_per_info_check_email);

    }

    public void setListener() {

        TV_TitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersionInformationActivity.this, PersonalCenterActivity.class));
            }
        });

        TV_TitleToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersionInformationActivity.this, MainActivity.class));
            }
        });

        TV_ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersionInformationActivity.this,ChangPassActivity.class));
            }
        });

        BT_CheckEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckMail();
            }
        });

        BT_Signing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signing();
            }
        });
    }


    /**
     * 获取个人信息
     */
    public void getPersonalInfo() {
        IMEI = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody requestBody = new FormBody.Builder()
                            .add("username", "haha")
                            .add("token", "1234567890")
                            .build();
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url(ValueUtils.URL_USERINFO)
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
                        Looper.prepare();
                        handler.sendEmptyMessage(0);
                        Looper.loop();
                    }
                } catch (IOException e) {
                    Looper.prepare();
                    handler.sendEmptyMessage(0);
                    Looper.loop();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getHeaderImg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient getHeader = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ValueUtils.URL_MAIN + userInfoBean.getHeaderImgUrl())
                        .build();
                try {
                    Response response = getHeader.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                        Message message = new Message();
                        message.what = 2;
                        message.obj = bitmap;
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    Looper.prepare();
                    handler.sendEmptyMessage(0);
                    Looper.loop();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 执行签到
     */
    public void Signing() {

    }

    /**
     * 执行邮箱验证
     */
    public void CheckMail() {

    }

    /**
     * 解析请求结果的Json
     */
    public void parseJson(String jsonString) {
        Log.d("jsonString",jsonString);
        try {
            StringReader stringReader = new StringReader(jsonString);
            JsonReader jsonReader = new JsonReader(stringReader);
            jsonReader.beginObject();
            String info;
            while (jsonReader.hasNext()){
                info = jsonReader.nextName();
                if(info.contains("user")){
                    jsonReader.beginObject();
                    String key;
                    int i = 1;
                    while (jsonReader.hasNext()){
                        Log.d("i",i+"");
                        key = jsonReader.nextName();
                        Log.d("i",i+"="+key);
                        if (key.contains("email")){
                            Log.d("i",i+"-1");
                            userInfoBean.setEmail(jsonReader.nextString());
                            Log.d("i",i+userInfoBean.getEmail());
                        }else  if (key.contains("headimgurl")){
                            Log.d("i",i+"-2");
                            userInfoBean.setHeaderImgUrl(jsonReader.nextString());
                            getHeaderImg();
                            Log.d("HEADURL",userInfoBean.getHeaderImgUrl());
                        }else if (key.contains("points")){
                            Log.d("i",i+"-3");
                            userInfoBean.setPoints(jsonReader.nextInt());
                        }else if (key.contains("register")){
                            Log.d("i",i+"-4");
                            userInfoBean.setRegisterTime(jsonReader.nextString());
                        }else if (key.contains("signed")){
                            Log.d("i",i+"-5");
                            userInfoBean.setSignStatus(jsonReader.nextBoolean());
                        }else if (key.contains("status")){
                            Log.d("i",i+"-6");
                            userInfoBean.setAccountStatus(jsonReader.nextInt());
                        }else if (key.contains("username")){
                            Log.d("i",i+"-7");
                            userInfoBean.setUserName(jsonReader.nextString());
                        }
                        i++;
                    }
                    jsonReader.endObject();

                    Log.d("user",userInfoBean.toString());
                }else if (info.contains("error")){
                    jsonReader.beginObject();
                    String msgName = jsonReader.nextName();
                    String msg = jsonReader.nextString();

                    Log.d("msg",msg);
                }else if (info.contains("underwayTask")){
                    jsonReader.nextInt();
                }else if (info.contains("unReadMessage")){
                    jsonReader.nextInt();
                }
            }
            jsonReader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
