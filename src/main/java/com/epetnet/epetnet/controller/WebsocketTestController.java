package com.epetnet.epetnet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//add xxx/user  xx/user/id

@Controller
public class WebsocketTestController {
    @GetMapping("/index")
    public String getIndex(){
//        if(true){
//            //throw new SystemException(Code.SAVE_ERROR, "systemerror");
//            //throw new BusinessException(Code.BUSINESS_ERROR, "businesserror");
//            throw new RuntimeException();
//        }
        return "index";
    }
}
