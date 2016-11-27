package com.wteammall.iot.wteammall.UserModule.PersionalCenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wteammall.iot.wteammall.Adapter.CouponAdapter;
import com.wteammall.iot.wteammall.Bean.CouponBean.Coupon;
import com.wteammall.iot.wteammall.Bean.CouponBean.CouponDetail;
import com.wteammall.iot.wteammall.Bean.CouponBean.Coupons;
import com.wteammall.iot.wteammall.Bean.UserBean.MyUserInfoBean;
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

public class PersionCouponActivity extends AppCompatActivity {

    ListView CouponListView;
    TextView TV_Back;
    TextView TV_GetCoupon;

    Coupons myCoupons = new Coupons();
    CouponDetail mCouponDetail;
    Coupon mCoupon;
    ArrayList<String> mRange;
    private MyUserInfoBean mUserInfoBean;
    private String IMEI;
    private String UserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_coupon);

        IMEI = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        getLsetActivityInfo();
        initView();
        setListener();
        getMyCouponInfo();
    }

    public void getLsetActivityInfo(){
        Intent intent = getIntent();
        if (intent != null){
            Log.d("UserName","intent");
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                Log.d("UserName","bundle");
                if (bundle.get("UserName") != null){
                    Log.d("UserName",bundle.getString("UserName"));
                    UserName = (String) bundle.get("UserName");
                }
            }
        }
    }

    public void initView() {
        Log.d("haha", "GGG---view");
        CouponListView = (ListView) findViewById(R.id.lv_per_coupon);
        TV_Back = (TextView) findViewById(R.id.tv_per_coupon_title_back);
        TV_GetCoupon = (TextView) findViewById(R.id.tv_per_coupon_title_togetcoupon);
    }

    public void setListener() {
        TV_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersionCouponActivity.this, PersonalCenterActivity.class));
            }
        });

        TV_GetCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(PersionCouponActivity.this,PersonalCenterActivity.class));

            }
        });

        CouponListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("点击",adapterView.getItemAtPosition(i).toString());

                AlertDialog.Builder builder = new AlertDialog.Builder(PersionCouponActivity.this);
                builder.setMessage("确认删除吗");
                builder.setTitle("提示");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        arg0.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub


                        arg0.dismiss();
                    }
                });
                builder.create().show();
                return false;
            }
        });

        CouponListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(PersionCouponActivity.this,"==========",Toast.LENGTH_SHORT).show();



                }
        });
    }

    public void initCouponInfo(){
        CouponAdapter couponAdapter = new CouponAdapter(PersionCouponActivity.this,myCoupons);
        CouponListView.setAdapter(couponAdapter);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(PersionCouponActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    paresJson(msg.obj.toString());
                    initCouponInfo();
                    break;
                case 2:
                    break;

            }
        }
    };

    public void getMyCouponInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message;
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody
                            .Builder()
                            .add("username", UserName)
                            .add("token", IMEI)
                            .build();
                    Request request = new Request
                            .Builder()
                            .url(ValueUtils.URL_GET_USER_COUPON)
                            .post(formBody)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        message = new Message();
                        message.what = 1;
                        String s =  response.body().string();
                        Log.d("s",s);
                        message.obj = s;
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

    public void paresJson(String json) {
        Log.d("haha",json);
        try {
            String key_1;
            String key_2;
            String key_3;
            StringReader stringReader = new StringReader(json);
            JsonReader jsonReader = new JsonReader(stringReader);
            jsonReader.beginObject();
            if (jsonReader.hasNext()) {
                key_1 = jsonReader.nextName();
                if (key_1.contains("coupons")) {
                    jsonReader.beginArray();
                     myCoupons = new Coupons();
                    List<CouponDetail> couponslist = new ArrayList<CouponDetail>();
                    while (jsonReader.hasNext()) {
                        mCouponDetail = new CouponDetail();
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()){
                            key_2 = jsonReader.nextName();
                            if (key_2.contains("coupon")) {
                                mCoupon = new Coupon();
                                jsonReader.beginObject();
                                while (jsonReader.hasNext()) {
                                    key_3 = jsonReader.nextName();
                                    if (key_3.contains("beginTime")) {
                                        mCoupon.setBegintime(jsonReader.nextString());
                                    } else if (key_3.contains("endTime")) {
                                        mCoupon.setEndTime(jsonReader.nextString());
                                    } else if (key_3.contains("discount")){
                                        jsonReader.nextDouble();
                                    }else if (key_3.contains("function")) {
                                        mCoupon.setFuntion(jsonReader.nextInt());
                                    } else if (key_3.contains("name")) {
                                        mCoupon.setName(jsonReader.nextString());
                                    } else if(key_3.contains("maxGetNums")){
                                        mCoupon.setMaxGetNum(jsonReader.nextInt());
                                    } else if(key_3.contains("nums")){
                                        mCoupon.setNums(jsonReader.nextInt());
                                    } else if(key_3.contains("remainNums")){
                                        mCoupon.setRemainNums(jsonReader.nextInt());
                                    } else if(key_3.contains("remission")){
                                        mCoupon.setRemssion(jsonReader.nextDouble());
                                    } else if(key_3.contains("satisfy")){
                                        mCoupon.setSatify(jsonReader.nextDouble());
                                    } else if(key_3.contains("range")){
                                        jsonReader.beginArray();
                                        mRange = new ArrayList<String>();
                                        while (jsonReader.hasNext()){
                                            jsonReader.beginObject();
                                            jsonReader.nextName();
                                            mRange.add(jsonReader.nextString());
                                            jsonReader.endObject();
                                        }
                                        jsonReader.endArray();
                                        mCoupon.setRang(mRange);
                                    }
                                }
                                jsonReader.endObject();
                                mCouponDetail.setmCoupon(mCoupon);
                            } else if (key_2.contains("id")) {
                                mCouponDetail.setID(jsonReader.nextString());
                            } else if (key_2.contains("obtainTime")) {
                                mCouponDetail.setObtainTime(jsonReader.nextString());
                            } else if (key_2.contains("status")) {
                                mCouponDetail.setStatus(jsonReader.nextInt());
                            }
                        }
                        jsonReader.endObject();
                        couponslist.add(mCouponDetail);
                    }
                    myCoupons.setCouponlist(couponslist);
                    jsonReader.endArray();
                } else {
                    jsonReader.beginObject();
                    jsonReader.nextName();
                    jsonReader.nextString();
                }
            }
            jsonReader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
