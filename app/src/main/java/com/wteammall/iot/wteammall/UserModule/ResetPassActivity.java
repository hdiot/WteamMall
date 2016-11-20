package com.wteammall.iot.wteammall.UserModule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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

public class ResetPassActivity extends AppCompatActivity {

    TextView TV_BACK;
    TextView TV_ToMain;

    TextView TV_NameError;
    TextView TV_EmailError;
    TextView TV_VCodeError;
    TextView TV_MCodeError;
    TextView TV_NewPassError;

    ImageView IV_VCode;

    Button BT_GetMCode;
    Button BT_ResetPass;

    EditText ET_UserName;
    EditText ET_Email;
    EditText ET_VCode;
    EditText ET_EmailCode;
    EditText ET_NewPassword;

    String UserName;
    String Email;
    String VCdoe;
    String EmailCode;
    String NewPassword;

    String UserName_E = " ";
    String Email_E = " ";
    String VCode_E = " ";
    String EmailCode_E = " ";
    String NewPassword_E = " ";
    String Succeed = " ";

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        sharedPreferences = getSharedPreferences("JSESSIONID", MODE_PRIVATE);

        initView();

        setListener();

        getVCode();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            UserName_E = " ";
            Email_E = " ";
            VCode_E = " ";
            NewPassword_E = " ";
            EmailCode_E = " ";

            switch (msg.what) {

                case 0:
                    //Log.d("返回结果", (String) msg.obj);
                    Toast.makeText(ResetPassActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    break;

                case 1:
                    IV_VCode.setImageBitmap((Bitmap) msg.obj);
                    break;

                case 2:
                    try{
                        String result = (String) msg.obj;
                        Log.d("返回结果", result);
                        JSONObject jsonObject = new JSONObject();
                        StringReader stringReader = new StringReader(result);
                        JsonReader jsonReader = new JsonReader(stringReader);
                        jsonReader.beginObject();
                        if(jsonReader.nextName().contains("errors")){

                            jsonReader.beginObject();
                            while (jsonReader.hasNext()){
                                String errorinfo = jsonReader.nextName();
                                Log.d("errorinfo",errorinfo);
                                if(errorinfo.contains("username")){
                                    UserName_E = jsonReader.nextString();
                                    Log.d("Email_E",UserName_E);
                                }else if (errorinfo.contains("email")){
                                    Email_E = jsonReader.nextString();
                                    Log.d("Email_E",Email_E);
                                }else if (errorinfo.contains("vCode")){
                                    VCode_E = jsonReader.nextString();
                                    Log.d("VCode_E",VCode_E);
                                }else if (errorinfo.contains("msg")){
                                    Email_E = jsonReader.nextString();
                                    Log.d("MSG",Email_E);
                                }
                            }
                            jsonReader.endObject();

                        }else{
                            Succeed = jsonReader.nextString();
                            Toast.makeText(ResetPassActivity.this,Succeed,Toast.LENGTH_SHORT).show();
                            //跳转至主页面
                        }
                        jsonReader.endObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    Log.d("返回结果", (String) msg.obj);

                    try{
                        String result = (String) msg.obj;
                        Log.d("返回结果", result);
                        JSONObject jsonObject = new JSONObject();
                        StringReader stringReader = new StringReader(result);
                        JsonReader jsonReader = new JsonReader(stringReader);
                        jsonReader.beginObject();
                        if(jsonReader.nextName().contains("msg")){
                            Succeed = jsonReader.nextString();
                            Toast.makeText(ResetPassActivity.this,Succeed,Toast.LENGTH_SHORT).show();
                            //跳转至主页面
                        }else{
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()){
                                String errorinfo = jsonReader.nextName();
                                if(errorinfo.contains("msg")){
                                    NewPassword_E = jsonReader.nextString();
                                    Log.d("MSG",NewPassword_E);
                                }else if (errorinfo.contains("newpass")){
                                    NewPassword_E = jsonReader.nextString();
                                    Log.d("NewPassword_E",NewPassword_E);
                                }else if (errorinfo.contains("identifycode")){
                                    EmailCode_E = jsonReader.nextString();
                                    Log.d("EmailCode_E",EmailCode_E);
                                }else if (errorinfo.contains("username")){
                                    UserName_E = jsonReader.nextString();
                                    Log.d("UserName_E",UserName_E);
                                }
                            }
                            jsonReader.endObject();
                        }
                        jsonReader.endObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }

            TV_NewPassError.setText(NewPassword_E);
            TV_VCodeError.setText(VCode_E);
            TV_NameError.setText(UserName_E);
            TV_EmailError.setText(Email_E);
            TV_MCodeError.setText(EmailCode_E);
        }
    };

    public void initView() {

        TV_BACK = (TextView) findViewById(R.id.tv_resetpass_back);
        TV_ToMain = (TextView) findViewById(R.id.tv_resetpass_tomain);
        TV_VCodeError = (TextView) findViewById(R.id.tv_resetpass_vcodeerror);
        TV_EmailError = (TextView) findViewById(R.id.tv_resetpass_emailerror);
        TV_MCodeError = (TextView) findViewById(R.id.tv_resetpass_mcodeerror);
        TV_NameError = (TextView) findViewById(R.id.tv_resetpass_nameerror);
        TV_NewPassError = (TextView) findViewById(R.id.tv_resetpass_newpasserror);

        IV_VCode = (ImageView) findViewById(R.id.iv_resetpass_vcode);

        ET_Email = (EditText) findViewById(R.id.et_resetpass_email);
        ET_EmailCode = (EditText) findViewById(R.id.et_resetpass_mcode);
        ET_NewPassword = (EditText) findViewById(R.id.et_resetpass_newpass);
        ET_UserName = (EditText) findViewById(R.id.et_resetpass_username);
        ET_VCode = (EditText) findViewById(R.id.et_resetpass_vcode);

        BT_GetMCode = (Button) findViewById(R.id.bt_resetpass_getmcode);
        BT_ResetPass = (Button) findViewById(R.id.bt_resetpass_reseting);
    }

    public void setListener() {
        IV_VCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVCode();
            }
        });

        TV_BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResetPassActivity.this, LoginActivity.class));
                finish();
            }
        });

        TV_ToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResetPassActivity.this, MainActivity.class));
                finish();
            }
        });

        BT_ResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("--","重置密码");
                resetPass();
            }
        });

        BT_GetMCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("--","发送邮箱验证码");
                getEmailCode();
            }
        });
    }

    public void resetPass() {
        UserName = ET_UserName.getText().toString();
        NewPassword = ET_NewPassword.getText().toString();
        EmailCode = ET_EmailCode.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody requestBody = new FormBody.Builder()
                            .add("username", UserName)
                            .add("newpass", NewPassword)
                            .add("indentifycode", EmailCode)
                            .build();
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url(ValueUtils.URL_RESET_PASS)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {

                        String result = response.body().string();
                        Message resetMsg = new Message();
                        resetMsg.what = 3;
                        resetMsg.obj = result;
                        handler.sendMessage(resetMsg);
                        Log.d("返回结果", "NO-GG-3");

                    } else {
                        Log.d("返回结果", "GG-3");
                        handler.sendEmptyMessage(0);

                    }
                } catch (IOException e) {
                    Log.d("返回结果", "GG-2");
                    handler.sendEmptyMessage(0);
                    e.printStackTrace();

                }
            }
        }).start();
    }

    public void getVCode() {
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
                        msg.what = 1;
                        //把消息发送至主线程的消息队列
                        handler.sendMessage(msg);

                        String Set_Cookie = response
                                .header("Set-Cookie")
                                .toString();
                        String Cookie[] = Set_Cookie.split(";");
                        String jsid = Cookie[0];
                        sharedPreferences
                                .edit()
                                .putString("jsessionid", jsid)
                                .commit();

                        Log.d("JSID", jsid);

                    } else {
                        Message message = new Message();
                        message.what = 0;
                        handler.sendMessage(message);
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    Message message = new Message();
                    message.what = 0;
                    handler.handleMessage(message);
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void getEmailCode() {

        Log.d("返回结果", "NO-GG-2-1");
        UserName = ET_UserName.getText().toString();
        Email = ET_Email.getText().toString();
        VCdoe = ET_VCode.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("返回结果", "NO-GG-2-2");
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody requestBody = new FormBody.Builder()
                        .add("username", UserName)
                        .add("email", Email)
                        .add("vCode", VCdoe)
                        .build();
                Request request = new Request.Builder()
                        .post(requestBody)
                        .url(ValueUtils.URL_SEND_RESET_CODE_TO_EMAIL)
                        .addHeader("Cookie", sharedPreferences
                                .getString("jsessionid", "")
                                .toString())
                        .build();
                try {
                    Log.d("返回结果", "NO-GG-2-3");
                    Response response = okHttpClient
                            .newCall(request)
                            .execute();
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        Message getMCodeMsg = new Message();
                        Log.d("返回结果", result);
                        getMCodeMsg.obj = result;
                        getMCodeMsg.what = 2;
                        handler.sendMessage(getMCodeMsg);
                    } else {
                        Log.d("返回结果", "GG-2");
                        handler.sendEmptyMessage(0);
                    }
                } catch (IOException e) {

                    handler.sendEmptyMessage(0);
                    Log.d("返回结果", "GG");
                    e.printStackTrace();

                }
            }
        }).start();
    }
}
