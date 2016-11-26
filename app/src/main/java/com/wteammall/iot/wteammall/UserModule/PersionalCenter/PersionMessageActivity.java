package com.wteammall.iot.wteammall.UserModule.PersionalCenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wteammall.iot.wteammall.Adapter.MessageAdapter;
import com.wteammall.iot.wteammall.Bean.MessageBean.UserMeaasge;
import com.wteammall.iot.wteammall.Bean.MessageBean.UserMeaasgeDetail;
import com.wteammall.iot.wteammall.Bean.MessageBean.UserMessages;
import com.wteammall.iot.wteammall.Bean.UserBean.MyUserInfoBean;
import com.wteammall.iot.wteammall.MainActivity;
import com.wteammall.iot.wteammall.R;
import com.wteammall.iot.wteammall.Utils.ValueUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 找回密码
 */
public class PersionMessageActivity extends AppCompatActivity {

    TextView TV_Back;
    TextView TV_ToMain;
    ListView LV_Messsage;

    UserMessages mUserMessages;
    UserMeaasge mUserMessage;
    UserMeaasgeDetail mMeaasgeDetail;
    List<UserMeaasgeDetail> mMessageList;

    MyUserInfoBean mUserInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_message);

        getLsetActivityInfo();

        initView();

        setListener();

        getMyMessageInfo();

    }

    public void getLsetActivityInfo(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mUserInfoBean = (MyUserInfoBean) bundle.getSerializable("UserInfoBean");
    }

    public void initView() {
        TV_Back = (TextView) findViewById(R.id.tv_per_msg_back);
        TV_ToMain = (TextView) findViewById(R.id.tv_per_msg_mainpage);
        LV_Messsage = (ListView) findViewById(R.id.lv_persion_message);
    }

    public void setListener() {
        TV_ToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersionMessageActivity.this, MainActivity.class));
            }
        });

        TV_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersionMessageActivity.this, PersonalCenterActivity.class));
            }
        });
    }

    public void initMessage(){
        MessageAdapter messageAdapter = new MessageAdapter(PersionMessageActivity.this,mUserMessages);
        LV_Messsage.setAdapter(messageAdapter);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Toast.makeText(PersionMessageActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    parseJson(msg.obj.toString());
                    initMessage();
                    break;
            }
        }
    };

    public void getMyMessageInfo() {
        final String IMEI = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody requestBody = new FormBody.Builder()
                            //.add("username", mUserInfoBean.getUserName())
                            //.add("token", IMEI)
                            .add("username","1234")
                            .add("token","1234567890")
                            .build();
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url(ValueUtils.URL_GET_USER_MESSAGELIST)
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


    /**
     * 解析请求结果的Json
     */
    public void parseJson(String jsonString) {
        Log.d("jsonString", jsonString);
        try {
            String key_1;
            String key_2;
            String key_3;
            StringReader stringReader = new StringReader(jsonString);
            JsonReader jsonReader = new JsonReader(stringReader);
            jsonReader.beginObject();
            if (jsonReader.hasNext()){
                key_1 = jsonReader.nextName();
                if (key_1.contains("messages")){
                    mUserMessages = new UserMessages();
                    jsonReader.beginArray();
                    mMessageList = new ArrayList<UserMeaasgeDetail>();
                    while (jsonReader.hasNext()){
                        mMeaasgeDetail = new UserMeaasgeDetail();
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()){
                            key_2 = jsonReader.nextName();
                            if (key_2.contains("acceptTime")){
                                mMeaasgeDetail.setAcceptTime(jsonReader.nextString());
                            }else if (key_2.contains("id")){
                                mMeaasgeDetail.setID(jsonReader.nextString());
                            }else if (key_2.contains("message")){
                                mUserMessage = new UserMeaasge();
                                jsonReader.beginObject();
                                while (jsonReader.hasNext()){
                                    key_3 = jsonReader.nextName();
                                    if (key_3.contains("name")){
                                        mUserMessage.setName(jsonReader.nextString());
                                    }else if (key_3.contains("type")){
                                        mUserMessage.setType(jsonReader.nextInt());
                                    }
                                }
                                jsonReader.endObject();
                                mMeaasgeDetail.setmMessage(mUserMessage);
                            }else if (key_2.contains("status")){
                                mMeaasgeDetail.setStatus(jsonReader.nextInt());
                            }
                        }
                        jsonReader.endObject();
                        mMessageList.add(mMeaasgeDetail);
                    }
                    jsonReader.endArray();
                    mUserMessages.setmMessageList(mMessageList);
                }else {
                    jsonReader.nextName();
                    jsonReader.beginObject();
                    jsonReader.nextName();
                    jsonReader.nextString();
                    jsonReader.beginObject();
                }
            }
            jsonReader.endObject();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
