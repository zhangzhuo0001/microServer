package com.rayfay.bizcloud.microservices.poc.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rayfay.bizcloud.core.commons.exception.NRAPException;
import com.rayfay.bizcloud.microservices.poc.entity.UserInfo;
import com.rayfay.bizcloud.microservices.poc.entity.UserSearchCondition;
import com.rayfay.bizcloud.microservices.poc.message.SystemErrorCodeType;
import com.rayfay.bizcloud.microservices.poc.message.UserErrorCodeType;
import com.rayfay.bizcloud.microservices.poc.service.UserInfoService;
import com.rayfay.bizcloud.microservices.poc.util.BizCloudSorter;
import com.rayfay.bizcloud.microservices.poc.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhubj on 2018/4/2.
 */
@RestController
@RequestMapping(value = "/user")
public class UserInfoController {

    //默认每页记录数
    public static final String DEFAULT_PAGESIZE = "20";
    //默认当前页
    public static final String DEFAULT_PAGENUMBER = "0";
    private Logger logger = LoggerFactory.getLogger(UserInfoController.class);
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 创建人员身份信息
     *
     * @param userJson {@link UserInfo}
     * @return 返回结果信息
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public Object createUser(@RequestBody JSONObject userJson) {
        UserInfo user = ConventToUserInfo(userJson);

        Map<String, Object> responseMap;
        try {
            userValidCheck(user);
            if (user.getUserId() > 0) {
                throw new NRAPException(UserErrorCodeType.E_ID_NOT_NULL);
            }
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            UserInfo userTmp = userInfoService.insertUser(user);
            Map<String, Object> userMap = ResponseUtil.convertBean(userTmp);
            responseMap = ResponseUtil.makeSuccessResponse(userMap);
        } catch (NRAPException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "创建人员");
        }
        return responseMap;
    }

    /**
     * 删除人员身份信息
     *
     * @param userId 人员ID
     * @return 返回结果信息
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public Object deleteUser(@RequestParam Long userId) {
        Map<String, Object> responseMap;
        try {
            UserInfo userTmp = userInfoService.findUserById(userId);
            if (userTmp == null) {
                throw new NRAPException(SystemErrorCodeType.E_NOT_EXITS, "人员身份");
            }
            userInfoService.deleteUser(userId);
            responseMap = ResponseUtil.makeSuccessResponse();
        } catch (NRAPException e) {
            throw e;
        } catch (Exception e) {
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "删除人员");
        }

        return responseMap;
    }


    /**
     * 更新人员身份信息
     *
     * @param userJson {@link UserInfo}
     * @return 返回结果信息
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Object updateUser(@RequestBody JSONObject userJson) {
        UserInfo user = ConventToUserInfo(userJson);
        Map<String, Object> responseMap;
        try {
            userValidCheck(user);

            if(user.getUserId() == 0){
                throw new NRAPException(UserErrorCodeType.E_ID_NULL);
            }

            UserInfo userTmp = userInfoService.findUserById(user.getUserId());
            if (userTmp == null) {
                throw new NRAPException(SystemErrorCodeType.E_NOT_EXITS, "人员身份");
            }
            userTmp.setUpdateTime(new Date());
            userTmp.setBirthday(user.getBirthday());
            userTmp.setEmail(user.getEmail());
            userTmp.setName(user.getName());
            userTmp.setSex(user.getSex());
            userInfoService.insertUser(userTmp);
            responseMap = ResponseUtil.makeSuccessResponse();
        } catch (NRAPException e) {
            throw e;
        } catch (Exception e) {
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "更新人员");
        }
        return responseMap;
    }


    /**
     * 获取人员身份信息
     *
     * @param userId 人员ID
     * @return 返回结果信息
     */
    @RequestMapping(value = "/findUserById", method = RequestMethod.GET)
    public Object findUserById(@RequestParam(name = "userId") Long userId) {
        Map<String, Object> responseMap;
        try {
            UserInfo result = userInfoService.findUserById(userId);
            if (result == null) {
                throw new NRAPException(SystemErrorCodeType.E_NOT_EXITS, "人员身份");
            }
            SimpleDateFormat timeFormater = new SimpleDateFormat("yyyy-MM-dd");
            Map<String, Object> userMap = ResponseUtil.convertBean(result);
            userMap.put("birthday", timeFormater.format(result.getBirthday()));
            responseMap = ResponseUtil.makeSuccessResponse(userMap);
        } catch (NRAPException e) {
            throw e;
        } catch (Exception e) {
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "获取");
        }
        return responseMap;
    }

    /**
     * 根据检索条件获取人员身份信息(分页)
     *
     * @param pageSize    每页记录数
     * @param pageNumber  页码
     * @param queryObject {@link JSONObject}查询条件
     * @return 返回结果信息
     */
    @RequestMapping(value = "/findUserPageByQuery", method = RequestMethod.POST)
    public Object findUserPageByQuery(
            @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGESIZE, required = false) int pageSize,
            @RequestParam(name = "pageNumber", defaultValue = DEFAULT_PAGENUMBER, required = false) int pageNumber,
            @RequestBody JSONObject queryObject) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            JSONObject queryData = queryObject.getJSONObject("queryData");
            UserSearchCondition searchCondition = JSONObject.toJavaObject(queryData, UserSearchCondition.class);
            JSONArray sorter = queryObject.getJSONArray("sorter");
            List<BizCloudSorter> sorts = sorter.toJavaList(BizCloudSorter.class);
            Map<Integer, String> sexType = new HashMap<>();
            sexType.put(1, "男");
            sexType.put(2, "女");
            Page<UserInfo> result = userInfoService.findUserPageable(pageSize, pageNumber,
                    searchCondition, sorts);
            List<Map<String, Object>> rows = toLogMapList(result.getContent(), sexType);
            responseMap = ResponseUtil.makeSuccessResponse(result.getTotalElements(), rows);
        } catch (NRAPException e) {
            throw e;
        } catch (Exception e) {
            NRAPException ex = new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "获取");
            throw ex;
        }

        return responseMap;
    }

    private List<Map<String, Object>> toLogMapList(List<UserInfo> userList, Map<Integer, String> sexType) {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (UserInfo one : userList) {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("userId", one.getUserId());
            returnMap.put("sex", sexType.get(one.getSex()));
            returnMap.put("name", one.getName());
            returnMap.put("email", one.getEmail());
            SimpleDateFormat timeFormater = new SimpleDateFormat("yyyy-MM-dd");
            if(null != one.getBirthday())
                returnMap.put("birthday", timeFormater.format(one.getBirthday()));

            rows.add(returnMap);
        }
        return rows;
    }

    private UserInfo ConventToUserInfo(JSONObject userJson) {
        UserInfo user = new UserInfo();
        if (StringUtils.isNotEmpty(userJson.getString("sex"))) {
            user.setSex(Integer.parseInt(userJson.getString("sex")));
        }
        user.setName(userJson.getString("name"));
        user.setEmail(userJson.getString("email"));
        user.setUserId(0L);
        if (StringUtils.isNotEmpty(userJson.getString("userId"))) {
            user.setUserId(Long.parseLong(userJson.getString("userId")));
        }
        SimpleDateFormat timeFormater = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (StringUtils.isNotEmpty(userJson.getString("birthday"))) {
                user.setBirthday(timeFormater.parse(userJson.getString("birthday")));
            }
        }catch(Exception ex){
            throw new NRAPException(UserErrorCodeType.E_DATE_FORMAT);
        }
        return user;
    }

    /**
     * 人员身份信息校验
     *
     * @param user 人员身份信息
     */
    private void userValidCheck(UserInfo user) {
        if (user == null) {
            throw new NRAPException(UserErrorCodeType.E_IS_NULL, "人员身份信息");
        }
        //必须输入项目校验
        if (StringUtils.isEmpty(user.getName())) {
            throw new NRAPException(SystemErrorCodeType.E_NOT_NULL, "姓名");
        }
        //数据长度校验
        if (user.getName().length() > 128) {
            throw new NRAPException(UserErrorCodeType.E_EXTREME_LENGTH, "姓名", "128");
        }
        if (user.getEmail().length() > 100) {
            throw new NRAPException(UserErrorCodeType.E_EXTREME_LENGTH, "邮件", "100");
        }
        //性别校验
        if(user.getSex() != 1 && user.getSex() != 2){
            throw new NRAPException(UserErrorCodeType.E_SEX_TYPE);
        }
    }
}
