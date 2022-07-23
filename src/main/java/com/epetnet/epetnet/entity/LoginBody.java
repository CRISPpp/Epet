package com.epetnet.epetnet.entity;

import lombok.Data;

@Data
public class LoginBody {
    private String phone;
    private String password;
}