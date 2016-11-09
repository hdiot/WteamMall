package com.wteammall.iot.wteammall.UserModule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wteammall.iot.wteammall.R;
import com.wteammall.iot.wteammall.Utils.URLUtils;
import com.wteammall.iot.wteammall.Utils.ValueUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText ET_UserID;         //用户名编辑框
    EditText ET_Password;       //密码编辑框
    EditText ET_VCode;          //验证码
    Button BT_Login;            //登录按钮
    Button BT_Register;         //标题栏注册按钮
    Button BT_Back;             //标题栏返回按钮
    TextView TV_ForgetPWD;      //忘记密码文本框

    String UserID;              //用户ID
    String Password;            //密码
    String VCode;               //验证码
    String IMEI;                //android机器码

    Map<String , String > mReruestParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取android机器码
        IMEI = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();

        //加载view
        initView();

        //设置监听
        setListener();
    }

    /**
     * 加载view
     */
    public void initView(){
        BT_Login = (Button) findViewById(R.id.bt_login_sure);
        BT_Register = (Button) findViewById(R.id.bt_login_register);
        BT_Back = (Button) findViewById(R.id.bt_login_back);
        ET_UserID = (EditText) findViewById(R.id.et_login_userId);
        ET_Password = (EditText) findViewById(R.id.et_login_pwd);
        ET_VCode = (EditText) findViewById(R.id.et_login_vcode);
        TV_ForgetPWD = (TextView) findViewById(R.id.et_forgetPassword);
    }

    public void getVCode(){
        URLUtils urlUtils = new URLUtils(ValueUtils.URL_VERIFYCODE);
        urlUtils.get();
    }

    /**
     * 设置监听
     */
    public void setListener() {

        BT_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        BT_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
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
    }

    /**
     * @return 登录成功返回true
     */
    public void login(){
        UserID = ET_UserID.getText().toString();
        Password = ET_Password.getText().toString();
        VCode = ET_VCode.getText().toString();

        mReruestParams = new HashMap<String , String>();
        mReruestParams.put("username",UserID);
        mReruestParams.put("password",Password);
        mReruestParams.put("vCode",VCode);
        mReruestParams.put("token",IMEI);

        //登录网络请求
        URLUtils urlUtils = new URLUtils(ValueUtils.URL_LOGIN, mReruestParams);

    }

    public boolean checkLoginInfo(String userid,String password){
        if("".equals(userid)||"".equals(password)){
            Toast.makeText(LoginActivity.this,"用户名和密码不能为空",Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }
}
