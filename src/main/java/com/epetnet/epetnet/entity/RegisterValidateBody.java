package com.epetnet.epetnet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterValidateBody{
    private String validateCode;
    private User user;
}
