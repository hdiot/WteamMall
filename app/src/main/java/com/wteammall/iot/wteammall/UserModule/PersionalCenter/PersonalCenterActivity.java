package com.wteammall.iot.wteammall.UserModule.PersionalCenter;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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

public class PersonalCenterActivity extends AppCompatActivity {

    LinearLayout LY_Persion_Info;
    LinearLayout LY_Persion_Msg;
    LinearLayout LY_Persion_Order;
    LinearLayout LY_Persion_Coupon;
    LinearLayout LY_Persion_Integal;
    LinearLayout LY_Persion_Task;

    TextView TV_BACK;
    TextView TV_MsgNum;
    TextView TV_TaskNum;
    TextView TV_OrderNum;
    TextView TV_CouponNum;


    MyUserInfoBean userInfoBean;

    int MessgageNUm;
    int TaskNum;
    int OrderNum;
    int CouponNum;

    Intent mIntent;
    Bundle mBundle;
    String UserName;
    private String IMEI;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        IMEI = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        getUserLoginInfo();

        initView();
        setListener();
        getUserInfo();

    }

    public void getUserLoginInfo(){

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        UserName = (String) bundle.get("UserName");
        Log.d("UserName",UserName);
    }

    public void initView() {
        LY_Persion_Coupon = (LinearLayout) findViewById(R.id.ly_persion_persioncoupon);
        LY_Persion_Order = (LinearLayout) findViewById(R.id.ly_persion_persionoder);
        LY_Persion_Info = (LinearLayout) findViewById(R.id.ly_persion_persioninfo);
        LY_Persion_Msg = (LinearLayout) findViewById(R.id.ly_persion_persionmsg);
        LY_Persion_Integal = (LinearLayout) findViewById(R.id.ly_persion_persionintegral);
        LY_Persion_Task = (LinearLayout) findViewById(R.id.ly_persion_persiontask);

        TV_MsgNum = (TextView) findViewById(R.id.tv_persion_msg_num);
        TV_TaskNum = (TextView) findViewById(R.id.tv_persion_task_num);
        TV_OrderNum = (TextView) findViewById(R.id.tv_persion_order_num);
        TV_CouponNum = (TextView) findViewById(R.id.tv_persion_coupon_num);

        TV_BACK = (TextView) findViewById(R.id.tv_per_center_title_back);
    }

    public void setListener() {

        LY_Persion_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PersonalCenterActivity.this, "进入订单页面", Toast.LENGTH_LONG).show();
                mIntent = new Intent(PersonalCenterActivity.this, PersionOrderActivity.class);
                mBundle = new Bundle();
                mBundle.putString("UserName",UserName);
                Log.d("UserName",UserName);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
        LY_Persion_Msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mIntent = new Intent(PersonalCenterActivity.this, PersionMessageActivity.class);
                mBundle = new Bundle();
                mBundle.putString("UserName",UserName);
                Log.d("UserName",UserName);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
        LY_Persion_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(PersonalCenterActivity.this, PersionInformationActivity.class);
                mBundle = new Bundle();
                mBundle.putSerializable("UserInfoBean",userInfoBean);
                Log.d("UserName",UserName);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
                
            }
        });
        LY_Persion_Coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PersonalCenterActivity.this, "进入优惠券页面", Toast.LENGTH_LONG).show();
                mIntent = new Intent(PersonalCenterActivity.this, PersionCouponActivity.class);
                mBundle = new Bundle();
                mBundle.putString("UserName",UserName);
                Log.d("UserName",UserName);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
        LY_Persion_Integal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PersonalCenterActivity.this, "进入积分页面", Toast.LENGTH_LONG).show();
                startActivity(new Intent(PersonalCenterActivity.this, PersionIntegralActivity.class));
            }
        });

        LY_Persion_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mIntent = new Intent(PersonalCenterActivity.this,PersionTaskActivity.class);
                mBundle = new Bundle();
                mBundle.putString("UserName",UserName);
                Log.d("UserName",UserName);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
                startActivity(mIntent);
            }
        });

        TV_BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalCenterActivity.this, MainActivity.class));
            }
        });
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //Log.d("------",msg.obj.toString());
            switch (msg.what) {
                case 0:
                    Toast.makeText(PersonalCenterActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Log.d("msg",msg.obj.toString());
                    parseJson(msg.obj.toString());
                    MessgageNUm = userInfoBean.getUnreadMessage();
                    TaskNum = userInfoBean.getUnderwayTask();
                    OrderNum = userInfoBean.getWaitPay();
                    CouponNum = userInfoBean.getUnuseCoupon();

                    TV_TaskNum.setText(TaskNum+"");
                    TV_MsgNum.setText(MessgageNUm+"");
                    TV_OrderNum.setText(OrderNum+"");
                    TV_CouponNum.setText(CouponNum+"");
                    break;
                case 2:

                    break;
                case 3:
                    break;
            }
        }
    };

    public void getUserInfo() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody requestBody = new FormBody.Builder()
                            .add("username",UserName)
                            .add("token", IMEI)
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
                        handler.sendEmptyMessage(0);

                    }
                } catch (IOException e) {
                    handler.sendEmptyMessage(0);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 解析请求结果的Json
     */
    public void parseJson(String jsonString) {
        Log.d("jsonString",jsonString);
        try {
            userInfoBean = new MyUserInfoBean();
            StringReader stringReader = new StringReader(jsonString);
            JsonReader jsonReader = new JsonReader(stringReader);
            jsonReader.beginObject();
            String info;
            while (jsonReader.hasNext()){
                info = jsonReader.nextName();
                Log.d("info","="+info);
                if(info.contains("user")){
                    jsonReader.beginObject();
                    String key;
                    int i = 1;
                    while (jsonReader.hasNext()){
                        Log.d("i",i+"");
                        key = jsonReader.nextName();
                        Log.d("i",i+"="+key);
                        if (key.contains("unReadMessage")){
                            userInfoBean.setUnreadMessage(jsonReader.nextInt());
                        } else if (key.contains("underwayTask")){
                            userInfoBean.setUnderwayTask(jsonReader.nextInt());
                        }else if (key.contains("email")){
                            Log.d("i",i+"-1");
                            userInfoBean.setEmail(jsonReader.nextString());
                            Log.d("i",i+userInfoBean.getEmail());
                        }else  if (key.contains("headimgurl")){
                            Log.d("i",i+"-2");
                            userInfoBean.setHeaderImgUrl(jsonReader.nextString());
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
                }else if (info.contains("underwayTask")){
                    userInfoBean.setUnderwayTask(jsonReader.nextInt());

                }else if (info.contains("unReadMessage")){
                    userInfoBean.setUnreadMessage(jsonReader.nextInt());
                }else if (info.contains("unUseCoupon")){
                    userInfoBean.setUnuseCoupon(jsonReader.nextInt());
                }else if (info.contains("error")){
                    jsonReader.beginObject();
                    String msgName = jsonReader.nextName();
                    String msg = jsonReader.nextString();

                    Log.d("msg",msg);
                }else if (info.contains("waitFinish")){
                    userInfoBean.setWaitFinish(jsonReader.nextInt());
                }else if(info.contains("waitPay")){
                    userInfoBean.setWaitPay(jsonReader.nextInt());
                }else if (info.contains("waitShip")){
                    userInfoBean.setWaitFinish(jsonReader.nextInt());
                }else if (info.contains("unUseCoupon")){
                    userInfoBean.setUnuseCoupon(jsonReader.nextInt());
                }
            }
            jsonReader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
