package com.epetnet.epetnet.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ApiModel
public class User implements Serializable {
    private static final long serialVersionUID = 1123123123L;//序列化版本

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "头像")
    private String icon;
    @ApiModelProperty(value = "性别")
    private String sex;
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "位置")
    private String position;
    @ApiModelProperty(value = "简介")
    private String profile;
    @ApiModelProperty(value = "邮箱")
    private String mail;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "用户名")
    private String userName;

    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    @ApiModelProperty(value = "逻辑删除", example = "1")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
