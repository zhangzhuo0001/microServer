package com.rayfay.bizcloud.apps.pocapp.controller;

import com.alibaba.fastjson.JSONObject;
import com.rayfay.bizcloud.apps.pocapp.client.ExtractClient;
import com.rayfay.bizcloud.apps.pocapp.message.SystemErrorCodeType;
import com.rayfay.bizcloud.core.commons.exception.NRAPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserInfoController {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);
    private final ExtractClient extractClient;

    @Autowired
    public UserInfoController(ExtractClient extractClient) {
        this.extractClient = extractClient;
    }

    /**
     * 创建人员身份信息
     *
     * @param user {@link JSONObject}
     * @return 返回结果信息
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public Object createUser(@RequestBody JSONObject user) {
        try {
            return extractClient.createUser(user);
        } catch (Exception e) {
            logger.error("创建人员身份信息失败。\n{}", e.getMessage());
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "创建人员身份信息");
        }
    }

    /**
     * 删除人员身份信息
     *
     * @param userId 人员ID
     * @return 返回结果信息
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public Object deleteUser(@RequestParam(name = "userId") Long userId) {
        try {
            return extractClient.deleteUser(userId);
        } catch (Exception e) {
            logger.error("删除人员身份信息失败。\n{}", e.getMessage());
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "删除人员身份信息");
        }
    }

    /**
     * 更新人员身份信息
     *
     * @param user {@link JSONObject}
     * @return 返回结果信息
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Object updateUser(@RequestBody JSONObject user) {
        try {
            return extractClient.updateUser(user);
        } catch (Exception e) {
            logger.error("更新人员身份信息失败。\n{}", e.getMessage());
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "更新人员身份信息");
        }
    }


    /**
     * 获取人员身份信息
     *
     * @param userId 人员ID
     * @return 返回结果信息
     */
    @RequestMapping(value = "/findUserById", method = RequestMethod.GET)
    public Object findUserById(@RequestParam(name = "userId") Long userId) {
        try {
            return extractClient.findUserById(userId);
        } catch (Exception e) {
            logger.error("获取人员身份信息失败。\n{}", e.getMessage());
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "获取人员身份信息");
        }
    }

    /**
     * 根据检索条件获取人员身份信息(分页)
     *
     * @param pageSize    每页记录数
     * @param pageNumber  页码
     * @param queryObject {@link JSONObject}查询条件&排序
     * @return 返回结果信息
     */
    @RequestMapping(value = "/findUserPageByQuery", method = RequestMethod.POST)
    public Object findUserPageByQuery(
            @RequestParam(name = "pageSize", required = false) int pageSize,
            @RequestParam(name = "pageNumber", required = false) int pageNumber,
            @RequestBody(required = false) JSONObject queryObject) {
        try {
            return extractClient.findUserPageByQuery(pageSize, pageNumber, queryObject);
        } catch (Exception e) {
            logger.error("获取人员身份信息失败。\n{}", e.getMessage());
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "获取人员身份信息");
        }
    }
}
