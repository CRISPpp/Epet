package com.epetnet.epetnet.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.epetnet.epetnet.common.R;
import com.epetnet.epetnet.entity.User;
import com.epetnet.epetnet.service.MailService;
import com.epetnet.epetnet.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;
    @PostMapping("/register")
    public R<String> userRegister(@RequestBody User user){
        //mailService.sendSimpleMail("2715554797@qq.com", "test", "test");
        log.info(String.valueOf(user));
        //如果user的手机或邮箱为空返回错误
        if(user.getPhone() == null || user.getMail() == null) return R.error("手机或邮箱不能为空");


        //查看用户的手机或者邮箱是否存在，如果存在则返回error
        LambdaQueryWrapper<User> wrapperPhone = new LambdaQueryWrapper<>();
        wrapperPhone.eq(User::getPhone, user.getPhone());
        User userPhone = userService.getOne(wrapperPhone);
        if(userPhone != null) return R.error("手机号已经被注册");
        LambdaQueryWrapper<User> wrapperMail = new LambdaQueryWrapper<>();
        wrapperMail.eq(User::getMail,user.getMail());
        User userMail = userService.getOne(wrapperMail);
        if(userMail != null) return R.error("邮箱已经被注册");


        //存入数据
        userService.save(user);
        return R.success("注册成功");
    }
}
