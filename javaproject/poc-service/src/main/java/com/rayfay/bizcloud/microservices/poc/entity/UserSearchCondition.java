package com.rayfay.bizcloud.microservices.poc.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchCondition {
    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private int sex;

    /**
     * 生日start
     */
    private long startBirthday;

    /**
     * 生日end
     */
    private long endBirthday;

}

