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
        // -------------------------------------------
        System.out.println("enter callback");
        // -------------------------------------------
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id("e31daf76b4835054d13a");
        accessTokenDto.setClient_secret("073f23f5cca2720471f6f6b96c348da77f00dfe7");
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        if(accessToken != null) {
            GithubUser githubUser = githubProvider.getUser(accessToken);
            if(githubUser != null) {
                // -------------------------------------------
                System.out.println(githubUser.getName());
                // -------------------------------------------
            }else{
                System.out.println("no user");
            }
        }
        return "index";
    }
}
