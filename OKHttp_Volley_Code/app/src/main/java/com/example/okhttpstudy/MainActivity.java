package com.example.okhttpstudy;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.okhttpstudy.net.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    private Button btn_get;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化所有控件
        initView();

        // 绑定所有的事件
        initEvent();
    }

    private void initEvent() {
        btn_get.setOnClickListener(v -> new Thread(){
            @Override
            public void run() {
                OkHttpUtils.getInstance().doGet("https://www.httpbin.org/get?a=1&b=2", new CallBack() {
                    @Override
                    public void onSuccess(String result) {
                        tv_content.setText(result);
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(MainActivity.this, "网络操作失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }.start());
    }

    private void initView() {
        btn_get = findViewById(R.id.btn_get);
        tv_content = findViewById(R.id.tv_content);
    }
}