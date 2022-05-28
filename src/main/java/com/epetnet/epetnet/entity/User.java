package com.epetnet.epetnet.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;//序列化版本


    private Long id;
    private String icon;
    private String nickname;
    private String sex;
    private String city;
    private String position;
    private String profile;
    private String mail;
    private String phone;
    private String password;

    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
