package com.wteammall.iot.wteammall.UserModule.PersionalCenter;

import android.content.Intent;
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

import com.wteammall.iot.wteammall.Bean.UserBean.MyUserInfoBean;
import com.wteammall.iot.wteammall.R;
import com.wteammall.iot.wteammall.Utils.ValueUtils;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CheckEmailActivity extends AppCompatActivity {

    EditText ET_UserName;
    EditText ET_VCode;
    EditText ET_MCode;
    TextView TV_Back;
    Button BT_checking;
    Button BT_GetEmailCode;
    ImageView IV_VCode;

    TextView TV_MCode_E;
    TextView TV_VCode_E;

    private String JSID;
    private String UserName;
    private String Email;
    private String VCdoe;
    private String IMEI;

    private String MCdoe_E;
    private String VCode_E;

    MyUserInfoBean userInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_email);

        IMEI = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        initView();
        setListener();
        getVCode();
        getUserInfo();
    }

    public void initView() {
        TV_Back = (TextView) findViewById(R.id.tv_per_checkemail_back);
        ET_UserName = (EditText) findViewById(R.id.et_checkemail_name);
        ET_VCode = (EditText) findViewById(R.id.et_checkemail_VCode);
        ET_MCode = (EditText) findViewById(R.id.et_checkemail_mcode);
        IV_VCode = (ImageView) findViewById(R.id.iv_checkemail_vcode);
        BT_checking = (Button) findViewById(R.id.bt_checkemail_checking);
        BT_GetEmailCode = (Button) findViewById(R.id.bt_chekemail_getmcode);
        TV_MCode_E = (TextView) findViewById(R.id.tv_checkemail_mcode_error);
        TV_VCode_E = (TextView) findViewById(R.id.tv_checkemail_vcode_error);
    }

    public void setListener() {
        BT_checking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checking();
            }
        });


        BT_GetEmailCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEmailCode();
            }
        });

        TV_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckEmailActivity.this,PersionInformationActivity.class));
            }
        });
        IV_VCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVCode();
            }
        });
    }

    public void getUserInfo() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userInfoBean = (MyUserInfoBean) bundle.get("UserInfoBean");
        if(userInfoBean != null){
            ET_UserName.setText(userInfoBean.getUserName());
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            StringReader stringReader;
            JsonReader jsonReader;
            String key_1;
            String key_2;
            MCdoe_E = "";
            VCode_E = "";
            switch (msg.what) {
                case 0:
                    Toast.makeText(CheckEmailActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    IV_VCode.setImageBitmap((Bitmap) msg.obj);
                    break;
                case 2:
                    JSID = (String) msg.obj;
                    break;
                case 3:
                    try {
                        String VeryInfo = (String) msg.obj;

                        stringReader = new StringReader(VeryInfo);
                        jsonReader = new JsonReader(stringReader);
                        jsonReader.beginObject();
                        key_1 = jsonReader.nextName();
                        if (key_1.contains("error")) {
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()) {
                                key_2 = jsonReader.nextName();
                                if (key_2.contains("username")) {
                                    jsonReader.nextName();
                                } else if (key_2.contains("vCode")) {
                                    VCode_E = jsonReader.nextString();
                                }else if (key_2.contains("token")){
                                    jsonReader.nextString();
                                    Toast.makeText(CheckEmailActivity.this, "发送验证码至邮箱失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                            jsonReader.endObject();
                        } else if (key_1.contains("msg")) {
                            jsonReader.beginObject();
                            jsonReader.nextName();
                            key_2 = jsonReader.nextString();
                            Toast.makeText(CheckEmailActivity.this, key_2, Toast.LENGTH_SHORT).show();
                            jsonReader.endObject();
                        }
                        jsonReader.endObject();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    String Result = (String) msg.obj;
                    Log.d("Result",Result);
                    try {
                        stringReader = new StringReader(Result);
                        jsonReader = new JsonReader(stringReader);
                        jsonReader.beginObject();
                        key_1 = jsonReader.nextName();
                        if(key_1.contains("msg")){
                            key_2 = jsonReader.nextString();
                            Toast.makeText(CheckEmailActivity.this, key_2, Toast.LENGTH_SHORT).show();
                        }else if (key_1.contains("error")){
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()){
                                key_2 = jsonReader.nextName();
                                if (key_2.contains("msg")){
                                    Toast.makeText(CheckEmailActivity.this, jsonReader.nextString() , Toast.LENGTH_SHORT).show();
                                }else if (key_2.contains("identifycode")){
                                    MCdoe_E = jsonReader.nextString();
                                }else if(key_2.contains("token")){
                                    jsonReader.nextString();
                                    Toast.makeText(CheckEmailActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                                }else if (key_2.contains("username")){
                                    jsonReader.nextString();
                                    Toast.makeText(CheckEmailActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                            jsonReader.endObject();
                        }
                        jsonReader.endObject();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            TV_VCode_E.setText(VCode_E);
            TV_MCode_E.setText(MCdoe_E);
        }
    };

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

                        Message msg;
                        msg = new Message();
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
                        Log.d("JSID", jsid);
                        msg = new Message();
                        msg.what = 2;
                        msg.obj = jsid;
                        handler.sendMessage(msg);

                    } else {
                        Message message = new Message();
                        message.what = 0;
                        handler.sendMessage(message);
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    handler.sendEmptyMessage(0);
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void getEmailCode() {

        Log.d("返回结果", "NO-GG-2-1");
        UserName = ET_UserName.getText().toString();
        VCdoe = ET_VCode.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("返回结果", "NO-GG-2-2");
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody requestBody = new FormBody.Builder()
                        .add("username", UserName)
                        .add("tocken", IMEI)
                        .add("vCode", VCdoe)
                        .build();
                Request request = new Request.Builder()
                        .post(requestBody)
                        .url(ValueUtils.URL_SEND_CODE_TO_EMAIL)
                        .addHeader("Cookie", JSID)
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
                        getMCodeMsg.what = 3;
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

    public void Checking() {
        final String IdentiFyCode = ET_MCode.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("username",userInfoBean.getUserName())
                            .add("tocken", IMEI)
                            .add("identifycode",IdentiFyCode)
                            .build();
                    Request request = new Request.Builder()
                            .post(formBody)
                            .url(ValueUtils.URL_VERIFY_EMAI)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Message message = new Message();
                        message.obj = response.body().string();
                        message.what = 4;
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

}
