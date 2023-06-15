package com.example.volley;

import androidx.appcompat.app.AppCompatActivity;
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

        if (v.getId() == R.id.btn_request_get){

            Log.d("onClick", "btn_request_get被点击了");
            Toast.makeText(MainActivity.this,"requestGet",Toast.LENGTH_SHORT).show();
            volleyGet();

        } else if (v.getId() == R.id.btn_request_post) {

            Log.d("onClick", "btn_request_post被点击了");
            Toast.makeText(MainActivity.this,"requestPost",Toast.LENGTH_SHORT).show();
            volleyPost();

        } else if (v.getId() == R.id.btn_request_jsonData) {

            Log.d("onClick", "btn_request_jsonData被点击了");
            Toast.makeText(MainActivity.this,"requestJsonData",Toast.LENGTH_SHORT).show();
            volleyGetJson();

        } else if (v.getId() == R.id.btn_imageRequest) {

            Log.d("onClick", "btn_imageRequest被点击了");
            Toast.makeText(MainActivity.this,"imageRequest",Toast.LENGTH_SHORT).show();
            volleyGet();

        } else if (v.getId() == R.id.btn_imageLoader) {

            Log.d("onClick", "btn_imageLoader被点击了");
            Toast.makeText(MainActivity.this,"imageLoader",Toast.LENGTH_SHORT).show();
            volleyGet();

        } else if (v.getId() == R.id.btn_networkImageView) {

            Log.d("onClick", "btn_networkImageView被点击了");
            Toast.makeText(MainActivity.this,"networkImageView",Toast.LENGTH_SHORT).show();
            volleyGet();

        } else {

            Log.d("switch_R_Id", "无按钮ID匹配");

        }
    }

    private void volleyGet() {
        String url = "https://www.httpbin.org/get?a=1&b=2";

        // 创建一个get请求
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            // 正确接收数据，回调
            @Override
            public void onResponse(String response) {
                tv_showResult.setText(response);
            }
        }, new Response.ErrorListener() {
            // 错误接收数据，回调
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_showResult.setText("加载错误");
            }
        });

        // 将请求添加到请求序列中
        requestQueue.add(stringRequest);
    }

    private void volleyPost() {
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
}