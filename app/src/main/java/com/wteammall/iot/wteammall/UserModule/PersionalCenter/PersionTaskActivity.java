package com.wteammall.iot.wteammall.UserModule.PersionalCenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.wteammall.iot.wteammall.Adapter.TaskAdapter;
import com.wteammall.iot.wteammall.Bean.TaskBean.Task;
import com.wteammall.iot.wteammall.Bean.TaskBean.TaskDetail;
import com.wteammall.iot.wteammall.Bean.TaskBean.Tasks;
import com.wteammall.iot.wteammall.R;
import com.wteammall.iot.wteammall.Utils.ValueUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PersionTaskActivity extends AppCompatActivity {

    ListView LV_Task;
    Task mTask;
    Tasks mTasks;
    TaskDetail mTaskDetail;
    ArrayList<TaskDetail> mTaskDetailList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_task);

        initView();

        getTaskInfo();
    }

    public void initView() {
        LV_Task = (ListView) findViewById(R.id.lv_per_task);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(PersionTaskActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    paresJson(msg.obj.toString());
                    if (mTasks != null) {
                        TaskAdapter taskAdapter = new TaskAdapter(PersionTaskActivity.this, mTasks);
                        LV_Task.setAdapter(taskAdapter);
                    } else {

                    }
                    break;
                case 2:
                    Toast.makeText(PersionTaskActivity.this, "你还没有领取任务", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void getTaskInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message;
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody
                            .Builder()
                            .add("username", "1234")
                            .add("token", "1234567890")
                            .build();
                    Request request = new Request
                            .Builder()
                            .url(ValueUtils.URL_GET_USER_TASKLIST)
                            .post(formBody)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        message = new Message();
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

    public void paresJson(String json) {
        Log.d("Task", json);
        try {
            String key_1;
            String key_2;
            String key_3;
            StringReader stringReader = new StringReader(json);
            JsonReader jsonReader = new JsonReader(stringReader);
            jsonReader.beginObject();
            if (jsonReader.hasNext()) {
                key_1 = jsonReader.nextName();
                if (key_1.contains("tasks")) {
                    if (jsonReader.hasNext()) {
                        mTasks = new Tasks();
                        mTaskDetailList = new ArrayList<TaskDetail>();
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            jsonReader.beginObject();
                            mTaskDetail = new TaskDetail();
                            while (jsonReader.hasNext()) {
                                key_2 = jsonReader.nextName();
                                if (key_2.contains("accepttime")) {
                                    mTaskDetail.setAcceptTime(jsonReader.nextString());
                                } else if (key_2.contains("id")) {
                                    mTaskDetail.setID(jsonReader.nextString());
                                } else if (key_2.contains("status")) {
                                    mTaskDetail.setStatus(jsonReader.nextInt());
                                } else if (key_2.contentEquals("task")) {
                                    jsonReader.beginObject();
                                    mTask = new Task();
                                    while (jsonReader.hasNext()) {
                                        key_3 = jsonReader.nextName();
                                        if (key_3.contains("name")) {
                                            mTask.setName(jsonReader.nextString());
                                        } else if (key_3.contains("points")) {
                                            mTask.setPoints(jsonReader.nextDouble());
                                        } else if (key_3.contains("type")) {
                                            mTask.setType(jsonReader.nextInt());
                                        }
                                    }
                                    jsonReader.endObject();
                                    mTaskDetail.setmTask(mTask);
                                }
                            }
                            jsonReader.endObject();
                            mTaskDetailList.add(mTaskDetail);
                        }
                        jsonReader.endArray();
                        mTasks.setTaskList(mTaskDetailList);
                    } else {
                        handler.sendEmptyMessage(2);
                    }
                } else if (key_1.contains("error")) {
                    jsonReader.beginObject();
                    jsonReader.nextName();
                    Toast.makeText(PersionTaskActivity.this, jsonReader.nextString(), Toast.LENGTH_SHORT).show();
                    jsonReader.endObject();
                }
            } else {
                Toast.makeText(PersionTaskActivity.this, "没有任务", Toast.LENGTH_SHORT).show();
            }
            jsonReader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
