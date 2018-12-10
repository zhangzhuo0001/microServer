package com.rayfay.bizcloud.apps.pocapp.client;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "POC-SERVICE")
public interface ExtractClient {
    @RequestMapping(method = {RequestMethod.GET}, value = "/user/findUserById")
    JSONObject findUserById(@RequestParam(name = "userId") Long userId);

    @RequestMapping(method = {RequestMethod.POST}, value = "/user/createUser")
    JSONObject createUser(@RequestBody JSONObject user);

    @RequestMapping(method = {RequestMethod.GET}, value = "/user/deleteUser")
    JSONObject deleteUser(@RequestParam(name = "userId") Long userId);

    @RequestMapping(method = {RequestMethod.POST}, value = "/user/updateUser")
    JSONObject updateUser(@RequestBody JSONObject user);

    @RequestMapping(method = {RequestMethod.POST}, value = "/user/findUserPageByQuery")
    JSONObject findUserPageByQuery(@RequestParam(name = "pageSize", required = false) int pageSize,
                                   @RequestParam(name = "pageNumber", required = false) int pageNumber,
                                   @RequestBody JSONObject queryObject);
    

    @RequestMapping(method = {RequestMethod.GET}, value = "/test/deleteCarBox")
    JSONObject deleteCarBox(@RequestParam(name = "id") Long id);
    
    @RequestMapping(method = {RequestMethod.POST}, value = "/test/queryDatasByPages")
    JSONObject queryDatasByPages(@RequestParam(name = "pageSize", required = false) int pageSize,
                                   @RequestParam(name = "currentPage", required = false) int currentPage,
                                   @RequestBody JSONObject queryObject);

    @RequestMapping(value = "/test/createCarBox", method = {RequestMethod.POST})
    public Object createCarBox(@RequestBody JSONObject carBox);
    
    @RequestMapping(value = "/test/findCarBoxById", method = RequestMethod.GET)
    public Object findCarBoxById(@RequestParam(name = "id") Long id);
    
    @RequestMapping(value = "/test/updateCarBox", method = RequestMethod.POST)
    public Object updateCarBox(@RequestBody JSONObject carBox);
}
