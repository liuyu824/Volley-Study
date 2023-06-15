package com.example.volley;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_request_get;
    private Button btn_request_post;
    private Button btn_request_jsonData;
    private Button btn_imageRequest;
    private Button btn_imageLoader;
    private Button btn_networkImageView;
    private NetworkImageView iv_networkImageView;
    private TextView tv_showResult;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initListener();
    }

    private void initListener() {
        btn_request_get.setOnClickListener(this);
        btn_request_post.setOnClickListener(this);
        btn_request_jsonData.setOnClickListener(this);
        btn_imageRequest.setOnClickListener(this);
        btn_imageLoader.setOnClickListener(this);
        btn_networkImageView.setOnClickListener(this);
    }

    private void initData() {
    }

    private void initView() {
        btn_request_get = findViewById(R.id.btn_request_get);
        btn_request_post = findViewById(R.id.btn_request_post);
        btn_request_jsonData = findViewById(R.id.btn_request_jsonData);
        btn_imageRequest = findViewById(R.id.btn_imageRequest);
        btn_imageLoader = findViewById(R.id.btn_imageLoader);
        btn_networkImageView = findViewById(R.id.btn_networkImageView);
        iv_networkImageView = findViewById(R.id.iv_networkImageView);
        tv_showResult = findViewById(R.id.tv_showResult);
    }

    @Override
    public void onClick(View v) {

        // 统一创建一个请求序列
        requestQueue = Volley.newRequestQueue(MainActivity.this);

        if (v.getId() == R.id.btn_request_get){volleyGet();}
        else if (v.getId() == R.id.btn_request_post) {volleyPost();}
        else if (v.getId() == R.id.btn_request_jsonData) {volleyGetJson();}
        else if (v.getId() == R.id.btn_imageRequest) {volleyImageRequest();}
        else if (v.getId() == R.id.btn_imageLoader) {volleyImageLoader();}
        else if (v.getId() == R.id.btn_networkImageView) {volleyNetworkImageView();}
        else {Log.d("switch_R_Id", "无按钮ID匹配");}
    }

    private void volleyGet() {
        iv_networkImageView.setVisibility(View.GONE);
        String url = "https://www.httpbin.org/get?a=1&b=2";

        // 创建一个get请求
        // 正确接收数据，回调
        // 错误接收数据，回调
        StringRequest stringRequest = new StringRequest(
                url,
                response -> tv_showResult.setText(response),
                error -> tv_showResult.setText("加载错误"));

        // 将请求添加到请求序列中
        requestQueue.add(stringRequest);
    }

    private void volleyPost() {
        iv_networkImageView.setVisibility(View.GONE);
        String url = "https://httpbin.org/post";

        // 创建一个post请求
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> tv_showResult.setText(response),  // 正确接收数据
                error -> tv_showResult.setText("post请求失败"+error));  // 错误接收数据

        // 将请求添加到请求序列中
        requestQueue.add(stringRequest);
    }

    private void volleyGetJson(){
        iv_networkImageView.setVisibility(View.GONE);

        // 不需要创建请求队列，已经统一创建好
        // 创建一个请求
        String url = "https://httpbin.org/json";
        JsonObjectRequest request = new JsonObjectRequest(
                url,
                response -> tv_showResult.setText(response.toString()), //正确接收数据
                error -> tv_showResult.setText("请求失败"+error));  //错误接收数据

        // 将创建的请求添加到请求队列中
        requestQueue.add(request);
    }

    private void volleyImageRequest(){
        iv_networkImageView.setVisibility(View.GONE);
        String url = "https://httpbin.org/image";

        // 创建一个请求队列，已提前统一创建
        // 创建一个请求
        ImageRequest imageRequest = new ImageRequest(
                url, response -> {
                    iv_networkImageView.setVisibility(View.VISIBLE);
                    iv_networkImageView.setImageBitmap(response);
                    tv_showResult.setText("正确接收图片");
                }, 100, 100, Bitmap.Config.RGB_565,
                error -> tv_showResult.setText("无法正确接收图片"+error)
        );
        // 将创建的请求添加到请求队列中
        requestQueue.add(imageRequest);
    }

    private void volleyImageLoader(){

        iv_networkImageView.setVisibility(View.GONE);

        // 创建一个请求队列，前面已经统一创建过了
        // 创建一个请求
        ImageLoader imageLoader = new ImageLoader(
                requestQueue, new ImageLoader.ImageCache() {
            @Nullable
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });

        // 加载图片
        String url = "https://httpbin.org/image";
        iv_networkImageView.setVisibility(View.VISIBLE);
        ImageLoader.ImageListener imageListener = imageLoader.getImageListener(iv_networkImageView,R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground);
        imageLoader.get(url, imageListener);

    }

    private void volleyNetworkImageView(){

        // 设置【已经隐藏】的 imageView 显示
//        iv_networkImageView.setVisibility(View.VISIBLE);

        // 设置网址url
        String url = "";

        // 创建一个请求队列
        // 创建一个ImageLoader
//        ImageLoader imageLoader = new ImageLoader(requestQueue,new BitmapCache());

        // 默认图片设置
//        iv_networkImageView.setDefaultImageResId(R.drawable.ic_launcher_background);
//        iv_networkImageView.setErrorImageResId(R.drawable.ic_launcher_foreground);

        // 加载图片
//        iv_networkImageView.setImageUrl(url,imageLoader);
    }
}