package com.rayfay.bizcloud.microservices.poc.message;

import com.rayfay.bizcloud.core.commons.exception.ErrorCode;
import com.rayfay.bizcloud.core.commons.exception.ErrorCodeDefinition;
import com.rayfay.bizcloud.core.commons.exception.ErrorCodeValuedEnum;

@ErrorCodeDefinition(thousands = 60)
public class UserErrorCodeType {
    @ErrorCode(code = 101, msg = "({0}为空！")
    public static ErrorCodeValuedEnum E_IS_NULL;

    @ErrorCode(code = 102, msg = "{0}超过最大长度{1}位！")
    public static ErrorCodeValuedEnum E_EXTREME_LENGTH;

    @ErrorCode(code = 103, msg = "创建人员身份时人员ID不可设值！")
    public static ErrorCodeValuedEnum E_ID_NOT_NULL;

    @ErrorCode(code = 104, msg = "更新人员身份时人员ID不可为空！")
    public static ErrorCodeValuedEnum E_ID_NULL;

    @ErrorCode(code = 105, msg = "生日的格式不正确！")
    public static ErrorCodeValuedEnum E_DATE_FORMAT;

    @ErrorCode(code = 106, msg = "性别类型不正确(1:男,2:女)！")
    public static ErrorCodeValuedEnum E_SEX_TYPE;
}
