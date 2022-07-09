package com.epetnet.epetnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.epetnet.epetnet.jwt")
public class EpetnetApplication {
    public static void main(String[] args) {
        SpringApplication.run(EpetnetApplication.class, args);
    }

}
