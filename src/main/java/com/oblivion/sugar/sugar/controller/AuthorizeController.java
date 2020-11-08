package com.oblivion.sugar.sugar.controller;

import com.oblivion.sugar.sugar.dto.AccessTokenDto;
import com.oblivion.sugar.sugar.dto.GithubUserDto;
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

    @Value("${github.client.id}")
    private  String clientId;

    @Value("${github.client.secret}")
    private  String clientSecret;

    @Value("${github.redirect.uri}")
    private  String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        // 获取code参数设置
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setState(state);
        // 获取access_token
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        // 获取用户信息
        if(accessToken != null) {
            GithubUserDto githubUser = githubProvider.getUser(accessToken);
            if(githubUser != null) {
                // -------------------------------------------
                System.out.println(githubUser.getId());
                // -------------------------------------------
            }else{
                System.out.println("no user");
            }
        }
        return "index";
    }
}
