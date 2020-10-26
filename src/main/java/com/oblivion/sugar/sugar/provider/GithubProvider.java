package com.oblivion.sugar.sugar.provider;

import com.alibaba.fastjson.JSON;
import com.oblivion.sugar.sugar.dto.AccessTokenDto;
import com.oblivion.sugar.sugar.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto){
        // -------------------------------------------
        System.out.println("enter getAccessToken");
        System.out.println(JSON.toJSONString(accessTokenDto));
        // -------------------------------------------
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDto),mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseStr = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
            assert responseStr != null;
            System.out.println(responseStr);
            return responseStr.split("&")[0].split("=")[1];
        } catch (IOException e){
            // -------------------------------------------
            System.out.println(e.toString());
            // -------------------------------------------
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        // -------------------------------------------
        System.out.println("enter getUser:" + accessToken);
        // -------------------------------------------
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body()!= null ? Objects.requireNonNull(response.body()).string() : null;
            return JSON.parseObject(str,GithubUser.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}