package com.epetnet.epetnet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.epetnet.epetnet.entity.User;


public interface UserService extends IService<User> {
    public User selectUserByUserName(String userName);
    public User selectUserByPhone(String phone);
}
