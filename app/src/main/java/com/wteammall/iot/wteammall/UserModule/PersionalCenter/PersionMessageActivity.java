package com.wteammall.iot.wteammall.UserModule.PersionalCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.wteammall.iot.wteammall.Adapter.MessageAdapter;
import com.wteammall.iot.wteammall.Bean.MyMessageBean;
import com.wteammall.iot.wteammall.MainActivity;
import com.wteammall.iot.wteammall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 找回密码
 */
public class PersionMessageActivity extends AppCompatActivity {

    TextView TV_Back;
    TextView TV_ToMain;
    ListView LV_Messsage;
    MyMessageBean messageBean;
    List<MyMessageBean> myMessageBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_message);

        initView();

        setListener();

        getMyMessageInfo();

    }

    public void initView(){
        TV_Back = (TextView) findViewById(R.id.tv_per_msg_back);
        TV_ToMain = (TextView) findViewById(R.id.tv_per_msg_mainpage);
        LV_Messsage = (ListView) findViewById(R.id.lv_persion_message);
}

    public void setListener(){
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

    public void getMyMessageInfo(){
        myMessageBeanList = new ArrayList<MyMessageBean>();
        for(int i = 0 ; i<10 ; i++){
            messageBean = new MyMessageBean();
            messageBean.setSender("哈哈"+i);
            messageBean.setContext("你在逗我"+i);
            messageBean.setTime("2011-11-"+i+1);
            myMessageBeanList.add(messageBean);
        }
        MessageAdapter messageAdapter = new MessageAdapter(PersionMessageActivity.this,myMessageBeanList);
        LV_Messsage.setAdapter(messageAdapter);
    }

}
