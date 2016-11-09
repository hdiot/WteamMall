package com.wteammall.iot.wteammall.Utils;

import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by IOT on 2016/11/7.
 */
public class URLUtils {

    private static final int SERVICE_FAILED = 0;
    private static final int SERVICE_SUCCEED = 1;

    private Map<String, String> RequestParams;
    private String mUri;
    private URL mUrl;
    private String mResultData;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    /**
     * 当只用get方式请求网络时调用该构造函数
     */
    public URLUtils(String uri) {
        mUri = uri;
    }

    /**
     * 当使用post请求网络时调用该构造函数
     */
    public URLUtils(String uri, Map<String, String> rqparam) {
        RequestParams = rqparam;
        mUri = uri;
    }

    public void post() {

        new Thread(new Runnable() {
            Message message = new Message();
            @Override
            public void run() {
                try {
                    mUrl = new URL(mUri);
                    HttpURLConnection urlConn = (HttpURLConnection) mUrl.openConnection();
                    urlConn.setDoInput(true);//设置输入采用字节流
                    urlConn.setDoOutput(true);//设置输出采用字节流
                    urlConn.setRequestMethod("POST");
                    urlConn.setConnectTimeout(3000);
                    urlConn.connect();//连接服务器发送消息
                    OutputStream outputStream = urlConn.getOutputStream();
                    outputStream.write(getRequestParam(RequestParams).toString().getBytes());

                    //接收数据
                    int response = urlConn.getResponseCode();//获得服务器的响应码
                    if (response == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = urlConn.getInputStream();

                        message.what = SERVICE_SUCCEED;
                        message.obj = dealResponStream(inputStream);

                    }
                } catch (MalformedURLException e) {
                    message.what = SERVICE_FAILED;
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    message.what = SERVICE_FAILED;
                    e.printStackTrace();
                } catch (IOException e) {
                    message.what = SERVICE_FAILED;
                    e.printStackTrace();
                }finally {
                    handler.handleMessage(message);
                }
            }
        }).start();
    }

    public void get() {
        Thread thread = new Thread(new Runnable() {
            Message message = new Message();
            @Override
            public void run() {
                try {
                    mUrl = new URL(mUri);
                    HttpURLConnection urlConnection = (HttpURLConnection) mUrl.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.getResponseCode();
                    urlConnection.getInputStream();

                    int response = urlConnection.getResponseCode();//获得服务器的响应码
                    if (response == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = urlConnection.getInputStream();

                        message.what = SERVICE_SUCCEED;
                        message.obj = dealResponStream(inputStream);
                    }

                } catch (IOException e) {
                    message.what = SERVICE_FAILED;
                    e.printStackTrace();
                }finally {
                    handler.handleMessage(message);
                }
            }
        });
    }

    /**
     * 封装请求参数
     *
     * @param params 请求参数
     * @return StringBuffer 封装后的请求参数
     */
    public StringBuffer getRequestParam(Map<String, String> params) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                stringBuffer.append("\"")
                        .append(entry.getKey())
                        .append("\":\"")
                        .append(URLEncoder.encode(entry.getValue(), "utf-8"))
                        .append("\",");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个","
        stringBuffer.append("}");
        return stringBuffer;
    }

    /**
     * @param inputStream 相应信息流
     * @return 相应信息转字符串
     */
    public String dealResponStream(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }
}
