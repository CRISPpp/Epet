package com.epetnet.epetnet.interceptor;

import com.alibaba.fastjson.JSON;

import com.epetnet.epetnet.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class EpetInterceptor implements HandlerInterceptor {
    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    public static String[] urls = {
            "/index"
    };
    public boolean check(String[] urls, String uri){
        for(String url: urls){
            boolean ret = PATH_MATCHER.match(url, uri);
            if(ret) return true;
        }
        return false;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if(check(urls, uri)){
            log.info("拦截到请求, 放行：" + uri );
            return true;
        }
        log.info("拦截到请求, 拦截：" + uri );
        response.getWriter().write(JSON.toJSONString(R.error("gg")));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
