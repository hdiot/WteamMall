package com.wteammall.iot.wteammall.UserModule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wteammall.iot.wteammall.MainActivity;
import com.wteammall.iot.wteammall.R;
import com.wteammall.iot.wteammall.Utils.ValueUtils;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    EditText ET_UserID;         //用户名编辑框
    EditText ET_Password;       //密码编辑框
    EditText ET_VCode;          //验证码
    ImageView IV_VCode;            //验证码
    Button BT_Login;            //登录按钮
    Button BT_Register;         //标题栏注册按钮
    Button BT_Back;             //标题栏返回按钮
    TextView TV_ForgetPWD;      //忘记密码文本框

    TextView TV_UserNameError;  //用户名错误提示文本框
    TextView TV_PasswordError;  //密码错误提示文本框
    TextView TV_VCodeError;     //验证码错误提示文本框

    String UserID;              //用户ID
    String Password;            //密码
    String VCode;               //验证码
    String IMEI;                //android机器码

    String succeed = " ";
    String username = " ";
    String password = " ";
    String vCode = " ";

    SharedPreferences sharedPreferences;

    Handler handler = new Handler() {
        //此方法在主线程中调用，可以用来刷新ui
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    IV_VCode.setImageBitmap((Bitmap) msg.obj);
                    break;
                case 1:
                    Log.d("Login", (String) msg.obj);
                    String result = (String) msg.obj;
                    try {
                        succeed = " ";
                        username = " ";
                        password = " ";
                        vCode = " ";

                        StringReader stringReader = new StringReader(result);
                        JsonReader jsonReader = new JsonReader(stringReader);
                        jsonReader.beginObject();
                        if(jsonReader.nextName().contains("msg")){
                            succeed = jsonReader.nextString();
                            Toast.makeText(LoginActivity.this,succeed,Toast.LENGTH_SHORT).show();
                            //跳转至主页面
                        }else{
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()){
                                String errorinfo = jsonReader.nextName();
                                if (errorinfo.contains("username")){
                                    username = jsonReader.nextString();
                                }else if(errorinfo.contains("password")){
                                    password = jsonReader.nextString();
                                }else if (errorinfo.contains("vCode")){
                                    vCode = jsonReader.nextString();
                                }
                            }
                            jsonReader.endObject();
                        }
                        jsonReader.endObject();

                        TV_UserNameError.setText(username);
                        TV_PasswordError.setText(password);
                        TV_VCodeError.setText(vCode);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
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

        sharedPreferences = getSharedPreferences("JSESSIONID", MODE_PRIVATE);

        //
        getVCode(sharedPreferences);

        //设置监听
        setListener();

    }

    public void getVCode(final SharedPreferences sp) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(ValueUtils.URL_VERIFYCODE).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                        Message msg = new Message();
                        //消息对象可以携带数据
                        msg.obj = bm;
                        msg.what = 0;
                        //把消息发送至主线程的消息队列
                        handler.sendMessage(msg);

                        String Set_Cookie = response.header("Set-Cookie").toString();
                        String Cookie[] = Set_Cookie.split(";");
                        String jsid = Cookie[0];
                        sp.edit().putString("jsessionid", jsid).commit();

                        Log.d("JSID", jsid);

                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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

        TV_PasswordError = (TextView)findViewById(R.id.tv_login_passerror);
        TV_UserNameError = (TextView)findViewById(R.id.tv_login_usernameerror);
        TV_VCodeError = (TextView)findViewById(R.id.tv_login_vcodeerror);

    }

    /**
     * 设置监听
     */
    public void setListener() {

        BT_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        BT_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        BT_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        TV_ForgetPWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ResetPassActivity.class));
            }
        });

        IV_VCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVCode(sharedPreferences);
            }
        });
    }

    /**
     * @return 登录成功返回true
     */
    public void login() {

        UserID = ET_UserID.getText().toString();
        //Password = MD5Utils.encode(ET_Password.getText().toString());
        Password = ET_Password.getText().toString();
        VCode = ET_VCode.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                try {
                    FormBody requestBody = new FormBody.Builder()
                            .add("username", UserID)
                            .add("password", Password)
                            .add("vCode", VCode)
                            .add("token", IMEI)
                            .build();
                    Log.d("FormBody", requestBody.toString());
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url(ValueUtils.URL_LOGIN)
                            .addHeader("Cookie", sharedPreferences.getString("jsessionid", "").toString())
                            .build();

                    Response response = okHttpClient.newCall(request).execute();

                    if (response.isSuccessful()) {

                        Message msg = new Message();
                        //消息对象可以携带数据
                        msg.obj = response.body().string();
                        msg.what = 1;
                        //把消息发送至主线程的消息队列
                        handler.sendMessage(msg);

                        Log.d("TMD", "没有GGGG了");


                    } else {
                        Log.d("TMD", "GGGG了");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


}
