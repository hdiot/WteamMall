package com.wteammall.iot.wteammall.UserModule;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wteammall.iot.wteammall.R;
import com.wteammall.iot.wteammall.Utils.MD5Utils;
import com.wteammall.iot.wteammall.Utils.URLUtils;
import com.wteammall.iot.wteammall.Utils.ValueUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText ET_UserID;         //用户名编辑框
    EditText ET_Password;       //密码编辑框
    EditText ET_VCode;          //验证码
    static ImageView IV_VCode;            //验证码
    Button BT_Login;            //登录按钮
    Button BT_Register;         //标题栏注册按钮
    Button BT_Back;             //标题栏返回按钮
    TextView TV_ForgetPWD;      //忘记密码文本框

    String UserID;              //用户ID
    String Password;            //密码
    String VCode;               //验证码
    String IMEI;                //android机器码

    Map<String, String> mReruestParams;

    static  Handler handler = new Handler(){
        //此方法在主线程中调用，可以用来刷新ui
        public void handleMessage(android.os.Message msg) {

            IV_VCode.setImageBitmap((Bitmap) msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取android机器码
        IMEI = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();

        //加载view
        initView();

        //
        getVCode();

        //设置监听
        setListener();

    }

    public void getVCode() {

        Thread t = new Thread(){
            @Override
            public void run() {
                //下载图片
                //1.确定网址
                String path = "http://mywebtest.cn/Hall/VerifyCode";
                try {
                    //2.把网址封装成一个url对象
                    URL url = new URL(path);
                    //3.获取客户端和服务器的连接对象，此时还没有建立连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //4.对连接对象进行初始化
                    //设置请求方法，注意大写
                    conn.setRequestMethod("GET");
                    //设置连接超时
                    conn.setConnectTimeout(5000);
                    //设置读取超时
                    conn.setReadTimeout(5000);
                    //5.发送请求，与服务器建立连接
                    conn.connect();
                    //如果响应码为200，说明请求成功
                    if(conn.getResponseCode() == 200){
                        //获取服务器响应头中的流，流里的数据就是客户端请求的数据
                        InputStream is = conn.getInputStream();
                        //读取出流里的数据，并构造成位图对象
                        Bitmap bm = BitmapFactory.decodeStream(is);


                        Message msg = new Message();
                        //消息对象可以携带数据
                        msg.obj = bm;
                        msg.what = 1;
                        //把消息发送至主线程的消息队列
                        handler.sendMessage(msg);

                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        };
        t.start();
    }


    /**
     * 加载view
     */
    public void initView() {
        BT_Login = (Button) findViewById(R.id.bt_login_sure);
        BT_Register = (Button) findViewById(R.id.bt_login_register);
        BT_Back = (Button) findViewById(R.id.bt_login_back);
        ET_UserID = (EditText) findViewById(R.id.et_login_userId);
        ET_Password = (EditText) findViewById(R.id.et_login_pwd);
        ET_VCode = (EditText) findViewById(R.id.et_login_vcode);
        TV_ForgetPWD = (TextView) findViewById(R.id.et_forgetPassword);
        IV_VCode = (ImageView) findViewById(R.id.iv_login_vcode);
    }

    /**
     * 设置监听
     */
    public void setListener() {
        BT_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    login();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        BT_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        BT_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TV_ForgetPWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        IV_VCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getVCode();
            }
        });
    }

    /**
     * @return 登录成功返回true
     */
    public void login() throws InterruptedException {
        UserID = ET_UserID.getText().toString();
        Password = MD5Utils.encode(ET_Password.getText().toString());
        VCode = ET_VCode.getText().toString();

        mReruestParams = new HashMap<String,String>();
        mReruestParams.put("token", IMEI);
        mReruestParams.put("vCode", VCode);
        mReruestParams.put("password", Password);
        mReruestParams.put("username", UserID);
        //登录网络请求
        URLUtils urlUtils = new URLUtils(ValueUtils.URL_LOGIN, mReruestParams);
        Log.d("login",urlUtils.post());

    }

}
