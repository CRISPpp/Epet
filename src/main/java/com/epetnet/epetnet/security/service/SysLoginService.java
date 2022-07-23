package com.epetnet.epetnet.security.service;

import com.epetnet.epetnet.entity.LoginUser;
import com.epetnet.epetnet.service.UserService;
import com.epetnet.epetnet.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 登录校验
 */
@Service
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService userService;

    /**
     * 登录验证
     */
    public String login(String phone, String password){
        //用户验证
        Authentication authentication = null;
        try{
            //回去调用UserDetailServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
        }catch (Exception e){
            e.printStackTrace();
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return tokenService.createToken(loginUser);
    }


}
