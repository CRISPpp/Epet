package com.epetnet.epetnet.entity;

import lombok.Data;

@Data
public class LoginValidateBody {
    private String phone;
    private String validateCode;
}
