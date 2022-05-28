package com.epetnet.epetnet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.epetnet.epetnet.entity.User;
import com.epetnet.epetnet.mapper.UserMapper;
import com.epetnet.epetnet.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
