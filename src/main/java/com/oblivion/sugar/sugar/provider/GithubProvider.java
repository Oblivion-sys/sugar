package com.oblivion.sugar.sugar.provider;

import com.alibaba.fastjson.JSON;
import com.oblivion.sugar.sugar.dto.AccessTokenDto;
import com.oblivion.sugar.sugar.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto){
        // -------------------------------------------
        System.out.println("enter getAccessToken");
        // -------------------------------------------
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        // -------------------------------------------
        System.out.println("set getAccessToken");
        // -------------------------------------------
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        // -------------------------------------------
        System.out.println("post getAccessToken");
        // -------------------------------------------
        try {
            Response response = client.newCall(request).execute();
            // -------------------------------------------
            System.out.println("enter try");
            // -------------------------------------------
            String responseStr = response.body() != null ? response.body().string() : null;
            String accessToken = responseStr.split("&")[0].split("=")[1];
            return accessToken;
        } catch (IOException e){
            // -------------------------------------------
            System.out.println(e.toString());
            // -------------------------------------------
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        // -------------------------------------------
        System.out.println("enter getUser");
        // -------------------------------------------
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body()!= null ? response.body().string() : null;
            GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
            return githubUser;
        } catch (IOException e) {}
        return null;
    }
}