package com.example.zk3.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request=chain.request();//请求对象,原始对象，用户名密码
        Request.Builder builder = request.newBuilder();
        builder.addHeader("userId","1054");
        builder.addHeader("sessionId","15514967119571054");
        Request nrequest=builder.build();//修改过的request对象
        Response response= chain.proceed(nrequest);//响应对象
        return response;
    }
}
