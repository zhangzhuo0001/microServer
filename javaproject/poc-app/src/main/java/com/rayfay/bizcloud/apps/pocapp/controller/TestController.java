package com.rayfay.bizcloud.apps.pocapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.rayfay.bizcloud.apps.pocapp.client.ExtractClient;
import com.rayfay.bizcloud.apps.pocapp.message.SystemErrorCodeType;
import com.rayfay.bizcloud.core.commons.exception.NRAPException;

@RestController
@RequestMapping(value = "/test")
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    private final ExtractClient extractClient;
    
    public TestController(ExtractClient extractClient){
    	this.extractClient=extractClient;
    }
    @RequestMapping(value = "/queryDatasByPages", method = RequestMethod.POST)
    public Object queryDatasByPages(
    		@RequestParam(name = "pageSize", required = false) int pageSize,
            @RequestParam(name = "currentPage", required = false) int currentPage,
            @RequestBody(required = false) JSONObject queryObject){
//    	 queryObject =new JSONObject();
    	return extractClient.queryDatasByPages(pageSize, currentPage, queryObject);
    }
    
    @RequestMapping(value = "/deleteCarBox", method = RequestMethod.GET)
    public Object deleteCarBox(@RequestParam(name = "id") Long id) {
        try {
            return extractClient.deleteCarBox(id);
        } catch (Exception e) {
            logger.error("删除信息失败。\n{}", e.getMessage());
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "删除信息");
        }
    }

    @RequestMapping(value = "/createCarBox", method = RequestMethod.POST)
    public Object createCarBox(@RequestBody JSONObject carBox){
//    	 queryObject =new JSONObject();
    	return extractClient.createCarBox(carBox);
    }
    @RequestMapping(value = "/updateCarBox", method = RequestMethod.POST)
    public Object updateCarBox(@RequestBody JSONObject carBox){
//    	 queryObject =new JSONObject();
    	return extractClient.updateCarBox(carBox);
    }
    @RequestMapping(value = "/findCarBoxById", method = RequestMethod.GET)
    public Object findCarBoxById(@RequestParam(name = "id") Long id) {
        try {
            return extractClient.findCarBoxById(id);
        } catch (Exception e) {
//            logger.error("删除信息失败。\n{}", e.getMessage());
        	e.printStackTrace();
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "删除信息");
        }
    }
}
