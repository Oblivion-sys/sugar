package com.oblivion.sugar.sugar.controller;

import com.alibaba.fastjson.JSON;
import com.oblivion.sugar.sugar.dto.AccessTokenDto;
import com.oblivion.sugar.sugar.dto.GithubUser;
import com.oblivion.sugar.sugar.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("github.client.id")
    private  String clientId;

    @Value("github.client.secret")
    private  String clientSecret;

    @Value("github.redirect.uri")
    private  String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        System.out.println("enter callback");
        System.out.println("【code】" + code);
        System.out.println("【state】" + state);
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setState(state);
        String result = githubProvider.getAccessToken(accessTokenDto);
//        GithubUser githubUser = githubProvider.getUser(result);
        System.out.println(result);
        return "index";
    }
}
