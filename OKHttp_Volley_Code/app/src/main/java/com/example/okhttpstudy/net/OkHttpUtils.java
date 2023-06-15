package com.example.okhttpstudy.net;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import com.example.okhttpstudy.CallBack;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {

    private static OkHttpUtils instance = new OkHttpUtils();
    private static OkHttpClient okHttpClient;
    private Handler handler = new Handler(Looper.getMainLooper());

    private OkHttpUtils(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    public static OkHttpUtils getInstance() {
        return instance;
    }

    public void doGet(String url, CallBack callBack){
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);

        // execute方法会阻塞在这里，必须等到服务器相应
        // 得到response，才会执行下面的代码
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.post(() -> callBack.onError(e));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                String string  = null;
                try {
                    string = response.body().string();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                String finalString = string;
                handler.post(() -> callBack.onSuccess(finalString));
            }
        });
    }
}
