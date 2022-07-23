package com.epetnet.epetnet.security.handle;

import com.alibaba.fastjson2.JSON;
import com.epetnet.epetnet.common.R;
import com.epetnet.epetnet.entity.LoginUser;
import com.epetnet.epetnet.security.service.TokenService;
import com.epetnet.epetnet.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义的登出处理
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);

        if(loginUser != null){
            //删除用户记录，同时去除缓存的记录
            tokenService.delLoginUser(loginUser.getToken());
        }
        ServletUtils.renderString(response, JSON.toJSONString(R.success("退出成功")));
    }
}
