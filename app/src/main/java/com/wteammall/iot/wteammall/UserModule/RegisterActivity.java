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

import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    String mName;
    String mEmail;
    String mPassword;
    String mVCode;

    String IMEI;

    EditText ET_Nmae;
    EditText ET_Email;
    EditText ET_Password;
    EditText ET_VCdeo;
    ImageView IV_VCode;
    Button BT_Register;

    TextView TV_NameError;
    TextView TV_PassError;
    TextView TV_EmailError;
    TextView TV_VCodeError;

    TextView TV_TitleBack;
    TextView TV_TitleToMain;

    SharedPreferences sharedPreferences;

    String succeed = " ";
    String username = " ";
    String password = " ";
    String vCode = " ";
    String email = " ";

    Handler handler = new Handler() {
        //此方法在主线程中调用，可以用来刷新ui

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    IV_VCode.setImageBitmap((Bitmap) msg.obj);
                    break;
                case 1:

                    String result = (String) msg.obj;
                    Log.d("---",result);
                    try {
                        succeed = " ";
                        username = " ";
                        password = " ";
                        vCode = " ";
                        email = " ";
                        JSONObject jsonObject = new JSONObject();
                        StringReader stringReader = new StringReader(result);
                        JsonReader jsonReader = new JsonReader(stringReader);
                        jsonReader.beginObject();
                        if(jsonReader.nextName().contains("msg")){
                            succeed = jsonReader.nextString();
                            Toast.makeText(RegisterActivity.this,succeed,Toast.LENGTH_SHORT).show();
                            if(succeed.contains("成功")){
                                //跳转至登录面
                                Bundle bundle = new Bundle();
                                bundle.putString("UserName",mName);
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                        }else{
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()){
                                String errorinfo = jsonReader.nextName();
                                if (errorinfo.contains("username")){
                                    username = jsonReader.nextString();
                                }else if(errorinfo.contains("password")){
                                    password = jsonReader.nextString();
                                }else if (errorinfo.contains("email")){
                                    email = jsonReader.nextString();
                                }else if (errorinfo.contains("vCode")){
                                    vCode = jsonReader.nextString();
                                }
                            }
                            jsonReader.endObject();
                        }
                        jsonReader.endObject();

                        TV_NameError.setText(username);
                        TV_PassError.setText(password);
                        TV_VCodeError.setText(vCode);
                        TV_EmailError.setText(email);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this,"网络出错",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        IMEI = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();

        sharedPreferences = getSharedPreferences("JSESSIONID", MODE_PRIVATE);

        initView();

        setListener();

        getVCode(sharedPreferences);


    }

    public void initView() {
        ET_Nmae = (EditText) findViewById(R.id.et_register_name);
        ET_Email = (EditText) findViewById(R.id.et_register_email);
        ET_Password = (EditText) findViewById(R.id.et_register_pass);
        ET_VCdeo = (EditText) findViewById(R.id.et_register_VCode);
        BT_Register = (Button) findViewById(R.id.bt_register_registing);
        IV_VCode = (ImageView) findViewById(R.id.iv_register_vcode);

        TV_EmailError = (TextView) findViewById(R.id.tv_register_emailerror);
        TV_NameError = (TextView) findViewById(R.id.tv_register_nameerror);
        TV_PassError = (TextView) findViewById(R.id.tv_register_passworderror);
        TV_VCodeError = (TextView) findViewById(R.id.tv_register_vcodeerror);

        TV_TitleBack = (TextView) findViewById(R.id.tv_registertitle_back);
        TV_TitleToMain = (TextView) findViewById(R.id.tv_registertitle_mainpage);
    }

    public void setListener() {
        BT_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        IV_VCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVCode(sharedPreferences);
            }
        });

        TV_TitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });

        TV_TitleToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    public void register() {
        mName = ET_Nmae.getText().toString();
        mEmail = ET_Email.getText().toString();
        mPassword = ET_Password.getText().toString();
        mVCode = ET_VCdeo.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                OkHttpClient okHttpClient = new OkHttpClient();
                try {
                    FormBody requestBody = new FormBody.Builder()
                            .add("username", mName)
                            .add("password", mPassword)
                            .add("vCode", mVCode)
                            .add("token", IMEI)
                            .add("email", mEmail)
                            .build();

                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url(ValueUtils.URL_URL_REGISTER)
                            .addHeader("Cookie", sharedPreferences.getString("jsessionid", "").toString())
                            .build();
                    Log.d("JSID", sharedPreferences.getString("jsessionid", "").toString());
                    Response response = okHttpClient.newCall(request).execute();

                    if (response.isSuccessful()) {
                        //消息对象可以携带数据
                        msg.obj = response.body().string();
                        msg.what = 1;
                        //把消息发送至主线程的消息队列
                        handler.sendMessage(msg);

                    } else {

                        handler.sendEmptyMessage(2);
                    }
                } catch (IOException e) {
                    handler.sendEmptyMessage(2);
                    e.printStackTrace();
                }
            }
        }).start();

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
                    handler.sendEmptyMessage(2);
                    e.printStackTrace();
                }
            }
        }).start();

    }


}
