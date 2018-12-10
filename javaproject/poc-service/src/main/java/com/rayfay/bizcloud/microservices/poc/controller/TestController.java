package com.rayfay.bizcloud.microservices.poc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rayfay.bizcloud.core.commons.exception.NRAPException;
import com.rayfay.bizcloud.microservices.poc.entity.CarBoxSearchCondition;
import com.rayfay.bizcloud.microservices.poc.entity.T_car_box;
import com.rayfay.bizcloud.microservices.poc.entity.UserInfo;
import com.rayfay.bizcloud.microservices.poc.message.SystemErrorCodeType;
import com.rayfay.bizcloud.microservices.poc.service.CarBoxService;
import com.rayfay.bizcloud.microservices.poc.util.BizCloudSorter;
import com.rayfay.bizcloud.microservices.poc.util.ResponseUtil;

@RestController
@RequestMapping(value = "/test")
public class TestController {
	 //默认每页记录数
    public static final String DEFAULT_PAGESIZE = "20";
    //默认当前页
    public static final String DEFAULT_PAGENUMBER = "0";
//    private Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private CarBoxService carBoxService;
    @RequestMapping(value="/queryDatasByPages" , method = RequestMethod.POST)
    public Object queryDatasByPages(
    		@RequestParam(name = "pageSize", required = false) int pageSize,
            @RequestParam(name = "currentPage", required = false) int currentPage,
            @RequestBody(required = false) JSONObject queryObject){
    	Map<String, Object> responseMap = new HashMap<>();
    	JSONObject queryData = queryObject.getJSONObject("queryData");
    	CarBoxSearchCondition searchCondition = JSONObject.toJavaObject(queryData, CarBoxSearchCondition.class);
        JSONArray sorter = queryObject.getJSONArray("sorter");
        List<BizCloudSorter> sorts = sorter.toJavaList(BizCloudSorter.class);
        Page<T_car_box> result = carBoxService.findDataPageable(pageSize, currentPage,
                searchCondition, sorts);
        List<Map<String, Object>> rows = toLogMapList(result.getContent());
        responseMap = ResponseUtil.makeSuccessResponse(result.getTotalElements(), rows);
    	return responseMap;
    }
    private List<Map<String, Object>> toLogMapList(List<T_car_box> userList) {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (T_car_box one : userList) {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("id", one.getId());
            returnMap.put("car", one.getCar());
            returnMap.put("box", one.getBox());
            rows.add(returnMap);
        }
        return rows;
    }
    
    @RequestMapping(value = "/deleteCarBox", method = RequestMethod.GET)
    public Object deleteCarBox(@RequestParam Long id) {
        Map<String, Object> responseMap;
        try {
            T_car_box carBoxTmp = carBoxService.findCarBoxById(id);
            if (carBoxTmp == null) {
                throw new NRAPException(SystemErrorCodeType.E_NOT_EXITS, "CarBox信息");
            }
            carBoxService.deleteCarBox(id);
            responseMap = ResponseUtil.makeSuccessResponse();
            responseMap.put("message", "删除成功！");
        } catch (NRAPException e) {
            throw e;
        } catch (Exception e) {
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "删除人员");
        }

        return responseMap;
    }
    
    @RequestMapping(value = "/createCarBox", method = RequestMethod.POST)
    public Object createCarBox(@RequestBody JSONObject carBox){
    	Map<String, Object> responseMap;
    	T_car_box carBoxJavaObject = JSONObject.toJavaObject(carBox, T_car_box.class);
    	carBoxService.insertCarBox(carBoxJavaObject);
    	responseMap = ResponseUtil.makeSuccessResponse();
        responseMap.put("message", "新增成功！");
    	return responseMap;
    }
    
    @RequestMapping(value = "/updateCarBox", method = RequestMethod.POST)
    public Object updateCarBox(@RequestBody JSONObject carBox){
    	Map<String, Object> responseMap;
    	T_car_box carBoxJavaObject = JSONObject.toJavaObject(carBox, T_car_box.class);
    	try {
			carBoxService.insertCarBox(carBoxJavaObject);
			responseMap = ResponseUtil.makeSuccessResponse();
			responseMap.put("message", "更新成功！");
		} catch (Exception e) {
			e.printStackTrace();
			responseMap = ResponseUtil.makeErrorResponse("500","更新异常");
//			responseMap.put("message", "更新成功！");
		}
    	return responseMap;
    }
    
    @RequestMapping(value = "/findCarBoxById", method = RequestMethod.GET)
    public Object findCarBoxById(@RequestParam Long id) {
        Map<String, Object> responseMap;
        try {
            T_car_box carBoxTmp = carBoxService.findCarBoxById(id);
            if (carBoxTmp == null) {
                throw new NRAPException(SystemErrorCodeType.E_NOT_EXITS, "CarBox信息");
            }
        	JSONObject row =  (JSONObject) JSONObject.toJSON(carBoxTmp);
            responseMap = ResponseUtil.makeSuccessResponse();
            responseMap.put("row", row);
        } catch (NRAPException e) {
            throw e;
        } catch (Exception e) {
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "查找CarBox信息");
        }

        return responseMap;
    }
}
