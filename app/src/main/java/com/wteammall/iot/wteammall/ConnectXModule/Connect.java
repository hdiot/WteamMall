package com.wteammall.iot.wteammall.ConnectXModule;

import android.os.Message;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.id.message;

/**
 * Created by liupe on 2016/11/26.
 */

public class Connect {
    OkHttpClient client = new OkHttpClient();


    public String generalget(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {


            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
}
