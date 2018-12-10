package com.rayfay.bizcloud.microservices.poc.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author shenfu
 */
@Entity
@Getter
@Setter
@Table(name = "T_USER")
public class UserInfo {
    /**
     * 人员信息唯一ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId", insertable = true, updatable = true, nullable = false, length = 20)
    private Long userId;

    /**
     * 姓名
     */
    @Column(name = "name", length = 128)
    private String name;

    /**
     * 性别
     */
    @Column(name = "sex", length = 2)
    private Integer sex;

    /**
     * 邮件
     */
    @Column(name = "email", length = 100)
    private String email;

    /**
     * 生日
     */
    @Column(name = "birthday")//, columnDefinition = "TIMESTAMP")
    private Date birthday;

    /**
     * 创建时间
     */
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime")//, columnDefinition = "TIMESTAMP")
    private Date createTime;

    /**
     * 更新时间
     */
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime")//, columnDefinition = "TIMESTAMP")
    private Date updateTime;
}
