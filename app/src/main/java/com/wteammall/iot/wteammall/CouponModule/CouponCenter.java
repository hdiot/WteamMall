package com.wteammall.iot.wteammall.CouponModule;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wteammall.iot.wteammall.ConnectXModule.Connect;
import com.wteammall.iot.wteammall.ConnectXModule.Url;
import com.wteammall.iot.wteammall.MainActivity;
import com.wteammall.iot.wteammall.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static android.R.drawable.ic_popup_sync;

public class CouponCenter extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<CouponData> newsList;
    private CouponListAdapter adapter;
    private String json;
    android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    json = (String) msg.obj;

                    // TODO: 2016/11/26 情况
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_center);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);


        recyclerView = (RecyclerView) findViewById(R.id.coupom_list);


        // TODO: 2016/11/26 testforgetlist


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connect a = new Connect();
                    String b;

                    b = a.generalget(Url.getAllCoupon);
                    Message c = new Message();
                    c.what = 1;
                    c.obj = b;
                    handler.sendMessage(c);
                } catch (IOException el) {
                    ;
                }
            }
        }).start();
        initCouponData();


        adapter = new CouponListAdapter(newsList, getBaseContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void initCouponData() {
        newsList = new ArrayList<>();
        //添加优惠券
/*
        Gson gson = new Gson();
        Type listType = new

                TypeToken<LinkedList<sdada>>(){}.getType();
        newsList=gson.fromJson(json, listType);
*/

        Date d1 = new Date(2016 - 1900, 11 - 1, 9);
        Date d2 = new Date(2017 - 1900, 5 - 1, 9);
        newsList.add(new CouponData(1, 1, 20, "全品类", 100, d2, d1, new BigDecimal(Double.toString(100.0)), new BigDecimal(Double.toString(100.0))));
        newsList.add(new CouponData(1, 1, 10, "全品类", 100, d2, d1, new BigDecimal(Double.toString(100.0)), new BigDecimal(Double.toString(7.0))));
        newsList.add(new CouponData(1, 1, 20, "全品类", 99, d2, d1, new BigDecimal(Double.toString(100.0)), new BigDecimal(Double.toString(7.0))));
        newsList.add(new CouponData(0, 1, 20, "全品类", 100, d2, d1, new BigDecimal(Double.toString(100.0)), new BigDecimal(Double.toString(7.0))));
        newsList.add(new CouponData(1, 1, 20, "全品类", 100, d2, d1, new BigDecimal(Double.toString(200.0)), new BigDecimal(Double.toString(7.0))));
        newsList.add(new CouponData(1, 1, 20, "全品类", 100, d2, d1, new BigDecimal(Double.toString(100.0)), new BigDecimal(Double.toString(7.0))));


    }

}
