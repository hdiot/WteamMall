package com.wteammall.iot.wteammall.UserModule.PersionalCenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import com.wteammall.iot.wteammall.Bean.UserBean.MyUserInfoBean;
import com.wteammall.iot.wteammall.MainActivity;
import com.wteammall.iot.wteammall.R;
import com.wteammall.iot.wteammall.Utils.ValueUtils;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChangPassActivity extends AppCompatActivity {

    TextView TV_OldPass_E;
    TextView TV_NewPass_E;
    TextView TV_VCode_E;
    EditText ET_OldPass;
    EditText ET_NewPass;
    EditText ET_VCode;

    TextView TV_Back;
    TextView TV_ToMain;
    Button BT_Changing;
    ImageView IV_VCode;

    String NewPass;
    String OldPass;
    String VCode;
    String UserName;

    String NewPass_E;
    String OldPass_E;
    String VCode_E;

    SharedPreferences sharedPreferences;
    private MyUserInfoBean mUserInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_pass);

        getLsetActivityInfo();

        initView();

        setListener();

        sharedPreferences = getSharedPreferences("JSESSIONID", MODE_PRIVATE);

        getVCode(sharedPreferences);


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NewPass_E = " ";
            OldPass_E = " ";
            VCode_E = " ";
            switch (msg.what) {
                case 0:
                    Toast.makeText(ChangPassActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    parseJson(msg.obj.toString());
                    TV_NewPass_E.setText(NewPass_E);
                    TV_OldPass_E.setText(OldPass_E);
                    TV_VCode_E.setText(VCode_E);
                    break;
                case 2:
                    IV_VCode.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    public void getLsetActivityInfo() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mUserInfoBean = (MyUserInfoBean) bundle.getSerializable("UserInfoBean");
    }

    public void initView() {

        TV_Back = (TextView) findViewById(R.id.tv_per_changepass_title_back);
        TV_NewPass_E = (TextView) findViewById(R.id.tv_changepass_newpssserror);
        TV_OldPass_E = (TextView) findViewById(R.id.tv_changepass_oldpasserrror);
        TV_ToMain = (TextView) findViewById(R.id.tv_per_changepass_title_back);
        TV_VCode_E = (TextView) findViewById(R.id.tv_changepass_vcodeerror);

        BT_Changing = (Button) findViewById(R.id.bt_changpass_changing);
        IV_VCode = (ImageView) findViewById(R.id.iv_changepass_vcode);

        ET_NewPass = (EditText) findViewById(R.id.et_changepass_newpass);
        ET_OldPass = (EditText) findViewById(R.id.et_cahngepass_oldpass);
        ET_VCode = (EditText) findViewById(R.id.et_changepass_vocde);
    }

    public void setListener() {
        TV_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangPassActivity.this, PersionInformationActivity.class));
            }
        });

        TV_ToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangPassActivity.this, MainActivity.class));
            }
        });

        BT_Changing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
            }
        });

        IV_VCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVCode(sharedPreferences);
            }
        });
    }

    public void changePass() {
        OldPass = ET_OldPass.getText().toString();
        NewPass = ET_NewPass.getText().toString();
        VCode = ET_VCode.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody requestBody = new FormBody
                            .Builder()
                            .add("username", mUserInfoBean.getUserName())
                            .add("password", OldPass)
                            .add("newpass", NewPass)
                            .add("vCode", VCode)
                            .build();
                    Request request = new Request.Builder()
                            .url(ValueUtils.URL_CHANGE_PASS)
                            .addHeader("Cookie", sharedPreferences.getString("jsessionid", "").toString())
                            .post(requestBody)
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
                        msg.what = 2;
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

    public void parseJson(String jsonString) {
        Log.d("jsonString", jsonString);
        try {
            StringReader stringReader = new StringReader(jsonString);
            JsonReader jsonReader = new JsonReader(stringReader);
            jsonReader.beginObject();
            if (jsonReader.nextName().contains("error")) {
                jsonReader.beginObject();
                String key;
                int i = 1;
                while (jsonReader.hasNext()) {
                    Log.d("i", i + "");
                    key = jsonReader.nextName();
                    if (key.contains("username")) {
                        jsonReader.nextString();
                    } else if (key.contains("newpass")) {
                        NewPass_E = jsonReader.nextString();
                    } else if (key.contains("vCode")) {
                        VCode_E = jsonReader.nextString();
                    } else if (key.contains("password")) {
                        OldPass_E = jsonReader.nextString();
                        Log.d("newPass", OldPass_E);
                    }
                    i++;
                }
                jsonReader.endObject();

            } else {
                jsonReader.beginObject();
                String msgName = jsonReader.nextName();
                String msg = jsonReader.nextString();
                Toast.makeText(ChangPassActivity.this, msg, Toast.LENGTH_SHORT).show();
                Log.d("msg", msg);
            }
            jsonReader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
