package com.epetnet.epetnet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//add xxx/user  xx/user/id

@Controller
public class WebsocketTestController {
    @GetMapping("/index")
    public String getIndex() throws Exception {
        return "index";
    }
}
