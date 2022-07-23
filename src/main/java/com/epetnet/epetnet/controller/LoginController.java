package com.epetnet.epetnet.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.epetnet.epetnet.common.Constants;
import com.epetnet.epetnet.common.R;
import com.epetnet.epetnet.entity.LoginBody;
import com.epetnet.epetnet.entity.LoginValidateBody;
import com.epetnet.epetnet.entity.User;
import com.epetnet.epetnet.security.service.SysLoginService;
import com.epetnet.epetnet.service.UserService;
import com.epetnet.epetnet.util.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录Controller
 */
@Slf4j
@RestController
@RequestMapping("/login")
@Api("登录")
public class LoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService userService;

    @ApiOperation("通过密码登录")
    @PostMapping("/password")
    public R<String> loginByPassword(@RequestBody LoginBody loginBody){
        String token = loginService.login(loginBody.getPhone(), loginBody.getPassword());
        return R.success(token);
    }

    @ApiOperation("通过验证码登录")
    @PostMapping("/validate")
    public R<String> loginByValidate(@RequestBody LoginValidateBody loginValidateBody){
        String key = Constants.PHONE_LOGIN_VALIDATE_KEY + loginValidateBody.getPhone();
        String code = redisCache.getCacheObject(key);

        if(code == null) return R.error("验证码已过期");
        if(!(code.equals(loginValidateBody.getValidateCode()))) return R.error("验证码错误");

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, loginValidateBody.getPhone());
        User user = userService.getOne(wrapper);
        if(user == null) return R.error("该手机号用户不存在");

        String token = loginService.login(user.getPhone(), user.getPassword());
        return R.success(token);
    }
}
