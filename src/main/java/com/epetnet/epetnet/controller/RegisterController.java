package com.epetnet.epetnet.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.epetnet.epetnet.common.Constants;
import com.epetnet.epetnet.common.R;
import com.epetnet.epetnet.entity.RegisterValidateBody;
import com.epetnet.epetnet.entity.User;
import com.epetnet.epetnet.service.UserService;
import com.epetnet.epetnet.util.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api("注册")
@RestController
@Slf4j
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    RedisCache redisCache;

    //判断手机号是否违规
    public  boolean isMobile(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0|5|6|7|9])|(15[0-3])|(15[5-9])|(16[6|7])|(17[2|3|5|6|7|8])|(18[0-9])|(19[1|8|9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    @ApiOperation("通过手机号注册")
    @PostMapping
    public R<String> userRegister(@RequestBody RegisterValidateBody registerValidateBody){
        User user = registerValidateBody.getUser();

        //mailService.sendSimpleMail("2715554797@qq.com", "test", "test");
        log.info(String.valueOf(user));
        //如果user的手机为空或错误返回错误
        if(user.getPhone() == null || !isMobile(user.getPhone())) return R.error("手机号错误");

        //查看用户的手机是否存在，如果存在则返回error
        LambdaQueryWrapper<User> wrapperPhone = new LambdaQueryWrapper<>();
        wrapperPhone.eq(User::getPhone, user.getPhone());
        User userPhone = userService.getOne(wrapperPhone);
        if(userPhone != null) return R.error("手机号已经被注册");

        String key = Constants.PHONE_REGISTER_VALIDATE_KEY + user.getPhone();
        String code = redisCache.getCacheObject(key);
        if(code == null) return R.error("验证码已过期");

        if(!(code.equals(registerValidateBody.getValidateCode()))) return R.error("验证码错误");


        String psd = user.getPassword();
        user.setPassword(passwordEncoder.encode(psd));


        //存入数据
        userService.save(user);
        return R.success("注册成功");
    }
}
