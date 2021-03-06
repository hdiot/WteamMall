package com.wteammall.iot.wteammall.Utils;

import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.util.Log;

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

    private Map<String, String> RequestParams;
    private String mUri;
    private URL mUrl;
    private String mResultData;

    /**
     * 当使用post请求网络时调用该构造函数
     */
    public URLUtils(String uri, Map<String, String> rqparam) {
        RequestParams = rqparam;
        mUri = uri;
    }

    public String post() throws InterruptedException {
        Thread thread =  new Thread(new Runnable() {
            Message message = new Message();

            @Override
            public void run() {
                try {
                    mUrl = new URL(mUri);
                    HttpURLConnection urlConn = (HttpURLConnection) mUrl.openConnection();
                    urlConn.setDoInput(true);//设置输入采用字节流
                    urlConn.setDoOutput(true);//设置输出采用字节流
                    urlConn.setRequestMethod("POST");
                    urlConn.setConnectTimeout(5000);
                    urlConn.setReadTimeout(5000);
                    urlConn.connect();//连接服务器发送消息
                    OutputStream outputStream = urlConn.getOutputStream();
                    outputStream.write(getRequestParam(RequestParams).toString().getBytes());

                    Log.d("param",getRequestParam(RequestParams).toString());

                    //接收数据
                    int response = urlConn.getResponseCode();//获得服务器的响应码
                    if (response == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = urlConn.getInputStream();
                        mResultData = dealResponStream(inputStream);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();

        return mResultData;
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
