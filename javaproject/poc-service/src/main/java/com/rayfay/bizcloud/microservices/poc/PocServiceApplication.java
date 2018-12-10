package com.rayfay.bizcloud.microservices.poc;

import com.rayfay.bizcloud.core.commons.EnablePltWithMicroService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by zhubj on 2018/4/2.
 */
@SpringBootApplication
@EnablePltWithMicroService
public class PocServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PocServiceApplication.class, args);
    }
}
