package com.oblivion.sugar.sugar.provider;

import com.alibaba.fastjson.JSON;
import com.oblivion.sugar.sugar.dto.AccessTokenDto;
import com.oblivion.sugar.sugar.dto.GithubUserDto;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class GithubProvider {

    @Value("${github.token.url}")
    private String tokenUrl;

    @Value("${github.user.url}")
    private String userUrl;

    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDto),mediaType);
        Request request = new Request.Builder()
                .url(tokenUrl)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()){
            String responseStr = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
            assert responseStr != null;
            return responseStr.split("&")[0].split("=")[1];
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public GithubUserDto getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(userUrl)
                .addHeader("Authorization","token " + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body()!= null ? Objects.requireNonNull(response.body()).string() : null;
            System.out.println(str); 
            return JSON.parseObject(str, GithubUserDto.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}