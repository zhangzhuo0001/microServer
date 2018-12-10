package com.rayfay.bizcloud.apps.pocapp.message;

import com.rayfay.bizcloud.core.commons.exception.ErrorCode;
import com.rayfay.bizcloud.core.commons.exception.ErrorCodeDefinition;
import com.rayfay.bizcloud.core.commons.exception.ErrorCodeValuedEnum;

/**
 * Created by jujun on 2017/5/24.
 */
@ErrorCodeDefinition(thousands = 50)
public class SystemErrorCodeType {
    @ErrorCode(code = 102, msg = "{0}失败！")
    public static ErrorCodeValuedEnum E_ACTION_FALED;
}
