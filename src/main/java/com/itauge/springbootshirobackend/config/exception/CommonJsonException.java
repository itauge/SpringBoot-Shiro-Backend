package com.itauge.springbootshirobackend.config.exception;

import com.itauge.springbootshirobackend.util.CommonUtil;
import com.itauge.springbootshirobackend.util.constants.ErrorEnum;
import com.itauge.springbootshirobackend.vo.ResultVO;

/**
 * @description: 本系统使用的自定义错误类
 * 比如在校验参数时,如果不符合要求,可以抛出此错误类
 * 拦截器可以统一拦截此错误,将其中json返回给前端
 * */

public class CommonJsonException extends RuntimeException {

    ResultVO resultVO = new ResultVO();

    public CommonJsonException(ErrorEnum errorEnum){
        this.resultVO = CommonUtil.errorJson(errorEnum);
    }

    public ResultVO getResultVO(){
        return this.resultVO;
    }

}
