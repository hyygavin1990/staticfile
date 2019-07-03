package cn.datawin.controller;

import cn.datawin.entity.CommonErrorCode;
import cn.datawin.entity.CommonResponse;

/**
 * Created by satroler on 17-3-13.
 */
public class BaseController {
    public CommonResponse buildSuccessResponse() {
        return new CommonResponse(CommonErrorCode.SUCCESS.getCode(), CommonErrorCode.SUCCESS.getMessage(), CommonErrorCode.SUCCESS.getStatus());
    }
    public CommonResponse buildSuccessResponse(Object data) {
        return new CommonResponse(CommonErrorCode.SUCCESS.getCode(),data, CommonErrorCode.SUCCESS.getStatus());
    }
    public CommonResponse buildErrorResponse() {
        return new CommonResponse(CommonErrorCode.ERROR.getCode(), CommonErrorCode.ERROR.getMessage(), CommonErrorCode.ERROR.getStatus());
    }
    public CommonResponse buildErrorResponse( Object data) {
        return new CommonResponse(CommonErrorCode.ERROR.getCode(),data, CommonErrorCode.ERROR.getStatus());
    }

}
