package com.rayfay.bizcloud.apps.pocapp;


import com.rayfay.bizcloud.platforms.ui.annotation.EnableWebServerWithReact;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableWebServerWithReact
public class PocAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(PocAppApplication.class, args);
    }
}
