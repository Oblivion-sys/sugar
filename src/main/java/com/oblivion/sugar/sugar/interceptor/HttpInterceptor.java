package com.oblivion.sugar.sugar.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

import java.io.IOException;
import java.net.URLDecoder;

public class HttpInterceptor implements Interceptor {
    public static String TAG = "HttpInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String methodName = request.method();
        if (methodName.equalsIgnoreCase("GET")) {

        } else if (methodName.equalsIgnoreCase("POST")) {
            RequestBody mRequestBody = request.body();
            if (mRequestBody != null) {
                String msg = "-url--" + methodName + "--" + request.url();
                System.out.println(msg);
            }
        }

        Response response = chain.proceed(request);
        return response;
    }

    /**
     * 读取参数
     *
     * @param requestBody
     * @return
     */
    private String getParam(RequestBody requestBody) {
        Buffer buffer = new Buffer();
        String logparm;
        try {
            requestBody.writeTo(buffer);
            logparm = buffer.readUtf8();
            logparm = URLDecoder.decode(logparm, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return logparm;
    }
}
