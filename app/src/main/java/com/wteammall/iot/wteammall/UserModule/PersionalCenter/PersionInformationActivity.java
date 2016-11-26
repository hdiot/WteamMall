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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wteammall.iot.wteammall.Bean.UserBean.MyUserInfoBean;
import com.wteammall.iot.wteammall.MainActivity;
import com.wteammall.iot.wteammall.R;
import com.wteammall.iot.wteammall.Utils.ValueUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

    Button BT_Signing;                  //签到按钮
    Button BT_CheckEmail;               //邮箱验证按钮

    ImageView IV_Header;                //头像
    TextView TV_HeaderName;             //头像名称（这个好像有点鸡肋）

    TextView TV_TitleBack;              //标题栏返回按键
    TextView TV_TitleToMain;            //标题栏跳转主页按键

    MyUserInfoBean userInfoBean;         //用户信息容器

    String IMEI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_informatio);

        IMEI = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();

        initView();
        setListener();
        getPersionInfo();
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(PersionInformationActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    try {
                        JSONObject jsonObject = new JSONObject((String)msg.obj);
                        Toast.makeText(PersionInformationActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
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

    public void getPersionInfo(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userInfoBean = (MyUserInfoBean) bundle.get("UserInfoBean");
        if(userInfoBean != null){
            TV_HeaderName.setText(userInfoBean.getUserName()+"，您好");
            TV_UserName.setText(userInfoBean.getUserName());
            TV_Email.setText(userInfoBean.getEmail());
            TV_RegisterTime.setText(userInfoBean.getRegisterTime());
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

            getHeaderImg();
        }else {
            Toast.makeText(PersionInformationActivity.this,"好像出了点小问题",Toast.LENGTH_SHORT).show();
        }
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("username",userInfoBean.getUserName())
                            .add("token",IMEI)
                            .build();
                    Request request = new Request.Builder()
                            .post(formBody)
                            .url(ValueUtils.URL_USER_SIGN)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()){
                        Message message = new Message();
                        message.what = 1;
                        message.obj = response.body().string();
                        handler.sendMessage(message);
                    }else{
                        handler.sendEmptyMessage(0);
                    }
                } catch (IOException e) {
                    handler.sendEmptyMessage(0);
                    e.printStackTrace();
                }
            }
        }){

        }.start();
    }

    /**
     * 执行邮箱验证
     */
    public void CheckMail() {
        Intent intent = new Intent(PersionInformationActivity.this,CheckEmailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("UserInfoBean",userInfoBean);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
