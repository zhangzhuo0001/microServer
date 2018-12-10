package com.rayfay.bizcloud.microservices.poc.message;

import com.rayfay.bizcloud.core.commons.exception.ErrorCode;
import com.rayfay.bizcloud.core.commons.exception.ErrorCodeDefinition;
import com.rayfay.bizcloud.core.commons.exception.ErrorCodeValuedEnum;

/**
 * Created by shenfu on 2017/4/14.
 */
@ErrorCodeDefinition(thousands = 50)
public class SystemErrorCodeType {

    @ErrorCode(code = 102, msg = "{0}失败！")
    public static ErrorCodeValuedEnum E_ACTION_FALED;

    @ErrorCode(code = 103, msg = "{0}不存在！")
    public static ErrorCodeValuedEnum E_NOT_EXITS;

    @ErrorCode(code = 104, msg = "{0}不可为空！")
    public static ErrorCodeValuedEnum E_NOT_NULL;
}
