package com.epetnet.epetnet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.epetnet.epetnet.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
