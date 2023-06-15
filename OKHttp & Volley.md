

## OKHttp

### Android网络请求注意事项

- #### 使用HTTPS协议的URL

如果是CA签发的，一般情况下，直接访问即可，

如果是自签的，访问前需要设置SSL相关配置；

- #### 使用HTTP协议的URL

从Android P开始，默认不再允许直接访问HTTP请求，

通过设置Network Security Configuration支持。



### 主流网络框架OKHttp

```java
https://square.github.io/okhttp/
```

由Square公司贡献的一个处理网络请求的开源项目，是目前Android使用最广泛的网络框架。从Android4.0开始HttpURLConnection的底层实现采用的是OkHttp。



#### 常见网络请求

1. GET
2. 普通POST form请求    Content-Type: application/x-www-form-urlencoded
3. 支持文件上传的POST form请求   Content-Type: multipart/form-data; boundary=
4. POST json字符串
5. 请求中携带自定义header信息



### OKHttp 使用

- 将okHttp的jar包放入lib文件夹下
- 在app的build.gradle中导入依赖

```java
// 添加依赖
// 会自动下载 okhttp库 和 okio库
implementation("com.squareup.okhttp3:okhttp:4.11.0")
```



- 添加权限
- 在 AndroidManifest.xml 文件中添加：

```java
<!--  引入标签，可以进行网络访问  -->
<uses-permission android:name="android.permission.INTERNET"/>
```



### get 同/异步请求

<a style="color:red;font-weight:bold">get请求同步执行的基本步骤：</a>

1. 创建<mark>OkHttpClient的实例</mark>

```java
private static OkHttpClient okHttpClient = new OkHttpClient();
```



2. 创建<mark>Request对象</mark>，设置url地址

3. 创建call对象，调用<mark>execute()</mark>方法（同步）或者<mark>enquene</mark>方法（异步）

​	  (a) 发送请求，获取服务器返回的数据

```java
public String doGet(String url) throws Exception{
	Request request = new Request.Builder().url(url).build();
	Call call = okHttpClient.newCall(request);

  // execute方法会阻塞在这里，必须等到服务器相应
  // 得到response，才会执行下面的代码
  Response response = call.execute();
  return response.body().string();
}
```



4. 得到服务器返回的数据的具体内容

```java
private void initEvent() {
	btn_get.setOnClickListener(v -> new Thread(){
    @Override
    public void run() {
      try {
        String context = OkHttpUtils.getInstance().doGet("https://www.httpbin.org/get?a=1&b=2");
        handler.post(() -> tv_content.setText(context));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }.start());
}
```



<a style="color:red;font-weight:bold">同步与异步的区别</a>

同步call.execute()，异步call.enqueue()

<mark>同步</mark>

同步请求是指在请求发起到拿到响应结果之前，程序一直会处于阻塞状态，无法接收新的请求，直到拿到响应；

<mark>异步</mark>

异步请求是指请求发起后到拿到响应结果之前，程序是非阻塞状态，可以继续接收新的请求，响应回来时会调用一个回调处理响应数据。



<a style="color:red;font-weight:bold">get请求异步执行的基本步骤：</a>

定义CallBack接口

```java
package com.example.okhttpstudy;
public interface CallBack {
    void onSuccess(String result);
    void onError(Exception e);
}
```



### okHttp拦截器

1. 了解okHttp拦截器
2. 引入官方logging-interceptor，打印请求的详细信息
3. 自定义拦截器，对每个请求添加作者，时间戳等信息



### 项目中okHttp的简单封装

1. 一般情况okHttp的对象全局只需要有一个
2. 注意将okHttp的回调转发到UI线程
3. 封装过程可以选择屏蔽okHttp实现细节



## Volley

### 简介

Volley是Google I/O 发布的网络框架，基于android平台，网络通信快，简单健全。

- 优点
  - 网络请求排序（scheduling）
  - 网络请求优先级处理
  - 缓存
  - 多级别取消请求
  - 和Activity和生命周期的联动（Activity结束时同时取消所有网络请求）
  - 适合数据量不大，通信频繁的网络操作
  - 可以自定义扩展
- 缺点
  - 对于大数据量操作，例如下载文件，Volley表现不好



### 下载地址

- 官网地址：https://android.googlesource.com/platform/frameworks/volley

### API

1. 请求String类型数据：StringRequest
2. 请求JSON数据：JsonRequest
   - JsonObjectRequest Json对象请求
   - JsonArrayRequest   Json数组请求
3. 请求图片数据：ImageRequest

### 使用步骤

1. 导入jar包

   ```java
   // 或者implementation导入依赖
   implementation 'com.android.volley:volley:1.2.1'
   ```

2. 添加联网权限

```java
<uses-permission android:name="android.permission.INTERNET"/>
```

### 例子

网络请求

<mark> get请求 </mark>

- 创建一个请求队列

```java
Volley.newRequestQueue requestQueue = Volley.newRequestQueue(VolleyActivity.this);
```

- 创建一个请求

```java
StringRequest stringRequest = new StringRequest(

  String url = "https://www.httpbin.org/get?a=1&b=2";
  
  // 包含了3个参数
  // url,能够返回response参数的get请求网站
  // 提前在方法最上面，以String类型形式定义好
  url,
  Response.Listener(),      // 成功返回response进行操作
  Response.ErrorListener(),//未成功返回response进行操作
  
);
```

- 将创建的请求添加到请求队列中

```java
requestQueue.add(stringRequest);
```



<mark> post请求 </mark>

- 创建一个请求队列

```java
Volley.newRequestQueue requestQueue = Volley.newRequestQueue(VolleyActivity.this);
```

- 创建一个请求

**参数要求：**

Request.Method.POST , url , Listener , ErrorLisenter

```java
StringRequest stringRequest = new StringRequest(

  String url = "https://www.httpbin.org/get?a=1&b=2";
  
  // 包含了4个参数
  // 声明此方法用于post请求response返回参数
  Request.Method.POST,
  
  // url,能够返回response参数的get请求网站
  // 提前在方法最上面，以String类型形式定义好
  url,
  Response.Listener(),      // 成功返回response进行操作
  Response.ErrorListener(),//未成功返回response进行操作
  
);
```



- 将创建的请求添加到请求队列中

```java
requestQueue.add(stringRequest);
```



<mark> Json数据请求 </mark>

- 创建一个请求队列

```java
Volley.newRequestQueue requestQueue = Volley.newRequestQueue(VolleyActivity.this);
```

- 创建一个请求

```java
String url = "https://httpbin.org/json";
	JsonObjectRequest request = new JsonObjectRequest(
    url,
    response -> tv_showResult.setText(response.toString()), //正确接收数据
    error -> tv_showResult.setText("请求失败"+error));  //错误接收数据
```

```java
https://httpbin.org/json
```

- 将请求添加到队列中

```java
// 将创建的请求添加到请求队列中
requestQueue.add(request);
```



<mark> ImageRequest 加载图片请求 </mark>

- 创建一个请求队列

```java
Volley.newRequestQueue requestQueue = Volley.newRequestQueue(VolleyActivity.this);
```

- 创建一个请求

```java
ImageRequest imageRequest = new ImageRequest(
  url, 
  response -> {
   iv_networkImageView.setVisibility(View.VISIBLE);
   iv_networkImageView.setImageBitmap(response);
   tv_showResult.setText("正确接收图片");}, 
  100, 
  100, 
  Bitmap.Config.RGB_565,
  error -> tv_showResult.setText("无法正确接收图片"+error)
);
```

```java
https://httpbin.org/json
```

- 将请求添加到队列中

```java
// 将创建的请求添加到请求队列中
requestQueue.add(request);
```



<mark> ImageLoader 加载图片请求（不带缓存） </mark>

- 创建请求队列
- 创建一个请求

```java
ImageLoader imageLoader = new ImageLoader(){
  requestQueue, new ImageLoader.ImageCache(){
    @Nullable
    @Override
    public Bitmap getBitmap(String url) {
      return null;
    }
    
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
      
    }
  }
}

// 加载图片
String url = "https://httpbin.org/image";
iv_networkImageView.setVisibility(View.VISIBLE);
ImageLoader.ImageListener imageListener = imageLoader.getImageListener(iv_networkImageView,R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground);
imageLoader.get(url, imageListener);
```



<mark> ImageLoader 加载图片请求（带缓存） </mark>

新建一个BitmapCache工具类，复制代码，导包

![截屏2023-06-15 11.19.17](/Users/liuyu/Library/Application Support/typora-user-images/截屏2023-06-15 11.19.17.png)

```java
// 替换掉原来的ImageLoader.ImageCache
ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
```



<mark> NetworkImageView 加载图片 </mark>

```xml
// 在xml布局文件中，使用此标签来创建ImageView
<com.android.volley.toolbox.NetworkImageView></com.android.volley.toolbox.NetworkImageView>
```





