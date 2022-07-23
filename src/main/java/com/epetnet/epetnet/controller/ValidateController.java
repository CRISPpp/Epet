package com.epetnet.epetnet.controller;

import com.epetnet.epetnet.common.Constants;
import com.epetnet.epetnet.common.R;
import com.epetnet.epetnet.entity.ValidateBody;
import com.epetnet.epetnet.util.RedisCache;
import com.epetnet.epetnet.util.ValidateCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Api("验证码")
@RestController
@RequestMapping("/validate")
public class ValidateController {

    @Autowired
    RedisCache redisCache;

    @ApiOperation("登录验证码")
    @PostMapping("/login")
    public R<String> getLoginCode(@RequestBody ValidateBody validateBody){
        String phone = validateBody.getPhone();
        //生成验证码
        String validate = ValidateCodeUtils.generateValidateCode4String(4);
        //放入缓存
        String key = Constants.PHONE_LOGIN_VALIDATE_KEY + phone;
        redisCache.setCacheObject(key, validate, 60, TimeUnit.SECONDS);

        return R.success("已发送");
    }

    @ApiOperation("注册验证码")
    @PostMapping("/register")
    public R<String> getRegisterCode(@RequestBody ValidateBody validateBody){
        String phone = validateBody.getPhone();
        //生成验证码
        String validate = ValidateCodeUtils.generateValidateCode4String(4);
        //放入缓存
        String key = Constants.PHONE_REGISTER_VALIDATE_KEY + phone;
        redisCache.setCacheObject(key, validate, 60, TimeUnit.SECONDS);

        return R.success("已发送");
    }
}
