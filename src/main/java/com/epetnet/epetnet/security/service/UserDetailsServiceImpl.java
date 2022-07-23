package com.epetnet.epetnet.security.service;

import com.epetnet.epetnet.entity.LoginUser;
import com.epetnet.epetnet.entity.User;
import com.epetnet.epetnet.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * 用户验证处理
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserService userService;


    public UserDetails createLoginUser(User user){
        return new LoginUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userService.selectUserByPhone(phone);
        if(user == null){
            log.info("登录用户：{} 不存在", phone);
            throw new RuntimeException("登录用户：" + phone + " 不存在");
        }
        return createLoginUser(user);
    }
}
